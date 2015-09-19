package com.helger.peppol.testfiles.official;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.io.resource.ClassPathResource;

/**
 * Test class for class {@link OfficialTestFiles}
 *
 * @author Philip Helger
 */
public final class OfficialTestFilesTest
{
  @Test
  public void testExists ()
  {
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesCatalogue_01_T19 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesCatalogue_01_T58 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrder_03_T01 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesInvoice_04_T10 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesBilling_05_T14 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrdering_28_T01 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesOrdering_28_T76 ())
      assertTrue (aRes.getPath (), aRes.exists ());
    for (final ClassPathResource aRes : OfficialTestFiles.getAllTestFilesDespatchAdvice_30_T16 ())
      assertTrue (aRes.getPath (), aRes.exists ());
  }
}
