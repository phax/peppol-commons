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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.participant.BadRequestFault;
import com.helger.peppol.smlclient.participant.ParticipantIdentifierPageType;
import com.helger.peppol.smlclient.participant.ServiceMetadataPublisherServiceForParticipantType;
import com.helger.peppol.smlclient.participant.UnauthorizedFault;
import com.helger.peppol.smlclient.smp.NotFoundFault;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.xsds.peppol.id1.ParticipantIdentifierType;

/**
 * @author Ravnholt<br>
 *         Philip Helger
 */
@Ignore ("Requires a running SML")
public final class SMLFuncTest extends AbstractSMLClientTestCase
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SMLFuncTest.class);
  private static final String SMP_ID = "SMP-TEST-ID";
  private static final String SMP_ID2 = "SMP-TEST-ID2";

  private static final String L_ENDPOINTADDRESS = "http://test.com";
  private static final String P_ENDPOINTADDRESS = "127.0.0.1";

  private static final String TEST_BUSINESS_IDENTIFIER1 = "0088:5798000999999";
  private static final String TEST_BUSINESS_IDENTIFIER2 = "0088:5798009999999";

  private ManageServiceMetadataServiceCaller m_aSMClient;
  private ServiceMetadataPublisherServiceType m_aServiceMetadataPublisher;

  @Nonnull
  private static ServiceMetadataPublisherServiceType _createSMPData (@Nonnull final ManageServiceMetadataServiceCaller aSMLSMPClient,
                                                                     @Nonnull @Nonempty final String sSMPID) throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataCreate = new ServiceMetadataPublisherServiceType ();
    aServiceMetadataCreate.setServiceMetadataPublisherID (sSMPID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (L_ENDPOINTADDRESS);
    aEndpoint.setPhysicalAddress (P_ENDPOINTADDRESS);
    aServiceMetadataCreate.setPublisherEndpoint (aEndpoint);

    aSMLSMPClient.create (aServiceMetadataCreate);
    return aServiceMetadataCreate;
  }

  @Before
  public void cleanup () throws Exception
  {
    m_aSMClient = new ManageServiceMetadataServiceCaller (SML_INFO);
    m_aSMClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
    try
    {
      m_aSMClient.delete (SMP_ID);
    }
    catch (final NotFoundFault e)
    {
      // This is fine, since we are just cleaning
    }

    m_aServiceMetadataPublisher = _createSMPData (m_aSMClient, SMP_ID);
  }

  @Test
  @Ignore ("Requires a local SML for testing")
  public void testDnsCreation () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);
    try
    {
      aPIClient.delete (SMP_ID, aPI);
    }
    catch (final com.helger.peppol.smlclient.participant.NotFoundFault e)
    {
      // Do nothing since we just want to make sure it doesn't exists.
    }

    final String sDNSString = "http://" + TEST_BUSINESS_IDENTIFIER1 + ".0010.ubis.sml.smloc.org/";

    InetAddress aAddress;
    try
    {
      aAddress = InetAddress.getByName (sDNSString);
      fail ("The hostname shouldn't exist");
    }
    catch (final UnknownHostException e)
    {
      // This should happen!
    }

    aPIClient.create (m_aServiceMetadataPublisher.getServiceMetadataPublisherID (), aPI);

    aAddress = InetAddress.getByName (sDNSString);
    assertEquals (P_ENDPOINTADDRESS, aAddress.getHostAddress ());
  }

  @Test
  public void testManageServiceMetadata () throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataRead = m_aSMClient.read (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());

    m_aSMClient.delete (aServiceMetadataRead.getServiceMetadataPublisherID ());

    assertEquals (m_aServiceMetadataPublisher.getServiceMetadataPublisherID (), aServiceMetadataRead.getServiceMetadataPublisherID ());
    assertEquals (m_aServiceMetadataPublisher.getPublisherEndpoint ().getLogicalAddress (),
                  aServiceMetadataRead.getPublisherEndpoint ().getLogicalAddress ());
    assertEquals (m_aServiceMetadataPublisher.getPublisherEndpoint ().getPhysicalAddress (),
                  aServiceMetadataRead.getPublisherEndpoint ().getPhysicalAddress ());
  }

  @Test
  public void testManageServiceMetadataWithManyIdentifier () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final long nStartIdentifier = 5798000999999l;
    final int nLastIdentifier = 150;
    for (int i = 0; i <= nLastIdentifier; i++)
    {
      LOGGER.info ("Creating number: " + i);
      final long nIdentifier = nStartIdentifier + i;
      aPIClient.create (m_aServiceMetadataPublisher.getServiceMetadataPublisherID (),
                        PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:" + nIdentifier));
    }

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());

    final ServiceMetadataPublisherServiceType aServiceMetadataCreateNew = _createSMPData (m_aSMClient, SMP_ID);

    // Delete one that was on a second page
    final long nIdentifier = nStartIdentifier + nLastIdentifier;
    aPIClient.create (aServiceMetadataCreateNew.getServiceMetadataPublisherID (),
                      PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme ("0088:" + nIdentifier));
  }

  @Test (expected = NotFoundFault.class)
  public void testManageServiceMetadataDoubleDelete () throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataRead = m_aSMClient.read (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aServiceMetadataRead);
    m_aSMClient.delete (aServiceMetadataRead.getServiceMetadataPublisherID ());
    // Delete again
    m_aSMClient.delete (aServiceMetadataRead.getServiceMetadataPublisherID ());
  }

  @Test
  public void testManageServiceMetadataUpdate () throws Exception
  {
    final ServiceMetadataPublisherServiceType aServiceMetadataRead = m_aSMClient.read (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aServiceMetadataRead);
    aServiceMetadataRead.getPublisherEndpoint ().setPhysicalAddress ("173.156.1.1");
    m_aSMClient.update (aServiceMetadataRead);
    final ServiceMetadataPublisherServiceType aAfterSignedServiceMetadataRead = m_aSMClient.read (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    m_aSMClient.delete (aAfterSignedServiceMetadataRead.getServiceMetadataPublisherID ());
    assertEquals ("173.156.1.1", aAfterSignedServiceMetadataRead.getPublisherEndpoint ().getPhysicalAddress ());
  }

  @Test
  public void testManageBusinessIdentifier () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final IParticipantIdentifier aBusinessIdentifierCreate = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);

    final ServiceMetadataPublisherServiceForParticipantType saSrviceMetadataPublisherServiceForBusiness = new ServiceMetadataPublisherServiceForParticipantType ();
    // Explicit constructor call needed for type conversion
    saSrviceMetadataPublisherServiceForBusiness.setParticipantIdentifier (new SimpleParticipantIdentifier (aBusinessIdentifierCreate));
    saSrviceMetadataPublisherServiceForBusiness.setServiceMetadataPublisherID (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());

    aPIClient.create (saSrviceMetadataPublisherServiceForBusiness);

    final ParticipantIdentifierPageType aResult = aPIClient.list ("", m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aResult);
    final List <ParticipantIdentifierType> aBusinessIdentifiers = aResult.getParticipantIdentifier ();

    assertEquals (1, aBusinessIdentifiers.size ());

    final ParticipantIdentifierType aBusinessIdentifierRead = aBusinessIdentifiers.get (0);

    assertEquals (aBusinessIdentifierCreate.getScheme (), aBusinessIdentifierRead.getScheme ());
    assertEquals (aBusinessIdentifierCreate.getValue (), aBusinessIdentifierRead.getValue ());

    aPIClient.deleteList (aBusinessIdentifiers);

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
  }

  @Test (expected = NotFoundFault.class)
  public void testManageBusinessIdentifierDoubleDelete () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final IParticipantIdentifier aBusinessIdentifierCreate = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);

    final ServiceMetadataPublisherServiceForParticipantType aServiceMetadataPublisherServiceForBusiness = new ServiceMetadataPublisherServiceForParticipantType ();
    // Explicit constructor call needed for type conversion
    aServiceMetadataPublisherServiceForBusiness.setParticipantIdentifier (new SimpleParticipantIdentifier (aBusinessIdentifierCreate));
    aServiceMetadataPublisherServiceForBusiness.setServiceMetadataPublisherID (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());

    aPIClient.create (aServiceMetadataPublisherServiceForBusiness);

    final ParticipantIdentifierPageType aResult = aPIClient.list ("", m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aResult);
    final List <ParticipantIdentifierType> aBusinessIdentifiers = aResult.getParticipantIdentifier ();

    assertEquals (1, aBusinessIdentifiers.size ());

    final ParticipantIdentifierType aBusinessIdentifierRead = aBusinessIdentifiers.get (0);

    assertEquals (aBusinessIdentifierCreate.getScheme (), aBusinessIdentifierRead.getScheme ());
    assertEquals (aBusinessIdentifierCreate.getValue (), aBusinessIdentifierRead.getValue ());

    aPIClient.deleteList (aBusinessIdentifiers);
    aPIClient.deleteList (aBusinessIdentifiers);

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
  }

  @Test
  public void testManageBusinessIdentifierListWithZeroElements () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final ICommonsList <IParticipantIdentifier> aRecipientBusinessIdentifiers = new CommonsArrayList <> ();

    aPIClient.createList (aRecipientBusinessIdentifiers, SMP_ID);

    final ParticipantIdentifierPageType aResult = aPIClient.list ("", m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aResult);

    final List <ParticipantIdentifierType> aBusinessIdentifiers = aResult.getParticipantIdentifier ();
    assertEquals (0, aBusinessIdentifiers.size ());

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
  }

  @Test
  public void testManageBusinessIdentifierListWithOneElement () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final ICommonsList <IParticipantIdentifier> aRecipientBusinessIdentifiers = new CommonsArrayList <> ();
    final IParticipantIdentifier aBusinessIdentifierCreate1 = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);
    aRecipientBusinessIdentifiers.add (aBusinessIdentifierCreate1);

    aPIClient.createList (aRecipientBusinessIdentifiers, SMP_ID);

    final ParticipantIdentifierPageType aResult = aPIClient.list ("", m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aResult);

    final List <ParticipantIdentifierType> aBusinessIdentifiers = aResult.getParticipantIdentifier ();

    assertEquals (1, aBusinessIdentifiers.size ());

    final ParticipantIdentifierType aBusinessIdentifierRead = aBusinessIdentifiers.get (0);

    assertEquals (aBusinessIdentifierCreate1.getScheme (), aBusinessIdentifierRead.getScheme ());
    assertEquals (aBusinessIdentifierCreate1.getValue (), aBusinessIdentifierRead.getValue ());

    aPIClient.deleteList (aBusinessIdentifiers);

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
  }

  @Test
  public void testManageBusinessIdentifierListWithTwoElement () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final ICommonsMap <String, IParticipantIdentifier> aBusinessIdentifiersCreate = new CommonsHashMap <> ();

    IParticipantIdentifier aBusinessIdentifierCreate1 = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);
    IParticipantIdentifier aBusinessIdentifierCreate2 = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER2);

    aBusinessIdentifiersCreate.put (aBusinessIdentifierCreate1.getValue (), aBusinessIdentifierCreate1);
    aBusinessIdentifiersCreate.put (aBusinessIdentifierCreate2.getValue (), aBusinessIdentifierCreate2);

    aPIClient.createList (aBusinessIdentifiersCreate.values (), SMP_ID);

    final ParticipantIdentifierPageType aResult = aPIClient.list ("", m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
    assertNotNull (aResult);

    final List <ParticipantIdentifierType> aBusinessIdentifiers = aResult.getParticipantIdentifier ();

    assertEquals (2, aBusinessIdentifiers.size ());

    final ParticipantIdentifierType aBusinessIdentifierRead1 = aBusinessIdentifiers.get (0);
    aBusinessIdentifierCreate1 = aBusinessIdentifiersCreate.get (aBusinessIdentifierRead1.getValue ());

    final ParticipantIdentifierType aBusinessIdentifierRead2 = aBusinessIdentifiers.get (1);
    aBusinessIdentifierCreate2 = aBusinessIdentifiersCreate.get (aBusinessIdentifierRead2.getValue ());

    assertEquals (aBusinessIdentifierCreate1.getScheme (), aBusinessIdentifierRead1.getScheme ());
    assertEquals (aBusinessIdentifierCreate1.getValue (), aBusinessIdentifierRead1.getValue ());

    assertEquals (aBusinessIdentifierCreate2.getScheme (), aBusinessIdentifierRead2.getScheme ());
    assertEquals (aBusinessIdentifierCreate2.getValue (), aBusinessIdentifierRead2.getValue ());

    aPIClient.deleteList (aBusinessIdentifiers);

    m_aSMClient.delete (m_aServiceMetadataPublisher.getServiceMetadataPublisherID ());
  }

  @Test
  public void testMigrate () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClientOld = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClientOld.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final ManageServiceMetadataServiceCaller aClient2 = new ManageServiceMetadataServiceCaller (SML_INFO);
    aClient2.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
    try
    {
      aClient2.delete (SMP_ID2);
    }
    catch (final NotFoundFault e)
    {
      // This is fine, since we are just cleaning
    }

    _createSMPData (aClient2, SMP_ID2);

    final ManageParticipantIdentifierServiceCaller aPIClientNew = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClientNew.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final IParticipantIdentifier aPI = PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1);
    aPIClientOld.create (SMP_ID, aPI);
    final String sMigrationKey = aPIClientOld.prepareToMigrate (aPI, SMP_ID);
    assertNotNull (sMigrationKey);
    aPIClientNew.migrate (aPI, sMigrationKey, SMP_ID2);

    try
    {
      aPIClientOld.delete (SMP_ID, aPI);
      fail ();
    }
    catch (final UnauthorizedFault e)
    {
      // This must happen.
    }

    // Should be able to delete, since New is now the owner
    aPIClientNew.delete (SMP_ID, aPI);
  }

  @Test (expected = BadRequestFault.class)
  public void testCreateExistingBusinessIdentifierUnauthorized () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClientOld = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClientOld.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    final ManageServiceMetadataServiceCaller aClient2 = new ManageServiceMetadataServiceCaller (SML_INFO);
    aClient2.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
    try
    {
      aClient2.delete (SMP_ID2);
    }
    catch (final NotFoundFault e)
    {
      // This is fine, since we are just cleaning
    }

    _createSMPData (aClient2, SMP_ID2);

    final ManageParticipantIdentifierServiceCaller aPIClientNew = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClientNew.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));

    aPIClientOld.create (SMP_ID, PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1));

    aPIClientNew.create (SMP_ID, PeppolIdentifierFactory.INSTANCE.createParticipantIdentifierWithDefaultScheme (TEST_BUSINESS_IDENTIFIER1));
  }
}
