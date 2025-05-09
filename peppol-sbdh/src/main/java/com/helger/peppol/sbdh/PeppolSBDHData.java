/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Element;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.attr.StringMap;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.mime.IMimeType;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppol.sbdh.payload.PeppolSBDHPayloadBinaryMarshaller;
import com.helger.peppol.sbdh.payload.PeppolSBDHPayloadTextMarshaller;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.TextContentType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xml.XMLHelper;

/**
 * This class contains all the Peppol data per SBDH document in a syntax neutral way. This class
 * maps to the requirements of the Peppol Business Message Envelope 2.0 specification.
 *
 * @author Philip Helger
 * @since 9.2.0 - was previously called "PeppolSBDHDocument"
 */
@NotThreadSafe
public class PeppolSBDHData
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSBDHData.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  // Sender
  private String m_sSenderScheme;
  private String m_sSenderValue;
  // Receiver
  private String m_sReceiverScheme;
  private String m_sReceiverValue;
  // BusinessScope
  private String m_sDocumentTypeScheme;
  private String m_sDocumentTypeValue;
  private String m_sProcessScheme;
  private String m_sProcessValue;
  private String m_sCountryC1;
  private String m_sMLSToScheme;
  private String m_sMLSToValue;
  private EPeppolMLSType m_eMLSType;
  // DocumentIdentification
  private String m_sStandard;
  private String m_sTypeVersion;
  private String m_sType;
  private String m_sInstanceIdentifier;
  private XMLOffsetDateTime m_aCreationDateAndTime;
  // BusinessMessage
  private Element m_aBusinessMessage;
  // Additional attributes
  private final StringMap m_aAdditionalAttrs = new StringMap ();

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public PeppolSBDHData (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * @return The sender participant identifier scheme. May be <code>null</code> if not initialized.
   *         This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Sender/Identifier/@Authority</code> .
   */
  @Nullable
  public String getSenderScheme ()
  {
    return m_sSenderScheme;
  }

  /**
   * @return The sender participant identifier value. May be <code>null</code> if not initialized.
   *         This field is mapped to <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   */
  @Nullable
  public String getSenderValue ()
  {
    return m_sSenderValue;
  }

  /**
   * @return The sender participant identifier as a participant identifier or <code>null</code> if
   *         certain information are missing or are invalid.
   */
  @Nullable
  public IParticipantIdentifier getSenderAsIdentifier ()
  {
    return m_aIdentifierFactory.createParticipantIdentifier (m_sSenderScheme, m_sSenderValue);
  }

  /**
   * Set the sender participant identifier.
   *
   * @param sScheme
   *        The Peppol identifier scheme. This is usually always
   *        {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. May neither be
   *        <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/@Authority</code> .
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setSender (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sSenderScheme = sScheme;
    m_sSenderValue = sValue;
    return this;
  }

  /**
   * Set the sender participant identifier.
   *
   * @param aSenderID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   * @since 8.6.1
   */
  @Nonnull
  public PeppolSBDHData setSender (@Nonnull final IParticipantIdentifier aSenderID)
  {
    ValueEnforcer.notNull (aSenderID, "SenderID");

    return setSender (aSenderID.getScheme (), aSenderID.getValue ());
  }

  /**
   * Set the sender participant identifier value using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setSenderWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setSender (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
  }

  /**
   * @return The receiver participant identifier scheme. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Receiver/Identifier/@Authority</code> .
   */
  @Nullable
  public String getReceiverScheme ()
  {
    return m_sReceiverScheme;
  }

  /**
   * @return The receiver participant identifier value. May be <code>null</code> if not initialized.
   *         This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   */
  @Nullable
  public String getReceiverValue ()
  {
    return m_sReceiverValue;
  }

  /**
   * @return The receiver participant identifier as a participant identifier or <code>null</code> if
   *         certain information are missing or are invalid.
   */
  @Nullable
  public IParticipantIdentifier getReceiverAsIdentifier ()
  {
    return m_aIdentifierFactory.createParticipantIdentifier (m_sReceiverScheme, m_sReceiverValue);
  }

  /**
   * Set the receiver participant identifier.
   *
   * @param sScheme
   *        The Peppol identifier scheme. This is usually always
   *        {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME} . May neither be
   *        <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/@Authority</code> .
   * @param sValue
   *        The receiver identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setReceiver (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sReceiverScheme = sScheme;
    m_sReceiverValue = sValue;
    return this;
  }

  /**
   * Set the receiver participant identifier.
   *
   * @param aReceiverID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   * @since 8.6.1
   */
  @Nonnull
  public PeppolSBDHData setReceiver (@Nonnull final IParticipantIdentifier aReceiverID)
  {
    ValueEnforcer.notNull (aReceiverID, "ReceiverID");

    return setReceiver (aReceiverID.getScheme (), aReceiverID.getValue ());
  }

  /**
   * Set the receiver participant identifier value using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setReceiverWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setReceiver (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
  }

  /**
   * @return The document type identifier scheme. May be <code>null</code> if not initialized yet.
   *         This field is currently not mapped.
   */
  @Nullable
  public String getDocumentTypeScheme ()
  {
    return m_sDocumentTypeScheme;
  }

  /**
   * @return The document type identifier value. May be <code>null</code> if not initialized yet.
   *         This field is mapped to
   *         <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *         .
   */
  @Nullable
  public String getDocumentTypeValue ()
  {
    return m_sDocumentTypeValue;
  }

  /**
   * @return The document type identifier as an object or <code>null</code> if certain information
   *         are missing or are invalid.
   */
  @Nullable
  public IDocumentTypeIdentifier getDocumentTypeAsIdentifier ()
  {
    return m_aIdentifierFactory.createDocumentTypeIdentifier (m_sDocumentTypeScheme, m_sDocumentTypeValue);
  }

  /**
   * Set the document type identifier.
   *
   * @param sScheme
   *        The document type identifier scheme. May neither be <code>null</code> nor empty.
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code> nor empty. This
   *        field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setDocumentType (@Nonnull @Nonempty final String sScheme,
                                         @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sDocumentTypeScheme = sScheme;
    m_sDocumentTypeValue = sValue;
    return this;
  }

  /**
   * Set the document type identifier.
   *
   * @param aDocTypeID
   *        The document type identifier to use. May not be <code>null</code>.
   * @return this
   * @since 8.6.1
   */
  @Nonnull
  public PeppolSBDHData setDocumentType (@Nonnull final IDocumentTypeIdentifier aDocTypeID)
  {
    ValueEnforcer.notNull (aDocTypeID, "DocTypeID");

    return setDocumentType (aDocTypeID.getScheme (), aDocTypeID.getValue ());
  }

  /**
   * Set the document type identifier using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS} .
   *
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code> nor empty. This
   *        field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setDocumentTypeWithBusdoxDocidQns (@Nonnull @Nonempty final String sValue)
  {
    return setDocumentType (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, sValue);
  }

  /**
   * Set the document type identifier using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD} .
   *
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code> nor empty. This
   *        field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setDocumentTypeWithPeppolDoctypeWildcard (@Nonnull @Nonempty final String sValue)
  {
    return setDocumentType (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD, sValue);
  }

  /**
   * @return The process identifier scheme. May be <code>null</code> if not initialized yet. This
   *         field is currently not mapped.
   */
  @Nullable
  public String getProcessScheme ()
  {
    return m_sProcessScheme;
  }

  /**
   * @return The process identifier value. May be <code>null</code> if not initialized yet. This
   *         field is mapped to
   *         <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *         .
   */
  @Nullable
  public String getProcessValue ()
  {
    return m_sProcessValue;
  }

  /**
   * @return The process identifier as an object or <code>null</code> if certain information are
   *         missing or are invalid.
   */
  @Nullable
  public IProcessIdentifier getProcessAsIdentifier ()
  {
    return m_aIdentifierFactory.createProcessIdentifier (m_sProcessScheme, m_sProcessValue);
  }

  /**
   * Set the process identifier.
   *
   * @param sScheme
   *        The process identifier scheme. May neither be <code>null</code> nor empty.
   * @param sValue
   *        The process identifier. May neither be <code>null</code> nor empty. This field is mapped
   *        to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setProcess (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sProcessScheme = sScheme;
    m_sProcessValue = sValue;
    return this;
  }

  /**
   * Set the process identifier.
   *
   * @param aProcessID
   *        The process identifier to use. May not be <code>null</code>.
   * @return this
   * @since 8.6.1
   */
  @Nonnull
  public PeppolSBDHData setProcess (@Nonnull final IProcessIdentifier aProcessID)
  {
    ValueEnforcer.notNull (aProcessID, "ProcessID");

    return setProcess (aProcessID.getScheme (), aProcessID.getValue ());
  }

  /**
   * Set the document type identifier using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DEFAULT_PROCESS_SCHEME}.
   *
   * @param sValue
   *        The process identifier. May neither be <code>null</code> nor empty. This field is mapped
   *        to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Nonnull
  public PeppolSBDHData setProcessWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setProcess (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, sValue);
  }

  /**
   * Get the country code of C1. This is a new mandatory field, introduced by the Peppol Business
   * Message Envelope 2.0 specification. It is mapped inside the BusinessScope area.
   *
   * @return The country code of C1. May be <code>null</code>.
   * @see #setCountryC1(String)
   * @see #hasCountryC1()
   * @since 9.0.5
   */
  @Nullable
  public String getCountryC1 ()
  {
    return m_sCountryC1;
  }

  /**
   * Check if the country code of C1 is present or not.
   *
   * @return <code>true</code> if the country code of C1 is present, <code>false</code> if not.
   * @see #getCountryC1()
   * @see #setCountryC1(String)
   * @since 9.0.5
   */
  public boolean hasCountryC1 ()
  {
    return StringHelper.hasText (m_sCountryC1);
  }

  /**
   * Set the country code of C1 to be used.
   *
   * @param sCountryC1
   *        The country code of C1 to be used. May neither be <code>null</code> nor empty.
   * @return this for chaining
   * @see #getCountryC1()
   * @see #hasCountryC1()
   * @since 9.0.5
   */
  @Nonnull
  public PeppolSBDHData setCountryC1 (@Nonnull @Nonempty final String sCountryC1)
  {
    ValueEnforcer.notEmpty (sCountryC1, "CountryC1");

    m_sCountryC1 = sCountryC1;
    return this;
  }

  /**
   * Get the optional MLS addressee scheme (MLS_TO) as used in the Peppol MLS specification. It is
   * mapped inside the BusinessScope area.
   *
   * @return The optional MLS addressee scheme (MLS_TO) to be used. May be <code>null</code>.
   * @see #setMLSToScheme(String)
   * @see #hasMLSToScheme()
   * @since 10.3.3
   */
  @Nullable
  public String getMLSToScheme ()
  {
    return m_sMLSToScheme;
  }

  /**
   * Check if the optional MLS addressee scheme (MLS_TO) is present or not.
   *
   * @return <code>true</code> if the optional MLS addressee scheme (MLS_TO) is present,
   *         <code>false</code> if not.
   * @see #getMLSToScheme()
   * @see #setMLSToScheme(String)
   * @since 10.3.3
   */
  public boolean hasMLSToScheme ()
  {
    return StringHelper.hasText (m_sMLSToScheme);
  }

  /**
   * Set the optional MLS addressee scheme (MLS_TO) to be used.
   *
   * @param sMLSToScheme
   *        The optional MLS addressee scheme (MLS_TO) to be used. May be <code>null</code>.
   * @return this for chaining
   * @see #getMLSToScheme()
   * @see #hasMLSToScheme()
   * @since 10.3.3
   */
  @Nonnull
  public PeppolSBDHData setMLSToScheme (@Nullable final String sMLSToScheme)
  {
    m_sMLSToScheme = sMLSToScheme;
    return this;
  }

  /**
   * Get the optional MLS addressee value (MLS_TO) as used in the Peppol MLS specification. It is
   * mapped inside the BusinessScope area.
   *
   * @return The optional MLS addressee value (MLS_TO) to be used. May be <code>null</code>.
   * @see #setMLSToValue(String)
   * @see #hasMLSToValue()
   * @since 10.3.3
   */
  @Nullable
  public String getMLSToValue ()
  {
    return m_sMLSToValue;
  }

  /**
   * Check if the optional MLS addressee value (MLS_TO) is present or not.
   *
   * @return <code>true</code> if the optional MLS addressee value (MLS_TO) is present,
   *         <code>false</code> if not.
   * @see #getMLSToValue()
   * @see #setMLSToValue(String)
   * @since 10.3.3
   */
  public boolean hasMLSToValue ()
  {
    return StringHelper.hasText (m_sMLSToValue);
  }

  /**
   * Set the optional MLS addressee value (MLS_TO) to be used.
   *
   * @param sMLSToValue
   *        The optional MLS addressee value (MLS_TO) to be used. May be <code>null</code>.
   * @return this for chaining
   * @see #getMLSToValue()
   * @see #hasMLSToValue()
   * @since 10.3.3
   */
  @Nonnull
  public PeppolSBDHData setMLSToValue (@Nullable final String sMLSToValue)
  {
    m_sMLSToValue = sMLSToValue;
    return this;
  }

  /**
   * Get the optional MLS type (MLS_TYPE) as used in the Peppol MLS specification. It is mapped
   * inside the BusinessScope area.
   *
   * @return The optional MLS addressee to be used. May be <code>null</code>.
   * @see #setMLSType(EPeppolMLSType)
   * @see #hasMLSType()
   * @since 10.3.3
   */
  @Nullable
  public EPeppolMLSType getMLSType ()
  {
    return m_eMLSType;
  }

  /**
   * Check if the optional MLS type (MLS_TYPE) is present or not.
   *
   * @return <code>true</code> if the optional MLS type (MLS_TYPE) is present, <code>false</code> if
   *         not.
   * @see #getMLSType()
   * @see #setMLSType(EPeppolMLSType)
   * @since 10.3.3
   */
  public boolean hasMLSType ()
  {
    return m_eMLSType != null;
  }

  /**
   * Set the optional MLS type (MLS_TYPE) to be used.
   *
   * @param eMLSType
   *        The optional MLS type (MLS_TYPE) to be used. May be <code>null</code>.
   * @return this for chaining
   * @see #getMLSType()
   * @see #hasMLSType()
   * @since 10.3.3
   */
  @Nonnull
  public PeppolSBDHData setMLSType (@Nullable final EPeppolMLSType eMLSType)
  {
    m_eMLSType = eMLSType;
    return this;
  }

  /**
   * @return The mutable attribute map where all additional attributes according to Spec v1.1,
   *         chapter 1.6.1 were added.
   * @since 6.1.4
   */
  @Nonnull
  @ReturnsMutableObject
  public StringMap additionalAttributes ()
  {
    return m_aAdditionalAttrs;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sStandard
   *        The standard of the enveloped business message, normally described by use of the XML
   *        namespace of the business message root element (such as urn:oasis:names:specification:
   *        ubl:schema:xsd:Order-2). May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code> .
   * @param sTypeVersion
   *        The version number of the enveloped business message (such as the value "2.1" for OASIS
   *        UBL 2.1). May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code> .
   * @param sType
   *        Message type - mandatory in SBDH. XML local element name of the root-element in the
   *        business message. May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Type</code> .
   * @param sInstanceIdentifier
   *        An informative unique ID created by the issuer of the envelope. The InstanceIdentifier
   *        MUST be unique for each Business Message Envelope being created. This ID is not the same
   *        as the ID of the business message (such as the Invoice Number). It is not the same as a
   *        transmission Message ID generated by the application sending the message (as defined in
   *        AS4).<br>
   *        The InstanceIdentifier MUST be globally unique and it is RECOMMENDED to use UUID (such
   *        as 118e3040-51d2-11e3-8f96-0800200c9a66). May not be <code>null</code>. This field is
   *        mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code> .
   * @param aCreationDateAndTime
   *        The date and time for when this envelope was created. It is NOT necessarily the same as
   *        the issue date of the business document (such as the invoice) being enveloped. It is NOT
   *        necessarily the date time for transmission.<br>
   *        The format of the value of this MUST include timezone information. May not be
   *        <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code> .
   * @return this
   * @see #setStandard(String)
   * @see #setTypeVersion(String)
   * @see #setType(String)
   * @see #setInstanceIdentifier(String)
   * @see #setCreationDateAndTime(XMLOffsetDateTime)
   */
  @Nonnull
  public PeppolSBDHData setDocumentIdentification (@Nonnull @Nonempty final String sStandard,
                                                   @Nonnull @Nonempty final String sTypeVersion,
                                                   @Nonnull @Nonempty final String sType,
                                                   @Nonnull @Nonempty final String sInstanceIdentifier,
                                                   @Nonnull final XMLOffsetDateTime aCreationDateAndTime)
  {
    setStandard (sStandard);
    setTypeVersion (sTypeVersion);
    setType (sType);
    setInstanceIdentifier (sInstanceIdentifier);
    setCreationDateAndTime (aCreationDateAndTime);
    return this;
  }

  /**
   * The standard of the enveloped business message, normally described by use of the XML namespace
   * of the business message root element (such as
   * urn:oasis:names:specification:ubl:schema:xsd:Order-2). This field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code> .
   *
   * @return The standard value. May be <code>null</code>.
   */
  @Nullable
  public String getStandard ()
  {
    return m_sStandard;
  }

  /**
   * @return <code>true</code> if a standard is present, <code>false</code> if not.
   * @since 7.0.0
   */
  public boolean hasStandard ()
  {
    return StringHelper.hasText (m_sStandard);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sStandard
   *        The standard of the enveloped business message, normally described by use of the XML
   *        namespace of the business message root element (such as urn:oasis:names:specification:
   *        ubl:schema:xsd:Order-2). May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code> .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String, XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setStandard (@Nonnull @Nonempty final String sStandard)
  {
    ValueEnforcer.notEmpty (sStandard, "Standard");

    m_sStandard = sStandard;
    return this;
  }

  /**
   * The version number of the enveloped business message (such as the value "2.1" for OASIS UBL 2.1
   * or "2.2" for OASIS UBL 2.2). This field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code> .
   *
   * @return The type version. May be <code>null</code>.
   */
  @Nullable
  public String getTypeVersion ()
  {
    return m_sTypeVersion;
  }

  /**
   * @return <code>true</code> if a type version is present, <code>false</code> if not.
   * @since 7.0.0
   */
  public boolean hasTypeVersion ()
  {
    return StringHelper.hasText (m_sTypeVersion);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sTypeVersion
   *        The version number of the enveloped business message (such as the value "2.1" for OASIS
   *        UBL 2.1). May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code> .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String, XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setTypeVersion (@Nonnull @Nonempty final String sTypeVersion)
  {
    ValueEnforcer.notEmpty (sTypeVersion, "TypeVersion");

    m_sTypeVersion = sTypeVersion;
    return this;
  }

  /**
   * Message type - mandatory in SBDH. XML local element name of the root-element in the business
   * message. This field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/Type</code>.
   *
   * @return Type value. May be <code>null</code>.
   */
  @Nullable
  public String getType ()
  {
    return m_sType;
  }

  /**
   * @return <code>true</code> if a type is present, <code>false</code> if not.
   * @since 7.0.0
   */
  public boolean hasType ()
  {
    return StringHelper.hasText (m_sType);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sType
   *        Message type - mandatory in SBDH. XML local element name of the root-element in the
   *        business message. May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Type</code> .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String, XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setType (@Nonnull @Nonempty final String sType)
  {
    ValueEnforcer.notEmpty (sType, "Type");

    m_sType = sType;
    return this;
  }

  /**
   * An informative unique ID created by the issuer of the envelope. The InstanceIdentifier MUST be
   * unique for each Business Message Envelope being created. This ID is not the same as the ID of
   * the business message (such as the Invoice Number). It is not the same as a transmission Message
   * ID generated by the application sending the message (as defined in AS2 or START).<br>
   * The InstanceIdentifier MUST be globally unique and it is RECOMMENDED to use UUID (such as
   * 118e3040-51d2-11e3-8f96-0800200c9a66). This field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code> .
   *
   * @return The instance identifier. May be <code>null</code>.
   */
  @Nullable
  public String getInstanceIdentifier ()
  {
    return m_sInstanceIdentifier;
  }

  /**
   * @return <code>true</code> if an instance identifier is present, <code>false</code> if not.
   * @since 7.0.0
   */
  public boolean hasInstanceIdentifier ()
  {
    return StringHelper.hasText (m_sInstanceIdentifier);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sInstanceIdentifier
   *        An informative unique ID created by the issuer of the envelope. The InstanceIdentifier
   *        MUST be unique for each Business Message Envelope being created. This ID is not the same
   *        as the ID of the business message (such as the Invoice Number). It is not the same as a
   *        transmission Message ID generated by the application sending the message (as defined in
   *        AS4).<br>
   *        The InstanceIdentifier MUST be globally unique and it is RECOMMENDED to use UUID (such
   *        as 118e3040-51d2-11e3-8f96-0800200c9a66). May not be <code>null</code>. This field is
   *        mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code> .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String, XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setInstanceIdentifier (@Nonnull @Nonempty final String sInstanceIdentifier)
  {
    ValueEnforcer.notEmpty (sInstanceIdentifier, "InstanceIdentifier");

    m_sInstanceIdentifier = sInstanceIdentifier;
    return this;
  }

  /**
   * The date and time for when this envelope was created. It is NOT necessarily the same as the
   * issue date of the business document (such as the invoice) being enveloped. It is NOT
   * necessarily the date time for transmission.<br>
   * The format of the value of this MUST include timezone information. This field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code> .
   *
   * @return The creation date time. May be <code>null</code>.
   */
  @Nullable
  public XMLOffsetDateTime getCreationDateAndTime ()
  {
    return m_aCreationDateAndTime;
  }

  /**
   * @return <code>true</code> if creation date and time is present, <code>false</code> if not.
   * @since 7.0.0
   */
  public boolean hasCreationDateAndTime ()
  {
    return m_aCreationDateAndTime != null;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param aCreationDateAndTime
   *        The date and time for when this envelope was created. It is NOT necessarily the same as
   *        the issue date of the business document (such as the invoice) being enveloped. It is NOT
   *        necessarily the date time for transmission.<br>
   *        The format of the value of this MUST include timezone information. May not be
   *        <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code> .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String, XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Nonnull
  public PeppolSBDHData setCreationDateAndTime (@Nonnull final XMLOffsetDateTime aCreationDateAndTime)
  {
    ValueEnforcer.notNull (aCreationDateAndTime, "CreationDateAndTime");

    // Make sure to use only milliseconds for XML usage
    m_aCreationDateAndTime = PDTFactory.getWithMillisOnly (aCreationDateAndTime);
    return this;
  }

  /**
   * Get the contained business message.
   *
   * @return <code>null</code> if no business message is present. A clone (deep copy) of the
   *         business message otherwise.
   * @see #getBusinessMessageNoClone()
   */
  @Nullable
  @ReturnsMutableCopy
  public Element getBusinessMessage ()
  {
    return m_aBusinessMessage == null ? null : (Element) m_aBusinessMessage.cloneNode (true);
  }

  /**
   * Get the contained business message without cloning it.
   *
   * @return <code>null</code> if no business message is present.
   * @see #getBusinessMessage()
   * @since 8.6.1
   */
  @Nullable
  @ReturnsMutableObject
  public Element getBusinessMessageNoClone ()
  {
    return m_aBusinessMessage;
  }

  /**
   * Check if a business message is present without having the need to explicitly call
   * {@link #getBusinessMessage()} which returns a cloned node and is therefore an expensive
   * operation.
   *
   * @return <code>true</code> if a business message is present, <code>false</code> otherwise.
   */
  public boolean hasBusinessMessage ()
  {
    return m_aBusinessMessage != null;
  }

  /**
   * Parse the existing business message as a special Peppol "BinaryContent" element.
   *
   * @return The parsed payload as a Peppol SBDH "BinaryContent" or <code>null</code> if the
   *         existing Business Message is not a valid binary content.
   * @see #setBusinessMessageBinaryOnly(byte[], IMimeType, Charset)
   * @since 8.2.4
   */
  @Nullable
  public BinaryContentType getBusinessMessageAsBinaryContent ()
  {
    if (m_aBusinessMessage == null)
      return null;

    if (!"BinaryContent".equals (XMLHelper.getLocalNameOrTagName (m_aBusinessMessage)))
      return null;

    return new PeppolSBDHPayloadBinaryMarshaller ().read (m_aBusinessMessage);
  }

  /**
   * Parse the existing business message as a special Peppol SBDH "TextContent" element.
   *
   * @return The parsed payload as a Peppol "TextContent" or <code>null</code> if the existing
   *         Business Message is not a valid text content.
   * @see #setBusinessMessageTextOnly(String, IMimeType)
   * @since 8.2.4
   */
  @Nullable
  public TextContentType getBusinessMessageAsTextContent ()
  {
    if (m_aBusinessMessage == null)
      return null;

    if (!"TextContent".equals (XMLHelper.getLocalNameOrTagName (m_aBusinessMessage)))
      return null;

    return new PeppolSBDHPayloadTextMarshaller ().read (m_aBusinessMessage);
  }

  /**
   * Set the main business message that should be transmitted together with the SBDH. The DOM
   * element is cloned internally to avoid outside modification
   *
   * @param aBusinessMessage
   *        The business message to be set. May not be <code>null</code>. Internally the passed
   *        element is cloned, so that further modifications outside of this method have no impact
   *        on the business message inside this object.
   * @return this
   * @see #setBusinessMessageNoClone(Element)
   */
  @Nonnull
  public PeppolSBDHData setBusinessMessage (@Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    // Create a deep copy of the element to avoid outside modifications
    m_aBusinessMessage = (Element) aBusinessMessage.cloneNode (true);
    return this;
  }

  /**
   * Set the main business message that should be transmitted together with the SBDH. The DOM
   * element is not cloned / copied internally.
   *
   * @param aBusinessMessage
   *        The business message to be set. May not be <code>null</code>. Internally the passed
   *        element is cloned, so that further modifications outside of this method have no impact
   *        on the business message inside this object.
   * @return this
   * @see #setBusinessMessage(Element)
   * @since 8.8.1
   */
  @Nonnull
  public PeppolSBDHData setBusinessMessageNoClone (@Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    m_aBusinessMessage = aBusinessMessage;
    return this;
  }

  /**
   * Set a business message with binary payload. Based on the Peppol SBDH v1.2 binary payload
   * specification. This is wrapper for creating the Peppol SBDH "BinaryContent" element.
   *
   * @param aBinaryPayload
   *        The bytes to be wrapped. May not be <code>null</code>.
   * @param aMimeType
   *        The MIME type to use. May not be <code>null</code>.
   * @param aCharset
   *        The character set to be used, if the MIME type is text based. May be <code>null</code>.
   * @return this for chaining
   * @see #setBusinessMessage(Element)
   * @see #setBusinessMessageTextOnly(String, IMimeType)
   * @see #getBusinessMessageAsBinaryContent()
   * @since 6.2.4
   */
  @Nonnull
  public PeppolSBDHData setBusinessMessageBinaryOnly (@Nonnull final byte [] aBinaryPayload,
                                                      @Nonnull final IMimeType aMimeType,
                                                      @Nullable final Charset aCharset)
  {
    ValueEnforcer.notNull (aBinaryPayload, "BinaryPayload");
    ValueEnforcer.notNull (aMimeType, "MimeType");

    final BinaryContentType aBC = new BinaryContentType ();
    aBC.setValue (aBinaryPayload);
    aBC.setMimeType (aMimeType.getAsString ());
    aBC.setEncoding (aCharset == null ? null : aCharset.name ());
    m_aBusinessMessage = new PeppolSBDHPayloadBinaryMarshaller ().getAsElement (aBC);
    if (m_aBusinessMessage == null)
      throw new IllegalStateException ("Failed to create 'BinaryContent' element.");
    return this;
  }

  /**
   * Set a business message with text payload. Based on the Peppol SBDH v1.2 text payload
   * specification. Note: the character set of the wrapped text must be identical to the character
   * set of the SBDH surrounding it. In case the payload requires a specific character set, it is
   * suggested to use the binary message. This is wrapper for creating the Peppol SBDH "TextContent"
   * element.
   *
   * @param sTextPayload
   *        The text to be wrapped. May not be <code>null</code>.
   * @param aMimeType
   *        The MIME type to use. May not be <code>null</code>.
   * @return this for chaining
   * @see #setBusinessMessage(Element)
   * @see #setBusinessMessageBinaryOnly(byte[], IMimeType, Charset)
   * @see #getBusinessMessageAsTextContent()
   * @since 6.2.4
   */
  @Nonnull
  public PeppolSBDHData setBusinessMessageTextOnly (@Nonnull final String sTextPayload,
                                                    @Nonnull final IMimeType aMimeType)
  {
    ValueEnforcer.notNull (sTextPayload, "TextPayload");
    ValueEnforcer.notNull (aMimeType, "MimeType");

    final TextContentType aTC = new TextContentType ();
    aTC.setValue (sTextPayload);
    aTC.setMimeType (aMimeType.getAsString ());
    m_aBusinessMessage = new PeppolSBDHPayloadTextMarshaller ().getAsElement (aTC);
    if (m_aBusinessMessage == null)
      throw new IllegalStateException ("Failed to create 'TextContent' element.");
    return this;
  }

  /**
   * Check if all mandatory fields are set in the SBDH data.
   *
   * @param bLogMissing
   *        <code>true</code> if log messages should be emitted, <code>false</code> if not
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   */
  public boolean areAllFieldsSet (final boolean bLogMissing)
  {
    return areAllFieldsSet (bLogMissing ? LOGGER::info : x -> {});
  }

  /**
   * Check if all mandatory fields are set in the SBDH data.
   *
   * @param aMissingFieldConsumer
   *        The consumer to be invoked for each missing field. May not be <code>null</code>
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   * @since 9.6.1
   */
  public boolean areAllFieldsSet (@Nonnull final Consumer <String> aMissingFieldConsumer)
  {
    ValueEnforcer.notNull (aMissingFieldConsumer, "MissingFieldConsumer");

    int nMissing = 0;
    if (StringHelper.hasNoText (m_sSenderScheme))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Sender Scheme is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sSenderValue))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Sender Value is missing");
      nMissing++;
    }

    if (StringHelper.hasNoText (m_sReceiverScheme))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Receiver Scheme is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sReceiverValue))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Reeiver Value is missing");
      nMissing++;
    }

    if (StringHelper.hasNoText (m_sDocumentTypeScheme))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Document Type ID Scheme is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sDocumentTypeValue))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Document Type ID Value is missing");
      nMissing++;
    }

    if (StringHelper.hasNoText (m_sProcessScheme))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Process ID Scheme is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sProcessValue))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Process ID Value is missing");
      nMissing++;
    }

    if (StringHelper.hasNoText (m_sCountryC1))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Country C1 is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sStandard))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Standard is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sTypeVersion))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Type Version is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sType))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Type is missing");
      nMissing++;
    }
    if (StringHelper.hasNoText (m_sInstanceIdentifier))
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Instance Identifier is missing");
      nMissing++;
    }
    if (m_aCreationDateAndTime == null)
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Creation Date and Time is missing");
      nMissing++;
    }
    if (m_aBusinessMessage == null)
    {
      aMissingFieldConsumer.accept ("Peppol SBDH data - Business Message is missing");
      nMissing++;
    }

    return nMissing == 0;
  }

  /**
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   */
  public boolean areAllFieldsSet ()
  {
    return areAllFieldsSet (false);
  }

  /**
   * Check if all additional attributes contain non-reserved names.
   *
   * @return <code>true</code> if no additional attributes are present or if all additional
   *         attributes contain valid names.
   */
  public boolean areAllAdditionalAttributesValid ()
  {
    if (m_aAdditionalAttrs.isNotEmpty ())
      for (final String sName : m_aAdditionalAttrs.keySet ())
        if (PeppolSBDHAdditionalAttributes.isReservedAttributeName (sName))
          return false;

    return true;
  }

  /**
   * @return A generic JAXB SBD document of this data. Never <code>null</code>.
   * @since 9.2.0
   * @see PeppolSBDHDataWriter for the main logic
   */
  @Nonnull
  public StandardBusinessDocument getAsStandardBusinessDocument ()
  {
    return new PeppolSBDHDataWriter ().createStandardBusinessDocument (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PeppolSBDHData rhs = (PeppolSBDHData) o;
    return EqualsHelper.equals (m_sSenderScheme, rhs.m_sSenderScheme) &&
           EqualsHelper.equals (m_sSenderValue, rhs.m_sSenderValue) &&
           EqualsHelper.equals (m_sReceiverScheme, rhs.m_sReceiverScheme) &&
           EqualsHelper.equals (m_sReceiverValue, rhs.m_sReceiverValue) &&
           EqualsHelper.equals (m_sDocumentTypeScheme, rhs.m_sDocumentTypeScheme) &&
           EqualsHelper.equals (m_sDocumentTypeValue, rhs.m_sDocumentTypeValue) &&
           EqualsHelper.equals (m_sProcessScheme, rhs.m_sProcessScheme) &&
           EqualsHelper.equals (m_sProcessValue, rhs.m_sProcessValue) &&
           EqualsHelper.equals (m_sCountryC1, rhs.m_sCountryC1) &&
           EqualsHelper.equals (m_sMLSToScheme, rhs.m_sMLSToScheme) &&
           EqualsHelper.equals (m_sMLSToValue, rhs.m_sMLSToValue) &&
           EqualsHelper.equals (m_eMLSType, rhs.m_eMLSType) &&
           EqualsHelper.equals (m_sStandard, rhs.m_sStandard) &&
           EqualsHelper.equals (m_sTypeVersion, rhs.m_sTypeVersion) &&
           EqualsHelper.equals (m_sType, rhs.m_sType) &&
           EqualsHelper.equals (m_sInstanceIdentifier, rhs.m_sInstanceIdentifier) &&
           EqualsHelper.equals (m_aCreationDateAndTime, rhs.m_aCreationDateAndTime) &&
           EqualsHelper.equals (m_aBusinessMessage, rhs.m_aBusinessMessage) &&
           m_aAdditionalAttrs.equals (rhs.m_aAdditionalAttrs);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sSenderScheme)
                                       .append (m_sSenderValue)
                                       .append (m_sReceiverScheme)
                                       .append (m_sReceiverValue)
                                       .append (m_sDocumentTypeScheme)
                                       .append (m_sDocumentTypeValue)
                                       .append (m_sProcessScheme)
                                       .append (m_sProcessValue)
                                       .append (m_sCountryC1)
                                       .append (m_sMLSToScheme)
                                       .append (m_sMLSToValue)
                                       .append (m_eMLSType)
                                       .append (m_sStandard)
                                       .append (m_sTypeVersion)
                                       .append (m_sType)
                                       .append (m_sInstanceIdentifier)
                                       .append (m_aCreationDateAndTime)
                                       .append (m_aBusinessMessage)
                                       .append (m_aAdditionalAttrs)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SenderScheme", m_sSenderScheme)
                                       .append ("SenderValue", m_sSenderValue)
                                       .append ("ReceiverScheme", m_sReceiverScheme)
                                       .append ("ReceiverValue", m_sReceiverValue)
                                       .append ("DocumentTypeScheme", m_sDocumentTypeScheme)
                                       .append ("DocumentTypeValue", m_sDocumentTypeValue)
                                       .append ("ProcessScheme", m_sProcessScheme)
                                       .append ("ProcessValue", m_sProcessValue)
                                       .append ("CountryC1", m_sCountryC1)
                                       .append ("MLSToScheme", m_sMLSToScheme)
                                       .append ("MLSToValue", m_sMLSToValue)
                                       .append ("MLSType", m_eMLSType)
                                       .append ("Standard", m_sStandard)
                                       .append ("TypeVersion", m_sTypeVersion)
                                       .append ("Type", m_sType)
                                       .append ("InstanceIdentifier", m_sInstanceIdentifier)
                                       .append ("CreationDateAndTime", m_aCreationDateAndTime)
                                       .append ("BusinessMessage", m_aBusinessMessage)
                                       .append ("AdditionalAttributes", m_aAdditionalAttrs)
                                       .getToString ();
  }

  /**
   * Create a new {@link PeppolSBDHData} object for a business message assuming it is UBL 2.1. The
   * resulting object has all required fields set, except for:
   * <ul>
   * <li>sender ID</li>
   * <li>receiver ID</li>
   * <li>document type ID</li>
   * <li>and process ID</li>
   * </ul>
   *
   * @param aBusinessMessage
   *        The XML business message. May not be <code>null</code>.
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   * @return A pre-filled {@link PeppolSBDHData} object with some information still missing.
   * @see #setSender(String, String)
   * @see #setReceiver(String, String)
   * @see #setDocumentType(String, String)
   * @see #setProcess(String, String)
   * @since 8.3.1
   */
  @Nonnull
  public static PeppolSBDHData createUBL21 (@Nonnull final Element aBusinessMessage,
                                            @Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    final PeppolSBDHData ret = new PeppolSBDHData (aIdentifierFactory);
    ret.setBusinessMessage (aBusinessMessage);
    // 1. Always use UBL 2.1
    // 2. Use a new UUID as the instance identifier
    // 3. Use the current date time
    ret.setDocumentIdentification (aBusinessMessage.getNamespaceURI (),
                                   CPeppolSBDH.TYPE_VERSION_21,
                                   aBusinessMessage.getLocalName (),
                                   UUID.randomUUID ().toString (),
                                   PDTFactory.getCurrentXMLOffsetDateTimeMillisOnly ());
    return ret;
  }
}
