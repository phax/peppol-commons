/*
 * Copyright (C) 2024-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.w3c.dom.Element;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.array.ArrayHelper;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.mime.CMimeType;
import com.helger.mime.IMimeType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;

/**
 * This class contains all the DBNAlliance data per Payload instance, such as a single invoice in a
 * syntax neutral way. This class maps to the requirements of the Exchange Header Envelope (XHE)
 * Version 1.0 specification.
 * <p>
 * Per the XHE Envelope Profile v1.0 (section 6 - Data Model), the
 * <code>XHE/Payloads/Payload/PayloadContent</code> element may carry one of three different kinds
 * of content:
 * </p>
 * <ul>
 * <li>An XML payload - a single apex element. Content type code MUST be
 * <code>application/xml</code>.</li>
 * <li>A textual payload - encoded according to XML text encoding rules (special markup characters
 * escaped). Content type code MUST be an IANA registered MIME type.</li>
 * <li>A binary payload - the raw bytes are Base64 encoded. Content type code MUST be an IANA
 * registered MIME type.</li>
 * </ul>
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
  // Only one of these three is set at any point in time
  private Element m_aPayloadContentElement;
  private String m_sPayloadContentText;
  private byte [] m_aPayloadContentBinary;

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public DBNAlliancePayload (@NonNull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * Description - An OPTIONAL human readable description of the payload. This field is mapped to
   * <code>XHE/Payloads/Payload/Description</code>.
   *
   * @return Description value. May be <code>null</code>.
   */
  @Nullable
  public String getDescription ()
  {
    return m_sDescription;
  }

  /**
   * @return <code>true</code> if a description is present, <code>false</code> if not.
   */
  public boolean hasDescription ()
  {
    return StringHelper.isNotEmpty (m_sDescription);
  }

  /**
   * Set the content of the fields that are mapped to <code>XHE/Payloads/Payload/Description</code>.
   *
   * @param s
   *        Description - An OPTIONAL human readable description of the payload. This field is
   *        mapped to <code>XHE/Payloads/Payload/Description</code> .
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setDescription (@Nullable final String s)
  {
    m_sDescription = s;
    return this;
  }

  /**
   * @return The content type code list id. May be <code>null</code> if not initialized. This field
   *         is mapped to <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code> .
   */
  @Nullable
  public String getContentTypeCodeListID ()
  {
    return m_sContentTypeCodeListID;
  }

  /**
   * @return <code>true</code> if a Content-Type Code list ID is present, <code>false</code> if not.
   */
  public boolean hasContentTypeCodeListID ()
  {
    return StringHelper.isNotEmpty (m_sContentTypeCodeListID);
  }

  /**
   * Set the content type code list ID.
   *
   * @param s
   *        An OPTIONAL aIribute specifying that the ContentTypeCode value is a MIME Type. When set,
   *        this aIribute MUST be set to: <code>MIME</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setContentTypeCodeListID (@Nullable final String s)
  {
    m_sContentTypeCodeListID = s;
    return this;
  }

  /**
   * @return The content type code value. May be <code>null</code> if not initialized. This field is
   *         mapped to <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   */
  @Nullable
  public String getContentTypeCode ()
  {
    return m_sContentTypeCode;
  }

  /**
   * @return <code>true</code> if a Content-Type Code value is present, <code>false</code> if not.
   */
  public boolean hasContentTypeCode ()
  {
    return StringHelper.isNotEmpty (m_sContentTypeCode);
  }

  /**
   * Set the content type code. The MIME Type of the payload content. For XML payload content the
   * ContentTypeCode MUST be set to: <code>application/xml</code>. For all other payload content
   * types the ContentTypeCode MUST be set to an IANA registered MIME Type.
   *
   * @param s
   *        The Content-Type code to use. This field is mapped to
   *        <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setContentTypeCode (@NonNull @Nonempty final String s)
  {
    ValueEnforcer.notEmpty (s, "Value");

    m_sContentTypeCode = s;
    return this;
  }

  @NonNull
  public DBNAlliancePayload setContentTypeCode (@Nullable final String sCodeListID,
                                                @NonNull @Nonempty final String sValue)
  {
    return setContentTypeCodeListID (sCodeListID).setContentTypeCode (sValue);
  }

  /**
   * Set the content type code. The MIME Type of the payload content. For XML payload content the
   * ContentTypeCode MUST be set to: <code>application/xml</code>. For all other payload content
   * types the ContentTypeCode MUST be set to an IANA registered MIME Type.
   *
   * @param a
   *        The Content-Type code to use. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setContentTypeCode (@NonNull final IMimeType a)
  {
    ValueEnforcer.notNull (a, "Value");
    return setContentTypeCode (a.getAsString ());
  }

  /**
   * Set the content type code to <code>application/xml</code>
   *
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setContentTypeCodeXML ()
  {
    return setContentTypeCode (CMimeType.APPLICATION_XML);
  }

  /**
   * @return The identifier of the scheme used for the CustomizationID if one is defined. May be
   *         <code>null</code> if not initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   */
  @Nullable
  public String getCustomizationIDSchemeID ()
  {
    return m_sCustomizationIDSchemeID;
  }

  public boolean hasCustomizationIDSchemeID ()
  {
    return StringHelper.isNotEmpty (m_sCustomizationIDSchemeID);
  }

  /**
   * Set the customization identifier scheme ID.
   *
   * @param s
   *        The identifier of the scheme used for the CustomizationID if one is defined. May be
   *        <code>null</code> if not initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setCustomizationIDSchemeID (@Nullable final String s)
  {
    m_sCustomizationIDSchemeID = s;
    return this;
  }

  /**
   * @return If defined in the business document profile or specification of the payload, this MUST
   *         be set to the Customization ID as specified therein. May be <code>null</code> if not
   *         initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/CustomizationID/</code>.
   */
  @Nullable
  public String getCustomizationID ()
  {
    return m_sCustomizationID;
  }

  public boolean hasCustomizationID ()
  {
    return StringHelper.isNotEmpty (m_sCustomizationID);
  }

  /**
   * Set the customization identifier.
   *
   * @param s
   *        If defined in the business document profile or specification of the payload, this MUST
   *        be set to the Customization ID as specified therein. Otherwise, MUST NOT be used. May be
   *        <code>null</code> if not initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setCustomizationID (@Nullable final String s)
  {
    m_sCustomizationID = s;
    return this;
  }

  /**
   * @return The customization identifier as a document type identifier or <code>null</code> if
   *         certain information are missing or are invalid.
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
  @NonNull
  public DBNAlliancePayload setCustomizationID (@Nullable final IDocumentTypeIdentifier a)
  {
    if (a != null)
      return setCustomizationIDSchemeID (a.getScheme ()).setCustomizationID (a.getValue ());
    return this;
  }

  @NonNull
  public DBNAlliancePayload setCustomizationID (@Nullable final String sSchemeID, @Nullable final String sValue)
  {
    return setCustomizationIDSchemeID (sSchemeID).setCustomizationID (sValue);
  }

  /**
   * @return The identifier of the scheme used for the ProfileID if one is defined. May be
   *         <code>null</code> if not initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   */
  @Nullable
  public String getProfileIDSchemeID ()
  {
    return m_sProfileIDSchemeID;
  }

  public boolean hasProfileIDSchemeID ()
  {
    return StringHelper.isNotEmpty (m_sProfileIDSchemeID);
  }

  /**
   * Set the profile identifier scheme ID.
   *
   * @param s
   *        The identifier of the scheme used for the ProfileID if one is defined. May be
   *        <code>null</code> if not initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setProfileIDSchemeID (@Nullable final String s)
  {
    m_sProfileIDSchemeID = s;
    return this;
  }

  /**
   * @return If defined in the business document profile or specification of the payload, this MUST
   *         be set to the Profile ID as specified therein. Otherwise, MUST NOT be used. May be
   *         <code>null</code> if not initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ProfileID/</code>.
   */
  @Nullable
  public String getProfileID ()
  {
    return m_sProfileID;
  }

  public boolean hasProfileID ()
  {
    return StringHelper.isNotEmpty (m_sProfileID);
  }

  /**
   * Set the profile identifier.
   *
   * @param s
   *        If defined in the business document profile or specification of the payload, this MUST
   *        be set to the Profile ID as specified therein. Otherwise, MUST NOT be used. May be
   *        <code>null</code> if not initialized. This field is mapped to
   *        <code>XHE/Payloads/Payload/ProfileID/</code>.
   * @return this
   */
  @NonNull
  public DBNAlliancePayload setProfileID (@Nullable final String s)
  {
    m_sProfileID = s;
    return this;
  }

  /**
   * @return The profile identifier or <code>null</code> if certain information are missing or are
   *         invalid.
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
  @NonNull
  public DBNAlliancePayload setProfileID (@Nullable final IProcessIdentifier aProfileID)
  {
    if (aProfileID != null)
      return setProfileIDSchemeID (aProfileID.getScheme ()).setProfileID (aProfileID.getValue ());
    return this;
  }

  @NonNull
  public DBNAlliancePayload setProfileID (@Nullable final String sSchemeID, @Nullable final String sValue)
  {
    return setProfileIDSchemeID (sSchemeID).setProfileID (sValue);
  }

  /**
   * InstanceEncryptionIndicator - Indicator to state whether the payload instance is encrypted or
   * not. This field is mapped to <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>.
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
   *        Indicator to state whether the payload instance is encrypted or not. This field is
   *        mapped to <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code> .
   * @return this
   */
  @NonNull
  public DBNAlliancePayload setInstanceEncryptionIndicator (final boolean b)
  {
    m_bInstanceEncryptionIndicator = b;
    return this;
  }

  /**
   * The method or algorithm used for encrypting payload content. When encryption is used, payloads
   * MUST be encrypted using one of the supported encryption methods and algorithms as specified in
   * section 7.3 and the value of this element MUST be set to the corresponding identifier. This
   * field is mapped to <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @return InstanceEncryptionMethod value. Default value is <code>false</code>.
   */
  @Nullable
  public String getInstanceEncryptionMethod ()
  {
    return m_sInstanceEncryptionMethod;
  }

  /**
   * @return <code>true</code> if a InstanceEncryptionMethod is present, <code>false</code> if not.
   */
  public boolean hasInstanceEncryptionMethod ()
  {
    return StringHelper.isNotEmpty (m_sInstanceEncryptionMethod);
  }

  /**
   * Set the method or algorithm used for encrypting payload content. When encryption is used,
   * payloads MUST be encrypted using one of the supported encryption methods and algorithms as
   * specified in section 7.3 and the value of this element MUST be set to the corresponding
   * identifier. The content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @param s
   *        Method used to encrypt the payload instance. This field is mapped to
   *        <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code> .
   * @return this for chaining
   */
  @NonNull
  public DBNAlliancePayload setInstanceEncryptionMethod (@Nullable final String s)
  {
    m_sInstanceEncryptionMethod = s;
    return this;
  }

  /**
   * Get the contained XML payload content.
   *
   * @return <code>null</code> if no XML payload content is present (i.e. either nothing is set, or
   *         a textual or binary payload is set instead). A clone (deep copy) of the payload content
   *         otherwise.
   * @see #getPayloadContentNoClone()
   * @see #hasPayloadContentXML()
   */
  @Nullable
  @ReturnsMutableCopy
  public Element getPayloadContent ()
  {
    return m_aPayloadContentElement == null ? null : (Element) m_aPayloadContentElement.cloneNode (true);
  }

  /**
   * Get the contained XML payload content without cloning it.
   *
   * @return <code>null</code> if no XML payload content is present.
   * @see #getPayloadContent()
   */
  @Nullable
  @ReturnsMutableObject
  public Element getPayloadContentNoClone ()
  {
    return m_aPayloadContentElement;
  }

  /**
   * @return The textual payload content (already in its final form, i.e. ready to be inserted as
   *         element text content). May be <code>null</code> if no textual payload content is
   *         present.
   * @see #hasPayloadContentText()
   * @since 12.5.1
   */
  @Nullable
  public String getPayloadContentText ()
  {
    return m_sPayloadContentText;
  }

  /**
   * Get the contained binary payload content. The bytes returned here are the raw, decoded bytes
   * (i.e. before Base64 encoding for transport).
   *
   * @return <code>null</code> if no binary payload content is present. A copy of the binary content
   *         otherwise.
   * @see #getPayloadContentBinaryNoClone()
   * @see #hasPayloadContentBinary()
   * @since 12.5.1
   */
  @Nullable
  @ReturnsMutableCopy
  public byte [] getPayloadContentBinary ()
  {
    return ArrayHelper.getCopy (m_aPayloadContentBinary);
  }

  /**
   * Get the contained binary payload content without cloning the array. The bytes returned here are
   * the raw, decoded bytes (i.e. before Base64 encoding for transport).
   *
   * @return <code>null</code> if no binary payload content is present.
   * @see #getPayloadContentBinary()
   * @since 12.5.1
   */
  @Nullable
  @ReturnsMutableObject
  public byte [] getPayloadContentBinaryNoClone ()
  {
    return m_aPayloadContentBinary;
  }

  /**
   * Check if an XML payload content is present.
   *
   * @return <code>true</code> if an XML payload content is present, <code>false</code> otherwise.
   * @since 12.5.1
   */
  public boolean hasPayloadContentXML ()
  {
    return m_aPayloadContentElement != null;
  }

  /**
   * Check if a textual payload content is present.
   *
   * @return <code>true</code> if a textual payload content is present, <code>false</code>
   *         otherwise.
   * @since 12.5.1
   */
  public boolean hasPayloadContentText ()
  {
    return m_sPayloadContentText != null;
  }

  /**
   * Check if a binary payload content is present.
   *
   * @return <code>true</code> if a binary payload content is present, <code>false</code> otherwise.
   * @since 12.5.1
   */
  public boolean hasPayloadContentBinary ()
  {
    return m_aPayloadContentBinary != null;
  }

  /**
   * Check if any payload content (XML, textual or binary) is present without having the need to
   * explicitly call {@link #getPayloadContent()} which returns a cloned node and is therefore an
   * expensive operation.
   *
   * @return <code>true</code> if any payload content is present, <code>false</code> otherwise.
   */
  public boolean hasPayloadContent ()
  {
    return hasPayloadContentXML () || hasPayloadContentText () || hasPayloadContentBinary ();
  }

  /**
   * Set the main XML payload content that should be transmitted together with the XHE. The DOM
   * element is cloned internally to avoid outside modification. Any previously set textual or
   * binary payload content is cleared.
   *
   * @param aPayloadContent
   *        The payload content to be set. May not be <code>null</code>. Internally the passed
   *        element is cloned, so that further modifications outside of this method have no impact
   *        on the XHE inside this object.
   * @return this
   * @see #setPayloadContentNoClone(Element)
   */
  @NonNull
  public DBNAlliancePayload setPayloadContent (@NonNull final Element aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    // Create a deep copy of the element to avoid outside modifications
    m_aPayloadContentElement = (Element) aPayloadContent.cloneNode (true);
    m_sPayloadContentText = null;
    m_aPayloadContentBinary = null;
    return this;
  }

  /**
   * Set the main XML payload content that should be transmitted together with the XHE. The DOM
   * element is not cloned / copied internally. Any previously set textual or binary payload content
   * is cleared.
   *
   * @param aPayloadContent
   *        The payload content to be set. May not be <code>null</code>.
   * @return this
   * @see #setPayloadContent(Element)
   */
  @NonNull
  public DBNAlliancePayload setPayloadContentNoClone (@NonNull final Element aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    m_aPayloadContentElement = aPayloadContent;
    m_sPayloadContentText = null;
    m_aPayloadContentBinary = null;
    return this;
  }

  /**
   * Set the textual payload content that should be transmitted together with the XHE. The string is
   * inserted as XML text content; the XML serializer takes care of escaping any special markup
   * characters (per XHE Envelope Profile v1.0 section 6). Any previously set XML or binary payload
   * content is cleared.
   *
   * @param sPayloadContent
   *        The textual payload content to be set. May not be <code>null</code>.
   * @return this
   * @since 12.5.1
   */
  @NonNull
  public DBNAlliancePayload setPayloadContentText (@NonNull final String sPayloadContent)
  {
    ValueEnforcer.notNull (sPayloadContent, "PayloadContent");

    m_aPayloadContentElement = null;
    m_sPayloadContentText = sPayloadContent;
    m_aPayloadContentBinary = null;
    return this;
  }

  /**
   * Set the binary payload content that should be transmitted together with the XHE. On
   * serialization, the bytes will be Base64 encoded as required by the XHE Envelope Profile v1.0
   * (section 6). The array is internally cloned to avoid outside modification. Any previously set
   * XML or textual payload content is cleared.
   *
   * @param aPayloadContent
   *        The binary payload content to be set. May not be <code>null</code>.
   * @return this
   * @see #setPayloadContentBinaryNoClone(byte[])
   * @since 12.5.1
   */
  @NonNull
  public DBNAlliancePayload setPayloadContentBinary (final byte @NonNull [] aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    m_aPayloadContentElement = null;
    m_sPayloadContentText = null;
    m_aPayloadContentBinary = ArrayHelper.getCopy (aPayloadContent);
    return this;
  }

  /**
   * Set the binary payload content that should be transmitted together with the XHE. The array is
   * not cloned / copied internally. Any previously set XML or textual payload content is cleared.
   *
   * @param aPayloadContent
   *        The binary payload content to be set. May not be <code>null</code>.
   * @return this
   * @see #setPayloadContentBinary(byte[])
   * @since 12.5.1
   */
  @NonNull
  public DBNAlliancePayload setPayloadContentBinaryNoClone (final byte @NonNull [] aPayloadContent)
  {
    ValueEnforcer.notNull (aPayloadContent, "PayloadContent");

    m_aPayloadContentElement = null;
    m_sPayloadContentText = null;
    m_aPayloadContentBinary = aPayloadContent;
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
                                       .appendIfNotNull ("PayloadContentElement", m_aPayloadContentElement)
                                       .appendIfNotNull ("PayloadContentText", m_sPayloadContentText)
                                       .appendIfNotNull ("PayloadContentBinary", m_aPayloadContentBinary)
                                       .getToString ();
  }
}
