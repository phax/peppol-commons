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
package com.helger.peppol.sml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.PHTestUtils;

/**
 * Test class for class {@link SimpleSMLInfo}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class SimpleSMLInfoTest
{
  @Test
  public void testAll ()
  {
    SimpleSMLInfo si = new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                                          ESML.PRODUCTION.getManagementHostName (),
                                          ESML.PRODUCTION.getManagementServiceURL (),
                                          ESML.PRODUCTION.requiresClientCertificate ());

    assertEquals ("sml.peppolcentral.org.", si.getDNSZone ());
    assertEquals ("publisher.sml.peppolcentral.org.", si.getPublisherDNSName ());
    assertEquals ("https://sml.peppolcentral.org", si.getManagementHostName ());
    assertEquals ("https://sml.peppolcentral.org", si.getManagementServiceURL ());
    assertEquals ("https://sml.peppolcentral.org/manageservicemetadata", si.getManageServiceMetaDataEndpointAddress ()
                                                                           .toExternalForm ());
    assertEquals ("https://sml.peppolcentral.org/manageparticipantidentifier",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.requiresClientCertificate ());

    si = new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                            ESML.PRODUCTION.getManagementHostName (),
                            ESML.PRODUCTION.getManagementServiceURL () + '/',
                            ESML.PRODUCTION.requiresClientCertificate ());
    assertEquals ("sml.peppolcentral.org.", si.getDNSZone ());
    assertEquals ("publisher.sml.peppolcentral.org.", si.getPublisherDNSName ());
    assertEquals ("https://sml.peppolcentral.org", si.getManagementHostName ());
    assertEquals ("https://sml.peppolcentral.org", si.getManagementServiceURL ());
    assertEquals ("https://sml.peppolcentral.org/manageservicemetadata", si.getManageServiceMetaDataEndpointAddress ()
                                                                           .toExternalForm ());
    assertEquals ("https://sml.peppolcentral.org/manageparticipantidentifier",
                  si.getManageParticipantIdentifierEndpointAddress ().toExternalForm ());
    assertTrue (si.requiresClientCertificate ());

    PHTestUtils.testDefaultImplementationWithEqualContentObject (si,
                                                                 new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                                                                                    ESML.PRODUCTION.getManagementHostName (),
                                                                                    ESML.PRODUCTION.getManagementServiceURL (),
                                                                                    ESML.PRODUCTION.requiresClientCertificate ()));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (si,
                                                                     new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone () +
                                                                                            ".x",
                                                                                        ESML.PRODUCTION.getManagementHostName (),
                                                                                        ESML.PRODUCTION.getManagementServiceURL (),
                                                                                        ESML.PRODUCTION.requiresClientCertificate ()));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (si,
                                                                     new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                                                                                        ESML.PRODUCTION.getManagementHostName () +
                                                                                            ".x",
                                                                                        ESML.PRODUCTION.getManagementServiceURL (),
                                                                                        ESML.PRODUCTION.requiresClientCertificate ()));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (si,
                                                                     new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                                                                                        ESML.PRODUCTION.getManagementHostName (),
                                                                                        ESML.PRODUCTION.getManagementServiceURL () +
                                                                                            ".x",
                                                                                        ESML.PRODUCTION.requiresClientCertificate ()));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (si,
                                                                     new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (),
                                                                                        ESML.PRODUCTION.getManagementHostName (),
                                                                                        ESML.PRODUCTION.getManagementServiceURL (),
                                                                                        !ESML.PRODUCTION.requiresClientCertificate ()));
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // DNS name may not be empty
      new SimpleSMLInfo ("", ESML.PRODUCTION.getManagementHostName (), ESML.PRODUCTION.getManagementServiceURL (), true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Hostname may not be empty
      new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (), "", ESML.PRODUCTION.getManagementServiceURL (), true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Service URL may not be empty
      new SimpleSMLInfo (ESML.PRODUCTION.getDNSZone (), ESML.PRODUCTION.getManagementHostName (), "", true);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
