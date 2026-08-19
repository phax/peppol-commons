/*
 * Copyright (C) 2015-2026 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppolid.peppol.enduser;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.GuardedBy;
import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.concurrent.SimpleReadWriteLock;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.state.EChange;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringRemove;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IParticipantIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * This class helps to determine a unique End User ID from a Peppol participant identifier, as it is
 * e.g. needed for the Peppol End User Statistics Report (EUSR).
 * <p>
 * Using the participant identifier as the End User ID is only a mediocre simplification, because
 * several countries have multiple identifier schemes running in parallel, that all identify the
 * same End User. Sending to <code>0208:0123456789</code> and to <code>9925:BE0123456789</code>
 * clearly targets the same End User, but counting the participant identifiers would result in two
 * different End Users.
 * </p>
 * <p>
 * Therefore this class applies a list of {@link IPeppolEndUserIDMapping} rules onto the provided
 * participant identifier, before it is converted to the End User ID String. The default mappings
 * are {@link #MAPPING_BE_VAT_TO_BE_EN}, {@link #MAPPING_DE_VAT_TO_DE_GEBA} and
 * {@link #MAPPING_FI_OVT_TO_FI_OVT2}. Additional mappings can be added via
 * {@link #registerMapping(IPeppolEndUserIDMapping)} and the default mappings can be removed via
 * {@link #unregisterMapping(IPeppolEndUserIDMapping)},
 * {@link #unregisterAllMappingsOfSourceISO6523Code(String)} or {@link #removeAllMappings()}. The
 * mappings are evaluated in the order of their registration and the first applicable mapping wins.
 * </p>
 * <p>
 * Independent of the mappings, all identifiers are unified using the configured
 * {@link IParticipantIdentifierFactory} (see {@link #getIdentifierFactory()}), so that only the
 * case of an identifier value does not lead to different End User IDs.
 * </p>
 * <p>
 * The created End User IDs are URI encoded participant identifiers - e.g.
 * <code>iso6523-actorid-upis::0208:0123456789</code>.
 * </p>
 *
 * @author Philip Helger
 * @since 12.8.1
 * @see <a href="https://github.com/phax/peppol-commons/issues/80">Issue #80</a>
 */
@ThreadSafe
public final class PeppolEndUserHelper
{
  private static final class FI
  {
    /** The fixed prefix of a Finnish OVT code */
    private static final String FI_OVT_PREFIX = "0037";
    /** The minimum length of a Finnish OVT code: 4 chars prefix plus 8 chars Business ID */
    private static final int FI_OVT_MIN_LENGTH = 12;
    /** The maximum length of a Finnish OVT code: minimum length plus 5 chars suffix */
    private static final int FI_OVT_MAX_LENGTH = 17;

    @Nullable
    static String getFinnishOVTCode (@NonNull final String sValue)
    {
      // The Business ID may contain a hyphen in scheme 0037, whereas scheme 0216 allows digits and
      // letters only
      final String sPlainValue = StringRemove.removeAll (sValue, '-');

      // The prefix is optional in scheme 0037 but mandatory in scheme 0216. As the Business ID
      // consists of 8 digits, a value that already contains the prefix has at least the minimum
      // length
      final boolean bHasPrefix = sPlainValue.startsWith (FI_OVT_PREFIX) && sPlainValue.length () >= FI_OVT_MIN_LENGTH;
      final String ret = bHasPrefix ? sPlainValue : FI_OVT_PREFIX + sPlainValue;

      // Don't map values that are no valid OVT codes
      return ret.length () >= FI_OVT_MIN_LENGTH && ret.length () <= FI_OVT_MAX_LENGTH ? ret : null;
    }
  }

  /**
   * Belgium: map the Belgian VAT numbers (<code>9925</code>) onto the Belgian enterprise numbers
   * (<code>0208</code>) by removing the leading country code <code>BE</code> (case insensitive) if
   * present.
   */
  public static final IPeppolEndUserIDMapping MAPPING_BE_VAT_TO_BE_EN = PeppolEndUserIDMapping.createValueWithoutPrefix (EPredefinedParticipantIdentifierScheme.BE_VAT.getISO6523Code (),
                                                                                                                         EPredefinedParticipantIdentifierScheme.BE_EN.getISO6523Code (),
                                                                                                                         "BE");
  /**
   * Germany: map the German VAT numbers (<code>9930</code>) onto the German Electronic Business
   * Address numbers (<code>0246</code>) without modifying the identifier value.
   */
  public static final IPeppolEndUserIDMapping MAPPING_DE_VAT_TO_DE_GEBA = PeppolEndUserIDMapping.createValueUnchanged (EPredefinedParticipantIdentifierScheme.DE_VAT.getISO6523Code (),
                                                                                                                       EPredefinedParticipantIdentifierScheme.DE_GEBA.getISO6523Code ());
  /**
   * Finland: map the removed OVT identifiers (<code>0037</code>) onto the OVT codes
   * (<code>0216</code>). Both schemes contain the same OVT code, but in scheme <code>0037</code>
   * the fixed prefix <code>0037</code> as well as a hyphen inside the Business ID are optional (see
   * the code list validation rule <code>(0037)?[0-9]{7}-?[0-9][0-9A-Z]{0,5}</code> and the example
   * values <code>0037:00371234567800001</code> and <code>0037:1234567800001</code>), whereas an
   * identifier of scheme <code>0216</code> always starts with <code>0037</code> and contains digits
   * and letters only. Therefore the hyphen is removed and a missing prefix is added. Values that do
   * not result in an OVT code of 12 to 17 characters are not mapped.
   */
  public static final IPeppolEndUserIDMapping MAPPING_FI_OVT_TO_FI_OVT2 = new PeppolEndUserIDMapping ("0037",
                                                                                                      EPredefinedParticipantIdentifierScheme.FI_OVT2.getISO6523Code (),
                                                                                                      FI::getFinnishOVTCode);

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolEndUserHelper.class);

  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static final ICommonsList <IPeppolEndUserIDMapping> MAPPINGS = new CommonsArrayList <> ();
  @GuardedBy ("RW_LOCK")
  private static IParticipantIdentifierFactory s_aIdentifierFactory = PeppolIdentifierFactory.INSTANCE;

  static
  {
    setToDefaultMappings ();
  }

  @PresentForCodeCoverage
  private static final PeppolEndUserHelper INSTANCE = new PeppolEndUserHelper ();

  private PeppolEndUserHelper ()
  {}

  /**
   * @return A list of all default mappings in the default order. Never <code>null</code>.
   * @see #setToDefaultMappings()
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <IPeppolEndUserIDMapping> getAllDefaultMappings ()
  {
    return new CommonsArrayList <> (MAPPING_BE_VAT_TO_BE_EN, MAPPING_DE_VAT_TO_DE_GEBA, MAPPING_FI_OVT_TO_FI_OVT2);
  }

  /**
   * Remove all existing mappings and register the default mappings only. This is the state after
   * class initialization.
   *
   * @see #getAllDefaultMappings()
   */
  public static void setToDefaultMappings ()
  {
    final ICommonsList <IPeppolEndUserIDMapping> aDefaultMappings = getAllDefaultMappings ();
    RW_LOCK.writeLocked (() -> MAPPINGS.setAll (aDefaultMappings));
  }

  /**
   * @return A copy of all contained mappings in the order they are evaluated. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <IPeppolEndUserIDMapping> getAllMappings ()
  {
    return RW_LOCK.readLockedGet (MAPPINGS::getClone);
  }

  /**
   * Add a new mapping at the end of the list of existing mappings.
   *
   * @param aMapping
   *        The mapping to be added. May not be <code>null</code>.
   */
  public static void registerMapping (@NonNull final IPeppolEndUserIDMapping aMapping)
  {
    ValueEnforcer.notNull (aMapping, "Mapping");
    RW_LOCK.writeLocked (() -> MAPPINGS.add (aMapping));
  }

  /**
   * Remove the provided mapping. As {@link IPeppolEndUserIDMapping} implementations usually don't
   * implement <code>equals</code>, this only works with the exact same object that was registered
   * before - like {@link #MAPPING_BE_VAT_TO_BE_EN}.
   *
   * @param aMapping
   *        The mapping to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if the mapping was removed, {@link EChange#UNCHANGED}
   *         otherwise.
   */
  @NonNull
  public static EChange unregisterMapping (@Nullable final IPeppolEndUserIDMapping aMapping)
  {
    if (aMapping == null)
      return EChange.UNCHANGED;

    return RW_LOCK.writeLockedGet (() -> MAPPINGS.removeObject (aMapping));
  }

  /**
   * Remove all mappings with the provided source ISO6523 code. This is the preferred way to replace
   * one of the default mappings with a customized one.
   *
   * @param sSourceISO6523Code
   *        The source ISO6523 code to search. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if at least one mapping was removed, {@link EChange#UNCHANGED}
   *         otherwise.
   */
  @NonNull
  public static EChange unregisterAllMappingsOfSourceISO6523Code (@Nullable final String sSourceISO6523Code)
  {
    if (StringHelper.isEmpty (sSourceISO6523Code))
      return EChange.UNCHANGED;

    return RW_LOCK.writeLockedGet (() -> EChange.valueOf (MAPPINGS.removeIf (x -> x.getSourceISO6523Code ()
                                                                                   .equals (sSourceISO6523Code))));
  }

  /**
   * Remove all contained mappings. Afterwards the participant identifiers are only unified but no
   * longer mapped.
   *
   * @return {@link EChange#CHANGED} if at least one mapping was removed, {@link EChange#UNCHANGED}
   *         otherwise.
   */
  @NonNull
  public static EChange removeAllMappings ()
  {
    return RW_LOCK.writeLockedGet (MAPPINGS::removeAll);
  }

  /**
   * @return The identifier factory used to unify and to validate the participant identifiers. Never
   *         <code>null</code>. By default this is {@link PeppolIdentifierFactory#INSTANCE}.
   */
  @NonNull
  public static IParticipantIdentifierFactory getIdentifierFactory ()
  {
    return RW_LOCK.readLockedGet (() -> s_aIdentifierFactory);
  }

  /**
   * Set the identifier factory to be used to unify and to validate the participant identifiers.
   *
   * @param aIdentifierFactory
   *        The identifier factory to be used. May not be <code>null</code>.
   */
  public static void setIdentifierFactory (@NonNull final IParticipantIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
    RW_LOCK.writeLocked (() -> s_aIdentifierFactory = aIdentifierFactory);
  }

  /**
   * Get the unique End User ID of the provided participant identifier parts. The result is the URI
   * encoded representation of the unified participant identifier - e.g.
   * <code>iso6523-actorid-upis::0208:0123456789</code>.
   *
   * @param sScheme
   *        The participant identifier scheme to use. May be <code>null</code>.
   * @param sValue
   *        The participant identifier value to use. May be <code>null</code>.
   * @return <code>null</code> if either scheme or value are empty. If the provided identifier is
   *         not valid according to the identifier factory in use, it is URI encoded as-is.
   */
  @Nullable
  public static String getEffectiveEndUserID (@Nullable final String sScheme, @Nullable final String sValue)
  {
    if (StringHelper.isEmpty (sScheme) || StringHelper.isEmpty (sValue))
      return null;

    final IParticipantIdentifierFactory aIF = getIdentifierFactory ();

    // This unifies the identifier value (e.g. lower casing)
    final IParticipantIdentifier aPID = aIF.createParticipantIdentifier (sScheme, sValue);
    if (aPID == null)
    {
      // Not a valid participant identifier - use it as it is
      LOGGER.warn ("The participant identifier '" +
                   CIdentifier.getURIEncoded (sScheme, sValue) +
                   "' is not valid - using it as the End User ID unmodified");
      return CIdentifier.getURIEncoded (sScheme, sValue);
    }

    // Mappings are only defined for the default participant identifier scheme, because only there
    // the ISO6523 code is known
    if (aPID.hasScheme (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME))
    {
      final List <String> aParts = StringHelper.getExploded (':', aPID.getValue (), 2);
      if (aParts.size () == 2)
      {
        final String sISO6523Code = aParts.get (0);
        final String sLocalValue = aParts.get (1);

        // The first applicable mapping wins
        for (final IPeppolEndUserIDMapping aMapping : getAllMappings ())
          if (aMapping.getSourceISO6523Code ().equals (sISO6523Code))
          {
            final String sMappedValue = aMapping.getMappedValue (sLocalValue);
            if (StringHelper.isNotEmpty (sMappedValue))
            {
              final IParticipantIdentifier aMappedPID = aIF.createParticipantIdentifier (aPID.getScheme (),
                                                                                         sMappedValue);
              if (aMappedPID != null)
              {
                if (LOGGER.isDebugEnabled ())
                  LOGGER.debug ("Mapped the End User participant identifier '" +
                                aPID.getURIEncoded () +
                                "' to '" +
                                aMappedPID.getURIEncoded () +
                                "'");
                return aMappedPID.getURIEncoded ();
              }

              LOGGER.warn ("The End User ID mapping of the participant identifier '" +
                           aPID.getURIEncoded () +
                           "' resulted in the invalid identifier value '" +
                           sMappedValue +
                           "' - ignoring that mapping");
            }
            // else: this mapping is not applicable for that value - try the next one
          }
      }
    }

    return aPID.getURIEncoded ();
  }

  /**
   * Get the unique End User ID of the provided participant identifier. The result is the URI
   * encoded representation of the unified participant identifier - e.g.
   * <code>iso6523-actorid-upis::0208:0123456789</code>.
   *
   * @param aParticipantID
   *        The participant identifier to be used. Usually that is the C1 participant identifier for
   *        sending and the C4 participant identifier for receiving. May be <code>null</code>.
   * @return <code>null</code> if the provided participant identifier is <code>null</code>. If the
   *         provided identifier is not valid according to the identifier factory in use, it is URI
   *         encoded as-is.
   * @see #getEffectiveEndUserID(String, String)
   */
  @Nullable
  public static String getEffectiveEndUserID (@Nullable final IParticipantIdentifier aParticipantID)
  {
    return aParticipantID == null ? null
                                  : getEffectiveEndUserID (aParticipantID.getScheme (), aParticipantID.getValue ());
  }
}
