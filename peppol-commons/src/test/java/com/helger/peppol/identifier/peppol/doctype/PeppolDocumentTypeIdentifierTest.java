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
package com.helger.peppol.identifier.peppol.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.factory.IIdentifierFactory;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.generic.doctype.IBusdoxDocumentTypeIdentifierParts;
import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.xml.mock.XMLTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolDocumentTypeIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolDocumentTypeIdentifierTest
{
  @Test
  public void testHasDefaultDocumentTypeIdentifierScheme ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    assertTrue (aIF.createDocumentTypeIdentifierWithDefaultScheme ("abc")
                   .hasScheme (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME));
    assertFalse (new PeppolDocumentTypeIdentifier ("doctype", "abc").hasDefaultScheme ());
  }

  @Test
  public void testIsValidDocumentTypeIdentifierValue ()
  {
    assertFalse (IPeppolDocumentTypeIdentifier.isValidValue (null));
    assertFalse (IPeppolDocumentTypeIdentifier.isValidValue (""));

    assertTrue (IPeppolDocumentTypeIdentifier.isValidValue ("invoice"));
    assertTrue (IPeppolDocumentTypeIdentifier.isValidValue ("order "));

    assertTrue (IPeppolDocumentTypeIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                      PeppolIdentifierHelper.MAX_DOCUEMNT_TYPE_VALUE_LENGTH)));
    assertFalse (IPeppolDocumentTypeIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                       PeppolIdentifierHelper.MAX_DOCUEMNT_TYPE_VALUE_LENGTH +
                                                                                            1)));
  }

  @Test
  public void testCtor ()
  {
    final PeppolDocumentTypeIdentifier aID = new PeppolDocumentTypeIdentifier ("scheme", "value");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier (aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final PeppolDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier ("scheme", "value");
    final PeppolDocumentTypeIdentifier aID2 = new PeppolDocumentTypeIdentifier ("scheme", "value");
    final PeppolDocumentTypeIdentifier aID3 = new PeppolDocumentTypeIdentifier ("scheme2", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }

  @Test
  public void testURIStuff ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final IDocumentTypeIdentifier aID1 = new PeppolDocumentTypeIdentifier ("scheme1", "value1");
    assertEquals ("scheme1::value1", aID1.getURIEncoded ());
    assertEquals ("scheme1%3A%3Avalue1", aID1.getURIPercentEncoded ());
    final IDocumentTypeIdentifier aID2 = aIF.parseDocumentTypeIdentifier ("scheme1::value1");
    assertEquals (aID1, aID2);

    assertNull (aIF.parseDocumentTypeIdentifier (null));
    assertNull (aIF.parseDocumentTypeIdentifier (""));
    assertNull (aIF.parseDocumentTypeIdentifier ("scheme1"));

    assertNotNull (aIF.parseDocumentTypeIdentifier ("doctype::invoice"));
    assertNotNull (aIF.parseDocumentTypeIdentifier ("doctype::order "));

    assertNull (aIF.parseDocumentTypeIdentifier ("doctypethatiswaytoolongforwhatisexpected::order"));
    assertNull (aIF.parseDocumentTypeIdentifier ("doctype::" +
                                                 StringHelper.getRepeated ('a',
                                                                           PeppolIdentifierHelper.MAX_DOCUEMNT_TYPE_VALUE_LENGTH +
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
      new PeppolDocumentTypeIdentifier (null, "value");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // null value not allowed
      new PeppolDocumentTypeIdentifier (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Both null not allowed
      new PeppolDocumentTypeIdentifier (null, null);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Empty is not allowed
      new PeppolDocumentTypeIdentifier (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Cannot be mapped to ISO-8859-1:
      new PeppolDocumentTypeIdentifier (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, "Љ");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Scheme too long
      new PeppolDocumentTypeIdentifier (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME +
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
      new PeppolDocumentTypeIdentifier (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME,
                                        StringHelper.getRepeated ('a',
                                                                  PeppolIdentifierHelper.MAX_DOCUEMNT_TYPE_VALUE_LENGTH +
                                                                       1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testValue () throws Exception
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final String documentIdAsText = "urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2::ApplicationResponse##urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0::2.0";
    final IDocumentTypeIdentifier documentTypeIdentifier = aIF.createDocumentTypeIdentifierWithDefaultScheme (documentIdAsText);
    assertEquals (documentTypeIdentifier.getValue (), documentIdAsText);
    assertTrue (documentTypeIdentifier.hasValue (documentIdAsText));
  }

  @Test
  public void testStandardMethods ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
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
      final PeppolDocumentTypeIdentifier aDocTypeID = new PeppolDocumentTypeIdentifier (e);
      final IBusdoxDocumentTypeIdentifierParts aParts = aDocTypeID.getParts ();
      assertNotNull (aParts);
      assertEquals (aDocTypeID.getValue (), aParts.getAsDocumentTypeIdentifierValue ());
    }
  }
}
