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

import org.junit.Test;

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.bdxr.smp1.CBDXR1Identifier;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link SimpleIdentifierFactory}.
 *
 * @author Philip Helger
 */
public final class SimpleIdentifierFactoryTest
{
  @Test
  public void testDocTypeIDCreation ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    assertNotNull (aIF.createDocumentTypeIdentifier (null, null));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, "any"));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, ""));
    assertNotNull (aIF.createDocumentTypeIdentifier ("", ""));
    assertNotNull (aIF.createDocumentTypeIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testProcessIDCreation ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    assertNotNull (aIF.createProcessIdentifier (null, null));
    assertNotNull (aIF.createProcessIdentifier (null, "any"));
    assertNotNull (aIF.createProcessIdentifier (null, ""));
    assertNotNull (aIF.createProcessIdentifier ("", ""));
    assertNotNull (aIF.createProcessIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testParticipantIDCreation ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    assertNotNull (aIF.createParticipantIdentifier (null, null));
    assertNotNull (aIF.createParticipantIdentifier (null, "any"));
    assertNotNull (aIF.createParticipantIdentifier (null, ""));
    assertNotNull (aIF.createParticipantIdentifier ("", ""));
    assertNotNull (aIF.createParticipantIdentifier ("bla::#:///:::#", null));
  }

  @Test
  public void testParticipantID ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    String sScheme = PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME;
    IParticipantIdentifier aID1 = aIF.createParticipantIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower case internally
    IParticipantIdentifier aID2 = aIF.createParticipantIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));

    // different scheme
    sScheme = "cs-actorid-upis";
    aID1 = aIF.createParticipantIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower case internally
    aID2 = aIF.createParticipantIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }

  @Test
  public void testDocTypeID ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    String sScheme = CBDXR1Identifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
    IDocumentTypeIdentifier aID1 = aIF.createDocumentTypeIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower case internally
    IDocumentTypeIdentifier aID2 = aIF.createDocumentTypeIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));

    // Different scheme
    sScheme = "cs-doctype-scheme";
    aID1 = aIF.createDocumentTypeIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower case internally
    aID2 = aIF.createDocumentTypeIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }

  @Test
  public void testProcessIDCaseInsensitive ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    // Default scheme - handle case insensitive
    final String sScheme = CBDXR1Identifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
    final IProcessIdentifier aID1 = aIF.createProcessIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is lower case internally
    final IProcessIdentifier aID2 = aIF.createProcessIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }

  @Test
  public void testProcessIDCaseSensitive ()
  {
    final SimpleIdentifierFactory aIF = SimpleIdentifierFactory.INSTANCE;
    // Different scheme - handle case sensitive
    final String sScheme = "cs-docid-qns";
    final IProcessIdentifier aID1 = aIF.createProcessIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    // Value is NOT lower case internally
    final IProcessIdentifier aID2 = aIF.createProcessIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("ABC", aID2.getValue ());
    assertFalse (aID1.hasSameContent (aID2));
  }
}
