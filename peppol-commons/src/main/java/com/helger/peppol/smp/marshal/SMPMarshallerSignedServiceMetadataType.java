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
import com.helger.peppol.smp.SignedServiceMetadataType;

/**
 * A simple JAXB marshaller for the {@link SignedServiceMetadataType} type.
 *
 * @author Philip Helger
 */
public class SMPMarshallerSignedServiceMetadataType extends AbstractSMPMarshaller <SignedServiceMetadataType>
{
  public SMPMarshallerSignedServiceMetadataType ()
  {
    // Disable validation by default, because the mandatory signature element is
    // applied afterwards
    this (false);
  }

  public SMPMarshallerSignedServiceMetadataType (final boolean bValidationEnabled)
  {
    super (SignedServiceMetadataType.class, bValidationEnabled, new ObjectFactory ()::createSignedServiceMetadata);
  }
}
