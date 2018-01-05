/**
 * Copyright (C) 2015-2018 Philip Helger
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
package com.helger.peppol.testfiles;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.error.level.EErrorLevel;
import com.helger.commons.error.level.IErrorLevel;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

public final class ErrorDefinition implements Comparable <ErrorDefinition>
{
  private final IErrorLevel m_aLevel;
  private final String m_sErrorCode;

  private ErrorDefinition (@Nonnull final IErrorLevel aLevel, @Nonnull @Nonempty final String sErrorCode)
  {
    ValueEnforcer.notNull (aLevel, "Level");
    ValueEnforcer.notEmpty (sErrorCode, "ErrorCode");
    m_aLevel = aLevel;
    m_sErrorCode = sErrorCode;
  }

  @Nonnull
  public IErrorLevel getLevel ()
  {
    return m_aLevel;
  }

  @Nonnull
  @Nonempty
  public String getErrorCode ()
  {
    return m_sErrorCode;
  }

  public int compareTo (@Nonnull final ErrorDefinition rhs)
  {
    int i = m_aLevel.getNumericLevel () - rhs.m_aLevel.getNumericLevel ();
    if (i == 0)
      i = m_sErrorCode.compareTo (rhs.m_sErrorCode);
    return i;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ErrorDefinition rhs = (ErrorDefinition) o;
    return m_aLevel.equals (rhs.m_aLevel) && m_sErrorCode.equals (rhs.m_sErrorCode);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aLevel).append (m_sErrorCode).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("level", m_aLevel).append ("errorCode", m_sErrorCode).getToString ();
  }

  @Nonnull
  public static ErrorDefinition createWarning (@Nonnull @Nonempty final String sErrorCode)
  {
    return new ErrorDefinition (EErrorLevel.WARN, sErrorCode);
  }

  @Nonnull
  public static ErrorDefinition createError (@Nonnull @Nonempty final String sErrorCode)
  {
    return new ErrorDefinition (EErrorLevel.FATAL_ERROR, sErrorCode);
  }
}
