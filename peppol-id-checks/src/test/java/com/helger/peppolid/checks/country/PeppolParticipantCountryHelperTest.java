/*
 * Copyright (C) 2026 Philip Helger
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
package com.helger.peppolid.checks.country;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.collection.commons.ICommonsMap;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.pidscheme.EPredefinedParticipantIdentifierScheme;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.text.locale.country.CountryCache;

/**
 * Test class for class {@link PeppolParticipantCountryHelper}.
 *
 * @author Philip Helger
 */
public final class PeppolParticipantCountryHelperTest
{
  @Test
  public void testAllCountrySpecificSchemes ()
  {
    // Every scheme that is bound to a country must resolve to that country
    for (final EPredefinedParticipantIdentifierScheme e : EPredefinedParticipantIdentifierScheme.values ())
    {
      final String sCountryCode = e.getCountryCode ();
      if ("international".equals (sCountryCode))
        continue;

      assertNull ("No such country '" + sCountryCode + "' for " + e,
                  CountryCache.getInstance ().getCountry (sCountryCode) == null ? sCountryCode : null);
      assertEquals ("Failed for " + e,
                    sCountryCode,
                    PeppolParticipantCountryHelper.getCountryCode (e.createIdentifierValue ("0123456789")));
    }
  }

  @Test
  public void testExistingMappings ()
  {
    // The mappings that were hard coded before
    assertEquals (PeppolParticipantCountryHelper.AU,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:9312345678901"));
    assertEquals (PeppolParticipantCountryHelper.AU,
                  PeppolParticipantCountryHelper.getCountryCode ("0151:12345678901"));

    assertEquals (PeppolParticipantCountryHelper.BE,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:5412345678901"));
    assertEquals (PeppolParticipantCountryHelper.BE, PeppolParticipantCountryHelper.getCountryCode ("0208:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.BE,
                  PeppolParticipantCountryHelper.getCountryCode ("9925:BE0123456789"));
    assertEquals (PeppolParticipantCountryHelper.BE,
                  PeppolParticipantCountryHelper.getCountryCode ("9918:BE71096123456769"));
    assertEquals (PeppolParticipantCountryHelper.BE, PeppolParticipantCountryHelper.getCountryCode ("9956:0123456789"));

    assertEquals (PeppolParticipantCountryHelper.DE,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:4012345678901"));
    assertEquals (PeppolParticipantCountryHelper.DE,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:4401234567890"));
    // 441-449 is not assigned - the German GS1 range ends with 440
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0088:4412345678901"));
    assertEquals (PeppolParticipantCountryHelper.DE,
                  PeppolParticipantCountryHelper.getCountryCode ("0204:05314000-12345-67"));
    assertEquals (PeppolParticipantCountryHelper.DE, PeppolParticipantCountryHelper.getCountryCode ("0246:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.DE,
                  PeppolParticipantCountryHelper.getCountryCode ("9930:DE123456789"));
    assertEquals (PeppolParticipantCountryHelper.DE, PeppolParticipantCountryHelper.getCountryCode ("9958:0123456789"));

    assertEquals (PeppolParticipantCountryHelper.DK, PeppolParticipantCountryHelper.getCountryCode ("0096:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.DK, PeppolParticipantCountryHelper.getCountryCode ("0184:DK12345678"));
    assertEquals (PeppolParticipantCountryHelper.DK, PeppolParticipantCountryHelper.getCountryCode ("0198:DK12345678"));
    assertEquals (PeppolParticipantCountryHelper.DK, PeppolParticipantCountryHelper.getCountryCode ("9901:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.DK, PeppolParticipantCountryHelper.getCountryCode ("9905:0123456789"));

    assertEquals (PeppolParticipantCountryHelper.FR, PeppolParticipantCountryHelper.getCountryCode ("0002:784301772"));
    assertEquals (PeppolParticipantCountryHelper.FR,
                  PeppolParticipantCountryHelper.getCountryCode ("0009:78430177200025"));
    assertEquals (PeppolParticipantCountryHelper.FR,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:3012345678901"));
    assertEquals (PeppolParticipantCountryHelper.FR,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:3712345678901"));
    assertEquals (PeppolParticipantCountryHelper.FR, PeppolParticipantCountryHelper.getCountryCode ("0225:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.FR,
                  PeppolParticipantCountryHelper.getCountryCode ("9957:FR12345678901"));

    assertEquals (PeppolParticipantCountryHelper.JP,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:4512345678901"));
    assertEquals (PeppolParticipantCountryHelper.JP, PeppolParticipantCountryHelper.getCountryCode ("0188:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.JP, PeppolParticipantCountryHelper.getCountryCode ("0221:0123456789"));

    assertEquals (PeppolParticipantCountryHelper.NZ,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:9412345678901"));

    assertEquals (PeppolParticipantCountryHelper.SK,
                  PeppolParticipantCountryHelper.getCountryCode ("0088:8581234567890"));
    assertEquals (PeppolParticipantCountryHelper.SK, PeppolParticipantCountryHelper.getCountryCode ("0245:0123456789"));
    assertEquals (PeppolParticipantCountryHelper.SK,
                  PeppolParticipantCountryHelper.getCountryCode ("9918:SK3112000000198742637541"));
  }

  @Test
  public void testGS1BasedSchemes ()
  {
    // GLN
    assertEquals ("AT", PeppolParticipantCountryHelper.getCountryCode ("0088:9001234567890"));
    assertEquals ("NL", PeppolParticipantCountryHelper.getCountryCode ("0088:8701234567890"));
    // Multi country prefixes return the primary country
    assertEquals ("IT", PeppolParticipantCountryHelper.getCountryCode ("0088:8001234567890"));
    assertEquals ("CH", PeppolParticipantCountryHelper.getCountryCode ("0088:7601234567890"));
    // GS1 prefixes without a country
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0088:9770123456789"));

    // GS1 uses the same value syntax as GLN
    assertEquals ("AT", PeppolParticipantCountryHelper.getCountryCode ("0209:9001234567890"));
  }

  @Test
  public void testIBAN ()
  {
    assertEquals ("AT", PeppolParticipantCountryHelper.getCountryCode ("9918:AT611904300234573201"));
    assertEquals ("NO", PeppolParticipantCountryHelper.getCountryCode ("9918:NO9386011117947"));
    // Peppol identifiers are commonly stored in lower case
    assertEquals ("NO", PeppolParticipantCountryHelper.getCountryCode ("9918:no9386011117947"));
    // Not an IBAN
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("9918:12345678"));
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("9918:"));
  }

  @Test
  public void testEUVAT ()
  {
    assertEquals ("AT", PeppolParticipantCountryHelper.getCountryCode ("9912:ATU12345678"));
    assertEquals ("DE", PeppolParticipantCountryHelper.getCountryCode ("9912:DE123456789"));
    // "EL" is the EU VAT prefix of Greece
    assertEquals ("GR", PeppolParticipantCountryHelper.getCountryCode ("9912:EL123456789"));
    // "XI" is the EU VAT prefix of Northern Ireland
    assertEquals ("GB", PeppolParticipantCountryHelper.getCountryCode ("9912:XI123456789"));
    // No leading country code
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("9912:123456789"));
  }

  @Test
  public void testNoCountry ()
  {
    // No value at all
    assertNull (PeppolParticipantCountryHelper.getCountryCode ((String) null));
    // No separator at all
    assertNull (PeppolParticipantCountryHelper.getCountryCode (""));
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0088"));
    // Unknown scheme
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("1234:0123456789"));
    // International schemes without a country in the value
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0060:812810734"));
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0130:0123456789"));
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("0199:0123456789"));
    assertNull (PeppolParticipantCountryHelper.getCountryCode ("9913:0123456789"));
  }

  @Test
  public void testParticipantIdentifier ()
  {
    final PeppolIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;

    assertEquals ("DE",
                  PeppolParticipantCountryHelper.getCountryCode (aIF.createParticipantIdentifierWithDefaultScheme ("0246:0123456789")));
    // The identifier factory lower cases the value
    assertEquals ("AT",
                  PeppolParticipantCountryHelper.getCountryCode (aIF.createParticipantIdentifierWithDefaultScheme ("9918:AT611904300234573201")));

    assertNull (PeppolParticipantCountryHelper.getCountryCode ((IParticipantIdentifier) null));
    // Only the default participant identifier scheme is evaluated
    assertNull (PeppolParticipantCountryHelper.getCountryCode (new SimpleParticipantIdentifier ("other-scheme",
                                                                                                "0246:0123456789")));
  }

  @Test
  public void testGetAllSchemeCountryCodes ()
  {
    final ICommonsMap <String, String> aMap = PeppolParticipantCountryHelper.getAllSchemeCountryCodes ();
    assertTrue (aMap.isNotEmpty ());
    assertEquals ("DE", aMap.get ("0246"));
    assertEquals ("BE", aMap.get ("0208"));
    // The schemes that carry the country inside the value are not contained
    assertFalse (aMap.containsKey (PeppolParticipantCountryHelper.ICD_GLN));
    assertFalse (aMap.containsKey (PeppolParticipantCountryHelper.ICD_GS1));
    assertFalse (aMap.containsKey (PeppolParticipantCountryHelper.ICD_EU_VAT));
    assertFalse (aMap.containsKey (PeppolParticipantCountryHelper.ICD_IBAN));

    // Modifying the returned map has no effect
    aMap.clear ();
    assertTrue (PeppolParticipantCountryHelper.getAllSchemeCountryCodes ().isNotEmpty ());
  }

  @Test
  public void testAllCountryCodesAreKnown ()
  {
    for (final EPredefinedParticipantIdentifierScheme e : EPredefinedParticipantIdentifierScheme.values ())
    {
      final String sCountryCode = PeppolParticipantCountryHelper.getCountryCode (e.createIdentifierValue ("0123456789"));
      if (sCountryCode != null)
        assertEquals ("Unknown country '" + sCountryCode + "' for " + e,
                      sCountryCode,
                      CountryCache.getInstance ().getCountry (sCountryCode).getCountry ().toUpperCase (Locale.ROOT));
    }
  }
}
