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
package com.helger.peppol.identifier.peppol.process;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link PredefinedProcessIdentifierManager}.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class PredefinedProcessIdentifierManagerTest
{
  @Test
  public void testAll ()
  {
    assertNotNull (PredefinedProcessIdentifierManager.getAllProcessIdentifiers ());
    assertNotNull (PredefinedProcessIdentifierManager.getAllProcessIdentifierIDs ());

    final IPeppolPredefinedProcessIdentifier aPPI = PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0");
    assertNotNull (aPPI);
    assertFalse (aPPI.getDocumentTypeIdentifiers ().isEmpty ());

    assertNotNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0"));
    assertNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID ("urn:www.cenbii.eu:profile:bii01:ver1.0a"));
    assertTrue (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID ("urn:www.cenbii.eu:profile:bii01:ver1.0"));
    assertFalse (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID ("urn:www.cenbii.eu:profile:bii01:ver1.0a"));
  }
}
