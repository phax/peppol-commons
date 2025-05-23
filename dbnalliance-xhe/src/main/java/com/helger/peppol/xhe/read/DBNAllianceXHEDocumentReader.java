/*
 * Copyright (C) 2024-2025 Philip Helger
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
package com.helger.peppol.xhe.read;

import javax.annotation.Nonnull;

import com.helger.peppolid.factory.IIdentifierFactory;

/**
 * Main class to read exchange header envelope and extract the DBNAlliance required data out of it.
 *
 * @author Robinson Garcia
 * @author Philip Helger
 * @deprecated Use {@link DBNAllianceXHEDataReader} instead
 */
@Deprecated (forRemoval = true, since = "10.2.1")
public class DBNAllianceXHEDocumentReader extends DBNAllianceXHEDataReader
{
  public DBNAllianceXHEDocumentReader (@Nonnull final IIdentifierFactory aIdentifierFactory)
  {
    super (aIdentifierFactory);
  }
}
