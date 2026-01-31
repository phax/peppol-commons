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
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.OverridingMethodsMustInvokeSuper;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.array.ArrayHelper;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringImplode;
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
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.wsclient.WSClientConfig;
import com.helger.xsds.peppol.id1.ParticipantIdentifierType;

import jakarta.xml.ws.BindingProvider;

/**
 * This class is used for calling the Manage Participant Identifier interface on the SML.
 *
 * @author Ravnholt
 * @author Philip Helger
 */
public class ManageParticipantIdentifierServiceCaller extends WSClientConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger (ManageParticipantIdentifierServiceCaller.class);

  /**
   * Constructs a service caller for the manage business identifier interface. <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/managebusinessidentifier
   *
   * @param aSMLInfo
   *        The SML info object. May not be <code>null</code>.
   */
  public ManageParticipantIdentifierServiceCaller (@NonNull final ISMLInfo aSMLInfo)
  {
    this (aSMLInfo.getManageParticipantIdentifierEndpointAddress ());
  }

  /**
   * Constructs a service caller for the manage business identifier interface. <br>
   * Example of a host:<br>
   * https://sml.peppolcentral.org/managebusinessidentifier
   *
   * @param aEndpointAddress
   *        The URL of the manage participant identifier interface. May not be <code>null</code>.
   */
  public ManageParticipantIdentifierServiceCaller (@NonNull final URL aEndpointAddress)
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
  public void create (@NonNull @Nonempty final String sSMPID, @NonNull final IParticipantIdentifier aIdentifier)
                                                                                                                 throws BadRequestFault,
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
  public void create (@NonNull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault,
                                                                                                              InternalErrorFault,
                                                                                                              UnauthorizedFault,
                                                                                                              NotFoundFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (),
                           "SMPParticpantService.ParticipantIdentifier");
    ValueEnforcer.notEmpty (aSMPParticpantService.getServiceMetadataPublisherID (),
                            "SMPParticpantService.ServiceMetadataPublisherID");

    LOGGER.info ("Trying to create new participant " +
                 CIdentifier.getURIEncoded (aSMPParticpantService.getParticipantIdentifier ()) +
                 " in SMP '" +
                 aSMPParticpantService.getServiceMetadataPublisherID () +
                 "'");
    createWSPort ().create (aSMPParticpantService);
  }

  @NonNull
  private static String _toString (@NonNull final Collection <? extends IParticipantIdentifier> aParticipantIdentifiers)
  {
    return StringImplode.imploder ()
                        .separator (", ")
                        .source (aParticipantIdentifiers, IParticipantIdentifier::getURIEncoded)
                        .build ();
  }

  @NonNull
  private static String _toString2 (@NonNull final Collection <? extends ParticipantIdentifierType> aParticipantIdentifiers)
  {
    return StringImplode.imploder ()
                        .separator (", ")
                        .source (aParticipantIdentifiers, CIdentifier::getURIEncoded)
                        .build ();
  }

  /**
   * Creates a list of participant identifiers.
   *
   * @param aParticipantIdentifiers
   *        The collection of identifiers to create. May neither be <code>null</code> nor empty nor
   *        may it contain <code>null</code> values.
   * @param sSMPID
   *        The id of the service meta data. May neither be <code>null</code> nor empty.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         If the SMP does not exist
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void createList (@NonNull @Nonempty final Collection <? extends IParticipantIdentifier> aParticipantIdentifiers,
                          @NonNull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                  InternalErrorFault,
                                                                  NotFoundFault,
                                                                  UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    LOGGER.info ("Trying to create multiple new participants " +
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
   * This is a workaround for a bug in CIPA SMK 3.0 which requires the SMP ID. Previously it was
   * never needed!
   *
   * @param sSMPID
   *        The id of the service meta data. May neither be <code>null</code> nor empty.
   * @param aIdentifier
   *        The business identifier to delete. May not be <code>null</code>.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if the business identifier could not be found and therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void delete (@NonNull @Nonempty final String sSMPID, @NonNull final IParticipantIdentifier aIdentifier)
                                                                                                                 throws BadRequestFault,
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
   *         Is thrown if the business identifier could not be found and therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void delete (@NonNull final ServiceMetadataPublisherServiceForParticipantType aSMPParticpantService) throws BadRequestFault,
                                                                                                              InternalErrorFault,
                                                                                                              NotFoundFault,
                                                                                                              UnauthorizedFault
  {
    ValueEnforcer.notNull (aSMPParticpantService, "SMPParticpantService");
    ValueEnforcer.notNull (aSMPParticpantService.getParticipantIdentifier (),
                           "SMPParticpantService.ParticipantIdentifier");

    LOGGER.info ("Trying to delete participant " +
                 CIdentifier.getURIEncoded (aSMPParticpantService.getParticipantIdentifier ()));

    createWSPort ().delete (aSMPParticpantService);
  }

  /**
   * Deletes a list of participant identifiers
   *
   * @param aParticipantIdentifiers
   *        The list of participant identifiers. May neither be <code>null</code> nor empty nor may
   *        it contain <code>null</code> values.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         Is thrown if a business identifier could not be found and therefore deleted.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void deleteList (@NonNull @Nonempty final Collection <? extends ParticipantIdentifierType> aParticipantIdentifiers) throws BadRequestFault,
                                                                                                                             InternalErrorFault,
                                                                                                                             NotFoundFault,
                                                                                                                             UnauthorizedFault
  {
    ValueEnforcer.notEmptyNoNullValue (aParticipantIdentifiers, "ParticipantIdentifiers");

    LOGGER.info ("Trying to delete multiple participants " + _toString2 (aParticipantIdentifiers));

    final ParticipantIdentifierPageType deleteListIn = new ParticipantIdentifierPageType ();
    for (final ParticipantIdentifierType aPI : aParticipantIdentifiers)
    {
      // Constructor call needed for type conversion
      deleteListIn.addParticipantIdentifier (aPI.clone ());
    }
    createWSPort ().deleteList (deleteListIn);
  }

  /**
   * Lists the participant identifiers registered for the SMP associated with the publisher id. The
   * method is paged, so the page id can be used to get the next page.
   *
   * @param sPageId
   *        The id of the next page. May not be <code>null</code> but maybe empty if it is the first
   *        page.
   * @param sSMPID
   *        The publisher id corresponding to the SMP. May neither be <code>null</code> nor empty.
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
  public ParticipantIdentifierPageType list (@NonNull final String sPageId, @NonNull @Nonempty final String sSMPID)
                                                                                                                    throws BadRequestFault,
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
   * Lists the participant identifiers registered for the SMP associated with the publisher id. The
   * method is paged, so the page id can be used to get the next page.
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
  public ParticipantIdentifierPageType list (@NonNull final PageRequestType aPageRequest) throws BadRequestFault,
                                                                                          InternalErrorFault,
                                                                                          NotFoundFault,
                                                                                          UnauthorizedFault
  {
    ValueEnforcer.notNull (aPageRequest, "PageRequest");
    ValueEnforcer.notEmpty (aPageRequest.getServiceMetadataPublisherID (), "PageRequest.ServiceMetadataPublisherID");

    LOGGER.info ("Trying to list participants in SMP '" + aPageRequest.getServiceMetadataPublisherID () + "'");

    return createWSPort ().list (aPageRequest);
  }

  private static final char [] MK_LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray ();
  private static final char [] MK_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray ();
  private static final char [] MK_DIGITS = "0123456789".toCharArray ();
  private static final char [] MK_SPECIAL = "@#$%()[]{}*^-!~|+=".toCharArray ();
  private static final char [] MK_ALL = ArrayHelper.getConcatenated (MK_LOWER,
                                                                     ArrayHelper.getConcatenated (MK_UPPER,
                                                                                                  ArrayHelper.getConcatenated (MK_DIGITS,
                                                                                                                               MK_SPECIAL)));

  private static char _random (@NonNull final char [] aRange)
  {
    final int nIndex = ThreadLocalRandom.current ().nextInt (aRange.length);
    return aRange[nIndex];
  }

  /**
   * Create a random migration key. Prior to version 8.1.2 this was a random UUID. In 8.1.2 the
   * formula was changed to follow the rules of the BDMSL implementation.
   *
   * @return A new random migration key that matches the regex pattern of
   *         {@link CSMLDefault#MIGRATION_CODE_PATTERN}.
   */
  @NonNull
  @Nonempty
  public static final String createRandomMigrationKey ()
  {
    final StringBuilder aSB = new StringBuilder (CSMLDefault.MAX_MIGRATION_CODE_LENGTH);
    // Fulfill the minimum requirements
    aSB.append (_random (MK_LOWER));
    aSB.append (_random (MK_UPPER));
    aSB.append (_random (MK_DIGITS));
    aSB.append (_random (MK_SPECIAL));
    aSB.append (_random (MK_LOWER));
    aSB.append (_random (MK_UPPER));
    aSB.append (_random (MK_DIGITS));
    aSB.append (_random (MK_SPECIAL));
    // Fill up the rest
    for (int i = 8; i < CSMLDefault.MAX_MIGRATION_CODE_LENGTH; ++i)
      aSB.append (_random (MK_ALL));
    return aSB.toString ();
  }

  /**
   * Prepares a migrate of the given participant identifier from one SMP to another. This method
   * must be called from the source SMP. This method creates a new random migration key via
   * {@link #createRandomMigrationKey()} and than calls
   * {@link #prepareToMigrate(IParticipantIdentifier, String, String)}
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
   * @see #prepareToMigrate(IParticipantIdentifier, String, String)
   */
  @NonNull
  public String prepareToMigrate (@NonNull final IParticipantIdentifier aIdentifier,
                                  @NonNull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                          InternalErrorFault,
                                                                          NotFoundFault,
                                                                          UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    final String sMigrationKey = createRandomMigrationKey ();
    return prepareToMigrate (aIdentifier, sMigrationKey, sSMPID);
  }

  /**
   * Prepares a migrate of the given participant identifier from one SMP to another. This method
   * must be called from the source SMP. This overload takes a migration key that was created
   * outside.
   *
   * @param aIdentifier
   *        The participant identifier.
   * @param sMigrationKey
   *        The migration key to be used. May neither be <code>null</code> nor empty.
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
   * @since 8.1.2
   */
  @NonNull
  public String prepareToMigrate (@NonNull final IParticipantIdentifier aIdentifier,
                                  @NonNull @Nonempty final String sMigrationKey,
                                  @NonNull @Nonempty final String sSMPID) throws BadRequestFault,
                                                                          InternalErrorFault,
                                                                          NotFoundFault,
                                                                          UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    LOGGER.info ("Preparing to migrate participant " +
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

    LOGGER.info ("Successfully called 'prepareToMigrate'");

    return sMigrationKey;
  }

  /**
   * Migrates an existing Service Group from another SMP to this SMP. The source SMP must have
   * called the {@link #prepareToMigrate(IParticipantIdentifier, String, String)} method and pass
   * the migration key to the receiving SMP. The receiving SMP must use this method to indicate to
   * the SMK/SML that the new participant is now usable by the new SMP.
   *
   * @param aIdentifier
   *        The participant identifier to migrate. May not be <code>null</code>.
   * @param sMigrationKey
   *        The migration key received by the previous owner. May not be <code>null</code>. Must
   *        have at last 24 characters.
   * @param sSMPID
   *        The publisher id corresponding to the new owner SMP. May neither be <code>null</code>
   *        nor empty.
   * @throws BadRequestFault
   *         Is thrown if the request sent to the service was not well-formed.
   * @throws InternalErrorFault
   *         Is thrown if an internal error happened on the service.
   * @throws NotFoundFault
   *         If the business identifier was not found.
   * @throws UnauthorizedFault
   *         Is thrown if the user was not authorized.
   */
  public void migrate (@NonNull final IParticipantIdentifier aIdentifier,
                       @NonNull @Nonempty final String sMigrationKey,
                       @NonNull @Nonempty final String sSMPID) throws BadRequestFault,
                                                               InternalErrorFault,
                                                               NotFoundFault,
                                                               UnauthorizedFault
  {
    ValueEnforcer.notNull (aIdentifier, "Identifier");
    ValueEnforcer.notEmpty (sMigrationKey, "MigrationKey");
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    LOGGER.info ("Finishing migration of participant " +
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

    LOGGER.info ("Successfully called 'migrate'");
  }
}
