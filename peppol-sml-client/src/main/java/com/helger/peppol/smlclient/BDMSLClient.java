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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.url.URLHelper;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
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

/**
 * A client for the new BDMSL specific "CIPA service"
 *
 * @author Philip Helger
 */
public class BDMSLClient extends AbstractSMLClientCaller
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
    aSMP.setParticipantIdentifier (new SimpleParticipantIdentifier (aParticipantID));
    aBody.setCreateParticipantIdentifier (aSMP);
    aBody.setServiceName (sServiceName);
    createWSPort ().createParticipantIdentifier (aBody);
  }

  @Nullable
  public List <ParticipantListItem> listParticipants () throws InternalErrorFault
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
    final List <ParticipantListItem> ret = new ArrayList <ParticipantListItem> ();
    if (aList != null)
      for (final ParticipantsType aParticipant : aList.getParticipant ())
        ret.add (new ParticipantListItem (aParticipant.getServiceMetadataPublisherID (), aParticipant.getParticipantIdentifier ()));
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
