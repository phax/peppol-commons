/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.dbnalliance.commons;

import org.jspecify.annotations.Nullable;

import com.helger.peppolid.factory.BDXR2IdentifierFactory;
import com.helger.peppolid.factory.IIdentifierFactory;

/**
 * Implementation of {@link IIdentifierFactory} for the DBNAlliance network.
 * <p>
 * Behaves like {@link BDXR2IdentifierFactory} but treats document type identifiers as case
 * insensitive regardless of the scheme. This matches the DBNAlliance interpretation of the OASIS
 * BDXR SMP v2.0 specification, which states that all identifier schemes are case insensitive unless
 * explicitly stated otherwise (see <a href="https://github.com/phax/peppol-commons/issues/71">issue
 * #71</a>).
 *
 * @author Philip Helger
 * @since 12.5.1
 */
public class DBNAllianceIdentifierFactory extends BDXR2IdentifierFactory
{
  /** Global instance to be used. */
  public static final IIdentifierFactory INSTANCE = new DBNAllianceIdentifierFactory ();

  public DBNAllianceIdentifierFactory ()
  {}

  @Override
  public boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return true;
  }

  @Override
  public boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return true;
  }
}
