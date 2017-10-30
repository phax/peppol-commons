/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smlclient.supplementary.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;

/**
 * This class ensures the SML contains the necessary data for performing the SMP
 * client tests.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class MainSetupSMLForSMPTests
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainSetupSMLForSMPTests.class);
  private static final ISMLInfo SML_INFO = ESML.DEVELOPMENT_LOCAL;
  private static final String SMP_ID1 = "SMP-ID1";

  public static void main (final String [] args) throws Exception
  {
    final ManageServiceMetadataServiceCaller aSMClient = new ManageServiceMetadataServiceCaller (SML_INFO);
    aSMClient.setSSLSocketFactory (AbstractSMLClientTestCase.createConfiguredSSLSocketFactory (SML_INFO));

    final ManageParticipantIdentifierServiceCaller aParticipantClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aParticipantClient.setSSLSocketFactory (AbstractSMLClientTestCase.createConfiguredSSLSocketFactory (SML_INFO));

    try
    {
      aSMClient.create (SMP_ID1, "127.0.0.1", "http://localhost");
    }
    catch (final Exception e)
    {
      // ignore
      s_aLogger.info (e.getMessage ());
    }

    try
    {
      final IParticipantIdentifier serviceGroupId = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:5798000000001");
      aParticipantClient.create (SMP_ID1, serviceGroupId);
    }
    catch (final Exception e)
    {
      // ignore
      s_aLogger.info (e.getMessage ());
    }
  }
}
