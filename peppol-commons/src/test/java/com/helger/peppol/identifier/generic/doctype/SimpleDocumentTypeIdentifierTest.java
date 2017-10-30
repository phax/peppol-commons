/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.generic.doctype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SimpleDocumentTypeIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class SimpleDocumentTypeIdentifierTest
{
  @Test
  public void testCtor ()
  {
    final SimpleDocumentTypeIdentifier aID = new SimpleDocumentTypeIdentifier ("scheme", "value");
    assertEquals ("scheme", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final SimpleDocumentTypeIdentifier aID2 = new SimpleDocumentTypeIdentifier (aID);
    assertEquals ("scheme", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final SimpleDocumentTypeIdentifier aID1 = new SimpleDocumentTypeIdentifier ("scheme", "value");
    final SimpleDocumentTypeIdentifier aID2 = new SimpleDocumentTypeIdentifier ("scheme", "value");
    final SimpleDocumentTypeIdentifier aID3 = new SimpleDocumentTypeIdentifier ("scheme2", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }
}
