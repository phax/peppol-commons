/*
 * Copyright (C) 2024-2025 Philip Helger
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
package com.helger.peppol.xhe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.xml.serialize.read.DOMReader;

/**
 * Test class for class {@link DBNAlliancePayload}.
 *
 * @author Philip Helger
 */
public final class DBNAlliancePayloadTest
{
  @Test
  public void testBasic ()
  {
    final Document aDoc = DOMReader.readXMLDOM ("<root xmlns='anyurl' />");

    final DBNAlliancePayload aPayload = new DBNAlliancePayload (SimpleIdentifierFactory.INSTANCE);
    assertFalse (aPayload.areAllMandatoryFieldsSet ());

    aPayload.setDescription ("desc")
            .setContentTypeCodeListID ("tcl")
            .setContentTypeCode ("tc")
            .setCustomizationIDSchemeID ("cids")
            .setCustomizationID ("cid")
            .setProfileIDSchemeID ("pids")
            .setProfileID ("pid")
            .setInstanceEncryptionIndicator (true)
            .setInstanceEncryptionMethod ("iem")
            .setPayloadContentNoClone (aDoc.getDocumentElement ());
    assertEquals ("desc", aPayload.getDescription ());
    assertEquals ("tcl", aPayload.getContentTypeCodeListID ());
    assertEquals ("tc", aPayload.getContentTypeCode ());
    assertEquals ("cids", aPayload.getCustomizationIDSchemeID ());
    assertEquals ("cid", aPayload.getCustomizationID ());
    assertEquals ("pids", aPayload.getProfileIDSchemeID ());
    assertEquals ("pid", aPayload.getProfileID ());
    assertTrue (aPayload.isInstanceEncryptionIndicator ());
    assertEquals ("iem", aPayload.getInstanceEncryptionMethod ());
    assertSame (aDoc.getDocumentElement (), aPayload.getPayloadContentNoClone ());
    assertTrue (aPayload.areAllMandatoryFieldsSet ());
  }
}
