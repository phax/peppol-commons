/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.config;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.peppolid.bdxr.smp1.doctype.BDXR1DocumentTypeIdentifier;
import com.helger.peppolid.bdxr.smp1.doctype.BDXR1DocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppolid.bdxr.smp1.participant.BDXR1ParticipantIdentifier;
import com.helger.peppolid.bdxr.smp1.participant.BDXR1ParticipantIdentifierMicroTypeConverter;
import com.helger.peppolid.bdxr.smp1.process.BDXR1ProcessIdentifier;
import com.helger.peppolid.bdxr.smp1.process.BDXR1ProcessIdentifierMicroTypeConverter;
import com.helger.peppolid.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifier;
import com.helger.peppolid.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppolid.bdxr.smp2.participant.BDXR2ParticipantIdentifier;
import com.helger.peppolid.bdxr.smp2.participant.BDXR2ParticipantIdentifierMicroTypeConverter;
import com.helger.peppolid.bdxr.smp2.process.BDXR2ProcessIdentifier;
import com.helger.peppolid.bdxr.smp2.process.BDXR2ProcessIdentifierMicroTypeConverter;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppolid.peppol.doctype.PeppolDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppolid.peppol.participant.PeppolParticipantIdentifierMicroTypeConverter;
import com.helger.peppolid.peppol.process.PeppolProcessIdentifier;
import com.helger.peppolid.peppol.process.PeppolProcessIdentifierMicroTypeConverter;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifierMicroTypeConverter;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifierMicroTypeConverter;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistry;

/**
 * Special micro type converter for this project.
 *
 * @author Philip Helger
 */
@IsSPIImplementation
public final class MicroTypeConverterRegistrar_peppol_id implements IMicroTypeConverterRegistrarSPI
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
  }
}
