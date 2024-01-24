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
package com.helger.peppol.sbdh;

import java.nio.charset.Charset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.w3c.dom.Element;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.commons.mime.IMimeType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * This class contains all the Peppol data per SBDH document in a syntax neutral
 * way.
 *
 * @author Philip Helger
 * @deprecated Use {@link PeppolSBDHData} instead
 */
@Deprecated (forRemoval = true, since = "9.2.0")
@NotThreadSafe
public class PeppolSBDHDocument extends PeppolSBDHData
{
  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public PeppolSBDHDocument (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    super (aIdentifierFactory);
  }

  /**
   * Set the sender participant identifier.
   *
   * @param aSenderID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   * @since 8.6.1
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setSender (@Nonnull final IParticipantIdentifier aSenderID)
  {
    super.setSender (aSenderID);
    return this;
  }

  /**
   * Set the sender participant identifier value using the default identifier
   * scheme/authority {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setSenderWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    super.setSenderWithDefaultScheme (sValue);
    return this;
  }

  /**
   * Set the sender participant identifier.
   *
   * @param sScheme
   *        The Peppol identifier scheme. This is usually always
   *        {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}. May
   *        neither be <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/@Authority</code>
   *        .
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setSender (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    super.setSender (sScheme, sValue);
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
  @Override
  @Nonnull
  public PeppolSBDHDocument setReceiver (@Nonnull final IParticipantIdentifier aReceiverID)
  {
    super.setReceiver (aReceiverID);
    return this;
  }

  /**
   * Set the receiver participant identifier value using the default identifier
   * scheme/authority {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setReceiverWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    super.setReceiverWithDefaultScheme (sValue);
    return this;
  }

  /**
   * Set the receiver participant identifier.
   *
   * @param sScheme
   *        The Peppol identifier scheme. This is usually always
   *        {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME} . May
   *        neither be <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/@Authority</code>
   *        .
   * @param sValue
   *        The receiver identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setReceiver (@Nonnull @Nonempty final String sScheme,
                                         @Nonnull @Nonempty final String sValue)
  {
    super.setReceiver (sScheme, sValue);
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
  @Override
  @Nonnull
  public PeppolSBDHDocument setDocumentType (@Nonnull final IDocumentTypeIdentifier aDocTypeID)
  {
    super.setDocumentType (aDocTypeID);
    return this;
  }

  /**
   * Set the document type identifier.
   *
   * @param sScheme
   *        The document type identifier scheme. May neither be
   *        <code>null</code> nor empty.
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code>
   *        nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setDocumentType (@Nonnull @Nonempty final String sScheme,
                                             @Nonnull @Nonempty final String sValue)
  {
    super.setDocumentType (sScheme, sValue);
    return this;
  }

  /**
   * Set the document type identifier using the default identifier
   * scheme/authority
   * {@link PeppolIdentifierHelper#DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS} .
   *
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code>
   *        nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setDocumentTypeWithBusdoxDocidQns (@Nonnull @Nonempty final String sValue)
  {
    super.setDocumentTypeWithBusdoxDocidQns (sValue);
    return this;
  }

  /**
   * Set the document type identifier using the default identifier
   * scheme/authority
   * {@link PeppolIdentifierHelper#DOCUMENT_TYPE_SCHEME_PEPPOL_DOCTYPE_WILDCARD}
   * .
   *
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code>
   *        nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setDocumentTypeWithPeppolDoctypeWildcard (@Nonnull @Nonempty final String sValue)
  {
    super.setDocumentTypeWithPeppolDoctypeWildcard (sValue);
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
  @Override
  @Nonnull
  public PeppolSBDHDocument setProcess (@Nonnull final IProcessIdentifier aProcessID)
  {
    super.setProcess (aProcessID);
    return this;
  }

  /**
   * Set the document type identifier using the default identifier
   * scheme/authority {@link PeppolIdentifierHelper#DEFAULT_PROCESS_SCHEME}.
   *
   * @param sValue
   *        The process identifier. May neither be <code>null</code> nor empty.
   *        This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setProcessWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    super.setProcessWithDefaultScheme (sValue);
    return this;
  }

  /**
   * Set the process identifier.
   *
   * @param sScheme
   *        The process identifier scheme. May neither be <code>null</code> nor
   *        empty.
   * @param sValue
   *        The process identifier. May neither be <code>null</code> nor empty.
   *        This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setProcess (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    super.setProcess (sScheme, sValue);
    return this;
  }

  /**
   * Set the country code of C1 to be used.
   *
   * @param sCountryC1
   *        The country code of C1 to be used. May be <code>null</code>.
   * @return this for chaining
   * @see #getCountryC1()
   * @see #hasCountryC1()
   * @since 9.0.5
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setCountryC1 (@Nullable final String sCountryC1)
  {
    super.setCountryC1 (sCountryC1);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sStandard
   *        The standard of the enveloped business message, normally described
   *        by use of the XML namespace of the business message root element
   *        (such as urn:oasis:names:specification: ubl:schema:xsd:Order-2). May
   *        not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code>
   *        .
   * @param sTypeVersion
   *        The version number of the enveloped business message (such as the
   *        value "2.1" for OASIS UBL 2.1 or "2.0" for OASIS UBL 2.0). May not
   *        be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code>
   *        .
   * @param sType
   *        Message type - mandatory in SBDH. XML local element name of the
   *        root-element in the business message. May not be <code>null</code>.
   *        This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Type</code>
   *        .
   * @param sInstanceIdentifier
   *        An informative unique ID created by the issuer of the envelope. The
   *        InstanceIdentifier MUST be unique for each Business Message Envelope
   *        being created. This ID is not the same as the ID of the business
   *        message (such as the Invoice Number). It is not the same as a
   *        transmission Message ID generated by the application sending the
   *        message (as defined in AS2 or START).<br>
   *        The InstanceIdentifier MUST be globally unique and it is RECOMMENDED
   *        to use UUID (such as 118e3040-51d2-11e3-8f96-0800200c9a66). May not
   *        be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code>
   *        .
   * @param aCreationDateAndTime
   *        The date and time for when this envelope was created. It is NOT
   *        necessarily the same as the issue date of the business document
   *        (such as the invoice) being enveloped. It is NOT necessarily the
   *        date time for transmission.<br>
   *        The format of the value of this MUST include timezone information.
   *        May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code>
   *        .
   * @return this
   * @see #setStandard(String)
   * @see #setTypeVersion(String)
   * @see #setType(String)
   * @see #setInstanceIdentifier(String)
   * @see #setCreationDateAndTime(XMLOffsetDateTime)
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setDocumentIdentification (@Nonnull final String sStandard,
                                                             @Nonnull final String sTypeVersion,
                                                             @Nonnull final String sType,
                                                             @Nonnull final String sInstanceIdentifier,
                                                             @Nonnull final XMLOffsetDateTime aCreationDateAndTime)
  {
    super.setDocumentIdentification (sStandard, sTypeVersion, sType, sInstanceIdentifier, aCreationDateAndTime);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sStandard
   *        The standard of the enveloped business message, normally described
   *        by use of the XML namespace of the business message root element
   *        (such as urn:oasis:names:specification: ubl:schema:xsd:Order-2). May
   *        not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code>
   *        .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String,
   *      XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setStandard (@Nonnull final String sStandard)
  {
    super.setStandard (sStandard);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sTypeVersion
   *        The version number of the enveloped business message (such as the
   *        value "2.1" for OASIS UBL 2.1 or "2.0" for OASIS UBL 2.0). May not
   *        be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code>
   *        .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String,
   *      XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setTypeVersion (@Nonnull final String sTypeVersion)
  {
    super.setTypeVersion (sTypeVersion);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sType
   *        Message type - mandatory in SBDH. XML local element name of the
   *        root-element in the business message. May not be <code>null</code>.
   *        This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/Type</code>
   *        .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String,
   *      XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setType (@Nonnull final String sType)
  {
    super.setType (sType);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param sInstanceIdentifier
   *        An informative unique ID created by the issuer of the envelope. The
   *        InstanceIdentifier MUST be unique for each Business Message Envelope
   *        being created. This ID is not the same as the ID of the business
   *        message (such as the Invoice Number). It is not the same as a
   *        transmission Message ID generated by the application sending the
   *        message (as defined in AS2 or START).<br>
   *        The InstanceIdentifier MUST be globally unique and it is RECOMMENDED
   *        to use UUID (such as 118e3040-51d2-11e3-8f96-0800200c9a66). May not
   *        be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code>
   *        .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String,
   *      XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setInstanceIdentifier (@Nonnull final String sInstanceIdentifier)
  {
    super.setInstanceIdentifier (sInstanceIdentifier);
    return this;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification</code>.
   *
   * @param aCreationDateAndTime
   *        The date and time for when this envelope was created. It is NOT
   *        necessarily the same as the issue date of the business document
   *        (such as the invoice) being enveloped. It is NOT necessarily the
   *        date time for transmission.<br>
   *        The format of the value of this MUST include timezone information.
   *        May not be <code>null</code>. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code>
   *        .
   * @return this
   * @see #setDocumentIdentification(String, String, String, String,
   *      XMLOffsetDateTime)
   * @since 8.3.1
   */
  @Override
  @Nonnull
  public final PeppolSBDHDocument setCreationDateAndTime (@Nonnull final XMLOffsetDateTime aCreationDateAndTime)
  {
    super.setCreationDateAndTime (aCreationDateAndTime);
    return this;
  }

  /**
   * Set the main business message that should be transmitted together with the
   * SBDH. The DOM element is cloned internally to avoid outside modification
   *
   * @param aBusinessMessage
   *        The business message to be set. May not be <code>null</code>.
   *        Internally the passed element is cloned, so that further
   *        modifications outside of this method have no impact on the business
   *        message inside this object.
   * @return this
   * @see #setBusinessMessageNoClone(Element)
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setBusinessMessage (@Nonnull final Element aBusinessMessage)
  {
    super.setBusinessMessage (aBusinessMessage);
    return this;
  }

  /**
   * Set the main business message that should be transmitted together with the
   * SBDH. The DOM element is not cloned / copied internally.
   *
   * @param aBusinessMessage
   *        The business message to be set. May not be <code>null</code>.
   *        Internally the passed element is cloned, so that further
   *        modifications outside of this method have no impact on the business
   *        message inside this object.
   * @return this
   * @see #setBusinessMessage(Element)
   * @since 8.8.1
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setBusinessMessageNoClone (@Nonnull final Element aBusinessMessage)
  {
    super.setBusinessMessageNoClone (aBusinessMessage);
    return this;
  }

  /**
   * Set a business message with binary payload. Based on the Peppol SBDH v1.2
   * binary payload specification.
   *
   * @param aBinaryPayload
   *        The bytes to be wrapped. May not be <code>null</code>.
   * @param aMimeType
   *        The MIME type to use. May not be <code>null</code>.
   * @param aCharset
   *        The character set to be used, if the MIME type is text based. May be
   *        <code>null</code>.
   * @return this for chaining
   * @see #setBusinessMessage(Element)
   * @see #setBusinessMessageTextOnly(String, IMimeType)
   * @see #getBusinessMessageAsBinaryContent()
   * @since 6.2.4
   */
  @Override
  @Nonnull
  public PeppolSBDHDocument setBusinessMessageBinaryOnly (@Nonnull final byte [] aBinaryPayload,
                                                          @Nonnull final IMimeType aMimeType,
                                                          @Nullable final Charset aCharset)
  {
    super.setBusinessMessageBinaryOnly (aBinaryPayload, aMimeType, aCharset);
    return this;
  }

  /**
   * Set a business message with text payload. Based on the Peppol SBDH v1.2
   * text payload specification. Note: the character set of the wrapped text
   * must be identical to the character set of the SBDH surrounding it. In case
   * the payload requires a specific character set, it is suggested to use the
   * binary message.
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
  @Override
  @Nonnull
  public PeppolSBDHDocument setBusinessMessageTextOnly (@Nonnull final String sTextPayload,
                                                        @Nonnull final IMimeType aMimeType)
  {
    super.setBusinessMessageTextOnly (sTextPayload, aMimeType);
    return this;
  }
}
