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
package com.helger.peppol.bdxr.smp1.marshal;

import com.helger.xsds.bdxr.smp1.ObjectFactory;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;

/**
 * A simple JAXB marshaller for the {@link SignedServiceMetadataType} type.
 *
 * @author Philip Helger
 */
public final class BDXR1MarshallerSignedServiceMetadataType extends AbstractBDXR1Marshaller <SignedServiceMetadataType>
{
  public BDXR1MarshallerSignedServiceMetadataType ()
  {
    // Disable validation by default, because the mandatory Signature element is
    // created afterwards
    this (false);
  }

  public BDXR1MarshallerSignedServiceMetadataType (final boolean bValidationEnabled)
  {
    super (SignedServiceMetadataType.class, bValidationEnabled, new ObjectFactory ()::createSignedServiceMetadata);
  }
}
