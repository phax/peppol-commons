/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.peppol.mls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;

/**
 * Test class for class {@link PeppolMLSLineResponseResponseBuilder}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSLineResponseResponseBuilderTest
{
  @Test
  public void testDefaultState ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    assertNull (aBuilder.description ());
    assertNull (aBuilder.statusReasonCode ());
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testDescriptionGetterSetter ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    assertNull (aBuilder.description ());

    aBuilder.description ("An error occurred");
    assertEquals ("An error occurred", aBuilder.description ());

    aBuilder.description ("Updated error message");
    assertEquals ("Updated error message", aBuilder.description ());

    aBuilder.description (null);
    assertNull (aBuilder.description ());
  }

  @Test
  public void testStatusReasonCodeGetterSetter ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    assertNull (aBuilder.statusReasonCode ());

    aBuilder.statusReasonCode (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL);
    assertEquals (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, aBuilder.statusReasonCode ());

    aBuilder.statusReasonCode (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION);
    assertEquals (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION, aBuilder.statusReasonCode ());

    aBuilder.statusReasonCode (null);
    assertNull (aBuilder.statusReasonCode ());
  }

  @Test
  public void testConvenienceMethodBusinessRuleViolationFatal ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.statusReasonCodeBusinessRuleViolationFatal ();
    assertEquals (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, aBuilder.statusReasonCode ());

    aBuilder.description ("fatal error");
    final ResponseType aResponse = aBuilder.build ();
    assertNotNull (aResponse);
    assertEquals ("BV", aResponse.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test
  public void testConvenienceMethodBusinessRuleViolationWarning ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.statusReasonCodeBusinessRuleViolationWarning ();
    assertEquals (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING, aBuilder.statusReasonCode ());

    aBuilder.description ("warning");
    final ResponseType aResponse = aBuilder.build ();
    assertNotNull (aResponse);
    assertEquals ("BW", aResponse.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test
  public void testConvenienceMethodFailureOfDelivery ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.statusReasonCodeFailureOfDelivery ();
    assertEquals (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY, aBuilder.statusReasonCode ());

    aBuilder.description ("delivery failed");
    final ResponseType aResponse = aBuilder.build ();
    assertNotNull (aResponse);
    assertEquals ("FD", aResponse.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test
  public void testConvenienceMethodSyntaxViolation ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.statusReasonCodeSyntaxViolation ();
    assertEquals (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION, aBuilder.statusReasonCode ());

    aBuilder.description ("syntax error");
    final ResponseType aResponse = aBuilder.build ();
    assertNotNull (aResponse);
    assertEquals ("SV", aResponse.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test
  public void testAreAllFieldsSetStepByStep ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();

    // Nothing set
    assertFalse (aBuilder.areAllFieldsSet (false));
    assertFalse (aBuilder.areAllFieldsSet (true));

    // Only description
    aBuilder.description ("test");
    assertFalse (aBuilder.areAllFieldsSet (false));

    // Description + status code
    aBuilder.statusReasonCode (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION);
    assertTrue (aBuilder.areAllFieldsSet (false));
    assertTrue (aBuilder.areAllFieldsSet (true));
  }

  @Test
  public void testAreAllFieldsSetOnlyStatusCode ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.statusReasonCode (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY);
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetEmptyDescription ()
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    aBuilder.description ("");
    aBuilder.statusReasonCode (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY);
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testBuildMinimal ()
  {
    final ResponseType aResponse = new PeppolMLSLineResponseResponseBuilder ().description ("Error in field")
                                                                              .statusReasonCodeBusinessRuleViolationFatal ()
                                                                              .build ();
    assertNotNull (aResponse);
    assertEquals (1, aResponse.getDescription ().size ());
    assertEquals ("Error in field", aResponse.getDescriptionAtIndex (0).getValue ());
    assertEquals (1, aResponse.getStatus ().size ());
    assertEquals ("BV", aResponse.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsMissingDescription ()
  {
    new PeppolMLSLineResponseResponseBuilder ().statusReasonCodeBusinessRuleViolationFatal ().build ();
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsMissingStatusCode ()
  {
    new PeppolMLSLineResponseResponseBuilder ().description ("test").build ();
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsEmptyBuilder ()
  {
    new PeppolMLSLineResponseResponseBuilder ().build ();
  }

  @Test
  public void testCreateForLineResponseResponse ()
  {
    // Build an original response
    final ResponseType aOriginal = new PeppolMLSLineResponseResponseBuilder ().description ("Original error")
                                                                              .statusReasonCodeSyntaxViolation ()
                                                                              .build ();

    // Reverse it
    final PeppolMLSLineResponseResponseBuilder aReverse = PeppolMLSLineResponseResponseBuilder.createForLineResponseResponse (aOriginal);
    assertNotNull (aReverse);
    assertEquals ("Original error", aReverse.description ());
    assertEquals (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION, aReverse.statusReasonCode ());

    // Build again and verify round-trip
    final ResponseType aRebuilt = aReverse.build ();
    assertEquals (aOriginal.getDescriptionAtIndex (0).getValue (), aRebuilt.getDescriptionAtIndex (0).getValue ());
    assertEquals (aOriginal.getStatusAtIndex (0).getStatusReasonCodeValue (),
                  aRebuilt.getStatusAtIndex (0).getStatusReasonCodeValue ());
  }

  @Test
  public void testCreateForLineResponseResponseAllCodes ()
  {
    for (final EPeppolMLSStatusReasonCode eCode : EPeppolMLSStatusReasonCode.values ())
    {
      final ResponseType aOriginal = new PeppolMLSLineResponseResponseBuilder ().description ("Test " + eCode.getID ())
                                                                                .statusReasonCode (eCode)
                                                                                .build ();
      final PeppolMLSLineResponseResponseBuilder aReverse = PeppolMLSLineResponseResponseBuilder.createForLineResponseResponse (aOriginal);
      assertEquals (eCode, aReverse.statusReasonCode ());
      assertEquals ("Test " + eCode.getID (), aReverse.description ());
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForLineResponseResponseNoStatus ()
  {
    final ResponseType aNoStatus = new ResponseType ();
    aNoStatus.addDescription (new oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType ("desc"));
    PeppolMLSLineResponseResponseBuilder.createForLineResponseResponse (aNoStatus);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForLineResponseResponseNoDescription ()
  {
    final ResponseType aNoDesc = new ResponseType ();
    final oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.StatusType aStatus = new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.StatusType ();
    aStatus.setStatusReasonCode ("BV");
    aNoDesc.addStatus (aStatus);
    PeppolMLSLineResponseResponseBuilder.createForLineResponseResponse (aNoDesc);
  }

  @Test
  public void testFluentChaining ()
  {
    // Verify all setters return 'this' for chaining
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ().description ("test")
                                                                                                     .statusReasonCode (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL);
    assertNotNull (aBuilder);
    assertEquals ("test", aBuilder.description ());
    assertEquals (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, aBuilder.statusReasonCode ());
    assertNotNull (aBuilder.build ());
  }
}
