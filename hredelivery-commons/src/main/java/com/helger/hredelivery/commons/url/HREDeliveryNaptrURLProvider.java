/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.hredelivery.commons.url;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.clone.ICloneable;
import com.helger.smpclient.url.AbstractBDXLURLProvider;
import com.helger.smpclient.url.IBDXLURLProvider;

/**
 * The implementation of {@link IBDXLURLProvider} suitable for the HR eDelivery Network to resolve
 * NAPTR records as defined in the AMS specification.<br>
 * Layout:
 * <code>strip-trailing(base32(sha256(lowercase(ID-VALUE))),"=")+"."+ID-SCHEME+"."+SML-ZONE-NAME</code>
 *
 * @author Philip Helger
 * @since 12.0.2
 */
@ThreadSafe
public class HREDeliveryNaptrURLProvider extends AbstractBDXLURLProvider implements
                                         ICloneable <HREDeliveryNaptrURLProvider>
{
  /**
   * U NAPTR service name for HR eDelivery. AMS Spec v1.4, chapter 5.4 suggests to use "ERACUN:meta"
   * but in practice they are using "Meta:SMP".
   */
  public static final String DNS_UNAPTR_SERVICE_NAME_ERACUN_META = true ? "Meta:SMP" : "ERACUN:meta";

  /** The writable API of the default instance */
  public static final HREDeliveryNaptrURLProvider MUTABLE_INSTANCE = new HREDeliveryNaptrURLProvider ();
  /** The default instance that should be used */
  public static final IBDXLURLProvider INSTANCE = MUTABLE_INSTANCE;

  /**
   * Default constructor.
   */
  public HREDeliveryNaptrURLProvider ()
  {
    // No lowercasing specified in the spec
    setLowercaseValueBeforeHashing (false);
    setAddIdentifierSchemeToZone (true);
    setNAPTRServiceName (DNS_UNAPTR_SERVICE_NAME_ERACUN_META);
    setUseDNSCache (false);
  }

  /**
   * Copy constructor.
   *
   * @param rhs
   *        the object to copy from. May not be <code>null</code>.
   */
  protected HREDeliveryNaptrURLProvider (@NonNull final HREDeliveryNaptrURLProvider rhs)
  {
    super (rhs);
  }

  @NonNull
  @ReturnsMutableCopy
  public HREDeliveryNaptrURLProvider getClone ()
  {
    return new HREDeliveryNaptrURLProvider (this);
  }
}
