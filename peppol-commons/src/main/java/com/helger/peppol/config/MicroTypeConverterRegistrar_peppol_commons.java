/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
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
