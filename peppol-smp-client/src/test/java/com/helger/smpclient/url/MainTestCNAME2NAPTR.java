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

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.dns.naptr.NaptrLookup;
import com.helger.dns.naptr.NaptrResolver;

public final class MainTestCNAME2NAPTR
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainTestCNAME2NAPTR.class);

  public static void main (final String [] args) throws Exception
  {
    String s = NaptrResolver.builder ()
                            .domainName ("AM7VC3VF63WABJQGQRHI36U2K6N6EJR7SHL6ELGLR7YRYQSFWOVQ.iso6523-actorid-upis.0151.test.participant.bdxl.services.")
                            .serviceName (BDXLURLProvider.DNS_UNAPTR_SERVICE_NAME_META_SMP)
                            .build ()
                            .resolveUNAPTR ();
    LOGGER.info ("Result: '" + s + "'");

    final String sDomain2 = "CDEF123456789012345678901234567890123456789012345678.iso6523-actorid-upis.0151.test.participant.bdxl.services.";
    s = NaptrResolver.builder ()
                     .domainName (sDomain2)
                     .naptrRecords (NaptrLookup.builder ()
                                               .domainName (sDomain2)
                                               .timeout (Duration.ofSeconds (10))
                                               .maxRetries (1)
                                               .debugMode (true))
                     .serviceName (BDXLURLProvider.DNS_UNAPTR_SERVICE_NAME_META_SMP)
                     .build ()
                     .resolveUNAPTR ();
    LOGGER.info ("Result: '" + s + "'");
  }
}
