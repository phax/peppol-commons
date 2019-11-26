package com.helger.peppol.utils;

import static org.junit.Assert.assertEquals;

import java.time.Month;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.state.ETriState;

/**
 * Test class for class {@link PeppolCertificateChecker}
 *
 * @author Philip Helger
 */
public class PeppolCertificateCheckerTest
{
  @Test
  public void testBasic ()
  {
    EPeppolCertificateCheckResult e = PeppolCertificateChecker.checkPeppolAPCertificate (null,
                                                                                         null,
                                                                                         ETriState.UNDEFINED,
                                                                                         ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.NO_CERTIFICATE_PROVIDED, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           PDTFactory.createLocalDateTime (2000, Month.JANUARY, 1),
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.NOT_YET_VALID, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           PDTFactory.createLocalDateTime (2099, Month.JANUARY, 1),
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.EXPIRED, e);

    e = PeppolCertificateChecker.checkPeppolAPCertificate (PeppolKeyStoreHelper.Config2018.CERTIFICATE_PILOT_AP,
                                                           null,
                                                           ETriState.UNDEFINED,
                                                           ETriState.UNDEFINED);
    assertEquals (EPeppolCertificateCheckResult.UNSUPPORTED_ISSUER, e);
  }
}
