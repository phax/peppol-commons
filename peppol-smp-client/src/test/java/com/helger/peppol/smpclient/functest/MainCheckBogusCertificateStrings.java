/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smpclient.functest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.smpclient.SMPClientReadOnly;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.url.IPeppolURLProvider;
import com.helger.peppol.url.PeppolURLProvider;

public final class MainCheckBogusCertificateStrings
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainCheckBogusCertificateStrings.class);
  private static final IPeppolURLProvider URL_PROVIDER = PeppolURLProvider.INSTANCE;

  public static void main (final String [] args) throws CertificateException, SMPClientException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9906:testconsip");
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (URL_PROVIDER, aPI, ESML.DIGIT_TEST);
    final X509Certificate aCert = aSMPClient.getEndpointCertificate (aPI,
                                                                     PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1"),
                                                                     PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii03:ver2.0"),
                                                                     ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    s_aLogger.info (String.valueOf (aCert));
  }
}
