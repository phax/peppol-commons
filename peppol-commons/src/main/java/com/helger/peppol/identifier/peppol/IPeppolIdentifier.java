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
package com.helger.peppol.identifier.peppol;

import com.helger.peppol.identifier.IIdentifier;

/**
 * Base interface for all PEPPOL identifiers.
 *
 * @author Philip Helger
 */
public interface IPeppolIdentifier extends IIdentifier
{
  /**
   * Check if this identifier uses the default scheme. E.g. for participant
   * identifiers this would be <code>true</code> if the scheme equals
   * <code>iso6523-actorid-upis</code>.
   *
   * @return <code>true</code> if is the default scheme, <code>false</code>
   *         otherwise.
   */
  boolean hasDefaultScheme ();
}
