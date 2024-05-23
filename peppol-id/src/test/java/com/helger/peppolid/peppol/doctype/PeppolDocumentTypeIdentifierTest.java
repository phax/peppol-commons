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
package com.helger.peppolid.peppol.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.simple.doctype.IBusdoxDocumentTypeIdentifierParts;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolDocumentTypeIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolDocumentTypeIdentifierTest
{
  private static final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;

  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    assertTrue (aIF.createDocumentTypeIdentifierWithDefaultScheme ("urn:rootnamespace::localelement##customizationid::version")
                   .hasScheme (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS));
    assertFalse (new PeppolDocumentTypeIdentifier (aIF,
                                                   "doctype",
                                                   "urn:rootnamespace::localelement##customizationid::version").hasDefaultScheme ());
  }

  @Test
  public void testCtor ()
  {
    final PeppolDocumentTypeIdentifier aID = new PeppolDocumentTypeIdentifier (aIF,
                                                                               "scheme",
                                                                               "urn:rootnamespace::localelement##customizationid::version");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("urn:rootnamespace::localelement##customizationid::version", aID.getValue ());

    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier (aIF, aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("urn:rootnamespace::localelement##customizationid::version", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier (aIF,
                                                                                "scheme",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier (aIF,
                                                                                "scheme",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    final PeppolDocumentTypeIdentifier aID3 = new PeppolDocumentTypeIdentifier (aIF,
                                                                                "scheme2",
                                                                                "urn:rootnamespace::localelement##customizationid::version");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier (aIF,
                                                                           "scheme1",
                                                                           "urn:rootnamespace::localelement##customizationid::version");
    assertEquals ("scheme1::urn:rootnamespace::localelement##customizationid::version", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Aurn%3Arootnamespace%3A%3Alocalelement%23%23customizationid%3A%3Aversion",
                  aID1.getURIPercentEncoded ());
    final IDocumentTypeIdentifier aID2 = aIF.parseDocumentTypeIdentifier ("scheme1::urn:rootnamespace::localelement##customizationid::version");
    assertEquals (aID1, aID2);

    assertNull (aIF.parseDocumentTypeIdentifier (null));
    assertNull (aIF.parseDocumentTypeIdentifier (""));
    assertNull (aIF.parseDocumentTypeIdentifier ("scheme1"));

    assertNotNull (aIF.parseDocumentTypeIdentifier ("doctype::urn:rootnamespace::localelement##customizationid::version"));
    assertNotNull (aIF.parseDocumentTypeIdentifier ("doctype::urn:rootnamespace::localelement##customizationid2::version "));

    assertNull (aIF.parseDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertNull (aIF.parseDocumentTypeIdentifier ("doctype::" +
                                                 StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_DOCUMENT_TYPE_VALUE_LENGTH +
                                                                                1)));
    assertNull (aIF.parseDocumentTypeIdentifier ("doctype:order"));
    assertNull (aIF.parseDocumentTypeIdentifier ("doctypeorder"));
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void testConstraints ()
  {
    try
    {
      // null key not allowed
      new PeppolDocumentTypeIdentifier (aIF, null, "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // invalid scheme
      new PeppolDocumentTypeIdentifier (aIF, "", "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // separator is forbidden
      new PeppolDocumentTypeIdentifier (aIF, "abc::def", "urn:rootnamespace::localelement##customizationid::version");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolDocumentTypeIdentifier (aIF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolDocumentTypeIdentifier (aIF, null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolDocumentTypeIdentifier (aIF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolDocumentTypeIdentifier (aIF, PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolDocumentTypeIdentifier (aIF,
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
      new PeppolDocumentTypeIdentifier (aIF,
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
    final IDocumentTypeIdentifier documentTypeIdentifier = aIF.createDocumentTypeIdentifierWithDefaultScheme (documentIdAsText);
    assertEquals (documentTypeIdentifier.getValue (), documentIdAsText);
    assertTrue (documentTypeIdentifier.hasValue (documentIdAsText));
  }

  @Test
  public void testStandardMethods ()
  {
    final String s = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::2.0";
    final String s2 = "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1::3.0";

    final IDocumentTypeIdentifier d1 = aIF.createDocumentTypeIdentifierWithDefaultScheme (s);
    final IDocumentTypeIdentifier d2 = aIF.createDocumentTypeIdentifierWithDefaultScheme (s);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (d1, d2);

    final IDocumentTypeIdentifier d3 = aIF.createDocumentTypeIdentifierWithDefaultScheme (s2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (d1, d3);
  }

  @Test
  public void testGetParts ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      final PeppolDocumentTypeIdentifier aDocTypeID = new PeppolDocumentTypeIdentifier (aIF, e);
      final IBusdoxDocumentTypeIdentifierParts aParts = aDocTypeID.getParts ();
      assertNotNull (aParts);
      assertEquals (aDocTypeID.getValue (), aParts.getAsDocumentTypeIdentifierValue ());
    }
  }
}
