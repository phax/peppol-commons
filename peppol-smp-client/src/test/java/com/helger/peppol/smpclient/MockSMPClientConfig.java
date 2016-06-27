/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smpclient;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import com.helger.commons.base64.Base64;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.io.file.SimpleFileIO;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;
import com.helger.peppol.utils.CertificateHelper;
import com.helger.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.settings.exchange.configfile.ConfigFile;
import com.helger.settings.exchange.configfile.ConfigFileBuilder;
import com.helger.web.http.basicauth.BasicAuthClientCredentials;

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
  private static final ConfigFile s_aConfig = new ConfigFileBuilder ().addPath ("private-smp-client-test.properties")
                                                                      .addPath ("smp-client-test.properties")
                                                                      .build ();

  // init
  static
  {
    // How to get the Cert String:
    if (false)
      System.out.println (Base64.encodeBytes (SimpleFileIO.getAllFileBytes (new File ("src/test/resources/SMP_PEPPOL_SML_PEPPOL_SERVICE_METADATA_PUBLISHER_TEST_CA.cer"))));

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

    // Apply system properties
    s_aConfig.applyAllNetworkSystemProperties ();
  }

  private MockSMPClientConfig ()
  {}

  @Nullable
  public static String getSMPUserName ()
  {
    return s_aConfig.getAsString ("smp.username");
  }

  @Nullable
  public static String getSMPPassword ()
  {
    return s_aConfig.getAsString ("smp.password");
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
      return new URI (s_aConfig.getAsString ("smp.uri"));
    }
    catch (final URISyntaxException ex)
    {
      throw new IllegalStateException (ex);
    }
  }

  @Nonnull
  public static final PeppolParticipantIdentifier getParticipantID ()
  {
    return PeppolParticipantIdentifier.createWithDefaultScheme (s_aConfig.getAsString ("participantid"));
  }

  @Nonnull
  public static final PeppolDocumentTypeIdentifier getDocumentTypeID ()
  {
    return PeppolDocumentTypeIdentifier.createWithDefaultScheme (s_aConfig.getAsString ("documenttypeid"));
  }

  @Nonnull
  public static final PeppolProcessIdentifier getProcessTypeID ()
  {
    return PeppolProcessIdentifier.createWithDefaultScheme (s_aConfig.getAsString ("processtypeid"));
  }

  @Nonnull
  public static final W3CEndpointReference getAPEndpointRef ()
  {
    return W3CEndpointReferenceHelper.createEndpointReference (s_aConfig.getAsString ("ap.uri"));
  }

  @Nullable
  public static String getAPCert ()
  {
    return s_aConfig.getAsString ("ap.cert");
  }

  @Nullable
  public static String getAPServiceDescription ()
  {
    return s_aConfig.getAsString ("ap.servicedescription");
  }

  @Nullable
  public static String getAPContact ()
  {
    return s_aConfig.getAsString ("ap.contact");
  }

  @Nullable
  public static String getAPInfo ()
  {
    return s_aConfig.getAsString ("ap.info");
  }
}
