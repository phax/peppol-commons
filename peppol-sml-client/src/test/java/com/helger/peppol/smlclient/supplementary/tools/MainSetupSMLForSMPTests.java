/*
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppol.smlclient.supplementary.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * This class ensures the SML contains the necessary data for performing the SMP
 * client tests.
 *
 * @author Philip Helger
 */
public final class MainSetupSMLForSMPTests
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainSetupSMLForSMPTests.class);
  private static final ISMLInfo SML_INFO = ESML.DEVELOPMENT_LOCAL;
  private static final String SMP_ID1 = "SMP-ID1";

  public static void main (final String [] args) throws Exception
  {
    final ManageServiceMetadataServiceCaller aSMClient = new ManageServiceMetadataServiceCaller (SML_INFO);
    aSMClient.setSSLSocketFactory (AbstractSMLClientTestCase.createConfiguredSSLSocketFactory (SML_INFO, false));

    final ManageParticipantIdentifierServiceCaller aParticipantClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aParticipantClient.setSSLSocketFactory (AbstractSMLClientTestCase.createConfiguredSSLSocketFactory (SML_INFO, false));

    try
    {
      aSMClient.create (SMP_ID1, "127.0.0.1", "http://localhost");
    }
    catch (final Exception e)
    {
      // ignore
      LOGGER.info (e.getMessage ());
    }

    try
    {
      final IParticipantIdentifier serviceGroupId = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:5798000000001");
      aParticipantClient.create (SMP_ID1, serviceGroupId);
    }
    catch (final Exception e)
    {
      // ignore
      LOGGER.info (e.getMessage ());
    }
  }
}
