/**
 * Copyright (C) 2015-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.utils;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;

/**
 * Helper class to resolve NAPTR DNS records for BDMSL
 *
 * @author Philip Helger
 * @since 5.1.5
 */
@Immutable
public final class NAPTRResolver
{
  /** U NAPTR service name for e-SENS/PEPPOL */
  public static final String DNS_UNAPTR_SERVICE_NAME_META_SMP = "Meta:SMP";

  private static final Logger LOGGER = LoggerFactory.getLogger (NAPTRResolver.class);

  private NAPTRResolver ()
  {}

  @Nullable
  private static String _getAppliedNAPTRRegEx (@Nonnull final String sRegEx, @Nonnull final String sDomainName)
  {
    final char cSep = sRegEx.charAt (0);
    final int nSecond = sRegEx.indexOf (cSep, 1);
    if (nSecond < 0)
      return null;
    final String sEre = sRegEx.substring (1, nSecond);
    final int nThird = sRegEx.indexOf (cSep, nSecond + 1);
    if (nThird < 0)
      return null;
    final String sRepl = sRegEx.substring (nSecond + 1, nThird);
    final String sFlags = sRegEx.substring (nThird + 1);

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("NAPTR regex: '" + sEre + "' - '" + sRepl + "' - '" + sFlags + "'");

    final int nOptions = "i".equalsIgnoreCase (sFlags) ? Pattern.CASE_INSENSITIVE : 0;
    final String ret = RegExHelper.stringReplacePattern (sEre, nOptions, sDomainName, sRepl);

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("  NAPTR replacement: '" + sDomainName + "' -> '" + ret + "'");
    return ret;
  }

  /**
   * Look up the passed DNS name (usually a dynamic DNS name that was created by
   * an algorithm) and resolve any BDMSL U-NAPTR records.
   *
   * @param sDNSName
   *        The created DNS name. May be <code>null</code>.
   * @param sPrimaryDNSServer
   *        An optional primary DNS server to be used for resolution. May be
   *        <code>null</code> in which case the default resolver will be used.
   * @param sServiceName
   *        The service name (inside the U NAPTR) to query. May neither be
   *        <code>null</code> nor empty. For e-SENS/PEPPOL use "Meta:SMP"
   * @return <code>null</code> if no U-NAPTR was found or could not be resolved.
   *         If non-<code>null</code> the fully qualified domain name, including
   *         and protocol (like http://) is returned.
   * @throws TextParseException
   *         In case the original DNS name does not constitute a valid DNS name
   *         and could not be parsed
   */
  @Nullable
  public static String resolveFromNAPTR (@Nullable final String sDNSName,
                                         @Nullable final String sPrimaryDNSServer,
                                         @Nonnull @Nonempty final String sServiceName) throws TextParseException
  {
    ValueEnforcer.notEmpty (sServiceName, "ServiceName");

    if (StringHelper.hasNoText (sDNSName))
      return null;

    // Use the default (static) cache that is used by default
    final Lookup aLookup = new Lookup (sDNSName, Type.NAPTR);
    final int nMaxRetries = 1;
    ExtendedResolver aNewResolver;
    try
    {
      aNewResolver = new ExtendedResolver ();
      // A special primary DNS server is provided - use as the first one
      if (StringHelper.hasText (sPrimaryDNSServer))
        aNewResolver.addResolver (new SimpleResolver (sPrimaryDNSServer));
      aNewResolver.addResolver (Lookup.getDefaultResolver ());
      // Timeout in seconds
      aNewResolver.setTimeout (4);
      aNewResolver.setRetries (nMaxRetries);
      aLookup.setResolver (aNewResolver);
    }
    catch (final UnknownHostException ex)
    {
      // Stay with the default resolver
      LOGGER.error ("Failed to create DNS resolver", ex);
      return null;
    }

    // By default try UDP
    // Stumbled upon an issue, where UDP datagram size was too small for MTU
    // size of 1500
    Record [] aRecords;
    int nLeft = nMaxRetries;
    do
    {
      aRecords = aLookup.run ();
      --nLeft;
    } while (aLookup.getResult () == Lookup.TRY_AGAIN && nLeft >= 0);

    if (aLookup.getResult () == Lookup.TRY_AGAIN)
    {
      aNewResolver.setTCP (true);

      // Retry with TCP instead of UDP
      nLeft = nMaxRetries;
      do
      {
        aRecords = aLookup.run ();
        --nLeft;
      } while (aLookup.getResult () == Lookup.TRY_AGAIN && nLeft >= 0);
    }

    if (aLookup.getResult () != Lookup.SUCCESSFUL)
    {
      // Wrong domain name
      if (LOGGER.isWarnEnabled ())
        LOGGER.warn ("Error looking up '" + sDNSName + "': " + aLookup.getErrorString ());
      return null;
    }

    final ICommonsList <NAPTRRecord> aMatchingRecords = new CommonsArrayList <> ();
    for (final Record record : aRecords)
    {
      final NAPTRRecord naptrRecord = (NAPTRRecord) record;
      if ("U".equalsIgnoreCase (naptrRecord.getFlags ()) && sServiceName.equals (naptrRecord.getService ()))
        aMatchingRecords.add (naptrRecord);
    }

    if (aMatchingRecords.isEmpty ())
    {
      // No matching NAPTR present
      if (LOGGER.isWarnEnabled ())
        LOGGER.warn ("No matching DNS NAPTR records returned for '" + sDNSName + "'");
      return null;
    }

    // Sort by order than by preference according to RFC 2915
    aMatchingRecords.sort ( (x, y) -> {
      int ret = x.getOrder () - y.getOrder ();
      if (ret == 0)
        ret = x.getPreference () - y.getPreference ();
      return ret;
    });
    for (final NAPTRRecord aRecord : aMatchingRecords)
    {
      // The "U" record is terminal, so a RegExp must be present
      final String sRegEx = aRecord.getRegexp ();
      // At least 3 separator chars must be present :)
      if (StringHelper.getLength (sRegEx) > 3)
      {
        final String sFinalDNSName = _getAppliedNAPTRRegEx (sRegEx, sDNSName);
        if (sFinalDNSName != null)
        {
          if (LOGGER.isDebugEnabled ())
            LOGGER.debug ("Using '" + sFinalDNSName + "' for original DNS name '" + sDNSName + "'");

          return sFinalDNSName;
        }
      }
    }

    // Weird - no regexp present
    if (LOGGER.isWarnEnabled ())
      LOGGER.warn ("None of the matching DNS NAPTR records for '" +
                   sDNSName +
                   "' has a valid regular expression. Details: " +
                   aMatchingRecords);
    return null;
  }
}
