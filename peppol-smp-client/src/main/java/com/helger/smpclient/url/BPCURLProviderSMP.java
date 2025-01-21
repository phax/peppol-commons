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
package com.helger.smpclient.url;

import javax.annotation.concurrent.ThreadSafe;

/**
 * An implementation of {@link IBDXLURLProvider} suitable for the BPC network in
 * the Market pilot.
 *
 * @author Philip Helger
 * @since 8.7.3
 * @deprecated Use {@link DBNAURLProviderSMP} instead
 */
@ThreadSafe
@Deprecated (forRemoval = true, since = "9.3.4")
public class BPCURLProviderSMP extends AbstractBDXLURLProvider implements IBDXLURLProvider
{
  /**
   * The U-NAPTR record service name. Based on BPC SMP Profile 1.0 chapter 7.
   */
  public static final String NAPTR_SERVICE_NAME = "oasis-bdxr-smp-2#bpc1.0";

  /** The writable API of the default instance */
  public static final BPCURLProviderSMP MUTABLE_INSTANCE = new BPCURLProviderSMP ();
  /** The default instance that should be used */
  public static final IBDXLURLProvider INSTANCE = MUTABLE_INSTANCE;

  /**
   * Default constructor.
   */
  public BPCURLProviderSMP ()
  {
    setLowercaseValueBeforeHashing (true);
    setAddIdentifierSchemeToZone (false);
    setNAPTRServiceName (NAPTR_SERVICE_NAME);
    setUseDNSCache (false);
  }
}
