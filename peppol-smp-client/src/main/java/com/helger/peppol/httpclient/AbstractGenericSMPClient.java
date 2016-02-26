package com.helger.peppol.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.lang.GenericReflection;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppol.smpclient.SMPClientConfiguration;
import com.helger.peppol.smpclient.exception.SMPClientBadRequestException;
import com.helger.peppol.smpclient.exception.SMPClientException;
import com.helger.peppol.smpclient.exception.SMPClientNotFoundException;
import com.helger.peppol.smpclient.exception.SMPClientUnauthorizedException;

public abstract class AbstractGenericSMPClient <IMPLTYPE extends AbstractGenericSMPClient <IMPLTYPE>>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (AbstractGenericSMPClient.class);

  /**
   * The string representation of the SMP host URL, always ending with a
   * trailing slash!
   */
  private final String m_sSMPHost;

  private HttpHost m_aProxy;
  private int m_nConnectionTimeoutMS = 5000;
  private int m_nRequestTimeoutMS = 10000;

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

  @Nonnull
  protected final IMPLTYPE thisAsT ()
  {
    return GenericReflection.uncheckedCast (this);
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
   * authentication is currently not supported!
   *
   * @param aProxy
   *        May be <code>null</code> to indicate no proxy.
   * @return this for chaining
   */
  @Nonnull
  public IMPLTYPE setProxy (@Nullable final HttpHost aProxy)
  {
    m_aProxy = aProxy;
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
   * The main execution routine. Overwrite this method to add additional
   * properties to the call.
   *
   * @param aRequest
   *        The request to be executed. Never <code>null</code>.
   * @return The HTTP execution response. Never <code>null</code>.
   * @throws IOException
   *         On HTTP error
   * @see #setProxy(HttpHost)
   * @see #setConnectionTimeoutMS(int)
   * @see #setRequestTimeoutMS(int)
   */
  @Nonnull
  @OverrideOnDemand
  protected Response executeRequest (@Nonnull final Request aRequest) throws IOException
  {
    if (m_aProxy != null)
      aRequest.viaProxy (m_aProxy);
    if (m_nConnectionTimeoutMS > 0)
      aRequest.connectTimeout (m_nConnectionTimeoutMS);
    if (m_nRequestTimeoutMS > 0)
      aRequest.socketTimeout (m_nRequestTimeoutMS);

    return aRequest.execute ();
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing
   * the PEPPOL Directory BusinessCard API. Compared to
   * {@link #executeGenericRequest(Request, ResponseHandler)} this method does
   * NOT convert the {@link IOException} from HTTP communication problems to
   * {@link IOException}.
   *
   * @param aRequest
   *        The request to be executed. The proxy + connection and request
   *        timeout are set in this method.
   * @param aResponseHandler
   *        The response handler to be used. May not be <code>null</code>.
   * @return The return value of the response handler.
   * @throws IOException
   *         On HTTP communication error
   * @see #executeGenericRequest(Request, ResponseHandler)
   */
  @Nonnull
  public <T> T executeRequest (@Nonnull final Request aRequest,
                               @Nonnull final ResponseHandler <T> aResponseHandler) throws IOException
  {
    return executeRequest (aRequest).handleResponse (aResponseHandler);
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

    // Generic version
    return new SMPClientException ("Unknown error thrown by SMP server (" + ex.getMessage () + ")", ex);
  }

  /**
   * Execute a generic request on the SMP. This is e.g. helpful for accessing
   * the PEPPOL Directory BusinessCard API. This is equivalent to
   * {@link #executeRequest(Request, ResponseHandler)} but includes the
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
   * @see #executeRequest(Request, ResponseHandler)
   * @see #getConvertedException(Exception)
   */
  @Nonnull
  public <T> T executeGenericRequest (@Nonnull final Request aRequest,
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
                                       .toString ();
  }
}
