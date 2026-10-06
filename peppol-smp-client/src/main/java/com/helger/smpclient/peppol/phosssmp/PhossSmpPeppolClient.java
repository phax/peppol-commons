/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.smpclient.peppol.phosssmp;

import java.net.URI;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.peppol.SMPClient;
import com.helger.smpclient.peppol.marshal.SMPMarshallerEndpointType;
import com.helger.smpclient.phosssmp.IPhossSmpClient;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.peppol.smp1.EndpointType;

/**
 * This class is used for calling the REST interface of phoss SMP, configured for the Peppol SMP
 * REST type. It contains all the reading and writing methods of {@link SMPClient} and additionally
 * all the phoss SMP specific methods of {@link IPhossSmpClient}.
 *
 * @author Philip Helger
 * @since 13.2.0
 */
public class PhossSmpPeppolClient extends SMPClient implements IPhossSmpClient <EndpointType>
{
  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param aSMLInfo
   *        The SML to be used. Required to build the SMP access URI.
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  public PhossSmpPeppolClient (@NonNull final ISMPURLProvider aURLProvider,
                               @NonNull final IParticipantIdentifier aParticipantIdentifier,
                               @NonNull final ISMLInfo aSMLInfo) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, aSMLInfo);
  }

  /**
   * Constructor with SML lookup
   *
   * @param aURLProvider
   *        The URL provider to be used. May not be <code>null</code>.
   * @param aParticipantIdentifier
   *        The participant identifier to be used. Required to build the SMP access URI.
   * @param sSMLZoneName
   *        The SML DNS zone name to be used. Required to build the SMP access URI. Must end with a
   *        trailing dot (".") and may neither be <code>null</code> nor empty to build a correct
   *        URL. May not start with "http://". Example: <code>sml.peppolcentral.org.</code>
   * @throws SMPDNSResolutionException
   *         if DNS resolution fails
   */
  public PhossSmpPeppolClient (@NonNull final ISMPURLProvider aURLProvider,
                               @NonNull final IParticipantIdentifier aParticipantIdentifier,
                               @NonNull @Nonempty final String sSMLZoneName) throws SMPDNSResolutionException
  {
    super (aURLProvider, aParticipantIdentifier, sSMLZoneName);
  }

  /**
   * Constructor with a direct SMP URL.
   *
   * @param aSMPHost
   *        The address of the SMP service. May not be <code>null</code>.
   */
  public PhossSmpPeppolClient (@NonNull final URI aSMPHost)
  {
    super (aSMPHost);
  }

  @Nullable
  public String getEndpointAsXMLString (@NonNull final EndpointType aEndpoint)
  {
    return new SMPMarshallerEndpointType ().getAsString (aEndpoint);
  }
}
