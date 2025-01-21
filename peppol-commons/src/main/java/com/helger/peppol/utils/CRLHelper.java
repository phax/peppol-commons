/*
 * Copyright (C) 2015-2025 Philip Helger
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
import java.io.UncheckedIOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
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
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.stream.NonBlockingByteArrayInputStream;

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
  private static final Logger LOGGER = LoggerFactory.getLogger (CRLHelper.class);

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

    try (final NonBlockingByteArrayInputStream aIS = new NonBlockingByteArrayInputStream (aCRLBytes))
    {
      final CertificateFactory cf = CertificateFactory.getInstance ("X.509");
      return (X509CRL) cf.generateCRL (aIS);
    }
    catch (final CertificateException ex)
    {
      throw new IllegalArgumentException (ex);
    }
    catch (final CRLException ex)
    {
      throw new IllegalArgumentException ("Cannot generate X.509 CRL from the stream data", ex);
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
                if (LOGGER.isDebugEnabled ())
                  LOGGER.info ("Found CRL URL '" + sURL + "' in certificate");
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
}
