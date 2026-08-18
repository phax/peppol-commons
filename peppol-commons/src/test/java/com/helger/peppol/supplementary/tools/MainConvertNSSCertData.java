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
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.io.nonblocking.NonBlockingBufferedReader;
import com.helger.base.io.nonblocking.NonBlockingByteArrayInputStream;
import com.helger.base.io.nonblocking.NonBlockingByteArrayOutputStream;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.CommonsTreeMap;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSortedMap;
import com.helger.datetime.helper.PDTFactory;
import com.helger.io.file.SimpleFileIO;
import com.helger.security.keystore.EKeyStoreType;

/**
 * Reads the Mozilla NSS <code>certdata.txt</code> file and extracts trusted root CA certificates
 * into a Java {@link KeyStore}. This is similar to what
 * <a href="https://github.com/agl/extract-nss-root-certs">extract-nss-root-certs</a> does for
 * Go/PEM.
 * <p>
 * The NSS certdata.txt format defines PKCS#11 objects. This reader looks for
 * <code>CKO_CERTIFICATE</code> objects (to extract the DER-encoded certificate from
 * <code>CKA_VALUE</code>) and <code>CKO_NSS_TRUST</code> objects (to check
 * <code>CKA_TRUST_SERVER_AUTH</code>). Only certificates whose corresponding trust object has
 * <code>CKT_NSS_TRUSTED_DELEGATOR</code> for server authentication are included by default.
 * </p>
 * <p>
 * The certdata.txt file can be obtained from:<br>
 * <code>https://hg-edge.mozilla.org/projects/nss/raw-file/tip/lib/ckfw/builtins/certdata.txt</code>
 * </p>
 * <p>
 * The certdata.txt file itself contains neither a version nor a publication date. The version of
 * the trust list is therefore taken from the <code>NSS_BUILTINS_LIBRARY_VERSION</code> macro of the
 * <code>nssckbi.h</code> file that resides in the same directory.
 * </p>
 * <p>
 * See <a href="https://github.com/phax/peppol-commons/issues/68">Issue #68</a>
 * </p>
 *
 * @author Philip Helger
 */
public final class MainConvertNSSCertData
{
  /** The URL to download the latest NSS certdata.txt */
  public static final String CERTDATA_URL = "https://hg-edge.mozilla.org/projects/nss/raw-file/tip/lib/ckfw/builtins/certdata.txt";

  /** The URL to download the latest NSS nssckbi.h that contains the trust list version */
  public static final String NSSCKBI_URL = "https://hg-edge.mozilla.org/projects/nss/raw-file/tip/lib/ckfw/builtins/nssckbi.h";

  /** Trust value indicating a trusted delegator (CA) */
  public static final String TRUST_TRUSTED_DELEGATOR = "CKT_NSS_TRUSTED_DELEGATOR";

  /** Maximum age of a cached file before re-downloading */
  private static final Duration CACHE_MAX_AGE = Duration.ofHours (24);

  /** Local cache file for the downloaded certdata.txt */
  private static final Path CACHE_FILE_CERTDATA = Path.of (System.getProperty ("java.io.tmpdir"),
                                                           "nss-certdata-cache.txt");

  /** Local cache file for the downloaded nssckbi.h */
  private static final Path CACHE_FILE_NSSCKBI = Path.of (System.getProperty ("java.io.tmpdir"), "nss-nssckbi-cache.h");

  /** The regular expression to extract the trust list version from nssckbi.h */
  private static final Pattern PATTERN_BUILTINS_VERSION = Pattern.compile ("#define\\s+NSS_BUILTINS_LIBRARY_VERSION\\s+\"([^\"]+)\"");

  private static final Logger LOGGER = LoggerFactory.getLogger (MainConvertNSSCertData.class);

  private MainConvertNSSCertData ()
  {}

  /**
   * Parse a MULTILINE_OCTAL data block. Each line contains backslash-escaped octal values (e.g.
   * <code>\060\127\061\013</code>). The block is terminated by a line containing only
   * <code>END</code>.
   *
   * @param aReader
   *        the reader positioned after the MULTILINE_OCTAL declaration
   * @return the decoded byte array
   * @throws IOException
   *         on read error
   */
  @NonNull
  private static byte [] _readMultilineOctal (@NonNull final NonBlockingBufferedReader aReader) throws IOException
  {
    try (final NonBlockingByteArrayOutputStream aBAOS = new NonBlockingByteArrayOutputStream (4096))
    {
      String sLine;
      while ((sLine = aReader.readLine ()) != null)
      {
        sLine = sLine.trim ();
        if ("END".equals (sLine))
          break;

        int nIdx = 0;
        while (nIdx < sLine.length ())
        {
          if (sLine.charAt (nIdx) == '\\' && nIdx + 3 < sLine.length ())
          {
            final String sOctal = sLine.substring (nIdx + 1, nIdx + 4);
            aBAOS.write (Integer.parseInt (sOctal, 8));
            nIdx += 4;
          }
          else
          {
            // Skip unexpected characters
            nIdx++;
          }
        }
      }
      return aBAOS.toByteArray ();
    }
  }

  /**
   * Holder for a parsed certificate object from certdata.txt.
   */
  private static final record CertEntry (String label, byte [] derBytes)
  {}

  /**
   * Holder for a parsed trust object from certdata.txt.
   */
  private static final record TrustEntry (String label, String serverAuthTrust)
  {}

  /**
   * Emit a completed object (certificate or trust) into the respective collection.
   */
  private static void _emitObject (@Nullable final String sCurClass,
                                   @Nullable final String sCurLabel,
                                   @Nullable final byte [] aCurValue,
                                   @Nullable final String sCurServerAuthTrust,
                                   @NonNull final ICommonsMap <String, CertEntry> aCerts,
                                   @NonNull final ICommonsMap <String, TrustEntry> aTrusts)
  {
    if (sCurClass == null || sCurLabel == null)
      return;

    if ("CKO_CERTIFICATE".equals (sCurClass))
    {
      if (aCurValue != null && aCurValue.length > 0)
        aCerts.put (sCurLabel, new CertEntry (sCurLabel, aCurValue));
      else
        LOGGER.warn ("Certificate '" + sCurLabel + "' has no CKA_VALUE data");
    }
    else
      if ("CKO_NSS_TRUST".equals (sCurClass))
      {
        if (sCurServerAuthTrust != null)
          aTrusts.put (sCurLabel, new TrustEntry (sCurLabel, sCurServerAuthTrust));
      }
  }

  /**
   * Read certificates from a Mozilla NSS certdata.txt input stream.
   *
   * @param aISCertData
   *        the input stream to read from. Not <code>null</code>. Not closed by this method.
   * @param bOnlyServerAuthTrusted
   *        if <code>true</code>, only include certificates whose corresponding trust object has
   *        <code>CKT_NSS_TRUSTED_DELEGATOR</code> for <code>CKA_TRUST_SERVER_AUTH</code>. If
   *        <code>false</code>, all certificates are included.
   * @return a map of label to {@link X509Certificate} in alphabetical alias order. Never
   *         <code>null</code>.
   * @throws IOException
   *         on read error
   */
  @NonNull
  private static ICommonsSortedMap <String, X509Certificate> _readCerts (@NonNull final InputStream aISCertData,
                                                                         final boolean bOnlyServerAuthTrusted) throws IOException
  {
    final ICommonsMap <String, CertEntry> aCerts = new CommonsLinkedHashMap <> ();
    final ICommonsMap <String, TrustEntry> aTrusts = new CommonsLinkedHashMap <> ();

    try (final NonBlockingBufferedReader aReader = new NonBlockingBufferedReader (new InputStreamReader (aISCertData,
                                                                                                         StandardCharsets.UTF_8)))
    {
      String sLine;
      String sCurClass = null;
      String sCurLabel = null;
      byte [] aCurValue = null;
      String sCurServerAuthTrust = null;

      while ((sLine = aReader.readLine ()) != null)
      {
        sLine = sLine.trim ();

        // Skip empty lines and comments
        if (sLine.isEmpty () || sLine.startsWith ("#"))
          continue;

        if ("BEGINDATA".equals (sLine))
          continue;

        // Detect new object
        if (sLine.startsWith ("CKA_CLASS"))
        {
          // Emit previous object
          _emitObject (sCurClass, sCurLabel, aCurValue, sCurServerAuthTrust, aCerts, aTrusts);

          // Reset state
          sCurLabel = null;
          aCurValue = null;
          sCurServerAuthTrust = null;

          if (sLine.contains ("CKO_CERTIFICATE"))
            sCurClass = "CKO_CERTIFICATE";
          else
            if (sLine.contains ("CKO_NSS_TRUST"))
              sCurClass = "CKO_NSS_TRUST";
            else
              sCurClass = null;
          continue;
        }

        // Only parse attributes within recognized objects
        if (sCurClass == null)
          continue;

        if (sLine.startsWith ("CKA_LABEL"))
        {
          // Format: CKA_LABEL UTF8 "Some Label"
          final int nFirstQuote = sLine.indexOf ('"');
          final int nLastQuote = sLine.lastIndexOf ('"');
          if (nFirstQuote >= 0 && nLastQuote > nFirstQuote)
            sCurLabel = sLine.substring (nFirstQuote + 1, nLastQuote);
        }
        else
          if ("CKO_CERTIFICATE".equals (sCurClass) &&
              sLine.startsWith ("CKA_VALUE") &&
              sLine.contains ("MULTILINE_OCTAL"))
          {
            aCurValue = _readMultilineOctal (aReader);
          }
          else
            if ("CKO_NSS_TRUST".equals (sCurClass) && sLine.startsWith ("CKA_TRUST_SERVER_AUTH"))
            {
              // Format: CKA_TRUST_SERVER_AUTH CK_TRUST CKT_NSS_TRUSTED_DELEGATOR
              final String [] aParts = sLine.split ("\\s+");
              if (aParts.length >= 3)
                sCurServerAuthTrust = aParts[aParts.length - 1];
            }
      }

      // Emit last object
      _emitObject (sCurClass, sCurLabel, aCurValue, sCurServerAuthTrust, aCerts, aTrusts);
    }

    // Match certificates with their trust entries
    final CertificateFactory aCertFactory;
    try
    {
      aCertFactory = CertificateFactory.getInstance ("X.509");
    }
    catch (final CertificateException ex)
    {
      throw new IOException ("Failed to create X.509 CertificateFactory", ex);
    }

    final ICommonsSortedMap <String, X509Certificate> aResult = new CommonsTreeMap <> (String::compareToIgnoreCase);
    for (final var aEntry : aCerts.entrySet ())
    {
      final String sLabel = aEntry.getKey ();
      final CertEntry aCert = aEntry.getValue ();

      if (bOnlyServerAuthTrusted)
      {
        final TrustEntry aTrust = aTrusts.get (sLabel);
        if (aTrust == null)
        {
          LOGGER.warn ("No trust object found for certificate '" + sLabel + "' - skipping");
          continue;
        }
        if (!TRUST_TRUSTED_DELEGATOR.equals (aTrust.serverAuthTrust))
        {
          LOGGER.debug ("Certificate '" +
                        sLabel +
                        "' is not trusted for server auth (trust=" +
                        aTrust.serverAuthTrust +
                        "), skipping");
          continue;
        }
      }

      try
      {
        final X509Certificate aX509 = (X509Certificate) aCertFactory.generateCertificate (new NonBlockingByteArrayInputStream (aCert.derBytes));
        aResult.put (sLabel, aX509);
      }
      catch (final CertificateException ex)
      {
        LOGGER.warn ("Failed to parse certificate '" + sLabel + "'", ex);
      }
    }

    LOGGER.info ("Read " +
                 aResult.size () +
                 " certificates from NSS certdata.txt (" +
                 aCerts.size () +
                 " total, " +
                 aResult.size () +
                 " trusted for server auth)");
    return aResult;
  }

  /**
   * Convert a label into a valid KeyStore alias by replacing special characters with underscores
   * and lowercasing.
   *
   * @param sLabel
   *        the certificate label
   * @return a sanitized alias string
   */
  @NonNull
  private static String _labelToAlias (@NonNull final String sLabel)
  {
    return sLabel.toLowerCase (Locale.US).replaceAll ("[^a-z0-9]", "_");
  }

  /**
   * Read the Mozilla NSS certdata.txt from an input stream and create a Java {@link KeyStore}
   * containing all TLS server-trusted root certificates.
   *
   * @param aISCertData
   *        the input stream for certdata.txt. Not <code>null</code>.
   * @param eKeyStoreType
   *        the KeyStore type to create. Not <code>null</code>.
   * @param sPassword
   *        the password to protect the KeyStore. Not <code>null</code>.
   * @return the populated KeyStore, or <code>null</code> on error
   */
  @Nullable
  private static KeyStore _createTrustStore (@NonNull final InputStream aISCertData,
                                             @NonNull final EKeyStoreType eKeyStoreType,
                                             @NonNull final String sPassword)
  {
    try
    {
      final ICommonsSortedMap <String, X509Certificate> aCerts = _readCerts (aISCertData, true);
      if (aCerts.isEmpty ())
      {
        LOGGER.warn ("No certificates found in NSS certdata.txt");
        return null;
      }

      final KeyStore aKeyStore = eKeyStoreType.getKeyStore ();
      aKeyStore.load (null, sPassword.toCharArray ());

      int nDuplicateIndex = 0;
      for (final var aEntry : aCerts.entrySet ())
      {
        String sAlias = _labelToAlias (aEntry.getKey ());
        // Handle potential alias collisions
        if (aKeyStore.containsAlias (sAlias))
        {
          sAlias = sAlias + "_" + (++nDuplicateIndex);
        }
        aKeyStore.setCertificateEntry (sAlias, aEntry.getValue ());
      }

      LOGGER.info ("Created " + eKeyStoreType.getID () + " trust store with " + aCerts.size () + " certificates");
      return aKeyStore;
    }
    catch (final Exception ex)
    {
      LOGGER.error ("Failed to create trust store from NSS certdata.txt", ex);
      return null;
    }
  }

  /**
   * Make sure a locally cached copy of the provided URL exists, downloading it from Mozilla if the
   * cache is missing or older than {@link #CACHE_MAX_AGE}.
   *
   * @param sURL
   *        the URL to download. Not <code>null</code>.
   * @param aCacheFile
   *        the local cache file to use. Not <code>null</code>.
   * @throws IOException
   *         on download or I/O error
   */
  private static void _ensureCached (@NonNull final String sURL, @NonNull final Path aCacheFile) throws IOException
  {
    boolean bNeedDownload = true;
    if (Files.exists (aCacheFile))
    {
      final Instant aLastModified = Files.getLastModifiedTime (aCacheFile).toInstant ();
      final Duration aAge = Duration.between (aLastModified, Instant.now ());
      if (aAge.compareTo (CACHE_MAX_AGE) < 0)
      {
        LOGGER.info ("Using cached file " + aCacheFile + " (age: " + aAge.toHours () + "h)");
        bNeedDownload = false;
      }
      else
      {
        LOGGER.info ("Cached file " + aCacheFile + " is " + aAge.toHours () + "h old - re-downloading");
      }
    }

    if (bNeedDownload)
    {
      LOGGER.info ("Downloading " + sURL);
      try (final InputStream aIS = URI.create (sURL).toURL ().openStream ())
      {
        Files.copy (aIS, aCacheFile, StandardCopyOption.REPLACE_EXISTING);
      }
      LOGGER.info ("Cached content to " + aCacheFile);
    }
  }

  /**
   * Get a locally cached copy of certdata.txt, downloading it from Mozilla if the cache is missing
   * or older than {@link #CACHE_MAX_AGE}.
   *
   * @return an {@link InputStream} for the (possibly cached) certdata.txt
   * @throws IOException
   *         on download or I/O error
   */
  @NonNull
  private static InputStream _getCachedCertData () throws IOException
  {
    _ensureCached (CERTDATA_URL, CACHE_FILE_CERTDATA);
    return new FileInputStream (CACHE_FILE_CERTDATA.toFile ());
  }

  /**
   * Get the version of the NSS trust list from the <code>nssckbi.h</code> file that belongs to the
   * downloaded certdata.txt. The <code>NSS_BUILTINS_LIBRARY_VERSION</code> macro contained therein
   * is increased by Mozilla each time the list of trusted certificates is changed. The
   * certdata.txt file itself contains no version and no publication date.
   *
   * @return the version String (like <code>2.90</code>) or <code>null</code> if it could not be
   *         determined.
   */
  @Nullable
  private static String _getBuiltinsLibraryVersion ()
  {
    try
    {
      _ensureCached (NSSCKBI_URL, CACHE_FILE_NSSCKBI);

      final String sContent = SimpleFileIO.getFileAsString (CACHE_FILE_NSSCKBI.toFile (), StandardCharsets.UTF_8);
      if (sContent != null)
      {
        final Matcher aMatcher = PATTERN_BUILTINS_VERSION.matcher (sContent);
        if (aMatcher.find ())
          return aMatcher.group (1);
      }
      LOGGER.warn ("Failed to find NSS_BUILTINS_LIBRARY_VERSION in " + CACHE_FILE_NSSCKBI);
    }
    catch (final IOException ex)
    {
      LOGGER.warn ("Failed to read NSS trust list version from " + NSSCKBI_URL, ex);
    }
    return null;
  }

  /**
   * Download the NSS certdata.txt from the Mozilla Mercurial repository (using a local file cache)
   * and write a trust store file.
   *
   * @param aOutputFile
   *        the file to write the trust store to. Not <code>null</code>.
   * @param eKeyStoreType
   *        the KeyStore type to write. Not <code>null</code>.
   * @param sPassword
   *        the password to protect the KeyStore. Not <code>null</code>.
   * @throws Exception
   *         on any error
   */
  private static void _downloadAndConvert (@NonNull final File aOutputFile,
                                           @NonNull final EKeyStoreType eKeyStoreType,
                                           @NonNull final String sPassword) throws Exception
  {
    try (final InputStream aISCertData = _getCachedCertData ())
    {
      final KeyStore aKeyStore = _createTrustStore (aISCertData, eKeyStoreType, sPassword);
      if (aKeyStore == null)
        throw new IllegalStateException ("Failed to create trust store from NSS certdata.txt");

      try (final OutputStream aOS = new FileOutputStream (aOutputFile))
      {
        aKeyStore.store (aOS, sPassword.toCharArray ());
      }
      LOGGER.info ("Wrote trust store to " + aOutputFile.getAbsolutePath () + " (" + aKeyStore.size () + " entries)");
    }
  }

  public static void main (final String [] args) throws Exception
  {
    // Default: create a PKCS12 trust store with all Mozilla-trusted TLS root CAs
    final EKeyStoreType eType = EKeyStoreType.PKCS12;
    final String sPassword = "changeit";
    final File aTargetFolder = new File ("src/main/resources/truststore");
    final File aOutputFile = new File (aTargetFolder, "mozilla-nss-root-certs.p12");

    _downloadAndConvert (aOutputFile, eType, sPassword);

    // Print summary
    final KeyStore aKS = eType.getKeyStore ();
    try (final InputStream aIS = new FileInputStream (aOutputFile))
    {
      aKS.load (aIS, sPassword.toCharArray ());
    }

    LOGGER.info ("Trust store contains " + aKS.size () + " certificates");

    final String sBuiltinsVersion = _getBuiltinsLibraryVersion ();
    if (sBuiltinsVersion != null)
      LOGGER.info ("Using NSS trust list version " + sBuiltinsVersion);

    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("Content of the Mozilla NSS Root Certificate Truststore");
    if (sBuiltinsVersion != null)
      aSB.append (" v").append (sBuiltinsVersion);
    aSB.append (" (last update: ")
       .append (DateTimeFormatter.ISO_LOCAL_DATE.format (PDTFactory.getCurrentLocalDate ()))
       .append (")\n")
       .append ("Password: **")
       .append (sPassword)
       .append ("**\n\n");
    final Enumeration <String> aAliases = aKS.aliases ();
    while (aAliases.hasMoreElements ())
    {
      final String sAlias = aAliases.nextElement ();
      final X509Certificate aCert = (X509Certificate) aKS.getCertificate (sAlias);
      aSB.append ("* Alias `" +
                  sAlias +
                  "` refering to " +
                  aCert.getSubjectX500Principal ().getName () +
                  " (valid from " +
                  DateTimeFormatter.ISO_OFFSET_DATE_TIME.format (PDTFactory.createOffsetDateTime (aCert.getNotBefore ())) +
                  " to " +
                  DateTimeFormatter.ISO_OFFSET_DATE_TIME.format (PDTFactory.createOffsetDateTime (aCert.getNotAfter ())) +
                  ")\n");
    }
    SimpleFileIO.writeFile (new File (aTargetFolder, "mozilla-nss-root-certs.md"),
                            aSB.toString (),
                            StandardCharsets.UTF_8);
  }
}
