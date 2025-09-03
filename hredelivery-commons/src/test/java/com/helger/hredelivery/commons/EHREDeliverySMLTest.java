package com.helger.hredelivery.commons;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;

/**
 * Test class for class {@link EHREDeliverySML}.
 *
 * @author Philip Helger
 */
public final class EHREDeliverySMLTest
{
  @Test
  public void testBasic ()
  {
    for (final EHREDeliverySML e : EHREDeliverySML.values ())
    {
      assertTrue (StringHelper.isNotEmpty (e.getZoneName ()));
    }
  }
}
