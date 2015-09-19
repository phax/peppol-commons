package com.helger.peppol.testfiles.sbdh;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.io.resource.ClassPathResource;

/**
 * Test class for class {@link PeppolSBDHTestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolSBDHTestFilesTest
{
  @Test
  public void testExists ()
  {
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllBadCases ())
      assertTrue (aRes.getPath (), aRes.exists ());
  }
}
