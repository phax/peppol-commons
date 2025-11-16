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

import java.util.UUID;
import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
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
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.xml.XMLOffsetDateTime;
import com.helger.jaxb.adapter.JAXBHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * This class contains all the HR eDelivery data per SBDH document in a syntax neutral way.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class HREDeliverySBDHData
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliverySBDHData.class);

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
  public HREDeliverySBDHData (@NonNull final IIdentifierFactory aIdentifierFactory)
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
  @NonNull
  public HREDeliverySBDHData setSender (@NonNull @Nonempty final String sScheme, @NonNull @Nonempty final String sValue)
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
   */
  @NonNull
  public HREDeliverySBDHData setSender (@NonNull final IParticipantIdentifier aSenderID)
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
  @NonNull
  public HREDeliverySBDHData setSenderWithDefaultScheme (@NonNull @Nonempty final String sValue)
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
  @NonNull
  public HREDeliverySBDHData setReceiver (@NonNull @Nonempty final String sScheme,
                                          @NonNull @Nonempty final String sValue)
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
   */
  @NonNull
  public HREDeliverySBDHData setReceiver (@NonNull final IParticipantIdentifier aReceiverID)
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
  @NonNull
  public HREDeliverySBDHData setReceiverWithDefaultScheme (@NonNull @Nonempty final String sValue)
  {
    return setReceiver (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
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
  public HREDeliverySBDHData setDocumentIdentification (@NonNull @Nonempty final String sStandard,
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
  public HREDeliverySBDHData setStandard (@NonNull @Nonempty final String sStandard)
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
  public HREDeliverySBDHData setTypeVersion (@NonNull @Nonempty final String sTypeVersion)
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
  public HREDeliverySBDHData setType (@NonNull @Nonempty final String sType)
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
  public HREDeliverySBDHData setInstanceIdentifier (@NonNull @Nonempty final String sInstanceIdentifier)
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
  public HREDeliverySBDHData setCreationDateAndTime (@NonNull final XMLOffsetDateTime aCreationDateAndTime)
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
  public HREDeliverySBDHData setBusinessMessage (@NonNull final Element aBusinessMessage)
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
   */
  @NonNull
  public HREDeliverySBDHData setBusinessMessageNoClone (@NonNull final Element aBusinessMessage)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    m_aBusinessMessage = aBusinessMessage;
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
   */
  public boolean areAllFieldsSet (@NonNull final Consumer <String> aMissingFieldConsumer)
  {
    ValueEnforcer.notNull (aMissingFieldConsumer, "MissingFieldConsumer");

    int nMissing = 0;
    if (StringHelper.isEmpty (m_sSenderScheme))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Sender Scheme is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (m_sSenderValue))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Sender Value is missing");
      nMissing++;
    }

    if (StringHelper.isEmpty (m_sReceiverScheme))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Receiver Scheme is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (m_sReceiverValue))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Reeiver Value is missing");
      nMissing++;
    }

    if (StringHelper.isEmpty (m_sStandard))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Standard is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (m_sTypeVersion))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Type Version is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (m_sType))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Type is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (m_sInstanceIdentifier))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Instance Identifier is missing");
      nMissing++;
    }
    if (m_aCreationDateAndTime == null)
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Creation Date and Time is missing");
      nMissing++;
    }
    if (m_aBusinessMessage == null)
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Business Message is missing");
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
   * @return A generic JAXB SBD document of this data. Never <code>null</code>.
   * @see HREDeliverySBDHDataWriter for the main logic
   */
  @NonNull
  public StandardBusinessDocument getAsStandardBusinessDocument ()
  {
    return new HREDeliverySBDHDataWriter ().createStandardBusinessDocument (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final HREDeliverySBDHData rhs = (HREDeliverySBDHData) o;
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

  /**
   * Create a new {@link HREDeliverySBDHData} object for a business message assuming it is UBL 2.1.
   * The resulting object has all required fields set, except for:
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
   * @return A pre-filled {@link HREDeliverySBDHData} object with some information still missing.
   * @see #setSender(String, String)
   * @see #setReceiver(String, String)
   */
  @NonNull
  public static HREDeliverySBDHData createUBL21 (@NonNull final Element aBusinessMessage,
                                                 @NonNull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    final HREDeliverySBDHData ret = new HREDeliverySBDHData (aIdentifierFactory);
    ret.setBusinessMessage (aBusinessMessage);
    // 1. Always use UBL 2.1
    // 2. Use a new UUID as the instance identifier
    // 3. Use the current date time
    ret.setDocumentIdentification (aBusinessMessage.getNamespaceURI (),
                                   CHREDeliverySBDH.TYPE_VERSION_21,
                                   aBusinessMessage.getLocalName (),
                                   UUID.randomUUID ().toString (),
                                   PDTFactory.getCurrentXMLOffsetDateTimeMillisOnly ());
    return ret;
  }
}
