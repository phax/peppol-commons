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
package com.helger.peppolid.config;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.style.IsSPIImplementation;
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
 * @since 13.0.0
 */
@IsSPIImplementation
public final class MicroTypeConverterRegistrar_edelivery_id implements IMicroTypeConverterRegistrarSPI
{
  public void registerMicroTypeConverter (@NonNull final IMicroTypeConverterRegistry aRegistry)
  {
    aRegistry.registerMicroElementTypeConverter (SimpleDocumentTypeIdentifier.class,
                                                 new SimpleDocumentTypeIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleParticipantIdentifier.class,
                                                 new SimpleParticipantIdentifierMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SimpleProcessIdentifier.class,
                                                 new SimpleProcessIdentifierMicroTypeConverter ());
  }
}
