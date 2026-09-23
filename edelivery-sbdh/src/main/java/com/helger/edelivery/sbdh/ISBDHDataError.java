/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
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
package com.helger.edelivery.sbdh;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;

/**
 * Base interface for the errors that can occur while reading a Standard Business Document. Every
 * network defines its own set of errors, because the rules to be checked differ.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
public interface ISBDHDataError extends IHasID <String>
{
  /**
   * @return The error message without any parameter. May neither be <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  String getErrorMessage ();

  /**
   * Get the error message with the provided arguments filled in.
   *
   * @param aArgs
   *        The arguments to be added to the error message. May neither be <code>null</code> nor
   *        empty.
   * @return The formatted error message. May neither be <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  String getErrorMessage (@NonNull @Nonempty Object... aArgs);
}
