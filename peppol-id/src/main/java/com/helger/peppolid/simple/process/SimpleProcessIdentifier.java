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
package com.helger.peppolid.simple.process;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.base.hashcode.IHashCodeGenerator;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.xsds.peppol.id1.ProcessIdentifierType;

/**
 * This is a sanity class around the {@link ProcessIdentifierType} class with easier construction
 * and some sanity access methods. It may be used in all places where {@link ProcessIdentifierType}
 * objects are required.<br>
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class SimpleProcessIdentifier extends ProcessIdentifierType implements
                                     IProcessIdentifier,
                                     IMutableIdentifier,
                                     Comparable <SimpleProcessIdentifier>,
                                     ICloneable <SimpleProcessIdentifier>
{
  private transient int m_nHashCode = IHashCodeGenerator.ILLEGAL_HASHCODE;

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleProcessIdentifier (@NonNull final IProcessIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleProcessIdentifier (@Nullable final String sScheme, @NonNull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@NonNull final SimpleProcessIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @NonNull
  @ReturnsMutableCopy
  public SimpleProcessIdentifier getClone ()
  {
    return new SimpleProcessIdentifier (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    return super.equals (o);
  }

  @Override
  public int hashCode ()
  {
    // Cash for performance reasons
    int ret = m_nHashCode;
    if (ret == IHashCodeGenerator.ILLEGAL_HASHCODE)
      ret = m_nHashCode = super.hashCode ();
    return ret;
  }

  @NonNull
  public static SimpleProcessIdentifier wrap (final com.helger.xsds.peppol.id1.@NonNull ProcessIdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static SimpleProcessIdentifier wrap (final com.helger.xsds.bdxr.smp1.@NonNull ProcessIdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getScheme (), aID.getValue ());
  }

  @NonNull
  public static SimpleProcessIdentifier wrap (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getSchemeID (), aID.getValue ());
  }
}
