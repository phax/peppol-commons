/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppolid.peppol.doctype;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.peppolid.simple.doctype.IBusdoxDocumentTypeIdentifierParts;

/**
 * Contains all the different fields of a document identifier for PEPPOL in BIS
 * V1 style. Note: the sub type identifier is specified in more detail than in
 * BusDox: <code>&lt;customization id&gt;::&lt;version&gt;</code> even more
 * detailed the customization ID can be split further:
 * <code>&lt;transactionId&gt;:#&lt;extensionId&gt;[#&lt;extensionId&gt;]::&lt;version&gt;</code>
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public interface IPeppolDocumentTypeIdentifierParts extends IBusdoxDocumentTypeIdentifierParts
{
  /**
   * @return transaction ID + extension IDs (no version number)
   */
  @Nonnull
  @Nonempty
  String getCustomizationID ();

  /**
   * @return The version number
   */
  @Nonnull
  @Nonempty
  String getVersion ();
}
