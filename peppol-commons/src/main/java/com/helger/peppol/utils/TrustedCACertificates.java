/*
 * Copyright (C) 2015-2024 Philip Helger
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

import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.string.ToStringGenerator;
import com.helger.security.certificate.CertificateHelper;

/**
 * Manages a list of trusted CA certificates.
 *
 * @author Philip Helger
 * @since 9.4.0
 */
public class TrustedCACertificates
{
  private static final Logger LOGGER = LoggerFactory.getLogger (TrustedCACertificates.class);

  private final ICommonsList <X509Certificate> m_aCerts = new CommonsArrayList <> ();
  private final ICommonsSet <X500Principal> m_aIssuers = new CommonsHashSet <> ();

  public TrustedCACertificates ()
  {}

  /**
   * Register a trusted CA Certificate
   *
   * @param aCert
   *        The CA certificate to be added. May not be <code>null</code>.
   * @throws IllegalArgumentException
   *         If the provided certificate is already trusted
   */
  public void addTrustedCACertificate (@Nonnull final X509Certificate aCert)
  {
    ValueEnforcer.notNull (aCert, "Certificate");

    if (!CertificateHelper.isCA (aCert))
      throw new IllegalArgumentException ("The provided certificate does not seem to be a CA: " + aCert);

    if (m_aCerts.contains (aCert))
      throw new IllegalArgumentException ("Certificate is already trusted as a CA: " + aCert);

    m_aCerts.add (aCert);
    m_aIssuers.add (aCert.getSubjectX500Principal ());
  }

  /**
   * Explicitly remove all known trusted CA certificates so that different ones
   * can be added. Handle this with care!
   */
  public void clearTrustedCACertificates ()
  {
    if (!m_aCerts.isEmpty ())
    {
      LOGGER.warn ("Explicitly removing all " + m_aCerts.size () + " entries from the list of trusted CA certificates");
      m_aCerts.clear ();
      m_aIssuers.clear ();
    }
  }

  /**
   * @return All the CA certificates currently contained. Never
   *         <code>null</code>.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <X509Certificate> getAllTrustedCACertificates ()
  {
    return m_aCerts.getClone ();
  }

  /**
   * @return All the CA issuers currently valid. Neither <code>null</code> nor
   *         empty.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsSet <X500Principal> getAllTrustedCAIssuers ()
  {
    return m_aIssuers.getClone ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Certs#", m_aCerts.size ())
                                       .append ("Issuers", m_aIssuers)
                                       .getToString ();
  }
}
