/*
 * Copyright (C) 2015-2021 Philip Helger
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

import org.junit.Test;

import com.helger.commons.regex.RegExHelper;
import com.helger.peppol.sml.CSMLDefault;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;

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
}
