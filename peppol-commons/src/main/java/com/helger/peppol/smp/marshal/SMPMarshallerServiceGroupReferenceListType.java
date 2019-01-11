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
package com.helger.peppol.smp.marshal;

import com.helger.peppol.smp.ObjectFactory;
import com.helger.peppol.smp.ServiceGroupReferenceListType;

/**
 * A simple JAXB marshaller for the {@link ServiceGroupReferenceListType} type.
 *
 * @author Philip Helger
 */
public final class SMPMarshallerServiceGroupReferenceListType extends
                                                              AbstractSMPMarshaller <ServiceGroupReferenceListType>
{
  public SMPMarshallerServiceGroupReferenceListType ()
  {
    super (ServiceGroupReferenceListType.class, new ObjectFactory ()::createServiceGroupReferenceList);
  }
}
