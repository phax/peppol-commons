/*
 * Copyright (C) 2015-2023 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.compare.CompareHelper;
import com.helger.commons.compare.IComparator;

/**
 * Marker-interface that is specific for document type identifiers.<br>
 * This can be used as the read-only/immutable counterpart of the implementation
 * class.
 *
 * @author Philip Helger
 */
public interface IDocumentTypeIdentifier extends IIdentifier
{
  /**
   * Check if the passed document type identifier has the same scheme and value
   * as this identifier. <code>equals</code> cannot be used in many cases,
   * because equals also checks if the implementation class is identical which
   * is not always the case.
   *
   * @param aOther
   *        The identifier to compare to. May be <code>null</code>.
   * @return <code>true</code> if the parameter is not <code>null</code> and has
   *         the same scheme and value as this
   */
  default boolean hasSameContent (@Nullable final IDocumentTypeIdentifier aOther)
  {
    // Check value before scheme because the possibility of a divergent value is
    // much higher
    return aOther != null && hasValue (aOther.getValue ()) && hasScheme (aOther.getScheme ());
  }

  default int compareTo (@Nonnull final IDocumentTypeIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  static IComparator <IDocumentTypeIdentifier> comparator ()
  {
    return (a, b) -> a.compareTo (b);
  }
}
