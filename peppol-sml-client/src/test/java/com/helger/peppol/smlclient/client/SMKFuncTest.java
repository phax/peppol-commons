/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertSame;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.annotation.Nonempty;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;
import com.helger.peppol.utils.PeppolTechnicalSetup;

/**
 * @author Philip Helger
 */
@Ignore ("Requires a running SML and a configured certificate")
public final class SMKFuncTest extends AbstractSMLClientTestCase
{
  private static final String L_ENDPOINTADDRESS = "http://test-smp.example.org";
  private static final String P_ENDPOINTADDRESS = "127.0.0.1";
  private static final String SMP_ID = "SMP-TEST-ID-PH";

  static
  {
    assertSame (SML_INFO, ESML.DIGIT_TEST);
  }

  @Nonnull
  private static ServiceMetadataPublisherServiceType _createSMPData (@Nonnull final ManageServiceMetadataServiceCaller aSMLSMPClient,
                                                                     @Nonnull @Nonempty final String sSMPID) throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataCreate = new ServiceMetadataPublisherServiceType ();
    aServiceMetadataCreate.setServiceMetadataPublisherID (sSMPID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (L_ENDPOINTADDRESS);
    aEndpoint.setPhysicalAddress (P_ENDPOINTADDRESS);
    aServiceMetadataCreate.setPublisherEndpoint (aEndpoint);

    aSMLSMPClient.create (aServiceMetadataCreate);
    return aServiceMetadataCreate;
  }

  @Test
  public void testCreateAndDeletePublisher () throws Exception
  {
    PeppolTechnicalSetup.enableSoapLogging (true);
    try
    {
      // Create client
      final ManageServiceMetadataServiceCaller m_aSMClient = new ManageServiceMetadataServiceCaller (SML_INFO);
      m_aSMClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO));

      // Create and delete SMP - with network logging
      _createSMPData (m_aSMClient, SMP_ID);
      try
      {}
      finally
      {
        m_aSMClient.delete (SMP_ID);
      }
    }
    finally
    {
      PeppolTechnicalSetup.enableSoapLogging (false);
    }
  }
}
