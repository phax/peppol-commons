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
package com.helger.peppol.identifier.bdxr.process;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.bdxr.BDXRIdentifierHelper;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;

/**
 * Special interface for OASIS BDXR SMP process identifiers.
 *
 * @author Philip Helger
 */
public interface IBDXRProcessIdentifier extends IProcessIdentifier
{
  /**
   * Check if the passed process identifier scheme is valid or not.
   *
   * @param sScheme
   *        The scheme to check.
   * @return Currently always <code>true</code>.
   */
  static boolean isValidScheme (@Nullable final String sScheme)
  {
    return true;
  }

  /**
   * Check if the passed process identifier value is valid or not.
   *
   * @param sValue
   *        The value to check.
   * @return <code>true</code> if the passed value is a valid identifier value,
   *         <code>false</code> otherwise.
   * @see BDXRIdentifierHelper#isValidIdentifierValue(String)
   */
  static boolean isValidValue (@Nullable final String sValue)
  {
    return BDXRIdentifierHelper.isValidIdentifierValue (sValue);
  }
}
