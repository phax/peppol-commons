/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
package com.helger.peppol.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.commons.charset.CCharset;
import com.helger.commons.codec.URLCodec;
import com.helger.commons.messagedigest.EMessageDigestAlgorithm;
import com.helger.commons.messagedigest.MessageDigestGeneratorHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IReadonlyParticipantIdentifier;
import com.helger.peppol.identifier.IdentifierUtils;
import com.helger.peppol.sml.ISMLInfo;

/**
 * Utility methods for assembling URLs and URL elements required for BusDox.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class BusdoxURLUtils
{
  public static final Charset URL_CHARSET = CCharset.CHARSET_UTF_8_OBJ;
  public static final Locale URL_LOCALE = Locale.US;

  @SuppressWarnings ("unused")
  @PresentForCodeCoverage
  private static final BusdoxURLUtils s_aInstance = new BusdoxURLUtils ();

  private BusdoxURLUtils ()
  {}

  /**
   * Escape the passed URL to use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentEncodedURL (@Nullable final String sURL)
  {
    if (sURL != null)
      return new URLCodec ().encodeText (sURL);
    return null;
  }

  /**
   * Unescape the passed URL to NOT use the percentage maskings.
   *
   * @param sURL
   *        The input URL or URL part. May be <code>null</code>.
   * @return <code>null</code> if the input string was <code>null</code>.
   */
  @Nullable
  public static String createPercentDecodedURL (@Nullable final String sURL)
  {
    if (sURL != null)
      return new URLCodec ().decodeText (sURL);
    return null;
  }

  /**
   * Get the MD5-hash-string-representation of the passed value using the
   * {@link #URL_CHARSET} encoding. Each hash byte is represented as 2
   * characters in the range [0-9a-f]. Note: the hash value creation is done
   * case sensitive! The caller needs to ensure that the value to hash is lower
   * case!
   *
   * @param sValueToHash
   *        The value to be hashed. May not be <code>null</code>.
   * @return The non-<code>null</code> String containing the hash value.
   */
  @Nonnull
  public static String getHashValueStringRepresentation (@Nonnull final String sValueToHash)
  {
    // Create the MD5 hash
    final byte [] aDigest = MessageDigestGeneratorHelper.getDigest (sValueToHash,
                                                                    URL_CHARSET,
                                                                    EMessageDigestAlgorithm.MD5);
    // Convert to hex-encoded string
    return MessageDigestGeneratorHelper.getHexValueFromDigest (aDigest);
  }

  /**
   * Get DNS record from ParticipantIdentifier. Example PI:
   * <code>iso6523-actorid-upis::0010:1234</code> would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code>
   * . This method ensures that the hash value is created from the lower case
   * value of the identifier. Lower casing is done with the {@link #URL_LOCALE}
   * locale. The result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param aSML
   *        The SML information object to be used. May not be <code>null</code>.
   * @return DNS record
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @Nonnull
  public static String getDNSNameOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                                @Nonnull final ISMLInfo aSML)
  {
    return getDNSNameOfParticipant (aParticipantIdentifier, aSML.getDNSZone ());
  }

  /**
   * Get DNS record from ParticipantIdentifier. Example PI:
   * <code>iso6523-actorid-upis::0010:1234</code> would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code>
   * . This method ensures that the hash value is created from the lower case
   * value of the identifier. Lower casing is done with the {@link #URL_LOCALE}
   * locale. The result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param sSMLZoneName
   *        e.g. "sml.peppolcentral.org.". May be empty. If it is not empty, it
   *        must end with a dot!
   * @return DNS record. It does not contain any prefix like http:// or any path
   *         suffix. It is the plain DNS host name. Since version 1.1.4 this
   *         method returns the DNS name without the trailing dot!
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @Nonnull
  public static String getDNSNameOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                                @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notEmpty (aParticipantIdentifier.getScheme (), "ParticipantIdentifier scheme");
    ValueEnforcer.notEmpty (aParticipantIdentifier.getValue (), "ParticipantIdentifier value");
    if (StringHelper.hasText (sSMLZoneName) && !StringHelper.endsWith (sSMLZoneName, '.'))
      throw new IllegalArgumentException ("if an SML zone name is specified, it must end with a dot (.). Value is: " +
                                          sSMLZoneName);

    // Check identifier scheme (must be lowercase for the URL later on!)
    final String sScheme = aParticipantIdentifier.getScheme ().toLowerCase (URL_LOCALE);
    if (!IdentifierUtils.isValidParticipantIdentifierScheme (sScheme))
      throw new IllegalArgumentException ("Invalid participant identifier scheme '" + sScheme + "'");

    // Get the identifier value
    final String sValue = aParticipantIdentifier.getValue ();
    final StringBuilder ret = new StringBuilder ();
    if ("*".equals (sValue))
    {
      // Wild card registration
      ret.append ("*.");
    }
    else
    {
      // Important: create hash from lowercase string!
      // Here the "B-0011223344..." string is assembled!
      ret.append (CIdentifier.DNS_HASHED_IDENTIFIER_PREFIX)
         .append (getHashValueStringRepresentation (sValue.toLowerCase (URL_LOCALE)))
         .append ('.');
    }

    // append the identifier scheme
    ret.append (sScheme).append ('.');

    // append the SML DNS zone name (if available)
    if (StringHelper.hasText (sSMLZoneName))
    {
      // If it is present, it ends with a dot
      ret.append (sSMLZoneName);
    }

    // in some cases it gives a problem later when trying to retrieve the
    // participant's metadata ex:
    // http://B-51538b9890f1999ca08302c65f544719.iso6523-actorid-upis.sml.peppolcentral.org./iso6523-actorid-upis%3A%3A9917%3A550403315099
    // That's wehy we cut of the dot here
    ret.deleteCharAt (ret.length () - 1);

    // We're fine and done
    return ret.toString ();
  }

  @Nonnull
  public static URI getSMPURIOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                            @Nonnull final ISMLInfo aSMLInfo)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");

    return getSMPURIOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  @Nonnull
  public static URI getSMPURIOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                            @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    final String sURIString = "http://" + getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName);

    try
    {
      return new URI (sURIString);
    }
    catch (final URISyntaxException ex)
    {
      throw new IllegalArgumentException ("Error building SMP URI from string '" + sURIString + "'", ex);
    }
  }

  @Nonnull
  public static URL getSMPURLOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                            @Nonnull final ISMLInfo aSMLInfo)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");

    return getSMPURLOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  @Nonnull
  public static URL getSMPURLOfParticipant (@Nonnull final IReadonlyParticipantIdentifier aParticipantIdentifier,
                                            @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    final URI aURI = getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName);
    try
    {
      return aURI.toURL ();
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalArgumentException ("Error building SMP URL from URI: " + aURI, ex);
    }
  }
}
