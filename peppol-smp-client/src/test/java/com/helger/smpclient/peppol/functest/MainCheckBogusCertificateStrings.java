/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.smpclient.peppol.functest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.peppol.SMPClientReadOnly;
import com.helger.smpclient.url.IPeppolURLProvider;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * @author Philip Helger
 */
@Deprecated
public final class MainCheckBogusCertificateStrings
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCheckBogusCertificateStrings.class);
  private static final IPeppolURLProvider URL_PROVIDER = PeppolURLProvider.INSTANCE;

  public static void main (final String [] args) throws CertificateException,
                                                 SMPClientException,
                                                 SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9906:testconsip");
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URL_PROVIDER, aPI, ESML.DIGIT_TEST);
    final X509Certificate aCert = aSMPClient.getEndpointCertificate (aPI,
                                                                     PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1"),
                                                                     PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii03:ver2.0"),
                                                                     ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    LOGGER.info (String.valueOf (aCert));
  }
}
