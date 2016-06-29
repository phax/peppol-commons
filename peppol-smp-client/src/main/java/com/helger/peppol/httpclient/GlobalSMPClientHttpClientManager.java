package com.helger.peppol.httpclient;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import com.helger.commons.io.stream.StreamHelper;
import com.helger.httpclient.HttpClientManager;

public final class GlobalSMPClientHttpClientManager
{
  private static final HttpClientManager s_aHttpClientMgr = new HttpClientManager ();

  private GlobalSMPClientHttpClientManager ()
  {}

  /**
   * Free up all resources used by the internal {@link HttpClientManager}.
   */
  public static void close ()
  {
    StreamHelper.close (s_aHttpClientMgr);
  }

  @Nullable
  public static <T> T execute (@Nonnull final HttpUriRequest aRequest,
                               @Nullable final HttpContext aHttpContext,
                               @Nonnull final ResponseHandler <T> aResponseHandler) throws IOException
  {
    return s_aHttpClientMgr.execute (aRequest, aHttpContext, aResponseHandler);
  }
}
