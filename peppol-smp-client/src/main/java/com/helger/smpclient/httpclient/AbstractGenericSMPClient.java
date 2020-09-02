/**
 * Copyright (C) 2015-2020 Philip Helger
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.ICommonsOrderedSet;
import com.helger.commons.mime.CMimeType;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.traits.IGenericImplTrait;
import com.helger.httpclient.HttpClientManager;
import com.helger.httpclient.HttpClientSettings;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.exception.SMPClientBadRequestException;
import com.helger.smpclient.exception.SMPClientException;
import com.helger.smpclient.exception.SMPClientNotFoundException;
import com.helger.smpclient.exception.SMPClientUnauthorizedException;

/**
 * Abstract base class for SMP clients - wraps all the HTTP stuff
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 * @param <IMPLTYPE>
 *        Real implementation class
 */
public abstract class AbstractGenericSMPClient <IMPLTYPE extends AbstractGenericSMPClient <IMPLTYPE>> implements
                                               IGenericImplTrait <IMPLTYPE>
{
  public static final int DEFAULT_CONNECTION_TIMEOUT_MS = HttpClientSettings.DEFAULT_CONNECTION_TIMEOUT_MS;
  public static final int DEFAULT_REQUEST_TIMEOUT_MS = HttpClientSettings.DEFAULT_SOCKET_TIMEOUT_MS;
  public static final boolean DEFAULT_FOLLOW_REDIRECTS = true;
  public static final boolean DEFAULT_XML_SCHEMA_VALIDATION = true;

  // The default text/xml content type uses iso-8859-1!
  public static final ContentType CONTENT_TYPE_TEXT_XML = ContentType.create (CMimeType.TEXT_XML.getAsString (), StandardCharsets.UTF_8);

  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractGenericSMPClient.class);
  private static final KeyStore DEFAULT_TRUST_STORE;
  static
  {
    DEFAULT_TRUST_STORE = SMPClientConfiguration.loadTrustStore ();
    if (DEFAULT_TRUST_STORE != null)
    {
      if (LOGGER.isDebugEnabled ())
        LOGGER.debug ("Successfully loaded configured SMP client trust store");
    }
    else
    {
      if (StringHelper.hasNoText (SMPClientConfiguration.getTrustStorePath ()))
      {
        if (LOGGER.isInfoEnabled ())
          LOGGER.info ("No SMP client trust store is configured");
      }
      else
      {
        LOGGER.warn ("Failed to load the configured SMP client trust store");
      }
    }
  }

  /**
   * The string representation of the SMP host URL, always ending with a
   * trailing slash!
   */
  private final String m_sSMPHost;
  private boolean m_bVerifySignature = SMPHttpResponseHandlerSigned.DEFAULT_VERIFY_SIGNATURE;
  private KeyStore m_aTrustStore = DEFAULT_TRUST_STORE;
  private boolean m_bFollowSMPRedirects = DEFAULT_FOLLOW_REDIRECTS;
  private boolean m_bXMLSchemaValidation = DEFAULT_XML_SCHEMA_VALIDATION;
  private final SMPHttpClientSettings m_aHttpClientSettings = new SMPHttpClientSettings ();

  /**
   * Constructor with a direct SMP URL.<br>
   * Remember: must be HTTP and using port 80 only!
   *
   * @param aSMPHost
   *        The address of the SMP service. Must be port 80 and basic http only
   *        (no https!). Example: http://smpcompany.company.org
   * @param bPeppolLimitationsActive
   *        <code>true</code> if the Peppol limitations (Port 80, http only, in
   *        root context) should be complained about or not.
   */
  public AbstractGenericSMPClient (@Nonnull final URI aSMPHost, final boolean bPeppolLimitationsActive)
  {
    ValueEnforcer.notNull (aSMPHost, "SMPHost");

    if (bPeppolLimitationsActive)
    {
      if (!"http".equals (aSMPHost.getScheme ()))
        if (LOGGER.isWarnEnabled ())
          LOGGER.warn ("SMP URI " + aSMPHost + " does not use the expected http scheme!");

      // getPort () returns -1 if none was explicitly specified
      if (aSMPHost.getPort () != 80 && aSMPHost.getPort () != -1)
        if (LOGGER.isWarnEnabled ())
          LOGGER.warn ("SMP URI " + aSMPHost + " is not running on port 80!");
    }

    // Build string and ensure it ends with a "/"
    final String sSMPHost = aSMPHost.toString ();
    m_sSMPHost = sSMPHost.endsWith ("/") ? sSMPHost : sSMPHost + '/';
  }

  /**
   * @return The SMP host URI string we're operating on. Never <code>null</code>
   *         . Always has a trailing "/".
   */
  @Nonnull
  public final String getSMPHostURI ()
  {
    return m_sSMPHost;
  }

  /**
   * @return The HTTP client settings to be configured. Never <code>null</code>.
   * @since 8.0.1
   */
  @Nonnull
  @ReturnsMutableObject
  public final SMPHttpClientSettings httpClientSettings ()
  {
    return m_aHttpClientSettings;
  }

  /**
   * @return The HTTP proxy to be used to access the SMP server. Is
   *         <code>null</code> by default.
   * @see #getProxyCredentials()
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nullable
  @Deprecated
  public final HttpHost getProxy ()
  {
    return m_aHttpClientSettings.getProxyHost ();
  }

  /**
   * Set the proxy to be used to access the SMP server. Note: proxy
   * authentication must be set explicitly via
   * {@link #setProxyCredentials(Credentials)}<br>
   * Note: if {@link #setUseProxySystemProperties(boolean)} is enabled, any
   * proxy that is set via this method is reset!
   *
   * @param aProxy
   *        May be <code>null</code> to indicate no proxy.
   * @return this for chaining
   * @see #setProxyCredentials(Credentials)
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setProxy (@Nullable final HttpHost aProxy)
  {
    m_aHttpClientSettings.setProxyHost (aProxy);
    return thisAsT ();
  }

  /**
   * @return The HTTP proxy credentials to be used to access the SMP server. Is
   *         <code>null</code> by default. This is only used if a proxy is set.
   * @see #getProxy()
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nullable
  @Deprecated
  public final Credentials getProxyCredentials ()
  {
    return m_aHttpClientSettings.getProxyCredentials ();
  }

  /**
   * Set the proxy credentials to be used to access the SMP server. Note: proxy
   * authentication is of course only used if a proxy server is present!
   *
   * @param aProxyCredentials
   *        May be <code>null</code> to indicate no proxy credentials (the
   *        default). Usually they are of type
   *        {@link org.apache.http.auth.UsernamePasswordCredentials}.
   * @return this for chaining
   * @see #setProxy(HttpHost)
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setProxyCredentials (@Nullable final Credentials aProxyCredentials)
  {
    m_aHttpClientSettings.setProxyCredentials (aProxyCredentials);
    return thisAsT ();
  }

  /**
   * @return The hosts for which non HTTP proxy should be used. Never
   *         <code>null</code> but maybe empty.
   * @see #getProxy()
   * @since 6.2.4
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @ReturnsMutableObject
  @Deprecated
  public final ICommonsOrderedSet <String> nonProxyHosts ()
  {
    return m_aHttpClientSettings.nonProxyHosts ();
  }

  /**
   * @return <code>true</code> if the system properties for HTTP proxy handling
   *         are enabled, <code>false</code> if they are disabled. By default
   *         they are disabled.
   * @since 5.2.2
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  public final boolean isUseProxySystemProperties ()
  {
    return m_aHttpClientSettings.isUseSystemProperties ();
  }

  /**
   * Set the usage of the HTTP proxy system properties. This must be enabled if
   * e.g. non-proxy hosts should be supported (see issue #9). For backwards
   * compatibility this is disabled by default. If the system properties are
   * enabled, the proxy set via {@link #setProxy(HttpHost)} is automatically
   * reset, because the manual proxy host has precedence over the system
   * properties (internally in Apache HTTPClient) - use the 'http.proxyHost'
   * system property instead!<br>
   * Note: since v5.2.4 this property can be configured via an SMP client
   * properties file (see
   * {@link SMPClientConfiguration#isUseProxySystemProperties()}).<br>
   * Supported properties are (source:
   * http://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/client/HttpClientBuilder.html):
   * <ul>
   * <li>ssl.TrustManagerFactory.algorithm</li>
   * <li>javax.net.ssl.trustStoreType</li>
   * <li>javax.net.ssl.trustStore</li>
   * <li>javax.net.ssl.trustStoreProvider</li>
   * <li>javax.net.ssl.trustStorePassword</li>
   * <li>ssl.KeyManagerFactory.algorithm</li>
   * <li>javax.net.ssl.keyStoreType</li>
   * <li>javax.net.ssl.keyStore</li>
   * <li>javax.net.ssl.keyStoreProvider</li>
   * <li>javax.net.ssl.keyStorePassword</li>
   * <li>https.protocols</li>
   * <li>https.cipherSuites</li>
   * <li>http.proxyHost</li>
   * <li>http.proxyPort</li>
   * <li>http.nonProxyHosts</li>
   * <li>http.keepAlive</li>
   * <li>http.maxConnections</li>
   * <li>http.agent</li>
   * </ul>
   *
   * @param bUseProxySystemProperties
   *        <code>true</code> to use system properties, <code>false</code> to
   *        not use them.
   * @return this for chaining
   * @since 5.2.2
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setUseProxySystemProperties (final boolean bUseProxySystemProperties)
  {
    m_aHttpClientSettings.setUseSystemProperties (bUseProxySystemProperties);
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if DNS client caching is enabled (default),
   *         <code>false</code> if it is disabled.
   * @since 5.2.5
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  public final boolean isUseDNSClientCache ()
  {
    return m_aHttpClientSettings.isUseDNSClientCache ();
  }

  /**
   * Enable or disable DNS client caching. By default caching is enabled.
   *
   * @param bUseDNSClientCache
   *        <code>true</code> to use DNS caching, <code>false</code> to disable
   *        it.
   * @return this for chaining
   * @since 5.2.5
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setUseDNSClientCache (final boolean bUseDNSClientCache)
  {
    m_aHttpClientSettings.setUseDNSClientCache (bUseDNSClientCache);
    return thisAsT ();
  }

  /**
   * @return The connection timeout in milliseconds. Defaults to 5000 (5 secs).
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  public final int getConnectionTimeoutMS ()
  {
    return m_aHttpClientSettings.getConnectionTimeoutMS ();
  }

  /**
   * Set the connection timeout in milliseconds.
   *
   * @param nConnectionTimeoutMS
   *        The connection timeout milliseconds to use. Only values &gt; 0 are
   *        considered.
   * @return this for chaining
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setConnectionTimeoutMS (final int nConnectionTimeoutMS)
  {
    m_aHttpClientSettings.setConnectionTimeoutMS (nConnectionTimeoutMS);
    return thisAsT ();
  }

  /**
   * @return The request timeout in milliseconds. Defaults to 10000 (10 secs).
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  public final int getRequestTimeoutMS ()
  {
    return m_aHttpClientSettings.getSocketTimeoutMS ();
  }

  /**
   * Set the request timeout in milliseconds.
   *
   * @param nRequestTimeoutMS
   *        The request timeout milliseconds to use. Only values &gt; 0 are
   *        considered.
   * @return this for chaining
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Nonnull
  @Deprecated
  public final IMPLTYPE setRequestTimeoutMS (final int nRequestTimeoutMS)
  {
    m_aHttpClientSettings.setSocketTimeoutMS (nRequestTimeoutMS);
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_VERIFY_SIGNATURE}).
   * @since 5.2.1
   * @deprecated since 8.0.3; Use {@link #isVerifySignature()} instead
   */
  @Deprecated
  public final boolean isCheckCertificate ()
  {
    return isVerifySignature ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_VERIFY_SIGNATURE}).
   * @since 8.0.3
   */
  public final boolean isVerifySignature ()
  {
    return m_bVerifySignature;
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!<br>
   * Uses the trust store configured in the SMP client configuration.
   *
   * @param bVerifySignature
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 5.2.1
   * @deprecated since 8.0.3; Use {@link #setVerifySignature(boolean)} instead
   */
  @Deprecated
  @Nonnull
  public final IMPLTYPE setCheckCertificate (final boolean bVerifySignature)
  {
    return setVerifySignature (bVerifySignature);
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!<br>
   * Uses the trust store configured in the SMP client configuration.
   *
   * @param bVerifySignature
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 8.0.3
   */
  @Nonnull
  public final IMPLTYPE setVerifySignature (final boolean bVerifySignature)
  {
    m_bVerifySignature = bVerifySignature;
    return thisAsT ();
  }

  /**
   * @return The trust store to be used for verifying the signature. May be
   *         <code>null</code> if an invalid trust store is configured.
   * @since 8.1.1
   */
  @Nullable
  public final KeyStore getTrustStore ()
  {
    return m_aTrustStore;
  }

  /**
   * Set the trust store to be used.
   *
   * @param aTrustStore
   *        The trust store to be used. May not be <code>null</code>.
   * @return this for chaining
   * @since 8.1.1
   */
  @Nonnull
  public final IMPLTYPE setTrustStore (@Nonnull final KeyStore aTrustStore)
  {
    ValueEnforcer.notNull (aTrustStore, "TrustStore");
    m_aTrustStore = aTrustStore;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP redirects should be followed,
   *         <code>false</code> if not. By default this check is enabled (see
   *         {@link #DEFAULT_FOLLOW_REDIRECTS}).
   * @since 7.0.6
   */
  public final boolean isFollowSMPRedirects ()
  {
    return m_bFollowSMPRedirects;
  }

  /**
   * Should the SMP client follow the SMP redirects that can be found in service
   * registrations. Enabled by default.
   *
   * @param bFollowSMPRedirects
   *        <code>true</code> to follow SMP redirects (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 7.0.6
   */
  @Nonnull
  public final IMPLTYPE setFollowSMPRedirects (final boolean bFollowSMPRedirects)
  {
    m_bFollowSMPRedirects = bFollowSMPRedirects;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if responses should be checked against the XML
   *         Schemas, <code>false</code> if not. By default this check is
   *         enabled (see {@link #DEFAULT_XML_SCHEMA_VALIDATION}).
   * @since 8.0.5
   */
  public final boolean isXMLSchemaValidation ()
  {
    return m_bXMLSchemaValidation;
  }

  /**
   * Should the SMP client perform XML Schema validation or not. Enabled by
   * default.
   *
   * @param bXMLSchemaValidation
   *        <code>true</code> to perform XML Schema validation,
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 8.0.5
   */
  @Nonnull
  public final IMPLTYPE setXMLSchemaValidation (final boolean bXMLSchemaValidation)
  {
    m_bXMLSchemaValidation = bXMLSchemaValidation;
    return thisAsT ();
  }

  /**
   * @return The custom user agent HTTP header to be used. <code>null</code> by
   *         default.
   * @since 7.0.3
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  @Nullable
  public final String getUserAgent ()
  {
    return m_aHttpClientSettings.getUserAgent ();
  }

  /**
   * Set a custom user agent HTTP header to be used. If none is set, the default
   * from the underlying Apache HTTP Client library is used.
   *
   * @param sUserAgent
   *        The custom user agent to be used. May be <code>null</code>.
   * @return this for chaining
   * @since 7.0.3
   * @deprecated since v8.0.1; Use {@link #httpClientSettings()} instead
   */
  @Deprecated
  @Nonnull
  public final IMPLTYPE setUserAgent (@Nullable final String sUserAgent)
  {
    m_aHttpClientSettings.setUserAgent (sUserAgent);
    return thisAsT ();
  }

  @Nonnull
  @OverrideOnDemand
  protected HttpContext createHttpContext ()
  {
    return HttpClientContext.create ();
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing
   * the PEPPOL Directory BusinessCard API. Compared to
   * {@link #executeGenericRequest(HttpUriRequest, ResponseHandler)} this method
   * does NOT convert the {@link IOException} from HTTP communication problems
   * to {@link IOException}.
   *
   * @param aRequest
   *        The request to be executed. The proxy + connection and request
   *        timeout are set in this method.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws IOException
   *         On HTTP communication error
   * @see #executeGenericRequest(HttpUriRequest, ResponseHandler)
   * @param <T>
   *        Expected response type
   */
  @Nonnull
  public <T> T executeRequest (@Nonnull final HttpUriRequest aRequest,
                               @Nonnull final ResponseHandler <T> aResponseHandler) throws IOException
  {
    final HttpContext aHttpContext = createHttpContext ();
    try (final HttpClientManager aHttpClientMgr = HttpClientManager.create (m_aHttpClientSettings))
    {
      return aHttpClientMgr.execute (aRequest, aHttpContext, aResponseHandler);
    }
  }

  /**
   * Convert the passed generic HTTP exception into a more specific exception.
   *
   * @param ex
   *        The generic exception. May not be <code>null</code>.
   * @return A new SMP specific exception, using the passed exception as the
   *         cause.
   */
  @Nonnull
  public static SMPClientException getConvertedException (@Nonnull final Exception ex)
  {
    if (ex instanceof SMPClientException)
      return (SMPClientException) ex;

    if (ex instanceof HttpResponseException)
    {
      final HttpResponseException hex = (HttpResponseException) ex;
      final int nHttpStatus = hex.getStatusCode ();
      switch (nHttpStatus)
      {
        case HttpStatus.SC_BAD_REQUEST:
          return new SMPClientBadRequestException (hex);
        case HttpStatus.SC_FORBIDDEN:
          return new SMPClientUnauthorizedException (hex);
        case HttpStatus.SC_NOT_FOUND:
          return new SMPClientNotFoundException (hex);
        default:
          return new SMPClientException ("Error thrown with HTTP status code " + nHttpStatus, hex);
      }
    }

    // Special case
    if (ex instanceof UnknownHostException)
      return new SMPClientNotFoundException ((UnknownHostException) ex);
    if (ex instanceof ConnectException)
      return new SMPClientNotFoundException ((ConnectException) ex);

    // For new SMPClientBadResponseException
    if (ex instanceof ClientProtocolException && ex.getCause () instanceof SMPClientException)
      return (SMPClientException) ex.getCause ();

    // Generic version
    return new SMPClientException ("Unknown error thrown by SMP server (" + ex.getMessage () + ")", ex);
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing
   * the PEPPOL Directory BusinessCard API. This is equivalent to
   * {@link #executeRequest(HttpUriRequest, ResponseHandler)} but includes the
   * conversion of Exceptions to {@link SMPClientException} objects.
   *
   * @param aRequest
   *        The request to be executed. The proxy + connection and request
   *        timeout are set in this method.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws SMPClientException
   *         One of the converted exceptions
   * @param <T>
   *        Expected response type
   * @see #executeRequest(HttpUriRequest, ResponseHandler)
   * @see #getConvertedException(Exception)
   */
  @Nonnull
  public <T> T executeGenericRequest (@Nonnull final HttpUriRequest aRequest,
                                      @Nonnull final ResponseHandler <T> aResponseHandler) throws SMPClientException
  {
    try
    {
      return executeRequest (aRequest, aResponseHandler);
    }
    catch (final Exception ex)
    {
      throw getConvertedException (ex);
    }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("SMPHost", m_sSMPHost)
                                       .append ("VerifySignature", m_bVerifySignature)
                                       .append ("FollowSMPRedirects", m_bFollowSMPRedirects)
                                       .append ("XMLSchemaValidation", m_bXMLSchemaValidation)
                                       .append ("HttpClientSettings", m_aHttpClientSettings)
                                       .getToString ();
  }
}
