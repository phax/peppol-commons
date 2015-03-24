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
package com.helger.peppol.identifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.PHTestUtils;
import com.helger.peppol.identifier.BusdoxDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.IBusdoxDocumentTypeIdentifierParts;

/**
 * Test class for class {@link BusdoxDocumentTypeIdentifierParts}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class BusdoxDocumentTypeIdentifierPartsTest
{
  @Test
  public void testExtract ()
  {
    IBusdoxDocumentTypeIdentifierParts aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1");

    // With sub-type
    aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("root::local##subtype");
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertEquals ("subtype", aParts.getSubTypeIdentifier ());
    assertEquals ("root::local##subtype", aParts.getAsDocumentTypeIdentifierValue ());

    // Without sub-type
    aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("root::local");
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertNull (aParts.getSubTypeIdentifier ());
    assertEquals ("root::local", aParts.getAsDocumentTypeIdentifierValue ());

    // Test equals/hashCode/toString
    PHTestUtils.testDefaultImplementationWithEqualContentObject (aParts,
                                                                 new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                        "local",
                                                                                                        null));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                     new BusdoxDocumentTypeIdentifierParts ("root2",
                                                                                                            "local",
                                                                                                            null));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                     new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                            "local2",
                                                                                                            null));
    PHTestUtils.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                     new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                            "local",
                                                                                                            "subtype"));
  }

  @Test
  public void testInvalid ()
  {
    try
    {
      // Empty namespace not allowed
      new BusdoxDocumentTypeIdentifierParts ("", "local", null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty local name not allowed
      new BusdoxDocumentTypeIdentifierParts ("rootns", "", null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null String is not allowed
      BusdoxDocumentTypeIdentifierParts.extractFromString (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    try
    {
      // Empty String is not allowed
      BusdoxDocumentTypeIdentifierParts.extractFromString ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // No local name present
      BusdoxDocumentTypeIdentifierParts.extractFromString ("root#subtype");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null not allowed
      BusdoxDocumentTypeIdentifierParts.getAsDocumentTypeIdentifierValue (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
  }
}
