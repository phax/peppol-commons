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
package com.helger.peppolid.peppol.process;

import javax.annotation.Nonnull;

import com.helger.commons.version.Version;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.peppol.IPeppolIdentifier;

/**
 * Base interface for predefined process identifiers.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IPeppolPredefinedProcessIdentifier extends IProcessIdentifier, IPeppolIdentifier
{
  default boolean hasDefaultScheme ()
  {
    return true;
  }

  /**
   * @return The ID of the corresponding PEPPOL BIS.
   */
  @Nonnull
  String getBISID ();

  /**
   * @return The {@link PeppolProcessIdentifier} version of this predefined
   *         process identifier.
   */
  @Nonnull
  PeppolProcessIdentifier getAsProcessIdentifier ();

  /**
   * @return The internal code list version in which the identifier was added.
   *         Never <code>null</code>.
   */
  @Nonnull
  Version getSince ();

  /**
   * @return <code>true</code> if this identifier is deprecated and should no
   *         longer be used, <code>false</code> if not.
   * @since 7.0.0
   */
  boolean isDeprecated ();
}
