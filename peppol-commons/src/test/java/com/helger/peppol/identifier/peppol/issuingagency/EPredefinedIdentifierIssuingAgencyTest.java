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
package com.helger.peppol.identifier.peppol.issuingagency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link EPredefinedIdentifierIssuingAgency}.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class EPredefinedIdentifierIssuingAgencyTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedIdentifierIssuingAgency e : EPredefinedIdentifierIssuingAgency.values ())
    {
      assertTrue (StringHelper.hasText (e.getSchemeID ()));
      // May be null but not empty
      final String sAgency = e.getSchemeAgency ();
      if (sAgency != null)
        assertTrue (StringHelper.hasText (sAgency));
      assertTrue (StringHelper.hasText (e.getISO6523Code ()));
      assertSame (e, EPredefinedIdentifierIssuingAgency.valueOf (e.name ()));
      assertTrue (e.createIdentifierValue ("abc").endsWith (":abc"));
      assertTrue (e.createParticipantIdentifier ("def").getURIEncoded ().endsWith (":def"));
      assertNotNull (e.getSince ());
    }
  }
}
