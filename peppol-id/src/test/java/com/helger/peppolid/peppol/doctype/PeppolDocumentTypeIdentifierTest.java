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
package com.helger.peppolid.peppol.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link PeppolDocumentTypeIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolDocumentTypeIdentifierTest
{
  private static final IIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    assertTrue (IF.createDocumentTypeIdentifierWithDefaultScheme ("urn:rootnamespace::localelement##customizationid::version")
                  .hasScheme (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS));
    assertFalse (new PeppolDocumentTypeIdentifier (IF,
                                                   "doctype",
                                                   "urn:rootnamespace::localelement##customizationid::version").hasDefaultScheme ());
  }

  @Test
  public void testCtor ()
  {
    final PeppolDocumentTypeIdentifier aID = new PeppolDocumentTypeIdentifier (IF,
                                                                               "scheme",
                                                                               "urn:rootnamespace::localelement##customizationid::version");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("urn:rootnamespace::localelement##customizationid::version", aID.getValue ());

    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier (IF, aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("urn:rootnamespace::localelement##customizationid::version", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier (IF,
                                                                                "scheme",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier (IF,
                                                                                "scheme",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    final PeppolDocumentTypeIdentifier aID3 = new PeppolDocumentTypeIdentifier (IF,
                                                                                "scheme2",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    TestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    TestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    TestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier (IF,
                                                                           "scheme1",
                                                                           "urn:rootnamespace::localelement##customizationid::version");
    assertEquals ("scheme1::urn:rootnamespace::localelement##customizationid::version", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Aurn%3Arootnamespace%3A%3Alocalelement%23%23customizationid%3A%3Aversion",
                  aID1.getURIPercentEncoded ());
    final IDocumentTypeIdentifier aID2 = IF.parseDocumentTypeIdentifier ("scheme1::urn:rootnamespace::localelement##customizationid::version");
    assertEquals (aID1, aID2);

    assertNull (IF.parseDocumentTypeIdentifier (null));
    assertNull (IF.parseDocumentTypeIdentifier (""));
    assertNull (IF.parseDocumentTypeIdentifier ("scheme1"));

    assertNotNull (IF.parseDocumentTypeIdentifier ("doctype::urn:rootnamespace::localelement##customizationid::version"));
    assertNotNull (IF.parseDocumentTypeIdentifier ("doctype::urn:rootnamespace::localelement##customizationid2::version "));

    assertNull (IF.parseDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertNull (IF.parseDocumentTypeIdentifier ("doctype::" +
                                                StringHelper.getRepeated ('a',
                                                                          PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                               1)));
    assertNull (IF.parseDocumentTypeIdentifier ("doctype:order"));
    assertNull (IF.parseDocumentTypeIdentifier ("doctypeorder"));
  }

  @Test
  public void testConstraints ()
  {
    try
    {
      // null key not allowed
      new PeppolDocumentTypeIdentifier (IF, null, "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // invalid scheme
      new PeppolDocumentTypeIdentifier (IF, "", "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolDocumentTypeIdentifier (IF, "abc::def", "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolDocumentTypeIdentifier (IF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolDocumentTypeIdentifier (IF, null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolDocumentTypeIdentifier (IF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolDocumentTypeIdentifier (IF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolDocumentTypeIdentifier (IF,
                                        PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS +
                                            StringHelper.getRepeated ('a',
                                                                      PeppolIdentifierHelper.MAX_IDENTIFIER_SCHEME_LENGTH +
                                                                           1),
                                        "abc");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Value too long
      new PeppolDocumentTypeIdentifier (IF,
                                        PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS,
                                        StringHelper.getRepeated ('a',
                                                                  PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                       1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testValue () throws Exception
  {
    final String documentIdAsText = "urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0";
    final IDocumentTypeIdentifier documentTypeIdentifier = IF.createDocumentTypeIdentifierWithDefaultScheme (documentIdAsText);
    assertEquals (documentTypeIdentifier.getValue (), documentIdAsText);
    assertTrue (documentTypeIdentifier.hasValue (documentIdAsText));
  }

  @Test
  public void testStandardMethods ()
  {
    final String s = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0";
    final String s2 = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::3.0";

    final IDocumentTypeIdentifier d1 = IF.createDocumentTypeIdentifierWithDefaultScheme (s);
    final IDocumentTypeIdentifier d2 = IF.createDocumentTypeIdentifierWithDefaultScheme (s);
    TestHelper.testDefaultImplementationWithEqualContentObject (d1, d2);

    final IDocumentTypeIdentifier d3 = IF.createDocumentTypeIdentifierWithDefaultScheme (s2);
    TestHelper.testDefaultImplementationWithDifferentContentObject (d1, d3);
  }

  @Test
  public void testGetParts ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      final PeppolDocumentTypeIdentifier aDocTypeID = new PeppolDocumentTypeIdentifier (IF, e);
      final IPeppolGenericDocumentTypeIdentifierParts aParts = aDocTypeID.getParts ();
      assertNotNull (aParts);
      assertEquals (aDocTypeID.getValue (), aParts.getAsDocumentTypeIdentifierValue ());
    }
  }
}
