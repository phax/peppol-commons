/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.peppolid.peppol.enduser;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;

/**
 * Base interface for a single End User ID mapping rule, as used by {@link PeppolEndUserHelper}.
 * <p>
 * A mapping rule translates the participant identifiers of one Peppol issuing agency (identified by
 * its ISO6523 code) to the participant identifiers of another issuing agency. That is needed,
 * because several countries have multiple identifier schemes running in parallel that all identify
 * the same End User - e.g. in Belgium the schemes <code>0208</code> and <code>9925</code>. Without
 * such a mapping, a single End User would be counted multiple times.
 * </p>
 * <p>
 * Mapping rules only apply to participant identifiers using the default participant identifier
 * scheme <code>iso6523-actorid-upis</code>, because only for those the ISO6523 code is known.
 * </p>
 *
 * @author Philip Helger
 * @since 12.8.1
 */
public interface IPeppolEndUserIDMapping
{
  /**
   * @return The ISO6523 code of the issuing agency this mapping rule applies to. E.g.
   *         <code>9925</code> for Belgian VAT numbers. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  String getSourceISO6523Code ();

  /**
   * Get the identifier value to be used with the target issuing agency.
   *
   * @param sSourceValue
   *        The local participant identifier value of the source identifier. That is the part after
   *        the ISO6523 code and the colon. Neither <code>null</code> nor empty. For case
   *        insensitive identifier schemes, the value is already unified (lower cased).
   * @return <code>null</code> or an empty String, if this mapping rule is not applicable for the
   *         provided value. In that case the source identifier is left unchanged. The ISO6523 code
   *         of the issuing agency, the identifiers are mapped to and the local participant
   *         identifier value otherwise.
   */
  @Nullable
  String getMappedValue (@NonNull @Nonempty String sSourceValue);
}
