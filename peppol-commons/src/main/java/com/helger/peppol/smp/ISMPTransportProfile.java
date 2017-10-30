/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smp;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.name.IHasName;
import com.helger.commons.type.ITypedObject;

/**
 * Base interface for SMP transport profiles. Two transport profiles are
 * considered equal if the IDs are equal. The name of a transport profile has no
 * semantic meaning and is only present for interpretation by humans.
 *
 * @author Philip Helger
 * @see ESMPTransportProfile for a set of predefined transport profiles
 */
@MustImplementEqualsAndHashcode
public interface ISMPTransportProfile extends ITypedObject <String>, IHasName, Serializable
{
  /**
   * Get the ID to be stored in an SMP endpoint.
   */
  @Nonnull
  @Nonempty
  String getID ();

  /**
   * The display name of this transport profile has no semantics and is just for
   * informational purposes. May neither be <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  String getName ();

  /**
   * Check if this transport profile is deprecated or not.
   *
   * @return <code>true</code> if it is deprecated, <code>false</code> if not.
   * @since 5.2.6
   */
  default boolean isDeprecated ()
  {
    return false;
  }
}
