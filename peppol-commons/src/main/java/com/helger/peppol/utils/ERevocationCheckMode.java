/*
 * Copyright (C) 2015-2022 Philip Helger
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

import java.security.cert.PKIXRevocationChecker;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * An enum that defines the revocation checking modes.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
public enum ERevocationCheckMode
{
  /**
   * Try OCSP before CRL.
   */
  OCSP_BEFORE_CRL (true, true, new HashSet <> ()),
  /**
   * Try OCSP but don't try CRL.
   */
  OCSP (true, false, EnumSet.of (PKIXRevocationChecker.Option.NO_FALLBACK)),
  /**
   * Try CRL before OCSP.
   */
  CRL_BEFORE_OCSP (true, true, EnumSet.of (PKIXRevocationChecker.Option.PREFER_CRLS)),
  /**
   * Try CRL but don't try OCSP.
   */
  CRL (false, true, EnumSet.of (PKIXRevocationChecker.Option.PREFER_CRLS, PKIXRevocationChecker.Option.NO_FALLBACK)),
  /**
   * Don't do remote checking.
   */
  NONE (false, false, new HashSet <> ());

  private final boolean m_bOCSP;
  private final boolean m_bCRL;
  private final Set <PKIXRevocationChecker.Option> m_aOptions;

  ERevocationCheckMode (final boolean bOCSP, final boolean bCRL, @Nonnull final Set <PKIXRevocationChecker.Option> aOptions)
  {
    m_bOCSP = bOCSP;
    m_bCRL = bCRL;
    m_aOptions = aOptions;
  }

  public boolean isOCSP ()
  {
    return m_bOCSP;
  }

  public boolean isCRL ()
  {
    return m_bCRL;
  }

  public boolean isOnlyOne ()
  {
    return (m_bOCSP && !m_bCRL) || (m_bCRL && !m_bOCSP);
  }

  public boolean isNone ()
  {
    return !m_bOCSP && !m_bCRL;
  }

  public void addAllOptionsTo (@Nonnull final Collection <? super PKIXRevocationChecker.Option> aTarget)
  {
    aTarget.addAll (m_aOptions);
  }
}
