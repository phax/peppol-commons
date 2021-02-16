package com.helger.xsds.peppol.smp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.io.resource.ClassPathResource;

/**
 * Test class for class {@link CSMPDataTypes}.
 *
 * @author Philip Helger
 */
public final class CSMPDataTypesTest
{
  @Test
  public void testBasic ()
  {
    assertTrue (CSMPDataTypes.getXSDResourcePeppolSMP ().exists ());
    assertTrue (CSMPDataTypes.getXSDResourcePeppolSMP ().canRead ());
    for (final ClassPathResource aRes : CSMPDataTypes.getAllXSDResources ())
      assertTrue (aRes.exists ());
  }
}
