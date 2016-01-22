/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test class for class {@link ConfigFile}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class ConfigFileTest
{
  @Test
  public void testAll ()
  {
    final ConfigFile aCF = new ConfigFile ("config.properties");
    assertTrue (aCF.isRead ());
    // Existing elements
    assertEquals ("string", aCF.getString ("element1"));
    assertEquals (6, aCF.getCharArray ("element1").length);
    assertEquals (2, aCF.getInt ("element2", 5));
    assertFalse (aCF.getBoolean ("element3", true));
    assertEquals ("abc", aCF.getString ("element4"));

    // Non-existing elements
    assertNull (aCF.getString ("element1a"));
    assertNull (aCF.getCharArray ("element1a"));
    assertEquals (5, aCF.getInt ("element2a", 5));
    assertTrue (aCF.getBoolean ("element3a", true));

    // All keys
    assertEquals (5, aCF.getAllKeys ().size ());
    assertEquals (5, aCF.getAllEntries ().size ());

    assertNotNull (aCF.toString ());
  }

  @Test
  public void testNonExisting ()
  {
    final ConfigFile aCF = new ConfigFile ("non-existent-file.xml");
    assertFalse (aCF.isRead ());
    assertNull (aCF.getString ("any"));
    assertEquals (0, aCF.getAllKeys ().size ());
    assertEquals (0, aCF.getAllEntries ().size ());

    assertNotNull (aCF.toString ());
  }

  @Test
  public void testNoPath ()
  {
    try
    {
      new ConfigFile ((String []) null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
    try
    {
      new ConfigFile (new String [0]);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    try
    {
      new ConfigFile (null, "bla");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
