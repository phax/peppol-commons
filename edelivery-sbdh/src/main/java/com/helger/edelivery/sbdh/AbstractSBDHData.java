/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.edelivery.sbdh;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.w3c.dom.Element;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.trait.IGenericImplTrait;
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.xml.XMLOffsetDateTime;
import com.helger.jaxb.adapter.JAXBHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * Base class for the data of a Standard Business Document Header in a syntax neutral way. It
 * contains the parts that are independent of the concrete network - sender, receiver, document
 * identification and the business message. Everything that is specific to a single network, like
 * the business scope, is added by the derived classes.
 *
 * @author Philip Helger
 * @param <IMPLTYPE>
 *        The implementation type
 * @since 13.0.0
 */
@NotThreadSafe
public abstract class AbstractSBDHData <IMPLTYPE extends AbstractSBDHData <IMPLTYPE>> implements
                                       IGenericImplTrait <IMPLTYPE>
{
  private final IIdentifierFactory m_aIdentifierFactory;
  // Sender
  private String m_sSenderScheme;
  private String m_sSenderValue;
  // Receiver
  private String m_sReceiverScheme;
  private String m_sReceiverValue;
  // DocumentIdentification
  private String m_sStandard;
  private String m_sTypeVersion;
  private String m_sType;
  private String m_sInstanceIdentifier;
  private XMLOffsetDateTime m_aCreationDateAndTime;
  // BusinessMessage
  private Element m_aBusinessMessage;

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  protected AbstractSBDHData (@NonNull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * @return The identifier factory as provided in the constructor. Never <code>null</code>.
   */
  @NonNull
  public final IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIdentifierFactory;
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
  @NonNull
  public IMPLTYPE setSender (@NonNull @Nonempty final String sScheme, @NonNull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sSenderScheme = sScheme;
    m_sSenderValue = sValue;
    return thisAsT ();
  }

  /**
   * Set the sender participant identifier.
   *
   * @param aSenderID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   */
  @NonNull
  public IMPLTYPE setSender (@NonNull final IParticipantIdentifier aSenderID)
  {
    ValueEnforcer.notNull (aSenderID, "SenderID");

    return setSender (aSenderID.getScheme (), aSenderID.getValue ());
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
  @NonNull
  public IMPLTYPE setReceiver (@NonNull @Nonempty final String sScheme, @NonNull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sReceiverScheme = sScheme;
    m_sReceiverValue = sValue;
    return thisAsT ();
  }

  /**
   * Set the receiver participant identifier.
   *
   * @param aReceiverID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   */
  @NonNull
  public IMPLTYPE setReceiver (@NonNull final IParticipantIdentifier aReceiverID)
  {
    ValueEnforcer.notNull (aReceiverID, "ReceiverID");

    return setReceiver (aReceiverID.getScheme (), aReceiverID.getValue ());
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
  @NonNull
  public IMPLTYPE setDocumentIdentification (@NonNull @Nonempty final String sStandard,
                                             @NonNull @Nonempty final String sTypeVersion,
                                             @NonNull @Nonempty final String sType,
                                             @NonNull @Nonempty final String sInstanceIdentifier,
                                             @NonNull final XMLOffsetDateTime aCreationDateAndTime)
  {
    setStandard (sStandard);
    setTypeVersion (sTypeVersion);
    setType (sType);
    setInstanceIdentifier (sInstanceIdentifier);
    setCreationDateAndTime (aCreationDateAndTime);
    return thisAsT ();
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
   */
  public boolean hasStandard ()
  {
    return StringHelper.isNotEmpty (m_sStandard);
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
   */
  @NonNull
  public IMPLTYPE setStandard (@NonNull @Nonempty final String sStandard)
  {
    ValueEnforcer.notEmpty (sStandard, "Standard");

    m_sStandard = sStandard;
    return thisAsT ();
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
   */
  public boolean hasTypeVersion ()
  {
    return StringHelper.isNotEmpty (m_sTypeVersion);
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
   */
  @NonNull
  public IMPLTYPE setTypeVersion (@NonNull @Nonempty final String sTypeVersion)
  {
    ValueEnforcer.notEmpty (sTypeVersion, "TypeVersion");

    m_sTypeVersion = sTypeVersion;
    return thisAsT ();
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
   */
  public boolean hasType ()
  {
    return StringHelper.isNotEmpty (m_sType);
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
   */
  @NonNull
  public IMPLTYPE setType (@NonNull @Nonempty final String sType)
  {
    ValueEnforcer.notEmpty (sType, "Type");

    m_sType = sType;
    return thisAsT ();
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
   */
  public boolean hasInstanceIdentifier ()
  {
    return StringHelper.isNotEmpty (m_sInstanceIdentifier);
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
   */
  @NonNull
  public IMPLTYPE setInstanceIdentifier (@NonNull @Nonempty final String sInstanceIdentifier)
  {
    ValueEnforcer.notEmpty (sInstanceIdentifier, "InstanceIdentifier");

    m_sInstanceIdentifier = sInstanceIdentifier;
    return thisAsT ();
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
   */
  @NonNull
  public IMPLTYPE setCreationDateAndTime (@NonNull final XMLOffsetDateTime aCreationDateAndTime)
  {
    ValueEnforcer.notNull (aCreationDateAndTime, "CreationDateAndTime");

    // Make sure to use only milliseconds for XML usage
    m_aCreationDateAndTime = PDTFactory.getWithMillisOnly (aCreationDateAndTime);
    return thisAsT ();
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
  @NonNull
  public IMPLTYPE setBusinessMessage (@NonNull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    // Create a deep copy of the element to avoid outside modifications
    m_aBusinessMessage = (Element) aBusinessMessage.cloneNode (true);
    return thisAsT ();
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
   */
  @NonNull
  public IMPLTYPE setBusinessMessageNoClone (@NonNull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    m_aBusinessMessage = aBusinessMessage;
    return thisAsT ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final AbstractSBDHData <?> rhs = (AbstractSBDHData <?>) o;
    return EqualsHelper.equals (m_sSenderScheme, rhs.m_sSenderScheme) &&
           EqualsHelper.equals (m_sSenderValue, rhs.m_sSenderValue) &&
           EqualsHelper.equals (m_sReceiverScheme, rhs.m_sReceiverScheme) &&
           EqualsHelper.equals (m_sReceiverValue, rhs.m_sReceiverValue) &&
           EqualsHelper.equals (m_sStandard, rhs.m_sStandard) &&
           EqualsHelper.equals (m_sTypeVersion, rhs.m_sTypeVersion) &&
           EqualsHelper.equals (m_sType, rhs.m_sType) &&
           EqualsHelper.equals (m_sInstanceIdentifier, rhs.m_sInstanceIdentifier) &&
           EqualsHelper.equals (m_aCreationDateAndTime, rhs.m_aCreationDateAndTime) &&
           JAXBHelper.equalDOMNodes (m_aBusinessMessage, rhs.m_aBusinessMessage);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sSenderScheme)
                                       .append (m_sSenderValue)
                                       .append (m_sReceiverScheme)
                                       .append (m_sReceiverValue)
                                       .append (m_sStandard)
                                       .append (m_sTypeVersion)
                                       .append (m_sType)
                                       .append (m_sInstanceIdentifier)
                                       .append (m_aCreationDateAndTime)
                                       .append (JAXBHelper.getHashCode (m_aBusinessMessage))
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SenderScheme", m_sSenderScheme)
                                       .append ("SenderValue", m_sSenderValue)
                                       .append ("ReceiverScheme", m_sReceiverScheme)
                                       .append ("ReceiverValue", m_sReceiverValue)
                                       .append ("Standard", m_sStandard)
                                       .append ("TypeVersion", m_sTypeVersion)
                                       .append ("Type", m_sType)
                                       .append ("InstanceIdentifier", m_sInstanceIdentifier)
                                       .append ("CreationDateAndTime", m_aCreationDateAndTime)
                                       .append ("BusinessMessage", m_aBusinessMessage)
                                       .getToString ();
  }
}
