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
package com.helger.peppolid.peppol.enduser;

import java.util.function.UnaryOperator;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringParser;
import com.helger.base.tostring.ToStringGenerator;

/**
 * The default implementation of {@link IPeppolEndUserIDMapping}, based on the source ISO6523 code,
 * the target ISO6523 code and a function to convert the local participant identifier value.
 * <p>
 * Note: this class does not implement <code>equals</code> and <code>hashCode</code>, because the
 * contained value mapper cannot be compared in a meaningful way.
 * </p>
 *
 * @author Philip Helger
 * @since 12.8.1
 */
@Immutable
public class PeppolEndUserIDMapping implements IPeppolEndUserIDMapping
{
  /** The number of characters of an ISO6523 code in a Peppol participant identifier */
  public static final int ISO6523_CODE_LENGTH = 4;

  private final String m_sSourceISO6523Code;
  private final String m_sTargetISO6523Code;
  private final UnaryOperator <String> m_aValueMapper;

  @NonNull
  @Nonempty
  private static String _checkISO6523Code (@NonNull @Nonempty final String sCode, @NonNull final String sName)
  {
    ValueEnforcer.notEmpty (sCode, sName);
    ValueEnforcer.isTrue (sCode.length () == ISO6523_CODE_LENGTH && StringParser.isUnsignedInt (sCode),
                          () -> "The " + sName + " '" + sCode + "' is not a valid ISO6523 code");
    return sCode;
  }

  /**
   * Constructor.
   *
   * @param sSourceISO6523Code
   *        The ISO6523 code of the issuing agency this mapping applies to. Must be a 4 digit value.
   * @param sTargetISO6523Code
   *        The ISO6523 code of the issuing agency to map to. Must be a 4 digit value.
   * @param aValueMapper
   *        The function to convert the local participant identifier value. It may return
   *        <code>null</code> or an empty String to indicate, that this mapping is not applicable
   *        for a certain value. May not be <code>null</code>.
   */
  public PeppolEndUserIDMapping (@NonNull @Nonempty final String sSourceISO6523Code,
                                 @NonNull @Nonempty final String sTargetISO6523Code,
                                 @NonNull final UnaryOperator <String> aValueMapper)
  {
    m_sSourceISO6523Code = _checkISO6523Code (sSourceISO6523Code, "SourceISO6523Code");
    m_sTargetISO6523Code = _checkISO6523Code (sTargetISO6523Code, "TargetISO6523Code");
    m_aValueMapper = ValueEnforcer.notNull (aValueMapper, "ValueMapper");
  }

  @NonNull
  @Nonempty
  public String getSourceISO6523Code ()
  {
    return m_sSourceISO6523Code;
  }

  @Nullable
  public String getMappedValue (@NonNull @Nonempty final String sSourceValue)
  {
    final String ret = m_aValueMapper.apply (sSourceValue);
    if (ret == null)
      return null;
    return m_sTargetISO6523Code + ":" + ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SourceISO6523Code", m_sSourceISO6523Code)
                                       .append ("TargetISO6523Code", m_sTargetISO6523Code)
                                       .append ("ValueMapper", m_aValueMapper)
                                       .getToString ();
  }

  /**
   * Create a mapping that uses the local participant identifier value unchanged.
   *
   * @param sSourceISO6523Code
   *        The ISO6523 code of the issuing agency this mapping applies to. Must be a 4 digit value.
   * @param sTargetISO6523Code
   *        The ISO6523 code of the issuing agency to map to. Must be a 4 digit value.
   * @return Never <code>null</code>.
   */
  @NonNull
  public static PeppolEndUserIDMapping createValueUnchanged (@NonNull @Nonempty final String sSourceISO6523Code,
                                                             @NonNull @Nonempty final String sTargetISO6523Code)
  {
    return new PeppolEndUserIDMapping (sSourceISO6523Code, sTargetISO6523Code, UnaryOperator.identity ());
  }

  /**
   * Create a mapping that removes a leading prefix (like a country code) from the local participant
   * identifier value. If the value does not start with the provided prefix, the value is used
   * unchanged.
   *
   * @param sSourceISO6523Code
   *        The ISO6523 code of the issuing agency this mapping applies to. Must be a 4 digit value.
   * @param sTargetISO6523Code
   *        The ISO6523 code of the issuing agency to map to. Must be a 4 digit value.
   * @param sPrefix
   *        The prefix to be removed, case insensitive. May neither be <code>null</code> nor empty.
   * @return Never <code>null</code>.
   */
  @NonNull
  public static PeppolEndUserIDMapping createValueWithoutPrefix (@NonNull @Nonempty final String sSourceISO6523Code,
                                                                 @NonNull @Nonempty final String sTargetISO6523Code,
                                                                 @NonNull @Nonempty final String sPrefix)
  {
    ValueEnforcer.notEmpty (sPrefix, "Prefix");
    return new PeppolEndUserIDMapping (sSourceISO6523Code, sTargetISO6523Code, sValue -> {
      final String sRest = StringHelper.startsWithIgnoreCase (sValue, sPrefix) ? sValue.substring (sPrefix.length ())
                                                                               : sValue;
      // Don't map to an empty value
      return StringHelper.isEmpty (sRest) ? null : sRest;
    });
  }
}
