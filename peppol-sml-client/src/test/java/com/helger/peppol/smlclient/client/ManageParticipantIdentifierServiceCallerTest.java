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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.base.url.URLHelper;
import com.helger.cache.regex.RegExHelper;
import com.helger.peppol.sml.CSMLDefault;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * Test class for class {@link ManageParticipantIdentifierServiceCaller}.
 *
 * @author Philip Helger
 */
public final class ManageParticipantIdentifierServiceCallerTest
{
  @Test
  public void testCreateRandomMigrationKey ()
  {
    for (int i = 0; i < 100_000; ++i)
    {
      final String s = ManageParticipantIdentifierServiceCaller.createRandomMigrationKey ();
      assertTrue ("Oops: '" + s + "'", RegExHelper.stringMatchesPattern (CSMLDefault.MIGRATION_CODE_PATTERN, s));
    }
  }

  @Test
  public void testPrepareToMigrateEmptyKey () throws Exception
  {
    // The check happens before the SML is contacted
    final ManageParticipantIdentifierServiceCaller aCaller = new ManageParticipantIdentifierServiceCaller (URLHelper.getAsURL ("http://localhost:1/managebusinessidentifier"));
    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:5798000999999");
    try
    {
      // An empty migration key would remove a prepared migration in BDMSL
      aCaller.prepareToMigrate (aPI, "", "SMP-TEST-ID");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }
}
