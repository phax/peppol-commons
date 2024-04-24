/*
 * Copyright (C) 2015-2024 Philip Helger
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
package com.helger.peppolid.factory;

/**
 * Specific implementation of {@link IIdentifierFactory} for lax handling of
 * Peppol identifiers. See https://github.com/phax/phoss-smp/issues/275 for
 * background information.
 *
 * @author Philip Helger
 * @since 9.3.6
 */
public class PeppolLaxIdentifierFactory extends PeppolIdentifierFactory
{
  /** Global instance to be used. */
  @SuppressWarnings ("hiding")
  public static final PeppolLaxIdentifierFactory INSTANCE = new PeppolLaxIdentifierFactory ();

  public PeppolLaxIdentifierFactory ()
  {
    super (false);
  }
}
