/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.peppol.config;

import javax.annotation.Nonnull;

import com.helger.commons.annotations.IsSPIImplementation;
import com.helger.commons.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.commons.microdom.convert.IMicroTypeConverterRegistry;
import com.helger.peppol.identifier.DocumentIdentifierType;
import com.helger.peppol.identifier.ParticipantIdentifierType;
import com.helger.peppol.identifier.ProcessIdentifierType;
import com.helger.peppol.identifier.doctype.DocumentIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.participant.ParticipantIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifierMicroTypeConverter;
import com.helger.peppol.identifier.process.ProcessIdentifierTypeMicroTypeConverter;
import com.helger.peppol.identifier.process.SimpleProcessIdentifier;
import com.helger.peppol.identifier.process.SimpleProcessIdentifierMicroTypeConverter;

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
    aRegistry.registerMicroElementTypeConverter (ParticipantIdentifierType.class,
                                                 new ParticipantIdentifierTypeMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleParticipantIdentifier.class,
                                                 new SimpleParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (ProcessIdentifierType.class,
                                                 new ProcessIdentifierTypeMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleProcessIdentifier.class,
                                                 new SimpleProcessIdentifierMicroTypeConverter ());
  }
}
