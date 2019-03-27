/**
 * Copyright (C) 2014-2019 Philip Helger
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
package com.helger.peppol.sbdh;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Contains an ordered set of custom variables to be provided in PEPPOL SBDH 1.1
 * documents.
 *
 * @author Philip Helger
 * @since 6.1.4
 */
@Immutable
public final class PeppolSBDHAdditionalAttributes
{
  private PeppolSBDHAdditionalAttributes ()
  {}

  /**
   * Check if the passed attribute name is reserved according to the
   * specification or not. Attribute names are case sensitive!
   *
   * @param sName
   *        Name of the attribute to check. May be <code>null</code>.
   * @return <code>true</code> if the name is not <code>null</code> and inside
   *         the list of reserved names.
   */
  public static boolean isReservedAttributeName (@Nullable final String sName)
  {
    if (sName == null)
      return false;
    return sName.equals ("DOCUMENTID") ||
           sName.equals ("PROCESSID") ||
           sName.equals ("TECHNICAL_VALIDATION_URL") ||
           sName.equals ("TECHNICAL_VALIDATION_REQUIRED");
  }
}
