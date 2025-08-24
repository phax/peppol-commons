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
package com.helger.smpclient.redirect;

import com.helger.annotation.Nonempty;

import jakarta.annotation.Nonnull;

/**
 * Callback interface to be invoked if an SMP client follows a redirect
 *
 * @author Philip Helger
 * @since 10.4.3
 */
public interface ISMPFollowRedirectCallback
{
  /**
   * Call when the data indicates that a redirect is present.
   *
   * @param bFollowing
   *        <code>true</code> if the SMP client is following the redirect, <code>false</code> if
   *        not.
   * @param sTargetHref
   *        The target URL to follow. May neither be <code>null</code> nor empty.
   */
  void onFollowSMPRedirect (boolean bFollowing, @Nonnull @Nonempty String sTargetHref);
}
