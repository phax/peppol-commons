/*
 * Copyright (C) 2015-2026 Philip Helger
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.OverridingMethodsMustInvokeSuper;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.url.URLHelper;
import com.helger.datetime.xml.XMLOffsetDateTime;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.bdmsl.BDMSLService;
import com.helger.peppol.smlclient.bdmsl.BDMSLServiceSoap;
import com.helger.peppol.smlclient.bdmsl.BadRequestFault;
import com.helger.peppol.smlclient.bdmsl.ExistsParticipantResponseType;
import com.helger.peppol.smlclient.bdmsl.InternalErrorFault;
import com.helger.peppol.smlclient.bdmsl.IsAliveType;
import com.helger.peppol.smlclient.bdmsl.ManageChangeCertificateResponseType;
import com.helger.peppol.smlclient.bdmsl.ManageChangeCertificateType;
import com.helger.peppol.smlclient.bdmsl.NotFoundFault;
import com.helger.peppol.smlclient.bdmsl.ParticipantsType;
import com.helger.peppol.smlclient.bdmsl.PrepareChangeCertificateType;
import com.helger.peppol.smlclient.bdmsl.SMPAdvancedServiceForParticipantType;
import com.helger.peppol.smlclient.bdmsl.SMPRefreshActiveStatus;
import com.helger.peppol.smlclient.bdmsl.SMPRefreshActiveStatusResponseType;
import com.helger.peppol.smlclient.bdmsl.ServiceMetadataPublisherServiceForParticipantType;
import com.helger.peppol.smlclient.bdmsl.UnauthorizedFault;
import com.helger.peppol.smlclient.support.EManageChangeCertificateAction;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.wsclient.WSClientConfig;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceException;

/**
 * A client for the new BDMSL specific "BDMSL service"
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part of the original
 * implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDMSLClient extends WSClientConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BDMSLClient.class);

  public BDMSLClient (@NonNull final ISMLInfo aSMLInfo)
  {
    super (URLHelper.getAsURL (aSMLInfo.getManagementServiceURL () + "/bdmslservice"));
  }

  /**
   * Constructs a service caller for the manage business identifier interface. <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/cipaservice
   *
   * @param aEndpointAddress
   *        The URL of the CIPA Service interface. May not be <code>null</code>.
   */
  public BDMSLClient (@NonNull final URL aEndpointAddress)
  {
    super (aEndpointAddress);
  }

  /**
   * Create the main WebService client for the specified endpoint address.
   *
   * @return The WebService port to be used. May not be <code>null</code>.
   */
  @NonNull
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
   * This operation allows a SMP to prepare a change of its certificate. It is typically called when
   * an SMP has a certificate that is about to expire and already has the new one.<br>
   * This operation MUST be called while the certificate that is already registered in the SML is
   * still valid.
   *
   * @param sNewCertificatePublicKey
   *        The new public key contained in the certificate.
   * @param aMigrationDate
   *        The migration date for the new certificate. Can't be in the past. If the migrationDate
   *        is not empty, then the new certificate MUST be valid at the date provided in the
   *        migrationDate element. i.e. The migrationDate must be within validFrom and validTo dates
   *        of the new certificate. If the migrationDate element is empty, then the "Valid From"
   *        date is extracted from the certificate and is used as the migrationDate. In this case,
   *        the "Not Before" date of the certificate must be in the future.
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         In case of error
   * @throws UnauthorizedFault
   *         In case of error
   * @see #manageChangeCertificate(EManageChangeCertificateAction, String, XMLOffsetDateTime) for
   *      the successor operation, that BDMSL 5.1 introduced and that it recommends over this one
   */
  public void prepareChangeCertificate (@NonNull @Nonempty final String sNewCertificatePublicKey,
                                        @Nullable final LocalDate aMigrationDate) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sNewCertificatePublicKey, "NewCertificatePublicKey");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("prepareChangeCertificate (" + sNewCertificatePublicKey + ", " + aMigrationDate + ")");

    final PrepareChangeCertificateType aBody = new PrepareChangeCertificateType ();
    aBody.setNewCertificatePublicKey (sNewCertificatePublicKey);
    aBody.setMigrationDate (aMigrationDate);
    createWSPort ().prepareChangeCertificate (aBody);
  }

  /**
   * This operation allows a SMP to manage the change of its certificate. It replaces
   * {@link #prepareChangeCertificate(String, LocalDate)} and was introduced in BDMSL 5.1. Older SML
   * implementations do not support it.
   *
   * @param eAction
   *        The action to perform. May not be <code>null</code>.
   * @param sCertificate
   *        The new X.509 certificate as Base64 or PEM encoded string. Required for
   *        {@link EManageChangeCertificateAction#UPSERT}, optional for
   *        {@link EManageChangeCertificateAction#ACTIVATE} and ignored otherwise. May be
   *        <code>null</code>.
   * @param aMigrationDate
   *        The migration date for the new certificate. Can't be in the past. If it is
   *        <code>null</code>, the "Valid From" date of the certificate is used as the migration
   *        date. May be <code>null</code>.
   * @return The response of the SML, containing the action, the certificate (only for
   *         {@link EManageChangeCertificateAction#READ}), the migration date and a status
   *         description. May be <code>null</code>.
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         In case of error
   * @throws UnauthorizedFault
   *         In case of error
   * @since 13.3.0
   */
  @Nullable
  public ManageChangeCertificateResponseType manageChangeCertificate (@NonNull final EManageChangeCertificateAction eAction,
                                                                      @Nullable final String sCertificate,
                                                                      @Nullable final XMLOffsetDateTime aMigrationDate) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (eAction, "Action");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("manageChangeCertificate (" + eAction + ", " + sCertificate + ", " + aMigrationDate + ")");

    final ManageChangeCertificateType aBody = new ManageChangeCertificateType ();
    aBody.setAction (eAction.getID ());
    aBody.setCertificate (sCertificate);
    aBody.setMigrationDate (aMigrationDate);
    return createWSPort ().manageChangeCertificate (aBody);
  }

  /**
   * Create a new participant identifier for the provided SMP, using a custom NAPTR service name.
   *
   * @param sSMPID
   *        The ID of the SMP the participant belongs to. May neither be <code>null</code> nor
   *        empty.
   * @param aParticipantID
   *        The participant identifier to create. May not be <code>null</code>.
   * @param sServiceName
   *        The name of the service for the NAPTR record. May neither be <code>null</code> nor
   *        empty.
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         In case of error
   * @throws UnauthorizedFault
   *         In case of error
   */
  public void createParticipantIdentifier (@NonNull @Nonempty final String sSMPID,
                                           @NonNull final IParticipantIdentifier aParticipantID,
                                           @NonNull @Nonempty final String sServiceName) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
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

  /**
   * Check if the provided participant identifier is registered for the provided SMP. This
   * operation was introduced in BDMSL 4.2. Older SML implementations do not support it.
   *
   * @param sSMPID
   *        The ID of the SMP to check. Must be owned by the calling certificate. May neither be
   *        <code>null</code> nor empty.
   * @param aParticipantID
   *        The participant identifier to check. May not be <code>null</code>.
   * @return <code>true</code> if the participant is registered for the SMP, <code>false</code> if
   *         not.
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         If the SMP does not exist
   * @throws UnauthorizedFault
   *         If the SMP is not owned by the calling certificate
   * @since 13.3.0
   */
  public boolean existsParticipantIdentifier (@NonNull @Nonempty final String sSMPID,
                                              @NonNull final IParticipantIdentifier aParticipantID) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notNull (aParticipantID, "ParticipantID");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("existsParticipantIdentifier (" + sSMPID + ", " + aParticipantID + ")");

    final ParticipantsType aBody = new ParticipantsType ();
    aBody.setServiceMetadataPublisherID (sSMPID);
    // Explicit constructor call is needed here!
    aBody.setParticipantIdentifier (new SimpleParticipantIdentifier (aParticipantID));
    final ExistsParticipantResponseType aResponse = createWSPort ().existsParticipantIdentifier (aBody);
    return aResponse != null && aResponse.isExist ();
  }

  /**
   * Refresh the active status of the provided SMP, so that the SML does not consider it inactive.
   * This operation was introduced in BDMSL 5.1. Older SML implementations do not support it.
   *
   * @param sSMPID
   *        The ID of the SMP to refresh. May neither be <code>null</code> nor empty.
   * @return The date and time (in UTC) until which the SMP is considered active. May be
   *         <code>null</code>.
   * @throws BadRequestFault
   *         In case of error
   * @throws InternalErrorFault
   *         In case of error
   * @throws NotFoundFault
   *         If the SMP does not exist
   * @throws UnauthorizedFault
   *         In case of error
   * @since 13.3.0
   */
  @Nullable
  public XMLOffsetDateTime refreshSmpActiveStatus (@NonNull @Nonempty final String sSMPID) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("refreshSmpActiveStatus (" + sSMPID + ")");

    final SMPRefreshActiveStatus aBody = new SMPRefreshActiveStatus ();
    aBody.setServiceMetadataPublisherID (sSMPID);
    final SMPRefreshActiveStatusResponseType aResponse = createWSPort ().refreshSmpActiveStatus (aBody);
    return aResponse == null ? null : aResponse.getActiveUntil ();
  }

  /**
   * Check if the SML is alive, using the <code>IsAlive</code> operation of the BDMSL service.<br>
   * Note: BDMSL 5.0 moved this operation to a separate monitoring service, so the BDMSL service of
   * BDMSL 5.0 or later no longer offers it. Against such an SML the call fails and this method
   * returns <code>false</code> - use {@link BDMSLMonitoringClient#isAlive()} instead.
   *
   * @return <code>true</code> if the SML is alive, <code>false</code> if not or if the call failed.
   */
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
