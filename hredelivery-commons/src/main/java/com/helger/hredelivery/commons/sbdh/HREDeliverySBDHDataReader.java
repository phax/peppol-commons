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
package com.helger.hredelivery.commons.sbdh;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.DocumentIdentification;
import org.unece.cefact.namespaces.sbdh.PartnerIdentification;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocumentHeader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.helger.annotation.WillClose;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.io.stream.StreamHelper;
import com.helger.base.string.StringHelper;
import com.helger.datetime.xml.XMLOffsetDateTime;
import com.helger.diagnostics.error.IError;
import com.helger.diagnostics.error.SingleError;
import com.helger.diagnostics.error.level.IHasErrorLevel;
import com.helger.diagnostics.error.list.ErrorList;
import com.helger.io.resource.IReadableResource;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.sbdh.SBDMarshaller;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Main class to read standard business documents and extract the HR eDelivery required data out of
 * it.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class HREDeliverySBDHDataReader
{
  public static final boolean DEFAULT_PERFORM_VALUE_CHECKS = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliverySBDHDataReader.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  private boolean m_bPerformValueChecks = DEFAULT_PERFORM_VALUE_CHECKS;

  public HREDeliverySBDHDataReader (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");

    m_aIdentifierFactory = aIdentifierFactory;
  }

  /**
   * @return The identifier provided in the constructor. Never <code>null</code>.
   */
  @Nonnull
  public final IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIdentifierFactory;
  }

  /**
   * @return <code>true</code> if value checks on data extraction are enabled, <code>false</code> if
   *         not. By default checks are enabled - see {@link #DEFAULT_PERFORM_VALUE_CHECKS}.
   */
  public final boolean isPerformValueChecks ()
  {
    return m_bPerformValueChecks;
  }

  /**
   * Enable or disable the performing of value checks on data extraction.
   *
   * @param b
   *        <code>true</code> to enable checks, <code>false</code> to disable them.
   * @return this for chaining
   */
  @Nonnull
  public final HREDeliverySBDHDataReader setPerformValueChecks (final boolean b)
  {
    m_bPerformValueChecks = b;
    return this;
  }

  /**
   * Check if the passed header version is valid or not. By default is must match
   * {@link CHREDeliverySBDH#HEADER_VERSION}. Override this method to allow for other schemes as well.
   *
   * @param sHeaderVersion
   *        The value to be checked. This is the content of the XML element
   *        <code>HeaderVersion</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidHeaderVersion (@Nullable final String sHeaderVersion)
  {
    return CHREDeliverySBDH.HEADER_VERSION.equals (sHeaderVersion);
  }

  /**
   * Check if the passed sender authority is valid or not. By default is must match
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. Override this method to allow for
   * other schemes as well.
   *
   * @param sSenderAuthority
   *        The value to be checked. This is the content of the XML attribute
   *        <code>Sender/Identifier/@Authority</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidSenderAuthority (@Nullable final String sSenderAuthority)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sSenderAuthority);
  }

  /**
   * Check if the passed sender identifier is valid or not. By default is must not be empty.
   * Override this method to perform further checks.
   *
   * @param sSenderAuthority
   *        The authority of the sender that was already validated with
   *        {@link #isValidSenderAuthority(String)}. This parameter is present to allow for
   *        different identifier checks for different authorities. May be <code>null</code>.
   * @param sSenderIdentifier
   *        The value to be checked. This conforms to the XML element value of
   *        <code>Sender/Identifier</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid for the given authority, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidSenderIdentifier (@Nullable final String sSenderAuthority,
                                             @Nullable final String sSenderIdentifier)
  {
    return StringHelper.isNotEmpty (sSenderIdentifier);
  }

  /**
   * Check if the passed receiver authority is valid or not. By default is must match
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. Override this method to allow for
   * other schemes as well.
   *
   * @param sReceiverAuthority
   *        The value to be checked. This is the content of the XML attribute
   *        <code>Receiver/Identifier/@Authority</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidReceiverAuthority (@Nullable final String sReceiverAuthority)
  {
    return PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME.equals (sReceiverAuthority);
  }

  /**
   * Check if the passed receiver identifier is valid or not. By default is must not be empty.
   * Override this method to perform further checks.
   *
   * @param sReceiverAuthority
   *        The authority of the receiver that was already validated with
   *        {@link #isValidReceiverAuthority(String)}. This parameter is present to allow for
   *        different identifier checks for different authorities. May be <code>null</code>.
   * @param sReceiverIdentifier
   *        The value to be checked. This conforms to the XML element value of
   *        <code>Receiver/Identifier</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid for the given authority, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidReceiverIdentifier (@Nullable final String sReceiverAuthority,
                                               @Nullable final String sReceiverIdentifier)
  {
    return StringHelper.isNotEmpty (sReceiverIdentifier);
  }

  /**
   * Check if the passed business message is valid or not. By default this method always returns
   * <code>true</code> since the element is never <code>null</code> and no UBL specific checks are
   * performed. Override this method to perform further or other checks.
   *
   * @param aBusinessMessage
   *        The business message element to check against. Never <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidBusinessMessage (@Nonnull final Element aBusinessMessage)
  {
    return aBusinessMessage != null;
  }

  /**
   * Check if the passed document identification standard is valid or not. By default this checks if
   * the standard is the same as the namespace URI of the business message root element. Override
   * this method to perform further or other checks.
   *
   * @param sStandard
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/Standard". May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never <code>null</code>.
   * @param sDocumentTypeIdentifierValue
   *        The document type identifier value provided. Never <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidStandard (@Nullable final String sStandard,
                                     @Nonnull final Element aBusinessMessage,
                                     @Nonnull final String sDocumentTypeIdentifierValue)
  {
    if (StringHelper.isEmpty (sStandard))
      return false;
    return sStandard.equals (aBusinessMessage.getNamespaceURI ()) &&
           sDocumentTypeIdentifierValue.startsWith (sStandard);
  }

  /**
   * Check if the passed document identification type version is valid or not. By default this
   * refers to the UBL version and must either be "2.0" or "2.1". Override this method to perform
   * further or other checks.
   *
   * @param sTypeVersion
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/TypeVersion". May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never <code>null</code>.
   * @param sDocumentTypeIdentifierValue
   *        The document type identifier value provided. Never <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidTypeVersion (@Nullable final String sTypeVersion,
                                        @Nonnull final Element aBusinessMessage,
                                        @Nonnull final String sDocumentTypeIdentifierValue)
  {
    if (StringHelper.isEmpty (sTypeVersion))
      return false;

    if (sTypeVersion.indexOf (':') >= 0)
      return false;

    // This is the key thing
    return sDocumentTypeIdentifierValue.endsWith (":" + sTypeVersion);
  }

  /**
   * Check if the passed document identification type is valid or not. By default this checks if the
   * type is the same as the local name of the business message root element. Override this method
   * to perform further or other checks.
   *
   * @param sType
   *        The value to be checked. This corresponds to the field "DocumentIdentification/Type".
   *        May be <code>null</code>.
   * @param aBusinessMessage
   *        The business message element to check against. Never <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidType (@Nullable final String sType, @Nonnull final Element aBusinessMessage)
  {
    return EqualsHelper.equals (sType, aBusinessMessage.getLocalName ());
  }

  /**
   * Check if the passed document identification instance identifier is valid or not. By default all
   * non-empty values are valid. Override this method to perform further or other checks.
   *
   * @param sInstanceIdentifier
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/InstanceIdentifier". May be <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidInstanceIdentifier (@Nullable final String sInstanceIdentifier)
  {
    return StringHelper.isNotEmpty (sInstanceIdentifier);
  }

  /**
   * Check if the passed document identification creation date time is valid or not. By default all
   * values are valid as they cannot be <code>null</code>. Override this method to perform further
   * or other checks.
   *
   * @param aCreationDateTime
   *        The value to be checked. This corresponds to the field
   *        "DocumentIdentification/CreationDateAndTime". Is never <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code> otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCreationDateTime (@Nonnull final XMLOffsetDateTime aCreationDateTime)
  {
    return true;
  }

  /**
   * Create a new SBD marshaller used for reading SBD documents. Override this method to customize
   * reading.
   *
   * @return An instance of the {@link SBDMarshaller} and never <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  protected SBDMarshaller createSBDMarshaller ()
  {
    final SBDMarshaller ret = new SBDMarshaller ();
    // Simply swallow all error messages where possible
    ret.setValidationEventHandler (null);
    return ret;
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter.
   *
   * @param aStandardBusinessDocument
   *        The input stream to read from. Will be closed by this method. May not be
   *        <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws HREDeliverySBDHDataReadException
   *         In case the passed Standard Business Document does not conform to the Peppol rules.
   */
  @Nonnull
  public HREDeliverySBDHData extractData (@Nonnull @WillClose final InputStream aStandardBusinessDocument) throws HREDeliverySBDHDataReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    try
    {
      // Convert to domain object
      final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
      if (aSBD == null)
        throw new HREDeliverySBDHDataReadException (EHREDeliverySBDHDataError.INVALID_SBD_XML);

      return extractData (aSBD);
    }
    finally
    {
      StreamHelper.close (aStandardBusinessDocument);
    }
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter.
   *
   * @param aStandardBusinessDocument
   *        The resource to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws HREDeliverySBDHDataReadException
   *         In case the passed Standard Business Document does not conform to the Peppol rules.
   */
  @Nonnull
  public HREDeliverySBDHData extractData (@Nonnull final IReadableResource aStandardBusinessDocument) throws HREDeliverySBDHDataReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Convert to domain object
    final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
    if (aSBD == null)
      throw new HREDeliverySBDHDataReadException (EHREDeliverySBDHDataError.INVALID_SBD_XML);

    return extractData (aSBD);
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter.
   *
   * @param aStandardBusinessDocument
   *        The DOM node to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws HREDeliverySBDHDataReadException
   *         In case the passed Standard Business Document does not conform to the Peppol rules.
   */
  @Nonnull
  public HREDeliverySBDHData extractData (@Nonnull final Node aStandardBusinessDocument) throws HREDeliverySBDHDataReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Convert to domain object
    final StandardBusinessDocument aSBD = createSBDMarshaller ().read (aStandardBusinessDocument);
    if (aSBD == null)
      throw new HREDeliverySBDHDataReadException (EHREDeliverySBDHDataError.INVALID_SBD_XML);

    return extractData (aSBD);
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter.
   *
   * @param aStandardBusinessDocument
   *        The domain object to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws HREDeliverySBDHDataReadException
   *         In case the passed Standard Business Document does not conform to the Peppol rules.
   */
  @Nonnull
  public HREDeliverySBDHData extractData (@Nonnull final StandardBusinessDocument aStandardBusinessDocument) throws HREDeliverySBDHDataReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Grab the header
    final StandardBusinessDocumentHeader aSBDH = aStandardBusinessDocument.getStandardBusinessDocumentHeader ();
    if (aSBDH == null)
      throw new HREDeliverySBDHDataReadException (EHREDeliverySBDHDataError.MISSING_SBDH);

    final Element aBusinessMessage = (Element) aStandardBusinessDocument.getAny ();
    return extractData (aSBDH, aBusinessMessage);
  }

  @Nonnull
  private static IError _toError (@Nullable final String sErrorField,
                                  @Nonnull final EHREDeliverySBDHDataError e,
                                  @Nullable final Object... aArgs)
  {
    return SingleError.builderError ()
                      .errorFieldName (sErrorField)
                      .errorID (e.getID ())
                      .errorText (aArgs == null ? e.getErrorMessage () : e.getErrorMessage (aArgs))
                      .build ();
  }

  /**
   * Validate the provided SBDH and the Business Message according to the Peppol rules and store the
   * results in an Error List.
   *
   * @param aSBDH
   *        The SBDH to be validated. Must not be <code>null</code>.
   * @param aBusinessMessage
   *        The Business Message to be validated (this does NOT mean Schematron validation). Must
   *        not be <code>null</code>.
   * @param aErrorList
   *        The error list to be filled. Must not be <code>null</code>.
   */
  public void validateData (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                            @Nonnull final Element aBusinessMessage,
                            @Nonnull final ErrorList aErrorList)
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");
    ValueEnforcer.notNull (aErrorList, "ErrorList");

    // Check that the header version is correct
    if (!isValidHeaderVersion (aSBDH.getHeaderVersion ()))
      aErrorList.add (_toError ("SBDH/HeaderVersion",
                                EHREDeliverySBDHDataError.INVALID_HEADER_VERSION,
                                aSBDH.getHeaderVersion ()));

    // Check sender
    {
      final int nSenderCount = aSBDH.getSenderCount ();
      if (nSenderCount != 1)
        aErrorList.add (_toError ("SBDH", EHREDeliverySBDHDataError.INVALID_SENDER_COUNT, Integer.toString (nSenderCount)));

      if (nSenderCount > 0)
      {
        // Identifier is mandatory
        final PartnerIdentification aSenderIdentification = aSBDH.getSenderAtIndex (0).getIdentifier ();
        if (aSenderIdentification != null)
        {
          final String sScheme = aSenderIdentification.getAuthority ();
          if (!isValidSenderAuthority (sScheme))
          {
            aErrorList.add (_toError ("SBDH/Sender[1]/Identifier/Authority",
                                      EHREDeliverySBDHDataError.INVALID_SENDER_AUTHORITY,
                                      sScheme));
          }

          // Check sender identifier value
          final String sValue = aSenderIdentification.getValue ();
          if (!isValidSenderIdentifier (sScheme, sValue))
          {
            aErrorList.add (_toError ("SBDH/Sender[1]/Identifier/Value",
                                      EHREDeliverySBDHDataError.INVALID_SENDER_VALUE,
                                      sValue));
          }
          else
          {
            final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
            if (aPID == null)
              aErrorList.add (_toError ("SBDH/Sender[1]/Identifier",
                                        EHREDeliverySBDHDataError.INVALID_SENDER_VALUE,
                                        CIdentifier.getURIEncoded (sScheme, sValue)));
          }
        }
      }
    }

    // Check receiver
    {
      final int nReceiverCount = aSBDH.getReceiverCount ();
      if (nReceiverCount != 1)
        aErrorList.add (_toError ("SBDH",
                                  EHREDeliverySBDHDataError.INVALID_RECEIVER_COUNT,
                                  Integer.toString (nReceiverCount)));

      if (nReceiverCount > 0)
      {
        // Identifier is mandatory
        final PartnerIdentification aReceiverIdentification = aSBDH.getReceiverAtIndex (0).getIdentifier ();
        if (aReceiverIdentification != null)
        {
          final String sScheme = aReceiverIdentification.getAuthority ();
          if (!isValidReceiverAuthority (sScheme))
          {
            aErrorList.add (_toError ("SBDH/Receiver[1]/Identifier/Authority",
                                      EHREDeliverySBDHDataError.INVALID_RECEIVER_AUTHORITY,
                                      sScheme));
          }

          // Check receiver identifier value
          final String sValue = aReceiverIdentification.getValue ();
          if (!isValidReceiverIdentifier (sScheme, sValue))
          {
            aErrorList.add (_toError ("SBDH/Receiver[1]/Identifier/Value",
                                      EHREDeliverySBDHDataError.INVALID_RECEIVER_VALUE,
                                      sValue));
          }
          else
          {
            final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
            if (aPID == null)
              aErrorList.add (_toError ("SBDH/Receiver[1]/Identifier",
                                        EHREDeliverySBDHDataError.INVALID_RECEIVER_VALUE,
                                        CIdentifier.getURIEncoded (sScheme, sValue)));
          }
        }
      }
    }

    // Check document and metadata
    {
      // Extract the main business message first - cannot be null and must be an
      // Element!
      if (!isValidBusinessMessage (aBusinessMessage))
        aErrorList.add (_toError (null, EHREDeliverySBDHDataError.INVALID_BUSINESS_MESSAGE));

      final DocumentIdentification aDI = aSBDH.getDocumentIdentification ();

      // The unique message ID
      final String sSBDHID = aDI.getInstanceIdentifier ();
      if (!isValidInstanceIdentifier (sSBDHID))
        aErrorList.add (_toError ("SBDH/DocumentIdentification/InstanceIdentifier",
                                  EHREDeliverySBDHDataError.INVALID_INSTANCE_IDENTIFIER,
                                  sSBDHID));

      // Mandatory date and time (cannot be null)
      final XMLOffsetDateTime aCreationDateAndTime = aDI.getCreationDateAndTime ();
      if (!isValidCreationDateTime (aCreationDateAndTime))
        aErrorList.add (_toError ("SBDH/DocumentIdentification/CreationDateAndTime",
                                  EHREDeliverySBDHDataError.INVALID_CREATION_DATE_TIME,
                                  String.valueOf (aCreationDateAndTime)));
    }
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter. Eventually value checks are performed if {@link #isPerformValueChecks()} is
   * <code>true</code>.
   *
   * @param aSBDH
   *        The header object to read from. May not be <code>null</code>.
   * @param aBusinessMessage
   *        The main business message (XML payload) to extract data from. May not be
   *        <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws HREDeliverySBDHDataReadException
   *         In case the passed Standard Business Document does not conform to the Peppol rules.
   */
  @Nonnull
  public HREDeliverySBDHData extractData (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                                     @Nonnull final Element aBusinessMessage) throws HREDeliverySBDHDataReadException
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    if (isPerformValueChecks ())
    {
      // Validate data
      final ErrorList aErrorList = new ErrorList ();
      validateData (aSBDH, aBusinessMessage, aErrorList);

      // Evaluate validation results
      final int nErrors = aErrorList.getErrorCount ();
      if (nErrors > 0)
      {
        // Collect all errors
        final StringBuilder aErrorMsgSB = new StringBuilder ();

        aErrorList.forEach (x -> {
          if (x.isError ())
          {
            final String sMsg = x.getAsStringLocaleIndepdent ();
            LOGGER.error ("Peppol SBDH validation " + sMsg);
            if (aErrorMsgSB.length () > 0)
              aErrorMsgSB.append ('\n');
            aErrorMsgSB.append (sMsg);
          }
        });

        // Find an error code
        final IError aFirst = aErrorList.findFirst (IHasErrorLevel::isError);
        final EHREDeliverySBDHDataError eError = EHREDeliverySBDHDataError.getFromIDOrDefault (aFirst.getErrorID (),
                                                                                     EHREDeliverySBDHDataError.GENERIC_SBDH_ERROR);
        throw new HREDeliverySBDHDataReadException (aErrorMsgSB.toString (), eError);
      }
    }

    return extractDataUnchecked (aSBDH, aBusinessMessage);
  }

  /**
   * Extract the document data from the Standard Business Document represents by the passed
   * parameter without any value checks. This might be handy, if value checks were executed
   * separately.
   *
   * @param aSBDH
   *        The header object to read from. May not be <code>null</code>.
   * @param aBusinessMessage
   *        The main business message (XML payload) to extract data from. May not be
   *        <code>null</code>.
   * @return The document data and never <code>null</code>.
   */
  @Nonnull
  public HREDeliverySBDHData extractDataUnchecked (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                                              @Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");
    final HREDeliverySBDHData ret = new HREDeliverySBDHData (m_aIdentifierFactory);

    // Check sender
    if (aSBDH.hasSenderEntries ())
    {
      // Identifier is mandatory
      final PartnerIdentification aSenderIdentification = aSBDH.getSenderAtIndex (0).getIdentifier ();
      ret.setSender (aSenderIdentification.getAuthority (), aSenderIdentification.getValue ());
    }

    // Check receiver
    if (aSBDH.hasReceiverEntries ())
    {
      // Identifier is mandatory
      final PartnerIdentification aReceiverIdentification = aSBDH.getReceiverAtIndex (0).getIdentifier ();
      ret.setReceiver (aReceiverIdentification.getAuthority (), aReceiverIdentification.getValue ());
    }

    // Set the main business message to the return data
    ret.setBusinessMessage (aBusinessMessage);

    // This field is mandatory in XML
    final DocumentIdentification aDI = aSBDH.getDocumentIdentification ();
    ret.setDocumentIdentification (aDI.getStandard (),
                                   aDI.getTypeVersion (),
                                   aDI.getType (),
                                   aDI.getInstanceIdentifier (),
                                   aDI.getCreationDateAndTime ());
    return ret;
  }
}
