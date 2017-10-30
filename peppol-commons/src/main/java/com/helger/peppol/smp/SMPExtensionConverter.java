/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smp;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.string.StringHelper;
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
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class SMPExtensionConverter
{
  private static final XMLWriterSettings s_aXWS = new XMLWriterSettings ().setSerializeDocType (EXMLSerializeDocType.IGNORE)
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
      return XMLWriter.getNodeAsString (aExtension.getAny (), s_aXWS);
    }
    return null;
  }

  /**
   * Convert the passed XML string to an SMP extension type.
   *
   * @param sXML
   *        the XML representation to be converted.
   * @return <code>null</code> if the passed string is empty.
   * @throws IllegalArgumentException
   *         If the String cannot be converted to a XML node
   */
  @Nullable
  public static ExtensionType convert (@Nullable final String sXML)
  {
    if (StringHelper.hasText (sXML))
    {
      try
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
      catch (final SAXException ex)
      {
        throw new IllegalArgumentException ("Error in parsing extension XML '" + sXML + "'", ex);
      }
    }

    return null;
  }

  /**
   * Convert the passed XML string to an SMP extension type.
   *
   * @param sXML
   *        the XML representation to be converted.
   * @return <code>null</code> if the passed string is empty or cannot be
   *         converted to a XML node
   */
  @Nullable
  public static ExtensionType convertOrNull (@Nullable final String sXML)
  {
    try
    {
      return convert (sXML);
    }
    catch (final IllegalArgumentException ex)
    {
      // Invalid XML passed
      return null;
    }
  }
}
