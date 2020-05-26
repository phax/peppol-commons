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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.TextParseException;

import com.helger.commons.timing.StopWatch;
import com.helger.commons.url.URLHelper;
import com.helger.dns.naptr.NaptrResolver;

public class MainTestNAPTR
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainTestNAPTR.class);

  @Nullable
  private static String _resolveFromNAPTR (@Nonnull final String sDNSName) throws TextParseException
  {
    return NaptrResolver.resolveFromUNAPTR (sDNSName, null, "Meta:SMP");
  }

  public static void main (final String [] args) throws TextParseException
  {
    // Warm up cache
    URLHelper.getAsURI ("http://www.example.org");

    // D2CQSE7LLUJ6H2AR32Z4RCK2M3QXCNQHB7K7QXNUMIYR5ESSXXPQ.ehealth-actorid-qns.acc.edelivery.tech.ec.europa.eu.
    // 60 IN NAPTR 100 10 "U" "Meta:SMP"
    // "!^.*$!http://EHEALTH-TEST-UPRC.publisher.acc.edelivery.tech.ec.europa.eu!"
    // .
    final String sDN = "D2CQSE7LLUJ6H2AR32Z4RCK2M3QXCNQHB7K7QXNUMIYR5ESSXXPQ.ehealth-actorid-qns.acc.edelivery.tech.ec.europa.eu";
    for (int i = 0; i < 10; ++i)
    {
      final StopWatch aSW = StopWatch.createdStarted ();
      final String sURI = _resolveFromNAPTR (sDN);
      LOGGER.info (aSW.stopAndGetMillis () + "ms " + sURI);
    }
  }
}
