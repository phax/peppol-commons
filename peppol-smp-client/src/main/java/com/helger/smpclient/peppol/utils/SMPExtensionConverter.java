/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.smpclient.peppol.utils;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.string.StringHelper;
import com.helger.smpclient.peppol.jaxb.ExtensionType;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.write.EXMLSerializeDocType;
import com.helger.xml.serialize.write.EXMLSerializeIndent;
import com.helger.xml.serialize.write.XMLWriter;
import com.helger.xml.serialize.write.XMLWriterSettings;

/**
 * This class is used for converting between a String representation of the
 * extension element and the "ExtensionType" complex type used in the PEPPOL
 * SMP.
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPExtensionConverter
{
  private static final XMLWriterSettings XWS = new XMLWriterSettings ().setSerializeDocType (EXMLSerializeDocType.IGNORE)
                                                                       .setIndent (EXMLSerializeIndent.NONE);

  @PresentForCodeCoverage
  private static final SMPExtensionConverter s_aInstance = new SMPExtensionConverter ();

  private SMPExtensionConverter ()
  {}

  /**
   * Convert the passed extension type to a string representation.
   *
   * @param aExtension
   *        The extension to be converted. May be <code>null</code>.
   * @return <code>null</code> if no extension was passed - the XML
   *         representation of the extension otherwise.
   * @throws IllegalArgumentException
   *         If the Extension cannot be converted to a String
   */
  @Nullable
  public static String convertToString (@Nullable final ExtensionType aExtension)
  {
    // If there is no extension present, nothing to convert
    if (aExtension != null && aExtension.getAny () != null)
    {
      // Get the extension content
      return XMLWriter.getNodeAsString (aExtension.getAny (), XWS);
    }
    return null;
  }

  /**
   * Convert the passed XML string to an SMP extension type.
   *
   * @param sXML
   *        the XML representation to be converted.
   * @return <code>null</code> if the passed string is empty or does not
   *         represent valid XML.
   */
  @Nullable
  public static ExtensionType convert (@Nullable final String sXML)
  {
    if (StringHelper.hasText (sXML))
    {
      // Try to interpret as XML
      final Document aDoc = DOMReader.readXMLDOM (sXML);
      if (aDoc != null)
      {
        final ExtensionType aExtension = new ExtensionType ();
        aExtension.setAny (aDoc.getDocumentElement ());
        return aExtension;
      }
    }

    return null;
  }
}
