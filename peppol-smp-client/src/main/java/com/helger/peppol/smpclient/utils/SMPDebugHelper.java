/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smpclient.utils;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.peppol.smp.EndpointType;
import com.helger.peppol.smp.ExtensionType;
import com.helger.peppol.smp.ProcessType;
import com.helger.peppol.smp.RedirectType;
import com.helger.peppol.smp.ServiceGroupType;
import com.helger.peppol.smp.ServiceInformationType;
import com.helger.peppol.smp.ServiceMetadataReferenceCollectionType;
import com.helger.peppol.smp.ServiceMetadataReferenceType;
import com.helger.peppol.smp.ServiceMetadataType;
import com.helger.peppol.utils.W3CEndpointReferenceHelper;
import com.helger.peppolid.CIdentifier;

/**
 * SMP utilities for debugging purposes. It converts the complex types to
 * strings for printing.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class SMPDebugHelper
{
  private SMPDebugHelper ()
  {}

  @Nonnull
  public static String getAsString (@Nonnull final ServiceGroupType aServiceGroup)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("ServiceGroup information:\n");
    aSB.append ("ParticipantIdentifier: ")
       .append (CIdentifier.getURIEncoded (aServiceGroup.getParticipantIdentifier ()))
       .append ('\n');

    // References
    final ServiceMetadataReferenceCollectionType aSMRC = aServiceGroup.getServiceMetadataReferenceCollection ();
    if (aSMRC != null && !aSMRC.getServiceMetadataReference ().isEmpty ())
    {
      aSB.append ("ServiceMetadataReferenceCollection:\n");
      for (final ServiceMetadataReferenceType aSMR : aSMRC.getServiceMetadataReference ())
        aSB.append ("  ").append (aSMR.getHref ()).append ('\n');
    }

    // Extension
    final ExtensionType aExt = aServiceGroup.getExtension ();
    if (aExt != null && aExt.getAny () != null)
    {
      aSB.append ("Extension:\n");
      aSB.append ("  Class = ").append (aExt.getAny ().getClass ().getName ()).append ('\n');
      aSB.append ("  Value = ").append (aExt.getAny ()).append ('\n');
    }
    return aSB.toString ();
  }

  @Nonnull
  public static String getAsString (@Nonnull final ServiceMetadataType aServiceMetadata)
  {
    final StringBuilder aSB = new StringBuilder ();
    aSB.append ("Service meta data:\n");

    final ServiceInformationType aServiceInformation = aServiceMetadata.getServiceInformation ();
    if (aServiceInformation != null)
    {
      aSB.append ("  Service information:\n");
      aSB.append ("    Participant: ")
         .append (CIdentifier.getURIEncoded (aServiceInformation.getParticipantIdentifier ()))
         .append ('\n');
      aSB.append ("    Document type: ")
         .append (CIdentifier.getURIEncoded (aServiceInformation.getDocumentIdentifier ()))
         .append ('\n');
      for (final ProcessType aProcess : aServiceInformation.getProcessList ().getProcess ())
      {
        aSB.append ("      Process: ")
           .append (CIdentifier.getURIEncoded (aProcess.getProcessIdentifier ()))
           .append ('\n');
        for (final EndpointType aEndpoint : aProcess.getServiceEndpointList ().getEndpoint ())
        {
          aSB.append ("        Endpoint: ")
             .append (W3CEndpointReferenceHelper.getAddress (aEndpoint.getEndpointReference ()))
             .append ('\n');
          aSB.append ("        Transport profile: ").append (aEndpoint.getTransportProfile ()).append ('\n');
          aSB.append ("        Business level signature: ")
             .append (aEndpoint.isRequireBusinessLevelSignature ())
             .append ('\n');
          aSB.append ("        Min auth level: ").append (aEndpoint.getMinimumAuthenticationLevel ()).append ('\n');
          if (aEndpoint.getServiceActivationDate () != null)
            aSB.append ("        Valid from: ").append (aEndpoint.getServiceActivationDate ()).append ('\n');
          if (aEndpoint.getServiceExpirationDate () != null)
            aSB.append ("        Valid to: ").append (aEndpoint.getServiceExpirationDate ()).append ('\n');
          aSB.append ("        Certficiate string: ").append (aEndpoint.getCertificate ()).append ('\n');
          aSB.append ("        Service description: ").append (aEndpoint.getServiceDescription ()).append ('\n');
          aSB.append ("        Contact URL: ").append (aEndpoint.getTechnicalContactUrl ()).append ('\n');
          aSB.append ("        Info URL: ").append (aEndpoint.getTechnicalInformationUrl ()).append ('\n');
        }
      }
    }

    final RedirectType aRedirect = aServiceMetadata.getRedirect ();
    if (aRedirect != null)
    {
      aSB.append ("  Service redirect:\n");
      aSB.append ("    Certificate UID: ").append (aRedirect.getCertificateUID ()).append ('\n');
      aSB.append ("    Href: ").append (aRedirect.getHref ()).append ('\n');
    }
    return aSB.toString ();
  }
}
