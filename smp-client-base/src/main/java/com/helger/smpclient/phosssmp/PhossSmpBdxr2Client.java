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
package com.helger.smpclient.phosssmp;

import java.net.URI;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.edelivery.sml.ISMLBase;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.bdxr2.BDXR2Client;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerEndpoint;
import com.helger.smpclient.url.ISMPURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.xsds.bdxr.smp2.ac.EndpointType;

/**
 * This class is used for calling the REST interface of phoss SMP, configured for the OASIS BDXR SMP
 * v2 REST type. It contains all the reading and writing methods of {@link BDXR2Client} and
 * additionally all the phoss SMP specific methods of {@link IPhossSmpClient}.
 *
 * @author Philip Helger
 * @since 13.1.2
 */
public class PhossSmpBdxr2Client extends BDXR2Client implements IPhossSmpClient <EndpointType>
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
  public PhossSmpBdxr2Client (@NonNull final ISMPURLProvider aURLProvider,
                              @NonNull final IParticipantIdentifier aParticipantIdentifier,
                              @NonNull final ISMLBase aSMLInfo) throws SMPDNSResolutionException
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
  public PhossSmpBdxr2Client (@NonNull final ISMPURLProvider aURLProvider,
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
  public PhossSmpBdxr2Client (@NonNull final URI aSMPHost)
  {
    super (aSMPHost);
  }

  @Nullable
  public String getEndpointAsXMLString (@NonNull final EndpointType aEndpoint)
  {
    return new BDXR2MarshallerEndpoint ().setUseSchema (isXMLSchemaValidation ()).getAsString (aEndpoint);
  }
}
