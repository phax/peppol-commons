/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Version: MPL 2.0/EUPL 1.2
 * -
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * -
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.2 or - as soon they will be approved
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
 * -
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

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.ws.WSClientConfig;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.smp.BadRequestFault;
import com.helger.peppol.smlclient.smp.InternalErrorFault;
import com.helger.peppol.smlclient.smp.ManageServiceMetadataService;
import com.helger.peppol.smlclient.smp.ManageServiceMetadataServiceSoap;
import com.helger.peppol.smlclient.smp.NotFoundFault;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;
import com.helger.peppol.smlclient.smp.UnauthorizedFault;

/**
 * This class is used for calling the service meta data interface of the SML. It
 * is used for connecting SMPs to the SML.
 *
 * @author Ravnholt
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public class ManageServiceMetadataServiceCaller extends WSClientConfig
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ManageServiceMetadataServiceCaller.class);

  /**
   * Creates a service caller for the service metadata interface
   *
   * @param aSMLInfo
   *        The SML info object. May not be <code>null</code>.
   */
  public ManageServiceMetadataServiceCaller (@Nonnull final ISMLInfo aSMLInfo)
  {
    this (aSMLInfo.getManageServiceMetaDataEndpointAddress ());
  }

  /**
   * Creates a service caller for the service meta data interface
   *
   * @param aEndpointAddress
   *        The address of the SML management interface. May not be
   *        <code>null</code>.
   */
  public ManageServiceMetadataServiceCaller (@Nonnull final URL aEndpointAddress)
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
  protected ManageServiceMetadataServiceSoap createWSPort ()
  {
    // Use default WSDL and default QName
    final ManageServiceMetadataService aService = new ManageServiceMetadataService ();
    final ManageServiceMetadataServiceSoap aPort = aService.getManageServiceMetadataServicePort ();
    applyWSSettingsToBindingProvider ((BindingProvider) aPort);
    return aPort;
  }

  /**
   * Custom validation method to provide additional physical address validation.
   * Throw an {@link IllegalArgumentException} if validation fails.
   *
   * @param sPhysicalAddress
   *        The physical address to be validated. Neither <code>null</code> nor
   *        empty.
   */
  @OverrideOnDemand
  protected void validatePhysicalAddress (@Nonnull @Nonempty final String sPhysicalAddress)
  {}

  /**
   * Custom validation method to provide additional logical address validation.
   * Throw an {@link IllegalArgumentException} if validation fails.
   *
   * @param sLogicalAddress
   *        The logical address to be validated. Neither <code>null</code> nor
   *        empty.
   */
  @OverrideOnDemand
  protected void validateLogicalAddress (@Nonnull @Nonempty final String sLogicalAddress)
  {}

  /**
   * Creates the service metadata for the specified user.
   *
   * @param sSMPID
   *        The certificate UID of the SMP. May neither be <code>null</code> nor
   *        empty.
   * @param sSMPAddressPhysical
   *        The physical address of the SMP (Example: 198.0.0.1). May neither be
   *        <code>null</code> nor empty.
   * @param sSMPAddressLogical
   *        The logical address of the SMP (Example: http://smp.example.org/).
   *        May neither be <code>null</code> nor empty.
   * @throws BadRequestFault
   *         The request was not well formed.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws UnauthorizedFault
   *         The user name or password was not correct.
   */
  public void create (@Nonnull @Nonempty final String sSMPID,
                      @Nonnull @Nonempty final String sSMPAddressPhysical,
                      @Nonnull @Nonempty final String sSMPAddressLogical) throws BadRequestFault,
                                                                          InternalErrorFault,
                                                                          UnauthorizedFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notEmpty (sSMPAddressPhysical, "SMPAddressPhysical");
    ValueEnforcer.notEmpty (sSMPAddressLogical, "SMPAddressLogical");

    // Custom validations
    validatePhysicalAddress (sSMPAddressPhysical);
    validateLogicalAddress (sSMPAddressLogical);

    final ServiceMetadataPublisherServiceType aServiceMetadata = new ServiceMetadataPublisherServiceType ();
    aServiceMetadata.setServiceMetadataPublisherID (sSMPID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (sSMPAddressLogical);
    aEndpoint.setPhysicalAddress (sSMPAddressPhysical);
    aServiceMetadata.setPublisherEndpoint (aEndpoint);
    create (aServiceMetadata);
  }

  /**
   * Creates the service metadata for the specified user.
   *
   * @param aServiceMetadata
   *        The data about the SMP. May not be <code>null</code>.
   * @throws BadRequestFault
   *         The request was not well formed.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws UnauthorizedFault
   *         The user name or password was not correct.
   */
  public void create (@Nonnull final ServiceMetadataPublisherServiceType aServiceMetadata) throws BadRequestFault,
                                                                                           InternalErrorFault,
                                                                                           UnauthorizedFault
  {
    ValueEnforcer.notNull (aServiceMetadata, "ServiceMetadata");
    ValueEnforcer.notEmpty (aServiceMetadata.getServiceMetadataPublisherID (),
                            "ServiceMetadata.ServiceMetadataPublisherID");
    ValueEnforcer.notNull (aServiceMetadata.getPublisherEndpoint (), "ServiceMetadata.PublisherEndpoint");
    ValueEnforcer.notEmpty (aServiceMetadata.getPublisherEndpoint ().getPhysicalAddress (),
                            "ServiceMetadata.PublisherEndpoint.PhysicalAddress");
    ValueEnforcer.notEmpty (aServiceMetadata.getPublisherEndpoint ().getLogicalAddress (),
                            "ServiceMetadata.PublisherEndpoint.LogicalAddress");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to create new SMP '" +
                      aServiceMetadata.getServiceMetadataPublisherID () +
                      "' with physical address '" +
                      aServiceMetadata.getPublisherEndpoint ().getPhysicalAddress () +
                      "' and logical address '" +
                      aServiceMetadata.getPublisherEndpoint ().getLogicalAddress () +
                      "'");

    createWSPort ().create (aServiceMetadata);
  }

  /**
   * Updates the specified service metadata given by the publisher id.
   *
   * @param sSMPID
   *        The publisher id. May neither be <code>null</code> nor empty.
   * @param sSMPAddressPhysical
   *        The physical address of the SMP (Example: 198.0.0.1). May neither be
   *        <code>null</code> nor empty.
   * @param sSMPAddressLogical
   *        The logical address of the SMP (Example: http://smp.example.org/).
   *        May neither be <code>null</code> nor empty.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws NotFoundFault
   *         The service metadata with the given publisher id was not found.
   * @throws UnauthorizedFault
   *         The username or password was not correct.
   * @throws BadRequestFault
   *         The request was not well formed.
   */
  public void update (@Nonnull @Nonempty final String sSMPID,
                      @Nonnull @Nonempty final String sSMPAddressPhysical,
                      @Nonnull @Nonempty final String sSMPAddressLogical) throws InternalErrorFault,
                                                                          NotFoundFault,
                                                                          UnauthorizedFault,
                                                                          BadRequestFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");
    ValueEnforcer.notEmpty (sSMPAddressPhysical, "SMPAddressPhysical");
    ValueEnforcer.notEmpty (sSMPAddressLogical, "SMPAddressLogical");

    // Custom validations
    validatePhysicalAddress (sSMPAddressPhysical);
    validateLogicalAddress (sSMPAddressLogical);

    final ServiceMetadataPublisherServiceType aServiceMetadata = new ServiceMetadataPublisherServiceType ();
    aServiceMetadata.setServiceMetadataPublisherID (sSMPID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (sSMPAddressLogical);
    aEndpoint.setPhysicalAddress (sSMPAddressPhysical);
    aServiceMetadata.setPublisherEndpoint (aEndpoint);
    update (aServiceMetadata);
  }

  /**
   * Updates the specified service metadata.
   *
   * @param aServiceMetadata
   *        The service metadata instance to update. May not be
   *        <code>null</code>.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws NotFoundFault
   *         The service metadata with the given publisher id was not found.
   * @throws UnauthorizedFault
   *         The username or password was not correct.
   * @throws BadRequestFault
   *         The request was not well formed.
   */
  public void update (@Nonnull final ServiceMetadataPublisherServiceType aServiceMetadata) throws InternalErrorFault,
                                                                                           NotFoundFault,
                                                                                           UnauthorizedFault,
                                                                                           BadRequestFault
  {
    ValueEnforcer.notNull (aServiceMetadata, "ServiceMetadata");
    ValueEnforcer.notEmpty (aServiceMetadata.getServiceMetadataPublisherID (),
                            "ServiceMetadata.ServiceMetadataPublisherID");
    ValueEnforcer.notNull (aServiceMetadata.getPublisherEndpoint (), "ServiceMetadata.PublisherEndpoint");
    ValueEnforcer.notEmpty (aServiceMetadata.getPublisherEndpoint ().getPhysicalAddress (),
                            "ServiceMetadata.PublisherEndpoint.PhysicalAddress");
    ValueEnforcer.notEmpty (aServiceMetadata.getPublisherEndpoint ().getLogicalAddress (),
                            "ServiceMetadata.PublisherEndpoint.LogicalAddress");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to update SMP '" +
                      aServiceMetadata.getServiceMetadataPublisherID () +
                      "' with physical address '" +
                      aServiceMetadata.getPublisherEndpoint ().getPhysicalAddress () +
                      "' and logical address '" +
                      aServiceMetadata.getPublisherEndpoint ().getLogicalAddress () +
                      "'");

    createWSPort ().update (aServiceMetadata);
  }

  /**
   * Deletes the service metadata given by the publisher id.
   *
   * @param sSMPID
   *        The publisher id of the service metadata to delete. May neither be
   *        <code>null</code> nor empty.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws NotFoundFault
   *         The service metadata with the given publisher id was not found.
   * @throws UnauthorizedFault
   *         The username or password was not correct.
   * @throws BadRequestFault
   *         The request was not well formed.
   */
  public void delete (@Nonnull @Nonempty final String sSMPID) throws InternalErrorFault,
                                                              NotFoundFault,
                                                              UnauthorizedFault,
                                                              BadRequestFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to delete SMP '" + sSMPID + "'");

    createWSPort ().delete (sSMPID);
  }

  /**
   * Returns information about the publisher given by the publisher id.
   *
   * @param sSMPID
   *        The publisher id of the service metadata to read.
   * @return The service metadata given by the id.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws NotFoundFault
   *         The service metadata with the given publisher id was not found.
   * @throws UnauthorizedFault
   *         The username or password was not correct.
   * @throws BadRequestFault
   *         The request was not well formed.
   */
  @Nonnull
  public ServiceMetadataPublisherServiceType read (@Nonnull @Nonempty final String sSMPID) throws InternalErrorFault,
                                                                                           NotFoundFault,
                                                                                           UnauthorizedFault,
                                                                                           BadRequestFault
  {
    ValueEnforcer.notEmpty (sSMPID, "SMPID");

    final ServiceMetadataPublisherServiceType aSMPService = new ServiceMetadataPublisherServiceType ();
    aSMPService.setServiceMetadataPublisherID (sSMPID);
    return read (aSMPService);
  }

  /**
   * Returns information about the publisher given by the publisher id.
   *
   * @param aSMPService
   *        The publisher id is read from this service metadata object. May not
   *        be <code>null</code>.
   * @return The service metadata given by the id.
   * @throws InternalErrorFault
   *         An internal error happened on the server.
   * @throws NotFoundFault
   *         The service metadata with the given publisher id was not found.
   * @throws UnauthorizedFault
   *         The username or password was not correct.
   * @throws BadRequestFault
   *         The request was not well formed.
   */
  @Nonnull
  public ServiceMetadataPublisherServiceType read (@Nonnull final ServiceMetadataPublisherServiceType aSMPService) throws InternalErrorFault,
                                                                                                                   NotFoundFault,
                                                                                                                   UnauthorizedFault,
                                                                                                                   BadRequestFault
  {
    ValueEnforcer.notNull (aSMPService, "SMPService");
    ValueEnforcer.notEmpty (aSMPService.getServiceMetadataPublisherID (), "SMPService.ServiceMetadataPublisherID");

    if (s_aLogger.isInfoEnabled ())
      s_aLogger.info ("Trying to read SMP '" + aSMPService.getServiceMetadataPublisherID () + "'");

    return createWSPort ().read (aSMPService);
  }
}
