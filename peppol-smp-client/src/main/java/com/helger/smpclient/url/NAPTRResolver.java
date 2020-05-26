/**
 * Copyright (C) 2015-2020 Philip Helger
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

import java.net.InetAddress;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.xbill.DNS.TextParseException;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.dns.naptr.NaptrResolver;
import com.helger.dns.resolve.DNSResolver;

/**
 * Helper class to resolve NAPTR DNS records for BDMSL
 *
 * @author Philip Helger
 * @since 5.1.5
 * @deprecated Use {@link NaptrResolver} instead
 */
@Immutable
@Deprecated
public final class NAPTRResolver
{
  /**
   * U NAPTR service name for e-SENS/PEPPOL.
   *
   * @deprecated Use {@link BDXLURLProvider#DNS_UNAPTR_SERVICE_NAME_META_SMP}
   *             instead
   */
  @Deprecated
  public static final String DNS_UNAPTR_SERVICE_NAME_META_SMP = "Meta:SMP";

  private NAPTRResolver ()
  {}

  /**
   * Look up the passed DNS name (usually a dynamic DNS name that was created by
   * an algorithm) and resolve any BDMSL U-NAPTR records.
   *
   * @param sDNSName
   *        The created DNS name. May be <code>null</code>.
   * @param sPrimaryDNSServer
   *        An optional primary DNS server to be used for resolution. May be
   *        <code>null</code> in which case the default resolver will be used.
   * @param sServiceName
   *        The service name (inside the U NAPTR) to query. May neither be
   *        <code>null</code> nor empty. For e-SENS/PEPPOL use "Meta:SMP"
   * @return <code>null</code> if no U-NAPTR was found or could not be resolved.
   *         If non-<code>null</code> the fully qualified domain name, including
   *         and protocol (like http://) is returned.
   * @throws TextParseException
   *         In case the original DNS name does not constitute a valid DNS name
   *         and could not be parsed
   */
  @Nullable
  public static String resolveFromNAPTR (@Nullable final String sDNSName,
                                         @Nullable final String sPrimaryDNSServer,
                                         @Nonnull @Nonempty final String sServiceName) throws TextParseException
  {
    final InetAddress aPrimaryDNS = DNSResolver.resolveByName (sPrimaryDNSServer);

    return NaptrResolver.resolveFromUNAPTR (sDNSName, aPrimaryDNS == null ? null : new CommonsArrayList <> (aPrimaryDNS), sServiceName);
  }
}
