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
    aRegistry.registerMicroElementTypeConverter (SimpleDocumentTypeIdentifier.class, new SimpleDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolDocumentTypeIdentifier.class, new PeppolDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1DocumentTypeIdentifier.class, new BDXR1DocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2DocumentTypeIdentifier.class, new BDXR2DocumentTypeIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SimpleParticipantIdentifier.class, new SimpleParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolParticipantIdentifier.class, new PeppolParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1ParticipantIdentifier.class, new BDXR1ParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2ParticipantIdentifier.class, new BDXR2ParticipantIdentifierMicroTypeConverter ());

    aRegistry.registerMicroElementTypeConverter (SimpleProcessIdentifier.class, new SimpleProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (PeppolProcessIdentifier.class, new PeppolProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR1ProcessIdentifier.class, new BDXR1ProcessIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (BDXR2ProcessIdentifier.class, new BDXR2ProcessIdentifierMicroTypeConverter ());
  }
}
