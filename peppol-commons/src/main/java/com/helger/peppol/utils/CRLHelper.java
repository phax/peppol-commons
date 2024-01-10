/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.peppol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.cache.Cache;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.timing.StopWatch;
import com.helger.commons.url.EURLProtocol;

/**
 * Helper class to deal with CRLs. This class requires BouncyCastle to be in the
 * classpath.
 *
 * @author Philip Helger
 * @since 8.5.2
 */
@Immutable
public final class CRLHelper
{
  public static final Duration DEFAULT_CACHING_DURATION = Duration.ofDays (1);
  private static final Logger LOGGER = LoggerFactory.getLogger (CRLHelper.class);

  /**
   * The combination of a CRL and the date time it was read. May be used to
   * expired the cache at a certain point in time.
   *
   * @author Philip Helger
   */
  public static class TimedCRL
  {
    private final LocalDateTime m_aReadDateTime;
    private final CRL m_aCRL;

    public TimedCRL (@Nonnull final LocalDateTime aReadDateTime, @Nonnull final CRL aCRL)
    {
      m_aReadDateTime = aReadDateTime;
      m_aCRL = aCRL;
    }

    /**
     * @return The date and time when it was read. Never <code>null</code>.
     */
    @Nonnull
    public final LocalDateTime getReadDateTime ()
    {
      return m_aReadDateTime;
    }

    /**
     * Check this entry is still valid
     *
     * @param aCachingDuration
     *        The caching duration that is allowed. May not be
     *        <code>null</code>.
     * @return <code>true</code> if the read date time plus the caching duration
     *         is before now
     */
    public boolean isValid (@Nonnull final Duration aCachingDuration)
    {
      return m_aReadDateTime.plus (aCachingDuration).isAfter (PDTFactory.getCurrentLocalDateTime ());
    }

    /**
     * @return The CRL itself. Never <code>null</code>.
     */
    @Nonnull
    public final CRL getCRL ()
    {
      return m_aCRL;
    }

    @Nonnull
    public static TimedCRL ofNow (@Nonnull final CRL aCRL)
    {
      return new TimedCRL (PDTFactory.getCurrentLocalDateTime (), aCRL);
    }
  }

  /**
   * Callback interface to download CRL data. Use it globally with
   * {@link CRLCache#setDownloader(ICRLDownloader)}.
   *
   * @author Philip Helger
   * @since 9.2.4
   */
  @FunctionalInterface
  public interface ICRLDownloader
  {
    /**
     * Download the content of the provided URL
     *
     * @param sURL
     *        The CRL URL to download. Neither <code>null</code> nor empty.
     * @return <code>null</code> if no payload was returned
     * @throws Exception
     *         In case of error
     */
    @Nullable
    byte [] downloadURL (@Nonnull @Nonempty String sURL) throws Exception;
  }

  /**
   * A cache for CRLs read from remote locations. The remote reading can be
   * customized by setting a specific CRL downloader via
   * {@link #setDownloader(ICRLDownloader)}
   *
   * @author Philip Helger
   */
  public static final class CRLCache extends Cache <String, TimedCRL>
  {
    public static final CRLCache INSTANCE = new CRLCache ();
    public static final ICRLDownloader DEFAULT_DOWNLOADER = sURL -> {
      // Use the built in HTTP client here (global proxy, etc.)
      try (final InputStream aIS = new URL (sURL).openStream ())
      {
        return StreamHelper.getAllBytes (aIS);
      }
    };

    private static ICRLDownloader s_aDownloader = DEFAULT_DOWNLOADER;

    @Nonnull
    public static ICRLDownloader getDownloader ()
    {
      return s_aDownloader;
    }

    public static void setDownloader (@Nonnull final ICRLDownloader aDownloader)
    {
      ValueEnforcer.notNull (aDownloader, "Downloader");
      s_aDownloader = aDownloader;
      LOGGER.info ("Set the global CRL Downloader to be " + aDownloader);
    }

    @Nullable
    private static TimedCRL _loadCRL (@Nonnull final String sCRLURL)
    {
      if (EURLProtocol.HTTP.isUsedInURL (sCRLURL) || EURLProtocol.HTTPS.isUsedInURL (sCRLURL))
      {
        // Try to download from remote URL
        LOGGER.info ("Downloading CRL from URL '" + sCRLURL + "'");
        final StopWatch aSW = StopWatch.createdStarted ();
        int nByteCount = 0;
        try
        {
          final byte [] aCRLBytes = s_aDownloader.downloadURL (sCRLURL);
          if (aCRLBytes != null)
          {
            nByteCount = aCRLBytes.length;
            return TimedCRL.ofNow (CRLHelper.convertToCRL (aCRLBytes));
          }
        }
        catch (final Exception ex)
        {
          LOGGER.error ("Error downloading CRL from URL '" + sCRLURL + "'", ex);
        }
        finally
        {
          aSW.stop ();
          if (aSW.getMillis () > 1_000)
            LOGGER.info ("Downloading the CRL took " + aSW.getMillis () + " milliseconds for " + nByteCount + " bytes");
        }
      }

      return null;
    }

    protected CRLCache ()
    {
      super (CRLCache::_loadCRL, 100, "CRL Cache");
    }

    void manuallyPutInCache (@Nonnull final String sCRLURL, @Nonnull final TimedCRL aTimedCRL)
    {
      ValueEnforcer.notEmpty (sCRLURL, "CRLURL");
      ValueEnforcer.notNull (aTimedCRL, "TimedCRL");
      super.putInCache (sCRLURL, aTimedCRL);
    }
  }

  private CRLHelper ()
  {}

  /**
   * Convert the provided CRL bytes into a {@link X509CRL} object.
   *
   * @param aCRLBytes
   *        The CRL bytes received from an external source. May neither be
   *        <code>null</code> nor empty.
   * @return The parsed CRL object.
   * @throws IllegalArgumentException
   *         In case of conversion errors
   */
  @Nonnull
  public static X509CRL convertToCRL (@Nonnull @Nonempty final byte [] aCRLBytes)
  {
    ValueEnforcer.notEmpty (aCRLBytes, "CRLBytes");

    try (final NonBlockingByteArrayInputStream crlStream = new NonBlockingByteArrayInputStream (aCRLBytes))
    {
      final CertificateFactory cf = CertificateFactory.getInstance ("X.509");
      return (X509CRL) cf.generateCRL (crlStream);
    }
    catch (final CertificateException e)
    {
      throw new IllegalArgumentException (e);
    }
    catch (final CRLException e)
    {
      throw new IllegalArgumentException ("Cannot generate X509CRL from the stream data", e);
    }
  }

  /**
   * Extracts all CRL distribution point URLs from the "CRL Distribution Point"
   * extension in a X.509 certificate. If CRL distribution point extension is
   * unavailable, returns an empty list.
   *
   * @param aCert
   *        The certificate to extract the CRLs from
   * @return Never <code>null</code> but maybe empty list of distribution
   *         points.
   */
  @Nonnull
  public static ICommonsList <String> getAllDistributionPoints (@Nonnull final X509Certificate aCert)
  {
    ValueEnforcer.notNull (aCert, "Certificate");
    final ICommonsList <String> ret = new CommonsArrayList <> ();

    // Gets the DER-encoded OCTET string for the extension value for
    // CRLDistributionPoints
    final byte [] aExtensionValue = aCert.getExtensionValue (Extension.cRLDistributionPoints.getId ());
    if (aExtensionValue != null)
    {
      // crlDPExtensionValue is encoded in ASN.1 format.
      try (final ASN1InputStream aAsn1IS = new ASN1InputStream (aExtensionValue))
      {
        // DER (Distinguished Encoding Rules) is one of ASN.1 encoding rules
        // defined in ITU-T X.690, 2002, specification.
        // ASN.1 encoding rules can be used to encode any data object into a
        // binary file. Read the object in octets.
        final CRLDistPoint aDistPoint;
        try
        {
          final DEROctetString aCrlDEROctetString = (DEROctetString) aAsn1IS.readObject ();
          // Get Input stream in octets
          try (final ASN1InputStream aAsn1InOctets = new ASN1InputStream (aCrlDEROctetString.getOctets ()))
          {
            final ASN1Primitive aCrlDERObject = aAsn1InOctets.readObject ();
            aDistPoint = CRLDistPoint.getInstance (aCrlDERObject);
          }
        }
        catch (final IOException e)
        {
          throw new UncheckedIOException (e);
        }

        // Loop through ASN1Encodable DistributionPoints
        for (final DistributionPoint aDP : aDistPoint.getDistributionPoints ())
        {
          // get ASN1Encodable DistributionPointName
          final DistributionPointName aDPName = aDP.getDistributionPoint ();
          if (aDPName != null && aDPName.getType () == DistributionPointName.FULL_NAME)
          {
            // Create ASN1Encodable General Names
            final GeneralName [] aGenNames = GeneralNames.getInstance (aDPName.getName ()).getNames ();
            // Look for a URI
            for (final GeneralName aGenName : aGenNames)
            {
              if (aGenName.getTagNo () == GeneralName.uniformResourceIdentifier)
              {
                // DERIA5String contains an ascii string.
                // A IA5String is a restricted character string type in the
                // ASN.1 notation
                final String sURL = ASN1IA5String.getInstance (aGenName.getName ()).getString ().trim ();
                LOGGER.info ("Found CRL URL '" + sURL + "'");
                ret.add (sURL);
              }
            }
          }
        }
      }
      catch (final IOException ex)
      {
        throw new UncheckedIOException (ex);
      }
    }
    return ret;
  }

  /**
   * Get the CRL object from the provided URL. Uses caching internally. Uses the
   * default caching duration {@link #DEFAULT_CACHING_DURATION}.
   *
   * @param sCRLURL
   *        The URL to read the CRL from.
   * @return <code>null</code> if the CRL could not be read from that URL.
   */
  @Nullable
  public static CRL getCRLFromURL (@Nullable final String sCRLURL)
  {
    return getCRLFromURL (sCRLURL, DEFAULT_CACHING_DURATION);
  }

  /**
   * Get the CRL object from the provided URL. Uses caching internally.
   *
   * @param sCRLURL
   *        The URL to read the CRL from.
   * @param aCachingDuration
   *        The maximum caching duration. May not be <code>null</code>.
   * @return <code>null</code> if the CRL could not be read from that URL.
   * @since 9.0.8
   */
  @Nullable
  public static CRL getCRLFromURL (@Nullable final String sCRLURL, @Nonnull final Duration aCachingDuration)
  {
    ValueEnforcer.notNull (aCachingDuration, "CachingDuration");
    if (StringHelper.hasText (sCRLURL))
    {
      TimedCRL aObject = CRLCache.INSTANCE.getFromCache (sCRLURL);
      if (aObject != null)
      {
        // maximum life time check
        if (aObject.isValid (aCachingDuration))
          return aObject.getCRL ();

        // Object expired - re-fetch
        CRLCache.INSTANCE.removeFromCache (sCRLURL);
        aObject = CRLCache.INSTANCE.getFromCache (sCRLURL);
        if (aObject != null)
          return aObject.getCRL ();
      }
    }
    return null;
  }

  /**
   * Allow to manually add a downloaded CRL into the cache, for the provided
   * URL.
   *
   * @param sCRLURL
   *        The URL for which the cached URL should be used. May neither be
   *        <code>null</code> nor empty.
   * @param aCRL
   *        The CRL to be used. May not be <code>null</code>.
   * @since 9.0.8
   */
  public static void setCRLOfURL (@Nonnull @Nonempty final String sCRLURL, @Nonnull final CRL aCRL)
  {
    ValueEnforcer.notEmpty (sCRLURL, "CRLURL");
    ValueEnforcer.notNull (aCRL, "CRL");
    CRLCache.INSTANCE.manuallyPutInCache (sCRLURL, TimedCRL.ofNow (aCRL));
  }
}
