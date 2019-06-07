/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.simple.participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.xml.mock.XMLTestHelper;

/**
 * Test class for class {@link SimpleParticipantIdentifier}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class SimpleParticipantIdentifierTest
{
  @Test
  public void testCtor ()
  {
    final SimpleParticipantIdentifier aID = new SimpleParticipantIdentifier ("scheme-actorid-test", "value");
    assertEquals ("scheme-actorid-test", aID.getScheme ());
    assertEquals ("value", aID.getValue ());

    final SimpleParticipantIdentifier aID2 = new SimpleParticipantIdentifier (aID);
    assertEquals ("scheme-actorid-test", aID2.getScheme ());
    assertEquals ("value", aID2.getValue ());

    assertTrue (aID.hasSameContent (aID2));
    XMLTestHelper.testMicroTypeConversion (aID2);
  }

  @Test
  public void testBasicMethods ()
  {
    final SimpleParticipantIdentifier aID1 = new SimpleParticipantIdentifier ("scheme-actorid-test", "value");
    final SimpleParticipantIdentifier aID2 = new SimpleParticipantIdentifier ("scheme-actorid-test", "value");
    final SimpleParticipantIdentifier aID3 = new SimpleParticipantIdentifier ("scheme2-actorid-test", "value");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (aID1, aID2);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID1, aID3);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (aID2, aID3);
  }
}
