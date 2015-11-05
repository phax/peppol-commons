package com.helger.peppol.smpclient.functest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.process.SimpleProcessIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.smpclient.SMPClientReadOnly;
import com.helger.peppol.smpclient.exception.SMPClientException;

public final class MainCheckBogusCertificateStrings
{
  public static void main (final String [] args) throws CertificateException, SMPClientException
  {
    final SimpleParticipantIdentifier aPI = SimpleParticipantIdentifier.createWithDefaultScheme ("9906:testconsip");
    final SMPClientReadOnly aSMPClient = new SMPClientReadOnly (aPI, ESML.DIGIT_TEST);
    final X509Certificate aCert = aSMPClient.getEndpointCertificate (aPI,
                                                                     SimpleDocumentTypeIdentifier.createWithDefaultScheme ("urn:oasis:names:specification:ubl:schema:xsd:Order-2::Order##urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0::2.1"),
                                                                     SimpleProcessIdentifier.createWithDefaultScheme ("urn:www.cenbii.eu:profile:bii03:ver2.0"),
                                                                     ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
    System.out.println (String.valueOf (aCert));
  }
}
