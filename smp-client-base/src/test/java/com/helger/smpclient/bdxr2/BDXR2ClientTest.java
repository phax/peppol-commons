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
package com.helger.smpclient.bdxr2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.jspecify.annotations.NonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerServiceGroup;
import com.helger.xsds.bdxr.smp2.ServiceGroupType;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;
import com.helger.xsds.bdxr.smp2.bc.IDType;
import com.helger.xsds.bdxr.smp2.bc.ParticipantIDType;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Test class for class {@link BDXR2Client}, specifically the request URIs of the non-standard
 * writing methods.
 * <p>
 * The point of this class is that the paths are <b>measured</b> and not derived from the source:
 * every request runs against a local HTTP server that records what it was asked for. The reason is
 * the defect this class pins - the writing methods used to put {@link
 * BDXR2ClientReadOnly#PATH_OASIS_BDXR_SMP_2} in front of the path, which is the path of the
 * <b>reading</b> interface. phoss SMP, the only server these non-standard methods target, answers a
 * <code>PUT</code> or <code>DELETE</code> there with <code>405 Allow: OPTIONS, GET, HEAD, POST</code>,
 * so none of them worked.
 * </p>
 * <p>
 * The last test pins the other direction as well: the reading methods <b>do</b> use that path. Both
 * assertions together are what keeps the two apart; either one alone invites "simplifying" them into
 * each other again.
 * </p>
 *
 * @author Philip Helger
 */
public final class BDXR2ClientTest
{
  private static final String PARTICIPANT_SCHEME = "iso6523-actorid-upis";
  private static final String PARTICIPANT_VALUE = "9915:test";
  private static final String DOCTYPE_SCHEME = "busdox-docid-qns";
  private static final String DOCTYPE_VALUE = "urn:test::doctype";

  private static final BasicAuthClientCredentials CREDENTIALS = new BasicAuthClientCredentials ("user", "pw");

  private HttpServer m_aServer;
  private final ICommonsList <String> m_aRequests = new CommonsArrayList <> ();

  @NonNull
  private static IParticipantIdentifier _participantID ()
  {
    return new SimpleParticipantIdentifier (PARTICIPANT_SCHEME, PARTICIPANT_VALUE);
  }

  @NonNull
  private static IDocumentTypeIdentifier _docTypeID ()
  {
    return new SimpleDocumentTypeIdentifier (DOCTYPE_SCHEME, DOCTYPE_VALUE);
  }

  @NonNull
  private static ParticipantIDType _participantIDType ()
  {
    final ParticipantIDType ret = new ParticipantIDType ();
    ret.setSchemeID (PARTICIPANT_SCHEME);
    ret.setValue (PARTICIPANT_VALUE);
    return ret;
  }

  @NonNull
  private static IDType _docTypeIDType ()
  {
    final IDType ret = new IDType ();
    ret.setSchemeID (DOCTYPE_SCHEME);
    ret.setValue (DOCTYPE_VALUE);
    return ret;
  }

  @NonNull
  private static ServiceGroupType _serviceGroup ()
  {
    final ServiceGroupType ret = new ServiceGroupType ();
    // Mandatory per the XML Schema - minOccurs="1"
    ret.setSMPVersionID (BDXR2Client.SMP_VERSION_ID);
    ret.setParticipantID (_participantIDType ());
    return ret;
  }

  private void _handle (@NonNull final HttpExchange aExchange) throws IOException
  {
    m_aRequests.add (aExchange.getRequestMethod () + " " + aExchange.getRequestURI ().getRawPath ());
    // Drain the body, otherwise the client may see a reset instead of the response
    aExchange.getRequestBody ().readAllBytes ();

    final byte [] aResponse;
    if ("GET".equals (aExchange.getRequestMethod ()))
    {
      final String sXML = new BDXR2MarshallerServiceGroup ().getAsString (_serviceGroup ());
      assertNotNull (sXML);
      aResponse = sXML.getBytes (StandardCharsets.UTF_8);
      aExchange.getResponseHeaders ().add ("Content-Type", "text/xml; charset=UTF-8");
    }
    else
      aResponse = new byte [0];

    aExchange.sendResponseHeaders (200, aResponse.length);
    try (final OutputStream aOS = aExchange.getResponseBody ())
    {
      aOS.write (aResponse);
    }
  }

  @NonNull
  private BDXR2Client _client ()
  {
    return new BDXR2Client (URI.create ("http://localhost:" + m_aServer.getAddress ().getPort () + "/"));
  }

  @NonNull
  private String _singleRequest ()
  {
    assertEquals ("Expected exactly one request, got " + m_aRequests, 1, m_aRequests.size ());
    final String ret = m_aRequests.getFirstOrNull ();
    assertNotNull (ret);
    return ret;
  }

  @Before
  public void before () throws IOException
  {
    m_aRequests.clear ();
    m_aServer = HttpServer.create (new InetSocketAddress ("localhost", 0), 0);
    m_aServer.createContext ("/", this::_handle);
    m_aServer.start ();
  }

  @After
  public void after ()
  {
    if (m_aServer != null)
      m_aServer.stop (0);
  }

  /** <code>PUT /{ParticipantID}</code> - at the root, not below the reading path. */
  @Test
  public void testSaveServiceGroup () throws Exception
  {
    _client ().saveServiceGroup (_serviceGroup (), CREDENTIALS);
    assertEquals ("PUT /" + _participantID ().getURIPercentEncoded (), _singleRequest ());
  }

  /**
   * The overload that takes only the participant identifier. It builds the ServiceGroup itself, and
   * that is the second thing this test pins: <code>SMPVersionID</code> is mandatory per the XML
   * Schema and schema validation is on by default, so a ServiceGroup without it never leaves the
   * client - the request below would not arrive at all.
   */
  @Test
  public void testSaveServiceGroupOfParticipantID () throws Exception
  {
    final ServiceGroupType aSG = _client ().saveServiceGroup (_participantID (), CREDENTIALS);
    assertEquals ("PUT /" + _participantID ().getURIPercentEncoded (), _singleRequest ());
    assertNotNull (aSG.getSMPVersionID ());
    assertEquals (BDXR2Client.SMP_VERSION_ID, aSG.getSMPVersionIDValue ());
  }

  /** <code>DELETE /{ParticipantID}</code> */
  @Test
  public void testDeleteServiceGroup () throws Exception
  {
    _client ().deleteServiceGroup (_participantID (), CREDENTIALS);
    assertEquals ("DELETE /" + _participantID ().getURIPercentEncoded (), _singleRequest ());
  }

  /** <code>PUT /{ParticipantID}/services/{DocumentTypeID}</code> */
  @Test
  public void testSaveServiceEndpoints () throws Exception
  {
    final EndpointType aEndpoint = new EndpointType ();
    aEndpoint.setTransportProfileID ("bdxr-transport-ebms3-as4-v1p0");
    aEndpoint.setAddressURI ("http://test.smpserver/as4");

    _client ().saveServiceEndpoints (_participantIDType (),
                                     _docTypeIDType (),
                                     new CommonsArrayList <> (aEndpoint),
                                     CREDENTIALS);
    assertEquals ("PUT /" + _participantID ().getURIPercentEncoded () + "/services/" + _docTypeID ().getURIPercentEncoded (),
                  _singleRequest ());
  }

  /** <code>DELETE /{ParticipantID}/services/{DocumentTypeID}</code> */
  @Test
  public void testDeleteServiceRegistration () throws Exception
  {
    _client ().deleteServiceRegistration (_participantID (), _docTypeID (), CREDENTIALS);
    assertEquals ("DELETE /" + _participantID ().getURIPercentEncoded () + "/services/" + _docTypeID ().getURIPercentEncoded (),
                  _singleRequest ());
  }

  /**
   * And the counterpart: a <b>read</b> keeps the path of the reading interface. Without this
   * assertion the fix above could be "simplified" into dropping the path everywhere.
   */
  @Test
  public void testReadingStillUsesTheReadingPath () throws Exception
  {
    final ServiceGroupType aSG = _client ().getServiceGroup (_participantID ());
    assertNotNull (aSG);

    final String sRequest = _singleRequest ();
    assertEquals ("GET /" + BDXR2ClientReadOnly.PATH_OASIS_BDXR_SMP_2 + _participantID ().getURIPercentEncoded (),
                  sRequest);
    assertTrue (sRequest.contains (BDXR2ClientReadOnly.PATH_OASIS_BDXR_SMP_2));
  }

  /** No writing method may use the path of the reading interface. */
  @Test
  public void testNoWriteUsesTheReadingPath () throws Exception
  {
    final BDXR2Client aClient = _client ();
    aClient.saveServiceGroup (_serviceGroup (), CREDENTIALS);
    aClient.deleteServiceRegistration (_participantID (), _docTypeID (), CREDENTIALS);
    aClient.deleteServiceGroup (_participantID (), CREDENTIALS);
    assertEquals (3, m_aRequests.size ());
    for (final String sRequest : m_aRequests)
      assertFalse (sRequest, sRequest.contains (BDXR2ClientReadOnly.PATH_OASIS_BDXR_SMP_2));
  }
}
