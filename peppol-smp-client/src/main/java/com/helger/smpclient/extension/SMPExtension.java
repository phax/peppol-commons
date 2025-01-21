/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.smpclient.extension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.json.IJsonObject;
import com.helger.json.JsonObject;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.write.EXMLSerializeIndent;
import com.helger.xml.serialize.write.IXMLWriterSettings;
import com.helger.xml.serialize.write.XMLWriter;
import com.helger.xml.serialize.write.XMLWriterSettings;

/**
 * This class contains a generic extension that works for Peppol SMP, OASIS BDXR
 * SMP v1 and OASIS BDXR SMP v2.
 *
 * @author Philip Helger
 * @since 8.7.3
 */
@NotThreadSafe
@MustImplementEqualsAndHashcode
public class SMPExtension
{
  public static final String JSON_ID = "ID";
  public static final String JSON_NAME = "Name";
  public static final String JSON_AGENCY_ID = "AgencyID";
  public static final String JSON_AGENCY_NAME = "AgencyName";
  public static final String JSON_AGENCY_URI = "AgencyURI";
  public static final String JSON_VERSION_ID = "VersionID";
  public static final String JSON_URI = "URI";
  public static final String JSON_REASON_CODE = "ReasonCode";
  public static final String JSON_REASON = "Reason";
  public static final String JSON_ANY = "Any";
  public static final IXMLWriterSettings XWS = new XMLWriterSettings ().setIndent (EXMLSerializeIndent.NONE);

  private String m_sExtensionID;
  private String m_sExtensionName;
  private String m_sExtensionAgencyID;
  private String m_sExtensionAgencyName;
  private String m_sExtensionAgencyURI;
  private String m_sExtensionVersionID;
  private String m_sExtensionURI;
  private String m_sExtensionReasonCode;
  private String m_sExtensionReason;
  private Element m_aAny;

  public SMPExtension ()
  {}

  /**
   * @return the value of the extensionID property. May be <code>null</code>.
   */
  @Nullable
  public String getExtensionID ()
  {
    return m_sExtensionID;
  }

  /**
   * Sets the value of the extensionID property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionID (@Nullable final String value)
  {
    m_sExtensionID = value;
    return this;
  }

  /**
   * @return the value of the extensionName property.
   */
  @Nullable
  public String getExtensionName ()
  {
    return m_sExtensionName;
  }

  /**
   * Sets the value of the extensionName property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionName (@Nullable final String value)
  {
    m_sExtensionName = value;
    return this;
  }

  /**
   * @return the value of the extensionAgencyID property.
   */
  @Nullable
  public String getExtensionAgencyID ()
  {
    return m_sExtensionAgencyID;
  }

  /**
   * Sets the value of the extensionAgencyID property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionAgencyID (@Nullable final String value)
  {
    m_sExtensionAgencyID = value;
    return this;
  }

  /**
   * @return the value of the extensionAgencyName property.
   */
  @Nullable
  public String getExtensionAgencyName ()
  {
    return m_sExtensionAgencyName;
  }

  /**
   * Sets the value of the extensionAgencyName property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionAgencyName (@Nullable final String value)
  {
    m_sExtensionAgencyName = value;
    return this;
  }

  /**
   * @return the value of the extensionAgencyURI property.
   */
  @Nullable
  public String getExtensionAgencyURI ()
  {
    return m_sExtensionAgencyURI;
  }

  /**
   * Sets the value of the extensionAgencyURI property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionAgencyURI (@Nullable final String value)
  {
    m_sExtensionAgencyURI = value;
    return this;
  }

  /**
   * @return the value of the extensionVersionID property.
   */
  @Nullable
  public String getExtensionVersionID ()
  {
    return m_sExtensionVersionID;
  }

  /**
   * Sets the value of the extensionVersionID property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionVersionID (@Nullable final String value)
  {
    m_sExtensionVersionID = value;
    return this;
  }

  /**
   * @return the value of the extensionURI property.
   */
  @Nullable
  public String getExtensionURI ()
  {
    return m_sExtensionURI;
  }

  /**
   * Sets the value of the extensionURI property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionURI (@Nullable final String value)
  {
    m_sExtensionURI = value;
    return this;
  }

  /**
   * @return the value of the extensionReasonCode property.
   */
  @Nullable
  public String getExtensionReasonCode ()
  {
    return m_sExtensionReasonCode;
  }

  /**
   * Sets the value of the extensionReasonCode property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionReasonCode (@Nullable final String value)
  {
    m_sExtensionReasonCode = value;
    return this;
  }

  /**
   * @return the value of the extensionReason property.
   */
  @Nullable
  public String getExtensionReason ()
  {
    return m_sExtensionReason;
  }

  /**
   * Sets the value of the extensionReason property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setExtensionReason (@Nullable final String value)
  {
    m_sExtensionReason = value;
    return this;
  }

  /**
   * @return the value of the any property.
   */
  @Nullable
  public Element getAny ()
  {
    return m_aAny;
  }

  /**
   * Sets the value of the any property.
   *
   * @param value
   *        May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public SMPExtension setAny (@Nullable final Element value)
  {
    m_aAny = value;
    return this;
  }

  /**
   * @return A JSON representation of the extension object or <code>null</code>
   *         if no property is set.
   */
  @Nullable
  public IJsonObject getAsJsonObject ()
  {
    final JsonObject ret = new JsonObject ();
    if (m_sExtensionID != null)
      ret.add (JSON_ID, m_sExtensionID);
    if (m_sExtensionName != null)
      ret.add (JSON_NAME, m_sExtensionName);
    if (m_sExtensionAgencyID != null)
      ret.add (JSON_AGENCY_ID, m_sExtensionAgencyID);
    if (m_sExtensionAgencyName != null)
      ret.add (JSON_AGENCY_NAME, m_sExtensionAgencyName);
    if (m_sExtensionAgencyURI != null)
      ret.add (JSON_AGENCY_URI, m_sExtensionAgencyURI);
    if (m_sExtensionVersionID != null)
      ret.add (JSON_VERSION_ID, m_sExtensionVersionID);
    if (m_sExtensionURI != null)
      ret.add (JSON_URI, m_sExtensionURI);
    if (m_sExtensionReasonCode != null)
      ret.add (JSON_REASON_CODE, m_sExtensionReasonCode);
    if (m_sExtensionReason != null)
      ret.add (JSON_REASON, m_sExtensionReason);
    if (m_aAny != null)
    {
      // Convert XML to String
      final String sAny = XMLWriter.getNodeAsString (m_aAny, XWS);
      ret.add (JSON_ANY, sAny);
    }
    return ret.isEmpty () ? null : ret;
  }

  @Nullable
  @ReturnsMutableCopy
  public com.helger.xsds.peppol.smp1.ExtensionType getAsPeppolExtension ()
  {
    if (m_aAny == null)
      return null;

    // Use only the XML element of the first extension
    final com.helger.xsds.peppol.smp1.ExtensionType ret = new com.helger.xsds.peppol.smp1.ExtensionType ();
    ret.setAny (m_aAny);
    return ret;
  }

  @Nonnull
  public com.helger.xsds.bdxr.smp1.ExtensionType getAsBDXRExtension ()
  {
    final com.helger.xsds.bdxr.smp1.ExtensionType ret = new com.helger.xsds.bdxr.smp1.ExtensionType ();
    ret.setExtensionID (m_sExtensionID);
    ret.setExtensionName (m_sExtensionName);
    ret.setExtensionAgencyID (m_sExtensionAgencyID);
    ret.setExtensionAgencyName (m_sExtensionAgencyName);
    ret.setExtensionAgencyURI (m_sExtensionAgencyURI);
    ret.setExtensionVersionID (m_sExtensionVersionID);
    ret.setExtensionURI (m_sExtensionURI);
    ret.setExtensionReasonCode (m_sExtensionReasonCode);
    ret.setExtensionReason (m_sExtensionReason);
    ret.setAny (m_aAny);
    return ret;
  }

  @Nonnull
  public com.helger.xsds.bdxr.smp2.ec.SMPExtensionType getAsBDXR2Extension ()
  {
    final com.helger.xsds.bdxr.smp2.ec.SMPExtensionType ret = new com.helger.xsds.bdxr.smp2.ec.SMPExtensionType ();
    if (m_sExtensionID != null)
      ret.setID (m_sExtensionID);
    if (m_sExtensionName != null)
      ret.setName (m_sExtensionName);
    if (m_sExtensionAgencyID != null)
      ret.setExtensionAgencyID (m_sExtensionAgencyID);
    if (m_sExtensionAgencyName != null)
      ret.setExtensionAgencyName (m_sExtensionAgencyName);
    if (m_sExtensionVersionID != null)
      ret.setExtensionVersionID (m_sExtensionVersionID);
    if (m_sExtensionAgencyURI != null)
      ret.setExtensionAgencyURI (m_sExtensionAgencyURI);
    if (m_sExtensionURI != null)
      ret.setExtensionURI (m_sExtensionURI);
    if (m_sExtensionReasonCode != null)
      ret.setExtensionReasonCode (m_sExtensionReasonCode);
    if (m_sExtensionReason != null)
      ret.setExtensionReason (m_sExtensionReason);
    if (m_aAny != null)
    {
      final com.helger.xsds.bdxr.smp2.ec.ExtensionContentType aEC = new com.helger.xsds.bdxr.smp2.ec.ExtensionContentType ();
      aEC.setAny (m_aAny);
      ret.setExtensionContent (aEC);
    }
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final SMPExtension rhs = (SMPExtension) o;
    return EqualsHelper.equals (m_sExtensionID, rhs.m_sExtensionID) &&
           EqualsHelper.equals (m_sExtensionName, rhs.m_sExtensionName) &&
           EqualsHelper.equals (m_sExtensionAgencyID, rhs.m_sExtensionAgencyID) &&
           EqualsHelper.equals (m_sExtensionAgencyName, rhs.m_sExtensionAgencyName) &&
           EqualsHelper.equals (m_sExtensionAgencyURI, rhs.m_sExtensionAgencyURI) &&
           EqualsHelper.equals (m_sExtensionVersionID, rhs.m_sExtensionVersionID) &&
           EqualsHelper.equals (m_sExtensionURI, rhs.m_sExtensionURI) &&
           EqualsHelper.equals (m_sExtensionReasonCode, rhs.m_sExtensionReasonCode) &&
           EqualsHelper.equals (m_sExtensionReason, rhs.m_sExtensionReason) &&
           EqualsHelper.equals (m_aAny, rhs.m_aAny);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sExtensionID)
                                       .append (m_sExtensionName)
                                       .append (m_sExtensionAgencyID)
                                       .append (m_sExtensionAgencyName)
                                       .append (m_sExtensionAgencyURI)
                                       .append (m_sExtensionVersionID)
                                       .append (m_sExtensionURI)
                                       .append (m_sExtensionReasonCode)
                                       .append (m_sExtensionReason)
                                       .append (m_aAny)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ExtensionID", m_sExtensionID)
                                       .append ("ExtensionName", m_sExtensionName)
                                       .append ("ExtensionAgencyID", m_sExtensionAgencyID)
                                       .append ("ExtensionAgencyName", m_sExtensionAgencyName)
                                       .append ("ExtensionAgencyURI", m_sExtensionAgencyURI)
                                       .append ("ExtensionVersionID", m_sExtensionVersionID)
                                       .append ("ExtensionURI", m_sExtensionURI)
                                       .append ("ExtensionReasonCode", m_sExtensionReasonCode)
                                       .append ("ExtensionReason", m_sExtensionReason)
                                       .append ("Any", m_aAny)
                                       .getToString ();
  }

  @Nullable
  public static SMPExtension ofJson (@Nullable final IJsonObject aObject)
  {
    if (aObject == null || aObject.isEmpty ())
      return null;

    final SMPExtension aExt = new SMPExtension ();
    aExt.setExtensionID (aObject.getAsString (JSON_ID));
    aExt.setExtensionName (aObject.getAsString (JSON_NAME));
    aExt.setExtensionAgencyID (aObject.getAsString (JSON_AGENCY_ID));
    aExt.setExtensionAgencyName (aObject.getAsString (JSON_AGENCY_NAME));
    aExt.setExtensionAgencyURI (aObject.getAsString (JSON_AGENCY_URI));
    aExt.setExtensionVersionID (aObject.getAsString (JSON_VERSION_ID));
    aExt.setExtensionURI (aObject.getAsString (JSON_URI));
    aExt.setExtensionReasonCode (aObject.getAsString (JSON_REASON_CODE));
    aExt.setExtensionReason (aObject.getAsString (JSON_REASON));
    final String sAny = aObject.getAsString (JSON_ANY);
    if (StringHelper.hasText (sAny))
    {
      final Document aDoc = DOMReader.readXMLDOM (sAny);
      if (aDoc != null)
        aExt.setAny (aDoc.getDocumentElement ());
    }
    return aExt;
  }

  @Nullable
  public static SMPExtension ofXML (@Nullable final String sXML)
  {
    if (StringHelper.hasText (sXML))
    {
      final Document aDoc = DOMReader.readXMLDOM (sXML);
      if (aDoc != null)
      {
        final Element aElement = aDoc.getDocumentElement ();
        if (aElement != null)
        {
          final SMPExtension aExtension = new SMPExtension ();
          aExtension.setAny (aElement);
          return aExtension;
        }
        // found an empty XML document
      }
      // failed to read XML
    }
    return null;
  }

  @Nullable
  public static SMPExtension ofBDXR1 (@Nullable final com.helger.xsds.bdxr.smp1.ExtensionType aExt)
  {
    if (aExt == null)
      return null;

    final SMPExtension ret = new SMPExtension ();
    ret.setExtensionID (aExt.getExtensionID ());
    ret.setExtensionName (aExt.getExtensionName ());
    ret.setExtensionAgencyID (aExt.getExtensionAgencyID ());
    ret.setExtensionAgencyName (aExt.getExtensionAgencyName ());
    ret.setExtensionAgencyURI (aExt.getExtensionAgencyURI ());
    ret.setExtensionVersionID (aExt.getExtensionVersionID ());
    ret.setExtensionURI (aExt.getExtensionURI ());
    ret.setExtensionReasonCode (aExt.getExtensionReasonCode ());
    ret.setExtensionReason (aExt.getExtensionReason ());
    if (aExt.getAny () instanceof Element)
      ret.setAny ((Element) aExt.getAny ());
    return ret;
  }

  @Nullable
  public static SMPExtension ofBDXR2 (@Nullable final com.helger.xsds.bdxr.smp2.ec.SMPExtensionType aExt)
  {
    if (aExt == null)
      return null;

    final SMPExtension ret = new SMPExtension ();
    ret.setExtensionID (aExt.getIDValue ());
    ret.setExtensionName (aExt.getNameValue ());
    ret.setExtensionAgencyID (aExt.getExtensionAgencyIDValue ());
    ret.setExtensionAgencyName (aExt.getExtensionAgencyNameValue ());
    ret.setExtensionAgencyURI (aExt.getExtensionAgencyURIValue ());
    ret.setExtensionVersionID (aExt.getExtensionVersionIDValue ());
    ret.setExtensionURI (aExt.getExtensionURIValue ());
    ret.setExtensionReasonCode (aExt.getExtensionReasonCodeValue ());
    ret.setExtensionReason (aExt.getExtensionReasonValue ());
    final com.helger.xsds.bdxr.smp2.ec.ExtensionContentType aEC = aExt.getExtensionContent ();
    if (aEC != null && aEC.getAny () instanceof Element)
      ret.setAny ((Element) aEC.getAny ());
    return ret;
  }
}
