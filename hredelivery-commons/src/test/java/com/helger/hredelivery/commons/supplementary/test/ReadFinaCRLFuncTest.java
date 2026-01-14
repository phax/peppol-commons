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
package com.helger.hredelivery.commons.supplementary.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.cert.CRL;
import java.security.cert.X509CRL;

import org.junit.Test;

import com.helger.base.io.stream.StreamHelper;
import com.helger.io.resource.ClassPathResource;
import com.helger.security.crl.CRLHelper;

public final class ReadFinaCRLFuncTest
{
  @Test
  public void testReadCRL ()
  {
    final byte [] aCRLBytes = StreamHelper.getAllBytes (new ClassPathResource ("FinaRDCCA2020.crl",
                                                                               ReadFinaCRLFuncTest.class.getClassLoader ()));

    final CRL aCRL = CRLHelper.convertToCRL (aCRLBytes);
    assertNotNull (aCRL);

    final X509CRL aX509CRL = (X509CRL) aCRL;
    assertEquals (111_296, aX509CRL.getRevokedCertificates ().size ());
  }
}
