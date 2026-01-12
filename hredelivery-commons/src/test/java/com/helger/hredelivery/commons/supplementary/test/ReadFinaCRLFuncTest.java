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
