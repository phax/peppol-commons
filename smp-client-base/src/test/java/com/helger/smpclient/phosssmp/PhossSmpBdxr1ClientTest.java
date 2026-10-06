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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.base.io.stream.StreamHelper;
import com.helger.base.state.ESuccess;
import com.helger.collection.commons.ICommonsList;
import com.helger.http.CHttpHeader;
import com.helger.http.IHttpClientCredentials;
import com.helger.http.bearerauth.BearerAuthClientCredentials;
import com.helger.peppol.businesscard.v3.PD3APIHelper;
import com.helger.peppol.businesscard.v3.PD3BusinessCardType;
import com.helger.peppol.businesscard.v3.PD3BusinessEntityType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.bdxr.smp1.doctype.BDXR1DocumentTypeIdentifier;
import com.helger.peppolid.bdxr.smp1.participant.BDXR1ParticipantIdentifier;
import com.helger.peppolid.bdxr.smp1.process.BDXR1ProcessIdentifier;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xsds.bdxr.smp1.EndpointType;
import com.sun.net.httpserver.HttpServer;

/**
 * Test class for class {@link IPhossSmpClient} using {@link PhossSmpBdxr1Client} against a local
 * HTTP server stub.
 *
 * @author Philip Helger
 */
public final class PhossSmpBdxr1ClientTest
{
  private static final IHttpClientCredentials CREDS = new BearerAuthClientCredentials ("token123");
  private static final IParticipantIdentifier SG = new BDXR1ParticipantIdentifier ("iso6523-actorid-upis",
                                                                                   "0088:5798000000001");
  private static final IDocumentTypeIdentifier DT = new BDXR1DocumentTypeIdentifier ("busdox-docid-qns",
                                                                                     "urn:test::Invoice##1.0");
  private static final IProcessIdentifier PROC = new BDXR1ProcessIdentifier ("cenbii-procid-ubl", "urn:proc:1");

  /** The last received request */
  private static final class Recorded
  {
    String m_sMethod;
    String m_sPath;
    String m_sQuery;
    String m_sAuth;
    String m_sContentType;
    byte [] m_aBody;
  }

  private static HttpServer s_aServer;
  private static PhossSmpBdxr1Client s_aClient;
  private static volatile Recorded s_aLast;
  private static volatile int s_nResponseCode;
  private static volatile byte [] s_aResponse;

  @BeforeClass
  public static void beforeClass () throws IOException
  {
    s_aServer = HttpServer.create (new InetSocketAddress ("127.0.0.1", 0), 0);
    s_aServer.createContext ("/", aExchange -> {
      final Recorded aRec = new Recorded ();
      aRec.m_sMethod = aExchange.getRequestMethod ();
      aRec.m_sPath = aExchange.getRequestURI ().getRawPath ();
      aRec.m_sQuery = aExchange.getRequestURI ().getRawQuery ();
      aRec.m_sAuth = aExchange.getRequestHeaders ().getFirst (CHttpHeader.AUTHORIZATION);
      aRec.m_sContentType = aExchange.getRequestHeaders ().getFirst (CHttpHeader.CONTENT_TYPE);
      aRec.m_aBody = StreamHelper.getAllBytes (aExchange.getRequestBody ());
      s_aLast = aRec;
      final byte [] aResponse = s_aResponse;
      if (aResponse == null)
        aExchange.sendResponseHeaders (s_nResponseCode, -1);
      else
      {
        aExchange.sendResponseHeaders (s_nResponseCode, aResponse.length);
        aExchange.getResponseBody ().write (aResponse);
      }
      aExchange.close ();
    });
    s_aServer.start ();
    s_aClient = new PhossSmpBdxr1Client (URI.create ("http://127.0.0.1:" + s_aServer.getAddress ().getPort () + '/'));
  }

  @AfterClass
  public static void afterClass ()
  {
    s_aServer.stop (0);
  }

  private static void _respond (final int nCode, @Nullable final String sBody)
  {
    s_nResponseCode = nCode;
    s_aResponse = sBody == null ? null : sBody.getBytes (StandardCharsets.UTF_8);
    s_aLast = null;
  }

  @NonNull
  private static Recorded _assertRequest (@NonNull final String sMethod,
                                          @NonNull final String sPath,
                                          final boolean bWithAuth)
  {
    final Recorded aRec = s_aLast;
    assertNotNull (aRec);
    assertEquals (sMethod, aRec.m_sMethod);
    assertEquals (sPath, aRec.m_sPath);
    if (bWithAuth)
      assertEquals ("Bearer token123", aRec.m_sAuth);
    else
      assertNull (aRec.m_sAuth);
    return aRec;
  }

  @NonNull
  private static String _body (@NonNull final Recorded aRec)
  {
    return new String (aRec.m_aBody, StandardCharsets.UTF_8);
  }

  @Test
  public void testServiceMetadata () throws Exception
  {
    final String sServices = "/" + SG.getURIPercentEncoded () + "/services";

    _respond (200, null);
    final EndpointType aEndpoint = new EndpointType ();
    aEndpoint.setTransportProfile ("bdxr-transport-ebms3-as4-v1p0");
    aEndpoint.setEndpointURI ("http://test.smpserver/as4");
    aEndpoint.setRequireBusinessLevelSignature (Boolean.FALSE);
    aEndpoint.setCertificate ("blacert".getBytes (StandardCharsets.ISO_8859_1));
    aEndpoint.setServiceDescription ("Unit test service");
    aEndpoint.setTechnicalContactUrl ("https://github.com/phax/phoss-smp");
    s_aClient.addServiceEndpoint (SG, DT, PROC, aEndpoint, CREDS);
    Recorded aRec = _assertRequest ("PUT",
                                    sServices + "/" + DT.getURIPercentEncoded () + "/" + PROC.getURIPercentEncoded (),
                                    true);
    assertTrue (aRec.m_sContentType.startsWith ("text/xml"));
    final String sBody = _body (aRec);
    assertTrue (sBody, sBody.contains (":Endpoint "));
    assertTrue (sBody, sBody.contains ("http://docs.oasis-open.org/bdxr/ns/SMP/2016/05"));
    assertTrue (sBody, sBody.contains ("http://test.smpserver/as4"));

    // 204 is handled as success
    _respond (204, null);
    s_aClient.deleteServiceProcess (SG, DT, PROC, CREDS);
    _assertRequest ("DELETE",
                    sServices + "/" + DT.getURIPercentEncoded () + "/" + PROC.getURIPercentEncoded (),
                    true);

    _respond (204, null);
    s_aClient.deleteAllServiceRegistrations (SG, CREDS);
    _assertRequest ("DELETE", sServices, true);

    _respond (404, null);
    try
    {
      s_aClient.deleteServiceProcess (SG, DT, PROC, CREDS);
      fail ();
    }
    catch (final SMPClientNotFoundException ex)
    {
      // expected
    }

    _respond (403, null);
    try
    {
      s_aClient.deleteAllServiceRegistrations (SG, CREDS);
      fail ();
    }
    catch (final SMPClientUnauthorizedException ex)
    {
      // expected
    }
  }

  @Test
  public void testServiceGroupIDs () throws Exception
  {
    _respond (200,
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<servicegroupids count=\"2\">\n" +
                   "  <servicegroupid>iso6523-actorid-upis::0007:5412345678901</servicegroupid>\n" +
                   "  <servicegroupid>iso6523-actorid-upis::9915:test</servicegroupid>\n" +
                   "</servicegroupids>");
    final ICommonsList <String> aIDs = s_aClient.getAllServiceGroupIDs (CREDS);
    _assertRequest ("GET", "/servicegroupids/all", true);
    assertEquals (2, aIDs.size ());
    assertEquals ("iso6523-actorid-upis::0007:5412345678901", aIDs.get (0));
    assertEquals ("iso6523-actorid-upis::9915:test", aIDs.get (1));
  }

  @Test
  public void testBusinessCard () throws Exception
  {
    final String sPath = "/businesscard/" + SG.getURIPercentEncoded ();

    final PD3BusinessCardType aBC = new PD3BusinessCardType ();
    aBC.setParticipantIdentifier (PD3APIHelper.createIdentifier (SG.getScheme (), SG.getValue ()));
    final PD3BusinessEntityType aBE = new PD3BusinessEntityType ();
    aBE.addName (PD3APIHelper.createName ("Test entity name", "en"));
    aBE.setCountryCode ("AT");
    aBC.addBusinessEntity (aBE);

    _respond (200, null);
    s_aClient.saveBusinessCard (SG, aBC, CREDS);
    final Recorded aRec = _assertRequest ("PUT", sPath, true);
    final String sBody = _body (aRec);
    assertTrue (sBody, sBody.contains ("http://www.peppol.eu/schema/pd/businesscard/20180621/"));
    assertTrue (sBody, sBody.contains ("Test entity name"));

    _respond (200, null);
    s_aClient.deleteBusinessCard (SG, CREDS);
    _assertRequest ("DELETE", sPath, true);

    _respond (200, null);
    s_aClient.pushBusinessCard (SG, CREDS);
    _assertRequest ("POST", sPath + "/push", true);
  }

  @Test
  public void testCustomProperties () throws Exception
  {
    final String sPath = "/" + SG.getURIPercentEncoded () + "/customproperties";

    // Without credentials
    _respond (200,
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<customproperties>\n" +
                   "  <customproperty type=\"public\" name=\"website\" value=\"https://example.org\"/>\n" +
                   "</customproperties>");
    SGCustomPropertyList aList = s_aClient.getAllCustomProperties (SG, null);
    _assertRequest ("GET", sPath, false);
    assertEquals (1, aList.size ());
    assertEquals ("https://example.org", aList.getValue ("website"));

    _respond (200, "Test participant äöü");
    assertEquals ("Test participant äöü", s_aClient.getCustomPropertyValue (SG, "internal-note", CREDS));
    _assertRequest ("GET", sPath + "/internal-note", true);

    _respond (204, null);
    s_aClient.setCustomProperty (SG, SGCustomProperty.createPrivate ("internal-note", "Wert äöü"), CREDS);
    Recorded aRec = _assertRequest ("PUT", sPath + "/private/internal-note", true);
    assertTrue (aRec.m_sContentType.startsWith ("text/plain"));
    assertEquals ("Wert äöü", _body (aRec));

    aList = new SGCustomPropertyList (SGCustomProperty.createPublic ("website", "https://example.org"),
                                      SGCustomProperty.createPrivate ("internal-note", "Test participant"));
    _respond (204, null);
    s_aClient.setAllCustomProperties (SG, aList, CREDS);
    aRec = _assertRequest ("PUT", sPath, true);
    final IMicroDocument aDoc = MicroReader.readMicroXML (aRec.m_aBody);
    assertNotNull (aDoc);
    assertEquals ("customproperties", aDoc.getDocumentElement ().getTagName ());
    assertEquals (aList, MicroTypeConverter.convertToNative (aDoc.getDocumentElement (), SGCustomPropertyList.class));

    _respond (200, "3");
    assertEquals (3, s_aClient.deleteAllCustomProperties (SG, CREDS));
    _assertRequest ("DELETE", sPath, true);

    _respond (204, null);
    s_aClient.deleteCustomProperty (SG, "website", CREDS);
    _assertRequest ("DELETE", sPath + "/website", true);

    _respond (404, null);
    try
    {
      s_aClient.getCustomPropertyValue (SG, "unknown", null);
      fail ();
    }
    catch (final SMPClientNotFoundException ex)
    {
      // expected
    }
  }

  @Test
  public void testMigration () throws Exception
  {
    final String sSG = SG.getURIPercentEncoded ();

    _respond (200,
              "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                   "<migrationOutboundResponse success=\"true\">\n" +
                   "  <participantID>iso6523-actorid-upis::0088:5798000000001</participantID>\n" +
                   "  <migrationKey>abcdef123456</migrationKey>\n" +
                   "</migrationOutboundResponse>");
    assertEquals ("abcdef123456", s_aClient.startParticipantMigrationOutbound (SG, CREDS));
    _assertRequest ("PUT", "/migration/outbound/start/" + sSG, true);

    _respond (200, null);
    s_aClient.cancelParticipantMigrationOutbound (SG, CREDS);
    _assertRequest ("PUT", "/migration/outbound/cancel/" + sSG, true);

    _respond (200, null);
    s_aClient.finalizeParticipantMigrationOutbound (SG, CREDS);
    _assertRequest ("PUT", "/migration/outbound/finalize/" + sSG, true);

    _respond (200,
              "<migrationInboundResponse success=\"true\" serviceGroupCreated=\"true\" migrationCreated=\"true\" />");
    assertEquals (ESuccess.SUCCESS, s_aClient.performParticipantMigrationInbound (SG, "ab+cd/ef=", CREDS));
    _assertRequest ("PUT", "/migration/inbound/" + sSG + "/ab%2Bcd%2Fef%3D", true);

    _respond (200,
              "<migrationInboundResponse success=\"false\" serviceGroupCreated=\"true\" migrationCreated=\"false\" />");
    assertEquals (ESuccess.FAILURE, s_aClient.performParticipantMigrationInbound (SG, "abc", CREDS));
  }

  @Test
  public void testExchange () throws Exception
  {
    final String sExport = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root version=\"1.0\"/>";

    _respond (200, sExport);
    assertArrayEquals (sExport.getBytes (StandardCharsets.UTF_8),
                       s_aClient.exportAllServiceGroupsAsBytes (false, CREDS));
    Recorded aRec = _assertRequest ("GET", "/exchange/export/all/xml/v1", true);
    assertEquals ("include-business-cards=false", aRec.m_sQuery);

    _respond (200, sExport);
    Document aDoc = s_aClient.exportServiceGroupsOfOwnerAsDocument ("user@example.org", true, CREDS);
    assertEquals ("root", aDoc.getDocumentElement ().getLocalName ());
    aRec = _assertRequest ("GET", "/exchange/export/byowner/user%40example.org/xml/v1", true);
    assertEquals ("include-business-cards=true", aRec.m_sQuery);

    _respond (200, sExport);
    assertNotNull (s_aClient.exportServiceGroupAsDocument (SG, true, CREDS));
    _assertRequest ("GET", "/exchange/export/specific/" + SG.getURIPercentEncoded () + "/xml/v1", true);

    _respond (200, sExport);
    assertNotNull (s_aClient.exportOutboundMigrationInProgressAsBytes (true, CREDS));
    _assertRequest ("GET", "/exchange/export/outboundmigip/xml/v1", true);

    _respond (200, "<actionlog />");
    aDoc = s_aClient.importServiceGroupsAsDocument (sExport.getBytes (StandardCharsets.UTF_8),
                                                    "user@example.org",
                                                    true,
                                                    CREDS);
    assertEquals ("actionlog", aDoc.getDocumentElement ().getLocalName ());
    aRec = _assertRequest ("PUT", "/exchange/import/xml/v1/user%40example.org", true);
    assertEquals ("overwrite-existing=true", aRec.m_sQuery);
    assertEquals (sExport, _body (aRec));
  }
}
