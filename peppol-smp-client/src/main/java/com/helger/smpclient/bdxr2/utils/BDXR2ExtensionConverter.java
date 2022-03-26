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
package com.helger.smpclient.bdxr2.utils;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.json.IJsonArray;
import com.helger.json.serialize.JsonWriterSettings;
import com.helger.xml.serialize.write.EXMLSerializeIndent;
import com.helger.xml.serialize.write.XMLWriterSettings;
import com.helger.xsds.bdxr.smp2.ec.SMPExtensionsType;

/**
 * This class is used for converting between a String representation of the
 * extension elements and the list of "ExtensionType" complex types used in the
 * BDXR SMP v2. Compared to the Peppol SMP, the BDXR SMP uses a complex
 * ExtensionType and has a multiplicity of 0-n (compared to 0-1 in PEPPOL SMP).
 * Therefore I've decided to use Json instead of XML to allow for a single
 * string solution to maintain DB schema compatibility.
 *
 * @author Philip Helger
 */
@Immutable
public final class BDXR2ExtensionConverter
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

  private static final Logger LOGGER = LoggerFactory.getLogger (BDXR2ExtensionConverter.class);
  private static final XMLWriterSettings XWS = new XMLWriterSettings ().setIndent (EXMLSerializeIndent.NONE);

  @PresentForCodeCoverage
  private static final BDXR2ExtensionConverter INSTANCE = new BDXR2ExtensionConverter ();

  private BDXR2ExtensionConverter ()
  {}

  /**
   * Convert the passed extension types to a string representation.
   *
   * @param aExtensions
   *        The extension to be converted. May be <code>null</code>.
   * @return <code>null</code> if no extension was passed - the String
   *         representation of the extensions otherwise.
   */
  @Nullable
  public static String convertToJsonString (@Nullable final SMPExtensionsType aExtensions)
  {
    // TODO
    final IJsonArray aArray = null;
    return aArray == null ? null : aArray.getAsJsonString (JsonWriterSettings.DEFAULT_SETTINGS);
  }
}
