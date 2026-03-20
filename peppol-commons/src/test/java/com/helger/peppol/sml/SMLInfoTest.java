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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.base.id.factory.GlobalIDFactory;
import com.helger.base.id.factory.MemoryIntIDFactory;
import com.helger.unittest.support.TestHelper;
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
    SMLInfo si = SMLInfo.builder ()
                        .idNew ()
                        .displayName ("Test 1")
                        .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                        .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                        .urlSuffixManageSMP ("/smp")
                        .urlSuffixManageParticipant ("")
                        .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                        .build ();
    assertEquals ("participant.sml.prod.tech.peppol.org.", si.getDNSZone ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml", si.getManagementServiceURL ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml/smp",
                  si.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.isClientCertificateRequired ());
    XMLTestHelper.testMicroTypeConversion (si);

    // With a trailing slash
    si = SMLInfo.builder ()
                .id ("dummy")
                .displayName ("Test 2")
                .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL () + '/')
                .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                .build ();
    assertEquals ("participant.sml.prod.tech.peppol.org.", si.getDNSZone ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml", si.getManagementServiceURL ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml/manageservicemetadata",
                  si.getManageServiceMetaDataEndpointAddress ().toExternalForm ());
    assertEquals ("https://api.sml.prod.tech.peppol.org/edelivery-sml/manageparticipantidentifier",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.isClientCertificateRequired ());

    TestHelper.testDefaultImplementationWithEqualContentObject (si,
                                                                SMLInfo.builder ()
                                                                       .id ("dummy")
                                                                       .displayName ("Test 2")
                                                                       .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                                                                       .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                                                                       .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                       .build ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                    SMLInfo.builder ()
                                                                           .id ("dummy2")
                                                                           .displayName ("Test 2")
                                                                           .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                                                                           .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                                                                           .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                           .build ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                    SMLInfo.builder ()
                                                                           .id ("dummy")
                                                                           .displayName ("Test 3")
                                                                           .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                                                                           .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                                                                           .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                           .build ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                    SMLInfo.builder ()
                                                                           .id ("dummy")
                                                                           .displayName ("Test 2")
                                                                           .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone () +
                                                                                     "x")
                                                                           .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                                                                           .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                           .build ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                    SMLInfo.builder ()
                                                                           .id ("dummy")
                                                                           .displayName ("Test 2")
                                                                           .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                                                                           .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL () +
                                                                                                  "x")
                                                                           .clientCertificateRequired (ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                           .build ());
    TestHelper.testDefaultImplementationWithDifferentContentObject (si,
                                                                    SMLInfo.builder ()
                                                                           .id ("dummy")
                                                                           .displayName ("Test 2")
                                                                           .dnsZone (ESML.PEPPOL_PRODUCTION.getDNSZone ())
                                                                           .managementServiceURL (ESML.PEPPOL_PRODUCTION.getManagementServiceURL ())
                                                                           .clientCertificateRequired (!ESML.PEPPOL_PRODUCTION.isClientCertificateRequired ())
                                                                           .build ());
    XMLTestHelper.testMicroTypeConversion (si);

    // With empty URL suffixes
    si = SMLInfo.builder ()
                .id ("dummy")
                .displayName ("Test 2")
                .dnsZone ("a.b.c.d")
                .managementServiceURL ("https://demo.xyz.test/")
                .urlSuffixManageParticipant ("")
                .urlSuffixManageSMP ("")
                .clientCertificateRequired (false)
                .build ();
    // System.out.println (MicroWriter.getNodeAsString (MicroTypeConverter.convertToMicroElement
    // (si, "Test")));
    XMLTestHelper.testMicroTypeConversion (si);
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // ID may not be empty
      SMLInfo.builder ().id ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Display name may not be empty
      SMLInfo.builder ().displayName ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // DNS name may not be empty
      SMLInfo.builder ().dnsZone ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Service URL may not be empty
      SMLInfo.builder ().managementServiceURL ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
