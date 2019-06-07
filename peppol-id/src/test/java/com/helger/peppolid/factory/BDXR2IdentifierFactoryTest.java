/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.bdxr.smp2.CBDXR2Identifier;
import com.helger.peppolid.factory.BDXR2IdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link BDXR2IdentifierFactory}.
 *
 * @author Philip Helger
 */
public final class BDXR2IdentifierFactoryTest
{
  @Test
  public void testDocTypeIDCreation ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    assertNotNull (aIF.createDocumentTypeIdentifier (null, null));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, "any"));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, ""));
    assertNotNull (aIF.createDocumentTypeIdentifier ("", ""));
    assertNull (aIF.createDocumentTypeIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testProcessIDCreation ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    assertNotNull (aIF.createProcessIdentifier (null, null));
    assertNotNull (aIF.createProcessIdentifier (null, "any"));
    assertNotNull (aIF.createProcessIdentifier (null, ""));
    assertNotNull (aIF.createProcessIdentifier ("", ""));
    assertNotNull (aIF.createProcessIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testParticipantIDCreation ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    assertNotNull (aIF.createParticipantIdentifier (null, null));
    assertNotNull (aIF.createParticipantIdentifier (null, "any"));
    assertNotNull (aIF.createParticipantIdentifier (null, ""));
    assertNotNull (aIF.createParticipantIdentifier ("", ""));
    assertNull (aIF.createParticipantIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testParticipantIDCaseInsensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    final String sScheme = PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME;
    final IParticipantIdentifier aID1 = aIF.createParticipantIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is lower cased internally
    final IParticipantIdentifier aID2 = aIF.createParticipantIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("abc", aID2.getValue ());
    assertTrue (aID1.hasSameContent (aID2));
  }

  @Test
  public void testParticipantIDCaseSensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    final String sScheme = "cs-actorid-upis";
    final IParticipantIdentifier aID1 = aIF.createParticipantIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower cased internally
    final IParticipantIdentifier aID2 = aIF.createParticipantIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }

  @Test
  public void testDocTypeIDCaseInsensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    final String sScheme = CBDXR2Identifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
    final IDocumentTypeIdentifier aID1 = aIF.createDocumentTypeIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is lower cased internally
    final IDocumentTypeIdentifier aID2 = aIF.createDocumentTypeIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("abc", aID2.getValue ());
    assertTrue (aID1.hasSameContent (aID2));
  }

  @Test
  public void testDocTypeIDCaseSensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    // Different scheme - handle case sensitive
    final String sScheme = "cs-doctype-scheme";
    final IDocumentTypeIdentifier aID1 = aIF.createDocumentTypeIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower cased internally
    final IDocumentTypeIdentifier aID2 = aIF.createDocumentTypeIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }

  @Test
  public void testProcessIDCaseInsensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    // Default scheme - handle case insensitive
    final String sScheme = CBDXR2Identifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
    final IProcessIdentifier aID1 = aIF.createProcessIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is lower cased internally
    final IProcessIdentifier aID2 = aIF.createProcessIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("abc", aID2.getValue ());
    assertTrue (aID1.hasSameContent (aID2));
  }

  @Test
  public void testProcessIDCaseSensitive ()
  {
    final BDXR2IdentifierFactory aIF = BDXR2IdentifierFactory.INSTANCE;
    // Different scheme - handle case sensitive
    final String sScheme = "cs-docid-qns";
    final IProcessIdentifier aID1 = aIF.createProcessIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower cased internally
    final IProcessIdentifier aID2 = aIF.createProcessIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }
}
