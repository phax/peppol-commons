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
package com.helger.smpclient.peppol.functest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.collection.commons.CommonsTreeSet;
import com.helger.collection.commons.ICommonsSortedSet;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;
import com.helger.smpclient.exception.SMPClientHttpException;
import com.helger.smpclient.peppol.SMPClient;

/**
 * Iterates over all entries of {@link EPredefinedParticipantIdentifierScheme}, creates a Service
 * Group on the SMP at <code>http://localhost:8080</code> with a participant identifier of the form
 * <code>&lt;ISO6523&gt;:phelger8626</code>, and immediately deletes it again. On the first error,
 * the program stops.
 *
 * @author Philip Helger
 */
public final class MainCreateAndDeleteServiceGroupForAllSchemes
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateAndDeleteServiceGroupForAllSchemes.class);

  private static final URI SMP_URI = URI.create ("http://localhost:90");
  private static final String SMP_USERNAME = "admin@helger.com";
  private static final String SMP_PASSWORD = "password";
  private static final String PARTICIPANT_VALUE = "phelger8626";

  public static void main (final String [] args) throws Exception
  {
    final BasicAuthClientCredentials aCredentials = new BasicAuthClientCredentials (SMP_USERNAME, SMP_PASSWORD);
    final SMPClient aClient = new SMPClient (SMP_URI);
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final ICommonsSortedSet <String> aFailed = new CommonsTreeSet <> ();

    final int nProcessed = 0;
    for (final EPredefinedParticipantIdentifierScheme eScheme : EPredefinedParticipantIdentifierScheme.values ())
    {
      final String sParticipantValue = eScheme.createIdentifierValue (PARTICIPANT_VALUE);
      final PeppolParticipantIdentifier aParticipantID = aIF.createParticipantIdentifier (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS,
                                                                                          sParticipantValue);
      if (aParticipantID == null)
        throw new IllegalStateException ("Failed to build participant identifier for scheme '" +
                                         eScheme.getSchemeID () +
                                         "' with value '" +
                                         sParticipantValue +
                                         "'");

      LOGGER.info ("Creating Service Group for '" + aParticipantID.getURIEncoded () + "'");
      try
      {
        if (aClient.saveServiceGroup (aParticipantID, aCredentials) == null)
        {
          if (false)
            throw new IllegalStateException ("Failed to save");
        }
        else
        {
          LOGGER.info ("Deleting Service Group for '" + aParticipantID.getURIEncoded () + "'");
          aClient.deleteServiceGroup (aParticipantID, aCredentials);
        }
        aParticipantID.getURIEncoded ();
      }
      catch (final SMPClientHttpException ex)
      {
        LOGGER.error ("Error in '" + aParticipantID.getURIEncoded () + "'");
        aFailed.add (aParticipantID.getURIEncoded ());
      }
    }

    LOGGER.info ("Done - successfully created and deleted " + nProcessed + " Service Groups");

    LOGGER.info ("Failed " +
                 aFailed.size () +
                 "/" +
                 EPredefinedParticipantIdentifierScheme.values ().length +
                 ":\n" +
                 String.join ("\n", aFailed));
  }
}
