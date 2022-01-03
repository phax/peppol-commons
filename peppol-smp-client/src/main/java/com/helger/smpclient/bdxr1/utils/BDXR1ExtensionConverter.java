/*
 * Copyright (C) 2015-2022 Philip Helger
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
package com.helger.smpclient.bdxr1.utils;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.json.IJson;
import com.helger.json.IJsonArray;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.JsonObject;
import com.helger.json.serialize.JsonReader;
import com.helger.json.serialize.JsonWriterSettings;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.write.EXMLSerializeIndent;
import com.helger.xml.serialize.write.XMLWriter;
import com.helger.xml.serialize.write.XMLWriterSettings;
import com.helger.xsds.bdxr.smp1.ExtensionType;
import com.helger.xsds.bdxr.smp2.ec.SMPExtensionType;

/**
 * This class is used for converting between a String representation of the
 * extension elements and the list of "ExtensionType" complex types used in the
 * BDXR SMP. Compared to the PEPPOL SMP, the BDXR SMP uses a complex
 * ExtensionType and has a multiplicity of 0-n (compared to 0-1 in PEPPOL SMP).
 * Therefore I've decided to use Json instead of XML to allow for a single
 * string solution to maintain DB schema compatibility.
 *
 * @author Philip Helger
 */
@Immutable
public final class BDXR1ExtensionConverter
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

  private static final Logger LOGGER = LoggerFactory.getLogger (BDXR1ExtensionConverter.class);
  private static final XMLWriterSettings XWS = new XMLWriterSettings ().setIndent (EXMLSerializeIndent.NONE);

  @PresentForCodeCoverage
  private static final BDXR1ExtensionConverter INSTANCE = new BDXR1ExtensionConverter ();

  private BDXR1ExtensionConverter ()
  {}

  /**
   * Convert the passed extension type to a JSON representation.
   *
   * @param aExtension
   *        The extension to be converted. May be <code>null</code>.
   * @return <code>null</code> if no extension or an empty extension was passed
   *         - the JSON of the extension otherwise.
   */
  @Nullable
  public static IJsonObject convertToJson (@Nullable final ExtensionType aExtension)
  {
    // If there is no extension present, nothing to convert
    if (aExtension != null && aExtension.getAny () != null)
    {
      Object aAny = aExtension.getAny ();
      if (aAny instanceof Element)
      {
        // Convert XML to String
        aAny = XMLWriter.getNodeAsString ((Element) aAny, XWS);
      }

      final JsonObject ret = new JsonObject ();
      if (aExtension.getExtensionID () != null)
        ret.add (JSON_ID, aExtension.getExtensionID ());
      if (aExtension.getExtensionName () != null)
        ret.add (JSON_NAME, aExtension.getExtensionName ());
      if (aExtension.getExtensionAgencyID () != null)
        ret.add (JSON_AGENCY_ID, aExtension.getExtensionAgencyID ());
      if (aExtension.getExtensionAgencyName () != null)
        ret.add (JSON_AGENCY_NAME, aExtension.getExtensionAgencyName ());
      if (aExtension.getExtensionAgencyURI () != null)
        ret.add (JSON_AGENCY_URI, aExtension.getExtensionAgencyURI ());
      if (aExtension.getExtensionVersionID () != null)
        ret.add (JSON_VERSION_ID, aExtension.getExtensionVersionID ());
      if (aExtension.getExtensionURI () != null)
        ret.add (JSON_URI, aExtension.getExtensionURI ());
      if (aExtension.getExtensionReasonCode () != null)
        ret.add (JSON_REASON_CODE, aExtension.getExtensionReasonCode ());
      if (aExtension.getExtensionReason () != null)
        ret.add (JSON_REASON, aExtension.getExtensionReason ());
      if (aAny != null)
        ret.add (JSON_ANY, aAny);
      return ret;
    }
    return null;
  }

  /**
   * Convert the passed extension types to a JSON representation.
   *
   * @param aExtensions
   *        The extension to be converted. May be <code>null</code>.
   * @return <code>null</code> if no extension was passed - the JSON
   *         representation of the extensions otherwise.
   */
  @Nullable
  public static IJsonArray convertToJson (@Nullable final List <ExtensionType> aExtensions)
  {
    // If there is no extension present, nothing to convert
    if (CollectionHelper.isNotEmpty (aExtensions))
    {
      final IJsonArray aArray = new JsonArray ();
      for (final ExtensionType aExtension : aExtensions)
      {
        final IJsonObject aObj = convertToJson (aExtension);
        if (aObj != null)
          aArray.add (aObj);
      }
      return aArray;
    }
    return null;
  }

  /**
   * Convert the passed extension types to a string representation.
   *
   * @param aExtensions
   *        The extension to be converted. May be <code>null</code>.
   * @return <code>null</code> if no extension was passed - the String
   *         representation of the extensions otherwise.
   */
  @Nullable
  public static String convertToString (@Nullable final List <ExtensionType> aExtensions)
  {
    final IJsonArray aArray = convertToJson (aExtensions);
    return aArray == null ? null : aArray.getAsJsonString (JsonWriterSettings.DEFAULT_SETTINGS);
  }

  /**
   * Parse the provided XML, and if it is valid, convert it to a simple
   * extension. This method exists, so that compatibility to the old PEPPOL SMP
   * specification is available (single extension with only a DOM Element).
   *
   * @param sXML
   *        The XML to be parsed. May be <code>null</code>.
   * @return <code>null</code> if no XML or invalid XML is provided, a
   *         non-<code>null</code> list with a single extension otherwise.
   */
  @Nullable
  public static ICommonsList <ExtensionType> convertXMLToSingleExtension (@Nullable final String sXML)
  {
    if (StringHelper.hasText (sXML))
    {
      final Document aDoc = DOMReader.readXMLDOM (sXML);
      if (aDoc != null)
      {
        final Element aElement = aDoc.getDocumentElement ();
        if (aElement != null)
        {
          final ExtensionType aExtension = new ExtensionType ();
          aExtension.setAny (aElement);
          return new CommonsArrayList <> (aExtension);
        }
      }
    }

    return null;
  }

  /**
   * Convert the passed Json string to a list of SMP extensions.
   *
   * @param sJson
   *        the Json representation to be converted.
   * @return <code>null</code> if the passed string is empty or cannot be
   *         interpreted as JSON.
   */
  @Nullable
  public static ICommonsList <ExtensionType> convert (@Nullable final String sJson)
  {
    if (StringHelper.hasText (sJson))
    {
      // Try to interpret as JSON
      final IJson aJson = JsonReader.readFromString (sJson);
      if (aJson == null || !aJson.isArray ())
      {
        LOGGER.warn ("Error in parsing extension JSON '" + sJson + "'");
      }
      else
      {
        final ICommonsList <ExtensionType> ret = new CommonsArrayList <> ();
        aJson.getAsArray ().forEach (aChild -> {
          final IJsonObject aObject = aChild.getAsObject ();
          final ExtensionType aExt = new ExtensionType ();
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
          ret.add (aExt);
        });
        return ret;
      }
    }
    return null;
  }

  @Nonnull
  public static ExtensionType convertFromExtensionBDXR2 (@Nonnull final SMPExtensionType aExt)
  {
    final ExtensionType ret = new ExtensionType ();
    ret.setExtensionID (aExt.getIDValue ());
    ret.setExtensionName (aExt.getNameValue ());
    ret.setExtensionAgencyID (aExt.getExtensionAgencyIDValue ());
    ret.setExtensionAgencyName (aExt.getExtensionAgencyNameValue ());
    ret.setExtensionAgencyURI (aExt.getExtensionAgencyURIValue ());
    ret.setExtensionVersionID (aExt.getExtensionVersionIDValue ());
    ret.setExtensionURI (aExt.getExtensionURIValue ());
    ret.setExtensionReasonCode (aExt.getExtensionReasonCodeValue ());
    ret.setExtensionReason (aExt.getExtensionReasonValue ());
    return ret;
  }
}
