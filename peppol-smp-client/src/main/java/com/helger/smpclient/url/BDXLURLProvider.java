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
 * The default implementation of {@link IBDXLURLProvider} suitable for the
 * E-SENS network. See e.g.
 * http://wiki.ds.unipi.gr/display/ESENS/PR+-+BDXL+1.4.0<br>
 * Layout:
 * <code>strip-trailing(base32(sha256(lowercase(ID-VALUE))),"=")+"."+ID-SCHEME+"."+SML-ZONE-NAME</code>
 *
 * @author Philip Helger
 */
@ThreadSafe
public class BDXLURLProvider extends AbstractBDXLURLProvider implements IBDXLURLProvider
{
  /** U NAPTR service name for e-SENS/PEPPOL */
  public static final String DNS_UNAPTR_SERVICE_NAME_META_SMP = "Meta:SMP";

  /** The writable API of the default instance */
  public static final BDXLURLProvider MUTABLE_INSTANCE = new BDXLURLProvider ();
  /** The default instance that should be used */
  public static final IBDXLURLProvider INSTANCE = MUTABLE_INSTANCE;

  /**
   * Default constructor.
   */
  public BDXLURLProvider ()
  {
    setLowercaseValueBeforeHashing (true);
    setAddIdentifierSchemeToZone (true);
    setNAPTRServiceName (DNS_UNAPTR_SERVICE_NAME_META_SMP);
    setUseDNSCache (false);
  }
}
