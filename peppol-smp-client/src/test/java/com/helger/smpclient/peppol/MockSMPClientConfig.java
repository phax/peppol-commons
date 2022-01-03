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
package com.helger.smpclient.peppol;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.base64.Base64;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.io.file.SimpleFileIO;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.security.certificate.CertificateHelper;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.peppol.utils.W3CEndpointReferenceHelper;

/**
 * This class manages the special test configuration file for this project. The
 * configuration file is located in
 * <code>src/test/resources/smp-client-test.properties</code>
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

  @Nullable
  public static String getSMPUserName ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("smp.username");
  }

  @Nullable
  public static String getSMPPassword ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("smp.password");
  }

  @Nonnull
  public static final BasicAuthClientCredentials getSMPCredentials ()
  {
    return new BasicAuthClientCredentials (getSMPUserName (), getSMPPassword ());
  }

  @Nonnull
  public static URI getSMPURI ()
  {
    try
    {
      return new URI (SMPClientConfiguration.getConfig ().getAsString ("smp.uri"));
    }
    catch (final URISyntaxException ex)
    {
      throw new IllegalStateException (ex);
    }
  }

  @Nonnull
  public static final IParticipantIdentifier getParticipantID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (SMPClientConfiguration.getConfig ()
                                                                                                                .getAsString ("participantid"));
  }

  @Nonnull
  public static final IDocumentTypeIdentifier getDocumentTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme (SMPClientConfiguration.getConfig ()
                                                                                                                 .getAsString ("documenttypeid"));
  }

  @Nonnull
  public static final IProcessIdentifier getProcessTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme (SMPClientConfiguration.getConfig ()
                                                                                                            .getAsString ("processtypeid"));
  }

  @Nonnull
  public static final W3CEndpointReference getAPEndpointRef ()
  {
    return W3CEndpointReferenceHelper.createEndpointReference (SMPClientConfiguration.getConfig ().getAsString ("ap.uri"));
  }

  @Nullable
  public static String getAPCert ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("ap.cert");
  }

  @Nullable
  public static String getAPServiceDescription ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("ap.servicedescription");
  }

  @Nullable
  public static String getAPContact ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("ap.contact");
  }

  @Nullable
  public static String getAPInfo ()
  {
    return SMPClientConfiguration.getConfig ().getAsString ("ap.info");
  }
}
