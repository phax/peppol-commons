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
package com.helger.peppolid.bdxr.smp1;

import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.string.StringHelper;
import com.helger.base.url.URLHelper;

/**
 * Helper methods for OASIS BDXR SMP v1 identifiers.
 *
 * @author Philip Helger
 */
@Immutable
public final class BDXR1IdentifierHelper
{
  @PresentForCodeCoverage
  private static final BDXR1IdentifierHelper INSTANCE = new BDXR1IdentifierHelper ();

  private BDXR1IdentifierHelper ()
  {}

  /**
   * Check if the given identifier is valid. It is valid if it is empty or a
   * valid URI.<br>
   * The scheme of the participant identifier MUST be in the form of a URI.<br>
   * The scheme of the document identifier MUST be in the form of a URI.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   */
  public static boolean isValidIdentifierScheme (@Nullable final String sScheme)
  {
    if (StringHelper.isEmpty (sScheme))
      return true;
    return URLHelper.getAsURI (sScheme) != null;
  }

  /**
   * Check if an identifier value is valid. Currently this check always returns
   * true.
   *
   * @param sValue
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the passed value is valid, <code>false</code>
   *         otherwise.
   */
  public static boolean isValidIdentifierValue (@Nullable final String sValue)
  {
    return true;
  }
}
