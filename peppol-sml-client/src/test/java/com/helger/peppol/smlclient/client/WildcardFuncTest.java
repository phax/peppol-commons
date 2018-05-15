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
package com.helger.peppol.smlclient.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.smlclient.AbstractSMLClientTestCase;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.participant.BadRequestFault;
import com.helger.peppol.smlclient.participant.UnauthorizedFault;
import com.helger.peppol.smlclient.smp.NotFoundFault;
import com.helger.peppol.smlclient.smp.PublisherEndpointType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;

/**
 * This class is used for generating test data.
 *
 * @author Ravnholt<br>
 *         PEPPOL.AT, BRZ, Philip Helger
 */
@Ignore ("Requires a running SML")
public final class WildcardFuncTest extends AbstractSMLClientTestCase
{
  private static final String BUSINESS_IDENTIFIER_SCHEME = PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME;
  private static final String WILDCARD_PI = "0088:1111100001111";
  private static final String WILDCARD_ACTORID_ALLOWED_SCHEME = "wildcard-actorid-allowed";

  /*
   * Wildcard user.
   */
  private static final String SMP_ID = "wildcard-user1";
  private static final String ADDRESS_LOGICAL = "http://doesnotexist.xx";
  private static final String ADDRESS_PHYSICAL = "198.18.0.0";

  private static final String UNAUTHRIZED_SML_ID = "SMP-ID2";

  private ManageServiceMetadataServiceCaller m_aSMClient;

  @Before
  public void initialize () throws Exception
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

    // Create SMP
    final ServiceMetadataPublisherServiceType aServiceMetadataCreate = new ServiceMetadataPublisherServiceType ();
    aServiceMetadataCreate.setServiceMetadataPublisherID (SMP_ID);
    final PublisherEndpointType aEndpoint = new PublisherEndpointType ();
    aEndpoint.setLogicalAddress (ADDRESS_LOGICAL);
    aEndpoint.setPhysicalAddress (ADDRESS_PHYSICAL);
    aServiceMetadataCreate.setPublisherEndpoint (aEndpoint);

    m_aSMClient.create (aServiceMetadataCreate);
  }

  @After
  public void deleteSMP () throws Exception
  {
    m_aSMClient.delete (SMP_ID);
  }

  @Test
  public void createWildcardUnauthorizedFault_WrongScheme () throws Exception
  {
    try
    {
      final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      aPIClient.create (SMP_ID, new PeppolParticipantIdentifier (BUSINESS_IDENTIFIER_SCHEME, "*"));
      fail ("User should not be allowed to register wild card for this scheme : " + BUSINESS_IDENTIFIER_SCHEME);
    }
    catch (final UnauthorizedFault e)
    {
      assertTrue (e.getMessage ().contains ("The user is not allowed to register Wildcard for this scheme"));
    }
  }

  @Test
  public void createWildcardUnauthorizedFault_WrongUser () throws Exception
  {
    try
    {
      final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      aPIClient.create (UNAUTHRIZED_SML_ID,
                        new PeppolParticipantIdentifier (WILDCARD_ACTORID_ALLOWED_SCHEME, WILDCARD_PI));
      fail ("The user should not be authorized to insert PI when wildcard is on for scheme.");
    }
    catch (final UnauthorizedFault e)
    {
      assertTrue (e.getMessage (),
                  e.getMessage ()
                   .contains ("The user is not allowed to register ParticipantIdentifiers for this scheme"));
    }
  }

  @Test
  public void createWildcardBadRequestFault_MustBeWildcard () throws Exception
  {
    try
    {
      final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      aPIClient.create (SMP_ID, new PeppolParticipantIdentifier (WILDCARD_ACTORID_ALLOWED_SCHEME, WILDCARD_PI));
      fail ("User should not be allowed to register wild card for this scheme : " + BUSINESS_IDENTIFIER_SCHEME);
    }
    catch (final BadRequestFault e)
    {
      assertTrue (e.getMessage ().contains ("Only ParticipantIdentifier Wildcards can be registered for this scheme"));
    }
  }

  @Test
  public void createDeleteWildcard () throws Exception
  {
    final ManageParticipantIdentifierServiceCaller aPIClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
    aPIClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
    final PeppolParticipantIdentifier aPI = new PeppolParticipantIdentifier (WILDCARD_ACTORID_ALLOWED_SCHEME, "*");
    aPIClient.create (SMP_ID, aPI);

    // try to delete with un-authorized user!
    try
    {
      final ManageParticipantIdentifierServiceCaller unAuthorizedClient = new ManageParticipantIdentifierServiceCaller (SML_INFO);
      unAuthorizedClient.setSSLSocketFactory (createConfiguredSSLSocketFactory (SML_INFO, false));
      unAuthorizedClient.delete (SMP_ID, aPI);
      fail ("The user does not own the identifier : *");
    }
    catch (final UnauthorizedFault e)
    {
      assertTrue (e.getMessage (), e.getMessage ().contains ("The user does not own the identifier."));
    }

    aPIClient.delete (SMP_ID, aPI);
  }
}
