package com.helger.peppol.utils;

import java.security.cert.CRL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.timing.StopWatch;
import com.helger.commons.url.EURLProtocol;

/**
 * A class for downloading CRL data.
 *
 * @author Philip Helger
 * @since 9.3.0
 */
public class CRLDownloader
{
  private static final Logger LOGGER = LoggerFactory.getLogger (CRLDownloader.class);

  private final IUrlDownloader m_aUrlDownloader;

  /**
   * Use the system default URL downloader.
   */
  public CRLDownloader ()
  {
    this (IUrlDownloader.createDefault ());
  }

  /**
   * Constructor using the provided URL downloader.
   *
   * @param aUrlDownloader
   *        The URL downloader to use. May not be <code>null</code>.
   */
  public CRLDownloader (@Nonnull final IUrlDownloader aUrlDownloader)
  {
    ValueEnforcer.notNull (aUrlDownloader, "UrlDownloader");
    m_aUrlDownloader = aUrlDownloader;
  }

  /**
   * @return The internal URL downloader used. Never <code>null</code>.
   */
  @Nonnull
  public final IUrlDownloader getUrlDownloader ()
  {
    return m_aUrlDownloader;
  }

  @Nullable
  public CRL downloadCRL (@Nonnull final String sCRLURL)
  {
    if (EURLProtocol.HTTP.isUsedInURL (sCRLURL) || EURLProtocol.HTTPS.isUsedInURL (sCRLURL))
    {
      // Try to download from remote URL
      LOGGER.info ("Downloading CRL from URL '" + sCRLURL + "'");
      final StopWatch aSW = StopWatch.createdStarted ();
      int nByteCount = 0;
      try
      {
        final byte [] aCRLBytes = getUrlDownloader ().downloadURL (sCRLURL);
        if (aCRLBytes != null)
        {
          nByteCount = aCRLBytes.length;
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Finished downloading CRL and received " + nByteCount + " bytes");

          return CRLHelper.convertToCRL (aCRLBytes);
        }
      }
      catch (final Exception ex)
      {
        LOGGER.error ("Error downloading CRL from URL '" + sCRLURL + "'", ex);
      }
      finally
      {
        aSW.stop ();
        if (aSW.getMillis () > 1_000)
          LOGGER.info ("Downloading the CRL from '" +
                       sCRLURL +
                       "' took " +
                       aSW.getMillis () +
                       " milliseconds for " +
                       nByteCount +
                       " bytes");
      }
    }

    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("UrlDownloader", m_aUrlDownloader).getToString ();
  }
}
