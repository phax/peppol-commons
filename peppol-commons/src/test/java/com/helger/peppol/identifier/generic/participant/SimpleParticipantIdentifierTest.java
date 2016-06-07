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
package com.helger.peppol.identifier.generic.participant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
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

    assertEquals (aID, aID2);
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
