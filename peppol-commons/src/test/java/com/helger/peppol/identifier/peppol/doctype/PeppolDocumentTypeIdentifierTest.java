/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
      final IPeppolDocumentTypeIdentifierParts aParts = aDocTypeID.getParts ();
      assertNotNull (aParts);
      assertEquals (aDocTypeID.getValue (), aParts.getAsDocumentTypeIdentifierValue ());
    }
  }
}
