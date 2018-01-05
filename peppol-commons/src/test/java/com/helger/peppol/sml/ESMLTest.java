/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.sml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Test class for class {@link ESML}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class ESMLTest
{
  @Test
  public void testProduction ()
  {
    for (final ESML eSML : ESML.values ())
    {
      assertNotNull (eSML.getDNSZone ());
      assertNotNull (eSML.getPublisherDNSZone ());
      assertNotNull (eSML.getManagementServiceURL ());
      assertNotNull (eSML.getManageServiceMetaDataEndpointAddress ());
      assertNotNull (eSML.getManageParticipantIdentifierEndpointAddress ());
      eSML.isClientCertificateRequired ();
      assertSame (eSML, ESML.valueOf (eSML.name ()));
    }
  }

  @Test
  public void testNewProductionValues ()
  {
    assertEquals ("edelivery.tech.ec.europa.eu.", ESML.DIGIT_PRODUCTION.getDNSZone ());
    assertEquals ("publisher.edelivery.tech.ec.europa.eu.", ESML.DIGIT_PRODUCTION.getPublisherDNSZone ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml", ESML.DIGIT_PRODUCTION.getManagementServiceURL ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageservicemetadata",
                  ESML.DIGIT_PRODUCTION.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageparticipantidentifier",
                  ESML.DIGIT_PRODUCTION.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());

    assertEquals ("acc.edelivery.tech.ec.europa.eu.", ESML.DIGIT_TEST.getDNSZone ());
    assertEquals ("publisher.acc.edelivery.tech.ec.europa.eu.", ESML.DIGIT_TEST.getPublisherDNSZone ());
    assertEquals ("https://acc.edelivery.tech.ec.europa.eu/edelivery-sml", ESML.DIGIT_TEST.getManagementServiceURL ());
    assertEquals ("https://acc.edelivery.tech.ec.europa.eu/edelivery-sml/manageservicemetadata",
                  ESML.DIGIT_TEST.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://acc.edelivery.tech.ec.europa.eu/edelivery-sml/manageparticipantidentifier",
                  ESML.DIGIT_TEST.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
  }
}
