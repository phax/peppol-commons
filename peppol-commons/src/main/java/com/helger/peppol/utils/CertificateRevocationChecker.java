/*
 * Copyright (C) 2015-2023 Philip Helger
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

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.builder.IBuilder;
import com.helger.commons.callback.IThrowingRunnable;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;
import com.helger.commons.timing.StopWatch;
import com.helger.commons.traits.IGenericImplTrait;

/**
 * A generic class to check certificates against OCSP and CRL servers.
 *
 * @author Philip Helger
 * @since 8.5.2
 */
@ThreadSafe
public final class CertificateRevocationChecker
{
  // By default only OCSP is used
  public static final ERevocationCheckMode DEFAULT_REVOCATION_CHECK_MODE = ERevocationCheckMode.OCSP;
  public static final boolean DEFAULT_ALLOW_SOFT_FAIL = false;
  public static final boolean DEFAULT_ALLOW_EXEC_SYNC = true;

  private static final Logger LOGGER = LoggerFactory.getLogger (CertificateRevocationChecker.class);

  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static ERevocationCheckMode s_eRevocationCheckMode = DEFAULT_REVOCATION_CHECK_MODE;
  @GuardedBy ("RW_LOCK")
  private static Consumer <? super GeneralSecurityException> s_aExceptionHdl = ex -> LOGGER.warn ("Certificate is revoked",
                                                                                                  ex);
  private static final AtomicBoolean ALLOW_SOFT_FAIL = new AtomicBoolean (DEFAULT_ALLOW_SOFT_FAIL);
  @GuardedBy ("RW_LOCK")
  private static Consumer <? super List <? extends CertPathValidatorException>> s_aSoftFailExceptionHdl = exs -> LOGGER.warn ("Certificate revocation check succeeded but has messages: " +
                                                                                                                              exs);
  private static final AtomicBoolean ALLOW_EXEC_SYNC = new AtomicBoolean (DEFAULT_ALLOW_EXEC_SYNC);

  private CertificateRevocationChecker ()
  {}

  /**
   * @return The global revocation check mode. Never <code>null</code>. The
   *         default is {@link ERevocationCheckMode#OCSP}.
   */
  @Nonnull
  public static ERevocationCheckMode getRevocationCheckMode ()
  {
    return RW_LOCK.readLockedGet ( () -> s_eRevocationCheckMode);
  }

  /**
   * Set the global revocation check mode to use, if no specific mode was
   * provided.
   *
   * @param eRevocationCheckMode
   *        The global revocation check mode to use. May not be
   *        <code>null</code>.
   */
  public static void setRevocationCheckMode (@Nonnull final ERevocationCheckMode eRevocationCheckMode)
  {
    ValueEnforcer.notNull (eRevocationCheckMode, "RevocationCheckMode");
    RW_LOCK.writeLocked ( () -> s_eRevocationCheckMode = eRevocationCheckMode);
    LOGGER.info ("Global CertificateRevocationChecker revocation mode was set to: " + eRevocationCheckMode);
  }

  /**
   * @return The exception handler to be invoked in case of an exception in
   *         certificate checking.
   */
  @Nonnull
  public static Consumer <? super GeneralSecurityException> getExceptionHdl ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aExceptionHdl);
  }

  /**
   * Set the exception handler to be invoked, if certificate checking throws an
   * exception.
   *
   * @param aExceptionHdl
   *        The exception handler to be used. May not be <code>null</code>.
   */
  public static void setExceptionHdl (@Nonnull final Consumer <? super GeneralSecurityException> aExceptionHdl)
  {
    ValueEnforcer.notNull (aExceptionHdl, "ExceptionHdl");
    RW_LOCK.writeLocked ( () -> s_aExceptionHdl = aExceptionHdl);
  }

  /**
   * Allow revocation check to succeed if the revocation status cannot be
   * determined for one of the following reasons:
   * <ul>
   * <li>The CRL or OCSP response cannot be obtained because of a network
   * error.</li>
   * <li>The OCSP responder returns one of the following errors specified in
   * section 2.3 of RFC 2560: internalError or tryLater.</li>
   * </ul>
   * Note that these conditions apply to both OCSP and CRLs, and unless the
   * NO_FALLBACK option is set, the revocation check is allowed to succeed only
   * if both mechanisms fail under one of the conditions as stated
   * above.Exceptions that cause the network errors are ignored but can be later
   * retrieved by calling the getSoftFailExceptions method.
   *
   * @return <code>true</code> if soft fail is enabled, <code>false</code> if
   *         not. Default is defined by {@link #DEFAULT_ALLOW_SOFT_FAIL}.
   */
  public static boolean isAllowSoftFail ()
  {
    return ALLOW_SOFT_FAIL.get ();
  }

  /**
   * Set enable "soft fail" mode.
   *
   * @param bAllow
   *        <code>true</code> to allow it, <code>false</code> to disallow it.
   */
  public static void setAllowSoftFail (final boolean bAllow)
  {
    ALLOW_SOFT_FAIL.set (bAllow);
    LOGGER.info ("Global CertificateRevocationChecker allows for soft fail: " + bAllow);
  }

  /**
   * @return The handler to be invoked in case of failures in certificate
   *         checking.
   * @see #isAllowSoftFail()
   */
  @Nonnull
  public static Consumer <? super List <CertPathValidatorException>> getSoftFailExceptionHdl ()
  {
    return RW_LOCK.readLockedGet ( () -> s_aSoftFailExceptionHdl);
  }

  /**
   * Set the handler to be invoked, if certificate checking has soft failures.
   *
   * @param aSoftFailExceptionHdl
   *        The handler to be used. May not be <code>null</code>.
   * @see #isAllowSoftFail()
   */
  public static void setSoftFailExceptionHdl (@Nonnull final Consumer <? super List <? extends CertPathValidatorException>> aSoftFailExceptionHdl)
  {
    ValueEnforcer.notNull (aSoftFailExceptionHdl, "SoftFailExceptionHdl");
    RW_LOCK.writeLocked ( () -> s_aSoftFailExceptionHdl = aSoftFailExceptionHdl);
  }

  /**
   * @return <code>true</code> if the revocation check should be performed in a
   *         <code>synchronized</code> block, <code>false</code> if not. Default
   *         is defined by {@link #DEFAULT_ALLOW_EXEC_SYNC}.
   */
  public static boolean isExecuteInSynchronizedBlock ()
  {
    return ALLOW_EXEC_SYNC.get ();
  }

  /**
   * Enable or disable the execution of the revocation check in a
   * <code>synchronized</code> block.
   *
   * @param bExecSync
   *        <code>true</code> to use the synchronized block, <code>false</code>
   *        to run it unsynchronized.
   */
  public static void setExecuteInSynchronizedBlock (final boolean bExecSync)
  {
    ALLOW_EXEC_SYNC.set (bExecSync);
    LOGGER.info ("Global CertificateRevocationChecker is executed synchronously: " + bExecSync);
  }

  /**
   * @return A new {@link RevocationCheckBuilder} instance.
   */
  public static RevocationCheckBuilder revocationCheck ()
  {
    return new RevocationCheckBuilder ();
  }

  /**
   * A utility class to configure the revocation check in a fine grained way.
   * This class does NOT use any caching, so it's up to the caller to do that
   * caching.
   *
   * @param <IMPLTYPE>
   *        Implementation type
   * @author Philip Helger
   */
  @NotThreadSafe
  public abstract static class AbstractRevocationCheckBuilder <IMPLTYPE extends AbstractRevocationCheckBuilder <IMPLTYPE>>
                                                              implements
                                                              IBuilder <ERevoked>,
                                                              IGenericImplTrait <IMPLTYPE>
  {
    public static final Duration DEFAULT_EXECUTION_WARN_DURATION = Duration.ofMillis (500);
    public static final Duration DEFAULT_CACHING_DURATION = CRLHelper.DEFAULT_CACHING_DURATION;

    private X509Certificate m_aCert;
    private final ICommonsList <X509Certificate> m_aValidCAs = new CommonsArrayList <> ();
    private Date m_aCheckDate;
    private ERevocationCheckMode m_eCheckMode;
    private Consumer <? super GeneralSecurityException> m_aExceptionHdl;
    private ETriState m_eAllowSoftFail = ETriState.UNDEFINED;
    private Consumer <? super List <CertPathValidatorException>> m_aSoftFailExceptionHdl;
    private ETriState m_eExecuteInSynchronizedBlock = ETriState.UNDEFINED;
    private Duration m_aExecutionDurationWarn = DEFAULT_EXECUTION_WARN_DURATION;
    private Duration m_aCRLCachingDuration = DEFAULT_CACHING_DURATION;

    public AbstractRevocationCheckBuilder ()
    {}

    /**
     * @return The certificate to be checked. May be <code>null</code>.
     */
    @Nullable
    public final X509Certificate certificate ()
    {
      return m_aCert;
    }

    /**
     * Set the certificate to be checked.
     *
     * @param a
     *        The certificate to be checked. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE certificate (@Nullable final X509Certificate a)
    {
      m_aCert = a;
      return thisAsT ();
    }

    /**
     * Set a valid CA to be checked against.
     *
     * @param a
     *        The CA certificate to be checked against. May be
     *        <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE validCA (@Nullable final X509Certificate a)
    {
      m_aValidCAs.set (a);
      return thisAsT ();
    }

    /**
     * Set the valid CAs to be checked against.
     *
     * @param a
     *        The list of CA certificates to be checked against. May be
     *        <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE validCAs (@Nullable final Iterable <? extends X509Certificate> a)
    {
      m_aValidCAs.setAll (a);
      return thisAsT ();
    }

    /**
     * Set the valid CAs to be checked against from the provided trust store.
     *
     * @param aTrustStore
     *        The trust store to be checked against. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE validCAs (@Nullable final KeyStore aTrustStore)
    {
      final ICommonsSet <X509Certificate> aCerts = new CommonsHashSet <> ();
      if (aTrustStore != null)
      {
        try
        {
          final Enumeration <String> aAliases = aTrustStore.aliases ();
          while (aAliases.hasMoreElements ())
          {
            final String alias = aAliases.nextElement ();
            if (aTrustStore.isCertificateEntry (alias))
            {
              final Certificate cert = aTrustStore.getCertificate (alias);
              if (cert instanceof X509Certificate)
                aCerts.add ((X509Certificate) cert);
            }
          }
        }
        catch (final KeyStoreException ex)
        {
          LOGGER.warn ("Failed to extract certificates from trust store", ex);
        }
      }
      return validCAs (aCerts);
    }

    /**
     * Add a valid CA to be checked against.
     *
     * @param a
     *        A CA certificate to be checked against. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE addValidCA (@Nullable final X509Certificate a)
    {
      if (a != null)
        m_aValidCAs.add (a);
      return thisAsT ();
    }

    /**
     * Add valid CAs to be checked against.
     *
     * @param a
     *        A CA certificates to be checked against. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE addValidCAs (@Nullable final Iterable <? extends X509Certificate> a)
    {
      m_aValidCAs.addAll (a);
      return thisAsT ();
    }

    /**
     * @return The current check dates. May be <code>null</code> to indicate
     *         "current date and time".
     */
    @Nullable
    public final Date checkDate ()
    {
      return m_aCheckDate;
    }

    /**
     * Set the date of check for the certificate to the "current date and time"
     * (which is the default).
     *
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE checkDateNow ()
    {
      return checkDate ((Date) null);
    }

    /**
     * Set the date of check for the certificate. May be <code>null</code> to
     * indicate "use the current date time".
     *
     * @param a
     *        The date to check at. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE checkDate (@Nullable final LocalDateTime a)
    {
      return checkDate (a == null ? null : PDTFactory.createDate (a));
    }

    /**
     * Set the date of check for the certificate. May be <code>null</code> to
     * indicate "use the current date time".
     *
     * @param a
     *        The date to check at. May be <code>null</code>.
     * @return thisAsT () for chaining
     */
    @Nonnull
    public final IMPLTYPE checkDate (@Nullable final OffsetDateTime a)
    {
      return checkDate (a == null ? null : PDTFactory.createDate (a));
    }

    /**
     * Set the date of check for the certificate. May be <code>null</code> to
     * indicate "use the current date time".
     *
     * @param a
     *        The date to check at. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE checkDate (@Nullable final ZonedDateTime a)
    {
      return checkDate (a == null ? null : PDTFactory.createDate (a));
    }

    /**
     * Set the date of check for the certificate. May be <code>null</code> to
     * indicate "use the current date time".
     *
     * @param a
     *        The date to check at. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE checkDate (@Nullable final Date a)
    {
      m_aCheckDate = a;
      return thisAsT ();
    }

    /**
     * Set the revocation check mode to use. If this parameter is not set, the
     * global default value is used.
     *
     * @param e
     *        The revocation check mode to use. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE checkMode (@Nullable final ERevocationCheckMode e)
    {
      m_eCheckMode = e;
      return thisAsT ();
    }

    /**
     * Set the the handler to be called if a certificate is indicated as
     * "revoked". If it is not set, the global exception handler is used.
     *
     * @param a
     *        The exception handler to be called. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE exceptionHandler (@Nullable final Consumer <? super GeneralSecurityException> a)
    {
      m_aExceptionHdl = a;
      return thisAsT ();
    }

    /**
     * Enable or disable the usage of "soft fail". If this method is not set,
     * the global setting is used.
     *
     * @param b
     *        <code>true</code> to enable it, <code>false</code> to disable it.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE allowSoftFail (final boolean b)
    {
      m_eAllowSoftFail = ETriState.valueOf (b);
      return thisAsT ();
    }

    /**
     * Use the global setting for "allow soft fail".
     *
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE allowSoftFailUndefined ()
    {
      m_eAllowSoftFail = ETriState.UNDEFINED;
      return thisAsT ();
    }

    /**
     * Set the the handler to be called if there was a problem communicating
     * with the remote servers. This is only called if "allow soft fail" is
     * enabled.
     *
     * @param a
     *        The handler to be called. May be <code>null</code>.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE softFailExceptionHandler (@Nullable final Consumer <? super List <CertPathValidatorException>> a)
    {
      m_aSoftFailExceptionHdl = a;
      return thisAsT ();
    }

    /**
     * This is a professional setting. Since the activation of OCSP requires the
     * setting of a global Security property, this call is by default not thread
     * safe. Therefore the checking logic is by default executed in a
     * <code>synchronized</code> block. This slows down parallel execution.
     * Consider disabling this only if you are sure to use a single setting for
     * your complete application. Please mind that the security properties may
     * also affect other applications run on the same application server.<br>
     * If this setting is not set, the global default setting is used.
     *
     * @param b
     *        <code>true</code> to enable it, <code>false</code> to disable it.
     * @return this for chaining
     * @see CertificateRevocationChecker#isExecuteInSynchronizedBlock()
     */
    @Nonnull
    public final IMPLTYPE executeInSynchronizedBlock (final boolean b)
    {
      m_eExecuteInSynchronizedBlock = ETriState.valueOf (b);
      return thisAsT ();
    }

    /**
     * Set the number of milliseconds that act as a barrier, if an execution
     * took longer than that duration, that a warning message is emitted. By
     * default it is 500 milliseconds meaning half a second.
     *
     * @param n
     *        the number of milliseconds.
     * @return this for chaining
     */
    @Nonnull
    public final IMPLTYPE executionDurationWarnMS (final long n)
    {
      return executionDurationWarn (Duration.ofMillis (n));
    }

    /**
     * Set the duration that act as a barrier, if an execution took longer than
     * that duration, that a warning message is emitted. By default it is 500
     * milliseconds meaning half a second.
     *
     * @param a
     *        the duration to use. May be <code>null</code>.
     * @return this for chaining
     * @since 9.0.8
     */
    @Nonnull
    public final IMPLTYPE executionDurationWarn (@Nullable final Duration a)
    {
      m_aExecutionDurationWarn = a;
      return thisAsT ();
    }

    /**
     * Set the CRL caching duration. Default is 1 day.
     *
     * @param a
     *        the duration to use. May not be <code>null</code>.
     * @return this for chaining
     * @since 9.0.8
     */
    @Nonnull
    public final IMPLTYPE crlCachingDuration (@Nonnull final Duration a)
    {
      ValueEnforcer.notNull (a, "CRLCachingDuration");
      m_aCRLCachingDuration = a;
      return thisAsT ();
    }

    /**
     * Check the certificate revocation status. This method requires that the
     * following fields are set:
     * <ul>
     * <li>certificate</li>
     * <li>validCAs</li>
     * </ul>
     * If the following fields are not set, a fallback to the default is
     * performed:
     * <ul>
     * <li>checkMode -
     * {@link CertificateRevocationChecker#getRevocationCheckMode()}</li>
     * <li>exceptionHandler -
     * {@link CertificateRevocationChecker#getExceptionHdl()}</li>
     * <li>allowSoftFail -
     * {@link CertificateRevocationChecker#isAllowSoftFail()}</li>
     * <li>softFailExceptionHandler -
     * {@link CertificateRevocationChecker#getSoftFailExceptionHdl()}</li>
     * <li>executeInSynchronizedBlock -
     * {@link CertificateRevocationChecker#isExecuteInSynchronizedBlock()}</li>
     * </ul>
     */
    @Nonnull
    public ERevoked build ()
    {
      // Fallback to global settings where possible
      final ERevocationCheckMode eRealCheckMode = m_eCheckMode != null ? m_eCheckMode : getRevocationCheckMode ();
      final Consumer <? super GeneralSecurityException> aRealExceptionHdl = m_aExceptionHdl != null ? m_aExceptionHdl
                                                                                                    : getExceptionHdl ();
      final boolean bAllowSoftFail = m_eAllowSoftFail.isDefined () ? m_eAllowSoftFail.getAsBooleanValue ()
                                                                   : isAllowSoftFail ();
      final Consumer <? super List <CertPathValidatorException>> aRealSoftFailExceptionHdl = m_aSoftFailExceptionHdl != null ? m_aSoftFailExceptionHdl
                                                                                                                             : getSoftFailExceptionHdl ();
      final boolean bExecuteSync = m_eExecuteInSynchronizedBlock.isDefined () ? m_eExecuteInSynchronizedBlock.getAsBooleanValue ()
                                                                              : isExecuteInSynchronizedBlock ();

      // Consistency checks
      if (m_aCert == null)
        throw new IllegalStateException ("The certificate to be checked must be set");
      if (m_aValidCAs.isEmpty ())
        throw new IllegalStateException ("At least one valid CAs must be present");
      if (m_aExecutionDurationWarn != null && m_aExecutionDurationWarn.toMillis () <= 0)
        throw new IllegalStateException ("The duration for warning on long execution must be positive");
      // Check date may be null

      // Run it
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Performing certificate revocation check on certificate '" +
                      m_aCert.getSubjectX500Principal ().getName () +
                      "' " +
                      (m_aCheckDate != null ? "for datetime " + m_aCheckDate : "without a datetime") +
                      (bExecuteSync ? " [synchronized]" : " [not synchronized]"));

      // check OCSP and CLR
      final StopWatch aSW = StopWatch.createdStarted ();
      try
      {
        if (eRealCheckMode.isNone ())
        {
          // No revocation check
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Certificate revocation check is disabled");
        }
        else
        {
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Certificate revocation check is performed using mode " + eRealCheckMode);

          final X509CertSelector aSelector = new X509CertSelector ();
          aSelector.setCertificate (m_aCert);

          // Certificate -> trust anchors; name constraints MUST be null
          final ICommonsSet <TrustAnchor> aTrustAnchors = new CommonsHashSet <> (m_aValidCAs,
                                                                                 x -> new TrustAnchor (x, null));
          final PKIXBuilderParameters aPKIXParams = new PKIXBuilderParameters (aTrustAnchors, aSelector);
          aPKIXParams.setRevocationEnabled (true);
          if (m_aCheckDate != null)
          {
            // Check at specific date
            aPKIXParams.setDate (m_aCheckDate);
          }

          final IThrowingRunnable <GeneralSecurityException> aPerformer = () -> {
            try
            {
              final boolean bEnable = eRealCheckMode.isOCSP ();
              if (LOGGER.isDebugEnabled ())
                LOGGER.debug ("Setting system property 'ocsp.enable' to " + bEnable);
              Security.setProperty ("ocsp.enable", Boolean.toString (bEnable));
            }
            catch (final SecurityException ex)
            {
              LOGGER.warn ("Failed to set Security property 'ocsp.enable' to '" + eRealCheckMode.isOCSP () + "'");
            }

            // Specify a list of intermediate certificates ("Collection" is a
            // key in the "SUN" security provider)
            final CertStore aIntermediateCertStore = CertStore.getInstance ("Collection",
                                                                            new CollectionCertStoreParameters (m_aValidCAs));
            aPKIXParams.addCertStore (aIntermediateCertStore);

            if (eRealCheckMode.isCRL ())
            {
              if (LOGGER.isDebugEnabled ())
                LOGGER.debug ("Setting up CRL check data");

              // Get all necessary CRLs
              final ICommonsList <String> aCRLURLs = CRLHelper.getAllDistributionPoints (m_aCert);
              final ICommonsList <CRL> aCRLs = new CommonsArrayList <> ();
              for (final String sCRLURL : aCRLURLs)
              {
                // Get from cache or download
                final CRL aCRL = CRLHelper.getCRLFromURL (sCRLURL, m_aCRLCachingDuration);
                if (aCRL != null)
                  aCRLs.add (aCRL);
              }
              if (aCRLs.isNotEmpty ())
              {
                aPKIXParams.addCertStore (CertStore.getInstance ("Collection",
                                                                 new CollectionCertStoreParameters (aCRLs)));
              }
            }

            if (LOGGER.isDebugEnabled ())
              LOGGER.debug ("Checking certificate\n" +
                            m_aCert +
                            "\n\nagainst " +
                            m_aValidCAs.size () +
                            " valid CAs:\n" +
                            m_aValidCAs);

            // Throws an exception in case of an error
            final CertPathBuilder aCPB = CertPathBuilder.getInstance ("PKIX");
            final PKIXRevocationChecker aRevChecker = (PKIXRevocationChecker) aCPB.getRevocationChecker ();

            // Build checking options
            final EnumSet <PKIXRevocationChecker.Option> aOptions = EnumSet.of (PKIXRevocationChecker.Option.ONLY_END_ENTITY);
            if (bAllowSoftFail)
              aOptions.add (PKIXRevocationChecker.Option.SOFT_FAIL);
            eRealCheckMode.addAllOptionsTo (aOptions);
            if (LOGGER.isDebugEnabled ())
              LOGGER.debug ("OCSP/CLR effective options = " + aOptions);
            aRevChecker.setOptions (aOptions);

            final PKIXCertPathBuilderResult aBuilderResult = (PKIXCertPathBuilderResult) aCPB.build (aPKIXParams);
            if (LOGGER.isDebugEnabled ())
              LOGGER.debug ("OCSP/CLR builder result = " + aBuilderResult);
            final CertPath aCertPath = aBuilderResult.getCertPath ();

            // Validate
            final CertPathValidator aCPV = CertPathValidator.getInstance ("PKIX");
            final PKIXCertPathValidatorResult aValidateResult = (PKIXCertPathValidatorResult) aCPV.validate (aCertPath,
                                                                                                             aPKIXParams);
            if (LOGGER.isDebugEnabled ())
              LOGGER.debug ("OCSP/CLR validation result = " + aValidateResult);

            if (bAllowSoftFail)
            {
              final List <CertPathValidatorException> aList = aRevChecker.getSoftFailExceptions ();
              if (!aList.isEmpty ())
                aRealSoftFailExceptionHdl.accept (aList);
            }
          };

          if (bExecuteSync)
          {
            // Synchronize because the change of the Security.property is global
            synchronized (CertificateRevocationChecker.class)
            {
              aPerformer.run ();
            }
          }
          else
          {
            // Run non-synchronized - quicker but more dangerous if multiple
            // checks run in parallel
            aPerformer.run ();
          }
        }

        return ERevoked.NOT_REVOKED;
      }
      catch (final GeneralSecurityException ex)
      {
        LOGGER.error ("Error running certification revocation check: " +
                      ex.getClass ().getName () +
                      " - " +
                      ex.getMessage ());
        aRealExceptionHdl.accept (ex);
        return ERevoked.REVOKED;
      }
      finally
      {
        final long nMillis = aSW.stopAndGetMillis ();
        if (m_aExecutionDurationWarn != null && nMillis > m_aExecutionDurationWarn.toMillis ())
          LOGGER.warn ("OCSP/CLR revocation check took " + nMillis + " milliseconds which is too long");
        else
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("OCSP/CLR revocation check took " + nMillis + " milliseconds");
      }
    }
  }

  /**
   * A generic revocation check builder that works with arbitrary certificates.
   *
   * @author Philip Helger
   */
  @NotThreadSafe
  public static class RevocationCheckBuilder extends AbstractRevocationCheckBuilder <RevocationCheckBuilder>
  {}
}
