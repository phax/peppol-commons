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
package com.helger.peppol.config;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.ProcessIdentifierType;
import com.helger.peppol.identifier.bdxr.doctype.BDXRDocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.doctype.BDXRDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.participant.BDXRParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.participant.BDXRParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.process.BDXRProcessIdentifier;
import com.helger.peppol.identifier.bdxr.process.BDXRProcessIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.generic.doctype.DocumentIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.generic.participant.ParticipantIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.generic.process.ProcessIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifierMicroTypeConverter;
import com.helger.peppol.sml.SMLInfo;
import com.helger.peppol.sml.SMLInfoMicroTypeConverter;
import com.helger.peppol.smp.SMPTransportProfile;
import com.helger.peppol.smp.SMPTransportProfileMicroTypeConverter;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistry;

/**
 * Special micro type converter for this project.
 *
 * @author Philip Helger
 */
@IsSPIImplementation
public final class MicroTypeConverterRegistrar_peppol_commons implements IMicroTypeConverterRegistrarSPI
{
  public void registerMicroTypeConverter (@Nonnull final IMicroTypeConverterRegistry aRegistry)
  {
    aRegistry.registerMicroElementTypeConverter (DocumentIdentifierType.class,
                                                 new DocumentIdentifierTypeMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleDocumentTypeIdentifier.class,
                                                 new SimpleDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolDocumentTypeIdentifier.class,
                                                 new PeppolDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXRDocumentTypeIdentifier.class,
                                                 new BDXRDocumentTypeIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (ParticipantIdentifierType.class,
                                                 new ParticipantIdentifierTypeMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleParticipantIdentifier.class,
                                                 new SimpleParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolParticipantIdentifier.class,
                                                 new PeppolParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXRParticipantIdentifier.class,
                                                 new BDXRParticipantIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (ProcessIdentifierType.class,
                                                 new ProcessIdentifierTypeMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleProcessIdentifier.class,
                                                 new SimpleProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolProcessIdentifier.class,
                                                 new PeppolProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXRProcessIdentifier.class,
                                                 new BDXRProcessIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SMLInfo.class, new SMLInfoMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SMPTransportProfile.class,
                                                 new SMPTransportProfileMicroTypeConverter ());
  }
}
