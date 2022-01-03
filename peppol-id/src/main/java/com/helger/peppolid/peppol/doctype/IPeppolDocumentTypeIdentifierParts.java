/*
 * Copyright (C) 2015-2022 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @author Philip Helger
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
