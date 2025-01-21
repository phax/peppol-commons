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
package com.helger.peppolid.peppol.pidscheme;

import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import com.helger.peppolid.peppol.EPeppolCodeListItemState;

/**
 * The Peppol specific version of {@link IParticipantIdentifierScheme}.
 *
 * @author Philip Helger
 * @since 9.4.0
 */
public interface IPeppolParticipantIdentifierScheme extends IParticipantIdentifierScheme
{
  /**
   * Get the scheme ID of this issuing agency.<br>
   * Example for GLN <code>"GLN"</code> is returned.<br>
   * Hint: this is NOT the meta scheme to be used!<br>
   * Hint: this value was deprecated in BIS v3 - use the numeric code only.
   *
   * @return The scheme ID of this issuing agency. May neither be
   *         <code>null</code> nor empty.
   * @see #getISO6523Code()
   */
  @Nonnull
  @Nonempty
  String getSchemeID ();

  /**
   * Get the optional name of the agency. This is pure descriptive text without
   * any predefined semantics.
   *
   * @return The optional name of this agency. May be <code>null</code>.
   */
  @Nullable
  String getSchemeAgency ();

  /**
   * Get the ISO-6523 based identifier value.<br>
   * Example: this method returns "0088" for GLN.
   *
   * @return The ISO6523 based identifier of this agency. May neither be
   *         <code>null</code> nor empty. Is currently always numeric, but may
   *         contain characters in the future.
   */
  @Nonnull
  @Nonempty
  String getISO6523Code ();

  /**
   * Get the real participant identifier value for the given local identifier.
   * <br>
   * Example: <code>GLN.createIdentifierValue ("123456")</code> results in
   * <code>0088:123456</code>
   *
   * @param sIdentifier
   *        The local participant identifier to be used.
   * @return The participant identifier value part. Never <code>null</code>.
   * @see #getISO6523Code()
   */
  @Nonnull
  @Nonempty
  default String createIdentifierValue (@Nonnull @Nonempty final String sIdentifier)
  {
    return getISO6523Code () + ":" + sIdentifier;
  }

  /**
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   * @since 8.7.1
   */
  @Nonnull
  Version getInitialRelease ();

  /**
   * @return <code>true</code> if the agency is deprecated and should not be
   *         used any longer, <code>false</code> otherwise.
   */
  default boolean isDeprecated ()
  {
    return getState ().isDeprecated ();
  }

  /**
   * @return The state of the item. Never <code>null</code>.
   * @since 8.7.1
   */
  @Nonnull
  EPeppolCodeListItemState getState ();

  /**
   * Get the version since when this item is deprecated.
   *
   * @return <code>null</code> if this item is not deprecated.
   * @see #getState()
   * @see #isDeprecated()
   * @since 8.7.1
   */
  @Nullable
  Version getDeprecationRelease ();

  /**
   * @return <code>true</code> if this item has a removal date,
   *         <code>false</code> if not.
   * @since 8.7.1
   */
  default boolean hasRemovalDate ()
  {
    return getRemovalDate () != null;
  }

  /**
   * Get the date, when this particular entry will be removed. This may be set,
   * even if the state is not "removed". This date may be in the future.
   *
   * @return <code>null</code> if no removal date is scheduled yet.
   * @since 8.7.1
   */
  @Nullable
  LocalDate getRemovalDate ();
}
