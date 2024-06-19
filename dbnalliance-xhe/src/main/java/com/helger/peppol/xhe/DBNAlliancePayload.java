/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.helger.peppol.xhe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import org.w3c.dom.Element;

/**
 *
 * @author robinsongarcia
 */
@NotThreadSafe
public class DBNAlliancePayload {
  
  private final IIdentifierFactory m_aIdentifierFactory;
  
  private String m_sDescription;
  private String m_sContentTypeCodeListID;
  private String m_sContentTypeCodeValue;
  private String m_sCustomizationIDScheme;
  private String m_sCustomizationIDValue;
  private String m_sProfileIDScheme;
  private String m_sProfileIDValue;
  private boolean m_bInstanceEncryptionIndicator = false;
  private String m_sInstanceEncryptionMethod;
  // PayloadContent
  private Element m_aPayloadContent;
  
  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public DBNAlliancePayload (@Nonnull final IIdentifierFactory aIdentifierFactory) {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }
  
  /**
   * Description - An OPTIONAL human readable description of the payload.
   * This field is mapped to
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
    return StringHelper.hasText (m_sDescription);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/Description</code>.
   *
   * @param sDescription
   *        Description - An OPTIONAL human readable description of the payload.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/Description</code>
   *        .
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setDescription (@Nonnull @Nonempty final String sDescription)
  {
    ValueEnforcer.notEmpty (sDescription, "Description");

    m_sDescription = sDescription;
    return this;
  }
  
  /**
   * @return The content type code list id. May be <code>null</code>
   *         if not initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code>
   *         .
   */
  @Nullable
  public String getContentTypeCodeListID ()
  {
    return m_sContentTypeCodeListID;
  }

  /**
   * @return The content type code value. May be <code>null</code>
   *         if not initialized. This field is mapped to
   *         <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   */
  @Nullable
  public String getContentTypeCodeValue ()
  {
    return m_sContentTypeCodeValue;
  }

  /**
   * Set the content type code.
   *
   * @param slistID
   *        The DBNAlliance identifier scheme. May neither be <code>null</code> 
   *        nor empty. This field is mapped to
   *        <code>XHE/Payloads/Payload/ContentTypeCode/@listID</code>
   *        .
   * @param sValue
   *        The from party identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>XHE/Payloads/Payload/ContentTypeCode/</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setContentTypeCode(@Nullable final String slistID, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sContentTypeCodeListID = slistID;
    m_sContentTypeCodeValue = sValue;
    return this;
  }
  
  /**
   * @return The customization id scheme that applies to the payload instance. 
   *         May be <code>null</code> if not initialized.
   *         This field is mapped to
   *         <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   */
  @Nullable
  public String getCustomizationIDScheme ()
  {
    return m_sCustomizationIDScheme;
  }

  /**
   * @return The customization id value that applies to the payload instance. 
   *         May be <code>null</code> if not initialized. 
   *         This field is mapped to
   *         <code>XHE/Payloads/Payload/CustomizationID/</code>.
   */
  @Nullable
  public String getCustomizationIDValue ()
  {
    return m_sCustomizationIDValue;
  }

  /**
   * @return The from party participant identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IDocumentTypeIdentifier getCustomizationIDAsIdentifier ()
  {
    return m_aIdentifierFactory.createDocumentTypeIdentifier(m_sCustomizationIDScheme, m_sCustomizationIDValue);
  }

  /**
   * Set the customization identifier.
   *
   * @param sScheme
   *        The customization id scheme that applies to the payload instance. 
   *        May be <code>null</code> if not initialized.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/@schemeID</code>.
   * @param sValue
   *        The customization id value that applies to the payload instance. 
   *        May be <code>null</code> if not initialized. 
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/CustomizationID/</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setCustomizationID (@Nullable final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sCustomizationIDScheme = sScheme;
    m_sCustomizationIDValue = sValue;
    return this;
  }
  
  /**
   * Set the customization identifier.
   *
   * @param aCustomizationID
   *        The document type identifier to use. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setCustomizationID (@Nonnull final IDocumentTypeIdentifier aCustomizationID)
  {
    ValueEnforcer.notNull (aCustomizationID, "CustomizationID");

    return setCustomizationID (aCustomizationID.getScheme (), aCustomizationID.getValue ());
  }
  
  /**
   * @return The profile id scheme that the payload instance is part of. 
   *         May be <code>null</code> if not initialized.
   *         This field is mapped to
   *         <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   */
  @Nullable
  public String getProfileIDScheme ()
  {
    return m_sProfileIDScheme;
  }

  /**
   * @return The profile id value that the payload instance is part of. 
   *         May be <code>null</code> if not initialized. 
   *         This field is mapped to
   *         <code>XHE/Payloads/Payload/ProfileID/</code>.
   */
  @Nullable
  public String getProfileIDValue ()
  {
    return m_sProfileIDValue;
  }

  /**
   * @return The from party participant identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IProcessIdentifier getProfileIDAsIdentifier ()
  {
    return m_aIdentifierFactory.createProcessIdentifier(m_sProfileIDScheme, m_sProfileIDValue);
  }

  /**
   * Set the profile identifier.
   *
   * @param sScheme
   *        The profile id scheme that the payload instance is part of. 
   *        May be <code>null</code> if not initialized.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/ProfileID/@schemeID</code>.
   * @param sValue
   *        The profile id value that the payload instance is part of. 
   *        May be <code>null</code> if not initialized. 
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/ProfileID/</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setProfileID (@Nullable final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sCustomizationIDScheme = sScheme;
    m_sCustomizationIDValue = sValue;
    return this;
  }
  
  /**
   * Set the profile identifier.
   *
   * @param aProfileID
   *        The process identifier to use. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setProfileID (@Nonnull final IProcessIdentifier aProfileID)
  {
    ValueEnforcer.notNull (aProfileID, "CustomizationID");

    return setCustomizationID (aProfileID.getScheme (), aProfileID.getValue ());
  }
  
  /**
   * InstanceEncryptionIndicator - Indicator to state whether the payload instance is encrypted or not.
   * This field is mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>.
   *
   * @return InstanceEncryptionIndicator value. Default value is false.
   */
  @Nullable
  public boolean getInstanceEncryptionIndicator ()
  {
    return m_bInstanceEncryptionIndicator;
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>.
   *
   * @param bInstanceEncryptionIndicator
   *        Indicator to state whether the payload instance is encrypted or not.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/InstanceEncryptionIndicator</code>
   *        .
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setInstanceEncryptionIndicator (@Nonnull final boolean bInstanceEncryptionIndicator)
  {
    ValueEnforcer.notNull(bInstanceEncryptionIndicator, "InstanceEncryptionIndicator");

    m_bInstanceEncryptionIndicator = bInstanceEncryptionIndicator;
    return this;
  }
  
  /**
   * InstanceEncryptionMethod - Method used to encrypt the payload instance.
   * This field is mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @return InstanceEncryptionMethod value. Default value is false.
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
    return StringHelper.hasText (m_sInstanceEncryptionMethod);
  }

  /**
   * Set the content of the fields that are mapped to
   * <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>.
   *
   * @param sInstanceEncryptionMethod
   *        Method used to encrypt the payload instance.
   *        This field is mapped to
   *        <code>XHE/Payloads/Payload/InstanceEncryptionMethod</code>
   *        .
   * @return this
   */
  @Nonnull
  public DBNAlliancePayload setInstanceEncryptionMethod (@Nonnull @Nonempty final String sInstanceEncryptionMethod)
  {
    ValueEnforcer.notNull(sInstanceEncryptionMethod, "InstanceEncryptionMethod");

    m_sInstanceEncryptionMethod = sInstanceEncryptionMethod;
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
   * Check if a payload content is present without having the need to
   * explicitly call {@link #getPayloadContent()} which returns a cloned node
   * and is therefore an expensive operation.
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
}
