package com.helger.peppol.httpclient;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import com.helger.commons.io.stream.StreamHelper;
import com.helger.httpclient.HttpClientManager;

/**
 * This class contains the central HTTP client manager for the SMP client.
 * Ideally you call {@link #close()} before your application shuts down.
 *
 * @author Philip Helger
 */
public final class GlobalSMPClientHttpClientManager
{
  private static HttpClientManager s_aHttpClientMgr = new HttpClientManager ();

  private GlobalSMPClientHttpClientManager ()
  {}

  /**
   * Free up all resources used by the internal {@link HttpClientManager}.
   */
  public static void close ()
  {
    StreamHelper.close (s_aHttpClientMgr);
    s_aHttpClientMgr = null;
  }

  private static void _checkClosed ()
  {
    if (s_aHttpClientMgr == null)
      throw new IllegalStateException ("The SMP client HTTP client manager is already closed!");
  }

  @Nullable
  public static <T> T execute (@Nonnull final HttpUriRequest aRequest,
                               @Nullable final HttpContext aHttpContext,
                               @Nonnull final ResponseHandler <T> aResponseHandler) throws IOException
  {
    _checkClosed ();
    return s_aHttpClientMgr.execute (aRequest, aHttpContext, aResponseHandler);
  }
}
