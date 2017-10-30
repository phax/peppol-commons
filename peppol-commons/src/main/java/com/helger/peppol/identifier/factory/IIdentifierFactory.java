/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.identifier.factory;

/**
 * A generic factory interface that allows to easily switch between default
 * identifiers (<code>Simple...Identifier</code>), Peppol identifiers (
 * <code>Peppol...Identifier</code>) and BDXR identifiers (
 * <code>BDXR...Identifier</code>).
 *
 * @author Philip Helger
 */
public interface IIdentifierFactory extends
                                    IDocumentTypeIdentifierFactory,
                                    IParticipantIdentifierFactory,
                                    IProcessIdentifierFactory
{
  /* empty */
}
