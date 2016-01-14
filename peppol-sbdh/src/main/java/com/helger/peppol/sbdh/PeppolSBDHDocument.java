/**
 * Copyright (C) 2014-2016 Philip Helger
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

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.joda.time.LocalDateTime;
import org.w3c.dom.Element;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.PDTFactory;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.doctype.IPeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.participant.IPeppolParticipantIdentifier;
import com.helger.peppol.identifier.process.IPeppolProcessIdentifier;

/**
 * This class contains all the PEPPOL data per SBDH document in a syntax neutral
 * way.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolSBDHDocument
{
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
  // DocumentIdentification
  private String m_sStandard;
  private String m_sTypeVersion;
  private String m_sType;
  private String m_sInstanceIdentifier;
  private LocalDateTime m_aCreationDateAndTime;
  // BusinessMessage
  private Element m_aBusinessMessage;

  public PeppolSBDHDocument ()
  {}

  /**
   * Set the sender identifier.
   *
   * @param sScheme
   *        The PEPPOL identifier scheme. This is usually always
   *        {@link CIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME}. May
   *        neither be <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/@Authority</code>
   *        .
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setSender (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    m_sSenderScheme = ValueEnforcer.notEmpty (sScheme, "Scheme");
    m_sSenderValue = ValueEnforcer.notEmpty (sValue, "Value");
    return this;
  }

  /**
   * Set the sender identifier value using the default identifier
   * scheme/authority {@link CIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setSenderWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setSender (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * @return The sender identifier scheme. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Sender/Identifier/@Authority</code>
   *         .
   */
  @Nullable
  public String getSenderScheme ()
  {
    return m_sSenderScheme;
  }

  /**
   * @return The sender identifier value. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   */
  @Nullable
  public String getSenderValue ()
  {
    return m_sSenderValue;
  }

  /**
   * @return The sender identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IPeppolParticipantIdentifier getSenderAsIdentifier ()
  {
    return IdentifierHelper.createParticipantIdentifierOrNull (m_sSenderScheme, m_sSenderValue);
  }

  /**
   * Set the receiver identifier.
   *
   * @param sScheme
   *        The PEPPOL identifier scheme. This is usually always
   *        {@link CIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME}. May
   *        neither be <code>null</code> nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/@Authority</code>
   *        .
   * @param sValue
   *        The receiver identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setReceiver (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    m_sReceiverScheme = ValueEnforcer.notEmpty (sScheme, "Scheme");
    m_sReceiverValue = ValueEnforcer.notEmpty (sValue, "Value");
    return this;
  }

  /**
   * Set the receiver identifier value using the default identifier
   * scheme/authority {@link CIdentifier#DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setReceiverWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setReceiver (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * @return The receiver identifier scheme. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Receiver/Identifier/@Authority</code>
   *         .
   */
  @Nullable
  public String getReceiverScheme ()
  {
    return m_sReceiverScheme;
  }

  /**
   * @return The receiver identifier value. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   */
  @Nullable
  public String getReceiverValue ()
  {
    return m_sReceiverValue;
  }

  /**
   * @return The receiver identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IPeppolParticipantIdentifier getReceiverAsIdentifier ()
  {
    return IdentifierHelper.createParticipantIdentifierOrNull (m_sReceiverScheme, m_sReceiverValue);
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
  @Nonnull
  public PeppolSBDHDocument setDocumentType (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    m_sDocumentTypeScheme = ValueEnforcer.notEmpty (sScheme, "Scheme");
    m_sDocumentTypeValue = ValueEnforcer.notEmpty (sValue, "Value");
    return this;
  }

  /**
   * Set the document type identifier using the default identifier
   * scheme/authority
   * {@link CIdentifier#DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME}.
   *
   * @param sValue
   *        The document type identifier value. May neither be <code>null</code>
   *        nor empty. This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setDocumentTypeWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setDocumentType (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * @return The document type identifier scheme. May be <code>null</code> if
   *         not initialized yet. This field is currently not mapped.
   */
  @Nullable
  public String getDocumentTypeScheme ()
  {
    return m_sDocumentTypeScheme;
  }

  /**
   * @return The document type identifier value. May be <code>null</code> if not
   *         initialized yet. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="DOCUMENTID"]/InstanceIdentifier</code>
   *         .
   */
  @Nullable
  public String getDocumentTypeValue ()
  {
    return m_sDocumentTypeValue;
  }

  /**
   * @return The document type identifier as an object or <code>null</code> if
   *         certain information are missing or are invalid.
   */
  @Nullable
  public IPeppolDocumentTypeIdentifier getDocumentTypeAsIdentifier ()
  {
    return IdentifierHelper.createDocumentTypeIdentifierOrNull (m_sDocumentTypeScheme, m_sDocumentTypeValue);
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
  @Nonnull
  public PeppolSBDHDocument setProcess (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    m_sProcessScheme = ValueEnforcer.notEmpty (sScheme, "Scheme");
    m_sProcessValue = ValueEnforcer.notEmpty (sValue, "Value");
    return this;
  }

  /**
   * Set the document type identifier using the default identifier
   * scheme/authority {@link CIdentifier#DEFAULT_PROCESS_IDENTIFIER_SCHEME}.
   *
   * @param sValue
   *        The process identifier. May neither be <code>null</code> nor empty.
   *        This field is mapped to
   *        <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *        .
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setProcessWithDefaultScheme (@Nonnull @Nonempty final String sValue)
  {
    return setProcess (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME, sValue);
  }

  /**
   * @return The process identifier scheme. May be <code>null</code> if not
   *         initialized yet. This field is currently not mapped.
   */
  @Nullable
  public String getProcessScheme ()
  {
    return m_sProcessScheme;
  }

  /**
   * @return The process identifier value. May be <code>null</code> if not
   *         initialized yet. This field is mapped to
   *         <code>StandardBusinessDocumentHeader/BusinessScope/Scope[Type/text()="PROCESSID"]/InstanceIdentifier</code>
   *         .
   */
  @Nullable
  public String getProcessValue ()
  {
    return m_sProcessValue;
  }

  /**
   * @return The process identifier as an object or <code>null</code> if certain
   *         information are missing or are invalid.
   */
  @Nullable
  public IPeppolProcessIdentifier getProcessAsIdentifier ()
  {
    return IdentifierHelper.createProcessIdentifierOrNull (m_sProcessScheme, m_sProcessValue);
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
   */
  @Nonnull
  public PeppolSBDHDocument setDocumentIdentification (@Nonnull final String sStandard,
                                                 @Nonnull final String sTypeVersion,
                                                 @Nonnull final String sType,
                                                 @Nonnull final String sInstanceIdentifier,
                                                 @Nonnull final LocalDateTime aCreationDateAndTime)
  {
    m_sStandard = ValueEnforcer.notNull (sStandard, "Standard");
    m_sTypeVersion = ValueEnforcer.notNull (sTypeVersion, "TypeVersion");
    m_sType = ValueEnforcer.notNull (sType, "Type");
    m_sInstanceIdentifier = ValueEnforcer.notNull (sInstanceIdentifier, "InstanceIdentifier");
    m_aCreationDateAndTime = ValueEnforcer.notNull (aCreationDateAndTime, "CreationDateAndTime");
    return this;
  }

  /**
   * The standard of the enveloped business message, normally described by use
   * of the XML namespace of the business message root element (such as
   * urn:oasis:names:specification:ubl:schema:xsd:Order-2). This field is mapped
   * to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/Standard</code>
   * .
   *
   * @return The standard value. May be <code>null</code>.
   */
  @Nullable
  public String getStandard ()
  {
    return m_sStandard;
  }

  /**
   * The version number of the enveloped business message (such as the value
   * "2.1" for OASIS UBL 2.1 or "2.0" for OASIS UBL 2.0). This field is mapped
   * to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/TypeVersion</code>
   * .
   *
   * @return The type version. May be <code>null</code>.
   */
  @Nullable
  public String getTypeVersion ()
  {
    return m_sTypeVersion;
  }

  /**
   * Message type - mandatory in SBDH. XML local element name of the
   * root-element in the business message. This field is mapped to
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
   * An informative unique ID created by the issuer of the envelope. The
   * InstanceIdentifier MUST be unique for each Business Message Envelope being
   * created. This ID is not the same as the ID of the business message (such as
   * the Invoice Number). It is not the same as a transmission Message ID
   * generated by the application sending the message (as defined in AS2 or
   * START).<br>
   * The InstanceIdentifier MUST be globally unique and it is RECOMMENDED to use
   * UUID (such as 118e3040-51d2-11e3-8f96-0800200c9a66). This field is mapped
   * to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/InstanceIdentifier</code>
   * .
   *
   * @return The instance identifier. May be <code>null</code>.
   */
  @Nullable
  public String getInstanceIdentifier ()
  {
    return m_sInstanceIdentifier;
  }

  /**
   * The date and time for when this envelope was created. It is NOT necessarily
   * the same as the issue date of the business document (such as the invoice)
   * being enveloped. It is NOT necessarily the date time for transmission.<br>
   * The format of the value of this MUST include timezone information. This
   * field is mapped to
   * <code>StandardBusinessDocumentHeader/DocumentIdentification/CreationDateAndTime</code>
   * .
   *
   * @return The creation date time. May be <code>null</code>.
   */
  @Nullable
  public LocalDateTime getCreationDateAndTime ()
  {
    return m_aCreationDateAndTime;
  }

  /**
   * Set the main business message that should be transmitted together with the
   * SBDH.
   *
   * @param aBusinessMessage
   *        The business message to be set. May not be <code>null</code>.
   *        Internally the passed element is cloned, so that further
   *        modifications outside of this method have no impact on the business
   *        message inside this object.
   * @return this
   */
  @Nonnull
  public PeppolSBDHDocument setBusinessMessage (@Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    // Create a deep copy of the element to avoid outside modifications
    m_aBusinessMessage = (Element) aBusinessMessage.cloneNode (true);
    return this;
  }

  /**
   * Check if a business message is present without having the need to
   * explicitly call {@link #getBusinessMessage()} which returns a cloned node
   * and is therefore an expensive operation.
   *
   * @return <code>true</code> if a business message is present,
   *         <code>false</code> otherwise.
   */
  public boolean hasBusinessMessage ()
  {
    return m_aBusinessMessage != null;
  }

  /**
   * Get the contained business message.
   *
   * @return <code>null</code> if no business message is present. A clone (deep
   *         copy) of the business message otherwise.
   */
  @Nullable
  @ReturnsMutableCopy
  public Element getBusinessMessage ()
  {
    return m_aBusinessMessage == null ? null : (Element) m_aBusinessMessage.cloneNode (true);
  }

  /**
   * @return <code>true</code> if all mandatory fields required for creating an
   *         SBDH are present, <code>false</code> if at least one field is not
   *         set.
   */
  public boolean areAllFieldsSet ()
  {
    return StringHelper.hasText (m_sSenderScheme) &&
           StringHelper.hasText (m_sSenderValue) &&
           StringHelper.hasText (m_sReceiverScheme) &&
           StringHelper.hasText (m_sReceiverValue) &&
           StringHelper.hasText (m_sDocumentTypeScheme) &&
           StringHelper.hasText (m_sDocumentTypeValue) &&
           StringHelper.hasText (m_sProcessScheme) &&
           StringHelper.hasText (m_sProcessValue) &&
           m_sStandard != null &&
           m_sTypeVersion != null &&
           m_sType != null &&
           m_sInstanceIdentifier != null &&
           m_aCreationDateAndTime != null &&
           m_aBusinessMessage != null;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final PeppolSBDHDocument rhs = (PeppolSBDHDocument) o;
    return EqualsHelper.equals (m_sSenderScheme, rhs.m_sSenderScheme) &&
           EqualsHelper.equals (m_sSenderValue, rhs.m_sSenderValue) &&
           EqualsHelper.equals (m_sReceiverScheme, rhs.m_sReceiverScheme) &&
           EqualsHelper.equals (m_sReceiverValue, rhs.m_sReceiverValue) &&
           EqualsHelper.equals (m_sDocumentTypeScheme, rhs.m_sDocumentTypeScheme) &&
           EqualsHelper.equals (m_sDocumentTypeValue, rhs.m_sDocumentTypeValue) &&
           EqualsHelper.equals (m_sProcessScheme, rhs.m_sProcessScheme) &&
           EqualsHelper.equals (m_sProcessValue, rhs.m_sProcessValue) &&
           EqualsHelper.equals (m_sStandard, rhs.m_sStandard) &&
           EqualsHelper.equals (m_sTypeVersion, rhs.m_sTypeVersion) &&
           EqualsHelper.equals (m_sType, rhs.m_sType) &&
           EqualsHelper.equals (m_sInstanceIdentifier, rhs.m_sInstanceIdentifier) &&
           EqualsHelper.equals (m_aCreationDateAndTime, rhs.m_aCreationDateAndTime) &&
           EqualsHelper.equals (m_aBusinessMessage, rhs.m_aBusinessMessage);
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
                                       .append (m_sStandard)
                                       .append (m_sTypeVersion)
                                       .append (m_sType)
                                       .append (m_sInstanceIdentifier)
                                       .append (m_aCreationDateAndTime)
                                       .append (m_aBusinessMessage)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("senderScheme", m_sSenderScheme)
                                       .append ("senderValue", m_sSenderValue)
                                       .append ("receiverScheme", m_sReceiverScheme)
                                       .append ("receiverValue", m_sReceiverValue)
                                       .append ("documentTypeScheme", m_sDocumentTypeScheme)
                                       .append ("documentTypeValue", m_sDocumentTypeValue)
                                       .append ("processScheme", m_sProcessScheme)
                                       .append ("processValue", m_sProcessValue)
                                       .append ("standard", m_sStandard)
                                       .append ("typeVersion", m_sTypeVersion)
                                       .append ("type", m_sType)
                                       .append ("instanceIdentifier", m_sInstanceIdentifier)
                                       .append ("creationDateAndTime", m_aCreationDateAndTime)
                                       .append ("businessMessage", m_aBusinessMessage)
                                       .toString ();
  }

  /**
   * Create a new {@link PeppolSBDHDocument} object for a business message. The
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
   * @return A pre-filled {@link PeppolSBDHDocument} object with some information
   *         still missing.
   * @see #setSender(String, String)
   * @see #setReceiver(String, String)
   * @see #setDocumentType(String, String)
   * @see #setProcess(String, String)
   */
  @Nonnull
  public static PeppolSBDHDocument create (@Nonnull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    final PeppolSBDHDocument ret = new PeppolSBDHDocument ();
    ret.setBusinessMessage (aBusinessMessage);
    // 1. Always use UBL 2.1
    // 2. Use a new UUID as the instance identifier
    // 3. Use the current date time
    ret.setDocumentIdentification (aBusinessMessage.getNamespaceURI (),
                                   CPeppolSBDH.TYPE_VERSION_21,
                                   aBusinessMessage.getLocalName (),
                                   UUID.randomUUID ().toString (),
                                   PDTFactory.getCurrentLocalDateTime ());
    return ret;
  }
}
