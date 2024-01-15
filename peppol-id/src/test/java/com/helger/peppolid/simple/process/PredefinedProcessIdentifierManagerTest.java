package com.helger.peppolid.simple.process;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.peppolid.peppol.process.PredefinedProcessIdentifierManager;

/**
 * Test class for class {@link PredefinedProcessIdentifierManager}.
 *
 * @author Philip Helger
 */
public class PredefinedProcessIdentifierManagerTest
{
  @Test
  public void testBasic ()
  {
    final String sValid = "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0";
    assertTrue (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid));
    assertFalse (PredefinedProcessIdentifierManager.containsProcessIdentifierWithID (sValid + "0"));

    assertNotNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid));
    assertNull (PredefinedProcessIdentifierManager.getProcessIdentifierOfID (sValid + "0"));
  }
}
