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
package com.helger.peppol.config;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIImplementation;
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
    aRegistry.registerMicroElementTypeConverter (SMLInfo.class, new SMLInfoMicroTypeConverter ());
    aRegistry.registerMicroElementTypeConverter (SMPTransportProfile.class,
                                                 new SMPTransportProfileMicroTypeConverter ());
  }
}
