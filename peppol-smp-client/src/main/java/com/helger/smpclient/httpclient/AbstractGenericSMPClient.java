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
package com.helger.smpclient.httpclient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.function.Consumer;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.style.OverrideOnDemand;
import com.helger.annotation.style.ReturnsImmutableObject;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.debug.GlobalDebug;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.trait.IGenericImplTrait;
import com.helger.collection.commons.CommonsHashSet;
import com.helger.collection.commons.ICommonsSet;
import com.helger.httpclient.HttpClientManager;
import com.helger.httpclient.IHttpClientSettings;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.mime.CMimeType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.security.certificate.CertificateDecodeHelper;
import com.helger.security.keystore.EKeyStoreType;
import com.helger.security.revocation.CertificateRevocationCheckerDefaults;
import com.helger.security.revocation.ERevocationCheckMode;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientHttpException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientParticipantNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;
import com.helger.xsds.xmldsig.X509DataType;

import jakarta.xml.bind.JAXBElement;

/**
 * Abstract base class for SMP clients - wraps all the HTTP stuff
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 * @param <IMPLTYPE>
 *        Real implementation class
 */
public abstract class AbstractGenericSMPClient <IMPLTYPE extends AbstractGenericSMPClient <IMPLTYPE>> implements
                                               IGenericImplTrait <IMPLTYPE>
{
  public static final boolean DEFAULT_FOLLOW_REDIRECTS = true;
  public static final boolean DEFAULT_XML_SCHEMA_VALIDATION = true;
  /**
   * Default value, whether the identifiers contained in a retrieved Service Metadata response are
   * checked against the requested identifiers.
   *
   * @since 12.6.1
   */
  public static final boolean DEFAULT_CHECK_SERVICE_METADATA_IDENTIFIERS = true;

  // The default text/xml content type uses iso-8859-1!
  public static final ContentType CONTENT_TYPE_TEXT_XML = ContentType.create (CMimeType.TEXT_XML.getAsString (),
                                                                              StandardCharsets.UTF_8);

  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractGenericSMPClient.class);
  private static final KeyStore DEFAULT_TRUST_STORE;

  static
  {
    final EKeyStoreType eType = SMPClientConfiguration.getTrustStoreType ();
    final String sPath = SMPClientConfiguration.getTrustStorePath ();

    DEFAULT_TRUST_STORE = SMPClientConfiguration.loadTrustStore ();
    if (DEFAULT_TRUST_STORE != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Successfully loaded configured SMP client trust store '" + sPath + "' of type " + eType);
    }
    else
    {
      if (StringHelper.isEmpty (sPath))
        LOGGER.warn ("No SMP client trust store is configured");
      else
        LOGGER.warn ("Failed to load the configured SMP client trust store '" + sPath + "' of type " + eType);
    }
  }

  /**
   * The string representation of the SMP host URL, always ending with a trailing slash!
   */
  private final String m_sSMPHost;
  private boolean m_bVerifySignature = SMPHttpResponseHandlerSigned.DEFAULT_VERIFY_SIGNATURE;
  private boolean m_bSecureValidation = SMPHttpResponseHandlerSigned.DEFAULT_SECURE_VALIDATION;
  // null means "use default from CertificateRevocationCheckerDefaults"
  private ERevocationCheckMode m_eRevocationCheckMode;
  private boolean m_bAllowRevocationSoftFail = CertificateRevocationCheckerDefaults.isAllowSoftFail ();
  private boolean m_bSynchronizedRevocationCheck = CertificateRevocationCheckerDefaults.isExecuteInSynchronizedBlock ();
  private KeyStore m_aTrustStore = DEFAULT_TRUST_STORE;
  private boolean m_bFollowSMPRedirects = DEFAULT_FOLLOW_REDIRECTS;
  private boolean m_bXMLSchemaValidation = DEFAULT_XML_SCHEMA_VALIDATION;
  private final SMPHttpClientSettings m_aHttpClientSettings = SMPHttpClientSettings.fromConfiguration ();
  private Consumer <? super GenericJAXBMarshaller <?>> m_aMarshallerConsumer;
  // A neutral default that never folds case; concrete clients set a more specific default (Peppol,
  // BDXR1, BDXR2) in their constructor
  private IIdentifierFactory m_aIdentifierFactory = SimpleIdentifierFactory.INSTANCE;
  private boolean m_bCheckServiceMetadataIDs = DEFAULT_CHECK_SERVICE_METADATA_IDENTIFIERS;

  /**
   * Constructor with a direct SMP URL.<br>
   * Remember: must be HTTP and using port 80 only!
   *
   * @param aSMPHost
   *        The address of the SMP service. Must be port 80 and basic http only (no https!).
   *        Example: http://smpcompany.company.org
   * @param bPeppolLimitationsActive
   *        <code>true</code> if the Peppol limitations (Port 80, http only, in root context) should
   *        be complained about or not.
   */
  protected AbstractGenericSMPClient (@NonNull final URI aSMPHost, final boolean bPeppolLimitationsActive)
  {
    ValueEnforcer.notNull (aSMPHost, "SMPHost");

    if (bPeppolLimitationsActive)
    {
      if (!"http".equals (aSMPHost.getScheme ()))
        LOGGER.warn ("SMP URI " + aSMPHost + " does not use the expected http scheme, which is required for Peppol!");

      // getPort () returns -1 if none was explicitly specified
      if (aSMPHost.getPort () != 80 && aSMPHost.getPort () != -1)
        LOGGER.warn ("SMP URI " + aSMPHost + " is not running on port 80, which is required for Peppol!");
    }

    // Build string and ensure it ends with a "/"
    final String sSMPHost = aSMPHost.toString ();
    m_sSMPHost = sSMPHost.endsWith ("/") ? sSMPHost : sSMPHost + '/';
  }

  /**
   * @return The SMP host URI string we're operating on. Never <code>null</code> . Always has a
   *         trailing "/".
   */
  @NonNull
  public final String getSMPHostURI ()
  {
    return m_sSMPHost;
  }

  /**
   * @return The HTTP client settings to be configured. Never <code>null</code>.
   * @see #getHttpClientSettings()
   * @since 8.0.1
   */
  @NonNull
  @ReturnsMutableObject
  public final SMPHttpClientSettings httpClientSettings ()
  {
    return m_aHttpClientSettings;
  }

  /**
   * @return The read-only HTTP client settings to be configured. Never <code>null</code>.
   * @see #httpClientSettings()
   * @since 12.5.0
   */
  @NonNull
  @ReturnsImmutableObject
  public final IHttpClientSettings getHttpClientSettings ()
  {
    return m_aHttpClientSettings;
  }

  /**
   * Special version to modify the httpClientSettings but with a chainable API.
   *
   * @param aConsumer
   *        The consumer that deals with the SMPHttpClientSettings
   * @return this for chaining
   * @since 9.0.9
   */
  @NonNull
  public final IMPLTYPE withHttpClientSettings (@NonNull final Consumer <? super SMPHttpClientSettings> aConsumer)
  {
    ValueEnforcer.notNull (aConsumer, "Consumer");
    aConsumer.accept (m_aHttpClientSettings);
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is enabled,
   *         <code>false</code> if it is disabled. By default this check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_VERIFY_SIGNATURE}).
   * @since 8.0.3
   */
  public final boolean isVerifySignature ()
  {
    return m_bVerifySignature;
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be helpful for debugging
   * and testing of SMP client connections!<br>
   * Uses the trust store configured in the SMP client configuration.
   *
   * @param bVerifySignature
   *        <code>true</code> to enable SMP response checking (on by default) or <code>false</code>
   *        to disable it.
   * @return this for chaining
   * @since 8.0.3
   */
  @NonNull
  public final IMPLTYPE setVerifySignature (final boolean bVerifySignature)
  {
    m_bVerifySignature = bVerifySignature;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking should use secure
   *         validation, <code>false</code> if validation also allows deprecated algorithms. By
   *         default this check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_SECURE_VALIDATION}).
   * @since 9.0.5
   */
  public final boolean isSecureValidation ()
  {
    return m_bSecureValidation;
  }

  /**
   * Enable or disable the usage of secure XMLDsig validation. By default secure validation is
   * enabled. Java 17 disables the usage of SHA-1 in XMLDsig by default, as documented in
   * https://bugs.openjdk.org/browse/JDK-8261246. Currently the Peppol SMP still uses SHA-1 so you
   * might want to disable this for the sake of sanity.
   *
   * @param bSecureValidation
   *        <code>true</code> to enable SMP secure certificate validation (enabled by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 9.0.5
   */
  @NonNull
  public final IMPLTYPE setSecureValidation (final boolean bSecureValidation)
  {
    m_bSecureValidation = bSecureValidation;
    return thisAsT ();
  }

  /**
   * @return The revocation check mode to use when verifying SMP response certificates.
   *         <code>null</code> means "use the JVM-wide default from
   *         {@link com.helger.security.revocation.CertificateRevocationCheckerDefaults}".
   * @since 12.4.3
   */
  @Nullable
  public final ERevocationCheckMode getRevocationCheckMode ()
  {
    return m_eRevocationCheckMode;
  }

  /**
   * Set the revocation check mode to use when verifying SMP response certificates.
   *
   * @param e
   *        The mode to use. <code>null</code> means "use the JVM-wide default from
   *        {@link com.helger.security.revocation.CertificateRevocationCheckerDefaults}".
   * @return this for chaining
   * @since 12.4.3
   */
  @NonNull
  public final IMPLTYPE setRevocationCheckMode (@Nullable final ERevocationCheckMode e)
  {
    m_eRevocationCheckMode = e;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if an undeterminable revocation status (e.g. an unreachable CRL
   *         distribution point or OCSP responder) is treated as "revocation soft fail" — the
   *         certificate is accepted in favour of the doubt. <code>false</code> means "revocation
   *         hard fail" — the certificate is rejected. The default is taken from
   *         {@link CertificateRevocationCheckerDefaults#isAllowSoftFail()}.
   * @since 12.4.4
   */
  public final boolean isAllowRevocationSoftFail ()
  {
    return m_bAllowRevocationSoftFail;
  }

  /**
   * Modify how an undeterminable revocation status is handled.
   *
   * @param b
   *        <code>true</code> to allow "revocation soft fail" (accept the certificate),
   *        <code>false</code> for "revocation hard fail" (reject the certificate).
   * @return this for chaining
   * @since 12.4.4
   */
  @NonNull
  public final IMPLTYPE setAllowRevocationSoftFail (final boolean b)
  {
    m_bAllowRevocationSoftFail = b;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if the revocation check should be executed in a synchronized block,
   *         <code>false</code> if not. The default is taken from
   *         {@link CertificateRevocationCheckerDefaults#isExecuteInSynchronizedBlock()}.
   * @since 12.5.4
   */
  public final boolean isSynchronizedRevocationCheck ()
  {
    return m_bSynchronizedRevocationCheck;
  }

  /**
   * Modify whether the revocation check should be executed in a synchronized block or not.
   *
   * @param b
   *        <code>true</code> to execute the revocation check in a synchronized block,
   *        <code>false</code> if not.
   * @return this for chaining
   * @since 12.5.4
   */
  @NonNull
  public final IMPLTYPE setSynchronizedRevocationCheck (final boolean b)
  {
    m_bSynchronizedRevocationCheck = b;
    return thisAsT ();
  }

  /**
   * @return The trust store to be used for verifying the signature. May be <code>null</code> if an
   *         invalid trust store is configured.
   * @since 8.1.1
   */
  @Nullable
  public final KeyStore getTrustStore ()
  {
    return m_aTrustStore;
  }

  /**
   * Set the trust store to be used. The trust store must be used, if signature verification is
   * enabled.
   *
   * @param aTrustStore
   *        The trust store to be used. May be <code>null</code>.
   * @return this for chaining
   * @since 8.1.1
   */
  @NonNull
  public final IMPLTYPE setTrustStore (@Nullable final KeyStore aTrustStore)
  {
    m_aTrustStore = aTrustStore;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP redirects should be followed, <code>false</code> if not. By
   *         default this check is enabled (see {@link #DEFAULT_FOLLOW_REDIRECTS}).
   * @since 7.0.6
   */
  public final boolean isFollowSMPRedirects ()
  {
    return m_bFollowSMPRedirects;
  }

  /**
   * Should the SMP client follow the SMP redirects that can be found in service registrations.
   * Enabled by default.
   *
   * @param bFollowSMPRedirects
   *        <code>true</code> to follow SMP redirects (on by default) or <code>false</code> to
   *        disable it.
   * @return this for chaining
   * @since 7.0.6
   */
  @NonNull
  public final IMPLTYPE setFollowSMPRedirects (final boolean bFollowSMPRedirects)
  {
    m_bFollowSMPRedirects = bFollowSMPRedirects;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if responses should be checked against the XML Schemas,
   *         <code>false</code> if not. By default this check is enabled (see
   *         {@link #DEFAULT_XML_SCHEMA_VALIDATION}).
   * @since 8.0.5
   */
  public final boolean isXMLSchemaValidation ()
  {
    return m_bXMLSchemaValidation;
  }

  /**
   * Should the SMP client perform XML Schema validation or not. Enabled by default.
   *
   * @param bXMLSchemaValidation
   *        <code>true</code> to perform XML Schema validation, <code>false</code> to disable it.
   * @return this for chaining
   * @since 8.0.5
   */
  @NonNull
  public final IMPLTYPE setXMLSchemaValidation (final boolean bXMLSchemaValidation)
  {
    m_bXMLSchemaValidation = bXMLSchemaValidation;
    return thisAsT ();
  }

  /**
   * @return The identifier factory used to compare the identifiers returned by the SMP against the
   *         requested identifiers. Never <code>null</code>. Each concrete SMP client uses a dialect
   *         specific default (e.g. <code>PeppolIdentifierFactory</code> for the Peppol SMP client).
   * @see #setIdentifierFactory(IIdentifierFactory)
   * @since 12.6.1
   */
  @NonNull
  public final IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIdentifierFactory;
  }

  /**
   * Set the identifier factory to be used to compare the identifiers returned by the SMP against
   * the requested identifiers. The identifier factory defines whether identifiers are handled case
   * sensitive or not (e.g. Peppol document type identifiers are case sensitive, whereas Peppol
   * participant identifiers are case insensitive).
   *
   * @param aIdentifierFactory
   *        The identifier factory to be used. May not be <code>null</code>.
   * @return this for chaining
   * @see #isCheckServiceMetadataIdentifiers()
   * @since 12.6.1
   */
  @NonNull
  public final IMPLTYPE setIdentifierFactory (@NonNull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
    m_aIdentifierFactory = aIdentifierFactory;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if the participant and document type identifiers contained in a
   *         retrieved Service Metadata response are checked against the requested identifiers,
   *         <code>false</code> if not. By default this check is enabled (see
   *         {@link #DEFAULT_CHECK_SERVICE_METADATA_IDENTIFIERS}).
   * @see #setCheckServiceMetadataIdentifiers(boolean)
   * @since 12.6.1
   */
  public final boolean isCheckServiceMetadataIdentifiers ()
  {
    return m_bCheckServiceMetadataIDs;
  }

  /**
   * Enable or disable the check, whether the participant and document type identifiers contained in
   * a retrieved Service Metadata response match the requested identifiers. This check honours the
   * case sensitivity rules of the configured {@link IIdentifierFactory} (see
   * {@link #setIdentifierFactory(IIdentifierFactory)}). Because e.g. Peppol document type
   * identifiers are case sensitive, an SMP that resolves them case insensitively may return a
   * response for a different document type than the one requested. See issue #73.
   *
   * @param bCheckServiceMetadataIDs
   *        <code>true</code> to enable the check, <code>false</code> to disable it.
   * @return this for chaining
   * @since 12.6.1
   */
  @NonNull
  public final IMPLTYPE setCheckServiceMetadataIdentifiers (final boolean bCheckServiceMetadataIDs)
  {
    m_bCheckServiceMetadataIDs = bCheckServiceMetadataIDs;
    return thisAsT ();
  }

  private static boolean _hasSameValue (@NonNull final IIdentifierFactory aIdentifierFactory,
                                        @Nullable final String sRequestedValue,
                                        @Nullable final String sReturnedValue,
                                        final boolean bCaseInsensitive)
  {
    if (bCaseInsensitive)
      return EqualsHelper.equals (aIdentifierFactory.getUnifiedValue (sRequestedValue),
                                  aIdentifierFactory.getUnifiedValue (sReturnedValue));
    return EqualsHelper.equals (sRequestedValue, sReturnedValue);
  }

  /**
   * Check if the requested and the returned participant identifier are the same, honouring the case
   * sensitivity rules of the provided identifier factory.
   *
   * @param aIdentifierFactory
   *        The identifier factory defining the case sensitivity rules. May not be
   *        <code>null</code>.
   * @param aRequested
   *        The requested participant identifier. May not be <code>null</code>.
   * @param aReturned
   *        The participant identifier returned by the SMP. May not be <code>null</code>.
   * @return <code>true</code> if both identifiers are considered the same, <code>false</code>
   *         otherwise.
   * @since 12.6.1
   */
  public static boolean isSameParticipantIdentifier (@NonNull final IIdentifierFactory aIdentifierFactory,
                                                     @NonNull final IParticipantIdentifier aRequested,
                                                     @NonNull final IParticipantIdentifier aReturned)
  {
    // Scheme is always case sensitive; value depends on the scheme
    final boolean bCaseInsensitive = aIdentifierFactory.isParticipantIdentifierCaseInsensitive (aRequested.getScheme ());
    return EqualsHelper.equals (aRequested.getScheme (), aReturned.getScheme ()) &&
      _hasSameValue (aIdentifierFactory, aRequested.getValue (), aReturned.getValue (), bCaseInsensitive);
  }

  /**
   * Check if the requested and the returned document type identifier are the same, honouring the
   * case sensitivity rules of the provided identifier factory.
   *
   * @param aIdentifierFactory
   *        The identifier factory defining the case sensitivity rules. May not be
   *        <code>null</code>.
   * @param aRequested
   *        The requested document type identifier. May not be <code>null</code>.
   * @param aReturned
   *        The document type identifier returned by the SMP. May not be <code>null</code>.
   * @return <code>true</code> if both identifiers are considered the same, <code>false</code>
   *         otherwise.
   * @since 12.6.1
   */
  public static boolean isSameDocumentTypeIdentifier (@NonNull final IIdentifierFactory aIdentifierFactory,
                                                      @NonNull final IDocumentTypeIdentifier aRequested,
                                                      @NonNull final IDocumentTypeIdentifier aReturned)
  {
    // Scheme is always case sensitive; value depends on the scheme
    final boolean bCaseInsensitive = aIdentifierFactory.isDocumentTypeIdentifierCaseInsensitive (aRequested.getScheme ());
    return EqualsHelper.equals (aRequested.getScheme (), aReturned.getScheme ()) &&
      _hasSameValue (aIdentifierFactory, aRequested.getValue (), aReturned.getValue (), bCaseInsensitive);
  }

  /**
   * Verify that the participant and document type identifiers contained in a retrieved Service
   * Metadata response match the requested identifiers. This check honours the case sensitivity
   * rules of the configured {@link IIdentifierFactory} and is only performed if
   * {@link #isCheckServiceMetadataIdentifiers()} is <code>true</code>. It is a safety check against
   * SMPs that resolve identifiers case insensitively even though the identifier scheme requires
   * case sensitive handling (e.g. Peppol document type identifiers). See issue #73.<br>
   * Returned identifiers that are <code>null</code> (because a specific SMP response type does not
   * contain them) are silently skipped.
   *
   * @param aRequestedServiceGroupID
   *        The requested participant identifier. May not be <code>null</code>.
   * @param aReturnedServiceGroupID
   *        The participant identifier contained in the response. May be <code>null</code>.
   * @param aRequestedDocumentTypeID
   *        The requested document type identifier. May not be <code>null</code>.
   * @param aReturnedDocumentTypeID
   *        The document type identifier contained in the response. May be <code>null</code>.
   * @throws SMPClientException
   *         If checking is enabled and one of the returned identifiers does not match the requested
   *         one.
   * @since 12.6.1
   */
  protected final void checkServiceMetadataIdentifiers (@NonNull final IParticipantIdentifier aRequestedServiceGroupID,
                                                        @Nullable final IParticipantIdentifier aReturnedServiceGroupID,
                                                        @NonNull final IDocumentTypeIdentifier aRequestedDocumentTypeID,
                                                        @Nullable final IDocumentTypeIdentifier aReturnedDocumentTypeID) throws SMPClientException
  {
    if (!m_bCheckServiceMetadataIDs)
      return;

    if (aReturnedServiceGroupID != null &&
      !isSameParticipantIdentifier (m_aIdentifierFactory, aRequestedServiceGroupID, aReturnedServiceGroupID))
    {
      throw new SMPClientException ("The SMP response contained the participant identifier '" +
                                    aReturnedServiceGroupID.getURIEncoded () +
                                    "' instead of the requested '" +
                                    aRequestedServiceGroupID.getURIEncoded () +
                                    "'. This may indicate a case sensitivity issue on the SMP side.");
    }

    if (aReturnedDocumentTypeID != null &&
      !isSameDocumentTypeIdentifier (m_aIdentifierFactory, aRequestedDocumentTypeID, aReturnedDocumentTypeID))
    {
      throw new SMPClientException ("The SMP response contained the document type identifier '" +
                                    aReturnedDocumentTypeID.getURIEncoded () +
                                    "' instead of the requested '" +
                                    aRequestedDocumentTypeID.getURIEncoded () +
                                    "'. This may indicate a case sensitivity issue on the SMP side.");
    }
  }

  @NonNull
  @OverrideOnDemand
  protected HttpClientContext createHttpContext ()
  {
    return HttpClientContext.create ();
  }

  /**
   * Configure the provided {@link SMPHttpResponseHandlerSigned} with all the signature-related
   * settings of this SMP client (verify signature, secure validation, revocation check mode, allow
   * revocation soft fail, synchronized revocation check). Subclasses may override to add additional
   * configuration but should call <code>super.configureResponseHandler(aHandler)</code> to keep the
   * defaults applied.
   *
   * @param aHandler
   *        The response handler to be configured. May not be <code>null</code>.
   * @return The same response handler for chaining. Never <code>null</code>.
   * @since 12.4.3
   * @param <T>
   *        Expected response handler content type
   */
  @NonNull
  @OverrideOnDemand
  protected <T> SMPHttpResponseHandlerSigned <T> configureResponseHandler (@NonNull final SMPHttpResponseHandlerSigned <T> aHandler)
  {
    ValueEnforcer.notNull (aHandler, "Handler");

    aHandler.setVerifySignature (m_bVerifySignature);
    aHandler.setSecureValidation (m_bSecureValidation);
    aHandler.setRevocationCheckMode (m_eRevocationCheckMode);
    aHandler.setAllowRevocationSoftFail (m_bAllowRevocationSoftFail);
    aHandler.setSynchronizedRevocationCheck (m_bSynchronizedRevocationCheck);
    return aHandler;
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing the PEPPOL Directory
   * BusinessCard API. Compared to
   * {@link #executeGenericRequest(HttpUriRequestBase, HttpClientResponseHandler)} this method does
   * NOT convert the {@link IOException} from HTTP communication problems to {@link IOException}.
   *
   * @param aRequest
   *        The request to be executed. The proxy + connection and request timeout are set in this
   *        method.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws IOException
   *         On HTTP communication error
   * @see #executeGenericRequest(HttpUriRequestBase, HttpClientResponseHandler)
   * @param <T>
   *        Expected response type
   */
  @NonNull
  public <T> T executeRequest (@NonNull final HttpUriRequestBase aRequest,
                               @NonNull final HttpClientResponseHandler <T> aResponseHandler) throws IOException
  {
    final HttpClientContext aHttpContext = createHttpContext ();
    try (final HttpClientManager aHttpClientMgr = HttpClientManager.create (m_aHttpClientSettings))
    {
      aRequest.setAbsoluteRequestUri (true);
      LOGGER.info ("Performing SMP query at '" + aRequest.toString () + "'");
      return aHttpClientMgr.execute (aRequest, aHttpContext, aResponseHandler);
    }
    catch (final RuntimeException | IOException ex)
    {
      if (ex.getMessage () == null || GlobalDebug.isDebugMode ())
        LOGGER.error ("Error performing SMP query [debug full exception]", ex);
      else
        LOGGER.error ("Error performing SMP query: " + ex.getClass ().getName () + " - " + ex.getMessage ());
      throw ex;
    }
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing the PEPPOL Directory
   * BusinessCard API. This is equivalent to
   * {@link #executeRequest(HttpUriRequestBase, HttpClientResponseHandler)} but includes the
   * conversion of Exceptions to {@link SMPClientException} objects.
   *
   * @param aRequest
   *        The request to be executed. The proxy + connection and request timeout are set in this
   *        method.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws SMPClientException
   *         One of the converted exceptions
   * @param <T>
   *        Expected response type
   * @see #executeRequest(HttpUriRequestBase, HttpClientResponseHandler)
   * @see #getConvertedException(Exception)
   */
  @NonNull
  public <T> T executeGenericRequest (@NonNull final HttpUriRequestBase aRequest,
                                      @NonNull final HttpClientResponseHandler <T> aResponseHandler) throws SMPClientException
  {
    try
    {
      return executeRequest (aRequest, aResponseHandler);
    }
    catch (final Exception ex)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Exception executing HTTP request " + aRequest, ex);
      throw getConvertedException (ex);
    }
  }

  /**
   * Convert the passed generic HTTP exception into a more specific exception.
   *
   * @param ex
   *        The generic exception. May not be <code>null</code>.
   * @return A new SMP specific exception, using the passed exception as the cause.
   */
  @NonNull
  public static SMPClientException getConvertedException (@NonNull final Exception ex)
  {
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Converting exception of class '" + ex.getClass ().getName () + "' to an SMP expception");

    if (ex instanceof SMPClientException)
      return (SMPClientException) ex;

    if (ex instanceof final HttpResponseException hex)
    {
      final int nHttpStatus = hex.getStatusCode ();
      return switch (nHttpStatus)
      {
        case HttpStatus.SC_BAD_REQUEST -> new SMPClientBadRequestException (hex);
        case HttpStatus.SC_FORBIDDEN -> new SMPClientUnauthorizedException (hex);
        case HttpStatus.SC_NOT_FOUND -> new SMPClientNotFoundException (hex);
        default -> new SMPClientHttpException (nHttpStatus, "Error thrown with HTTP status code " + nHttpStatus, hex);
      };
    }

    // Special case: participant does not exist
    if (ex instanceof final UnknownHostException uhex)
      return new SMPClientParticipantNotFoundException (uhex);
    if (ex instanceof final ConnectException cex)
      return new SMPClientParticipantNotFoundException (cex);

    // For new SMPClientBadResponseException
    if (ex instanceof ClientProtocolException && ex.getCause () instanceof SMPClientException)
      return (SMPClientException) ex.getCause ();

    // Generic version
    return new SMPClientException ("Unknown error thrown by SMP server (" + ex.getMessage () + ")", ex);
  }

  /**
   * Customize the JAXB marshaller, e.g. to add error handler etc.
   *
   * @param aMarshaller
   *        Never <code>null</code>.
   * @since 8.6.3
   * @see #getMarshallerCustomizer()
   * @see #setMarshallerCustomizer(Consumer)
   */
  protected final void customizeMarshaller (@NonNull final GenericJAXBMarshaller <?> aMarshaller)
  {
    if (m_aMarshallerConsumer != null)
      m_aMarshallerConsumer.accept (aMarshaller);
  }

  /**
   * @return The JAXB Marshaller Customizer. May be <code>null</code>.
   * @since 8.6.3
   */
  @Nullable
  public final Consumer <? super GenericJAXBMarshaller <?>> getMarshallerCustomizer ()
  {
    return m_aMarshallerConsumer;
  }

  /**
   * Set the JAXB Marshaller Customizer
   *
   * @param a
   *        The customizer to be used. May be <code>null</code>.
   * @return this for chaining
   * @since 8.6.3
   */
  @NonNull
  public final IMPLTYPE setMarshallerCustomizer (@Nullable final Consumer <? super GenericJAXBMarshaller <?>> a)
  {
    m_aMarshallerConsumer = a;
    return thisAsT ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SMPHost", m_sSMPHost)
                                       .append ("VerifySignature", m_bVerifySignature)
                                       .append ("TrustStore", m_aTrustStore)
                                       .append ("FollowSMPRedirects", m_bFollowSMPRedirects)
                                       .append ("XMLSchemaValidation", m_bXMLSchemaValidation)
                                       .append ("HttpClientSettings", m_aHttpClientSettings)
                                       .appendIfNotNull ("MarshallerConsumer", m_aMarshallerConsumer)
                                       .append ("IdentifierFactory", m_aIdentifierFactory)
                                       .append ("CheckServiceMetadataIDs", m_bCheckServiceMetadataIDs)
                                       .getToString ();
  }

  /**
   * Compare X509 principal names using the LDAP name syntax with arbitrary order
   *
   * @param s1
   *        Principal 1
   * @param s2
   *        Principal 2
   * @return <code>true</code> if they are equal, <code>false</code> if not.
   */
  private static boolean _isEqualRdn (final String s1, final String s2)
  {
    try
    {
      // The LdapName contains an list with arbitrary order - the HashSet removes the need for order
      final ICommonsSet <Rdn> aSet1 = new CommonsHashSet <> (new LdapName (s1).getRdns ());
      final ICommonsSet <Rdn> aSet2 = new CommonsHashSet <> (new LdapName (s2).getRdns ());
      return aSet1.equals (aSet2);
    }
    catch (final InvalidNameException ex)
    {
      // Wrong content
      return false;
    }
  }

  public static boolean containsRedirectSubject (@NonNull final X509DataType aX509Data,
                                                 @NonNull final String sRedirectCertificateUID) throws SMPClientException
  {
    for (final Object aX509Obj : aX509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName ())
      if (aX509Obj instanceof final JAXBElement <?> aX509Element)
      {
        // Find the first subject (of type string) (element name
        // X509SubjectName) (optional according to the spec)
        if ("X509SubjectName".equals (aX509Element.getName ().getLocalPart ()))
        {
          final String sSubject = (String) aX509Element.getValue ();
          if (!_isEqualRdn (sRedirectCertificateUID, sSubject))
          {
            throw new SMPClientException ("The certificate UID of the redirect did not match the certificate subject which is '" +
                                          sSubject +
                                          "'. Required certificate UID is '" +
                                          sRedirectCertificateUID +
                                          "'");
          }
          return true;
        }

        if ("X509Certificate".equals (aX509Element.getName ().getLocalPart ()))
        {
          final byte [] aCertBytes = (byte []) aX509Element.getValue ();
          final X509Certificate aCert = new CertificateDecodeHelper ().source (aCertBytes)
                                                                      .pemEncoded (false)
                                                                      .getDecodedOrNull ();
          if (aCert == null)
            return false;

          final String sSubjectCN = aCert.getSubjectX500Principal ().getName ();
          if (!_isEqualRdn (sRedirectCertificateUID, sSubjectCN))
          {
            throw new SMPClientException ("The certificate UID of the redirect did not match the certificate/subject which '" +
                                          sSubjectCN +
                                          "'. Required certificate UID is '" +
                                          sRedirectCertificateUID +
                                          "'");
          }
          return true;
        }
      }
    return false;
  }
}
