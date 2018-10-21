package com.helger.peppol.url;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * New checked excpetion to be thrown if DNS resolution fails.
 *
 * @author Philip Helger
 * @since 6.2.0
 */
public class PeppolDNSResolutionException extends Exception
{
  public PeppolDNSResolutionException (@Nonnull final String sMessage)
  {
    super (sMessage);
  }

  public PeppolDNSResolutionException (@Nonnull final String sMessage, @Nullable final Throwable aCause)
  {
    super (sMessage, aCause);
  }
}
