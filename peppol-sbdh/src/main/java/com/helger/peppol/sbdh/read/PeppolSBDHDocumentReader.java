/*
 * Copyright (C) 2014-2023 Philip Helger
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
package com.helger.peppol.sbdh.read;

import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.BusinessScope;
import org.unece.cefact.namespaces.sbdh.DocumentIdentification;
import org.unece.cefact.namespaces.sbdh.PartnerIdentification;
import org.unece.cefact.namespaces.sbdh.Scope;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocumentHeader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.PeppolSBDHAdditionalAttributes;
import com.helger.peppol.sbdh.PeppolSBDHDocument;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.sbdh.SBDMarshaller;

/**
 * Main class to read standard business documents and extract the Peppol
 * required data out of it.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolSBDHDocumentReader
{
  public static final boolean DEFAULT_PERFORM_VALUE_CHECKS = true;
  public static final String DEFAULT_COUNTRY_CODE_REGEX = "[A-Z0-9][A-Z0-9]";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSBDHDocumentReader.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  private boolean m_bPerformValueChecks = DEFAULT_PERFORM_VALUE_CHECKS;

  @Deprecated
  public PeppolSBDHDocumentReader ()
  {
    this (SimpleIdentifierFactory.INSTANCE);
  }

  public PeppolSBDHDocumentReader (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * @return The identifier provided in the constructor. Never
   *         <code>null</code>.
   * @since 8.2.3
   */
  @Nonnull
  public final IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIdentifierFactory;
  }

  /**
   * @return <code>true</code> if value checks are enabled, <code>false</code>
   *         if not. By default checks are enabled - see
   *         {@link #DEFAULT_PERFORM_VALUE_CHECKS}.
   * @since 8.2.3
   */
  public final boolean isPerformValueChecks ()
  {
    return m_bPerformValueChecks;
  }

  /**
   * Enable or disable the performing of value checks.
   *
   * @param bPerformValueChecks
   *        <code>true</code> to enable checks, <code>false</code> to disable
   *        them.
   * @return this for chaining
   * @since 8.2.3
   */
  @Nonnull
  public final PeppolSBDHDocumentReader setPerformValueChecks (final boolean bPerformValueChecks)
  {
    m_bPerformValueChecks = bPerformValueChecks;
    return this;
  }

  /**
   * Check if the passed header version is valid or not. By default is must
   * match {@link CPeppolSBDH#HEADER_VERSION}. Override this method to allow for
   * other schemes as well.
   *
   * @param sHeaderVersion
   *        The value to be checked. This is the content of the XML element
   *        <code>HeaderVersion</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidHeaderVersion (@Nullable final String sHeaderVersion)
  {
    return CPeppolSBDH.HEADER_VERSION.equals (sHeaderVersion);
  }

  /**
   * Check if the passed sender authority is valid or not. By default is must
   * match {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. Override
   * this method to allow for other schemes as well.
   *
   * @param sSenderAuthority
   *        The value to be checked. This is the content of the XML attribute
   *        <code>Sender/Identifier/@Authority</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidSenderAuthority (@Nullable final String sSenderAuthority)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sSenderAuthority);
  }

  /**
   * Check if the passed sender identifier is valid or not. By default is must
   * not be empty. Override this method to perform further checks.
   *
   * @param sSenderAuthority
   *        The authority of the sender that was already validated with
   *        {@link #isValidSenderAuthority(String)}. This parameter is present
   *        to allow for different identifier checks for different authorities.
   *        May be <code>null</code>.
   * @param sSenderIdentifier
   *        The value to be checked. This conforms to the XML element value of
   *        <code>Sender/Identifier</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid for the given authority,
   *         <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidSenderIdentifier (@Nullable final String sSenderAuthority, @Nullable final String sSenderIdentifier)
  {
    return StringHelper.hasText (sSenderIdentifier);
  }

  /**
   * Check if the passed receiver authority is valid or not. By default is must
   * match {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. Override
   * this method to allow for other schemes as well.
   *
   * @param sReceiverAuthority
   *        The value to be checked. This is the content of the XML attribute
   *        <code>Receiver/Identifier/@Authority</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidReceiverAuthority (@Nullable final String sReceiverAuthority)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sReceiverAuthority);
  }

  /**
   * Check if the passed receiver identifier is valid or not. By default is must
   * not be empty. Override this method to perform further checks.
   *
   * @param sReceiverAuthority
   *        The authority of the receiver that was already validated with
   *        {@link #isValidReceiverAuthority(String)}. This parameter is present
   *        to allow for different identifier checks for different authorities.
   *        May be <code>null</code>.
   * @param sReceiverIdentifier
   *        The value to be checked. This conforms to the XML element value of
   *        <code>Receiver/Identifier</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid for the given authority,
   *         <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidReceiverIdentifier (@Nullable final String sReceiverAuthority, @Nullable final String sReceiverIdentifier)
  {
    return StringHelper.hasText (sReceiverIdentifier);
  }

  /**
   * Check if the passed document type identifier is valid or not. By default it
   * must not be empty. Override this method to perform further checks.
   *
   * @param sDocumentTypeIdentifier
   *        The value to be checked excluding the Peppol identifier scheme. This
   *        conforms to the XML element value of
   *        <code>BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        . May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidDocumentTypeIdentifier (@Nullable final String sDocumentTypeIdentifier)
  {
    return StringHelper.hasText (sDocumentTypeIdentifier);
  }

  /**
   * Check if the passed process identifier is valid or not. By default is must
   * not be empty. Override this method to perform further checks.
   *
   * @param sProcessIdentifier
   *        The value to be checked excluding the Peppol identifier scheme. This
   *        conforms to the XML element value of
   *        <code>BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        . May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidProcessIdentifier (@Nullable final String sProcessIdentifier)
  {
    return StringHelper.hasText (sProcessIdentifier);
  }

  /**
   * Check if the passed C1 country code is valid or not. By default is must
   * follow the regular expression provided in the Peppol specification.
   * Override this method to perform further checks.
   *
   * @param sCountryC1
   *        The value to be checked excluding the Peppol identifier scheme. This
   *        conforms to the XML element value of
   *        <code>BusinessScope/Scope[Type/text()="COUNTRY_C1"]/InstanceIdentifier</code>
   *        . May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCountryC1 (@Nullable final String sCountryC1)
  {
    return sCountryC1 != null && RegExHelper.stringMatchesPattern (DEFAULT_COUNTRY_CODE_REGEX, sCountryC1);
  }

  /**
   * Check if the passed business message is valid or not. By default this
   * method always returns <code>true</code> since the element is never
   * <code>null</code> and no UBL specific checks are performed. Override this
   * method to perform further or other checks.
   *
   * @param aBusinessMessage
   *        The business message element to check against. Never
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidBusinessMessage (@Nonnull final Element aBusinessMessage)
  {
    return true;
  }

  /**
   * Check if the passed document identification standard is valid or not. By
   * default this checks if the standard is the same as the namespace URI of the
   * business message root element. Override this method to perform further or
   * other checks.
   *
   * @param sStandard
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/Standard". May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never
   *        <code>null</code>.
   * @param sDocumentTypeIdentifierValue
   *        The document type identifier value provided. Never
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidStandard (@Nullable final String sStandard,
                                     @Nonnull final Element aBusinessMessage,
                                     @Nonnull final String sDocumentTypeIdentifierValue)
  {
    if (StringHelper.hasNoText (sStandard))
      return false;
    return sStandard.equals (aBusinessMessage.getNamespaceURI ()) && sDocumentTypeIdentifierValue.startsWith (sStandard);
  }

  /**
   * Check if the passed document identification type version is valid or not.
   * By default this refers to the UBL version and must either be "2.0" or
   * "2.1". Override this method to perform further or other checks.
   *
   * @param sTypeVersion
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/TypeVersion". May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never
   *        <code>null</code>.
   * @param sDocumentTypeIdentifierValue
   *        The document type identifier value provided. Never
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidTypeVersion (@Nullable final String sTypeVersion,
                                        @Nonnull final Element aBusinessMessage,
                                        @Nonnull final String sDocumentTypeIdentifierValue)
  {
    if (StringHelper.hasNoText (sTypeVersion))
      return false;

    if (sTypeVersion.indexOf (':') >= 0)
      return false;

    // This is the key thing
    return sDocumentTypeIdentifierValue.endsWith (":" + sTypeVersion);
  }

  /**
   * Check if the passed document identification type is valid or not. By
   * default this checks if the type is the same as the local name of the
   * business message root element. Override this method to perform further or
   * other checks.
   *
   * @param sType
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/Type". May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidType (@Nullable final String sType, @Nonnull final Element aBusinessMessage)
  {
    return EqualsHelper.equals (sType, aBusinessMessage.getLocalName ());
  }

  /**
   * Check if the passed document identification instance identifier is valid or
   * not. By default all non-empty values are valid. Override this method to
   * perform further or other checks.
   *
   * @param sInstanceIdentifier
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/InstanceIdentifier". May be
   *        <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidInstanceIdentifier (@Nullable final String sInstanceIdentifier)
  {
    return StringHelper.hasText (sInstanceIdentifier);
  }

  /**
   * Check if the passed document identification creation date time is valid or
   * not. By default all values are valid as they cannot be <code>null</code>.
   * Override this method to perform further or other checks.
   *
   * @param aCreationDateTime
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/CreationDateAndTime". Is never
   *        <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCreationDateTime (@Nonnull final XMLOffsetDateTime aCreationDateTime)
  {
    return true;
  }

  /**
   * Create a new SBD marshaller used for reading SBD documents. Override this
   * method to customize reading.
   *
   * @return An instance of the {@link SBDMarshaller} and never
   *         <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  protected SBDMarshaller createSBDMarshaller ()
  {
    final SBDMarshaller ret = new SBDMarshaller ();
    // Simply swallow all error messages where possible
    ret.setValidationEventHandlerFactory (x -> null);
    return ret;
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter.
   *
   * @param aStandardBusinessDocument
   *        The input stream to read from. Will be closed by this method. May
   *        not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHDocument extractData (@Nonnull @WillClose final InputStream aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    try
    {
      // Convert to domain object
      final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
      if (aSBD == null)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SBD_XML);

      return extractData (aSBD);
    }
    finally
    {
      StreamHelper.close (aStandardBusinessDocument);
    }
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter.
   *
   * @param aStandardBusinessDocument
   *        The resource to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHDocument extractData (@Nonnull final IReadableResource aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Convert to domain object
    final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
    if (aSBD == null)
      throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SBD_XML);

    return extractData (aSBD);
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter.
   *
   * @param aStandardBusinessDocument
   *        The DOM node to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHDocument extractData (@Nonnull final Node aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Convert to domain object
    final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
    if (aSBD == null)
      throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SBD_XML);

    return extractData (aSBD);
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter.
   *
   * @param aSBDH
   *        The header object to read from. May not be <code>null</code>.
   * @param aBusinessMessage
   *        The main business message (XML payload) to extract data from. May
   *        not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHDocument extractData (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                                         @Nonnull final Element aBusinessMessage) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    final PeppolSBDHDocument ret = new PeppolSBDHDocument (m_aIdentifierFactory);

    // Check that the header version is correct
    if (m_bPerformValueChecks)
      if (!isValidHeaderVersion (aSBDH.getHeaderVersion ()))
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_HEADER_VERSION, aSBDH.getHeaderVersion ());

    // Check sender
    {
      if (aSBDH.getSenderCount () != 1)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SENDER_COUNT,
                                                   Integer.toString (aSBDH.getSenderCount ()));

      // Identifier is mandatory
      final PartnerIdentification aSenderIdentification = aSBDH.getSenderAtIndex (0).getIdentifier ();
      if (m_bPerformValueChecks)
        if (!isValidSenderAuthority (aSenderIdentification.getAuthority ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SENDER_AUTHORITY,
                                                     aSenderIdentification.getAuthority ());

      // Check sender identifier value
      if (m_bPerformValueChecks)
        if (!isValidSenderIdentifier (aSenderIdentification.getAuthority (), aSenderIdentification.getValue ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SENDER_VALUE, aSenderIdentification.getValue ());

      // Remember sender
      ret.setSender (aSenderIdentification.getAuthority (), aSenderIdentification.getValue ());
    }

    // Check receiver
    {
      if (aSBDH.getReceiverCount () != 1)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_RECEIVER_COUNT,
                                                   Integer.toString (aSBDH.getReceiverCount ()));

      // Identifier is mandatory
      final PartnerIdentification aReceiverIdentification = aSBDH.getReceiverAtIndex (0).getIdentifier ();
      if (m_bPerformValueChecks)
        if (!isValidReceiverAuthority (aReceiverIdentification.getAuthority ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_RECEIVER_AUTHORITY,
                                                     aReceiverIdentification.getAuthority ());

      // Check receiver identifier value
      if (m_bPerformValueChecks)
        if (!isValidReceiverIdentifier (aReceiverIdentification.getAuthority (), aReceiverIdentification.getValue ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_RECEIVER_VALUE,
                                                     aReceiverIdentification.getValue ());

      ret.setReceiver (aReceiverIdentification.getAuthority (), aReceiverIdentification.getValue ());
    }

    // Document type identifier and process identifier
    {
      final BusinessScope aBusinessScope = aSBDH.getBusinessScope ();
      if (aBusinessScope == null)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.BUSINESS_SCOPE_MISSING);

      // Check that at least 2 "Scope" elements are present
      if (aBusinessScope.getScopeCount () < 2)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_SCOPE_COUNT,
                                                   Integer.toString (aBusinessScope.getScopeCount ()));

      boolean bFoundDocumentIDScope = false;
      boolean bFoundProcessIDScope = false;
      boolean bFoundCountryC1 = false;
      for (final Scope aScope : aBusinessScope.getScope ())
      {
        final String sType = aScope.getType ();
        final String sInstanceIdentifier = aScope.getInstanceIdentifier ();
        if (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID.equals (sType))
        {
          if (m_bPerformValueChecks)
            if (!isValidDocumentTypeIdentifier (sInstanceIdentifier))
              throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_DOCUMENT_TYPE_IDENTIFIER,
                                                         sInstanceIdentifier);

          // The scheme was added in Spec v1.1
          String sScheme = aScope.getIdentifier ();
          if (sScheme == null)
            sScheme = PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS;

          ret.setDocumentType (sScheme, sInstanceIdentifier);
          bFoundDocumentIDScope = true;
        }
        else
          if (CPeppolSBDH.SCOPE_PROCESS_ID.equals (sType))
          {
            if (m_bPerformValueChecks)
              if (!isValidProcessIdentifier (sInstanceIdentifier))
                throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_PROCESS_IDENTIFIER, sInstanceIdentifier);

            // The scheme was added in Spec v1.1
            String sScheme = aScope.getIdentifier ();
            if (sScheme == null)
              sScheme = PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME;

            ret.setProcess (sScheme, sInstanceIdentifier);
            bFoundProcessIDScope = true;
          }
          else
            if (CPeppolSBDH.SCOPE_COUNTRY_C1.equals (sType))
            {
              if (m_bPerformValueChecks)
                if (!isValidCountryC1 (sInstanceIdentifier))
                  throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_COUNTRY_C1,
                                                             sInstanceIdentifier);

              ret.setCountryC1 (sInstanceIdentifier);
              bFoundCountryC1 = true;
            }
            else
              // read as additional attributes
              if (!PeppolSBDHAdditionalAttributes.isReservedAttributeName (sType))
              {
                if (StringHelper.hasText (sInstanceIdentifier))
                {
                  // Name and value
                  ret.additionalAttributes ().add (sType, sInstanceIdentifier);
                }
                else
                {
                  // Name only
                  // The problem is that InstanceIdentifier is a mandatory
                  // element and therefore there is no way to differentiate
                  // between empty string and not available
                  ret.additionalAttributes ().add (sType, (String) null);
                }
              }
              else
              {
                // Reserved for future use
                LOGGER.info ("Found a reserved attribute name '" + sType + "' in the SBDH. Ignored.");
              }
      }
      if (!bFoundDocumentIDScope)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.MISSING_DOCUMENT_TYPE_IDENTIFIER);
      if (!bFoundProcessIDScope)
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.MISSING_PROCESS_IDENTIFIER);
      if (!bFoundCountryC1 && CPeppolSBDH.isCountryC1Mandatory ())
        throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.MISSING_COUNTRY_C1);
    }

    // Check document and metadata
    {
      // Extract the main business message first - cannot be null and must be an
      // Element!
      if (m_bPerformValueChecks)
        if (!isValidBusinessMessage (aBusinessMessage))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_BUSINESS_MESSAGE);

      // Set the main business message to the return data
      ret.setBusinessMessage (aBusinessMessage);

      // This field is mandatory in XML
      final DocumentIdentification aDI = aSBDH.getDocumentIdentification ();

      final String sNamespaceURI = aDI.getStandard ();
      if (m_bPerformValueChecks)
        if (!isValidStandard (sNamespaceURI, aBusinessMessage, ret.getDocumentTypeValue ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_STANDARD,
                                                     sNamespaceURI,
                                                     aBusinessMessage.getNamespaceURI (),
                                                     ret.getDocumentTypeValue ());

      final String sTypeVersion = aDI.getTypeVersion ();
      if (m_bPerformValueChecks)
        if (!isValidTypeVersion (sTypeVersion, aBusinessMessage, ret.getDocumentTypeValue ()))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_TYPE_VERSION,
                                                     sTypeVersion,
                                                     ret.getDocumentTypeValue ());

      final String sLocalName = aDI.getType ();
      if (m_bPerformValueChecks)
        if (!isValidType (sLocalName, aBusinessMessage))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_TYPE,
                                                     sLocalName,
                                                     aBusinessMessage.getLocalName ());

      // The unique message ID
      final String sSBDHID = aDI.getInstanceIdentifier ();
      if (m_bPerformValueChecks)
        if (!isValidInstanceIdentifier (sSBDHID))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_INSTANCE_IDENTIFIER, sSBDHID);

      // Mandatory date and time (cannot be null)
      final XMLOffsetDateTime aCreationDateAndTime = aDI.getCreationDateAndTime ();
      if (m_bPerformValueChecks)
        if (!isValidCreationDateTime (aCreationDateAndTime))
          throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.INVALID_CREATION_DATE_TIME,
                                                     String.valueOf (aCreationDateAndTime));
      ret.setDocumentIdentification (sNamespaceURI, sTypeVersion, sLocalName, sSBDHID, aCreationDateAndTime);
    }

    return ret;
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter.
   *
   * @param aStandardBusinessDocument
   *        The domain object to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHDocument extractData (@Nonnull final StandardBusinessDocument aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Grab the header
    final StandardBusinessDocumentHeader aSBDH = aStandardBusinessDocument.getStandardBusinessDocumentHeader ();
    if (aSBDH == null)
      throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.MISSING_SBDH);

    final Element aBusinessMessage = (Element) aStandardBusinessDocument.getAny ();
    return extractData (aSBDH, aBusinessMessage);
  }
}
