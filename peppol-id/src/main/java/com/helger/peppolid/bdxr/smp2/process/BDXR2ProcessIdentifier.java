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
package com.helger.peppolid.bdxr.smp2.process;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.base.string.StringHelper;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.xsds.bdxr.smp2.bc.IDType;

/**
 * This is a sanity class around the {@link IDType} class with easier
 * construction and some sanity access methods. It may be used in all places
 * where {@link IDType} objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class BDXR2ProcessIdentifier extends IDType implements
                                    IProcessIdentifier,
                                    IMutableIdentifier,
                                    Comparable <BDXR2ProcessIdentifier>,
                                    ICloneable <BDXR2ProcessIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2ProcessIdentifier (@NonNull final IProcessIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2ProcessIdentifier (@Nullable final String sScheme, @NonNull final String sValue)
  {
    // Change "" to null
    setSchemeID (StringHelper.isEmpty (sScheme) ? null : sScheme);
    setValue (sValue);
  }

  @NonNull
  public final String getScheme ()
  {
    return getSchemeID ();
  }

  public final void setScheme (@Nullable final String sScheme)
  {
    setSchemeID (sScheme);
  }

  public int compareTo (@NonNull final BDXR2ProcessIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @NonNull
  @ReturnsMutableCopy
  public BDXR2ProcessIdentifier getClone ()
  {
    return new BDXR2ProcessIdentifier (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    // for compareTo
    return super.equals (o);
  }

  @Override
  public int hashCode ()
  {
    // for compareTo
    return super.hashCode ();
  }
}
