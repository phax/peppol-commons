/*
 * Copyright (C) 2015-2024 Philip Helger
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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.json.IJson;
import com.helger.json.IJsonArray;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.serialize.JsonReader;
import com.helger.json.serialize.JsonWriterSettings;
import com.helger.xml.serialize.write.XMLWriter;
import com.helger.xsds.bdxr.smp2.ec.SMPExtensionType;

/**
 * A list of generic {@link SMPExtension} objects.
 *
 * @author Philip Helger
 * @since 8.7.3
 */
@NotThreadSafe
@MustImplementEqualsAndHashcode
public class SMPExtensionList
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMPExtensionList.class);

  private final ICommonsList <SMPExtension> m_aExtensions = new CommonsArrayList <> ();

  public SMPExtensionList ()
  {}

  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <SMPExtension> extensions ()
  {
    return m_aExtensions;
  }

  @Nullable
  public IJsonArray getExtensionsAsJson ()
  {
    if (m_aExtensions.isEmpty ())
      return null;

    final IJsonArray ret = new JsonArray ();
    for (final SMPExtension aExt : m_aExtensions)
      ret.addIfNotNull (aExt.getAsJsonObject ());
    return ret.isEmpty () ? null : ret;
  }

  @Nullable
  public String getExtensionsAsJsonString ()
  {
    final IJsonArray aJson = getExtensionsAsJson ();
    return aJson == null ? null : aJson.getAsJsonString (JsonWriterSettings.DEFAULT_SETTINGS);
  }

  @Nullable
  public String getFirstExtensionXMLString ()
  {
    if (m_aExtensions.isEmpty ())
      return null;

    final Element aFirst = m_aExtensions.getFirstOrNull ().getAny ();
    return XMLWriter.getNodeAsString (aFirst, SMPExtension.XWS);
  }

  @Nonnull
  public final EChange setExtensionAsString (@Nullable final String sExtension)
  {
    ICommonsList <SMPExtension> aNewExt = null;
    if (StringHelper.hasText (sExtension))
    {
      // Soft migration :)
      if (sExtension.trim ().charAt (0) == '<')
      {
        final SMPExtension aExt = SMPExtension.ofXML (sExtension);
        if (aExt != null)
          aNewExt = new CommonsArrayList <> (aExt);
      }
      else
      {
        // Read as JSON array
        // Try to interpret as JSON
        final IJson aJson = JsonReader.readFromString (sExtension);
        if (aJson == null || !aJson.isArray ())
        {
          LOGGER.warn ("Error in parsing extension JSON '" + sExtension + "'");
        }
        else
        {
          final ICommonsList <SMPExtension> aParsedExts = new CommonsArrayList <> ();
          for (final IJsonObject aChild : aJson.getAsArray ().iteratorObjects ())
          {
            final SMPExtension aExt = SMPExtension.ofJson (aChild);
            if (aExt != null)
              aParsedExts.add (aExt);
          }
          if (aParsedExts.isNotEmpty ())
            aNewExt = aParsedExts;
        }
      }
    }
    if (m_aExtensions.equals (aNewExt))
      return EChange.UNCHANGED;
    m_aExtensions.setAll (aNewExt);
    return EChange.CHANGED;
  }

  @Nullable
  @ReturnsMutableCopy
  public com.helger.xsds.peppol.smp1.ExtensionType getAsPeppolExtension ()
  {
    if (m_aExtensions.isEmpty ())
      return null;

    if (m_aExtensions.size () > 1)
      LOGGER.warn ("The Peppol data model only knows 1 extension. You have " + m_aExtensions.size () + " extension");
    return m_aExtensions.getFirstOrNull ().getAsPeppolExtension ();
  }

  @Nullable
  @ReturnsMutableCopy
  public ICommonsList <com.helger.xsds.bdxr.smp1.ExtensionType> getAsBDXRExtensions ()
  {
    if (m_aExtensions.isEmpty ())
      return null;

    final ICommonsList <com.helger.xsds.bdxr.smp1.ExtensionType> ret = new CommonsArrayList <> ();
    for (final SMPExtension aExt : m_aExtensions)
      ret.add (aExt.getAsBDXRExtension ());
    return ret;
  }

  @Nullable
  @ReturnsMutableCopy
  public com.helger.xsds.bdxr.smp2.ec.SMPExtensionsType getAsBDXR2Extensions ()
  {
    if (m_aExtensions.isEmpty ())
      return null;

    final com.helger.xsds.bdxr.smp2.ec.SMPExtensionsType ret = new com.helger.xsds.bdxr.smp2.ec.SMPExtensionsType ();
    for (final SMPExtension aExt : m_aExtensions)
      ret.addSMPExtension (aExt.getAsBDXR2Extension ());
    return ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final SMPExtensionList rhs = (SMPExtensionList) o;
    return m_aExtensions.equals (rhs.m_aExtensions);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aExtensions).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Extensions", m_aExtensions).getToString ();
  }

  @Nullable
  public static SMPExtensionList of (@Nullable final SMPExtension... aExtensions)
  {
    final SMPExtensionList ret = new SMPExtensionList ();
    if (aExtensions != null)
      for (final SMPExtension aExt : aExtensions)
        if (aExt != null)
          ret.extensions ().add (aExt);
    return ret.extensions ().isEmpty () ? null : ret;
  }

  @Nullable
  public static SMPExtensionList ofString (@Nullable final String sExtensions)
  {
    final SMPExtensionList ret = new SMPExtensionList ();
    ret.setExtensionAsString (sExtensions);
    return ret.extensions ().isEmpty () ? null : ret;
  }

  @Nullable
  public static SMPExtensionList ofBDXR1 (@Nullable final List <com.helger.xsds.bdxr.smp1.ExtensionType> aExtensions)
  {
    if (aExtensions == null)
      return null;

    final SMPExtensionList ret = new SMPExtensionList ();
    for (final com.helger.xsds.bdxr.smp1.ExtensionType aExt : aExtensions)
    {
      final SMPExtension aRealExt = SMPExtension.ofBDXR1 (aExt);
      if (aRealExt != null)
        ret.extensions ().add (aRealExt);
    }
    return ret.extensions ().isEmpty () ? null : ret;
  }

  @Nullable
  public static SMPExtensionList ofBDXR2 (@Nullable final com.helger.xsds.bdxr.smp2.ec.SMPExtensionsType aExtensions)
  {
    if (aExtensions == null)
      return null;

    final SMPExtensionList ret = new SMPExtensionList ();
    for (final SMPExtensionType aExt : aExtensions.getSMPExtension ())
    {
      final SMPExtension aRealExt = SMPExtension.ofBDXR2 (aExt);
      if (aRealExt != null)
        ret.extensions ().add (aRealExt);
    }
    return ret.extensions ().isEmpty () ? null : ret;
  }
}
