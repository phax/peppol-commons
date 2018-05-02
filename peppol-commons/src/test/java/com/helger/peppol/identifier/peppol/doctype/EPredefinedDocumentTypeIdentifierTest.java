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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Test class for class {@link EPredefinedDocumentTypeIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class EPredefinedDocumentTypeIdentifierTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      assertEquals (PeppolIdentifierHelper.DEFAULT_DOCUMENT_TYPE_SCHEME, e.getScheme ());
      assertTrue (StringHelper.hasText (e.getValue ()));
      assertTrue (StringHelper.hasText (e.getRootNS ()));
      assertTrue (StringHelper.hasText (e.getLocalName ()));
      assertTrue (StringHelper.hasText (e.getSubTypeIdentifier ()));
      assertTrue (StringHelper.hasText (e.getCommonName ()));
      assertEquals (e.getAsDocumentTypeIdentifierValue (), e.getValue ());
      assertSame (e, EPredefinedDocumentTypeIdentifier.valueOf (e.name ()));
      assertSame (e, EPredefinedDocumentTypeIdentifier.getFromDocumentTypeIdentifierOrNull (e));

      final IPeppolDocumentTypeIdentifierParts p = e.getParts ();
      assertNotNull (p);
      assertTrue (StringHelper.hasText (p.getCustomizationID ()));
      assertTrue (StringHelper.hasText (p.getVersion ()));
    }
    assertNull (EPredefinedDocumentTypeIdentifier.getFromDocumentTypeIdentifierOrNull (null));
    assertNull (EPredefinedDocumentTypeIdentifier.getFromDocumentTypeIdentifierOrNull (new SimpleDocumentTypeIdentifier ("bla",
                                                                                                                         "foo")));
  }
}
