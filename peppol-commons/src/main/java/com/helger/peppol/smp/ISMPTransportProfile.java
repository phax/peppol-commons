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
   * Check if this transport profile is deprecated or not.<br>
   * Since v8.8.3 this method is deprecated in favour of {@link #getState()}
   *
   * @return <code>true</code> if it is deprecated, <code>false</code> if not.
   * @since 5.2.6
   */
  @Deprecated
  default boolean isDeprecated ()
  {
    return getState () == ESMPTransportProfileState.DEPRECATED;
  }

  /**
   * @return The state of the transport profile. May not be <code>null</code>.
   * @since 8.8.3
   */
  @Nonnull
  default ESMPTransportProfileState getState ()
  {
    return ESMPTransportProfileState.ACTIVE;
  }

  /**
   * @return The state ID of the transport profile. May neither be
   *         <code>null</code> nor empty.
   * @since 9.0.1
   */
  @Nonnull
  @Nonempty
  default String getStateID ()
  {
    return getState ().getID ();
  }
}
