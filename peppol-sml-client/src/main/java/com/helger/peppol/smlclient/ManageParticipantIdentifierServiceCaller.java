/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smlclient;

import java.net.URL;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.CSMLDefault;
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
import com.helger.wsclient.WSClientConfig;

/**
 * This class is used for calling the Manage Participant Identifier interface on
 * the SML.
 *
 * @author Ravnholt
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class ManageParticipantIdentifierServiceCaller extends WSClientConfig
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ManageParticipantIdentifierServiceCaller.class);

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
  public void create (@Nonnull @Nonempty final String sSMPID,
                      @Nonnull final IParticipantIdentifier aIdentifier) throws BadRequestFault,
                                                                         InternalErrorFault,
                                                                         UnauthorizedFault,
                                                                         NotFoundFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMPParticpantService.setServiceMetadataPublisherID (sSMPID);
    // Constructor call needed for type conversion
    aSMPParticpantService.setParticipantIdentifier (new SimpleParticipantIdentifier (aIdentifier));
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
  public void create (@Nonnull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault,
                                                                                                              InternalErrorFault,
                                                                                                              UnauthorizedFault,
                                                                                                              NotFoundFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (),
                           "SMPParticpantService.ParticipantIdentifier");
    ValueEnforcer.notEmpty (aSMPParticpantService.getServiceMetadataPublisherID (),
                            "SMPParticpantService.ServiceMetadataPublisherID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to create new participant " +
                      aSMPParticpantService.getParticipantIdentifier ().getURIEncoded () +
                      " in SMP '" +
                      aSMPParticpantService.getServiceMetadataPublisherID () +
                      "'");
    createWSPort ().create (aSMPParticpantService);
  }

  @Nonnull
  private static String _toString (@Nonnull final Iterable <? extends IParticipantIdentifier> aParticipantIdentifiers)
  {
    return StringHelper.getImplodedMapped (", ", aParticipantIdentifiers, IParticipantIdentifier::getURIEncoded);
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
  public void createList (@Nonnull @Nonempty final Iterable <? extends IParticipantIdentifier> aParticipantIdentifiers,
                          @Nonnull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                  InternalErrorFault,
                                                                  NotFoundFault,
                                                                  UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to create multiple new participants " +
                      _toString (aParticipantIdentifiers) +
                      " in SMP '" +
                      sSMPID +
                      "'");
    final ParticipantIdentifierPageType aParticipantList = new ParticipantIdentifierPageType ();
    for (final IParticipantIdentifier aPI : aParticipantIdentifiers)
    {
      // Constructor call needed for type conversion
      aParticipantList.addParticipantIdentifier (new SimpleParticipantIdentifier (aPI));
    }
    aParticipantList.setServiceMetadataPublisherID (sSMPID);
    createWSPort ().createList (aParticipantList);
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
  public void delete (@Nonnull @Nonempty final String sSMPID,
                      @Nonnull final IParticipantIdentifier aIdentifier) throws BadRequestFault,
                                                                         InternalErrorFault,
                                                                         NotFoundFault,
                                                                         UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");

    final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMPParticpantService.setServiceMetadataPublisherID (sSMPID);
    // Constructor call needed for type conversion
    aSMPParticpantService.setParticipantIdentifier (new SimpleParticipantIdentifier (aIdentifier));
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
  public void delete (@Nonnull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault,
                                                                                                              InternalErrorFault,
                                                                                                              NotFoundFault,
                                                                                                              UnauthorizedFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (),
                           "SMPParticpantService.ParticipantIdentifier");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to delete participant " +
                      aSMPParticpantService.getParticipantIdentifier ().getURIEncoded ());

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
  public void deleteList (@Nonnull @Nonempty final Iterable <? extends IParticipantIdentifier> aParticipantIdentifiers) throws BadRequestFault,
                                                                                                                        InternalErrorFault,
                                                                                                                        NotFoundFault,
                                                                                                                        UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to delete multiple participants " + _toString (aParticipantIdentifiers));

    final ParticipantIdentifierPageType deleteListIn = new ParticipantIdentifierPageType ();
    for (final IParticipantIdentifier aPI : aParticipantIdentifiers)
    {
      // Constructor call needed for type conversion
      deleteListIn.addParticipantIdentifier (new SimpleParticipantIdentifier (aPI));
    }
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
  public ParticipantIdentifierPageType list (@Nonnull final String sPageId,
                                             @Nonnull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                                     InternalErrorFault,
                                                                                     NotFoundFault,
                                                                                     UnauthorizedFault
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
  public ParticipantIdentifierPageType list (@Nonnull final PageRequestType aPageRequest) throws BadRequestFault,
                                                                                          InternalErrorFault,
                                                                                          NotFoundFault,
                                                                                          UnauthorizedFault
  {
    ValueEnforcer.notNull (aPageRequest, "PageRequest");
    ValueEnforcer.notEmpty (aPageRequest.getServiceMetadataPublisherID (), "PageRequest.ServiceMetadataPublisherID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to list participants in SMP '" + aPageRequest.getServiceMetadataPublisherID () + "'");

    return createWSPort ().list (aPageRequest);
  }

  /**
   * Ensure that the migration key used has at last 24 characters.
   *
   * @param sBaseKey
   *        The base migration key. May not be <code>null</code>. E.g.
   *        <code>UUID.toString()</code>
   * @return The passed string with at last 24 chars
   * @see CSMLDefault#MAX_MIGRATION_CODE_LENGTH
   */
  @Nonnull
  @Nonempty
  public static final String getSuitableMigrationKey (@Nonnull @Nonempty final String sBaseKey)
  {
    ValueEnforcer.notEmpty (sBaseKey, "BaseKey");

    // The SMK 3 imposes a new max length of 24 chars
    if (sBaseKey.length () <= CSMLDefault.MAX_MIGRATION_CODE_LENGTH)
      return sBaseKey;

    return sBaseKey.substring (0, CSMLDefault.MAX_MIGRATION_CODE_LENGTH);
  }

  /**
   * @return A new random migration key with at last 24 characters.
   */
  @Nonnull
  @Nonempty
  public static final String createRandomMigrationKey ()
  {
    return getSuitableMigrationKey (UUID.randomUUID ().toString ());
  }

  /**
   * Prepares a migrate of the given participant identifier.
   *
   * @param aIdentifier
   *        The participant identifier.
   * @param sSMPID
   *        SMP ID
   * @return The migration key to transfer out-of-band to the other SMP.
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
  public String prepareToMigrate (@Nonnull final IParticipantIdentifier aIdentifier,
                                  @Nonnull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                          InternalErrorFault,
                                                                          NotFoundFault,
                                                                          UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    final String sMigrationKey = createRandomMigrationKey ();

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Preparing to migrate participant " +
                      aIdentifier.getURIEncoded () +
                      " from SMP '" +
                      sSMPID +
                      "' using migration key '" +
                      sMigrationKey +
                      "'");

    final MigrationRecordType aMigrationRecord = new MigrationRecordType ();
    // Constructor call needed for type conversion
    aMigrationRecord.setParticipantIdentifier (new SimpleParticipantIdentifier (aIdentifier));
    aMigrationRecord.setMigrationKey (sMigrationKey);
    aMigrationRecord.setServiceMetadataPublisherID (sSMPID);

    createWSPort ().prepareToMigrate (aMigrationRecord);
    return sMigrationKey;
  }

  /**
   * Migrates a given participant identifier to an SMP given by the publisher
   * id.
   *
   * @param aIdentifier
   *        The participant identifier to migrate. May not be <code>null</code>.
   * @param sMigrationKey
   *        The migration key received by the previous owner. May not be
   *        <code>null</code>. Must have at last 24 characters.
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
  public void migrate (@Nonnull final IParticipantIdentifier aIdentifier,
                       @Nonnull @Nonempty final String sMigrationKey,
                       @Nonnull @Nonempty final String sSMPID) throws BadRequestFault,
                                                               InternalErrorFault,
                                                               NotFoundFault,
                                                               UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sMigrationKey, "MigrationKey");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    // Convert UUID to string
    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Finishing migration of participant " +
                      aIdentifier.getURIEncoded () +
                      " to SMP '" +
                      sSMPID +
                      "' using migration key '" +
                      sMigrationKey +
                      "'");

    final MigrationRecordType aMigrationRecord = new MigrationRecordType ();
    // Constructor call needed for type conversion
    aMigrationRecord.setParticipantIdentifier (new SimpleParticipantIdentifier (aIdentifier));
    aMigrationRecord.setMigrationKey (sMigrationKey);
    aMigrationRecord.setServiceMetadataPublisherID (sSMPID);

    createWSPort ().migrate (aMigrationRecord);
  }
}
