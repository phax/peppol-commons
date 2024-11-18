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
package com.helger.smpclient.url;

import javax.annotation.concurrent.ThreadSafe;

/**
 * An implementation of {@link IBDXLURLProvider} suitable for the DBNA network
 * in the Market pilot.
 *
 * @author Philip Helger
 * @since 9.3.4
 */
@ThreadSafe
public class DBNAURLProviderSMP extends AbstractBDXLURLProvider implements IBDXLURLProvider
{
  /**
   * The U-NAPTR record service name. Based on DBNA SML Profile 1.0 chapter 4.3.
   */
  public static final String NAPTR_SERVICE_NAME = "oasis-bdxr-smp-2#dbnalliance-1.1";

  /** The writable API of the default instance */
  public static final DBNAURLProviderSMP MUTABLE_INSTANCE = new DBNAURLProviderSMP ();
  /** The default instance that should be used */
  public static final IBDXLURLProvider INSTANCE = MUTABLE_INSTANCE;

  /**
   * Default constructor.
   */
  public DBNAURLProviderSMP ()
  {
    setLowercaseValueBeforeHashing (true);
    setAddIdentifierSchemeToZone (false);
    setNAPTRServiceName (NAPTR_SERVICE_NAME);
    setUseDNSCache (false);
  }
}
