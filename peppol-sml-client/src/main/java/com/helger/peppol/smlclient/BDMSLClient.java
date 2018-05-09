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
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.url.URLHelper;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.bdmsl.ParticipantListItem;
import com.helger.peppol.smlclient.bdmslcipa.BDMSLCipaService;
import com.helger.peppol.smlclient.bdmslcipa.BDMSLCipaServiceSoap;
import com.helger.peppol.smlclient.bdmslcipa.BadRequestFault;
import com.helger.peppol.smlclient.bdmslcipa.ClearCacheType;
import com.helger.peppol.smlclient.bdmslcipa.InternalErrorFault;
import com.helger.peppol.smlclient.bdmslcipa.IsAliveType;
import com.helger.peppol.smlclient.bdmslcipa.ListParticipantsInType;
import com.helger.peppol.smlclient.bdmslcipa.ListParticipantsType;
import com.helger.peppol.smlclient.bdmslcipa.NotFoundFault;
import com.helger.peppol.smlclient.bdmslcipa.ParticipantsType;
import com.helger.peppol.smlclient.bdmslcipa.PrepareChangeCertificateType;
import com.helger.peppol.smlclient.bdmslcipa.SMPAdvancedServiceForParticipantType;
import com.helger.peppol.smlclient.bdmslcipa.ServiceMetadataPublisherServiceForParticipantType;
import com.helger.peppol.smlclient.bdmslcipa.UnauthorizedFault;
import com.helger.wsclient.WSClientConfig;

/**
 * A client for the new BDMSL specific "CIPA service"
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
 *
 * @author Philip Helger
 */
public class BDMSLClient extends WSClientConfig
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (BDMSLClient.class);

  public BDMSLClient (@Nonnull final ISMLInfo aSMLInfo)
  {
    super (URLHelper.getAsURL (aSMLInfo.getManagementServiceURL () + "/cipaservice"));
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
  protected BDMSLCipaServiceSoap createWSPort ()
  {
    final BDMSLCipaService aService = new BDMSLCipaService ();
    final BDMSLCipaServiceSoap aPort = aService.getBDMSLCipaServicePort ();
    applyWSSettingsToBindingProvider ((BindingProvider) aPort);
    return aPort;
  }

  public void prepareChangeCertificate (@Nonnull @Nonempty final String sNewCertificatePublicKey,
                                        @Nonnull final LocalDate aMigrationDate) throws BadRequestFault,
                                                                                 InternalErrorFault,
                                                                                 NotFoundFault,
                                                                                 UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sNewCertificatePublicKey, "NewCertificatePublicKey");
    ValueEnforcer.notNull (aMigrationDate, "MigrationDate");

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("prepareChangeCertificate (" + sNewCertificatePublicKey + ", " + aMigrationDate + ")");

    final PrepareChangeCertificateType aBody = new PrepareChangeCertificateType ();
    aBody.setNewCertificatePublicKey (sNewCertificatePublicKey);
    aBody.setMigrationDate (PDTXMLConverter.getXMLCalendarDate (aMigrationDate));
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

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("createParticipantIdentifier (" + sSMPID + ", " + aParticipantID + ", " + sServiceName + ")");

    final SMPAdvancedServiceForParticipantType aBody = new SMPAdvancedServiceForParticipantType ();
    final ServiceMetadataPublisherServiceForParticipantType aSMP = new ServiceMetadataPublisherServiceForParticipantType ();
    aSMP.setServiceMetadataPublisherID (sSMPID);
    // Explicit constructor call is needed here!
    aSMP.setParticipantIdentifier (new SimpleParticipantIdentifier (aParticipantID));
    aBody.setCreateParticipantIdentifier (aSMP);
    aBody.setServiceName (sServiceName);
    createWSPort ().createParticipantIdentifier (aBody);
  }

  @Nullable
  public ICommonsList <ParticipantListItem> listParticipants () throws InternalErrorFault
  {
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("listParticipants ()");

    ListParticipantsType aList;
    try
    {
      final ListParticipantsInType aDummy = new ListParticipantsInType ();
      aList = createWSPort ().listParticipants (aDummy);
    }
    catch (final UnauthorizedFault ex)
    {
      s_aLogger.error ("Unauthorized to call listParticipants", ex);
      return null;
    }
    final ICommonsList <ParticipantListItem> ret = new CommonsArrayList <> ();
    if (aList != null)
      for (final ParticipantsType aParticipant : aList.getParticipant ())
        ret.add (new ParticipantListItem (aParticipant.getServiceMetadataPublisherID (),
                                          aParticipant.getParticipantIdentifier ()));
    return ret;
  }

  public void isAlive ()
  {
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("isAlive ()");

    final IsAliveType aDummy = new IsAliveType ();
    createWSPort ().isAlive (aDummy);
  }

  public void clearCache () throws InternalErrorFault
  {
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("clearCache ()");

    final ClearCacheType aDummy = new ClearCacheType ();
    createWSPort ().clearCache (aDummy);
  }
}
