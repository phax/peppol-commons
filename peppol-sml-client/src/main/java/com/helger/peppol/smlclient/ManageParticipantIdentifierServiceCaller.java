/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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
import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.peppol.identifier.IdentifierHelper;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.participant.BadRequestFault;
import com.helger.peppol.smlclient.participant.InternalErrorFault;
import com.helger.peppol.smlclient.participant.ManageBusinessIdentifierService;
import com.helger.peppol.smlclient.participant.ManageBusinessIdentifierServiceSoap;
import com.helger.peppol.smlclient.participant.MigrationRecordType;
import com.helger.peppol.smlclient.participant.NotFoundFault;
import com.helger.peppol.smlclient.participant.PageRequestType;
import com.helger.peppol.smlclient.participant.ParticipantIdentifierPageType;
import com.helger.peppol.smlclient.participant.ServiceMetadataPublisherServiceForParticipantType;
import com.helger.peppol.smlclient.participant.UnauthorizedFault;

/**
 * This class is used for calling the Manage Participant Identifier interface on
 * the SML.
 *
 * @author Ravnholt
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class ManageParticipantIdentifierServiceCaller extends AbstractSMLClientCaller
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ManageParticipantIdentifierServiceCaller.class);
  private static final String NO_SMP_ID_REQUIRED = "";

  /**
   * Constructs a service caller for the manage business identifier interface.
   * <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/managebusinessidentifier
   *
   * @param aSMLInfo
   *        The SML info object. May not be <code>null</code>.
   */
  public ManageParticipantIdentifierServiceCaller (@Nonnull final ISMLInfo aSMLInfo)
  {
    this (aSMLInfo.getManageParticipantIdentifierEndpointAddress ());
  }

  /**
   * Constructs a service caller for the manage business identifier interface.
   * <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/managebusinessidentifier
   *
   * @param aEndpointAddress
   *        The URL of the manage participant identifier interface. May not be
   *        <code>null</code>.
   */
  public ManageParticipantIdentifierServiceCaller (@Nonnull final URL aEndpointAddress)
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
  protected ManageBusinessIdentifierServiceSoap createWSPort ()
  {
    final ManageBusinessIdentifierService aService = new ManageBusinessIdentifierService ();
    final ManageBusinessIdentifierServiceSoap aPort = aService.getManageBusinessIdentifierServicePort ();
    applyWSSettingsToBindingProvider ((BindingProvider) aPort);
    return aPort;
  }

  /**
   * Creates a new participant identifier for the SMP given by the ID.
   *
   * @param sSMPID
   *        Identifies the SMP to create the identifier for. Example:
   *        <code>test-smp-0000000001</code>
   * @param aIdentifier
   *        The identifier to be created. May not be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   * @throws NotFoundFault
   *         Is thrown if the service meta data publisher was not found.
   */
  public void create (@Nonnull @Nonempty final String sSMPID, @Nonnull final ParticipantIdentifierType aIdentifier) throws BadRequestFault, InternalErrorFault, UnauthorizedFault, NotFoundFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMPParticpantService.setServiceMetadataPublisherID (sSMPID);
    aSMPParticpantService.setParticipantIdentifier (aIdentifier);
    create (aSMPParticpantService);
  }

  /**
   * Creates a new business identifier for the SMP given by the publisher id in
   * ServiceMetadataPublisherServiceForParticipantType.
   *
   * @param aSMPParticpantService
   *        Specifies the identifier to create. May not be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   * @throws NotFoundFault
   *         Is thrown if the service meta data publisher was not found.
   */
  public void create (@Nonnull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault, InternalErrorFault, UnauthorizedFault, NotFoundFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (), "SMPParticpantService.ParticipantIdentifier");
    ValueEnforcer.notEmpty (aSMPParticpantService.getServiceMetadataPublisherID (), "SMPParticpantService.ServiceMetadataPublisherID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to create new participant " +
                      IdentifierHelper.getIdentifierURIEncoded (aSMPParticpantService.getParticipantIdentifier ()) +
                      " in SMP '" +
                      aSMPParticpantService.getServiceMetadataPublisherID () +
                      "'");
    createWSPort ().create (aSMPParticpantService);
  }

  @Nonnull
  private static String _toString (@Nonnull final Collection <? extends ParticipantIdentifierType> aParticipantIdentifiers)
  {
    final StringBuilder aSB = new StringBuilder ();
    for (final ParticipantIdentifierType aPI : aParticipantIdentifiers)
    {
      if (aSB.length () > 0)
        aSB.append (", ");
      aSB.append (IdentifierHelper.getIdentifierURIEncoded (aPI));
    }
    return aSB.toString ();
  }

  /**
   * Creates a list of participant identifiers.
   *
   * @param aParticipantIdentifiers
   *        The collection of identifiers to create. May neither be
   *        <code>null</code> nor empty nor may it contain <code>null</code>
   *        values.
   * @param sSMPID
   *        The id of the service meta data. May neither be <code>null</code>
   *        nor empty.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         If the SMP does not exist
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void createList (@Nonnull @Nonempty final Collection <? extends ParticipantIdentifierType> aParticipantIdentifiers,
                          @Nonnull @Nonempty final String sSMPID) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to create multiple new participants " + _toString (aParticipantIdentifiers) + " in SMP '" + sSMPID + "'");
    final ParticipantIdentifierPageType aParticipantList = new ParticipantIdentifierPageType ();
    aParticipantList.getParticipantIdentifier ().addAll (aParticipantIdentifiers);
    aParticipantList.setServiceMetadataPublisherID (sSMPID);
    createWSPort ().createList (aParticipantList);
  }

  /**
   * Deletes a given participant identifier
   *
   * @param aIdentifier
   *        The business identifier to delete. May not be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if the business identifier could not be found and
   *         therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  @Deprecated
  public void delete (@Nonnull final ParticipantIdentifierType aIdentifier) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    // No SMP ID required here, since identifier scheme+value must be unique!
    delete (NO_SMP_ID_REQUIRED, aIdentifier);
  }

  /**
   * Deletes a given participant identifier. <br>
   * This is a workaround for a bug in CIPA SMK 3.0 which requires the SMP ID.
   * Previously it was never needed!
   *
   * @param sSMPID
   *        The id of the service meta data. May neither be <code>null</code>
   *        nor empty.
   * @param aIdentifier
   *        The business identifier to delete. May not be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if the business identifier could not be found and
   *         therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void delete (@Nonnull @Nonempty final String sSMPID, @Nonnull final ParticipantIdentifierType aIdentifier) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMPParticpantService.setServiceMetadataPublisherID (sSMPID);
    aSMPParticpantService.setParticipantIdentifier (aIdentifier);
    delete (aSMPParticpantService);
  }

  /**
   * Deletes a given participant identifier given by the
   * ServiceMetadataPublisherServiceForBusinessType parameter.
   *
   * @param aSMPParticpantService
   *        The participant identifier to delete. May be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if the business identifier could not be found and
   *         therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void delete (@Nonnull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (), "SMPParticpantService.ParticipantIdentifier");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to delete participant " + IdentifierHelper.getIdentifierURIEncoded (aSMPParticpantService.getParticipantIdentifier ()));

    createWSPort ().delete (aSMPParticpantService);
  }

  /**
   * Deletes a list of participant identifiers
   *
   * @param aParticipantIdentifiers
   *        The list of participant identifiers. May neither be
   *        <code>null</code> nor empty nor may it contain <code>null</code>
   *        values.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if a business identifier could not be found and therefore
   *         deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void deleteList (@Nonnull @Nonempty final Collection <ParticipantIdentifierType> aParticipantIdentifiers) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to delete multiple participants " + _toString (aParticipantIdentifiers));

    final ParticipantIdentifierPageType deleteListIn = new ParticipantIdentifierPageType ();
    deleteListIn.getParticipantIdentifier ().addAll (aParticipantIdentifiers);
    createWSPort ().deleteList (deleteListIn);
  }

  /**
   * Lists the participant identifiers registered for the SMP associated with
   * the publisher id. The method is paged, so the page id can be used to get
   * the next page.
   *
   * @param sPageId
   *        The id of the next page. May not be <code>null</code> but maybe
   *        empty if it is the first page.
   * @param sSMPID
   *        The publisher id corresponding to the SMP. May neither be
   *        <code>null</code> nor empty.
   * @return A page of participant identifiers.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if certificateUid was not found.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public ParticipantIdentifierPageType list (@Nonnull final String sPageId, @Nonnull @Nonempty final String sSMPID) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (sPageId, "PageId");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    final PageRequestType aPageRequest = new PageRequestType ();
    aPageRequest.setServiceMetadataPublisherID (sSMPID);
    aPageRequest.setNextPageIdentifier (sPageId);
    return list (aPageRequest);
  }

  /**
   * Lists the participant identifiers registered for the SMP associated with
   * the publisher id. The method is paged, so the page id can be used to get
   * the next page.
   *
   * @param aPageRequest
   *        The page request
   * @return A page of business identifiers.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if certificateUid was not found.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public ParticipantIdentifierPageType list (@Nonnull final PageRequestType aPageRequest) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (aPageRequest, "PageRequest");
    ValueEnforcer.notEmpty (aPageRequest.getServiceMetadataPublisherID (), "PageRequest.ServiceMetadataPublisherID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to list participants in SMP '" + aPageRequest.getServiceMetadataPublisherID () + "'");

    return createWSPort ().list (aPageRequest);
  }

  /**
   * @return A new random UUID. May not be <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  protected UUID createUUID ()
  {
    return UUID.randomUUID ();
  }

  /**
   * Prepares a migrate of the given participant identifier.
   *
   * @param aIdentifier
   *        The participant identifier.
   * @param sSMPID
   *        SMP ID
   * @return The UUID to transfer out-of-band to the other SMP.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         If the business identifier was not found.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  @Nonnull
  public UUID prepareToMigrate (@Nonnull final ParticipantIdentifierType aIdentifier, @Nonnull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                                                                              InternalErrorFault,
                                                                                                                              NotFoundFault,
                                                                                                                              UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Preparing to migrate participant " + IdentifierHelper.getIdentifierURIEncoded (aIdentifier) + " from SMP '" + sSMPID + "'");

    final UUID aUUID = createUUID ();
    final MigrationRecordType aMigrationRecord = new MigrationRecordType ();
    aMigrationRecord.setParticipantIdentifier (aIdentifier);
    aMigrationRecord.setMigrationKey (aUUID.toString ());
    aMigrationRecord.setServiceMetadataPublisherID (sSMPID);

    createWSPort ().prepareToMigrate (aMigrationRecord);
    return aUUID;
  }

  /**
   * Migrates a given participant identifier to an SMP given by the publisher
   * id.
   *
   * @param aIdentifier
   *        The participant identifier to migrate. May not be <code>null</code>.
   * @param aMigrationKey
   *        The migration key received by the previous owner. May not be
   *        <code>null</code>.
   * @param sSMPID
   *        The publisher id corresponding to the new owner SMP. May neither be
   *        <code>null</code> nor empty.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         If the business identifier was not found.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void migrate (@Nonnull final ParticipantIdentifierType aIdentifier,
                       @Nonnull final UUID aMigrationKey,
                       @Nonnull @Nonempty final String sSMPID) throws BadRequestFault, InternalErrorFault, NotFoundFault, UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notNull (aMigrationKey, "MigrationKey");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    // Convert UUID to string
    final String sMigrationKey = aMigrationKey.toString ();
    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Finishing migration of participant " + IdentifierHelper.getIdentifierURIEncoded (aIdentifier) + " to SMP '" + sSMPID + "' using migration key '" + sMigrationKey + "'");

    final MigrationRecordType aMigrationRecord = new MigrationRecordType ();
    aMigrationRecord.setParticipantIdentifier (aIdentifier);
    aMigrationRecord.setMigrationKey (sMigrationKey);
    aMigrationRecord.setServiceMetadataPublisherID (sSMPID);

    createWSPort ().migrate (aMigrationRecord);
  }
}
