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
package com.helger.peppol.identifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.compare.CompareHelper;
import com.helger.commons.compare.IComparator;

/**
 * Marker-interface that is specific for participant identifiers.<br>
 * This can be used as the read-only/immutable counterpart of the implementation
 * class.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IParticipantIdentifier extends IIdentifier
{
  /**
   * Check if the passed participant identifier has the same scheme and value as
   * this identifier. <code>equals</code> cannot be used in many cases, because
   * equals also checks if the implementation class is identical which is not
   * always the case.
   *
   * @param aOther
   *        The identifier to compare to. May be <code>null</code>.
   * @return <code>true</code> if the parameter is not <code>null</code> and has
   *         the same scheme and value as this
   */
  default boolean hasSameContent (@Nullable final IParticipantIdentifier aOther)
  {
    // Check value before scheme because the possibility of a divergent value is
    // much higher
    return aOther != null && hasValue (aOther.getValue ()) && hasScheme (aOther.getScheme ());
  }

  default int compareTo (@Nonnull final IParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  static IComparator <IParticipantIdentifier> comparator ()
  {
    return (a, b) -> a.compareTo (b);
  }
}
