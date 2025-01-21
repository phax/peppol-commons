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

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
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
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.builder.IBuilder;
import com.helger.commons.callback.IThrowingRunnable;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;
import com.helger.commons.timing.StopWatch;
import com.helger.commons.traits.IGenericImplTrait;

/**
 * A utility class to configure the revocation check in a fine grained way. This
 * class does NOT use any caching, so it's up to the caller to do that caching.
 *
 * @param <IMPLTYPE>
 *        Implementation type
 * @author Philip Helger
 */
@NotThreadSafe
public abstract class AbstractRevocationCheckBuilder <IMPLTYPE extends AbstractRevocationCheckBuilder <IMPLTYPE>>
                                                     implements
                                                     IBuilder <ERevoked>,
                                                     IGenericImplTrait <IMPLTYPE>
{
  public static final Duration DEFAULT_EXECUTION_WARN_DURATION = Duration.ofMillis (500);

  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractRevocationCheckBuilder.class);

  private X509Certificate m_aCert;
  private final ICommonsList <X509Certificate> m_aValidCAs = new CommonsArrayList <> ();
  private Date m_aCheckDate;
  private ERevocationCheckMode m_eCheckMode;
  private Consumer <? super GeneralSecurityException> m_aExceptionHdl;
  private ETriState m_eAllowSoftFail = ETriState.UNDEFINED;
  private Consumer <? super List <CertPathValidatorException>> m_aSoftFailExceptionHdl;
  private ETriState m_eExecuteInSynchronizedBlock = ETriState.UNDEFINED;
  private Duration m_aExecutionDurationWarn = DEFAULT_EXECUTION_WARN_DURATION;
  private CRLCache m_aCRLCache = CertificateRevocationCheckerDefaults.getDefaultCRLCache ();

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
   * Set a valid CA to be checked against. All previous trusted CAs are removed.
   *
   * @param a
   *        The CA certificate to be checked against. May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE validCA (@Nullable final X509Certificate a)
  {
    m_aValidCAs.set (a);
    return thisAsT ();
  }

  /**
   * Set the valid CAs to be checked against. All previous trusted CAs are
   * removed.
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
   * Set the valid CAs to be checked against. All previous trusted CAs are
   * removed.
   *
   * @param a
   *        The array of CA certificates to be checked against. May be
   *        <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE validCAs (@Nullable final X509Certificate... a)
  {
    m_aValidCAs.setAll (a);
    return thisAsT ();
  }

  /**
   * Set the valid CAs to be checked against from the provided trust store. All
   * previous trusted CAs are removed.
   *
   * @param aTrustStore
   *        The trust store to be checked against. May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE validCAs (@Nullable final KeyStore aTrustStore)
  {
    return validCAs (PeppolKeyStoreHelper.getAllTrustedCertificates (aTrustStore));
  }

  /**
   * Add a valid CA to be checked against. All previously contained valid CAs
   * are kept.
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
   * Add valid CAs to be checked against. All previously contained valid CAs are
   * kept.
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
   * Add valid CAs to be checked against. All previously contained valid CAs are
   * kept.
   *
   * @param a
   *        A CA certificates to be checked against. May be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE addValidCAs (@Nullable final X509Certificate... a)
  {
    m_aValidCAs.addAll (a);
    return thisAsT ();
  }

  /**
   * Add the valid CAs to be checked against from the provided trust store. All
   * previously contained valid CAs are kept.
   *
   * @param aTrustStore
   *        The trust store to be checked against. May be <code>null</code>.
   * @return this for chaining
   * @since 9.4.0
   */
  @Nonnull
  public final IMPLTYPE addValidCAs (@Nullable final KeyStore aTrustStore)
  {
    return addValidCAs (PeppolKeyStoreHelper.getAllTrustedCertificates (aTrustStore));
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
   * Enable or disable the usage of "soft fail". If this method is not set, the
   * global setting is used.
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
   * Set the the handler to be called if there was a problem communicating with
   * the remote servers. This is only called if "allow soft fail" is enabled.
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
   * @see CertificateRevocationCheckerDefaults#isExecuteInSynchronizedBlock()
   */
  @Nonnull
  public final IMPLTYPE executeInSynchronizedBlock (final boolean b)
  {
    m_eExecuteInSynchronizedBlock = ETriState.valueOf (b);
    return thisAsT ();
  }

  /**
   * Set the number of milliseconds that act as a barrier, if an execution took
   * longer than that duration, that a warning message is emitted. By default it
   * is 500 milliseconds meaning half a second.
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
   * Set the CRL cache to be used.
   *
   * @param a
   *        The cache to be used. Must not be <code>null</code>.
   * @return this for chaining
   * @since 9.3.0
   */
  @Nonnull
  public final IMPLTYPE crlCache (@Nonnull final CRLCache a)
  {
    ValueEnforcer.notNull (a, "CRLCache");
    m_aCRLCache = a;
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
   * {@link CertificateRevocationCheckerDefaults#getRevocationCheckMode()}</li>
   * <li>exceptionHandler -
   * {@link CertificateRevocationCheckerDefaults#getExceptionHdl()}</li>
   * <li>allowSoftFail -
   * {@link CertificateRevocationCheckerDefaults#isAllowSoftFail()}</li>
   * <li>softFailExceptionHandler -
   * {@link CertificateRevocationCheckerDefaults#getSoftFailExceptionHdl()}</li>
   * <li>executeInSynchronizedBlock -
   * {@link CertificateRevocationCheckerDefaults#isExecuteInSynchronizedBlock()}</li>
   * </ul>
   */
  @Nonnull
  public ERevoked build ()
  {
    // Fallback to global settings where possible
    final ERevocationCheckMode eRealCheckMode = m_eCheckMode != null ? m_eCheckMode
                                                                     : CertificateRevocationCheckerDefaults.getRevocationCheckMode ();
    final Consumer <? super GeneralSecurityException> aRealExceptionHdl = m_aExceptionHdl != null ? m_aExceptionHdl
                                                                                                  : CertificateRevocationCheckerDefaults.getExceptionHdl ();
    final boolean bAllowSoftFail = m_eAllowSoftFail.isDefined () ? m_eAllowSoftFail.getAsBooleanValue ()
                                                                 : CertificateRevocationCheckerDefaults.isAllowSoftFail ();
    final Consumer <? super List <CertPathValidatorException>> aRealSoftFailExceptionHdl = m_aSoftFailExceptionHdl != null ? m_aSoftFailExceptionHdl
                                                                                                                           : CertificateRevocationCheckerDefaults.getSoftFailExceptionHdl ();
    final boolean bExecuteSync = m_eExecuteInSynchronizedBlock.isDefined () ? m_eExecuteInSynchronizedBlock.getAsBooleanValue ()
                                                                            : CertificateRevocationCheckerDefaults.isExecuteInSynchronizedBlock ();

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

    // check OCSP and CRL
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
          LOGGER.debug ("Certificate revocation check is performed using mode " +
                        eRealCheckMode +
                        "; executeSync=" +
                        bExecuteSync);

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
              final CRL aCRL = m_aCRLCache.getCRLFromURL (sCRLURL);
              if (aCRL != null)
                aCRLs.add (aCRL);
            }
            if (aCRLs.isNotEmpty ())
            {
              aPKIXParams.addCertStore (CertStore.getInstance ("Collection",
                                                               new CollectionCertStoreParameters (aCRLs)));
            }
            else
            {
              LOGGER.warn ("Failed to find any CRL objects for revocation checking");
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
            LOGGER.debug ("OCSP/CRL effective options = " + aOptions);
          aRevChecker.setOptions (aOptions);

          // If this takes forever, then OCSP call is invoked that e.g. can't
          // reach the server (default is 15 secs)
          final PKIXCertPathBuilderResult aBuilderResult = (PKIXCertPathBuilderResult) aCPB.build (aPKIXParams);
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("OCSP/CRL builder result = " + aBuilderResult);
          final CertPath aCertPath = aBuilderResult.getCertPath ();

          // Validate
          // If this takes forever, then OCSP call is invoked that e.g. can't
          // reach the server (default is 15 secs)
          final CertPathValidator aCPV = CertPathValidator.getInstance ("PKIX");
          final PKIXCertPathValidatorResult aValidateResult = (PKIXCertPathValidatorResult) aCPV.validate (aCertPath,
                                                                                                           aPKIXParams);
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("OCSP/CRL validation result = " + aValidateResult);

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
          synchronized (CertificateRevocationCheckerDefaults.class)
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
        LOGGER.warn ("OCSP/CRL revocation check took " + nMillis + " milliseconds which is too long");
      else
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("OCSP/CRL revocation check took " + nMillis + " milliseconds");
    }
  }
}
