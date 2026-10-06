/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.peppol.phosssmp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import com.helger.smpclient.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.xsds.peppol.smp1.EndpointType;

/**
 * Test class for class {@link PhossSmpPeppolClient}.
 *
 * @author Philip Helger
 */
public final class PhossSmpPeppolClientTest
{
  @Test
  public void testEndpointSerialization ()
  {
    final PhossSmpPeppolClient aClient = new PhossSmpPeppolClient (URI.create ("http://localhost/"));
    final EndpointType aEndpoint = new EndpointType ();
    aEndpoint.setTransportProfile ("peppol-transport-as4-v2_0");
    aEndpoint.setEndpointReference (W3CEndpointReferenceHelper.createEndpointReference ("http://test.smpserver/as4"));
    aEndpoint.setRequireBusinessLevelSignature (false);
    aEndpoint.setCertificate ("blacert");
    aEndpoint.setServiceDescription ("Unit test service");
    aEndpoint.setTechnicalContactUrl ("https://github.com/phax/phoss-smp");
    final String sXML = aClient.getEndpointAsXMLString (aEndpoint);
    assertNotNull (sXML);
    assertTrue (sXML, sXML.contains (":Endpoint "));
    assertTrue (sXML, sXML.contains ("http://busdox.org/serviceMetadata/publishing/1.0/"));
    assertTrue (sXML, sXML.contains ("http://test.smpserver/as4"));
  }
}
