/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.peppol.pidscheme;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.factory.PeppolIdentifierFactory;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;

/**
 * Base interface for a single Participant identifier scheme.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IParticipantIdentifierScheme extends Serializable
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
   * Get the real participant identifier for the given local identifier.<br>
   * Example: <code>GLN.createParticipantIdentifier ("123456")</code> results in
   * the wrapped object for <code>iso6523-actorid-upis::0088:123456</code>
   *
   * @param sIdentifier
   *        The local participant identifier to be used.
   * @return The participant identifier. Never <code>null</code>.
   */
  @Nonnull
  default PeppolParticipantIdentifier createParticipantIdentifier (@Nonnull @Nonempty final String sIdentifier)
  {
    return PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (createIdentifierValue (sIdentifier));
  }

  /**
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   */
  @Nonnull
  Version getSince ();

  /**
   * @return <code>true</code> if the agency is deprecated and should not be
   *         used any longer, <code>false</code> otherwise.
   */
  boolean isDeprecated ();
}
