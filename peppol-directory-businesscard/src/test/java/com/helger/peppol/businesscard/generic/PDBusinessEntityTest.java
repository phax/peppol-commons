/*
 * Copyright (C) 2015-2023 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNull;

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
public final class PDBusinessEntityTest
{
  @Test
  public void testBasic ()
  {
    // Regular
    final LocalDate aToday = PDTFactory.getCurrentLocalDate ();
    PDBusinessEntity aBE = new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip", "en"), new PDName ("Helger", "es")),
                                                      "AT",
                                                      "geo",
                                                      new CommonsArrayList <> (new PDIdentifier ("s1", "v1"),
                                                                               new PDIdentifier ("s2", "v2")),
                                                      new CommonsArrayList <> ("uri1", "uri2", "uri3"),
                                                      new CommonsArrayList <> (new PDContact ("t", "n", "p", "e"),
                                                                               new PDContact ("t2", "n2", "p2", "e2")),
                                                      "additional info",
                                                      aToday);
    assertEquals (2, aBE.names ().size ());
    assertEquals (new PDName ("Philip", "en"), aBE.names ().get (0));
    assertEquals (new PDName ("Helger", "es"), aBE.names ().get (1));
    assertEquals ("AT", aBE.getCountryCode ());
    assertEquals ("geo", aBE.getGeoInfo ());
    assertEquals (2, aBE.identifiers ().size ());
    assertEquals (new PDIdentifier ("s1", "v1"), aBE.identifiers ().get (0));
    assertEquals (new PDIdentifier ("s2", "v2"), aBE.identifiers ().get (1));
    assertEquals (3, aBE.websiteURIs ().size ());
    assertEquals ("uri1", aBE.websiteURIs ().get (0));
    assertEquals ("uri2", aBE.websiteURIs ().get (1));
    assertEquals ("uri3", aBE.websiteURIs ().get (2));
    assertEquals (2, aBE.contacts ().size ());
    assertEquals (new PDContact ("t", "n", "p", "e"), aBE.contacts ().get (0));
    assertEquals (new PDContact ("t2", "n2", "p2", "e2"), aBE.contacts ().get (1));
    assertEquals ("additional info", aBE.getAdditionalInfo ());
    assertEquals (aToday, aBE.getRegistrationDate ());

    CommonsTestHelper.testDefaultSerialization (aBE);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBE,
                                                                       new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                  "en"),
                                                                                                                      new PDName ("Helger",
                                                                                                                                  "es")),
                                                                                             "AT",
                                                                                             "geo",
                                                                                             new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                        "v1"),
                                                                                                                      new PDIdentifier ("s2",
                                                                                                                                        "v2")),
                                                                                             new CommonsArrayList <> ("uri1",
                                                                                                                      "uri2",
                                                                                                                      "uri3"),
                                                                                             new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                     "n",
                                                                                                                                     "p",
                                                                                                                                     "e"),
                                                                                                                      new PDContact ("t2",
                                                                                                                                     "n2",
                                                                                                                                     "p2",
                                                                                                                                     "e2")),
                                                                                             "additional info",
                                                                                             aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "de")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "DE",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo2",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1a"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri22",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "E"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "Sowas aber auch",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday.minusDays (100)));
    // Partial null stuff
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 null,
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 null,
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 null,
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 null,
                                                                                                 "additional info",
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 null,
                                                                                                 aToday));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aBE,
                                                                           new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip",
                                                                                                                                      "en"),
                                                                                                                          new PDName ("Helger",
                                                                                                                                      "es")),
                                                                                                 "AT",
                                                                                                 "geo",
                                                                                                 new CommonsArrayList <> (new PDIdentifier ("s1",
                                                                                                                                            "v1"),
                                                                                                                          new PDIdentifier ("s2",
                                                                                                                                            "v2")),
                                                                                                 new CommonsArrayList <> ("uri1",
                                                                                                                          "uri2",
                                                                                                                          "uri3"),
                                                                                                 new CommonsArrayList <> (new PDContact ("t",
                                                                                                                                         "n",
                                                                                                                                         "p",
                                                                                                                                         "e"),
                                                                                                                          new PDContact ("t2",
                                                                                                                                         "n2",
                                                                                                                                         "p2",
                                                                                                                                         "e2")),
                                                                                                 "additional info",
                                                                                                 null));
    assertNotNull (aBE.getAsMicroXML (null, "a"));
    assertNotNull (aBE.getAsMicroXML ("urn:example.org", "a"));

    aBE = new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip")), "AT", null, null, null, null, null, null);
    assertEquals (1, aBE.names ().size ());
    assertEquals (new PDName ("Philip"), aBE.names ().get (0));
    assertEquals ("AT", aBE.getCountryCode ());
    assertNull (aBE.getGeoInfo ());
    assertEquals (0, aBE.identifiers ().size ());
    assertEquals (0, aBE.websiteURIs ().size ());
    assertEquals (0, aBE.contacts ().size ());
    assertNull (aBE.getAdditionalInfo ());
    assertNull (aBE.getRegistrationDate ());
  }

  @Test
  public void testJson ()
  {
    final LocalDate aToday = PDTFactory.getCurrentLocalDate ();
    PDBusinessEntity aBE = new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip", "en"), new PDName ("Helger", "es")),
                                                 "AT",
                                                 "geo",
                                                 new CommonsArrayList <> (new PDIdentifier ("s1", "v1"), new PDIdentifier ("s2", "v2")),
                                                 new CommonsArrayList <> ("uri1", "uri2", "uri3"),
                                                 new CommonsArrayList <> (new PDContact ("t", "n", "p", "e"),
                                                                          new PDContact ("t2", "n2", "p2", "e2")),
                                                 "additional info",
                                                 aToday);
    IJsonObject aJson = aBE.getAsJson ();
    assertNotNull (aJson);
    PDBusinessEntity aBE2 = PDBusinessEntity.of (aJson);
    assertNotNull (aBE2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBE2, aBE);

    // None set
    aBE = new PDBusinessEntity (new CommonsArrayList <> (new PDName ("Philip")), "AT", null, null, null, null, null, null);
    aJson = aBE.getAsJson ();
    assertNotNull (aJson);
    aBE2 = PDBusinessEntity.of (aJson);
    assertNotNull (aBE2);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aBE2, aBE);
  }
}
