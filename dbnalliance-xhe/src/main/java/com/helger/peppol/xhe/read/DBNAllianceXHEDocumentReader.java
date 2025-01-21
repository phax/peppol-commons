/*
 * Copyright (C) 2024-2025 Philip Helger
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
package com.helger.peppol.xhe.read;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.commons.error.IError;
import com.helger.commons.error.SingleError;
import com.helger.commons.error.level.IHasErrorLevel;
import com.helger.commons.error.list.ErrorList;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.xhe.CDBNAllianceXHE;
import com.helger.peppol.xhe.DBNAlliancePayload;
import com.helger.peppol.xhe.DBNAllianceXHEData;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.xhe.v10.XHE10Marshaller;
import com.helger.xhe.v10.XHE10XHEType;
import com.helger.xhe.v10.cac.XHE10HeaderType;
import com.helger.xhe.v10.cac.XHE10PartyType;
import com.helger.xhe.v10.cac.XHE10PayloadContentType;
import com.helger.xhe.v10.cac.XHE10PayloadType;
import com.helger.xhe.v10.cac.XHE10PayloadsType;
import com.helger.xhe.v10.cbc.XHE10ContentTypeCodeType;
import com.helger.xhe.v10.cbc.XHE10CustomizationIDType;
import com.helger.xhe.v10.cbc.XHE10IDType;
import com.helger.xhe.v10.cbc.XHE10InstanceEncryptionIndicatorType;
import com.helger.xhe.v10.cbc.XHE10InstanceEncryptionMethodType;
import com.helger.xhe.v10.cbc.XHE10ProfileIDType;

/**
 * Main class to read exchange header envelope and extract the DBNAlliance
 * required data out of it.
 *
 * @author Robinson Garcia
 * @author Philip Helger
 */
public class DBNAllianceXHEDocumentReader
{
  public static final boolean DEFAULT_PERFORM_VALUE_CHECKS = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (DBNAllianceXHEDocumentReader.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  private boolean m_bPerformValueChecks = DEFAULT_PERFORM_VALUE_CHECKS;

  public DBNAllianceXHEDocumentReader (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");

    m_aIdentifierFactory = aIdentifierFactory;
  }

  /**
   * @return The identifier provided in the constructor. Never
   *         <code>null</code>.
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
   */
  @Nonnull
  public final DBNAllianceXHEDocumentReader setPerformValueChecks (final boolean b)
  {
    m_bPerformValueChecks = b;
    return this;
  }

  /**
   * Check if the passed XHE version is valid or not. By default is must match
   * {@link CDBNAllianceXHE#XHE_VERSION_ID}. Override this method to allow for
   * other schemes as well.
   *
   * @param sXHEVersionID
   *        The value to be checked. This is the content of the XML element
   *        <code>/XHE/XHEVersionID</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidXHEVersionID (@Nullable final String sXHEVersionID)
  {
    return CDBNAllianceXHE.XHE_VERSION_ID.equals (sXHEVersionID);
  }

  /**
   * Check if the passed customization id schema is valid or not. By default is
   * must match {@link CDBNAllianceXHE#CUSTOMIZATION_SCHEMA_ID}. Override this
   * method to allow for other schemes as well.
   *
   * @param sSchemaID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/CustomizationID/@schemeID</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCustomizationIDSchemaID (@Nullable final String sSchemaID)
  {
    return CDBNAllianceXHE.CUSTOMIZATION_SCHEMA_ID.equals (sSchemaID);
  }

  /**
   * Check if the passed customization id is valid or not. By default is must
   * match {@link CDBNAllianceXHE#CUSTOMIZATION_ID}. Override this method to
   * allow for other schemes as well.
   *
   * @param sCustomizationID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/CustomizationID/</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCustomizationID (@Nullable final String sCustomizationID)
  {
    return CDBNAllianceXHE.CUSTOMIZATION_ID.equals (sCustomizationID);
  }

  /**
   * Check if the passed profile id is valid or not. By default is must match
   * {@link CDBNAllianceXHE#PROFILE_ID}. Override this method to allow for other
   * schemes as well.
   *
   * @param sProfileID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/ProfileID/</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidProfileID (@Nullable final String sProfileID)
  {
    return CDBNAllianceXHE.PROFILE_ID.equals (sProfileID);
  }

  /**
   * Check if the passed header id is valid or not. By default is must not be
   * empty. Override this method to perform further checks.
   *
   * @param sHeaderID
   *        The value to be checked. This conforms to the XML element value of
   *        <code>XHE/Header/ID</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidHeaderID (@Nullable final String sHeaderID)
  {
    return StringHelper.hasText (sHeaderID);
  }

  /**
   * Check if the passed document identification creation date time is valid or
   * not. By default all values are valid as they cannot be <code>null</code>.
   * Override this method to perform further or other checks.
   *
   * @param aCreationDateTime
   *        The value to be checked. This corresponds to the field
   *        "XHE/Header/CreationDateTime". Is never <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidCreationDateTime (@Nonnull final XMLOffsetDateTime aCreationDateTime)
  {
    return true;
  }

  /**
   * Check if the passed from party schema is is valid or not. By default is
   * must not be empty.
   *
   * @param sFromPartySchemaID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Header/FromParty/PartyIdentification/ID/@schemaID</code>.
   *        May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidFromPartySchemaID (@Nullable final String sFromPartySchemaID)
  {
    return StringHelper.hasText (sFromPartySchemaID);
  }

  /**
   * Check if the passed from party value is valid or not. By default is must
   * not be empty. Override this method to perform further checks.
   *
   * @param sFromPartySchemaID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Header/FromParty/PartyIdentification/ID/@schemaID</code>.
   *        May be <code>null</code>.
   * @param sFromPartyValue
   *        The value to be checked. This conforms to the XML element value of
   *        <code>XHE/Header/FromParty/PartyIdentification/ID/</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidFromPartyValue (@Nullable final String sFromPartySchemaID,
                                           @Nullable final String sFromPartyValue)
  {
    return StringHelper.hasText (sFromPartyValue);
  }

  /**
   * Check if the passed to party schema is valid or not. By default is must not
   * be empty.
   *
   * @param sToPartySchemaID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Header/ToParty/PartyIdentification/ID/@schemaID</code>.
   *        May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidToPartySchemaID (@Nullable final String sToPartySchemaID)
  {
    return StringHelper.hasText (sToPartySchemaID);
  }

  /**
   * Check if the passed to party value is valid or not. By default is must not
   * be empty. Override this method to perform further checks.
   *
   * @param sToPartySchemaID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Header/ToParty/PartyIdentification/ID/@schemaID</code>.
   *        May be <code>null</code>.
   * @param sToPartyValue
   *        The value to be checked. This conforms to the XML element value of
   *        <code>XHE/Header/ToParty/PartyIdentification/ID/</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidToPartyValue (@Nullable final String sToPartySchemaID, @Nullable final String sToPartyValue)
  {
    return StringHelper.hasText (sToPartyValue);
  }

  // Payloads validations

  /**
   * Check if the passed payload id is valid or not. By default is must not be
   * empty.
   *
   * @param sPayloadID
   *        The value to be checked. This corresponds to the field
   *        <code>XHE/Payloads/Payload/ID</code>. May be <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidPayloadIDValue (@Nullable final String sPayloadID)
  {
    return StringHelper.hasText (sPayloadID);
  }

  /**
   * Check if the passed list id is valid or not.
   *
   * @param sListID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidContentTypeCodeListID (@Nullable final String sListID)
  {
    return "MIME".equals (sListID);
  }

  /**
   * Check if the passed content type code value is valid or not. By default is
   * must not be empty.
   *
   * @param sListID
   *        The value to be checked. This is the content of the XML attribute
   *        <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code>. May be
   *        <code>null</code>.
   * @param sValue
   *        The value to be checked. This corresponds to the field
   *        <code>XHE/Payloads/Payload/ContentTypeCode</code>. May be
   *        <code>null</code>.
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidContentTypeCodeValue (@Nullable final String sListID, @Nullable final String sValue)
  {
    return "application/xml".equals (sValue);
  }

  /**
   * Check if the passed instance encryption indicator value is valid or not. By
   * default all values are valid as they cannot be <code>null</code>. Override
   * this method to perform further or other checks.
   *
   * @param bInstanceEncryptionIndicator
   *        The value to be checked. This corresponds to the field
   *        "XHE/Payloads/Payload/InstanceEncryptionIndicator". Is never
   *        <code>null</code> .
   * @return <code>true</code> if the value is valid, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isValidInstanceEncryptionIndicatorValue (@Nullable final boolean bInstanceEncryptionIndicator)
  {
    return true;
  }

  /**
   * The Instance Hash Value MUST NOT be included in the envelope. By default
   * all values are invalid as they cannot be present. Override this method to
   * perform further or other checks.
   *
   * @param sInstanceHashValue
   *        The value to be checked. This corresponds to the field
   *        "XHE/Payloads/Payload/InstanceHashValue". Is always
   *        <code>null</code> .
   * @return <code>true</code> if the field does not exist, <code>false</code>
   *         otherwise.
   */
  @OverrideOnDemand
  protected boolean isInstanceHashValueExist (@Nullable final String sInstanceHashValue)
  {
    return sInstanceHashValue == null;
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
   * Create a new XHE10 marshaller used for reading XHE documents. Override this
   * method to customize reading.
   *
   * @return An instance of the {@link XHE10Marshaller} and never
   *         <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  protected XHE10Marshaller createXHEMarshaller ()
  {
    final XHE10Marshaller ret = new XHE10Marshaller ();
    // Simply swallow all error messages where possible
    ret.setValidationEventHandler (null);
    return ret;
  }

  /**
   * Extract the document data from the Exchange Header Envelope represents by
   * the passed parameter.
   *
   * @param aExchangeHeaderEnvelope
   *        The input stream to read from. Will be closed by this method. May
   *        not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws DBNAllianceXHEDocumentReadException
   *         In case the passed Exchange Header Envelope does not conform to the
   *         DBNAlliance rules.
   */
  @Nonnull
  public DBNAllianceXHEData extractData (@Nonnull @WillClose final InputStream aExchangeHeaderEnvelope) throws DBNAllianceXHEDocumentReadException
  {
    ValueEnforcer.notNull (aExchangeHeaderEnvelope, "ExchangeHeaderEnvelope");

    try
    {
      // Convert to domain object
      final XHE10XHEType aXHE = createXHEMarshaller ().read (aExchangeHeaderEnvelope);
      if (aXHE == null)
        throw new DBNAllianceXHEDocumentReadException (EDBNAllianceXHEDocumentReadError.INVALID_XHE_XML);

      return extractData (aXHE);
    }
    finally
    {
      StreamHelper.close (aExchangeHeaderEnvelope);
    }
  }

  /**
   * Extract the document data from the Exchange Header Envelope represents by
   * the passed parameter.
   *
   * @param aExchangeHeaderEnvelope
   *        The resource to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws DBNAllianceXHEDocumentReadException
   *         In case the passed Exchange Header Envelope does not conform to the
   *         DBNAlliance rules.
   */
  @Nonnull
  public DBNAllianceXHEData extractData (@Nonnull final IReadableResource aExchangeHeaderEnvelope) throws DBNAllianceXHEDocumentReadException
  {
    ValueEnforcer.notNull (aExchangeHeaderEnvelope, "StandardBusinessDocument");

    // Convert to domain object
    final XHE10XHEType aXHE = createXHEMarshaller ().read (aExchangeHeaderEnvelope);
    if (aXHE == null)
      throw new DBNAllianceXHEDocumentReadException (EDBNAllianceXHEDocumentReadError.INVALID_XHE_XML);

    return extractData (aXHE);
  }

  /**
   * Extract the document data from the Exchange Header Envelope represents by
   * the passed parameter.
   *
   * @param aExchangeHeaderEnvelope
   *        The DOM node to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws DBNAllianceXHEDocumentReadException
   *         In case the passed Exchange Header Envelope does not conform to the
   *         DBNAlliance rules.
   */
  @Nonnull
  public DBNAllianceXHEData extractData (@Nonnull final Node aExchangeHeaderEnvelope) throws DBNAllianceXHEDocumentReadException
  {
    ValueEnforcer.notNull (aExchangeHeaderEnvelope, "ExchangeHeaderEnvelope");

    // Convert to domain object
    final XHE10XHEType aXHE = createXHEMarshaller ().read (aExchangeHeaderEnvelope);
    if (aXHE == null)
      throw new DBNAllianceXHEDocumentReadException (EDBNAllianceXHEDocumentReadError.INVALID_XHE_XML);

    return extractData (aXHE);
  }

  /**
   * Extract the document data from the Exchange Header Envelope represents by
   * the passed parameter.
   *
   * @param aExchangeHeaderEnvelope
   *        The domain object to read from. May not be <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws DBNAllianceXHEDocumentReadException
   *         In case the passed Exchange Header Envelope does not conform to the
   *         DBNAlliance rules.
   */
  @Nonnull
  public DBNAllianceXHEData extractData (@Nonnull final XHE10XHEType aExchangeHeaderEnvelope) throws DBNAllianceXHEDocumentReadException
  {
    ValueEnforcer.notNull (aExchangeHeaderEnvelope, "ExchangeHeaderEnvelope");

    // Grab the payloads
    final XHE10PayloadsType aPayloads = aExchangeHeaderEnvelope.getPayloads ();
    if (aPayloads == null || aPayloads.getPayload ().isEmpty ())
      throw new DBNAllianceXHEDocumentReadException (EDBNAllianceXHEDocumentReadError.MISSING_PAYLOADS_PAYLOAD);

    return extractData (aExchangeHeaderEnvelope, aPayloads);
  }

  @Nonnull
  private static IError _toError (@Nullable final String sErrorField,
                                  @Nonnull final EDBNAllianceXHEDocumentReadError e,
                                  @Nullable final Object... aArgs)
  {
    return SingleError.builderError ()
                      .errorFieldName (sErrorField)
                      .errorID (e.getID ())
                      .errorText (aArgs == null ? e.getErrorMessage () : e.getErrorMessage (aArgs))
                      .build ();
  }

  /**
   * Validate the provided XHE and the Payloads according to the DBNAlliance
   * rules and store the results in an Error List.
   *
   * @param aXHE
   *        The SBDH to be validated. Must not be <code>null</code>.
   * @param aPayloads
   *        The Payloads list to be validated (this does NOT mean Schematron
   *        validation). Must not be <code>null</code>.
   * @param aErrorList
   *        The error list to be filled. Must not be <code>null</code>.
   */
  public void validateData (@Nonnull final XHE10XHEType aXHE,
                            @Nonnull final XHE10PayloadsType aPayloads,
                            @Nonnull final ErrorList aErrorList)
  {
    ValueEnforcer.notNull (aXHE, "ExchangeHeaderEnvelope");
    ValueEnforcer.notNull (aPayloads, "Payloads");
    ValueEnforcer.notNull (aErrorList, "ErrorList");

    // Check that the xhe version id is correct
    if (!isValidXHEVersionID (aXHE.getXHEVersionIDValue ()))
      aErrorList.add (_toError ("XHE/XHEVersionID",
                                EDBNAllianceXHEDocumentReadError.INVALID_XHE_VERSION_ID,
                                aXHE.getXHEVersionIDValue ()));

    // Check CustomizationID
    {
      final XHE10CustomizationIDType aCustomizationID = aXHE.getCustomizationID ();
      if (aCustomizationID == null)
      {
        aErrorList.add (_toError ("XHE/CustomizationID", EDBNAllianceXHEDocumentReadError.CUSTOMIZATION_ID_MISSING));
      }
      else
      {
        // schemaID is mandatory
        final String sCustomizationIDSchemaID = aCustomizationID.getSchemeID ();
        if (!isValidCustomizationIDSchemaID (sCustomizationIDSchemaID))
          aErrorList.add (_toError ("XHE/CustomizationID/SchemaID",
                                    EDBNAllianceXHEDocumentReadError.INVALID_CUSTOMIZATION_ID_SCHEMA_ID,
                                    sCustomizationIDSchemaID));

        final String sCustomizationIDValue = aCustomizationID.getValue ();
        if (!isValidCustomizationID (sCustomizationIDValue))
          aErrorList.add (_toError ("XHE/CustomizationID/Value",
                                    EDBNAllianceXHEDocumentReadError.INVALID_CUSTOMIZATION_ID_VALUE,
                                    sCustomizationIDValue));
      }
    }

    // Check ProfileID
    {
      final XHE10ProfileIDType aProfileID = aXHE.getProfileID ();
      if (aProfileID == null)
      {
        aErrorList.add (_toError ("XHE/ProfileID", EDBNAllianceXHEDocumentReadError.PROFILE_ID_MISSING));
      }
      else
      {
        final String sProfileIDValue = aProfileID.getValue ();
        if (!isValidProfileID (sProfileIDValue))
          aErrorList.add (_toError ("XHE/ProfileID/Value",
                                    EDBNAllianceXHEDocumentReadError.INVALID_PROFILE_ID_VALUE,
                                    sProfileIDValue));
      }
    }

    // Check Header
    {
      final XHE10HeaderType aHeader = aXHE.getHeader ();
      if (aHeader == null)
      {
        aErrorList.add (_toError ("XHE/Header", EDBNAllianceXHEDocumentReadError.HEADER_MISSING));
      }
      else
      {
        // Header id is mandatory
        final String sHeaderID = aHeader.getIDValue ();
        if (!isValidHeaderID (sHeaderID))
        {
          aErrorList.add (_toError ("XHE/Header/ID", EDBNAllianceXHEDocumentReadError.INVALID_HEADER_ID, sHeaderID));
        }
        // Header creation date time is mandatory
        final XMLOffsetDateTime aCreationDateTime = aHeader.getCreationDateTimeValue ();
        if (!isValidCreationDateTime (aCreationDateTime))
        {
          aErrorList.add (_toError ("XHE/Header/CreationDateTime",
                                    EDBNAllianceXHEDocumentReadError.INVALID_CREATION_DATE_TIME,
                                    sHeaderID));
        }

        // Check from party
        final XHE10PartyType aFromParty = aHeader.getFromParty ();
        if (aFromParty != null)
        {
          final int nFromPartyCount = aFromParty.getPartyIdentificationCount ();
          if (nFromPartyCount != 1)
            aErrorList.add (_toError ("XHE/Header/FromParty/PartyIdentification",
                                      EDBNAllianceXHEDocumentReadError.INVALID_FROM_PARTY_COUNT,
                                      Integer.toString (nFromPartyCount)));

          if (nFromPartyCount > 0)
          {
            final XHE10IDType aFromPartyID = aFromParty.getPartyIdentificationAtIndex (0).getID ();
            final String sScheme = aFromPartyID.getSchemeID ();
            if (!isValidFromPartySchemaID (sScheme))
            {
              aErrorList.add (_toError ("XHE/Header/FromParty/PartyIdentification/ID/schemaID",
                                        EDBNAllianceXHEDocumentReadError.INVALID_FROM_PARTY_SCHEMA_ID,
                                        sScheme));
            }

            // Check from party id value
            final String sValue = aFromPartyID.getValue ();
            if (!isValidFromPartyValue (sScheme, sValue))
            {
              aErrorList.add (_toError ("XHE/Header/FromParty/PartyIdentification/ID",
                                        EDBNAllianceXHEDocumentReadError.INVALID_FROM_PARTY_VALUE,
                                        sValue));
            }
            else
            {
              final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
              if (aPID == null)
                aErrorList.add (_toError ("XHE/Header/FromParty/PartyIdentification/ID",
                                          EDBNAllianceXHEDocumentReadError.INVALID_FROM_PARTY_VALUE,
                                          CIdentifier.getURIEncoded (sScheme, sValue)));
            }
          }
        }

        // Check to party
        final int nToPartyCount = aHeader.getToPartyCount ();
        if (nToPartyCount != 1)
          aErrorList.add (_toError ("XHE/Header/ToParty",
                                    EDBNAllianceXHEDocumentReadError.INVALID_TO_PARTY_COUNT,
                                    Integer.toString (nToPartyCount)));

        if (nToPartyCount > 0)
        {
          final XHE10PartyType nToParty = aHeader.getToPartyAtIndex (0);
          final int nFromPartyIdentificationCount = nToParty.getPartyIdentificationCount ();
          if (nFromPartyIdentificationCount != 1)
            aErrorList.add (_toError ("XHE/Header/ToParty/PartyIdentification",
                                      EDBNAllianceXHEDocumentReadError.INVALID_TO_PARTY_IDENTIFICATION_COUNT,
                                      Integer.toString (nFromPartyIdentificationCount)));

          if (nFromPartyIdentificationCount > 0)
          {
            final XHE10IDType aToPartyID = nToParty.getPartyIdentificationAtIndex (0).getID ();
            final String sScheme = aToPartyID.getSchemeID ();
            if (!isValidToPartySchemaID (sScheme))
            {
              aErrorList.add (_toError ("XHE/Header/ToParty/PartyIdentification/ID/schemaID",
                                        EDBNAllianceXHEDocumentReadError.INVALID_TO_PARTY_SCHEMA_ID,
                                        sScheme));
            }

            // Check to party id value
            final String sValue = aToPartyID.getValue ();
            if (!isValidToPartyValue (sScheme, sValue))
            {
              aErrorList.add (_toError ("XHE/Header/ToParty/PartyIdentification/ID",
                                        EDBNAllianceXHEDocumentReadError.INVALID_TO_PARTY_VALUE,
                                        sValue));
            }
            else
            {
              final IParticipantIdentifier aPID = m_aIdentifierFactory.createParticipantIdentifier (sScheme, sValue);
              if (aPID == null)
                aErrorList.add (_toError ("XHE/Header/ToParty/PartyIdentification/ID",
                                          EDBNAllianceXHEDocumentReadError.INVALID_TO_PARTY_VALUE,
                                          CIdentifier.getURIEncoded (sScheme, sValue)));
            }
          }
        }
      }
    }

    // Check Payloads
    final List <XHE10PayloadType> aPayloadList = aPayloads.getPayload ();
    int nPayload = 1;
    for (final XHE10PayloadType aPayload : aPayloadList)
    {

      // ID is mandatory
      final String sPayloadID = aPayload.getIDValue ();
      if (!isValidPayloadIDValue (sPayloadID))
        aErrorList.add (_toError ("XHE/Payloads/Payload[" + nPayload + "]/ID",
                                  EDBNAllianceXHEDocumentReadError.INVALID_PAYLOAD_ID_VALUE,
                                  sPayloadID));

      // Check content type code
      {
        final XHE10ContentTypeCodeType aContentTypeCode = aPayload.getContentTypeCode ();

        final String sContentTypeCodeListID = aContentTypeCode.getListID ();
        if (sContentTypeCodeListID != null && !isValidContentTypeCodeListID (sContentTypeCodeListID))
          aErrorList.add (_toError ("XHE/Payloads/Payload[" + nPayload + "]/ContentTypeCode/listID",
                                    EDBNAllianceXHEDocumentReadError.INVALID_CONTENT_TYPE_CODE_LIST_ID,
                                    sContentTypeCodeListID));

        // Content type code values is mandatory
        final String sContentTypeCodeValue = aContentTypeCode.getValue ();
        if (!isValidContentTypeCodeValue (sContentTypeCodeListID, sContentTypeCodeValue))
          aErrorList.add (_toError ("XHE/Payloads/Payload[" + nPayload + "]/ContentTypeCode",
                                    EDBNAllianceXHEDocumentReadError.INVALID_CONTENT_TYPE_CODE_VALUE,
                                    sContentTypeCodeValue));
      }

      // Check instance encription indicator
      final XHE10InstanceEncryptionIndicatorType aInstanceEncryptionIndicator = aPayload.getInstanceEncryptionIndicator ();
      if (aInstanceEncryptionIndicator == null)
        aErrorList.add (_toError ("XHE/Payloads/Payload[" + nPayload + "]/InstanceEncryptionIndicator",
                                  EDBNAllianceXHEDocumentReadError.INSTANCE_ENCRYPTION_INDICATOR_MISSING));

      // Extract the payload content (business message) - cannot be null and
      // must be an
      // Element!
      final Element aPayloadContent = (Element) aPayload.getPayloadContent ();
      if (!isValidBusinessMessage (aPayloadContent))
        aErrorList.add (_toError (null, EDBNAllianceXHEDocumentReadError.INVALID_BUSINESS_MESSAGE));

      nPayload++;
    }
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter. Eventually value checks are performed if
   * {@link #isPerformValueChecks()} is <code>true</code>.
   *
   * @param aXHE
   *        The xhe object to read from. May not be <code>null</code>.
   * @param aPayloads
   *        The list of DBNAlliance payload to extract data from. May not be
   *        <code>null</code>.
   * @return The document data and never <code>null</code>.
   * @throws DBNAllianceXHEDocumentReadException
   *         In case the passed Exchange Header Envelope does not conform to the
   *         DBNAlliance rules.
   */
  @Nonnull
  public DBNAllianceXHEData extractData (@Nonnull final XHE10XHEType aXHE,
                                         @Nonnull final XHE10PayloadsType aPayloads) throws DBNAllianceXHEDocumentReadException
  {
    ValueEnforcer.notNull (aXHE, "ExchangeHeaderEnvelope");
    ValueEnforcer.notNull (aPayloads, "Payloads");

    if (isPerformValueChecks ())
    {
      // Validate data
      final ErrorList aErrorList = new ErrorList ();
      validateData (aXHE, aPayloads, aErrorList);
      final int nErrors = aErrorList.getErrorCount ();
      if (nErrors > 0)
      {
        // Collect all errors
        final StringBuilder aErrorMsgSB = new StringBuilder ();

        aErrorList.forEach (x -> {
          if (x.isError ())
          {
            final String sMsg = x.getAsStringLocaleIndepdent ();
            LOGGER.error ("DBNAlliance XHE validation " + sMsg);
            if (aErrorMsgSB.length () > 0)
              aErrorMsgSB.append ('\n');
            aErrorMsgSB.append (sMsg);
          }
        });

        // Find an error code
        final IError aFirst = aErrorList.findFirst (IHasErrorLevel::isError);
        final EDBNAllianceXHEDocumentReadError eError = EDBNAllianceXHEDocumentReadError.getFromIDOrDefault (aFirst.getErrorID (),
                                                                                                             EDBNAllianceXHEDocumentReadError.GENERIC_XHE_ERROR);
        throw new DBNAllianceXHEDocumentReadException (aErrorMsgSB.toString (), eError);
      }
    }

    return extractDataUnchecked (aXHE, aPayloads);
  }

  /**
   * Extract the document data from the Standard Business Document represents by
   * the passed parameter without any value checks. This might be handy, if
   * value checks were executed separately.
   *
   * @param aXHE
   *        The header object to read from. May not be <code>null</code>.
   * @param aPayloads
   *        The list of DBNAlliance payload to extract data from. May not be
   *        <code>null</code>.
   * @return The document data and never <code>null</code>.
   */
  @Nonnull
  public DBNAllianceXHEData extractDataUnchecked (@Nonnull final XHE10XHEType aXHE,
                                                  @Nonnull final XHE10PayloadsType aPayloads)
  {
    ValueEnforcer.notNull (aXHE, "ExchangeHeaderEnvelope");
    ValueEnforcer.notNull (aPayloads, "Payloads");
    final DBNAllianceXHEData ret = new DBNAllianceXHEData (m_aIdentifierFactory);

    // Check Header
    {
      final XHE10HeaderType aHeader = aXHE.getHeader ();
      if (aHeader != null)
      {
        ret.setID (aHeader.getIDValue ());
        ret.setCreationDateAndTime (aHeader.getCreationDateTimeValue ());

        // From Party
        {
          final XHE10PartyType aFromParty = aHeader.getFromParty ();
          if (aFromParty != null && aFromParty.hasPartyIdentificationEntries ())
          {
            final XHE10IDType aFromPartyID = aFromParty.getPartyIdentificationAtIndex (0).getID ();
            ret.setFromParty (aFromPartyID.getSchemeID (), aFromPartyID.getValue ());
          }
        }

        // To Party
        {
          if (aHeader.hasToPartyEntries ())
          {
            final XHE10PartyType aToParty = aHeader.getToPartyAtIndex (0);
            if (aToParty.hasPartyIdentificationEntries ())
            {
              final XHE10IDType aToPartyID = aToParty.getPartyIdentificationAtIndex (0).getID ();
              ret.setFromParty (aToPartyID.getSchemeID (), aToPartyID.getValue ());
            }
          }
        }
      }
    }

    // Check Payloads
    {
      final List <XHE10PayloadType> aXHEPayloads = aPayloads.getPayload ();
      for (final XHE10PayloadType aXHEPayload : aXHEPayloads)
      {
        final DBNAlliancePayload aPayload = new DBNAlliancePayload (m_aIdentifierFactory);
        if (aXHEPayload.hasDescriptionEntries ())
          aPayload.setDescription (aXHEPayload.getDescriptionAtIndex (0).getValue ());

        // Check content type code
        final XHE10ContentTypeCodeType aContentTypeCode = aXHEPayload.getContentTypeCode ();
        if (aContentTypeCode != null)
          aPayload.setContentTypeCode (aContentTypeCode.getListID (), aContentTypeCode.getValue ());

        // Check customization id
        final XHE10CustomizationIDType aCustomizationID = aXHEPayload.getCustomizationID ();
        if (aCustomizationID != null)
          aPayload.setCustomizationID (aCustomizationID.getSchemeID (), aCustomizationID.getValue ());

        // Check profile id
        final XHE10ProfileIDType aProfileID = aXHEPayload.getProfileID ();
        if (aProfileID != null)
          aPayload.setProfileID (aProfileID.getSchemeID (), aProfileID.getValue ());

        final XHE10InstanceEncryptionIndicatorType aInstanceEncryptionIndicator = aXHEPayload.getInstanceEncryptionIndicator ();
        if (aInstanceEncryptionIndicator != null)
          aPayload.setInstanceEncryptionIndicator (aInstanceEncryptionIndicator.isValue ());

        final XHE10InstanceEncryptionMethodType aInstanceEncryptionMethod = aXHEPayload.getInstanceEncryptionMethod ();
        if (aInstanceEncryptionMethod != null)
          aPayload.setInstanceEncryptionMethod (aInstanceEncryptionMethod.getValue ());

        final XHE10PayloadContentType aPayloadContent = aXHEPayload.getPayloadContent ();
        if (aPayloadContent != null)
          aPayload.setPayloadContent ((Element) aPayloadContent);

        ret.addPayload (aPayload);

      }
    }

    return ret;
  }
}
