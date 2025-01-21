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
package com.helger.peppol.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.expiration.IExpirable;

/**
 * An object that can expire.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The data type of the object that can expire.
 * @since 9.3.0
 */
public final class ExpiringObject <DATATYPE> implements IExpirable
{
  private final DATATYPE m_aObj;
  private final LocalDateTime m_aExpirationDT;

  public ExpiringObject (@Nullable final DATATYPE aObj, @Nonnull final LocalDateTime aExpirationDT)
  {
    ValueEnforcer.notNull (aExpirationDT, "ExpirationDT");
    m_aObj = aObj;
    m_aExpirationDT = aExpirationDT;
  }

  /**
   * @return The object it is all about. May be <code>null</code>.
   */
  @Nullable
  public DATATYPE getObject ()
  {
    return m_aObj;
  }

  @Nonnull
  public LocalDateTime getExpirationDateTime ()
  {
    return m_aExpirationDT;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Object", m_aObj)
                                       .append ("ExpirationDT", m_aExpirationDT)
                                       .getToString ();
  }

  @Nonnull
  public static <DATATYPE> ExpiringObject <DATATYPE> ofDuration (@Nullable final DATATYPE aObj,
                                                                 @Nonnull final Duration aValidityDuration)
  {
    ValueEnforcer.notNull (aValidityDuration, "ValidityDuration");
    ValueEnforcer.isFalse (aValidityDuration::isNegative, "ValidityDuration must not be negative");

    return new ExpiringObject <> (aObj, PDTFactory.getCurrentLocalDateTime ().plus (aValidityDuration));
  }
}
