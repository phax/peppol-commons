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

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.datetime.xml.XMLOffsetTime;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Test class for class {@link PeppolMLSBuilder}.
 *
 * @author Philip Helger
 */
public final class PeppolMLSBuilderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSBuilderTest.class);
  private static final PeppolIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  @Test
  public void testMinimalNoLine ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                         .referenceId ("SBDH-12345")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-receiver"))
                                                         .build ();
    assertNotNull (aMLS);
    final String sMLS1 = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sMLS1);

    // Check with reverse Builder
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    final String sMLS2 = new PeppolMLSMarshaller ().getAsString (aMLS2);
    assertEquals (sMLS1, sMLS2);

    if (false)
      LOGGER.info (new PeppolMLSMarshaller ().setFormattedOutput (true).getAsString (aMLS));
    if (false)
      new PeppolMLSMarshaller ().setFormattedOutput (true).write (aMLS, new File ("generated/mls1.xml"));
  }

  @Test
  public void testMinimalWithOneLine ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-12345")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/ID")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "The ID seems to be missing"))
                                                         .build ();
    assertNotNull (aMLS);

    final String sMLS1 = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sMLS1);

    // Check with reverse Builder
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    final String sMLS2 = new PeppolMLSMarshaller ().getAsString (aMLS2);
    assertEquals (sMLS1, sMLS2);

    if (false)
      LOGGER.info (new PeppolMLSMarshaller ().setFormattedOutput (true).getAsString (aMLS));
    if (false)
      new PeppolMLSMarshaller ().setFormattedOutput (true).write (aMLS, new File ("generated/mls2.xml"));
  }

  @Test
  public void testMinimalWithOneLineButMultipleResponses ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-12345")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:mls-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/ID")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "The ID is too short")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "The ID does not fulfill the regular expression"))
                                                         .build ();
    assertNotNull (aMLS);

    final String sMLS1 = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sMLS1);

    // Check with reverse Builder
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    final String sMLS2 = new PeppolMLSMarshaller ().getAsString (aMLS2);
    assertEquals (sMLS1, sMLS2);

    if (false)
      LOGGER.info (new PeppolMLSMarshaller ().setFormattedOutput (true).getAsString (aMLS));
    if (false)
      new PeppolMLSMarshaller ().setFormattedOutput (true).write (aMLS, new File ("generated/mls3.xml"));
  }

  @Test
  public void testAcknowledgingResponse ()
  {
    final PeppolMLSBuilder aBuilder = PeppolMLSBuilder.acknowledging ();
    assertEquals (EPeppolMLSResponseCode.ACKNOWLEDGING, aBuilder.responseCode ());
    assertNotNull (aBuilder.id ());
    assertNotNull (aBuilder.issueDate ());
    assertNotNull (aBuilder.issueTime ());

    aBuilder.referenceId ("SBDH-ACK-001")
            .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:ack-sender"))
            .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:ack-receiver"));

    final ApplicationResponseType aMLS = aBuilder.build ();
    assertNotNull (aMLS);
    assertEquals (CPeppolMLS.MLS_CUSTOMIZATION_ID, aMLS.getCustomizationIDValue ());
    assertEquals (CPeppolMLS.MLS_PROFILE_ID, aMLS.getProfileIDValue ());

    // Round-trip
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    assertEquals (new PeppolMLSMarshaller ().getAsString (aMLS), new PeppolMLSMarshaller ().getAsString (aMLS2));
  }

  @Test
  public void testGettersAndSetters ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    assertEquals (EPeppolMLSResponseCode.ACCEPTANCE, aBuilder.responseCode ());

    // Initially all null
    assertNull (aBuilder.id ());
    assertNull (aBuilder.issueDate ());
    assertNull (aBuilder.issueTime ());
    assertNull (aBuilder.senderParticipantID ());
    assertNull (aBuilder.receiverParticipantID ());
    assertNull (aBuilder.referenceId ());
    assertNull (aBuilder.referenceTypeCode ());
    assertNull (aBuilder.responseText ());
    assertTrue (aBuilder.lineResponses ().isEmpty ());

    // Set ID
    aBuilder.id ("my-id");
    assertEquals ("my-id", aBuilder.id ());

    // Set ID to null
    aBuilder.id (null);
    assertNull (aBuilder.id ());

    // Random ID
    aBuilder.randomID ();
    assertNotNull (aBuilder.id ());

    // Issue date
    final LocalDate aDate = LocalDate.of (2026, Month.MARCH, 1);
    aBuilder.issueDate (aDate);
    assertEquals (aDate, aBuilder.issueDate ());

    // Issue time with offset
    final XMLOffsetTime aTime = XMLOffsetTime.of (LocalTime.of (10, 30, 0), ZoneOffset.UTC);
    aBuilder.issueTime (aTime);
    assertEquals (aTime, aBuilder.issueTime ());

    // Sender PID
    final IParticipantIdentifier aSender = IF.createParticipantIdentifierWithDefaultScheme ("9915:test-sender");
    aBuilder.senderParticipantID (aSender);
    assertEquals (aSender, aBuilder.senderParticipantID ());

    // Receiver PID
    final IParticipantIdentifier aReceiver = IF.createParticipantIdentifierWithDefaultScheme ("9915:test-receiver");
    aBuilder.receiverParticipantID (aReceiver);
    assertEquals (aReceiver, aBuilder.receiverParticipantID ());

    // Reference ID
    aBuilder.referenceId ("ref-123");
    assertEquals ("ref-123", aBuilder.referenceId ());

    // Reference type code
    aBuilder.referenceTypeCode ("380");
    assertEquals ("380", aBuilder.referenceTypeCode ());

    // Response text
    aBuilder.responseText ("Some response");
    assertEquals ("Some response", aBuilder.responseText ());
  }

  @Test
  public void testIssueDateTimeNow ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueDateTimeNow ();
    assertNotNull (aBuilder.issueDate ());
    assertNotNull (aBuilder.issueTime ());
    assertNotNull (aBuilder.issueTime ().getOffset ());
  }

  @Test
  public void testIssueDateNow ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueDateNow ();
    assertNotNull (aBuilder.issueDate ());
  }

  @Test
  public void testIssueTimeNow ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueTimeNow ();
    assertNotNull (aBuilder.issueTime ());
    assertNotNull (aBuilder.issueTime ().getOffset ());
  }

  @Test
  public void testIssueTimeFromOffsetTime ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    final OffsetTime aOT = OffsetTime.of (14, 30, 0, 0, ZoneOffset.ofHours (2));
    aBuilder.issueTime (aOT);
    assertNotNull (aBuilder.issueTime ());
    assertEquals (ZoneOffset.ofHours (2), aBuilder.issueTime ().getOffset ());

    // Null OffsetTime
    aBuilder.issueTime ((OffsetTime) null);
    assertNull (aBuilder.issueTime ());
  }

  @Test
  public void testIssueTimeFromLocalTimeAndOffset ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueTime (LocalTime.of (9, 0, 0), ZoneOffset.ofHours (-5));
    assertNotNull (aBuilder.issueTime ());
    assertEquals (ZoneOffset.ofHours (-5), aBuilder.issueTime ().getOffset ());

    // Null LocalTime
    aBuilder.issueTime ((LocalTime) null, ZoneOffset.UTC);
    assertNull (aBuilder.issueTime ());
  }

  @Test
  public void testSenderParticipantIDFromString ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.senderParticipantID ("9915:string-sender");
    assertNotNull (aBuilder.senderParticipantID ());
  }

  @Test
  public void testReceiverParticipantIDFromString ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.receiverParticipantID ("9915:string-receiver");
    assertNotNull (aBuilder.receiverParticipantID ());
  }

  @Test
  public void testAddLineResponseNull ()
  {
    final PeppolMLSBuilder aBuilder = PeppolMLSBuilder.acceptance ();
    aBuilder.addLineResponse ((PeppolMLSLineResponseBuilder) null);
    assertTrue (aBuilder.lineResponses ().isEmpty ());
  }

  @Test
  public void testAreAllFieldsSetMissingID ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    // No fields set at all
    assertFalse (aBuilder.areAllFieldsSet (false));
    assertFalse (aBuilder.areAllFieldsSet (true));
  }

  @Test
  public void testAreAllFieldsSetMissingDate ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id");
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetMissingTime ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id").issueDateNow ();
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetMissingSender ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id").issueDateTimeNow ();
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetMissingReceiver ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id")
            .issueDateTimeNow ()
            .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:sender"));
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetMissingReferenceId ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id")
            .issueDateTimeNow ()
            .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:sender"))
            .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:receiver"));
    assertFalse (aBuilder.areAllFieldsSet (false));
  }

  @Test
  public void testAreAllFieldsSetRejectionWithoutLines ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.REJECTION);
    aBuilder.id ("test-id")
            .issueDateTimeNow ()
            .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:sender"))
            .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:receiver"))
            .referenceId ("ref-123");
    // Rejection requires at least one line response
    assertFalse (aBuilder.areAllFieldsSet (true));
  }

  @Test
  public void testAreAllFieldsSetAcceptanceComplete ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.id ("test-id")
            .issueDateTimeNow ()
            .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:sender"))
            .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:receiver"))
            .referenceId ("ref-123");
    // Acceptance without lines is OK
    assertTrue (aBuilder.areAllFieldsSet (false));
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsMissingFields ()
  {
    new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE).build ();
  }

  @Test (expected = IllegalStateException.class)
  public void testBuildFailsRejectionWithoutLines ()
  {
    new PeppolMLSBuilder (EPeppolMLSResponseCode.REJECTION).randomID ()
                                                           .issueDateTimeNow ()
                                                           .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:sender"))
                                                           .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:receiver"))
                                                           .referenceId ("ref-123")
                                                           .build ();
  }

  @Test
  public void testBuildWithOptionalFields ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                         .referenceId ("SBDH-OPT-001")
                                                         .referenceTypeCode ("380")
                                                         .responseText ("Message accepted successfully")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:opt-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:opt-receiver"))
                                                         .build ();
    assertNotNull (aMLS);

    // Verify optional fields are present in the output
    final String sXML = new PeppolMLSMarshaller ().getAsString (aMLS);
    assertNotNull (sXML);
    assertTrue (sXML.contains ("380"));
    assertTrue (sXML.contains ("Message accepted successfully"));

    // Round-trip preserves optional fields
    final PeppolMLSBuilder aReverse = PeppolMLSBuilder.createForApplicationResponse (aMLS);
    assertEquals ("380", aReverse.referenceTypeCode ());
    assertEquals ("Message accepted successfully", aReverse.responseText ());

    final ApplicationResponseType aMLS2 = aReverse.build ();
    assertEquals (sXML, new PeppolMLSMarshaller ().getAsString (aMLS2));
  }

  @Test
  public void testBuildWithExplicitDateAndTime ()
  {
    final LocalDate aDate = LocalDate.of (2026, Month.JANUARY, 15);
    final XMLOffsetTime aTime = XMLOffsetTime.of (LocalTime.of (12, 0, 0), ZoneOffset.ofHours (1));

    final ApplicationResponseType aMLS = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE).id ("explicit-dt-id")
                                                                                                 .issueDate (aDate)
                                                                                                 .issueTime (aTime)
                                                                                                 .referenceId ("SBDH-DT-001")
                                                                                                 .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:dt-sender"))
                                                                                                 .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:dt-receiver"))
                                                                                                 .build ();
    assertNotNull (aMLS);
    assertEquals ("explicit-dt-id", aMLS.getIDValue ());
    assertEquals (aDate, aMLS.getIssueDateValue ().toLocalDate ());

    // Round-trip
    final PeppolMLSBuilder aReverse = PeppolMLSBuilder.createForApplicationResponse (aMLS);
    assertEquals ("explicit-dt-id", aReverse.id ());
    assertEquals (aDate, aReverse.issueDate ());
    assertEquals ("SBDH-DT-001", aReverse.referenceId ());
  }

  @Test
  public void testRejectionWithMultipleLineResponses ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-MULTI-001")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:multi-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:multi-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/ID")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "ID is missing"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/IssueDate")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION,
                                                                                                                            "Date format invalid"))
                                                         .build ();
    assertNotNull (aMLS);

    // Verify two line responses
    assertEquals (2, aMLS.getDocumentResponse ().get (0).getLineResponse ().size ());

    // Round-trip
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    assertEquals (new PeppolMLSMarshaller ().getAsString (aMLS), new PeppolMLSMarshaller ().getAsString (aMLS2));
  }

  @Test
  public void testRejectionWithAllStatusReasonCodes ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-CODES-001")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:codes-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:codes-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("field-bv")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "Business rule fatal"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("field-bw")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING,
                                                                                                                            "Business rule warning"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("field-fd")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY,
                                                                                                                            "Delivery failed"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("field-sv")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION,
                                                                                                                            "Syntax error"))
                                                         .build ();
    assertNotNull (aMLS);
    assertEquals (4, aMLS.getDocumentResponse ().get (0).getLineResponse ().size ());

    // Round-trip
    final ApplicationResponseType aMLS2 = PeppolMLSBuilder.createForApplicationResponse (aMLS).build ();
    assertEquals (new PeppolMLSMarshaller ().getAsString (aMLS), new PeppolMLSMarshaller ().getAsString (aMLS2));
  }

  @Test
  public void testAcceptanceWithLineResponseIsAllowed ()
  {
    // Acceptance CAN have line responses (e.g. for warnings)
    final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                         .referenceId ("SBDH-WARN-001")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:warn-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:warn-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/Note")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING,
                                                                                                                            "Note field is unusual"))
                                                         .build ();
    assertNotNull (aMLS);
    assertEquals (1, aMLS.getDocumentResponse ().get (0).getLineResponse ().size ());
  }

  @Test
  public void testFactoryMethodsSetResponseCode ()
  {
    assertEquals (EPeppolMLSResponseCode.ACCEPTANCE, PeppolMLSBuilder.acceptance ().responseCode ());
    assertEquals (EPeppolMLSResponseCode.ACKNOWLEDGING, PeppolMLSBuilder.acknowledging ().responseCode ());
    assertEquals (EPeppolMLSResponseCode.REJECTION, PeppolMLSBuilder.rejection ().responseCode ());
  }

  @Test
  public void testFactoryMethodsPreInitialize ()
  {
    // All factory methods should pre-set ID, date and time
    for (final PeppolMLSBuilder aBuilder : new PeppolMLSBuilder [] { PeppolMLSBuilder.acceptance (),
                                                                     PeppolMLSBuilder.acknowledging (),
                                                                     PeppolMLSBuilder.rejection () })
    {
      assertNotNull (aBuilder.id ());
      assertNotNull (aBuilder.issueDate ());
      assertNotNull (aBuilder.issueTime ());
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForApplicationResponseWrongCustomizationID ()
  {
    final ApplicationResponseType aAR = new ApplicationResponseType ();
    aAR.setCustomizationID ("wrong-id");
    aAR.setProfileID (CPeppolMLS.MLS_PROFILE_ID);
    PeppolMLSBuilder.createForApplicationResponse (aAR);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForApplicationResponseWrongProfileID ()
  {
    final ApplicationResponseType aAR = new ApplicationResponseType ();
    aAR.setCustomizationID (CPeppolMLS.MLS_CUSTOMIZATION_ID);
    aAR.setProfileID ("wrong-profile");
    PeppolMLSBuilder.createForApplicationResponse (aAR);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateForApplicationResponseNoDocumentResponse ()
  {
    final ApplicationResponseType aAR = new ApplicationResponseType ();
    aAR.setCustomizationID (CPeppolMLS.MLS_CUSTOMIZATION_ID);
    aAR.setProfileID (CPeppolMLS.MLS_PROFILE_ID);
    PeppolMLSBuilder.createForApplicationResponse (aAR);
  }

  @Test
  public void testBuildVerifiesCustomizationAndProfileID ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                         .referenceId ("SBDH-VERIFY-001")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:verify-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:verify-receiver"))
                                                         .build ();
    assertEquals (CPeppolMLS.MLS_CUSTOMIZATION_ID, aMLS.getCustomizationIDValue ());
    assertEquals (CPeppolMLS.MLS_PROFILE_ID, aMLS.getProfileIDValue ());
  }

  @Test
  public void testBuildResponseCodeInOutput ()
  {
    // Acceptance
    {
      final ApplicationResponseType aMLS = PeppolMLSBuilder.acceptance ()
                                                           .referenceId ("ref")
                                                           .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:s"))
                                                           .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:r"))
                                                           .build ();
      assertEquals ("AP", aMLS.getDocumentResponse ().get (0).getResponse ().getResponseCodeValue ());
    }
    // Acknowledging
    {
      final ApplicationResponseType aMLS = PeppolMLSBuilder.acknowledging ()
                                                           .referenceId ("ref")
                                                           .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:s"))
                                                           .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:r"))
                                                           .build ();
      assertEquals ("AB", aMLS.getDocumentResponse ().get (0).getResponse ().getResponseCodeValue ());
    }
    // Rejection
    {
      final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                           .referenceId ("ref")
                                                           .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:s"))
                                                           .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:r"))
                                                           .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("f")
                                                                                                                .addResponse (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY,
                                                                                                                              "failed"))
                                                           .build ();
      assertEquals ("RE", aMLS.getDocumentResponse ().get (0).getResponse ().getResponseCodeValue ());
    }
  }

  @Test
  public void testRoundTripWithResponseText ()
  {
    final ApplicationResponseType aMLS = PeppolMLSBuilder.rejection ()
                                                         .referenceId ("SBDH-RT-001")
                                                         .responseText ("The invoice was rejected due to missing fields")
                                                         .senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:rt-sender"))
                                                         .receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme ("9915:rt-receiver"))
                                                         .addLineResponse (new PeppolMLSLineResponseBuilder ().errorField ("Invoice/TaxTotal")
                                                                                                              .addResponse (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL,
                                                                                                                            "Tax total is missing"))
                                                         .build ();
    assertNotNull (aMLS);

    final PeppolMLSBuilder aReverse = PeppolMLSBuilder.createForApplicationResponse (aMLS);
    assertEquals (EPeppolMLSResponseCode.REJECTION, aReverse.responseCode ());
    assertEquals ("The invoice was rejected due to missing fields", aReverse.responseText ());
    assertEquals ("SBDH-RT-001", aReverse.referenceId ());

    final ApplicationResponseType aMLS2 = aReverse.build ();
    assertEquals (new PeppolMLSMarshaller ().getAsString (aMLS), new PeppolMLSMarshaller ().getAsString (aMLS2));
  }

  @Test
  public void testIssueDateNull ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueDate ((LocalDate) null);
    assertNull (aBuilder.issueDate ());
  }

  @Test
  public void testIssueTimeNull ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.issueTime ((XMLOffsetTime) null);
    assertNull (aBuilder.issueTime ());
  }

  @Test
  public void testSenderParticipantIDNull ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.senderParticipantID ((IParticipantIdentifier) null);
    assertNull (aBuilder.senderParticipantID ());
  }

  @Test
  public void testReceiverParticipantIDNull ()
  {
    final PeppolMLSBuilder aBuilder = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    aBuilder.receiverParticipantID ((IParticipantIdentifier) null);
    assertNull (aBuilder.receiverParticipantID ());
  }
}
