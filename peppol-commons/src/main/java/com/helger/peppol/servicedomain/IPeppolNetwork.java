/*
 * Copyright (C) 2015-2025 Philip Helger
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
package com.helger.peppol.servicedomain;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.name.IHasDisplayName;
import com.helger.peppol.sml.ISMLInfo;

/**
 * Base interface defining the parameters for a general Peppol Network defintion.
 *
 * @author Philip Helger
 * @since 12.3.7
 */
public interface IPeppolNetwork extends IHasID <String>, IHasDisplayName
{
  /**
   * @return The URL of the Peppol Directory for this network stage. Ends with the domain name and
   *         without a trailing slash. Never <code>null</code>.
   */
  @NonNull
  @Nonempty
  String getDirectoryURL ();

  /**
   * @return The SML object to be used for this network stage. Never <code>null</code>.
   */
  @NonNull
  ISMLInfo getSMLInfo ();

  /**
   * @return <code>true</code> if this is a production network, <code>false</code> otherwise.
   */
  boolean isProduction ();

  /**
   * @return <code>true</code> if this is a test network, <code>false</code> otherwise.
   */
  boolean isTest ();
}
