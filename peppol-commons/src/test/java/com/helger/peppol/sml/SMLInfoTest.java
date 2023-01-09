/*
 * Copyright (C) 2015-2023 Philip Helger
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.id.factory.GlobalIDFactory;
import com.helger.commons.id.factory.MemoryIntIDFactory;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SMLInfo}.
 *
 * @author Philip Helger
 */
public final class SMLInfoTest
{
  static
  {
    if (!GlobalIDFactory.hasPersistentIntIDFactory ())
      GlobalIDFactory.setPersistentIntIDFactory (new MemoryIntIDFactory ());
  }

  @Test
  public void testAll ()
  {
    SMLInfo si = new SMLInfo ("Test 1",
                              ESML.DIGIT_PRODUCTION.getDNSZone (),
                              ESML.DIGIT_PRODUCTION.getManagementServiceURL (),
                              ESML.DIGIT_PRODUCTION.isClientCertificateRequired ());

    assertEquals ("edelivery.tech.ec.europa.eu.", si.getDNSZone ());
    assertEquals ("publisher.edelivery.tech.ec.europa.eu.", si.getPublisherDNSZone ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml", si.getManagementServiceURL ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageservicemetadata",
                  si.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageparticipantidentifier",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.isClientCertificateRequired ());
    XMLTestHelper.testMicroTypeConversion (si);

    // With a trailing slash
    si = new SMLInfo ("Test 2",
                      ESML.DIGIT_PRODUCTION.getDNSZone (),
                      ESML.DIGIT_PRODUCTION.getManagementServiceURL () + '/',
                      ESML.DIGIT_PRODUCTION.isClientCertificateRequired ());
    assertEquals ("edelivery.tech.ec.europa.eu.", si.getDNSZone ());
    assertEquals ("publisher.edelivery.tech.ec.europa.eu.", si.getPublisherDNSZone ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml", si.getManagementServiceURL ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageservicemetadata",
                  si.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://edelivery.tech.ec.europa.eu/edelivery-sml/manageparticipantidentifier",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.isClientCertificateRequired ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (si,
                                                                       new SMLInfo ("SML",
                                                                                    ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                                                    ESML.DIGIT_PRODUCTION.getManagementServiceURL (),
                                                                                    ESML.DIGIT_PRODUCTION.isClientCertificateRequired ()));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                           new SMLInfo ("SML",
                                                                                        ESML.DIGIT_PRODUCTION.getDNSZone () + ".x",
                                                                                        ESML.DIGIT_PRODUCTION.getManagementServiceURL (),
                                                                                        ESML.DIGIT_PRODUCTION.isClientCertificateRequired ()));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                           new SMLInfo ("SML",
                                                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                                                        ESML.DIGIT_PRODUCTION.getManagementServiceURL () +
                                                                                                                             ".x",
                                                                                        ESML.DIGIT_PRODUCTION.isClientCertificateRequired ()));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                           new SMLInfo ("SML",
                                                                                        ESML.DIGIT_PRODUCTION.getDNSZone (),
                                                                                        ESML.DIGIT_PRODUCTION.getManagementServiceURL (),
                                                                                        !ESML.DIGIT_PRODUCTION.isClientCertificateRequired ()));

    XMLTestHelper.testMicroTypeConversion (si);
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // Display name may not be empty
      new SMLInfo ("", ESML.DIGIT_PRODUCTION.getDNSZone (), ESML.DIGIT_PRODUCTION.getManagementServiceURL (), true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      // DNS name may not be empty
      new SMLInfo ("Test Name", "", ESML.DIGIT_PRODUCTION.getManagementServiceURL (), true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Service URL may not be empty
      new SMLInfo ("Test Name", ESML.DIGIT_PRODUCTION.getDNSZone (), "", true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
