/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.httpclient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.CommonsLinkedHashSet;
import com.helger.commons.collection.impl.ICommonsOrderedSet;
import com.helger.commons.http.CHttpHeader;
import com.helger.commons.mime.CMimeType;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.traits.IGenericImplTrait;
import com.helger.httpclient.HttpClientFactory;
import com.helger.httpclient.HttpClientManager;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.peppol.smpclient.exception.SMPClientBadRequestException;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;

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
  public static final int DEFAULT_CONNECTION_TIMEOUT_MS = 5_000;
  public static final int DEFAULT_REQUEST_TIMEOUT_MS = 10_000;

  // The default text/xml content type uses iso-8859-1!
  protected static final ContentType CONTENT_TYPE_TEXT_XML = ContentType.create (CMimeType.TEXT_XML.getAsString (),
                                                                                 StandardCharsets.UTF_8);

  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractGenericSMPClient.class);

  /**
   * The string representation of the SMP host URL, always ending with a
   * trailing slash!
   */
  private final String m_sSMPHost;
  private HttpHost m_aProxy;
  private Credentials m_aProxyCredentials;
  private final ICommonsOrderedSet <String> m_aNonProxyHosts = new CommonsLinkedHashSet <> ();
  private boolean m_bUseProxySystemProperties;
  private boolean m_bUseDNSClientCache;
  private int m_nConnectionTimeoutMS;
  private int m_nRequestTimeoutMS;
  private boolean m_bCheckCertificate = SMPHttpResponseHandlerSigned.DEFAULT_CHECK_CERTIFICATE;
  private String m_sUserAgent;

  /**
   * Constructor with a direct SMP URL.<br>
   * Remember: must be HTTP and using port 80 only!
   *
   * @param aSMPHost
   *        The address of the SMP service. Must be port 80 and basic http only
   *        (no https!). Example: http://smpcompany.company.org
   */
  public AbstractGenericSMPClient (@Nonnull final URI aSMPHost)
  {
    ValueEnforcer.notNull (aSMPHost, "SMPHost");

    if (!"http".equals (aSMPHost.getScheme ()))
      if (LOGGER.isWarnEnabled ())
        LOGGER.warn ("SMP URI " + aSMPHost + " does not use the expected http scheme!");

    // getPort () returns -1 if none was explicitly specified
    if (aSMPHost.getPort () != 80 && aSMPHost.getPort () != -1)
      if (LOGGER.isWarnEnabled ())
        LOGGER.warn ("SMP URI " + aSMPHost + " is not running on port 80!");

    // Build string and ensure it ends with a "/"
    final String sSMPHost = aSMPHost.toString ();
    m_sSMPHost = sSMPHost.endsWith ("/") ? sSMPHost : sSMPHost + '/';

    // Set default proxy from configuration file
    m_aProxy = SMPClientConfiguration.getHttpProxy ();
    m_aProxyCredentials = SMPClientConfiguration.getHttpProxyCredentials ();
    final String sNonProxyHosts = SMPClientConfiguration.getNonProxyHosts ();
    if (StringHelper.hasText (sNonProxyHosts))
      StringHelper.explode ('|', sNonProxyHosts, sHost -> {
        final String sTrimmedHost = sHost.trim ();
        if (StringHelper.hasText (sTrimmedHost))
          m_aNonProxyHosts.add (sTrimmedHost);
      });
    m_bUseProxySystemProperties = SMPClientConfiguration.isUseProxySystemProperties ();
    m_bUseDNSClientCache = SMPClientConfiguration.isUseDNSClientCache ();
    m_nConnectionTimeoutMS = SMPClientConfiguration.getConnectionTimeoutMS ();
    m_nRequestTimeoutMS = SMPClientConfiguration.getRequestTimeoutMS ();
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
   * @return The HTTP proxy to be used to access the SMP server. Is
   *         <code>null</code> by default.
   * @see #getProxyCredentials()
   */
  @Nullable
  public final HttpHost getProxy ()
  {
    return m_aProxy;
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
   */
  @Nonnull
  public final IMPLTYPE setProxy (@Nullable final HttpHost aProxy)
  {
    m_aProxy = aProxy;
    if (aProxy != null && m_bUseProxySystemProperties)
    {
      LOGGER.warn ("Since an explicit Proxy host for all servers is defined, the usage of the system properties is disabled.");
      m_bUseProxySystemProperties = false;
    }
    return thisAsT ();
  }

  /**
   * @return The HTTP proxy credentials to be used to access the SMP server. Is
   *         <code>null</code> by default. This is only used if a proxy is set.
   * @see #getProxy()
   */
  @Nullable
  public final Credentials getProxyCredentials ()
  {
    return m_aProxyCredentials;
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
   */
  @Nonnull
  public final IMPLTYPE setProxyCredentials (@Nullable final Credentials aProxyCredentials)
  {
    m_aProxyCredentials = aProxyCredentials;
    return thisAsT ();
  }

  /**
   * @return The hosts for which non HTTP proxy should be used. Never
   *         <code>null</code> but maybe empty.
   * @see #getProxy()
   * @since 6.2.4
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsOrderedSet <String> nonProxyHosts ()
  {
    return m_aNonProxyHosts;
  }

  /**
   * @return <code>true</code> if the system properties for HTTP proxy handling
   *         are enabled, <code>false</code> if they are disabled. By default
   *         they are disabled.
   * @since 5.2.2
   */
  public final boolean isUseProxySystemProperties ()
  {
    return m_bUseProxySystemProperties;
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
   */
  @Nonnull
  public final IMPLTYPE setUseProxySystemProperties (final boolean bUseProxySystemProperties)
  {
    m_bUseProxySystemProperties = bUseProxySystemProperties;
    if (bUseProxySystemProperties && m_aProxy != null)
    {
      LOGGER.warn ("Since the proxy system properties should be used, the explicit Proxy is removed.");
      m_aProxy = null;
    }
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if DNS client caching is enabled (default),
   *         <code>false</code> if it is disabled.
   * @since 5.2.5
   */
  public final boolean isUseDNSClientCache ()
  {
    return m_bUseDNSClientCache;
  }

  /**
   * Enable or disable DNS client caching. By default caching is enabled.
   *
   * @param bUseDNSClientCache
   *        <code>true</code> to use DNS caching, <code>false</code> to disable
   *        it.
   * @return this for chaining
   * @since 5.2.5
   */
  @Nonnull
  public final IMPLTYPE setUseDNSClientCache (final boolean bUseDNSClientCache)
  {
    m_bUseDNSClientCache = bUseDNSClientCache;
    return thisAsT ();
  }

  /**
   * @return The connection timeout in milliseconds. Defaults to 5000 (5 secs).
   */
  public final int getConnectionTimeoutMS ()
  {
    return m_nConnectionTimeoutMS;
  }

  /**
   * Set the connection timeout in milliseconds.
   *
   * @param nConnectionTimeoutMS
   *        The connection timeout milliseconds to use. Only values &gt; 0 are
   *        considered.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE setConnectionTimeoutMS (final int nConnectionTimeoutMS)
  {
    m_nConnectionTimeoutMS = nConnectionTimeoutMS;
    return thisAsT ();
  }

  /**
   * @return The request timeout in milliseconds. Defaults to 10000 (10 secs).
   */
  public final int getRequestTimeoutMS ()
  {
    return m_nRequestTimeoutMS;
  }

  /**
   * Set the request timeout in milliseconds.
   *
   * @param nRequestTimeoutMS
   *        The request timeout milliseconds to use. Only values &gt; 0 are
   *        considered.
   * @return this for chaining
   */
  @Nonnull
  public final IMPLTYPE setRequestTimeoutMS (final int nRequestTimeoutMS)
  {
    m_nRequestTimeoutMS = nRequestTimeoutMS;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_CHECK_CERTIFICATE}).
   * @since 5.2.1
   */
  public final boolean isCheckCertificate ()
  {
    return m_bCheckCertificate;
  }

  /**
   * Check the certificate retrieved from a signed SMP response? This may be
   * helpful for debugging and testing of SMP client connections!
   *
   * @param bCheckCertificate
   *        <code>true</code> to enable SMP response checking (on by default) or
   *        <code>false</code> to disable it.
   * @return this for chaining
   * @since 5.2.1
   */
  @Nonnull
  public final IMPLTYPE setCheckCertificate (final boolean bCheckCertificate)
  {
    m_bCheckCertificate = bCheckCertificate;
    return thisAsT ();
  }

  /**
   * @return The custom user agent HTTP header to be used. <code>null</code> by
   *         default.
   * @since 7.0.3
   */
  @Nullable
  public final String getUserAgent ()
  {
    return m_sUserAgent;
  }

  /**
   * Set a custom user agent HTTP header to be used. If none is set, the default
   * from the underlying Apache HTTP Client library is used.
   *
   * @param sUserAgent
   *        The custom user agent to be used. May be <code>null</code>.
   * @return this for chaining
   * @since 7.0.3
   */
  @Nonnull
  public final IMPLTYPE setUserAgent (@Nullable final String sUserAgent)
  {
    m_sUserAgent = sUserAgent;
    return thisAsT ();
  }

  @Nonnull
  @OverrideOnDemand
  protected HttpContext createHttpContext ()
  {
    final RequestConfig.Builder aRC = RequestConfig.custom ();
    if (m_nConnectionTimeoutMS > 0)
      aRC.setConnectTimeout (m_nConnectionTimeoutMS);
    if (m_nRequestTimeoutMS > 0)
      aRC.setSocketTimeout (m_nRequestTimeoutMS);

    final HttpClientContext aHttpContext = HttpClientContext.create ();
    aHttpContext.setRequestConfig (aRC.build ());
    return aHttpContext;
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
    final HttpClientFactory aHCFactory = new HttpClientFactory ();
    aHCFactory.setUseSystemProperties (m_bUseProxySystemProperties);
    aHCFactory.setUseDNSClientCache (m_bUseDNSClientCache);
    if (!m_bUseProxySystemProperties)
    {
      aHCFactory.setProxy (m_aProxy, m_aProxyCredentials);
      aHCFactory.nonProxyHosts ().addAll (m_aNonProxyHosts);
    }

    final HttpContext aHttpContext = createHttpContext ();
    try (final HttpClientManager aHttpClientMgr = new HttpClientManager (aHCFactory))
    {
      if (StringHelper.hasText (m_sUserAgent))
        aRequest.addHeader (CHttpHeader.USER_AGENT, m_sUserAgent);
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
                                       .appendIfNotNull ("Proxy", m_aProxy)
                                       .append ("UseProxySystemProperties", m_bUseProxySystemProperties)
                                       .append ("UseDNSClientCache", m_bUseDNSClientCache)
                                       .append ("ConnectionTimeoutMS", m_nConnectionTimeoutMS)
                                       .append ("RequestTimeoutMS", m_nRequestTimeoutMS)
                                       .append ("CheckCertificate", m_bCheckCertificate)
                                       .getToString ();
  }
}
