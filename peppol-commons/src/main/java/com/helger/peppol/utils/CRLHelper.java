/**
 * Copyright (C) 2015-2021 Philip Helger
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
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
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
  private CRLHelper ()
  {}

  /**
   * Convert the provided CRL bytes into a {@link X509CRL} object.
   *
   * @param aCRLBytes
   *        The CRL bytes received from an external source. May neither be
   *        <code>null</code> nor empty.
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
      try (final ASN1InputStream asn1In = new ASN1InputStream (aExtensionValue))
      {
        // DER (Distinguished Encoding Rules) is one of ASN.1 encoding rules
        // defined in ITU-T X.690, 2002, specification.
        // ASN.1 encoding rules can be used to encode any data object into a
        // binary file. Read the object in octets.
        final CRLDistPoint aDistPoint;
        try
        {
          final DEROctetString crlDEROctetString = (DEROctetString) asn1In.readObject ();
          // Get Input stream in octets
          final ASN1InputStream asn1InOctets = new ASN1InputStream (crlDEROctetString.getOctets ());
          final ASN1Primitive crlDERObject = asn1InOctets.readObject ();
          aDistPoint = CRLDistPoint.getInstance (crlDERObject);
        }
        catch (final IOException e)
        {
          throw new UncheckedIOException (e);
        }

        // Loop through ASN1Encodable DistributionPoints
        for (final DistributionPoint dp : aDistPoint.getDistributionPoints ())
        {
          // get ASN1Encodable DistributionPointName
          final DistributionPointName dpn = dp.getDistributionPoint ();
          if (dpn != null && dpn.getType () == DistributionPointName.FULL_NAME)
          {
            // Create ASN1Encodable General Names
            final GeneralName [] aGenNames = GeneralNames.getInstance (dpn.getName ()).getNames ();
            // Look for a URI
            for (final GeneralName aGenName : aGenNames)
            {
              if (aGenName.getTagNo () == GeneralName.uniformResourceIdentifier)
              {
                // DERIA5String contains an ascii string.
                // A IA5String is a restricted character string type in the
                // ASN.1 notation
                final String sURL = DERIA5String.getInstance (aGenName.getName ()).getString ().trim ();
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
   * A cache for CRLs read from remote locations.
   *
   * @author Philip Helger
   */
  public static final class CRLCache extends Cache <String, TimedCRL>
  {
    public static final CRLCache INSTANCE = new CRLCache ();

    private static final Logger LOGGER = LoggerFactory.getLogger (CRLHelper.CRLCache.class);

    @Nullable
    private static TimedCRL _loadCRL (@Nonnull final String sCRLURL)
    {
      if (EURLProtocol.HTTP.isUsedInURL (sCRLURL) || EURLProtocol.HTTPS.isUsedInURL (sCRLURL) || EURLProtocol.FTP.isUsedInURL (sCRLURL))
      {
        // Try to download from remote URL
        LOGGER.info ("Trying to download CRL from URL '" + sCRLURL + "'");
        final StopWatch aSW = StopWatch.createdStarted ();
        int nByteCount = 0;
        // Use the built in HTTP client here (global proxy, etc.)
        try (final InputStream aIS = new URL (sCRLURL).openStream ())
        {
          final byte [] aCRLBytes = StreamHelper.getAllBytes (aIS);
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
          LOGGER.info ("Downloading the CRL took " + aSW.getMillis () + " milliseconds for " + nByteCount + " bytes");
        }
      }

      return null;
    }

    protected CRLCache ()
    {
      super (CRLCache::_loadCRL, 100, "CRL Cache");
    }
  }

  /**
   * Get the CRL object from the provided URL. Uses caching internally.
   *
   * @param sCRLURL
   *        The URL to read the CRL from.
   * @return <code>null</code> if the CRL could not be read from that URL.
   */
  @Nullable
  public static CRL getCRLFromURL (@Nullable final String sCRLURL)
  {
    if (StringHelper.hasText (sCRLURL))
    {
      final TimedCRL aObject = CRLCache.INSTANCE.getFromCache (sCRLURL);
      if (aObject != null)
      {
        // TODO we could implement a maximum life time check here
        return aObject.getCRL ();
      }
    }
    return null;
  }
}
