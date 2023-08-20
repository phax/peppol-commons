/*
 * Copyright (C) 2015-2023 Philip Helger
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;

/**
 * Test class for class {@link EPredefinedDocumentTypeIdentifier}.
 *
 * @author Philip Helger
 */
public final class EPredefinedDocumentTypeIdentifierTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedDocumentTypeIdentifier e : EPredefinedDocumentTypeIdentifier.values ())
    {
      assertTrue (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS.equals (e.getScheme ()) ||
                  PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD.equals (e.getScheme ()));
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
