/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppol.smp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;

/**
 * Test class for class {@link ESMPTransportProfile}.
 *
 * @author Philip Helger
 */
public final class ESMPTransportProfileTest
{
  @SuppressWarnings ("deprecation")
  @Test
  public void testBasic ()
  {
    for (final ESMPTransportProfile e : ESMPTransportProfile.values ())
    {
      assertTrue (StringHelper.isNotEmpty (e.getID ()));
      assertSame (e, ESMPTransportProfile.getFromIDOrNull (e.getID ()));
    }
    assertSame (ESMPTransportProfileState.ACTIVE, ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2.getState ());
    assertFalse (ESMPTransportProfile.TRANSPORT_PROFILE_PEPPOL_AS4_V2.getState ().isDeprecated ());

    assertSame (ESMPTransportProfileState.DEPRECATED, ESMPTransportProfile.TRANSPORT_PROFILE_AS4.getState ());
    assertTrue (ESMPTransportProfile.TRANSPORT_PROFILE_AS4.getState ().isDeprecated ());
  }
}
