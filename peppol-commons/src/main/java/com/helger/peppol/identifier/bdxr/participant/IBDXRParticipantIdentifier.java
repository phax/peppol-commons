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
package com.helger.peppol.identifier.bdxr.participant;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.bdxr.BDXRIdentifierHelper;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;

/**
 * Special interface for OASIS BDXR SMP participant identifiers.
 *
 * @author Philip Helger
 */
public interface IBDXRParticipantIdentifier extends IParticipantIdentifier
{
  /**
   * Check if the passed participant identifier scheme is valid or not.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier
   *         scheme, <code>false</code> otherwise.
   * @see BDXRIdentifierHelper#isValidIdentifierScheme(String)
   */
  static boolean isValidScheme (@Nullable final String sScheme)
  {
    return BDXRIdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  /**
   * Check if the passed participant identifier value is valid or not.
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
