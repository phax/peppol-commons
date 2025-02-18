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
package com.helger.peppol.xhe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.w3c.dom.Element;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.mime.CMimeType;
import com.helger.commons.mime.IMimeType;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;

/**
 * This class contains all the DBNAlliance data per Payload instance, such as a
 * single invoice in a syntax neutral way. This class maps to the requirements
 * of the Exchange Header Envelope (XHE) Version 1.0 specification.
 *
 * @author Robinson Garcia
 * @author Philip Helger
 */
@NotThreadSafe
public class DBNAlliancePayload
{
  public static final String DEFAULT_CONTENT_TYPE_CODE_LIST_ID = "MIME";
  public static final boolean DEFAULT_INSTANCE_ENCRYPTION_INDICATOR = false;

  private final IIdentifierFactory m_aIdentifierFactory;

  private String m_sDescription;
  private String m_sContentTypeCodeListID;
  private String m_sContentTypeCode;
  private String m_sCustomizationIDSchemeID;
  private String m_sCustomizationID;
  private String m_sProfileIDSchemeID;
  private String m_sProfileID;
  private boolean m_bInstanceEncryptionIndicator = DEFAULT_INSTANCE_ENCRYPTION_INDICATOR;
  private String m_sInstanceEncryptionMethod;
  private Element m_aPayloadContent;

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public DBNAlliancePayload (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * Description - An OPTIONAL human readable description of the payload. This
   * field is mapped to <code>XHE/Payloads/Payload/Description</code>.
   *
   * @return Description value. May be <code>null</code>.
   */
  @Nullable
  public String getDescription ()
  {
    return m_sDescription;
  }

  /**
   * @return <code>true</code> if a description is present, <code>false</code>
   *         if not.
   */
  public boolean hasDescription ()
  {
    return StringHelper.hasText (m_sDescription);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/Description</code>.
   *
   * @param s
   *        Description - An OPTIONAL human readable description of the payload.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/Description</code> .
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setDescription (@Nullable final String s)
  {
    m_sDescription = s;
    return this;
  }

  /**
   * @return The content type code list id. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code> .
   */
  @Nullable
  public String getContentTypeCodeListID ()
  {
    return m_sContentTypeCodeListID;
  }

  /**
   * @return <code>true</code> if a Content-Type Code list ID is present,
   *         <code>false</code> if not.
   */
  public boolean hasContentTypeCodeListID ()
  {
    return StringHelper.hasText (m_sContentTypeCodeListID);
  }

  /**
   * Set the content type code list ID.
   *
   * @param s
   *        An OPTIONAL aIribute specifying that the ContentTypeCode value is a
   *        MIME Type. When set, this aIribute MUST be set to:
   *        <code>MIME</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setContentTypeCodeListID (@Nullable final String s)
  {
    m_sContentTypeCodeListID = s;
    return this;
  }

  /**
   * @return The content type code value. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   */
  @Nullable
  public String getContentTypeCode ()
  {
    return m_sContentTypeCode;
  }

  /**
   * @return <code>true</code> if a Content-Type Code value is present,
   *         <code>false</code> if not.
   */
  public boolean hasContentTypeCode ()
  {
    return StringHelper.hasText (m_sContentTypeCode);
  }

  /**
   * Set the content type code. The MIME Type of the payload content. For XML
   * payload content the ContentTypeCode MUST be set to:
   * <code>application/xml</code>
   *
   * @param s
   *        The Content-Type code to use. This field is mapped to
   *        <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setContentTypeCode (@Nonnull @Nonempty final String s)
  {
    ValueEnforcer.notEmpty (s, "Value");

    m_sContentTypeCode = s;
    return this;
  }

  @Nonnull
  public DBNAlliancePayload setContentTypeCode (@Nullable final String sCodeListID,
                                                @Nonnull @Nonempty final String sValue)
  {
    return setContentTypeCodeListID (sCodeListID).setContentTypeCode (sValue);
  }

  /**
   * Set the content type code. The MIME Type of the payload content. For XML
   * payload content the ContentTypeCode MUST be set to:
   * <code>application/xml</code>
   *
   * @param a
   *        The Content-Type code to use. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setContentTypeCode (@Nonnull final IMimeType a)
  {
    ValueEnforcer.notNull (a, "Value");
    return setContentTypeCode (a.getAsString ());
  }

  /**
   * Set the content type code to <code>application/xml</code>
   *
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setContentTypeCodeXML ()
  {
    return setContentTypeCode (CMimeType.APPLICATION_XML);
  }

  /**
   * @return The identifier of the scheme used for the CustomizationID if one is
   *         defined. May be <code>null</code> if not initialized. This field is
   *         mapped to
   *         <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   */
  @Nullable
  public String getCustomizationIDSchemeID ()
  {
    return m_sCustomizationIDSchemeID;
  }

  public boolean hasCustomizationIDSchemeID ()
  {
    return StringHelper.hasText (m_sCustomizationIDSchemeID);
  }

  /**
   * Set the customization identifier scheme ID.
   *
   * @param s
   *        The identifier of the scheme used for the CustomizationID if one is
   *        defined. May be <code>null</code> if not initialized. This field is
   *        mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setCustomizationIDSchemeID (@Nullable final String s)
  {
    m_sCustomizationIDSchemeID = s;
    return this;
  }

  /**
   * @return If defined in the business document profile or specification of the
   *         payload, this MUST be set to the Customization ID as specified
   *         therein. May be <code>null</code> if not initialized. This field is
   *         mapped to <code>XHE/Payloads/Payload/CustomizationID/</code>.
   */
  @Nullable
  public String getCustomizationID ()
  {
    return m_sCustomizationID;
  }

  public boolean hasCustomizationID ()
  {
    return StringHelper.hasText (m_sCustomizationID);
  }

  /**
   * Set the customization identifier.
   *
   * @param s
   *        If defined in the business document profile or specification of the
   *        payload, this MUST be set to the Customization ID as specified
   *        therein. Otherwise, MUST NOT be used. May be <code>null</code> if
   *        not initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setCustomizationID (@Nullable final String s)
  {
    m_sCustomizationID = s;
    return this;
  }

  /**
   * @return The customization identifier as a document type identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IDocumentTypeIdentifier getCustomizationIDAsIdentifier ()
  {
    return m_aIdentifierFactory.createDocumentTypeIdentifier (m_sCustomizationIDSchemeID, m_sCustomizationID);
  }

  /**
   * Set the customization identifier.
   *
   * @param a
   *        The document type identifier to use. May be <code>null</code>.
   * @return this for chaining
   * @see #setCustomizationIDSchemeID(String)
   * @see #setCustomizationID(String)
   */
  @Nonnull
  public DBNAlliancePayload setCustomizationID (@Nullable final IDocumentTypeIdentifier a)
  {
    if (a != null)
      return setCustomizationIDSchemeID (a.getScheme ()).setCustomizationID (a.getValue ());
    return this;
  }

  @Nonnull
  public DBNAlliancePayload setCustomizationID (@Nullable final String sSchemeID, @Nullable final String sValue)
  {
    return setCustomizationIDSchemeID (sSchemeID).setCustomizationID (sValue);
  }

  /**
   * @return The identifier of the scheme used for the ProfileID if one is
   *         defined. May be <code>null</code> if not initialized. This field is
   *         mapped to <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   */
  @Nullable
  public String getProfileIDSchemeID ()
  {
    return m_sProfileIDSchemeID;
  }

  public boolean hasProfileIDSchemeID ()
  {
    return StringHelper.hasText (m_sProfileIDSchemeID);
  }

  /**
   * Set the profile identifier scheme ID.
   *
   * @param s
   *        The identifier of the scheme used for the ProfileID if one is
   *        defined. May be <code>null</code> if not initialized. This field is
   *        mapped to <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setProfileIDSchemeID (@Nullable final String s)
  {
    m_sProfileIDSchemeID = s;
    return this;
  }

  /**
   * @return If defined in the business document profile or specification of the
   *         payload, this MUST be set to the Profile ID as specified therein.
   *         Otherwise, MUST NOT be used. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ProfileID/</code>.
   */
  @Nullable
  public String getProfileID ()
  {
    return m_sProfileID;
  }

  public boolean hasProfileID ()
  {
    return StringHelper.hasText (m_sProfileID);
  }

  /**
   * Set the profile identifier.
   *
   * @param s
   *        If defined in the business document profile or specification of the
   *        payload, this MUST be set to the Profile ID as specified therein.
   *        Otherwise, MUST NOT be used. May be <code>null</code> if not
   *        initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/ProfileID/</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setProfileID (@Nullable final String s)
  {
    m_sProfileID = s;
    return this;
  }

  /**
   * @return The profile identifier or <code>null</code> if certain information
   *         are missing or are invalid.
   */
  @Nullable
  public IProcessIdentifier getProfileIDAsIdentifier ()
  {
    return m_aIdentifierFactory.createProcessIdentifier (m_sProfileIDSchemeID, m_sProfileID);
  }

  /**
   * Set the profile identifier.
   *
   * @param aProfileID
   *        The process identifier to use. May be <code>null</code>.
   * @return this
   * @see #setProfileID(String)
   * @see #setProfileIDSchemeID(String)
   */
  @Nonnull
  public DBNAlliancePayload setProfileID (@Nullable final IProcessIdentifier aProfileID)
  {
    if (aProfileID != null)
      return setProfileIDSchemeID (aProfileID.getScheme ()).setProfileID (aProfileID.getValue ());
    return this;
  }

  @Nonnull
  public DBNAlliancePayload setProfileID (@Nullable final String sSchemeID, @Nullable final String sValue)
  {
    return setProfileIDSchemeID (sSchemeID).setProfileID (sValue);
  }

  /**
   * InstanceEncryptionIndicator - Indicator to state whether the payload
   * instance is encrypted or not. This field is mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>.
   *
   * @return InstanceEncryptionIndicator value. Default value is false.
   */
  public boolean isInstanceEncryptionIndicator ()
  {
    return m_bInstanceEncryptionIndicator;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>.
   *
   * @param b
   *        Indicator to state whether the payload instance is encrypted or not.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code> .
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setInstanceEncryptionIndicator (final boolean b)
  {
    m_bInstanceEncryptionIndicator = b;
    return this;
  }

  /**
   * The method or algorithm used for encrypting payload content. When
   * encryption is used, payloads MUST be encrypted using one of the supported
   * encryption methods and algorithms as specified in section 7.3 and the value
   * of this element MUST be set to the corresponding identifier. This field is
   * mapped to <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @return InstanceEncryptionMethod value. Default value is
   *         <code>false</code>.
   */
  @Nullable
  public String getInstanceEncryptionMethod ()
  {
    return m_sInstanceEncryptionMethod;
  }

  /**
   * @return <code>true</code> if a InstanceEncryptionMethod is present,
   *         <code>false</code> if not.
   */
  public boolean hasInstanceEncryptionMethod ()
  {
    return StringHelper.hasText (m_sInstanceEncryptionMethod);
  }

  /**
   * Set the method or algorithm used for encrypting payload content. When
   * encryption is used, payloads MUST be encrypted using one of the supported
   * encryption methods and algorithms as specified in section 7.3 and the value
   * of this element MUST be set to the corresponding identifier. The content of
   * the fields that are mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @param s
   *        Method used to encrypt the payload instance. This field is mapped to
   *        <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code> .
   * @return this for chaining
   */
  @Nonnull
  public DBNAlliancePayload setInstanceEncryptionMethod (@Nullable final String s)
  {
    m_sInstanceEncryptionMethod = s;
    return this;
  }

  /**
   * Get the contained payload content.
   *
   * @return <code>null</code> if no payload content is present. A clone (deep
   *         copy) of the payload content otherwise.
   * @see #getPayloadContentNoClone()
   */
  @Nullable
  @ReturnsMutableCopy
  public Element getPayloadContent ()
  {
    return m_aPayloadContent == null ? null : (Element) m_aPayloadContent.cloneNode (true);
  }

  /**
   * Get the contained payload content without cloning it.
   *
   * @return <code>null</code> if no payload content is present.
   * @see #getPayloadContent()
   */
  @Nullable
  @ReturnsMutableObject
  public Element getPayloadContentNoClone ()
  {
    return m_aPayloadContent;
  }

  /**
   * Check if a payload content is present without having the need to explicitly
   * call {@link #getPayloadContent()} which returns a cloned node and is
   * therefore an expensive operation.
   *
   * @return <code>true</code> if a payload content is present,
   *         <code>false</code> otherwise.
   */
  public boolean hasPayloadContent ()
  {
    return m_aPayloadContent != null;
  }

  /**
   * Set the main payload content that should be transmitted together with the
   * XHE. The DOM element is cloned internally to avoid outside modification
   *
   * @param aPayloadContent
   *        The payload content to be set. May not be <code>null</code>.
   *        Internally the passed element is cloned, so that further
   *        modifications outside of this method have no impact on the XHE
   *        inside this object.
   * @return this
   * @see #setPayloadContentNoClone(Element)
   */
  @Nonnull
  public DBNAlliancePayload setPayloadContent (@Nonnull final Element aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    // Create a deep copy of the element to avoid outside modifications
    m_aPayloadContent = (Element) aPayloadContent.cloneNode (true);
    return this;
  }

  /**
   * Set the main payload content that should be transmitted together with the
   * XHE. The DOM element is not cloned / copied internally.
   *
   * @param aPayloadContent
   *        The payload content to be set. May not be <code>null</code>.
   *        Internally the passed element is cloned, so that further
   *        modifications outside of this method have no impact on the XHE
   *        inside this object.
   * @return this
   * @see #setPayloadContent(Element)
   */
  @Nonnull
  public DBNAlliancePayload setPayloadContentNoClone (@Nonnull final Element aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    m_aPayloadContent = aPayloadContent;
    return this;
  }

  public boolean areAllMandatoryFieldsSet ()
  {
    return hasContentTypeCode () && hasPayloadContent ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Description", m_sDescription)
                                       .append ("ContentTypeCodeListID", m_sContentTypeCodeListID)
                                       .append ("ContentTypeCode", m_sContentTypeCode)
                                       .append ("CustomizationIDSchemeID", m_sCustomizationIDSchemeID)
                                       .append ("CustomizationID", m_sCustomizationID)
                                       .append ("ProfileIDSchemeID", m_sProfileIDSchemeID)
                                       .append ("ProfileID", m_sProfileID)
                                       .append ("InstanceEncryptionIndicator", m_bInstanceEncryptionIndicator)
                                       .append ("InstanceEncryptionMethod", m_sInstanceEncryptionMethod)
                                       .append ("PayloadContent", m_aPayloadContent)
                                       .getToString ();
  }
}
