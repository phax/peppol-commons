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
package com.helger.smpclient.peppol;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.Immutable;
import com.helger.base.codec.base64.Base64;
import com.helger.base.exception.InitializationException;
import com.helger.config.IConfig;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.io.file.SimpleFileIO;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.peppol.utils.W3CEndpointReferenceHelper;

import jakarta.annotation.Nullable;
import jakarta.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * This class manages the special test configuration file for this project. The configuration file
 * is located in <code>src/test/resources/smp-client-test.properties</code>
 *
 * @author Philip Helger
 */
@Immutable
public final class MockSMPClientConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MockSMPClientConfig.class);

  // init
  static
  {
    // How to get the Cert String:
    if (false)
      LOGGER.info (Base64.encodeBytes (SimpleFileIO.getAllFileBytes (new File ("src/test/resources/SMP_PEPPOL_SML_PEPPOL_SERVICE_METADATA_PUBLISHER_TEST_CA.cer"))));

    // Check if the certificate string is correct
    try
    {
      if (CertificateHelper.convertStringToCertficate (getAPCert ()) == null)
        throw new InitializationException ("Failed to convert certificate string from config file to a certificate!");
    }
    catch (final CertificateException ex)
    {
      throw new InitializationException ("Failed to convert certificate string from config file to a certificate!", ex);
    }
  }

  private MockSMPClientConfig ()
  {}

  @NonNull
  private static IConfig _getConfig ()
  {
    return SMPClientConfiguration.getConfig ();
  }

  @Nullable
  public static String getSMPUserName ()
  {
    return _getConfig ().getAsString ("smp.username");
  }

  @Nullable
  public static String getSMPPassword ()
  {
    return _getConfig ().getAsString ("smp.password");
  }

  @NonNull
  public static BasicAuthClientCredentials getSMPCredentials ()
  {
    return new BasicAuthClientCredentials (getSMPUserName (), getSMPPassword ());
  }

  @NonNull
  public static URI getSMPURI ()
  {
    try
    {
      return new URI (_getConfig ().getAsString ("smp.uri"));
    }
    catch (final URISyntaxException ex)
    {
      throw new IllegalStateException (ex);
    }
  }

  @NonNull
  public static IParticipantIdentifier getParticipantID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (_getConfig ().getAsString ("participantid"));
  }

  @NonNull
  public static IDocumentTypeIdentifier getDocumentTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme (_getConfig ().getAsString ("documenttypeid"));
  }

  @NonNull
  public static IProcessIdentifier getProcessTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme (_getConfig ().getAsString ("processtypeid"));
  }

  @NonNull
  public static W3CEndpointReference getAPEndpointRef ()
  {
    return W3CEndpointReferenceHelper.createEndpointReference (_getConfig ().getAsString ("ap.uri"));
  }

  @Nullable
  public static String getAPCert ()
  {
    return _getConfig ().getAsString ("ap.cert");
  }

  @Nullable
  public static String getAPServiceDescription ()
  {
    return _getConfig ().getAsString ("ap.servicedescription");
  }

  @Nullable
  public static String getAPContact ()
  {
    return _getConfig ().getAsString ("ap.contact");
  }

  @Nullable
  public static String getAPInfo ()
  {
    return _getConfig ().getAsString ("ap.info");
  }
}
