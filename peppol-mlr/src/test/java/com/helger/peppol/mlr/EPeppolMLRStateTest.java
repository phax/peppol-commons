package com.helger.peppol.mlr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link EPeppolMLRState}.
 *
 * @author Philip Helger
 */
public final class EPeppolMLRStateTest
{
  @Test
  public void testBasic ()
  {
    for (final EPeppolMLRState e : EPeppolMLRState.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertTrue (e.isSuccess () || e.isFailure ());
      assertFalse (e.isSuccess () && e.isFailure ());
      assertSame (e, EPeppolMLRState.getFromIDOrNull (e.getID ()));
    }
  }
}
