/*
 * Copyright (C) 2015-2025 Philip Helger (www.helger.com)
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
package com.helger.peppol.businesscard.generic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.json.IJsonObject;

/**
 * Test class for class {@link PDBusinessEntity}.
 *
 * @author Philip Helger
 */
public final class PDBusinessCardTest
{
  private static final LocalDate TODAY = PDTFactory.getCurrentLocalDate ();

  private static PDBusinessEntity _createBE ()
  {
    return new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip", "en"), new PDName ("Helger", "es")),
                                 "AT",
                                 "geo",
                                 new CommonsArrayList <> (new PDIdentifier ("s1", "v1"), new PDIdentifier ("s2", "v2")),
                                 new CommonsArrayList <> ("uri1", "uri2", "uri3"),
                                 new CommonsArrayList <> (new PDContact ("t", "n", "p", "e"), new PDContact ("t2", "n2", "p2", "e2")),
                                 "additional info",
                                 TODAY);
  }

  @Test
  public void testBasic ()
  {
    // Regular
    PDBusinessCard aBC = new PDBusinessCard (new PDIdentifier ("a", "b"), new CommonsArrayList <> (_createBE ()));
    assertEquals (new PDIdentifier ("a", "b"), aBC.getParticipantIdentifier ());
    assertEquals (1, aBC.businessEntities ().size ());
    assertEquals (_createBE (), aBC.businessEntities ().get (0));

    CommonsTestHelper.testDefaultSerialization (aBC);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBC,
                                                                       new PDBusinessCard (new PDIdentifier ("a", "b"),
                                                                                           new CommonsArrayList <> (_createBE ())));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBC,
                                                                           new PDBusinessCard (new PDIdentifier ("a", "c"),
                                                                                               new CommonsArrayList <> (_createBE ())));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBC,
                                                                           new PDBusinessCard (new PDIdentifier ("a", "b"),
                                                                                               new CommonsArrayList <> (_createBE (),
                                                                                                                        _createBE ())));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBC,
                                                                           new PDBusinessCard (new PDIdentifier ("a", "b"),
                                                                                               new CommonsArrayList <> (_createBE ().setGeoInfo ("something else"))));

    assertNotNull (aBC.getAsMicroXML (null, "a"));
    assertNotNull (aBC.getAsMicroXML ("urn:example.org", "a"));

    // Without entities
    aBC = new PDBusinessCard (new PDIdentifier ("a", "b"), null);
    assertEquals (new PDIdentifier ("a", "b"), aBC.getParticipantIdentifier ());
    assertEquals (0, aBC.businessEntities ().size ());
  }

  @Test
  public void testJson ()
  {
    PDBusinessCard aBC = new PDBusinessCard (new PDIdentifier ("a", "b"),
                                             new CommonsArrayList <> (_createBE (), _createBE ().setAdditionalInfo ("Gni gna gnu")));
    IJsonObject aJson = aBC.getAsJson ();
    assertNotNull (aJson);
    PDBusinessCard aBC2 = PDBusinessCard.of (aJson);
    assertNotNull (aBC2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBC2, aBC);

    // None set
    aBC = new PDBusinessCard (new PDIdentifier ("a", "b"), null);
    aJson = aBC.getAsJson ();
    assertNotNull (aJson);
    aBC2 = PDBusinessCard.of (aJson);
    assertNotNull (aBC2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBC2, aBC);
  }
}
