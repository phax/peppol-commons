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
package com.helger.peppol.identifier.peppol.process;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.IPeppolIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;

/**
 * Base interface for a PEPPOL read-only process identifier.
 *
 * @author philip
 */
public interface IPeppolProcessIdentifier extends IPeppolIdentifier, IProcessIdentifier
{
  default boolean hasDefaultScheme ()
  {
    return hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME);
  }

  /**
   * Check if the passed process identifier scheme is valid or not. For
   * processes no additional rules apply than the standard rules.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   * @see PeppolIdentifierHelper#isValidIdentifierScheme(String)
   */
  static boolean isValidScheme (@Nullable final String sScheme)
  {
    // No special rules
    return PeppolIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  /**
   * Check if the passed process identifier value is valid. A valid identifier
   * must have at least 1 character and at last
   * {@link PeppolIdentifierHelper#MAX_PROCESS_VALUE_LENGTH} characters. Also it
   * must be ISO-8859-1 encoded.
   *
   * @param sValue
   *        The process identifier value to be checked (without the scheme). May
   *        be <code>null</code>.
   * @return <code>true</code> if the process identifier value is valid,
   *         <code>false</code> otherwise
   */
  static boolean isValidValue (@Nullable final String sValue)
  {
    final int nLength = StringHelper.getLength (sValue);
    if (nLength == 0 || nLength > PeppolIdentifierHelper.MAX_PROCESS_VALUE_LENGTH)
      return false;

    // Check if the value is ISO-8859-1 encoded
    return PeppolIdentifierHelper.areCharsetChecksDisabled () ||
           StandardCharsets.ISO_8859_1.newEncoder ().canEncode (sValue);
  }
}
