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
package com.helger.peppolid;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.base.compare.CompareHelper;
import com.helger.base.compare.IComparator;

/**
 * Marker-interface that is specific for participant identifiers.<br>
 * This can be used as the read-only/immutable counterpart of the implementation
 * class.
 *
 * @author Philip Helger
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

  default int compareTo (@NonNull final IParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @NonNull
  static IComparator <IParticipantIdentifier> comparator ()
  {
    return (a, b) -> a.compareTo (b);
  }
}
