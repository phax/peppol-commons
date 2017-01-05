/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;

/**
 * This class contains several identifier related utility methods that works
 * with ALL kind of identifiers.<br>
 * Deprecated as per 5.1.6 because now the case sensitivity of identifiers is
 * directly handled in the identifier factory.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@ThreadSafe
@Deprecated
public final class IdentifierHelper
{
  @PresentForCodeCoverage
  private static final IdentifierHelper s_aInstance = new IdentifierHelper ();

  private IdentifierHelper ()
  {}

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areParticipantIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                             @Nullable final String sIdentifierValue2)
  {
    // equal case insensitive!
    return EqualsHelper.equalsIgnoreCase (sIdentifierValue1, sIdentifierValue2);
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areDocumentTypeIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                              @Nullable final String sIdentifierValue2)
  {
    // Case sensitive!
    return EqualsHelper.equals (sIdentifierValue1, sIdentifierValue2);
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param sIdentifierValue1
   *        First identifier value to compare. May be <code>null</code>.
   * @param sIdentifierValue2
   *        Second identifier value to compare. May be <code>null</code>.
   * @return <code>true</code> if the identifier values are equal,
   *         <code>false</code> otherwise. If both are <code>null</code> they
   *         are considered equal.
   */
  public static boolean areProcessIdentifierValuesEqual (@Nullable final String sIdentifierValue1,
                                                         @Nullable final String sIdentifierValue2)
  {
    // Case sensitive!
    return EqualsHelper.equals (sIdentifierValue1, sIdentifierValue2);
  }

  private static int _schemeCompare (@Nullable final String sScheme1,
                                     @Nullable final String sScheme2,
                                     final boolean bCaseSensitive)
  {
    final String sRealScheme1 = StringHelper.getNotNull (sScheme1);
    final String sRealScheme2 = StringHelper.getNotNull (sScheme2);
    return bCaseSensitive ? sRealScheme1.compareTo (sRealScheme2) : sRealScheme1.compareToIgnoreCase (sRealScheme2);
  }

  private static boolean _schemeEquals (@Nullable final String sScheme1,
                                        @Nullable final String sScheme2,
                                        final boolean bCaseSensitive)
  {
    final String sRealScheme1 = StringHelper.getNotNull (sScheme1);
    final String sRealScheme2 = StringHelper.getNotNull (sScheme2);
    return bCaseSensitive ? sRealScheme1.equals (sRealScheme2) : sRealScheme1.equalsIgnoreCase (sRealScheme2);
  }

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areParticipantIdentifiersEqual (@Nonnull final IParticipantIdentifier aIdentifier1,
                                                        @Nonnull final IParticipantIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ParticipantIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ParticipantIdentifier2");

    // Identifiers are equal, if both scheme and value match case insensitive!
    return _schemeEquals (aIdentifier1.getScheme (), aIdentifier2.getScheme (), false) &&
           areParticipantIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areDocumentTypeIdentifiersEqual (@Nonnull final IDocumentTypeIdentifier aIdentifier1,
                                                         @Nonnull final IDocumentTypeIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "DocumentTypeIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "DocumentTypeIdentifier2");

    // Identifiers are equal, if both scheme and value match case sensitive!
    return _schemeEquals (aIdentifier1.getScheme (), aIdentifier2.getScheme (), true) &&
           areDocumentTypeIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return <code>true</code> if the identifiers are equal, <code>false</code>
   *         otherwise.
   */
  public static boolean areProcessIdentifiersEqual (@Nonnull final IProcessIdentifier aIdentifier1,
                                                    @Nonnull final IProcessIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ProcessIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ProcessIdentifier2");

    // Identifiers are equal, if both scheme and value match case sensitive!
    return _schemeEquals (aIdentifier1.getScheme (), aIdentifier2.getScheme (), true) &&
           areProcessIdentifierValuesEqual (aIdentifier1.getValue (), aIdentifier2.getValue ());
  }

  /**
   * According to the specification, two participant identifiers are equal if
   * their parts are equal case insensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareParticipantIdentifiers (@Nonnull final IParticipantIdentifier aIdentifier1,
                                                   @Nonnull final IParticipantIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ParticipantIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ParticipantIdentifier2");

    // Compare case insensitive
    int ret = _schemeCompare (aIdentifier1.getScheme (), aIdentifier2.getScheme (), false);
    if (ret == 0)
      ret = CompareHelper.compareIgnoreCase (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }

  /**
   * According to the specification, two document identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareDocumentTypeIdentifiers (@Nonnull final IDocumentTypeIdentifier aIdentifier1,
                                                    @Nonnull final IDocumentTypeIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "DocumentTypeIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "DocumentTypeIdentifier2");

    // Compare case sensitive
    int ret = _schemeCompare (aIdentifier1.getScheme (), aIdentifier2.getScheme (), true);
    if (ret == 0)
      ret = CompareHelper.compare (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }

  /**
   * According to the specification, two process identifiers are equal if their
   * parts are equal case sensitive.
   *
   * @param aIdentifier1
   *        First identifier to compare. May not be null.
   * @param aIdentifier2
   *        Second identifier to compare. May not be null.
   * @return 0 if the identifiers are equal, -1 or +1 otherwise.
   */
  public static int compareProcessIdentifiers (@Nonnull final IProcessIdentifier aIdentifier1,
                                               @Nonnull final IProcessIdentifier aIdentifier2)
  {
    ValueEnforcer.notNull (aIdentifier1, "ProcessIdentifier1");
    ValueEnforcer.notNull (aIdentifier2, "ProcessIdentifier2");

    // Compare case sensitive
    int ret = _schemeCompare (aIdentifier1.getScheme (), aIdentifier2.getScheme (), true);
    if (ret == 0)
      ret = CompareHelper.compare (aIdentifier1.getValue (), aIdentifier2.getValue ());
    return ret;
  }
}
