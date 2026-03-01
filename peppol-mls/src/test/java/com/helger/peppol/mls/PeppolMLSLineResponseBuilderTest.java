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

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;

/**
 * Test class for class {@link PeppolMLSLineResponseBuilder}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSLineResponseBuilderTest
{
  @Test
  public void testDefaultState ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    assertNull (aBuilder.errorField ());
    assertNotNull (aBuilder.responses ());
    assertTrue (aBuilder.responses ().isEmpty ());
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testErrorFieldGetterSetter ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    assertNull (aBuilder.errorField ());

    aBuilder.errorField ("Invoice/ID");
    assertEquals ("Invoice/ID", aBuilder.errorField ());

    aBuilder.errorField ("Invoice/TaxTotal/TaxAmount");
    assertEquals ("Invoice/TaxTotal/TaxAmount", aBuilder.errorField ());

    aBuilder.errorField (null);
    assertNull (aBuilder.errorField ());
  }

  @Test
  public void testResponsesGetter ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    assertTrue (aBuilder.responses ().isEmpty ());

    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "Error 1");
    assertEquals (1, aBuilder.responses ().size ());

    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING, "Warning 1");
    assertEquals (2, aBuilder.responses ().size ());
  }

  @Test
  public void testAddResponseNull ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.addResponse ((ResponseType) null);
    assertTrue (aBuilder.responses ().isEmpty ());

    aBuilder.addResponse ((PeppolMLSLineResponseResponseBuilder) null);
    assertTrue (aBuilder.responses ().isEmpty ());
  }

  @Test
  public void testAddResponseFromBuilder ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.addResponse (new PeppolMLSLineResponseResponseBuilder ().description ("test")
                                                                     .statusReasonCodeBusinessRuleViolationFatal ());
    assertEquals (1, aBuilder.responses ().size ());
  }

  @Test
  public void testAddResponseFromCodeAndDescription ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.addResponse (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION, "Syntax error in field");
    assertEquals (1, aBuilder.responses ().size ());
  }

  @Test
  public void testResponsesVarargsSetter ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "first");
    assertEquals (1, aBuilder.responses ().size ());

    // Replace all with varargs
    final ResponseType aR1 = new PeppolMLSLineResponseResponseBuilder ().description ("r1")
                                                                        .statusReasonCodeSyntaxViolation ()
                                                                        .build ();
    final ResponseType aR2 = new PeppolMLSLineResponseResponseBuilder ().description ("r2")
                                                                        .statusReasonCodeFailureOfDelivery ()
                                                                        .build ();
    aBuilder.responses (aR1, aR2);
    assertEquals (2, aBuilder.responses ().size ());

    // Replace all with null
    aBuilder.responses ((ResponseType []) null);
    assertTrue (aBuilder.responses ().isEmpty ());
  }

  @Test
  public void testResponsesIterableSetter ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "first");

    final ResponseType aR1 = new PeppolMLSLineResponseResponseBuilder ().description ("r1")
                                                                        .statusReasonCodeSyntaxViolation ()
                                                                        .build ();
    final java.util.List <ResponseType> aList = java.util.Arrays.asList (aR1);
    aBuilder.responses (aList);
    assertEquals (1, aBuilder.responses ().size ());

    // Replace with null
    aBuilder.responses ((Iterable <? extends ResponseType>) null);
    assertTrue (aBuilder.responses ().isEmpty ());
  }

  @Test
  public void testAreAllFieldsSetStepByStep ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();

    // Nothing set
    assertFalse (aBuilder.areAllFieldsSet (false));
    assertFalse (aBuilder.areAllFieldsSet (true));

    // Only error field
    aBuilder.errorField ("field");
    assertFalse (aBuilder.areAllFieldsSet (false));

    // Error field + response
    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "error");
    assertTrue (aBuilder.areAllFieldsSet (false));
    assertTrue (aBuilder.areAllFieldsSet (true));
  }

  @Test
  public void testAreAllFieldsSetEmptyErrorField ()
  {
    final PeppolMLSLineResponseBuilder aBuilder = new PeppolMLSLineResponseBuilder ();
    aBuilder.errorField ("");
    aBuilder.addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "error");
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testBuildMinimal ()
  {
    final LineResponseType aLR = new PeppolMLSLineResponseBuilder ().errorField ("Invoice/ID")
                                                                    .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                  "ID is missing")
                                                                    .build ();
    assertNotNull (aLR);
    assertEquals ("Invoice/ID", aLR.getLineReference ().getLineIDValue ());
    assertEquals (1, aLR.getResponse ().size ());
  }

  @Test
  public void testBuildWithMultipleResponses ()
  {
    final LineResponseType aLR = new PeppolMLSLineResponseBuilder ().errorField ("Invoice/Amount")
                                                                    .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                  "Amount is negative")
                                                                    .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING,
                                                                                  "Amount is unusually large")
                                                                    .addResponse (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION,
                                                                                  "Amount has too many decimals")
                                                                    .build ();
    assertNotNull (aLR);
    assertEquals ("Invoice/Amount", aLR.getLineReference ().getLineIDValue ());
    assertEquals (3, aLR.getResponse ().size ());
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsMissingErrorField ()
  {
    new PeppolMLSLineResponseBuilder ().addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL, "test")
                                       .errorField (null)
                                       .build ();
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsMissingResponses ()
  {
    new PeppolMLSLineResponseBuilder ().errorField ("field").build ();
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsEmptyBuilder ()
  {
    new PeppolMLSLineResponseBuilder ().build ();
  }

  @Test
  public void testCreateForLineResponse ()
  {
    // Build a LineResponseType first
    final LineResponseType aOriginal = new PeppolMLSLineResponseBuilder ().errorField ("Invoice/Note")
                                                                         .addResponse (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY,
                                                                                       "Delivery failed")
                                                                         .build ();

    // Reverse it
    final PeppolMLSLineResponseBuilder aReverse = PeppolMLSLineResponseBuilder.createForLineResponse (aOriginal);
    assertNotNull (aReverse);
    assertEquals ("Invoice/Note", aReverse.errorField ());
    assertEquals (1, aReverse.responses ().size ());

    // Build again and verify round-trip
    final LineResponseType aRebuilt = aReverse.build ();
    assertEquals (aOriginal.getLineReference ().getLineIDValue (), aRebuilt.getLineReference ().getLineIDValue ());
    assertEquals (aOriginal.getResponse ().size (), aRebuilt.getResponse ().size ());
  }

  @Test
  public void testCreateForLineResponseMultipleResponses ()
  {
    final LineResponseType aOriginal = new PeppolMLSLineResponseBuilder ().errorField ("Invoice/ID")
                                                                         .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                       "ID missing")
                                                                         .addResponse (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION,
                                                                                       "ID format wrong")
                                                                         .build ();

    final PeppolMLSLineResponseBuilder aReverse = PeppolMLSLineResponseBuilder.createForLineResponse (aOriginal);
    assertEquals ("Invoice/ID", aReverse.errorField ());
    assertEquals (2, aReverse.responses ().size ());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForLineResponseNoResponses ()
  {
    final LineResponseType aEmpty = new LineResponseType ();
    final oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineReferenceType aLineRef = new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineReferenceType ();
    aLineRef.setLineID ("field");
    aEmpty.setLineReference (aLineRef);
    PeppolMLSLineResponseBuilder.createForLineResponse (aEmpty);
  }
}
