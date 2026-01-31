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
package com.helger.peppolid.peppol.issuingagency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;

/**
 * Test class for class {@link EPredefinedParticipantIdentifierScheme}.
 *
 * @author Philip Helger
 */
public final class EPredefinedParticipantIdentifierSchemeTest
{
  @Test
  public void testAll ()
  {
    for (final EPredefinedParticipantIdentifierScheme e : EPredefinedParticipantIdentifierScheme.values ())
    {
      assertTrue (StringHelper.isNotEmpty (e.getSchemeID ()));
      // May be null but not empty
      final String sAgency = e.getSchemeAgency ();
      if (sAgency != null)
        assertTrue (StringHelper.isNotEmpty (sAgency));
      assertTrue (StringHelper.isNotEmpty (e.getISO6523Code ()));
      assertSame (e, EPredefinedParticipantIdentifierScheme.valueOf (e.name ()));
      assertTrue (e.createIdentifierValue ("abc").endsWith (":abc"));
      assertTrue (PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (e.createIdentifierValue ("def"))
                                                  .getURIEncoded ()
                                                  .endsWith (":def"));
      assertNotNull (e.getInitialRelease ());
    }
  }
}
