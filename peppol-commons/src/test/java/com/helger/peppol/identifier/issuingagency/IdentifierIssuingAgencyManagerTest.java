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
package com.helger.peppol.identifier.issuingagency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.state.ETriState;

/**
 * Test class for class {@link IdentifierIssuingAgencyManager}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierIssuingAgencyManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (IdentifierIssuingAgencyManager.getAllAgencies ());
    assertEquals (66, IdentifierIssuingAgencyManager.getAllAgencies ().size ());

    // test valid
    assertNotNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code ("0088"));
    assertTrue (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code ("0088"));
    assertEquals ("GLN", IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code ("0088"));
    assertNotNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID ("GLN"));
    assertTrue (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID ("GLN"));
    assertEquals ("0088", IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID ("GLN"));

    // test invalid
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code ("1024"));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code ("1024"));
    assertNull (IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code ("1024"));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfISO6523Code (null));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithISO6523Code (null));
    assertNull (IdentifierIssuingAgencyManager.getSchemeIDOfISO6523Code (null));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID ("XYZ"));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID ("XYZ"));
    assertNull (IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID ("XYZ"));
    assertNull (IdentifierIssuingAgencyManager.getAgencyOfSchemeID (null));
    assertFalse (IdentifierIssuingAgencyManager.containsAgencyWithSchemeID (null));
    assertNull (IdentifierIssuingAgencyManager.getISO6523CodeOfSchemeID (null));

    // Test deprecated
    assertEquals (ETriState.TRUE, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("9916"));
    assertEquals (ETriState.TRUE, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("AT:CID"));
    assertEquals (ETriState.FALSE, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("9914"));
    assertEquals (ETriState.FALSE, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("AT:VAT"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated ("abcd"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated ("XYZ"));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithISO6523CodeDeprecated (null));
    assertEquals (ETriState.UNDEFINED, IdentifierIssuingAgencyManager.isAgencyWithSchemeIDDeprecated (null));
  }
}
