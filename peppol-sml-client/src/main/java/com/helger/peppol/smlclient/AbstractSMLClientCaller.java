/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
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
package com.helger.peppol.smlclient;

import java.net.URL;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;

/**
 * Abstract base class for the SML client caller.
 *
 * @author Philip Helger
 */
public abstract class AbstractSMLClientCaller
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (AbstractSMLClientCaller.class);

  private final URL m_aEndpointAddress;
  private SSLSocketFactory m_aSSLSocketFactory;
  private HostnameVerifier m_aHostnameVerifier;

  /**
   * Creates a service caller for the service meta data interface
   *
   * @param aEndpointAddress
   *        The address of the SML management interface. May not be
   *        <code>null</code>.
   */
  public AbstractSMLClientCaller (@Nonnull final URL aEndpointAddress)
  {
    ValueEnforcer.notNull (aEndpointAddress, "EndpointAddress");
    m_aEndpointAddress = aEndpointAddress;

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("Using SML endpoint address '" + m_aEndpointAddress.toExternalForm () + "'");
  }

  /**
   * @return The endpoint address as specified in the constructor. Never
   *         <code>null</code>.
   */
  @Nonnull
  public URL getEndpointAddress ()
  {
    return m_aEndpointAddress;
  }

  /**
   * @return The {@link SSLSocketFactory} to be used by this client. Is
   *         <code>null</code> by default.
   */
  @Nullable
  public SSLSocketFactory getSSLSocketFactory ()
  {
    return m_aSSLSocketFactory;
  }

  /**
   * Change the {@link SSLSocketFactory} to be used by this client.
   *
   * @param aSSLSocketFactory
   *        The factory to use. Maybe <code>null</code> to indicate, that the
   *        default {@link SSLSocketFactory} is to be used.
   */
  public void setSSLSocketFactory (@Nullable final SSLSocketFactory aSSLSocketFactory)
  {
    m_aSSLSocketFactory = aSSLSocketFactory;
  }

  /**
   * @return The {@link HostnameVerifier} to be used by this client. Is
   *         <code>null</code> by default.
   */
  @Nullable
  public HostnameVerifier getHostnameVerifier ()
  {
    return m_aHostnameVerifier;
  }

  /**
   * Change the {@link HostnameVerifier} to be used by this client.
   *
   * @param aHostnameVerifier
   *        The factory to use. Maybe <code>null</code> to indicate, that the
   *        default {@link HostnameVerifier} is to be used.
   */
  public void setHostnameVerifier (@Nullable final HostnameVerifier aHostnameVerifier)
  {
    m_aHostnameVerifier = aHostnameVerifier;
  }

  @OverrideOnDemand
  @OverridingMethodsMustInvokeSuper
  protected void applyWSSettingsToBindingProvider (@Nonnull final BindingProvider aBP)
  {
    aBP.getRequestContext ().put (BindingProvider.ENDPOINT_ADDRESS_PROPERTY, m_aEndpointAddress.toString ());
    if (m_aSSLSocketFactory != null)
    {
      aBP.getRequestContext ().put ("com.sun.xml.ws.transport.https.client.SSLSocketFactory", m_aSSLSocketFactory);
      aBP.getRequestContext ().put ("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", m_aSSLSocketFactory);
    }
    if (m_aHostnameVerifier != null)
    {
      aBP.getRequestContext ().put ("com.sun.xml.ws.transport.https.client.hostname.verifier", m_aHostnameVerifier);
      aBP.getRequestContext ().put ("com.sun.xml.internal.ws.transport.https.client.hostname.verifier", m_aHostnameVerifier);
    }

    // Add the WSSE header handler for "mustUnderstand"
    @SuppressWarnings ("rawtypes")
    final List <Handler> aHandlers = aBP.getBinding ().getHandlerChain ();
    aHandlers.add (new WSSESoapHandler ());
    aBP.getBinding ().setHandlerChain (aHandlers);
  }
}
