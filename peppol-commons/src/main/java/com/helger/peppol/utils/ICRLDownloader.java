/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.peppol.utils.CRLHelper.CRLCache;

/**
 * Callback interface to download CRL data. Use it globally with
 * {@link CRLCache#setDownloader(ICRLDownloader)}.
 *
 * @author Philip Helger
 * @since 9.2.4
 */
@FunctionalInterface
public interface ICRLDownloader
{
  /**
   * Download the content of the provided URL
   *
   * @param sURL
   *        The CRL URL to download. Neither <code>null</code> nor empty.
   * @return <code>null</code> if no payload was returned
   * @throws Exception
   *         In case of error
   */
  @Nullable
  byte [] downloadURL (@Nonnull @Nonempty String sURL) throws Exception;
}
