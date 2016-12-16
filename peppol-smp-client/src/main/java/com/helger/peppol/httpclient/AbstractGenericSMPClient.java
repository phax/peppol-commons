/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Note: some files, that were not part of the original package are currently
 *   licensed under Apache 2.0 license - https://www.apache.org/licenses/LICENSE-2.0
 *   The respective files contain a special class header!
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.httpclient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.traits.IGenericImplTrait;
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
public abstract class AbstractGenericSMPClient <IMPLTYPE extends AbstractGenericSMPClient <IMPLTYPE>>
                                               implements IGenericImplTrait <IMPLTYPE>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (AbstractGenericSMPClient.class);

  /**
   * The string representation of the SMP host URL, always ending with a
   * trailing slash!
   */
  private final String m_sSMPHost;

  private HttpHost m_aProxy;
  private boolean m_bUseProxySystemProperties = false;
  private int m_nConnectionTimeoutMS = 5000;
  private int m_nRequestTimeoutMS = 10000;

  private boolean m_bCheckCertificate = SMPHttpResponseHandlerSigned.DEFAULT_CHECK_CERTIFICATE;

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
      s_aLogger.warn ("SMP URI " + aSMPHost + " does not use the expected http scheme!");
    // getPort () returns -1 if none was explicitly specified
    if (aSMPHost.getPort () != 80 && aSMPHost.getPort () != -1)
      s_aLogger.warn ("SMP URI " + aSMPHost + " is not running on port 80!");

    // Build string and ensure it ends with a "/"
    final String sSMPHost = aSMPHost.toString ();
    m_sSMPHost = sSMPHost.endsWith ("/") ? sSMPHost : sSMPHost + '/';

    // Set default proxy from configuration file
    m_aProxy = SMPClientConfiguration.getHttpProxy ();
  }

  /**
   * @return The SMP host URI string we're operating on. Never <code>null</code>
   *         . Always has a trailing "/".
   */
  @Nonnull
  public String getSMPHostURI ()
  {
    return m_sSMPHost;
  }

  /**
   * @return The HTTP proxy to be used to access the SMP server. Is
   *         <code>null</code> by default.
   */
  @Nullable
  public HttpHost getProxy ()
  {
    return m_aProxy;
  }

  /**
   * Set the proxy to be used to access the SMP server. Note: proxy
   * authentication is currently not supported!<br>
   * Note: if {@link #setUseProxySystemProperties(boolean)} is enabled, any
   * proxy that is set via this method is reset!
   *
   * @param aProxy
   *        May be <code>null</code> to indicate no proxy.
   * @return this for chaining
   */
  @Nonnull
  public IMPLTYPE setProxy (@Nullable final HttpHost aProxy)
  {
    m_aProxy = aProxy;
    if (aProxy != null && m_bUseProxySystemProperties)
    {
      s_aLogger.warn ("Since an explicit Proxy host for all servers is defined, the usage of the system properties is disabled.");
      m_bUseProxySystemProperties = false;
    }
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if the system properties for HTTP proxy handling
   *         are enabled, <code>false</code> if they are disabled. By default
   *         they are disabled.
   * @since 5.2.2
   */
  public boolean isUseProxySystemProperties ()
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
   * system property instead! <br>
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
  public IMPLTYPE setUseProxySystemProperties (final boolean bUseProxySystemProperties)
  {
    m_bUseProxySystemProperties = bUseProxySystemProperties;
    if (bUseProxySystemProperties && m_aProxy != null)
    {
      s_aLogger.warn ("Since the proxy system properties should be used, the explicit Proxy is removed.");
      m_aProxy = null;
    }
    return thisAsT ();
  }

  /**
   * @return The connection timeout in milliseconds. Defaults to 5000 (5 secs).
   */
  public int getConnectionTimeoutMS ()
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
  public IMPLTYPE setConnectionTimeoutMS (final int nConnectionTimeoutMS)
  {
    m_nConnectionTimeoutMS = nConnectionTimeoutMS;
    return thisAsT ();
  }

  /**
   * @return The request timeout in milliseconds. Defaults to 10000 (10 secs).
   */
  public int getRequestTimeoutMS ()
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
  public IMPLTYPE setRequestTimeoutMS (final int nRequestTimeoutMS)
  {
    m_nRequestTimeoutMS = nRequestTimeoutMS;
    return thisAsT ();
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
  public IMPLTYPE setCheckCertificate (final boolean bCheckCertificate)
  {
    m_bCheckCertificate = bCheckCertificate;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if SMP client response certificate checking is
   *         enabled, <code>false</code> if it is disabled. By default this
   *         check is enabled (see
   *         {@link SMPHttpResponseHandlerSigned#DEFAULT_CHECK_CERTIFICATE}).
   * @since 5.2.1
   */
  public boolean isCheckCertificate ()
  {
    return m_bCheckCertificate;
  }

  @Nonnull
  private HttpContext _createHttpContext ()
  {
    final RequestConfig.Builder aRC = RequestConfig.custom ();
    if (m_aProxy != null)
      aRC.setProxy (m_aProxy);
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
    final HttpContext aHttpContext = _createHttpContext ();
    try (final HttpClientManager aHttpClientMgr = new HttpClientManager ( () -> new SMPHttpClientFactory (m_bUseProxySystemProperties).createHttpClient ()))
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
      }
      return new SMPClientException ("Error thrown with HTTP status code " + nHttpStatus, hex);
    }

    // Special case
    if (ex instanceof UnknownHostException)
      return new SMPClientNotFoundException ((UnknownHostException) ex);
    if (ex instanceof ConnectException)
      return new SMPClientNotFoundException ((ConnectException) ex);

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
                                       .append ("ConnectionTimeoutMS", m_nConnectionTimeoutMS)
                                       .append ("RequestTimeoutMS", m_nRequestTimeoutMS)
                                       .append ("CheckCertificate", m_bCheckCertificate)
                                       .toString ();
  }
}
