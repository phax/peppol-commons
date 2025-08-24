/*
 * Copyright (C) 2025 Philip Helger
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.rt.OffsetDate;
import com.helger.datetime.xml.XMLOffsetDate;
import com.helger.datetime.xml.XMLOffsetTime;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IParticipantIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.phive.api.result.ValidationResultList;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.EndpointIDType;

/**
 * Builder for a Peppol MLS. Fill all the fields and call {@link #build()} at the end.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolMLSBuilder implements IBuilder <ApplicationResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSBuilder.class);
  private static final IParticipantIdentifierFactory IF = PeppolIdentifierFactory.INSTANCE;

  private final EPeppolMLSResponseCode m_eResponseCode;
  private String m_sID;
  private LocalDate m_aIssueDate;
  private XMLOffsetTime m_aIssueTime;
  private IParticipantIdentifier m_aSenderPID;
  private IParticipantIdentifier m_aReceiverPID;
  private String m_sReferenceID;
  private String m_sReferenceTypeCode;
  private String m_sResponseText;
  private final ICommonsList <LineResponseType> m_aLineResponses = new CommonsArrayList <> ();

  public PeppolMLSBuilder (@Nonnull final EPeppolMLSResponseCode eResponseCode)
  {
    ValueEnforcer.notNull (eResponseCode, "ResponseCode");
    m_eResponseCode = eResponseCode;
  }

  /**
   * Set the ID of the MLS document to a random UUID.
   *
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSBuilder randomID ()
  {
    return id (UUID.randomUUID ().toString ());
  }

  /**
   * Set the ID of the MLS document to be send out. Mandatory element.
   *
   * @param s
   *        MLS ID
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSBuilder id (@Nullable final String s)
  {
    m_sID = s;
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder issueDateNow ()
  {
    return issueDate (PDTFactory.getCurrentLocalDate ());
  }

  @Nonnull
  public PeppolMLSBuilder issueDate (@Nullable final OffsetDate a)
  {
    return issueDate (a == null ? null : a.toLocalDate ());
  }

  @Nonnull
  public PeppolMLSBuilder issueDate (@Nullable final XMLOffsetDate a)
  {
    return issueDate (a == null ? null : a.toLocalDate ());
  }

  @Nonnull
  public PeppolMLSBuilder issueDate (@Nullable final LocalDate a)
  {
    m_aIssueDate = a;
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder issueTimeNow ()
  {
    return issueTime (PDTFactory.getCurrentXMLOffsetTimeMillisOnly ());
  }

  @Nonnull
  public PeppolMLSBuilder issueTime (@Nullable final OffsetTime a)
  {
    return issueTime (a == null ? null : XMLOffsetTime.of (a));
  }

  @Nonnull
  public PeppolMLSBuilder issueTime (@Nullable final LocalTime a, @Nonnull final ZoneOffset aZoneOffset)
  {
    return issueTime (a == null ? null : XMLOffsetTime.of (a, aZoneOffset));
  }

  @Nonnull
  public PeppolMLSBuilder issueTime (@Nullable final XMLOffsetTime a)
  {
    m_aIssueTime = a;
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder issueDateTimeNow ()
  {
    final OffsetDateTime aLDT = PDTFactory.getCurrentOffsetDateTimeMillisOnly ();
    return issueDate (aLDT.toLocalDate ()).issueTime (aLDT.toOffsetTime ());
  }

  @Nonnull
  public PeppolMLSBuilder senderParticipantID (@Nullable final String sValue)
  {
    return senderParticipantID (IF.createParticipantIdentifierWithDefaultScheme (sValue));
  }

  @Nonnull
  public PeppolMLSBuilder senderParticipantID (@Nullable final IParticipantIdentifier a)
  {
    m_aSenderPID = a;
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder receiverParticipantID (@Nullable final String sValue)
  {
    return receiverParticipantID (IF.createParticipantIdentifierWithDefaultScheme (sValue));
  }

  @Nonnull
  public PeppolMLSBuilder receiverParticipantID (@Nullable final IParticipantIdentifier a)
  {
    m_aReceiverPID = a;
    return this;
  }

  /**
   * Set the ID of the message we're referencing. This MUST be the Instance Identifier of the SBDH
   * of the source message.
   *
   * @param s
   *        Instance Identifier of the source message SBDH
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSBuilder referenceId (@Nullable final String s)
  {
    m_sReferenceID = s;
    return this;
  }

  /**
   * Set the type code of the message we're referencing. This is optional.
   *
   * @param s
   *        Type code of the source message (like <code>380</code> for an invoice)
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSBuilder referenceTypeCode (@Nullable final String s)
  {
    m_sReferenceTypeCode = s;
    return this;
  }

  /**
   * Set the response text returned to the sender. This is e.g. the reason for rejection. This must
   * be human readable text. The text may be multiline.
   *
   * @param s
   *        Response text.
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSBuilder responseText (@Nullable final String s)
  {
    m_sResponseText = s;
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder addLineResponse (@Nullable final PeppolMLSLineResponseBuilder a)
  {
    return addLineResponse (a == null ? null : a.build ());
  }

  @Nonnull
  public PeppolMLSBuilder addLineResponse (@Nullable final LineResponseType a)
  {
    if (a != null)
      m_aLineResponses.add (a);
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder lineResponses (@Nullable final LineResponseType... a)
  {
    m_aLineResponses.setAll (a);
    return this;
  }

  @Nonnull
  public PeppolMLSBuilder lineResponses (@Nullable final Iterable <? extends LineResponseType> a)
  {
    m_aLineResponses.setAll (a);
    return this;
  }

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    if (StringHelper.isEmpty (m_sID))
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS ID is missing");
      return false;
    }

    // Date is mandatory
    if (m_aIssueDate == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Issue Date is missing");
      return false;
    }

    // Time is mandatory
    if (m_aIssueTime == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Issue Time is missing");
      return false;
    }

    // Time zone of Time is mandatory
    if (m_aIssueTime.getOffset () == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Issue Time is missing the mandatory Zone Offset");
      return false;
    }

    if (m_aSenderPID == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Sender Participant ID is missing");
      return false;
    }

    if (m_aReceiverPID == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Receiver Participant ID is missing");
      return false;
    }

    if (StringHelper.isEmpty (m_sReferenceID))
    {
      if (bLogDetails)
        LOGGER.warn ("The MLS Reference ID is missing");
      return false;
    }

    if (m_eResponseCode.isFailure ())
    {
      // In case of rejection, at least one line response must be present
      if (m_aLineResponses.isEmpty ())
      {
        if (bLogDetails)
          LOGGER.warn ("The MLS contains a failure response code but no line responses are present");
        return false;
      }
    }

    return true;
  }

  @Nonnull
  public ApplicationResponseType build ()
  {
    if (!areAllFieldsSet (true))
      throw new IllegalStateException ("Not all mandatory fields for MLS are set");

    final ApplicationResponseType ret = new ApplicationResponseType ();
    ret.setCustomizationID (CPeppolMLS.MLS_CUSTOMIZATION_ID);
    ret.setProfileID (CPeppolMLS.MLS_PROFILE_ID);
    ret.setID (m_sID);
    ret.setIssueDate (m_aIssueDate);
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
        if (StringHelper.isNotEmpty (m_sResponseText))
          aResponse.addDescription (new DescriptionType (m_sResponseText));
        aDocResponse.setResponse (aResponse);
      }

      {
        final DocumentReferenceType aDocRef = new DocumentReferenceType ();
        aDocRef.setID (m_sReferenceID);
        if (StringHelper.isNotEmpty (m_sReferenceTypeCode))
          aDocRef.setDocumentTypeCode (m_sReferenceTypeCode);
        aDocResponse.addDocumentReference (aDocRef);
      }

      if (m_aLineResponses.isNotEmpty ())
        aDocResponse.setLineResponse (m_aLineResponses);

      ret.addDocumentResponse (aDocResponse);
    }

    return ret;
  }

  private static void _init (@Nonnull final PeppolMLSBuilder aBuilder)
  {
    aBuilder.randomID ().issueDateTimeNow ();
  }

  @Nonnull
  public static PeppolMLSBuilder acceptance ()
  {
    final PeppolMLSBuilder ret = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACCEPTANCE);
    _init (ret);
    return ret;
  }

  @Nonnull
  public static PeppolMLSBuilder acknowledging ()
  {
    final PeppolMLSBuilder ret = new PeppolMLSBuilder (EPeppolMLSResponseCode.ACKNOWLEDGING);
    _init (ret);
    return ret;
  }

  @Nonnull
  public static PeppolMLSBuilder rejection ()
  {
    final PeppolMLSBuilder ret = new PeppolMLSBuilder (EPeppolMLSResponseCode.REJECTION);
    _init (ret);
    return ret;
  }

  /**
   * Create a predefined Peppol MLS builder based on the validation result list. If the list
   * contains no error, {@link #acceptance()} is returned else {@link #rejection()} with the
   * pre-filled lines is returned. Sender, Receiver and Reference ID need to be set manually anyway.
   *
   * @param aVRL
   *        The Validation result list to evaluate. May not be <code>null</code>.
   * @return A new {@link PeppolMLSBuilder} and never <code>null</code>.
   */
  @Nonnull
  public static PeppolMLSBuilder createForValidationResultList (@Nonnull final ValidationResultList aVRL)
  {
    ValueEnforcer.notNull (aVRL, "ValidationResultList");

    // Overall status
    final PeppolMLSBuilder aMLSBuilder = aVRL.containsNoError () ? acceptance () : rejection ();

    // Add each warning/error
    final Locale aDisplayLocale = Locale.US;
    final ICommonsMap <String, PeppolMLSLineResponseBuilder> aBuilderMap = new CommonsHashMap <> ();
    aVRL.forEachFlattened (aError -> {
      // Single error or warning?
      aBuilderMap.computeIfAbsent (aError.getErrorFieldName (), k -> {
        final PeppolMLSLineResponseBuilder ret = new PeppolMLSLineResponseBuilder ().errorField (k);
        aMLSBuilder.addLineResponse (ret);
        return ret;
      })
                 .addResponse (new PeppolMLSLineResponseResponseBuilder ().statusReasonCode (aError.isError () ? EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL
                                                                                                               : EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING)
                                                                          .description (StringHelper.getConcatenatedOnDemand (aError.getErrorID (),
                                                                                                                              " - ",
                                                                                                                              aError.getErrorText (aDisplayLocale))));
    });
    return aMLSBuilder;
  }

  @Nullable
  private static String _combine (@Nullable final EndpointIDType aEndpoint)
  {
    if (aEndpoint == null)
      return null;
    return StringHelper.getConcatenatedOnDemand (aEndpoint.getSchemeID (), ':', aEndpoint.getValue ());
  }

  /**
   * Fill the builder with the values from the given application response.
   *
   * @param aAR
   *        The application response to load. May not be <code>null</code>.
   * @return A new {@link PeppolMLSBuilder} and never <code>null</code>.
   * @throws IllegalArgumentException
   *         If the given application response is not a valid MLS.
   * @since 10.3.3
   */
  @Nonnull
  public static PeppolMLSBuilder createForApplicationResponse (@Nonnull final ApplicationResponseType aAR)
  {
    ValueEnforcer.notNull (aAR, "ApplicationResponse");

    if (!CPeppolMLS.MLS_CUSTOMIZATION_ID.equals (aAR.getCustomizationIDValue ()))
      throw new IllegalArgumentException ("The given application response is not a valid MLS - wrong customization ID");
    if (!CPeppolMLS.MLS_PROFILE_ID.equals (aAR.getProfileIDValue ()))
      throw new IllegalArgumentException ("The given application response is not a valid MLS - wrong profile ID");
    if (aAR.getDocumentResponse ().size () <= 0)
      throw new IllegalArgumentException ("The given application response is not a valid MLS - exepcted exactly one document responses");

    final PartyType aSenderParty = aAR.getSenderParty ();
    final EndpointIDType aSenderEndpointID = aSenderParty.getEndpointID ();

    final PartyType aReceiverParty = aAR.getReceiverParty ();
    final EndpointIDType aReceiverEndpointID = aReceiverParty.getEndpointID ();

    final DocumentResponseType aDocResponse = aAR.getDocumentResponse ().get (0);
    final ResponseType aResponse = aDocResponse.getResponse ();
    if (aDocResponse.hasNoDocumentReferenceEntries ())
      throw new IllegalArgumentException ("The given application response is not a valid MLS - no document reference is contained");
    final DocumentReferenceType aDocRef = aDocResponse.getDocumentReferenceAtIndex (0);

    final EPeppolMLSResponseCode eResponseCode = EPeppolMLSResponseCode.getFromIDOrThrow (aResponse.getResponseCodeValue ());
    final PeppolMLSBuilder ret = new PeppolMLSBuilder (eResponseCode).id (aAR.getIDValue ())
                                                                     .issueDate (aAR.getIssueDateValue ())
                                                                     .issueTime (aAR.getIssueTimeValue ())
                                                                     .senderParticipantID (_combine (aSenderEndpointID))
                                                                     .receiverParticipantID (_combine (aReceiverEndpointID))
                                                                     .responseText (aResponse.hasDescriptionEntries () ? aResponse.getDescriptionAtIndex (0)
                                                                                                                                  .getValue ()
                                                                                                                       : null)
                                                                     .referenceId (aDocRef.getIDValue ())
                                                                     .referenceTypeCode (aDocRef.getDocumentTypeCodeValue ());
    for (final LineResponseType aLineResponse : aDocResponse.getLineResponse ())
    {
      final PeppolMLSLineResponseBuilder aLineBuilder = PeppolMLSLineResponseBuilder.createForLineResponse (aLineResponse);
      ret.addLineResponse (aLineBuilder);
    }
    return ret;
  }
}
