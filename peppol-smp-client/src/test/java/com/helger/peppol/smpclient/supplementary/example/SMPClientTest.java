package com.helger.peppol.smpclient.supplementary.example;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppol.smp.EndpointType;
import com.helger.peppol.smpclient.SMPClientReadOnly;
import com.helger.peppol.url.PeppolURLProvider;

public class SMPClientTest {
	
	public static void main(String[] args) throws Exception {
		IParticipantIdentifier participantId = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme("0088:5060510050006");
		SMPClientReadOnly smpClient = new SMPClientReadOnly(PeppolURLProvider.INSTANCE, participantId, ESML.DIGIT_PRODUCTION);
		
		IDocumentTypeIdentifier doctypeId = PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme("URN:OASIS:NAMES:SPECIFICATION:UBL:SCHEMA:XSD:ORDER-2::ORDER##URN:WWW.CENBII.EU:TRANSACTION:BIITRNS001:VER2.0:EXTENDED:URN:WWW.PEPPOL.EU:BIS:PEPPOL28A:VER1.0::2.1");
		IProcessIdentifier processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme("URN:WWW.CENBII.EU:PROFILE:BII28:VER2.0");
		
		EndpointType endpoint = smpClient.getEndpoint(participantId, doctypeId, processId, ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
		System.out.println("1 - " + endpoint);
		
		processId = PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme("urn:www.cenbii.eu:profile:bii28:ver2.0");
		endpoint = smpClient.getEndpoint(participantId, doctypeId, processId, ESMPTransportProfile.TRANSPORT_PROFILE_AS2);
		System.out.println("2 - " + endpoint);
	}
}
