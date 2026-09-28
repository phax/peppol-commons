/*
 * Copyright (C) 2023-2026 Philip Helger
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
package com.helger.peppol.mlr;

import com.helger.ubl21.UBL21Marshaller;
import com.helger.ubl21.UBL21Marshaller.UBL21JAXBMarshaller;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Special Peppol MLR Marshaller that does the same as the UBL 2.1 marshaller
 *
 * @author Philip Helger
 * @deprecated Peppol MLR is phased out of Peppol in favour of Peppol MLS. According to the "Peppol
 *             MLR Deprecation and Phase-out Plan" v1.0.0 the phase-out starts on 2027-03-01 (T2),
 *             the MLR specification is deprecated on 2027-04-01 (T3) and MLR is fully retired on
 *             2027-05-01 (T4). From T4 onwards no MLR may be sent anymore. Use
 *             <code>PeppolMLSMarshaller</code> from the <code>peppol-mls</code> module instead.
 */
@Deprecated (forRemoval = true, since = "13.1.0")
public class PeppolMLRMarshaller extends UBL21JAXBMarshaller <ApplicationResponseType>
{
  public PeppolMLRMarshaller ()
  {
    super (ApplicationResponseType.class,
           UBL21Marshaller.getAllApplicationResponseXSDs (),
           oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ObjectFactory._ApplicationResponse_QNAME);
  }
}
