package com.helger.peppol.servicedomain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.base.url.URLHelper;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.SMLInfo;

/**
 * Test class for class {@link EPeppolNetwork}.
 *
 * @author Philip Helger
 */
public final class EPeppolNetworkTest
{
  @Test
  public void testBasic ()
  {
    for (final var e : EPeppolNetwork.values ())
    {
      assertTrue (StringHelper.isNotEmpty (e.getID ()));

      assertTrue (StringHelper.isNotEmpty (e.getDisplayName ()));

      assertTrue (StringHelper.isNotEmpty (e.getDirectoryURL ()));
      assertNotNull (URLHelper.getAsURL (e.getDirectoryURL ()));

      assertNotNull (e.getSMLInfo ());

      assertSame (e, EPeppolNetwork.getFromIDOrNull (e.getID ()));
    }

    assertTrue (EPeppolNetwork.PRODUCTION.isProduction ());
    assertFalse (EPeppolNetwork.PRODUCTION.isTest ());

    assertFalse (EPeppolNetwork.TEST.isProduction ());
    assertTrue (EPeppolNetwork.TEST.isTest ());
  }

  @Test
  public void testGetFromESMLOrNull ()
  {
    assertSame (EPeppolNetwork.PRODUCTION, EPeppolNetwork.getFromESMLOrNull (ESML.DIGIT_PRODUCTION));
    assertSame (EPeppolNetwork.TEST, EPeppolNetwork.getFromESMLOrNull (ESML.DIGIT_TEST));
    assertNull (EPeppolNetwork.getFromESMLOrNull (null));
  }

  @Test
  public void testGetFromSMLInfoOrNull ()
  {
    assertSame (EPeppolNetwork.PRODUCTION, EPeppolNetwork.getFromSMLInfoOrNull (ESML.DIGIT_PRODUCTION));
    assertSame (EPeppolNetwork.TEST, EPeppolNetwork.getFromSMLInfoOrNull (ESML.DIGIT_TEST));
    assertNull (EPeppolNetwork.getFromSMLInfoOrNull (null));

    assertNull (EPeppolNetwork.getFromSMLInfoOrNull (SMLInfo.builder ()
                                                            .idNew ()
                                                            .displayName ("anything")
                                                            .dnsZone ("text.example.org")
                                                            .managementServiceURL ("https://bla.foo.com/sthg")
                                                            .build ()));
    assertSame (EPeppolNetwork.PRODUCTION,
                EPeppolNetwork.getFromSMLInfoOrNull (SMLInfo.builder ()
                                                            .id ("digitprod")
                                                            .displayName ("SML")
                                                            .dnsZone ("edelivery.tech.ec.europa.eu.")
                                                            .managementServiceURL ("https://edelivery.tech.ec.europa.eu/edelivery-sml")
                                                            .clientCertificateRequired (true)
                                                            .build ()));
  }
}
