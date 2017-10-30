/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.base64.Base64;
import com.helger.commons.exception.InitializationException;
import com.helger.commons.io.file.SimpleFileIO;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.security.certificate.CertificateHelper;
import com.helger.settings.exchange.configfile.ConfigFile;
import com.helger.settings.exchange.configfile.ConfigFileBuilder;

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
  private static final Logger s_aLogger = LoggerFactory.getLogger (MockSMPClientConfig.class);
  private static final ConfigFile s_aConfig = new ConfigFileBuilder ().addPath ("private-smp-client-test.properties")
                                                                      .addPath ("smp-client-test.properties")
                                                                      .build ();

  // init
  static
  {
    // How to get the Cert String:
    if (false)
      s_aLogger.info (Base64.encodeBytes (SimpleFileIO.getAllFileBytes (new File ("src/test/resources/SMP_PEPPOL_SML_PEPPOL_SERVICE_METADATA_PUBLISHER_TEST_CA.cer"))));

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
  public static final IParticipantIdentifier getParticipantID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (s_aConfig.getAsString ("participantid"));
  }

  @Nonnull
  public static final IDocumentTypeIdentifier getDocumentTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createDocumentTypeIdentifierWithDefaultScheme (s_aConfig.getAsString ("documenttypeid"));
  }

  @Nonnull
  public static final IProcessIdentifier getProcessTypeID ()
  {
    return PeppolIdentifierFactory.INSTANCE.createProcessIdentifierWithDefaultScheme (s_aConfig.getAsString ("processtypeid"));
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
