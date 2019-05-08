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
package com.helger.peppol.config;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.peppol.identifier.bdxr.smp1.doctype.BDXR1DocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.smp1.doctype.BDXR1DocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.smp1.participant.BDXR1ParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.smp1.participant.BDXR1ParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.smp1.process.BDXR1ProcessIdentifier;
import com.helger.peppol.identifier.bdxr.smp1.process.BDXR1ProcessIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.smp2.participant.BDXR2ParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.smp2.participant.BDXR2ParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.bdxr.smp2.process.BDXR2ProcessIdentifier;
import com.helger.peppol.identifier.bdxr.smp2.process.BDXR2ProcessIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.simple.doctype.SimpleDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.simple.participant.SimpleParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.simple.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.simple.process.SimpleProcessIdentifierMicroTypeConverter;
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
    aRegistry.registerMicroElementTypeConverter (SimpleDocumentTypeIdentifier.class,
                                                 new SimpleDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolDocumentTypeIdentifier.class,
                                                 new PeppolDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1DocumentTypeIdentifier.class,
                                                 new BDXR1DocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2DocumentTypeIdentifier.class,
                                                 new BDXR2DocumentTypeIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SimpleParticipantIdentifier.class,
                                                 new SimpleParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolParticipantIdentifier.class,
                                                 new PeppolParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1ParticipantIdentifier.class,
                                                 new BDXR1ParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2ParticipantIdentifier.class,
                                                 new BDXR2ParticipantIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SimpleProcessIdentifier.class,
                                                 new SimpleProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolProcessIdentifier.class,
                                                 new PeppolProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1ProcessIdentifier.class,
                                                 new BDXR1ProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2ProcessIdentifier.class,
                                                 new BDXR2ProcessIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SMLInfo.class, new SMLInfoMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SMPTransportProfile.class,
                                                 new SMPTransportProfileMicroTypeConverter ());
  }
}
