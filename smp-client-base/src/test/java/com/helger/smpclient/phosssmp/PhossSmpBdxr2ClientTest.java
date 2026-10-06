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
package com.helger.smpclient.phosssmp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

import com.helger.xsds.bdxr.smp2.ac.EndpointType;

/**
 * Test class for class {@link PhossSmpBdxr2Client}.
 *
 * @author Philip Helger
 */
public final class PhossSmpBdxr2ClientTest
{
  @Test
  public void testEndpointSerialization ()
  {
    final PhossSmpBdxr2Client aClient = new PhossSmpBdxr2Client (URI.create ("http://localhost/"));
    final EndpointType aEndpoint = new EndpointType ();
    aEndpoint.setTransportProfileID ("bdxr-transport-ebms3-as4-v1p0");
    aEndpoint.setDescription ("Unit test service");
    aEndpoint.setAddressURI ("http://test.smpserver/as4");
    final String sXML = aClient.getEndpointAsXMLString (aEndpoint);
    assertNotNull (sXML);
    assertTrue (sXML, sXML.contains (":Endpoint "));
    assertTrue (sXML, sXML.contains ("http://docs.oasis-open.org/bdxr/ns/SMP/2/AggregateComponents"));
    assertTrue (sXML, sXML.contains ("http://test.smpserver/as4"));
  }
}
