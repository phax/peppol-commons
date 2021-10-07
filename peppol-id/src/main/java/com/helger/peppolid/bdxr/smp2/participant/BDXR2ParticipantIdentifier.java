/*
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.peppolid.bdxr.smp2.participant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.DevelopersNote;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.compare.CompareHelper;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.xsds.bdxr.smp2.bc.ParticipantIDType;

/**
 * This is a sanity class around the {@link ParticipantIDType} class with easier
 * construction and some sanity access methods. It may be used in all places
 * where {@link ParticipantIDType} objects are required.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class BDXR2ParticipantIdentifier extends ParticipantIDType implements
                                        IParticipantIdentifier,
                                        IMutableIdentifier,
                                        Comparable <BDXR2ParticipantIdentifier>,
                                        ICloneable <BDXR2ParticipantIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2ParticipantIdentifier (@Nonnull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR2ParticipantIdentifier (@Nullable final String sScheme, @Nonnull final String sValue)
  {
    // Change "" to null
    setSchemeID (StringHelper.hasNoText (sScheme) ? null : sScheme);
    setValue (sValue);
  }

  @Nonnull
  public final String getScheme ()
  {
    return getSchemeID ();
  }

  public final void setScheme (@Nullable final String sScheme)
  {
    setSchemeID (sScheme);
  }

  public int compareTo (@Nonnull final BDXR2ParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public BDXR2ParticipantIdentifier getClone ()
  {
    return new BDXR2ParticipantIdentifier (this);
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
