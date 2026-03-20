/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppol.sml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link ESML}.
 *
 * @author Philip Helger
 */
public final class ESMLTest
{
  @Test
  public void testProduction ()
  {
    for (final ESML eSML : ESML.values ())
    {
      assertNotNull (eSML.getDNSZone ());
      assertNotNull (eSML.getManagementServiceURL ());
      assertNotNull (eSML.getManageServiceMetaDataEndpointAddress ());
      assertNotNull (eSML.getManageParticipantIdentifierEndpointAddress ());
      if (eSML != ESML.DEVELOPMENT_LOCAL)
        assertTrue (eSML.isClientCertificateRequired ());
      assertSame (eSML, ESML.valueOf (eSML.name ()));
    }
  }

  @Test
  public void testNewProductionValues ()
  {
    assertEquals ("participant.sml.prod.tech.peppol.org.", ESML.PEPPOL_PRODUCTION.getDNSZone ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml",
                  ESML.PEPPOL_PRODUCTION.getManagementServiceURL ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml/manageservicemetadata",
                  ESML.PEPPOL_PRODUCTION.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml/manageparticipantidentifier",
                  ESML.PEPPOL_PRODUCTION.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());

    assertEquals ("participant.sml.test.tech.peppol.org.", ESML.PEPPOL_TEST.getDNSZone ());
    assertEquals ("https://api.sml.test.tech.peppol.org/edelivery-sml", ESML.PEPPOL_TEST.getManagementServiceURL ());
    assertEquals ("https://api.sml.test.tech.peppol.org/edelivery-sml/manageservicemetadata",
                  ESML.PEPPOL_TEST.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://api.sml.test.tech.peppol.org/edelivery-sml/manageparticipantidentifier",
                  ESML.PEPPOL_TEST.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
  }
}
