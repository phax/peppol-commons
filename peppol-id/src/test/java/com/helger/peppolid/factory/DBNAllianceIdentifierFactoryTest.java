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
package com.helger.peppolid.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.bdxr.smp2.CBDXR2Identifier;

/**
 * Test class for class {@link DBNAllianceIdentifierFactory}.
 *
 * @author Philip Helger
 */
public final class DBNAllianceIdentifierFactoryTest
{
  @Test
  public void testDocTypeIDCaseInsensitiveDefaultScheme ()
  {
    final IIdentifierFactory aIF = DBNAllianceIdentifierFactory.INSTANCE;

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
  public void testDocTypeIDCaseInsensitiveCustomScheme ()
  {
    final IIdentifierFactory aIF = DBNAllianceIdentifierFactory.INSTANCE;

    // Unlike BDXR2IdentifierFactory, case insensitivity applies to all schemes
    final String sScheme = "cs-doctype-scheme";
    final IDocumentTypeIdentifier aID1 = aIF.createDocumentTypeIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    final IDocumentTypeIdentifier aID2 = aIF.createDocumentTypeIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("abc", aID2.getValue ());
    assertTrue (aID1.hasSameContent (aID2));
  }

  @Test
  public void testProcessIDCaseInsensitiveDefaultScheme ()
  {
    final IIdentifierFactory aIF = DBNAllianceIdentifierFactory.INSTANCE;

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
  public void testProcessIDCaseInsensitiveCustomScheme ()
  {
    final IIdentifierFactory aIF = DBNAllianceIdentifierFactory.INSTANCE;

    // Unlike BDXR2IdentifierFactory, case insensitivity applies to all schemes
    final String sScheme = "cs-process-scheme";
    final IProcessIdentifier aID1 = aIF.createProcessIdentifier (sScheme, "abc");
    assertEquals (sScheme, aID1.getScheme ());
    assertEquals ("abc", aID1.getValue ());

    final IProcessIdentifier aID2 = aIF.createProcessIdentifier (sScheme, "ABC");
    assertEquals (sScheme, aID2.getScheme ());
    assertEquals ("abc", aID2.getValue ());
    assertTrue (aID1.hasSameContent (aID2));
  }
}
