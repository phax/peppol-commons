/*
 * Copyright (C) 2015-2022 Philip Helger
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.cert.X509Certificate;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;

import org.junit.Test;

import com.helger.security.certificate.CertificateHelper;

/**
 * Test class for class {@link PeppolCertificateHelper}.
 *
 * @author Philip Helger
 */
public final class PeppolCertificateHelperTest
{
  @Test
  // @Ignore ("Because it doesn't work")
  public void testEncoding () throws Exception
  {
    final X509Certificate aCert = CertificateHelper.convertStringToCertficate ("-----BEGIN CERTIFICATE-----\r\n" +
                                                                               "MIIEsjCCApqgAwIBAgICEBYwDQYJKoZIhvcNAQELBQAwVzELMAkGA1UEBhMCRVUxDTALBgNVBAoMBFRPT1AxDTALBgNVBAsMBENDVEYxKjAoBgNVBAMMIVRPT1AgUElMT1RTIFRFU1QgQUNDRVNTIFBPSU5UUyBDQTAeFw0yMDA1MDgwODE4MDNaFw0yMjA1MDgwODE4MDNaMEExCzAJBgNVBAYTAkVFMSQwIgYDVQQKDBtSaWlnaSBJbmZvc3XDjMKIc3RlZW1pIEFtZXQxDDAKBgNVBAMMA1JJQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAOlEYCf5eN6R7IoZKzbRR1Ik8ezTIoRXhOOCoGflAUTUKMbuPyEDKbJ+iY9/HGy/aJ7ms0XbGTRghRRzjGrKDBEeM5fAblOqNVQFP+84UfkEMZgs+aFZ31ELzO+M0L4HXTP3wEGaIXymq4naMLwbVF6z4jmLBtmfSalwtU1sdpezJ06UEC0lPG2RZc0H791V8aehFRGh1+aKzB50v6ynj2YBa+v9DqtZO8d7Vs+kT6nDk/1wyAk960Jgdz2uamYoLVLIrYRZJCM/8PtUN0TpH2HlkFCZ16CqqufTPfODC8rI850vtvRHpeTloZgjlVcwEM9l29nKuwCCB0jnV47oElsCAwEAAaOBnTCBmjAJBgNVHRMEAjAAMBEGCWCGSAGG+EIBAQQEAwIEMDAqBglghkgBhvhCAQ0EHRYbVE9PUCBQaWxvdCBUZXN0IENlcnRpZmljYXRlMA4GA1UdDwEB/wQEAwIF4DAdBgNVHQ4EFgQUH58Yna0Kyb74iYV2ATZETcgy5cMwHwYDVR0jBBgwFoAUFc67oTzAwDRkaHfP0RTDL8FRp8EwDQYJKoZIhvcNAQELBQADggIBALDEilkdCRsy9ei1TZZ4NsYEMjbf7PUeb7IiiuFNX2pLfKiumW9A/wCLFIKRnrssEzzFTnr0t/IMsOQYBGpfPfEzcurBqMxm6gchTd4qFTbgRhLnmS6oOkR20hJjhj2Vp55NPyt8PWDIrdWjwDr/7fagD0USSLGmxB51Zl/3Gd4eCEb3XfA6AWqSY9+XueasjxV+JD+ogKQzGz0ZctkNcxqo6P/fTSOMXej5FA6ElSEuykltpBzM2Y1BzQSBTokZQarNzvaZgAf5xoOCSz98887cUR2bwc8b2rHl5gDfJB4YPquANgkGnOAOzDiv0s4cq67LzKJP9xGhvfIJ+GEe5pmyIMCnGKY98zn9aS8b/l2mwGVq9gFtoDOiC4F1uRj4FtPrsHs2M8GuIdmyLyjBecUNWcp5ixCMshezogQtA69hd+fBjCHssmIokvNJV2lBgvwqH6Blueg4hJLtumgejYtrRrOInnzTvawVHQk/u+7G9OF+hzOL+zXsFNTuMDM5rh+xaR74Tllh7ZMvXERtqNGZzZtgumECbjccoAMD5YpCgJ83w8hbnhiqIZZnB5AZ9SBUu1nHX0frv+9D1VUN5TT5k64YXojoKGvjBumMnUuef91jZozcml8dmUu1Wbc46UYUQfs8Dc8RiV+NMztd/iyQEP1yJw4ChN61gyiuIlA2\r\n" +
                                                                               "-----END CERTIFICATE-----");
    assertNotNull (aCert);

    // Does not work
    assertEquals ("CN=RIA,O=Riigi Infosüsteemi Amet,C=EE", aCert.getSubjectX500Principal ().getName (X500Principal.RFC2253));

    // Does also not work
    {
      String s = null;
      for (final Rdn aRdn : new LdapName (aCert.getSubjectX500Principal ().getName ()).getRdns ())
        if (aRdn.getType ().equalsIgnoreCase ("O"))
          s = (String) aRdn.getValue ();
      assertEquals ("Riigi Infosüsteemi Amet", s);
    }
  }
}
