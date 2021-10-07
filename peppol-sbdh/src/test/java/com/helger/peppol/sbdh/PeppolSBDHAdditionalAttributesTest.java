/*
 * Copyright (C) 2014-2021 Philip Helger
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
package com.helger.peppol.sbdh;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link PeppolSBDHAdditionalAttributes}.
 *
 * @author Philip Helger
 */
public class PeppolSBDHAdditionalAttributesTest
{
  @Test
  public void testReservedNames ()
  {
    assertFalse (PeppolSBDHAdditionalAttributes.isReservedAttributeName (null));
    assertFalse (PeppolSBDHAdditionalAttributes.isReservedAttributeName (""));
    assertFalse (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("documentid"));
    assertTrue (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("DOCUMENTID"));
    assertTrue (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("PROCESSID"));
    assertTrue (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("TECHNICAL_VALIDATION_URL"));
    assertTrue (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("TECHNICAL_VALIDATION_REQUIRED"));
    assertFalse (PeppolSBDHAdditionalAttributes.isReservedAttributeName ("DOCUMENT"));
  }
}
