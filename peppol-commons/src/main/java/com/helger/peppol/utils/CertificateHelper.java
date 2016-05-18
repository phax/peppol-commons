/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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

import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.base64.Base64;
import com.helger.commons.charset.CCharset;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.io.stream.StringInputStream;
import com.helger.commons.string.StringHelper;

/**
 * Some utility methods handling certificates.
 *
 * @author Philip Helger
 */
@Immutable
public final class CertificateHelper
{
  public static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
  public static final String END_CERTIFICATE = "-----END CERTIFICATE-----";
  public static final String BEGIN_CERTIFICATE_INVALID = "-----BEGINCERTIFICATE-----";
  public static final String END_CERTIFICATE_INVALID = "-----ENDCERTIFICATE-----";
  /** Character set used for String-Certificate conversion */
  private static final Charset CERT_CHARSET = CCharset.CHARSET_ISO_8859_1_OBJ;

  private static final Logger s_aLogger = LoggerFactory.getLogger (CertificateHelper.class);

  @SuppressWarnings ("unused")
  @PresentForCodeCoverage
  private static final CertificateHelper s_aInstance = new CertificateHelper ();

  private CertificateHelper ()
  {}

  /**
   * Handle certain misconfiguration issues. E.g. for 9906:testconsip on
   *
   * <pre>
   * http://b-c073e04afb234f70e74d3444ba3f8eaa.iso6523-actorid-upis.acc.edelivery.tech.ec.europa.eu/iso6523-actorid-upis%3A%3A9906%3Atestconsip/services/busdox-docid-qns%3A%3Aurn%3Aoasis%3Anames%3Aspecification%3Aubl%3Aschema%3Axsd%3AOrder-2%3A%3AOrder%23%23urn%3Awww.cenbii.eu%3Atransaction%3Abiitrns001%3Aver2.0%3Aextended%3Aurn%3Awww.peppol.eu%3Abis%3Apeppol3a%3Aver2.0%3A%3A2.1
   * </pre>
   *
   * @param sCertString
   *        Original certificate string
   */
  @Nonnull
  private static String _cutUnnecessaryLeadingAndTrailingParts (@Nonnull final String sCertString)
  {
    String ret = sCertString;
    ret = StringHelper.trimStart (ret, BEGIN_CERTIFICATE_INVALID);
    ret = StringHelper.trimEnd (ret, END_CERTIFICATE_INVALID);
    return ret.trim ();
  }

  @Nonnull
  public static String ensureBeginAndEndArePresent (@Nonnull final String sCertString)
  {
    String sRealCertString = sCertString;
    // Check without newline in case there are blanks between the string the
    // certificate
    if (!sRealCertString.startsWith (BEGIN_CERTIFICATE))
      sRealCertString = BEGIN_CERTIFICATE + "\n" + sRealCertString;
    if (!sRealCertString.trim ().endsWith (END_CERTIFICATE))
      sRealCertString += "\n" + END_CERTIFICATE;
    return sRealCertString;
  }

  /**
   * Convert the passed byte array to an X.509 certificate object.
   *
   * @param aCertBytes
   *        The original certificate bytes. May be <code>null</code> or empty.
   * @return <code>null</code> if the passed byte array is <code>null</code> or
   *         empty
   * @throws CertificateException
   *         In case the passed string cannot be converted to an X.509
   *         certificate.
   */
  @Nullable
  public static X509Certificate convertByteArrayToCertficate (@Nullable final byte [] aCertBytes) throws CertificateException
  {
    if (ArrayHelper.isEmpty (aCertBytes))
      return null;

    // Certificate is always ISO-8859-1 encoded
    return convertStringToCertficate (new String (aCertBytes, CERT_CHARSET));
  }

  @Nonnull
  private static X509Certificate _str2cert (@Nonnull final String sCertString,
                                            @Nonnull final CertificateFactory aCertificateFactory) throws CertificateException
  {
    String sRealCertString = sCertString;
    sRealCertString = _cutUnnecessaryLeadingAndTrailingParts (sRealCertString);
    sRealCertString = getRFC1421CompliantString (sRealCertString);
    sRealCertString = ensureBeginAndEndArePresent (sRealCertString);

    return (X509Certificate) aCertificateFactory.generateCertificate (new StringInputStream (sRealCertString,
                                                                                             CERT_CHARSET));
  }

  /**
   * Convert the passed String to an X.509 certificate.
   *
   * @param sCertString
   *        The original text string. May be <code>null</code> or empty. The
   *        String must be ISO-8859-1 encoded for the binary certificate to be
   *        read!
   * @return <code>null</code> if the passed string is <code>null</code> or
   *         empty
   * @throws CertificateException
   *         In case the passed string cannot be converted to an X.509
   *         certificate.
   */
  @Nullable
  public static X509Certificate convertStringToCertficate (@Nullable final String sCertString) throws CertificateException
  {
    if (StringHelper.hasNoText (sCertString))
    {
      // No string -> no certificate
      return null;
    }

    final CertificateFactory aCertificateFactory = CertificateFactory.getInstance ("X.509");

    // Convert certificate string to an object
    try
    {
      return _str2cert (sCertString, aCertificateFactory);
    }
    catch (final CertificateException ex)
    {
      // In some weird configurations, the result string is a hex encoded
      // certificate instead of the string
      // -> Try to work around it
      s_aLogger.warn ("Failed to decode provided X.509 certificate string: " + sCertString);

      String sHexDecodedString;
      try
      {
        sHexDecodedString = new String (StringHelper.getHexDecoded (sCertString), CERT_CHARSET);
      }
      catch (final IllegalArgumentException ex2)
      {
        // Can happen, when the source string has an odd length (like 3 or 117).
        // In this case the original exception is rethrown
        throw ex;
      }

      return _str2cert (sHexDecodedString, aCertificateFactory);
    }
  }

  /**
   * Remove any eventually preceding {@value #BEGIN_CERTIFICATE} and succeeding
   * {@value #END_CERTIFICATE} values from the passed certificate string.
   * Additionally all whitespaces of the string are removed.
   *
   * @param sCertificate
   *        The source certificate string. May be <code>null</code>.
   * @return <code>null</code> if the input string is <code>null</code> or
   *         empty, the stripped down string otherwise.
   */
  @Nullable
  public static String getWithoutPEMHeader (@Nullable final String sCertificate)
  {
    if (StringHelper.hasNoText (sCertificate))
      return null;

    // Remove special begin and end stuff
    String sRealCertificate = sCertificate;
    sRealCertificate = StringHelper.trimStart (sRealCertificate, BEGIN_CERTIFICATE);
    sRealCertificate = StringHelper.trimEnd (sRealCertificate, END_CERTIFICATE);

    // Remove all existing whitespace characters
    return StringHelper.getWithoutAnySpaces (sRealCertificate);
  }

  /**
   * The certificate string needs to be emitted in portions of 64 characters. If
   * characters are left, than &lt;CR&gt;&lt;LF&gt; ("\r\n") must be added to
   * the string so that the next characters start on a new line. After the last
   * part, no &lt;CR&gt;&lt;LF&gt; is needed. Respective RFC parts are 1421
   * 4.3.2.2 and 4.3.2.4. Additionally {@link #BEGIN_CERTIFICATE} is prepended
   * and {@link #END_CERTIFICATE} is appended.
   *
   * @param sCertificate
   *        Original certificate string as stored in the DB
   * @return The RFC 1421 compliant string
   */
  @Nullable
  public static String getRFC1421CompliantString (@Nullable final String sCertificate)
  {
    // true for backwards compatibility
    return getRFC1421CompliantString (sCertificate, true);
  }

  /**
   * The certificate string needs to be emitted in portions of 64 characters. If
   * characters are left, than &lt;CR&gt;&lt;LF&gt; ("\r\n") must be added to
   * the string so that the next characters start on a new line. After the last
   * part, no &lt;CR&gt;&lt;LF&gt; is needed. Respective RFC parts are 1421
   * 4.3.2.2 and 4.3.2.4
   *
   * @param sCertificate
   *        Original certificate string as stored in the DB
   * @param bIncludePEMHeader
   *        <code>true</code> to include {@link #BEGIN_CERTIFICATE} and
   *        {@link #END_CERTIFICATE}
   * @return The RFC 1421 compliant string
   */
  @Nullable
  public static String getRFC1421CompliantString (@Nullable final String sCertificate, final boolean bIncludePEMHeader)
  {
    // Remove special begin and end stuff
    String sPlainString = CertificateHelper.getWithoutPEMHeader (sCertificate);
    if (StringHelper.hasNoText (sPlainString))
      return null;

    // Start building the result
    final int nMaxLineLength = 64;
    final String sCRLF = "\r\n";
    // Start with the prefix
    final StringBuilder aSB = new StringBuilder ();
    if (bIncludePEMHeader)
      aSB.append (CertificateHelper.BEGIN_CERTIFICATE).append ('\n');
    while (sPlainString.length () > nMaxLineLength)
    {
      // Append line + CRLF
      aSB.append (sPlainString, 0, nMaxLineLength).append (sCRLF);

      // Remove the start of the string
      sPlainString = sPlainString.substring (nMaxLineLength);
    }

    // Append the rest
    aSB.append (sPlainString);

    // Add trailer
    if (bIncludePEMHeader)
      aSB.append ('\n').append (CertificateHelper.END_CERTIFICATE);

    return aSB.toString ();
  }

  /**
   * Convert the passed X.509 certificate string to a byte array.
   *
   * @param sCertificate
   *        The original certificate string. May be <code>null</code> or empty.
   * @return <code>null</code> if the passed string is <code>null</code> or
   *         empty or an invalid Base64 string
   */
  @Nullable
  public static byte [] convertCertificateStringToByteArray (@Nullable final String sCertificate)
  {
    // Remove prefix/suffix
    final String sPlainCert = getWithoutPEMHeader (sCertificate);
    if (StringHelper.hasNoText (sPlainCert))
      return null;

    // The remaining string is supposed to be Base64 encoded -> decode
    return Base64.safeDecode (sPlainCert);
  }
}
