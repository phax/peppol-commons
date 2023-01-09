/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.peppol.smlclient;

import java.net.URL;
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.url.URLHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.bdmsl.BDMSLService;
import com.helger.peppol.smlclient.bdmsl.BDMSLServiceSoap;
import com.helger.peppol.smlclient.bdmsl.BadRequestFault;
import com.helger.peppol.smlclient.bdmsl.InternalErrorFault;
import com.helger.peppol.smlclient.bdmsl.IsAliveType;
import com.helger.peppol.smlclient.bdmsl.NotFoundFault;
import com.helger.peppol.smlclient.bdmsl.PrepareChangeCertificateType;
import com.helger.peppol.smlclient.bdmsl.SMPAdvancedServiceForParticipantType;
import com.helger.peppol.smlclient.bdmsl.ServiceMetadataPublisherServiceForParticipantType;
import com.helger.peppol.smlclient.bdmsl.UnauthorizedFault;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.wsclient.WSClientConfig;

/**
 * A client for the new BDMSL specific "BDMSL service"
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDMSLClient extends WSClientConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BDMSLClient.class);

  public BDMSLClient (@Nonnull final ISMLInfo aSMLInfo)
  {
    super (URLHelper.getAsURL (aSMLInfo.getManagementServiceURL () + "/bdmslservice"));
  }

  /**
   * Constructs a service caller for the manage business identifier interface.
   * <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/cipaservice
   *
   * @param aEndpointAddress
   *        The URL of the CIPA Service interface. May not be <code>null</code>.
   */
  public BDMSLClient (@Nonnull final URL aEndpointAddress)
  {
    super (aEndpointAddress);
  }

  /**
   * Create the main WebService client for the specified endpoint address.
   *
   * @return The WebService port to be used. May not be <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  @OverridingMethodsMustInvokeSuper
  protected BDMSLServiceSoap createWSPort ()
  {
    final BDMSLService aService = new BDMSLService ();
    final BDMSLServiceSoap aPort = aService.getBDMSLServicePort ();
    applyWSSettingsToBindingProvider ((BindingProvider) aPort);
    return aPort;
  }

  /**
   * This operation allows a SMP to prepare a change of its certificate. It is
   * typically called when an SMP has a certificate that is about to expire and
   * already has the new one.<br>
   * This operation MUST be called while the certificate that is already
   * registered in the SML is still valid.
   *
   * @param sNewCertificatePublicKey
   *        The new public key contained in the certificate.
   * @param aMigrationDate
   *        The migration date for the new certificate. Can't be in the past. If
   *        the migrationDate is not empty, then the new certificate MUST be
   *        valid at the date provided in the migrationDate element. i.e. The
   *        migrationDate must be within validFrom and validTo dates of the new
   *        certificate. If the migrationDate element is empty, then the "Valid
   *        From" date is extracted from the certificate and is used as the
   *        migrationDate. In this case, the "Not Before" date of the
   *        certificate must be in the future.
   * @throws com.sun.xml.ws.client.ClientTransportException
   *         if the WS client invocation failed
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         In case of error
   * @throws UnauthorizedFault
   *         In case of error
   */
  public void prepareChangeCertificate (@Nonnull @Nonempty final String sNewCertificatePublicKey,
                                        @Nullable final LocalDate aMigrationDate) throws BadRequestFault,
                                                                                  InternalErrorFault,
                                                                                  NotFoundFault,
                                                                                  UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sNewCertificatePublicKey, "NewCertificatePublicKey");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("prepareChangeCertificate (" + sNewCertificatePublicKey + ", " + aMigrationDate + ")");

    final PrepareChangeCertificateType aBody = new PrepareChangeCertificateType ();
    aBody.setNewCertificatePublicKey (sNewCertificatePublicKey);
    aBody.setMigrationDate (aMigrationDate);
    createWSPort ().prepareChangeCertificate (aBody);
  }

  public void createParticipantIdentifier (@Nonnull @Nonempty final String sSMPID,
                                           @Nonnull final IParticipantIdentifier aParticipantID,
                                           @Nonnull @Nonempty final String sServiceName) throws BadRequestFault,
                                                                                         InternalErrorFault,
                                                                                         NotFoundFault,
                                                                                         UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");
    ValueEnforcer.notEmpty (sServiceName, "ServiceName");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("createParticipantIdentifier (" + sSMPID + ", " + aParticipantID + ", " + sServiceName + ")");

    final SMPAdvancedServiceForParticipantType aBody = new SMPAdvancedServiceForParticipantType ();
    final ServiceMetadataPublisherServiceForParticipantType aSMP = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMP.setServiceMetadataPublisherID (sSMPID);
    // Explicit constructor call is needed here!
    aSMP.setParticipantIdentifier (new SimpleParticipantIdentifier (aParticipantID));
    aBody.setCreateParticipantIdentifier (aSMP);
    aBody.setServiceName (sServiceName);
    createWSPort ().createParticipantIdentifier (aBody);
  }

  public boolean isAlive ()
  {
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("isAlive ()");

    try
    {
      final IsAliveType aDummy = new IsAliveType ();
      createWSPort ().isAlive (aDummy);
      return true;
    }
    catch (final InternalErrorFault | WebServiceException ex)
    {
      return false;
    }
  }
}
