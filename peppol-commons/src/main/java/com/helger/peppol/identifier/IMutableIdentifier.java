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
package com.helger.peppol.identifier;

import javax.annotation.Nonnull;

/**
 * The writable version of an identifier interface.
 * 
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IMutableIdentifier extends IIdentifier
{
  /**
   * Set the identifier scheme.
   * 
   * @param sScheme
   *        The scheme to be set. May not be <code>null</code>.
   */
  void setScheme (@Nonnull String sScheme);

  /**
   * Set the identifier value.
   * 
   * @param sValue
   *        The value to be set. May not be <code>null</code>.
   */
  void setValue (@Nonnull String sValue);
}
