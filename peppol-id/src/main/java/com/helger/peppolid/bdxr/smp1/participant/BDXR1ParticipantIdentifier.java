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
package com.helger.peppolid.bdxr.smp1.participant;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.misc.DevelopersNote;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.base.compare.CompareHelper;
import com.helger.base.string.StringHelper;
import com.helger.peppolid.IMutableIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.xsds.bdxr.smp1.ParticipantIdentifierType;

/**
 * This is a sanity class around the {@link ParticipantIdentifierType} class
 * with easier construction and some sanity access methods. It may be used in
 * all places where {@link ParticipantIdentifierType} objects are required.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class BDXR1ParticipantIdentifier extends ParticipantIdentifierType implements
                                        IParticipantIdentifier,
                                        IMutableIdentifier,
                                        Comparable <BDXR1ParticipantIdentifier>,
                                        ICloneable <BDXR1ParticipantIdentifier>
{
  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR1ParticipantIdentifier (@NonNull final IParticipantIdentifier aIdentifier)
  {
    this (aIdentifier.getScheme (), aIdentifier.getValue ());
  }

  @DevelopersNote ("Don't invoke manually. Always use the IdentifierFactory!")
  public BDXR1ParticipantIdentifier (@Nullable final String sScheme, @NonNull final String sValue)
  {
    // Change "" to null
    setScheme (StringHelper.isEmpty (sScheme) ? null : sScheme);
    setValue (sValue);
  }

  public int compareTo (@NonNull final BDXR1ParticipantIdentifier aOther)
  {
    int ret = CompareHelper.compare (getScheme (), aOther.getScheme ());
    if (ret == 0)
      ret = CompareHelper.compare (getValue (), aOther.getValue ());
    return ret;
  }

  @NonNull
  @ReturnsMutableCopy
  public BDXR1ParticipantIdentifier getClone ()
  {
    return new BDXR1ParticipantIdentifier (this);
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
