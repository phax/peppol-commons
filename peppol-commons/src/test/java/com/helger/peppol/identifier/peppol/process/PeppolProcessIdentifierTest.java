package com.helger.peppol.identifier.peppol.process;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.peppol.CPeppolIdentifier;

/**
 * Test class for class {@link PeppolProcessIdentifier}.
 *
 * @author Philip Helger
 */
public final class PeppolProcessIdentifierTest
{
  @Test
  public void testIsValidProcessIdentifierValue ()
  {
    assertFalse (IPeppolProcessIdentifier.isValidValue (null));
    assertFalse (IPeppolProcessIdentifier.isValidValue (""));

    assertTrue (IPeppolProcessIdentifier.isValidValue ("proc1"));
    assertTrue (IPeppolProcessIdentifier.isValidValue ("proc2 "));

    assertTrue (IPeppolProcessIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                 CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH)));
    assertFalse (IPeppolProcessIdentifier.isValidValue (StringHelper.getRepeated ('a',
                                                                                  CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                       1)));
  }

  @Test
  public void testHasDefaultProcessIdentifierScheme ()
  {
    assertTrue (PeppolProcessIdentifier.createWithDefaultScheme ("abc").hasDefaultScheme ());
    assertFalse (new PeppolProcessIdentifier ("proctype", "abc").hasDefaultScheme ());
  }

  @Test
  public void testIsValidProcessIdentifier ()
  {
    assertFalse (PeppolProcessIdentifier.isValidURIPart (null));
    assertFalse (PeppolProcessIdentifier.isValidURIPart (""));

    assertTrue (PeppolProcessIdentifier.isValidURIPart ("process::proc1"));
    assertTrue (PeppolProcessIdentifier.isValidURIPart ("process::proc2 "));

    assertFalse (PeppolProcessIdentifier.isValidURIPart ("processany-actorid-dummythatiswaytoolongforwhatisexpected::proc2"));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("process::" +
                                                         StringHelper.getRepeated ('a',
                                                                                   CPeppolIdentifier.MAX_PROCESS_IDENTIFIER_VALUE_LENGTH +
                                                                                        1)));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("process:proc2"));
    assertFalse (PeppolProcessIdentifier.isValidURIPart ("processproc2"));
  }

}
