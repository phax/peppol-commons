package com.helger.peppol.testfiles.ubl;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.locale.LocaleCache;
import com.helger.peppol.testfiles.TestResource;

/**
 * Test class for class {@link PeppolUBLTestFiles}
 *
 * @author Philip Helger
 */
public final class PeppolUBLTestFilesTest
{
  @Test
  public void testExists ()
  {
    final Locale AT = LocaleCache.getInstance ().getLocale ("de", "AT");
    for (final EPeppolUBLTestFileType e : EPeppolUBLTestFileType.values ())
    {
      for (final IReadableResource aRes : PeppolUBLTestFiles.getSuccessFiles (e))
        assertTrue (aRes.getPath (), aRes.exists ());
      for (final IReadableResource aRes : PeppolUBLTestFiles.getSuccessFiles (e, AT))
        assertTrue (aRes.getPath (), aRes.exists ());
      for (final TestResource aRes : PeppolUBLTestFiles.getErrorFiles (e))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
      for (final TestResource aRes : PeppolUBLTestFiles.getErrorFiles (e, AT))
        assertTrue (aRes.getPath (), aRes.getResource ().exists ());
    }
  }
}
