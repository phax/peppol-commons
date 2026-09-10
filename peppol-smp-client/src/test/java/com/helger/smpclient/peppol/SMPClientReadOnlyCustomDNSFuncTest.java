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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.collection.commons.ICommonsList;
import com.helger.peppol.security.PeppolTrustStores.Config2025;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smp.ESMPTransportProfile;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.url.PeppolNaptrURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.peppol.smp1.EndpointType;
import com.helger.xsds.peppol.smp1.ServiceGroupType;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Test class for class {@link SMPClientReadOnly} that performs basic lookups of Peppol production
 * participants using the custom DNS server <code>5.44.136.54</code>.<br>
 * Note: the custom DNS servers are always queried on the default DNS port 53, as ph-dns creates the
 * <code>SimpleResolver</code> instances without an explicit port.<br>
 * Note: ph-dns adds the system default DNS servers as fallbacks after the custom ones, so a lookup
 * may still succeed if the custom DNS server is not reachable or refuses the query.
 *
 * @author Philip Helger
 */
public final class SMPClientReadOnlyCustomDNSFuncTest
{
  /** The custom DNS server to be used - port 53 is implied */
  private static final String CUSTOM_DNS_SERVER = "5.44.136.54";

  /** A Peppol production participant identifier that is known to be registered */
  private static final String PARTICIPANT_ID_BRZ = "9915:b";

  @NonNull
  private static PeppolNaptrURLProvider _createURLProvider () throws UnknownHostException
  {
    final PeppolNaptrURLProvider ret = new PeppolNaptrURLProvider ();
    ret.customDNSServers ().addAll (InetAddress.getAllByName (CUSTOM_DNS_SERVER));
    return ret;
  }

  @NonNull
  private static SMPClientReadOnly _createSMPClient (@NonNull final IParticipantIdentifier aPI) throws UnknownHostException, SMPDNSResolutionException
  {
    final SMPClientReadOnly ret = new SMPClientReadOnly (_createURLProvider (), aPI, ESML.PEPPOL_PRODUCTION);
    // Explicitly needs the production truststore
    ret.setTrustStore (Config2025.TRUSTSTORE_SMP_PRODUCTION);
    ret.setXMLSchemaValidation (true);
    return ret;
  }

  @Test
  public void testResolveSMPHostURI () throws UnknownHostException, SMPDNSResolutionException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (PARTICIPANT_ID_BRZ);

    final SMPClientReadOnly aSMPClient = _createSMPClient (aPI);
    assertEquals ("https://www.erechnung.gv.at/smp/", aSMPClient.getSMPHostURI ());
  }

  @Test
  public void testResolveSMPHostURIOtherParticipant () throws UnknownHostException, SMPDNSResolutionException
  {
    // OpenPeppol AISBL
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("9925:be0848934496");

    final SMPClientReadOnly aSMPClient = _createSMPClient (aPI);
    assertEquals ("https://smp.peppol.org/", aSMPClient.getSMPHostURI ());
  }

  @Test
  public void testGetServiceGroup () throws UnknownHostException, SMPDNSResolutionException, SMPClientException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (PARTICIPANT_ID_BRZ);

    final SMPClientReadOnly aSMPClient = _createSMPClient (aPI);
    final ServiceGroupType aServiceGroup = aSMPClient.getServiceGroupOrNull (aPI);
    assertNotNull (aServiceGroup);
    assertNotNull (aServiceGroup.getParticipantIdentifier ());
    assertEquals (PARTICIPANT_ID_BRZ, aServiceGroup.getParticipantIdentifier ().getValue ());

    final ICommonsList <IDocumentTypeIdentifier> aDocTypes = SMPClientReadOnly.getAllDocumentTypes (aServiceGroup);
    assertTrue (aDocTypes.isNotEmpty ());
    assertTrue (aDocTypes.containsAny (x -> x.hasSameContent (EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30)));
  }

  @Test
  public void testGetServiceMetadata () throws UnknownHostException, SMPDNSResolutionException, SMPClientException
  {
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (PARTICIPANT_ID_BRZ);

    final SMPClientReadOnly aSMPClient = _createSMPClient (aPI);
    final SignedServiceMetadataType aSM = aSMPClient.getServiceMetadataOrNull (aPI,
                                                                               EPredefinedDocumentTypeIdentifier.INVOICE_EN16931_PEPPOL_V30);
    assertNotNull (aSM);

    final EndpointType aEndpoint = SMPClientReadOnly.getEndpoint (aSM,
                                                                  EPredefinedProcessIdentifier.BIS3_BILLING,
                                                                  ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2);
    assertNotNull (aEndpoint);
    assertNotNull (SMPClientReadOnly.getEndpointAddress (aEndpoint));
  }
}
