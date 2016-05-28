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
package com.helger.peppol.smlclient.supplementary.tools;

import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
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
      System.out.println (e.getMessage ());
    }

    try
    {
      final PeppolParticipantIdentifier serviceGroupId = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:5798000000001");
      aParticipantClient.create (SMP_ID1, serviceGroupId);
    }
    catch (final Exception e)
    {
      // ignore
      System.out.println (e.getMessage ());
    }
  }
}
