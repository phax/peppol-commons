/**
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
package com.helger.peppol.smlclient.swing;

import java.net.URL;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.ManageParticipantIdentifierServiceCaller;
import com.helger.peppol.smlclient.ManageServiceMetadataServiceCaller;
import com.helger.peppol.smlclient.participant.ParticipantIdentifierPageType;
import com.helger.peppol.smlclient.smp.ServiceMetadataPublisherServiceType;
import com.helger.peppol.smlclient.support.ESMLObjectType;
import com.helger.peppol.smlclient.swing.action.ESMLAction;
import com.helger.peppol.smlclient.swing.utils.AppProperties;

/**
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@NotThreadSafe
final class GuiSMLController
{
  private static ManageServiceMetadataServiceCaller s_aSMPClient;
  private static ManageParticipantIdentifierServiceCaller s_aParticipantClient;
  private static URL s_aParticipantEndpointAddress;
  private static URL s_aSMPClientEndpointAddress;
  private static String s_sSMPID;

  private GuiSMLController ()
  {}

  @Nonnull
  private static ManageServiceMetadataServiceCaller _getSMPClient ()
  {
    if (s_aSMPClient == null)
      s_aSMPClient = new ManageServiceMetadataServiceCaller (s_aSMPClientEndpointAddress);
    return s_aSMPClient;
  }

  @Nonnull
  private static ManageParticipantIdentifierServiceCaller _getParticipantClient ()
  {
    if (s_aParticipantClient == null)
      s_aParticipantClient = new ManageParticipantIdentifierServiceCaller (s_aParticipantEndpointAddress);
    return s_aParticipantClient;
  }

  private static String _create (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case PARTICIPANT:
        if (args.length < 2)
          return "Invalid number of args to create a new participant.";
        _getParticipantClient ().create (s_sSMPID, new SimpleParticipantIdentifier (args[1], args[0]));
        return "PARTICIPANT FOR SMP: " + s_sSMPID + " CREATED";
      case METADATA:
        if (args.length < 2)
          return "Invalid number of args to create a new SMP.";
        _getSMPClient ().create (s_sSMPID, args[0], args[1]);
        return "METADATA FOR SMP: " + s_sSMPID + " CREATED";
    }

    return "CANNOT DO CREATE ON " + eObject;
  }

  private static String _update (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case METADATA:
        if (args.length < 2)
          return "Invalid number of args to update an SMP.";
        _getSMPClient ().update (s_sSMPID, args[0], args[1]);
        return "METADATA FOR SMP " + s_sSMPID + " UPDATED";
      default:
        return "CANNOT DO UPDATE ON " + eObject;
    }
  }

  private static String _delete (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case PARTICIPANT:
        if (args.length < 2)
          return "Invalid number of args to delete aparticipant.";
        _getParticipantClient ().delete (s_sSMPID, new SimpleParticipantIdentifier (args[1], args[0]));
        return "PARTICIPANT DELETED";
      case METADATA:
        _getSMPClient ().delete (s_sSMPID);
        return "METADATA FOR SMP " + s_sSMPID + " DELETED";
    }
    return "CANNOT DO DELETE ON " + eObject;
  }

  private static String _read (final ESMLObjectType eObject) throws Exception
  {
    switch (eObject)
    {
      case METADATA:
        final ServiceMetadataPublisherServiceType aSMP = _getSMPClient ().read (s_sSMPID);
        return "METADATA FOR SMP " +
               s_sSMPID +
               " READ: physical address=" +
               aSMP.getPublisherEndpoint ().getPhysicalAddress () +
               "; logical address=" +
               aSMP.getPublisherEndpoint ().getLogicalAddress ();
      default:
        return "CANNOT DO READ ON " + eObject;
    }
  }

  private static StringBuilder _getListContent (@Nullable final ParticipantIdentifierPageType aPage)
  {
    final StringBuilder aSB = new StringBuilder ();
    if (aPage == null)
    {
      aSB.append ("Returned page is null\n");
    }
    else
    {
      final List <ParticipantIdentifierType> aPIs = aPage.getParticipantIdentifier ();
      aSB.append ("Found ").append (aPIs.size ()).append (" participant identifiers:\n");
      for (final ParticipantIdentifierType aPI : aPIs)
        aSB.append ("  ").append (aPI.getScheme ()).append (" : ").append (aPI.getValue ()).append ("\n");

      final String sNextPage = aPage.getNextPageIdentifier ();
      if (sNextPage != null)
        aSB.append ("\nNext page: ").append (sNextPage).append ("\n");
    }
    return aSB;
  }

  private static String _list (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case PARTICIPANT:
        final String sPageID = args.length == 0 ? "" : args[0];
        final StringBuilder aList = _getListContent (_getParticipantClient ().list (sPageID, s_sSMPID));
        return "PARTICIPANT FOR SMP: " + s_sSMPID + " LIST:\n" + aList.toString ();
      default:
        return "CANNOT DO LIST ON " + eObject;
    }
  }

  private static String _prepareToMigrate (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case PARTICIPANT:
        if (args.length < 2)
          return "Invalid number of args to prepare to migrate a participant.";
        final String sMigrationCode = _getParticipantClient ().prepareToMigrate (new SimpleParticipantIdentifier (args[1],
                                                                                                                  args[0]),
                                                                                 s_sSMPID);
        return "PARTICIPANT FOR SMP: " + s_sSMPID + " PREPARED TO MIGRATE. Migration code = " + sMigrationCode;
      default:
        return "CANNOT DO PREPARETOMIGRATE ON " + eObject;
    }
  }

  private static String _migrate (final ESMLObjectType eObject, final String [] args) throws Exception
  {
    switch (eObject)
    {
      case PARTICIPANT:
        if (args.length < 3)
          return "Invalid number of args to prepare to migrate.";
        _getParticipantClient ().migrate (new SimpleParticipantIdentifier (args[1], args[0]), args[2], s_sSMPID);
        return "PARTICIPANT FOR SMP: " + s_sSMPID + " MIGRATED";
      default:
        return "CANNOT DO MIGRATE ON " + eObject;
    }
  }

  private static String _handleCommand (@Nonnull final ISMLInfo aSML,
                                        @Nonnull @Nonempty final String sSMPID,
                                        @Nonnull final ESMLAction eAction,
                                        @Nonnull final String [] aArgs)
  {
    s_aSMPClient = null;
    s_aParticipantClient = null;
    s_aParticipantEndpointAddress = aSML.getManageParticipantIdentifierEndpointAddress ();
    s_aSMPClientEndpointAddress = aSML.getManageServiceMetaDataEndpointAddress ();
    s_sSMPID = sSMPID;

    try
    {
      switch (eAction.getCommand ())
      {
        case CREATE:
          return _create (eAction.getObjectType (), aArgs);
        case UPDATE:
          return _update (eAction.getObjectType (), aArgs);
        case DELETE:
          return _delete (eAction.getObjectType (), aArgs);
        case READ:
          return _read (eAction.getObjectType ());
        case LIST:
          return _list (eAction.getObjectType (), aArgs);
        case PREPARETOMIGRATE:
          return _prepareToMigrate (eAction.getObjectType (), aArgs);
        case MIGRATE:
          return _migrate (eAction.getObjectType (), aArgs);
        default:
          return "UNKOWN COMMAND GIVEN";
      }
    }
    catch (final Exception e)
    {
      e.printStackTrace ();

      return "AN INTERNAL ERROR OCCURED:\n  " + e.getMessage ();
    }
  }

  public static String performAction (@Nonnull final ESMLAction eAction, final String sParameter)
  {
    final String [] aParams = RegExHelper.getSplitToArray (sParameter, "[ \t]+");

    final AppProperties aAP = AppProperties.getInstance ();
    if (aAP.getSMLInfo () == null)
    {
      MainStatusBar.setStatusError ("No SML Hostname set");
      return "No SML Hostname set.";
    }

    if (StringHelper.hasNoText (aAP.getSMPID ()))
    {
      MainStatusBar.setStatusError ("No SMP ID set");
      return "No SMP ID set.";
    }

    return _handleCommand (aAP.getSMLInfo (), aAP.getSMPID (), eAction, aParams);
  }
}
