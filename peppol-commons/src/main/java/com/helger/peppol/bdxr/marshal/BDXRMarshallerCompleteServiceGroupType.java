/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.bdxr.marshal;

import com.helger.peppol.bdxr.CompleteServiceGroupType;
import com.helger.peppol.bdxr.ObjectFactory;

/**
 * A simple JAXB marshaller for the {@link CompleteServiceGroupType} type.
 *
 * @author Philip Helger
 */
public final class BDXRMarshallerCompleteServiceGroupType extends AbstractBDXRMarshaller <CompleteServiceGroupType>
{
  public BDXRMarshallerCompleteServiceGroupType ()
  {
    super (CompleteServiceGroupType.class, x -> new ObjectFactory ().createCompleteServiceGroup (x));
  }
}
