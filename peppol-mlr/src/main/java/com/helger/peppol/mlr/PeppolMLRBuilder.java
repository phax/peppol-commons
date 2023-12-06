/*
 * Copyright (C) 2023 Philip Helger
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
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.builder.IBuilder;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;

/**
 * Builder for a Peppol MLR. Fill all the fields and call {@link #build()} at
 * the end.
 *
 * @author Philip Helger
 */
public class PeppolMLRBuilder implements IBuilder <ApplicationResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLRBuilder.class);

  private final EPeppolMLRState m_eState;
  private String m_sID;
  private LocalDate m_aIssueDate;
  private LocalTime m_aIssueTime;
  private IParticipantIdentifier m_aSenderPID;
  private IParticipantIdentifier m_aReceiverPID;
  private String m_sReferenceID;
  private String m_sReferenceTypeCode;
  private String m_sResponseText;

  public PeppolMLRBuilder (@Nonnull final EPeppolMLRState eState)
  {
    ValueEnforcer.notNull (eState, "State");
    m_eState = eState;
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

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    if (StringHelper.hasNoText (m_sID))
    {
      if (bLogDetails)
        LOGGER.warn ("The MLR ID is missing");
      return false;
    }
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
      throw new IllegalStateException ("Not all mandatory fields are set");

    final ApplicationResponseType ret = new ApplicationResponseType ();
    ret.setCustomizationID (CPeppolMLR.MLR_CUSTOMIZATION_ID);
    ret.setProfileID (CPeppolMLR.MLR_PROFILE_ID);
    ret.setID (m_sID);
    ret.setIssueDate (m_aIssueDate);
    if (m_aIssueTime != null)
      ret.setIssueTime (m_aIssueTime);

    {
      final PartyType aParty = new PartyType ();
      aParty.setEndpointID (m_aSenderPID.getURIEncoded ());
      ret.setSenderParty (aParty);
    }

    {
      final PartyType aParty = new PartyType ();
      aParty.setEndpointID (m_aReceiverPID.getURIEncoded ());
      ret.setReceiverParty (aParty);
    }

    {
      final DocumentResponseType aDocResponse = new DocumentResponseType ();
      {
        final ResponseType aResponse = new ResponseType ();
        aResponse.setResponseCode (m_eState.getID ());
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
      ret.addDocumentResponse (aDocResponse);
    }
    return ret;
  }
}
