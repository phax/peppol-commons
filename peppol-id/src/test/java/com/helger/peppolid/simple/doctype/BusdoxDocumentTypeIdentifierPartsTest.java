/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid.simple.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link BusdoxDocumentTypeIdentifierParts}.
 *
 * @author Philip Helger
 */
public final class BusdoxDocumentTypeIdentifierPartsTest
{
  @Test
  public void testExtract ()
  {
    IBusdoxDocumentTypeIdentifierParts aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("urn:www.peppol.eu:schema:xsd:CatalogueTemplate-1::CatalogueTemplate##urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0::0.1");
    assertNotNull (aParts);

    // With sub-type
    aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("root::local##subtype");
    assertNotNull (aParts);
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertEquals ("subtype", aParts.getSubTypeIdentifier ());
    assertEquals ("root::local##subtype", aParts.getAsDocumentTypeIdentifierValue ());

    // Without sub-type
    aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("root::local");
    assertNotNull (aParts);
    assertEquals ("root", aParts.getRootNS ());
    assertEquals ("local", aParts.getLocalName ());
    assertNull (aParts.getSubTypeIdentifier ());
    assertEquals ("root::local", aParts.getAsDocumentTypeIdentifierValue ());

    // Test equals/hashCode/toString
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aParts,
                                                                       new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                              "local",
                                                                                                              null));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                           new BusdoxDocumentTypeIdentifierParts ("root2",
                                                                                                                  "local",
                                                                                                                  null));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                           new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                                  "local2",
                                                                                                                  null));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aParts,
                                                                           new BusdoxDocumentTypeIdentifierParts ("root",
                                                                                                                  "local",
                                                                                                                  "subtype"));
  }

  @Test
  public void testFrenchPoc ()
  {
    final IBusdoxDocumentTypeIdentifierParts aParts = BusdoxDocumentTypeIdentifierParts.extractFromString ("urn:oasis:names:specification:ubl:schema:xsd:AttachedDocument-2" +
                                                                                                           "::AttachedDocument" +
                                                                                                           "##urn:AIFE.fr::2023#urn:fdc:peppol.eu:2017:poac:UBL:1.0" +
                                                                                                           "::2.1");
    assertNotNull (aParts);
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:AttachedDocument-2", aParts.getRootNS ());
    assertEquals ("AttachedDocument", aParts.getLocalName ());
    assertEquals ("urn:AIFE.fr::2023#urn:fdc:peppol.eu:2017:poac:UBL:1.0::2.1", aParts.getSubTypeIdentifier ());
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
