/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.bdxr;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.json.IJson;
import com.helger.json.IJsonArray;
import com.helger.json.IJsonObject;
import com.helger.json.JsonArray;
import com.helger.json.JsonObject;
import com.helger.json.serialize.JsonReader;
import com.helger.json.serialize.JsonWriterSettings;

/**
 * This class is used for converting between a String representation of the
 * extension elements and the list of "ExtensionType" complex types used in the
 * BDXR SMP. Compared to the PEPPOL SMP, the BDXR SMP uses a complex
 * ExtensionType and has a multiplicity of 0-n (compared to 0-1 in PEPPOL SMP).
 * Therefore I've decided to use Json instead of XML to allow for a single
 * string solution to maintain DB schema compatibility.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class BDXRExtensionConverter
{
  private static final JsonWriterSettings s_aJWS = new JsonWriterSettings ().setIndentEnabled (false)
                                                                            .setWriteNewlineAtEnd (false);

  @PresentForCodeCoverage
  private static final BDXRExtensionConverter s_aInstance = new BDXRExtensionConverter ();

  private BDXRExtensionConverter ()
  {}

  /**
   * Convert the passed extension type to a JSON representation.
   *
   * @param aExtensions
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
      return new JsonObject ().add ("ID", aExtension.getExtensionID ())
                              .add ("Name", aExtension.getExtensionName ())
                              .add ("AgencyID", aExtension.getExtensionAgencyID ())
                              .add ("AgencyName", aExtension.getExtensionAgencyName ())
                              .add ("AgencyURI", aExtension.getExtensionAgencyURI ())
                              .add ("VersionID", aExtension.getExtensionVersionID ())
                              .add ("URI", aExtension.getExtensionURI ())
                              .add ("ReasonCode", aExtension.getExtensionReasonCode ())
                              .add ("Reason", aExtension.getExtensionReason ())
                              .add ("Any", aExtension.getAny ());
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
      final JsonArray aArray = new JsonArray ();
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
    return aArray == null ? null : aArray.getAsJsonString (s_aJWS);
  }

  /**
   * Convert the passed Json string to a list of SMP extensions.
   *
   * @param sJson
   *        the Json representation to be converted.
   * @return <code>null</code> if the passed string is empty.
   * @throws IllegalArgumentException
   *         If the String cannot be converted to a XML node
   */
  @Nullable
  public static ICommonsList <ExtensionType> convert (@Nullable final String sJson)
  {
    if (StringHelper.hasText (sJson))
    {
      // Try to interpret as JSON
      final IJson aJson = JsonReader.readFromString (sJson);
      if (aJson == null || !aJson.isArray ())
        throw new IllegalArgumentException ("Error in parsing extension JSON '" + sJson + "'");

      final ICommonsList <ExtensionType> ret = new CommonsArrayList<> ();
      aJson.getAsArray ().forEach (aChild -> {
        final IJsonObject aObject = aChild.getAsObject ();
        final ExtensionType aExt = new ExtensionType ();
        aExt.setExtensionID (aObject.getAsString ("ID"));
        aExt.setExtensionName (aObject.getAsString ("Name"));
        aExt.setExtensionAgencyID (aObject.getAsString ("AgencyID"));
        aExt.setExtensionAgencyName (aObject.getAsString ("AgencyName"));
        aExt.setExtensionAgencyURI (aObject.getAsString ("AgencyURI"));
        aExt.setExtensionVersionID (aObject.getAsString ("VersionID"));
        aExt.setExtensionURI (aObject.getAsString ("URI"));
        aExt.setExtensionReasonCode (aObject.getAsString ("ReasonCode"));
        aExt.setExtensionReason (aObject.getAsString ("Reason"));
        aExt.setAny (aObject.getValue ("Any"));
        ret.add (aExt);
      });
      return ret;
    }
    return null;
  }

  /**
   * Convert the passed JSON string to a list of SMP extension types.
   *
   * @param sJson
   *        the JSON representation to be converted.
   * @return <code>null</code> if the passed string is empty or cannot be
   *         converted to a XML node
   */
  @Nullable
  public static ICommonsList <ExtensionType> convertOrNull (@Nullable final String sJson)
  {
    try
    {
      return convert (sJson);
    }
    catch (final IllegalArgumentException ex)
    {
      // Invalid JSON passed
      return null;
    }
  }
}
