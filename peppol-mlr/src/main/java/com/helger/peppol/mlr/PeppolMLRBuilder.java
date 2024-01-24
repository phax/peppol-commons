/*
 * Copyright (C) 2023-2024 Philip Helger
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
package com.helger.peppol.mlr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.builder.IBuilder;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.phive.api.result.ValidationResultList;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.EndpointIDType;

/**
 * Builder for a Peppol MLR. Fill all the fields and call {@link #build()} at
 * the end.
 *
 * @author Philip Helger
 */
public class PeppolMLRBuilder implements IBuilder <ApplicationResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLRBuilder.class);

  private final EPeppolMLRResponseCode m_eResponseCode;
  private String m_sID;
  private LocalDate m_aIssueDate;
  private LocalTime m_aIssueTime;
  private IParticipantIdentifier m_aSenderPID;
  private IParticipantIdentifier m_aReceiverPID;
  private String m_sReferenceID;
  private String m_sReferenceTypeCode;
  private String m_sResponseText;
  private final ICommonsList <LineResponseType> m_aLineResponses = new CommonsArrayList <> ();

  public PeppolMLRBuilder (@Nonnull final EPeppolMLRResponseCode eResponseCode)
  {
    ValueEnforcer.notNull (eResponseCode, "ResponseCode");
    m_eResponseCode = eResponseCode;
  }

  /**
   * Set the ID of the MLR document to a random UUID.
   *
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRBuilder randomID ()
  {
    return id (UUID.randomUUID ().toString ());
  }

  /**
   * Set the ID of the MLR document to be send out. Mandatory element.
   *
   * @param s
   *        MLR ID
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRBuilder id (@Nullable final String s)
  {
    m_sID = s;
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder issueDateNow ()
  {
    return issueDate (PDTFactory.getCurrentLocalDate ());
  }

  @Nonnull
  public PeppolMLRBuilder issueDate (@Nullable final LocalDate a)
  {
    m_aIssueDate = a;
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder issueTimeNow ()
  {
    return issueTime (PDTFactory.getCurrentLocalTimeMillisOnly ());
  }

  @Nonnull
  public PeppolMLRBuilder issueTime (@Nullable final LocalTime a)
  {
    m_aIssueTime = a;
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder issueDateTimeNow ()
  {
    final LocalDateTime aLDT = PDTFactory.getCurrentLocalDateTimeMillisOnly ();
    return issueDate (aLDT.toLocalDate ()).issueTime (aLDT.toLocalTime ());
  }

  @Nonnull
  public PeppolMLRBuilder senderParticipantID (@Nullable final IParticipantIdentifier a)
  {
    m_aSenderPID = a;
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder receiverParticipantID (@Nullable final IParticipantIdentifier a)
  {
    m_aReceiverPID = a;
    return this;
  }

  /**
   * Set the ID of the message we're referencing. This MUST be the Instance
   * Identifier of the SBDH of the source message.
   *
   * @param s
   *        Instance Identifier of the source message SBDH
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRBuilder referenceId (@Nullable final String s)
  {
    m_sReferenceID = s;
    return this;
  }

  /**
   * Set the type code of the message we're referencing. This is optional.
   *
   * @param s
   *        Type code of the source message (like <code>380</code> for an
   *        invoice)
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRBuilder referenceTypeCode (@Nullable final String s)
  {
    m_sReferenceTypeCode = s;
    return this;
  }

  /**
   * Set the response text returned to the sender. This is e.g. the reason for
   * rejection. This must be human readable text. The text may be multiline.
   *
   * @param s
   *        Response text.
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRBuilder responseText (@Nullable final String s)
  {
    m_sResponseText = s;
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder addLineResponse (@Nullable final PeppolMLRLineResponseBuilder a)
  {
    return addLineResponse (a == null ? null : a.build ());
  }

  @Nonnull
  public PeppolMLRBuilder addLineResponse (@Nullable final LineResponseType a)
  {
    if (a != null)
      m_aLineResponses.add (a);
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder lineResponses (@Nullable final LineResponseType... a)
  {
    m_aLineResponses.setAll (a);
    return this;
  }

  @Nonnull
  public PeppolMLRBuilder lineResponses (@Nullable final Iterable <? extends LineResponseType> a)
  {
    m_aLineResponses.setAll (a);
    return this;
  }

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    if (StringHelper.hasNoText (m_sID))
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR ID is missing");
      return false;
    }

    // Date is mandatory, time is optional
    if (m_aIssueDate == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR Issue Date is missing");
      return false;
    }

    if (m_aSenderPID == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR Sender Participant ID is missing");
      return false;
    }

    if (m_aReceiverPID == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR Receiver Participant ID is missing");
      return false;
    }

    if (StringHelper.hasNoText (m_sReferenceID))
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR Reference ID is missing");
      return false;
    }
    return true;
  }

  @Nonnull
  public ApplicationResponseType build ()
  {
    if (!areAllFieldsSet (true))
      throw new IllegalStateException ("Not all mandatory fields for MLR are set");

    final ApplicationResponseType ret = new ApplicationResponseType ();
    ret.setCustomizationID (CPeppolMLR.MLR_CUSTOMIZATION_ID);
    ret.setProfileID (CPeppolMLR.MLR_PROFILE_ID);
    ret.setID (m_sID);
    ret.setIssueDate (m_aIssueDate);
    if (m_aIssueTime != null)
      ret.setIssueTime (m_aIssueTime);

    {
      final String sValue = m_aSenderPID.getValue ();
      final String [] aParts = StringHelper.getExplodedArray (':', sValue, 2);
      final PartyType aParty = new PartyType ();
      final EndpointIDType aEndpointID = new EndpointIDType ();
      if (aParts.length == 2)
      {
        aEndpointID.setSchemeID (aParts[0]);
        aEndpointID.setValue (aParts[1]);
      }
      else
      {
        aEndpointID.setValue (aParts[0]);
      }
      aParty.setEndpointID (aEndpointID);
      ret.setSenderParty (aParty);
    }

    {
      final String sValue = m_aReceiverPID.getValue ();
      final String [] aParts = StringHelper.getExplodedArray (':', sValue, 2);
      final PartyType aParty = new PartyType ();
      final EndpointIDType aEndpointID = new EndpointIDType ();
      if (aParts.length == 2)
      {
        aEndpointID.setSchemeID (aParts[0]);
        aEndpointID.setValue (aParts[1]);
      }
      else
      {
        aEndpointID.setValue (aParts[0]);
      }
      aParty.setEndpointID (aEndpointID);
      ret.setReceiverParty (aParty);
    }

    {
      final DocumentResponseType aDocResponse = new DocumentResponseType ();
      {
        final ResponseType aResponse = new ResponseType ();
        aResponse.setResponseCode (m_eResponseCode.getID ());
        if (StringHelper.hasText (m_sResponseText))
          aResponse.addDescription (new DescriptionType (m_sResponseText));
        aDocResponse.setResponse (aResponse);
      }

      {
        final DocumentReferenceType aDocRef = new DocumentReferenceType ();
        aDocRef.setID (m_sReferenceID);
        if (StringHelper.hasText (m_sReferenceTypeCode))
          aDocRef.setDocumentTypeCode (m_sReferenceTypeCode);
        aDocResponse.addDocumentReference (aDocRef);
      }

      if (m_aLineResponses.isNotEmpty ())
        aDocResponse.setLineResponse (m_aLineResponses);

      ret.addDocumentResponse (aDocResponse);
    }

    return ret;
  }

  private static void _init (@Nonnull final PeppolMLRBuilder aBuilder)
  {
    aBuilder.randomID ().issueDateTimeNow ();
  }

  @Nonnull
  public static PeppolMLRBuilder acceptance ()
  {
    final PeppolMLRBuilder ret = new PeppolMLRBuilder (EPeppolMLRResponseCode.ACCEPTANCE);
    _init (ret);
    return ret;
  }

  @Nonnull
  public static PeppolMLRBuilder acknowledging ()
  {
    final PeppolMLRBuilder ret = new PeppolMLRBuilder (EPeppolMLRResponseCode.ACKNOWLEDGING);
    _init (ret);
    return ret;
  }

  @Nonnull
  public static PeppolMLRBuilder rejection ()
  {
    final PeppolMLRBuilder ret = new PeppolMLRBuilder (EPeppolMLRResponseCode.REJECTION);
    _init (ret);
    return ret;
  }

  /**
   * Create a predefined Peppol MLR builder based on the validation result list.
   * If the list contains no error, {@link #acceptance()} is returned else
   * {@link #rejection()} with the pre-filled lines is returned. Sender,
   * Receiver and Reference ID need to be set manually anyway.
   *
   * @param aVRL
   *        The Validation result list to evaluate. May not be
   *        <code>null</code>.
   * @return A new {@link PeppolMLRBuilder} and never <code>null</code>.
   */
  @Nonnull
  public static PeppolMLRBuilder createForValidationResultList (@Nonnull final ValidationResultList aVRL)
  {
    ValueEnforcer.notNull (aVRL, "ValidationResultList");

    // Overall status
    final PeppolMLRBuilder aMLRBuilder = aVRL.containsNoError () ? acceptance () : rejection ();

    // Add each warning/error
    final Locale aDisplayLocale = Locale.US;
    aVRL.forEachFlattened (aError -> {
      // Single error or warning?
      final PeppolMLRLineResponseBuilder aLineBuilder = aError.isError () ? PeppolMLRLineResponseBuilder.rejection ()
                                                                                                        .statusReasonCodeBusinessRuleViolationFatal ()
                                                                          : PeppolMLRLineResponseBuilder.acknowledging ()
                                                                                                        .statusReasonCodeBusinessRuleViolationWarning ();
      aMLRBuilder.addLineResponse (aLineBuilder.errorField (aError.getErrorFieldName ())
                                               .description (StringHelper.getConcatenatedOnDemand (aError.getErrorID (),
                                                                                                   " - ",
                                                                                                   aError.getErrorText (aDisplayLocale))));
    });
    return aMLRBuilder;
  }
}
