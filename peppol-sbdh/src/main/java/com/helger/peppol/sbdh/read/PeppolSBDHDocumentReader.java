/*
 * Copyright (C) 2014-2024 Philip Helger
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
import java.util.Locale;

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
import com.helger.commons.error.IError;
import com.helger.commons.error.SingleError;
import com.helger.commons.error.level.IHasErrorLevel;
import com.helger.commons.error.list.ErrorList;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.PeppolSBDHAdditionalAttributes;
import com.helger.peppol.sbdh.PeppolSBDHData;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.sbdh.SBDMarshaller;
import com.helger.xsds.peppol.id1.ChangeV10;

/**
 * Main class to read standard business documents and extract the Peppol
 * required data out of it.
 *
 * @author Philip Helger
 */
@NotThreadSafe
@ChangeV10 ("Move to package of PeppolSBDHData")
public class PeppolSBDHDocumentReader
{
  public static final boolean DEFAULT_PERFORM_VALUE_CHECKS = true;
  public static final boolean DEFAULT_CHECK_FOR_COUNTRY_C1 = true;
  public static final String DEFAULT_COUNTRY_CODE_REGEX = "[A-Z0-9][A-Z0-9]";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSBDHDocumentReader.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  private boolean m_bPerformValueChecks = DEFAULT_PERFORM_VALUE_CHECKS;
  private boolean m_bCheckForCountryC1 = DEFAULT_CHECK_FOR_COUNTRY_C1;

  public PeppolSBDHDocumentReader (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");

    m_aIdentifierFactory = aIdentifierFactory;
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
   * @return <code>true</code> if value checks on data extraction are enabled,
   *         <code>false</code> if not. By default checks are enabled - see
   *         {@link #DEFAULT_PERFORM_VALUE_CHECKS}.
   * @since 8.2.3
   */
  public final boolean isPerformValueChecks ()
  {
    return m_bPerformValueChecks;
  }

  /**
   * Enable or disable the performing of value checks on data extraction.
   *
   * @param b
   *        <code>true</code> to enable checks, <code>false</code> to disable
   *        them.
   * @return this for chaining
   * @since 8.2.3
   */
  @Nonnull
  public final PeppolSBDHDocumentReader setPerformValueChecks (final boolean b)
  {
    m_bPerformValueChecks = b;
    return this;
  }

  /**
   * In case of value checks, should the Country C1 also be checked?
   *
   * @return <code>true</code> to check for mandatory country C1,
   *         <code>false</code> to not do it.
   * @since 9.2.2
   */
  public final boolean isCheckForCountryC1 ()
  {
    return m_bCheckForCountryC1;
  }

  /**
   * Enable or disable the checking for C1 country code. This needs to be called
   * upon message reception, is messages without a C1 country code should be
   * accepted.
   *
   * @param b
   *        <code>true</code> to enable the check, <code>false</code> to disable
   *        it.
   * @return this for chaining
   * @since 9.2.2
   */
  @Nonnull
  public final PeppolSBDHDocumentReader setCheckForCountryC1 (final boolean b)
  {
    final boolean bChanged = b != m_bCheckForCountryC1;
    m_bCheckForCountryC1 = b;
    if (bChanged)
      LOGGER.info ("Peppol SBDH C1 Country Code check is " + (b ? "enabled" : "disabled"));
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
  protected boolean isValidSenderIdentifier (@Nullable final String sSenderAuthority,
                                             @Nullable final String sSenderIdentifier)
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
  protected boolean isValidReceiverIdentifier (@Nullable final String sReceiverAuthority,
                                               @Nullable final String sReceiverIdentifier)
  {
    return StringHelper.hasText (sReceiverIdentifier);
  }

  /**
   * Check if the passed document type identifier value is valid or not. By
   * default it must not be empty. Override this method to perform further
   * checks.
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
   * Check if the passed process identifier value is valid or not. By default it
   * must not be empty. Override this method to perform further checks.
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
    if (StringHelper.hasNoText (sCountryC1))
      return false;
    if (!RegExHelper.stringMatchesPattern (DEFAULT_COUNTRY_CODE_REGEX, sCountryC1))
      return false;

    // TODO add code list check as well

    return true;
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
    return sStandard.equals (aBusinessMessage.getNamespaceURI ()) &&
           sDocumentTypeIdentifierValue.startsWith (sStandard);
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
    ret.setValidationEventHandler (null);
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
  public PeppolSBDHData extractData (@Nonnull @WillClose final InputStream aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
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
  public PeppolSBDHData extractData (@Nonnull final IReadableResource aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
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
  public PeppolSBDHData extractData (@Nonnull final Node aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
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
   *        The domain object to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws PeppolSBDHDocumentReadException
   *         In case the passed Standard Business Document does not conform to
   *         the Peppol rules.
   */
  @Nonnull
  public PeppolSBDHData extractData (@Nonnull final StandardBusinessDocument aStandardBusinessDocument) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    // Grab the header
    final StandardBusinessDocumentHeader aSBDH = aStandardBusinessDocument.getStandardBusinessDocumentHeader ();
    if (aSBDH == null)
      throw new PeppolSBDHDocumentReadException (EPeppolSBDHDocumentReadError.MISSING_SBDH);

    final Element aBusinessMessage = (Element) aStandardBusinessDocument.getAny ();
    return extractData (aSBDH, aBusinessMessage);
  }

  @Nonnull
  private static IError _toError (@Nullable final String sErrorField,
                                  @Nonnull final EPeppolSBDHDocumentReadError e,
                                  @Nullable final Object... aArgs)
  {
    return SingleError.builderError ()
                      .errorFieldName (sErrorField)
                      .errorID (e.getID ())
                      .errorText (aArgs == null ? e.getErrorMessage () : e.getErrorMessage (aArgs))
                      .build ();
  }

  /**
   * Validate the provided SBDH and the Business Message according to the Peppol
   * rules and store the results in an Error List.
   *
   * @param aSBDH
   *        The SBDH to be validated. Must not be <code>null</code>.
   * @param aBusinessMessage
   *        The Business Message to be validated (this does NOT mean Schematron
   *        validation). Must not be <code>null</code>.
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
                                EPeppolSBDHDocumentReadError.INVALID_HEADER_VERSION,
                                aSBDH.getHeaderVersion ()));

    // Check sender
    {
      final int nSenderCount = aSBDH.getSenderCount ();
      if (nSenderCount != 1)
        aErrorList.add (_toError ("SBDH",
                                  EPeppolSBDHDocumentReadError.INVALID_SENDER_COUNT,
                                  Integer.toString (nSenderCount)));

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
                                      EPeppolSBDHDocumentReadError.INVALID_SENDER_AUTHORITY,
                                      sScheme));
          }

          // Check sender identifier value
          final String sValue = aSenderIdentification.getValue ();
          if (!isValidSenderIdentifier (sScheme, sValue))
          {
            aErrorList.add (_toError ("SBDH/Sender[1]/Identifier/Value",
                                      EPeppolSBDHDocumentReadError.INVALID_SENDER_VALUE,
                                      sValue));
          }
          else
          {
            final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
            if (aPID == null)
              aErrorList.add (_toError ("SBDH/Sender[1]/Identifier",
                                        EPeppolSBDHDocumentReadError.INVALID_SENDER_VALUE,
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
                                  EPeppolSBDHDocumentReadError.INVALID_RECEIVER_COUNT,
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
                                      EPeppolSBDHDocumentReadError.INVALID_RECEIVER_AUTHORITY,
                                      sScheme));
          }

          // Check receiver identifier value
          final String sValue = aReceiverIdentification.getValue ();
          if (!isValidReceiverIdentifier (sScheme, sValue))
          {
            aErrorList.add (_toError ("SBDH/Receiver[1]/Identifier/Value",
                                      EPeppolSBDHDocumentReadError.INVALID_RECEIVER_VALUE,
                                      sValue));
          }
          else
          {
            final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
            if (aPID == null)
              aErrorList.add (_toError ("SBDH/Receiver[1]/Identifier",
                                        EPeppolSBDHDocumentReadError.INVALID_RECEIVER_VALUE,
                                        CIdentifier.getURIEncoded (sScheme, sValue)));
          }
        }
      }
    }

    // Test mandatory business scope
    IDocumentTypeIdentifier aDocTypeID = null;
    IProcessIdentifier aProcessID = null;

    final BusinessScope aBusinessScope = aSBDH.getBusinessScope ();
    if (aBusinessScope == null)
      aErrorList.add (_toError ("SBDH", EPeppolSBDHDocumentReadError.BUSINESS_SCOPE_MISSING));
    else
    {
      // Check that at least 3 "Scope" elements are present
      final int nMinimumScopeCount = isCheckForCountryC1 () ? 3 : 2;
      if (aBusinessScope.getScopeCount () < nMinimumScopeCount)
        aErrorList.add (_toError ("SBDH/BusinessScope",
                                  EPeppolSBDHDocumentReadError.INVALID_SCOPE_COUNT,
                                  Integer.toString (nMinimumScopeCount),
                                  Integer.toString (aBusinessScope.getScopeCount ())));

      boolean bFoundDocumentIDScope = false;
      boolean bFoundProcessIDScope = false;
      boolean bFoundCountryC1 = false;
      int nScopeIndex1Based = 1;
      for (final Scope aScope : aBusinessScope.getScope ())
      {
        final String sType = aScope.getType ();
        final String sInstanceIdentifier = aScope.getInstanceIdentifier ();
        if (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID.equals (sType))
        {
          if (!isValidDocumentTypeIdentifier (sInstanceIdentifier))
          {
            aErrorList.add (_toError ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]/InstanceIdentifier",
                                      EPeppolSBDHDocumentReadError.INVALID_DOCUMENT_TYPE_IDENTIFIER,
                                      sInstanceIdentifier));
          }
          else
          {
            // The scheme was added in Spec v1.1
            final String sScheme = StringHelper.getNotNull (aScope.getIdentifier (),
                                                            PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS);
            aDocTypeID = m_aIdentifierFactory.createDocumentTypeIdentifier (sScheme, sInstanceIdentifier);
            if (aDocTypeID == null)
              aErrorList.add (_toError ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]",
                                        EPeppolSBDHDocumentReadError.INVALID_DOCUMENT_TYPE_IDENTIFIER,
                                        CIdentifier.getURIEncoded (sScheme, sInstanceIdentifier)));
          }

          bFoundDocumentIDScope = true;
        }
        else
          if (CPeppolSBDH.SCOPE_PROCESS_ID.equals (sType))
          {
            if (!isValidProcessIdentifier (sInstanceIdentifier))
            {
              aErrorList.add (_toError ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]/InstanceIdentifier",
                                        EPeppolSBDHDocumentReadError.INVALID_PROCESS_IDENTIFIER,
                                        sInstanceIdentifier));
            }
            else
            {
              final String sScheme = StringHelper.getNotNull (aScope.getIdentifier (),
                                                              PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME);
              aProcessID = m_aIdentifierFactory.createProcessIdentifier (sScheme, sInstanceIdentifier);
              if (aProcessID == null)
                aErrorList.add (_toError ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]",
                                          EPeppolSBDHDocumentReadError.INVALID_PROCESS_IDENTIFIER,
                                          CIdentifier.getURIEncoded (sScheme, sInstanceIdentifier)));
            }
            bFoundProcessIDScope = true;
          }
          else
            if (CPeppolSBDH.SCOPE_COUNTRY_C1.equals (sType))
            {
              if (isCheckForCountryC1 ())
              {
                if (!isValidCountryC1 (sInstanceIdentifier))
                  aErrorList.add (_toError ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]/InstanceIdentifier",
                                            EPeppolSBDHDocumentReadError.INVALID_COUNTRY_C1,
                                            sInstanceIdentifier));
              }
              bFoundCountryC1 = true;
            }
            else
              // read as additional attributes
              if (PeppolSBDHAdditionalAttributes.isReservedAttributeName (sType))
              {
                // Reserved for future use
                aErrorList.add (SingleError.builderWarn ()
                                           .errorFieldName ("SBDH/BusinessScope/Scope[" + nScopeIndex1Based + "]/Type")
                                           .errorText ("Found a Peppol reserved attribute name '" +
                                                       sType +
                                                       "' in the SBDH - Ignoring it.")
                                           .build ());
              }

        nScopeIndex1Based++;
      }
      if (!bFoundDocumentIDScope)
        aErrorList.add (_toError ("SBDH/BusinessScope", EPeppolSBDHDocumentReadError.MISSING_DOCUMENT_TYPE_IDENTIFIER));
      if (!bFoundProcessIDScope)
        aErrorList.add (_toError ("SBDH/BusinessScope", EPeppolSBDHDocumentReadError.MISSING_PROCESS_IDENTIFIER));
      if (isCheckForCountryC1 ())
      {
        if (!bFoundCountryC1)
          aErrorList.add (_toError ("SBDH/BusinessScope", EPeppolSBDHDocumentReadError.MISSING_COUNTRY_C1));
      }
    }

    // Check document and metadata
    {
      // Extract the main business message first - cannot be null and must be an
      // Element!
      if (!isValidBusinessMessage (aBusinessMessage))
        aErrorList.add (_toError (null, EPeppolSBDHDocumentReadError.INVALID_BUSINESS_MESSAGE));

      // This field is mandatory in XML
      final DocumentIdentification aDI = aSBDH.getDocumentIdentification ();
      if (aDocTypeID != null)
      {
        final String sNamespaceURI = aDI.getStandard ();
        if (!isValidStandard (sNamespaceURI, aBusinessMessage, aDocTypeID.getValue ()))
          aErrorList.add (_toError ("SBDH/DocumentIdentification/Standard",
                                    EPeppolSBDHDocumentReadError.INVALID_STANDARD,
                                    sNamespaceURI,
                                    aBusinessMessage.getNamespaceURI (),
                                    aDocTypeID.getValue ()));

        final String sTypeVersion = aDI.getTypeVersion ();
        if (!isValidTypeVersion (sTypeVersion, aBusinessMessage, aDocTypeID.getValue ()))
          aErrorList.add (_toError ("SBDH/DocumentIdentification/TypeVersion",
                                    EPeppolSBDHDocumentReadError.INVALID_TYPE_VERSION,
                                    sTypeVersion,
                                    aDocTypeID.getValue ()));
      }

      final String sLocalName = aDI.getType ();
      if (!isValidType (sLocalName, aBusinessMessage))
        aErrorList.add (_toError ("SBDH/DocumentIdentification/Type",
                                  EPeppolSBDHDocumentReadError.INVALID_TYPE,
                                  sLocalName,
                                  aBusinessMessage.getLocalName ()));

      // The unique message ID
      final String sSBDHID = aDI.getInstanceIdentifier ();
      if (!isValidInstanceIdentifier (sSBDHID))
        aErrorList.add (_toError ("SBDH/DocumentIdentification/InstanceIdentifier",
                                  EPeppolSBDHDocumentReadError.INVALID_INSTANCE_IDENTIFIER,
                                  sSBDHID));

      // Mandatory date and time (cannot be null)
      final XMLOffsetDateTime aCreationDateAndTime = aDI.getCreationDateAndTime ();
      if (!isValidCreationDateTime (aCreationDateAndTime))
        aErrorList.add (_toError ("SBDH/DocumentIdentification/CreationDateAndTime",
                                  EPeppolSBDHDocumentReadError.INVALID_CREATION_DATE_TIME,
                                  String.valueOf (aCreationDateAndTime)));
    }
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter. Eventually value checks are performed if
   * {@link #isPerformValueChecks()} is <code>true</code>.
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
  public PeppolSBDHData extractData (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                                     @Nonnull final Element aBusinessMessage) throws PeppolSBDHDocumentReadException
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    if (isPerformValueChecks ())
    {
      // Validate data
      final ErrorList aErrorList = new ErrorList ();
      validateData (aSBDH, aBusinessMessage, aErrorList);
      final int nErrors = aErrorList.getErrorCount ();
      if (nErrors > 0)
      {
        // Collect all errors
        final StringBuilder aErrorMsgSB = new StringBuilder ();

        aErrorList.forEach (x -> {
          if (x.isError ())
          {
            final String sMsg = x.getAsString (Locale.US);
            LOGGER.error ("Peppol SBDH validation " + sMsg);
            if (aErrorMsgSB.length () > 0)
              aErrorMsgSB.append ('\n');
            aErrorMsgSB.append (sMsg);
          }
        });

        // Find an error code
        final IError aFirst = aErrorList.findFirst (IHasErrorLevel::isError);
        final EPeppolSBDHDocumentReadError eError = EPeppolSBDHDocumentReadError.getFromIDOrDefault (aFirst.getErrorID (),
                                                                                                     EPeppolSBDHDocumentReadError.GENERIC_SBDH_ERROR);
        throw new PeppolSBDHDocumentReadException (aErrorMsgSB.toString (), eError);
      }
    }

    return extractDataUnchecked (aSBDH, aBusinessMessage);
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter without any value checks. This might be handy, if
   * value checks were executed separately.
   *
   * @param aSBDH
   *        The header object to read from. May not be <code>null</code>.
   * @param aBusinessMessage
   *        The main business message (XML payload) to extract data from. May
   *        not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @since 9.2.2
   */
  @Nonnull
  public PeppolSBDHData extractDataUnchecked (@Nonnull final StandardBusinessDocumentHeader aSBDH,
                                              @Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aSBDH, "StandardBusinessDocumentHeader");
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");
    final PeppolSBDHData ret = new PeppolSBDHData (m_aIdentifierFactory);

    // Check sender
    if (aSBDH.getSenderCount () > 0)
    {
      // Identifier is mandatory
      final PartnerIdentification aSenderIdentification = aSBDH.getSenderAtIndex (0).getIdentifier ();
      ret.setSender (aSenderIdentification.getAuthority (), aSenderIdentification.getValue ());
    }

    // Check receiver
    if (aSBDH.getReceiverCount () > 0)
    {
      // Identifier is mandatory
      final PartnerIdentification aReceiverIdentification = aSBDH.getReceiverAtIndex (0).getIdentifier ();
      ret.setReceiver (aReceiverIdentification.getAuthority (), aReceiverIdentification.getValue ());
    }

    // Document type identifier and process identifier
    final BusinessScope aBusinessScope = aSBDH.getBusinessScope ();
    if (aBusinessScope != null)
    {
      for (final Scope aScope : aBusinessScope.getScope ())
      {
        final String sType = aScope.getType ();
        final String sInstanceIdentifier = aScope.getInstanceIdentifier ();
        if (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID.equals (sType))
        {
          // The scheme was added in Spec v1.1
          final String sScheme = StringHelper.getNotNull (aScope.getIdentifier (),
                                                          PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS);

          ret.setDocumentType (sScheme, sInstanceIdentifier);
        }
        else
          if (CPeppolSBDH.SCOPE_PROCESS_ID.equals (sType))
          {
            // The scheme was added in Spec v1.1
            final String sScheme = StringHelper.getNotNull (aScope.getIdentifier (),
                                                            PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME);

            ret.setProcess (sScheme, sInstanceIdentifier);
          }
          else
            if (CPeppolSBDH.SCOPE_COUNTRY_C1.equals (sType))
            {
              ret.setCountryC1 (sInstanceIdentifier);
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
      }
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
