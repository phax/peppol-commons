/**
 * Copyright (C) 2015-2020 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IProcessIdentifier;
import com.helger.peppolid.ProcessIdentifierType;

/**
 * This is a sanity class around the {@link ProcessIdentifierType} class with
 * easier construction and some sanity access methods. It may be used in all
 * places where {@link ProcessIdentifierType} objects are required.<br>
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
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleProcessIdentifier (@Nonnull final IProcessIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public SimpleProcessIdentifier (@Nonnull final String sScheme, @Nonnull final String sValue)
  {
    setScheme (sScheme);
    setValue (sValue);
  }

  public int compareTo (@Nonnull final SimpleProcessIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public SimpleProcessIdentifier getClone ()
  {
    return new SimpleProcessIdentifier (this);
  }

  @Nonnull
  public static SimpleProcessIdentifier wrap (@Nonnull final com.helger.peppolid.ProcessIdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static SimpleProcessIdentifier wrap (@Nonnull final com.helger.xsds.bdxr.smp1.ProcessIdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getScheme (), aID.getValue ());
  }

  @Nonnull
  public static SimpleProcessIdentifier wrap (@Nonnull final com.helger.xsds.ccts.cct.schemamodule.IdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getSchemeID (), aID.getValue ());
  }
}
