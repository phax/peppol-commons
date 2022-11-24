/*
 * Copyright (C) 2015-2022 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.smpclient.url;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.TextParseException;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.codec.Base32Codec;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.dns.naptr.NaptrLookup;
import com.helger.dns.naptr.NaptrResolver;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.security.messagedigest.EMessageDigestAlgorithm;
import com.helger.security.messagedigest.MessageDigestValue;

/**
 * An abstract implementation of {@link IBDXLURLProvider} that support U-NAPTR
 * record resolution.
 *
 * @author Philip Helger
 * @since 8.1.7
 */
@ThreadSafe
public abstract class AbstractBDXLURLProvider implements IBDXLURLProvider
{
  public static final boolean DEFAULT_USE_DNS_CACHE = false;
  public static final boolean DEFAULT_NAPTR_DEBUG = false;
  public static final Charset URL_CHARSET = StandardCharsets.UTF_8;
  public static final Locale URL_LOCALE = Locale.US;
  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractBDXLURLProvider.class);

  private final SimpleReadWriteLock m_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("m_aRWLock")
  private boolean m_bLowercaseValueBeforeHashing = true;
  @GuardedBy ("m_aRWLock")
  private boolean m_bAddIdentifierSchemeToZone = true;
  @GuardedBy ("m_aRWLock")
  private String m_sNAPTRServiceName = "not-set";
  @GuardedBy ("m_aRWLock")
  private boolean m_bUseDNSCache = DEFAULT_USE_DNS_CACHE;
  @GuardedBy ("m_aRWLock")
  private final ICommonsMap <String, String> m_aDNSCache = new CommonsHashMap <> ();
  private final ICommonsList <InetAddress> m_aCustomDNSServers = new CommonsArrayList <> ();
  @GuardedBy ("m_aRWLock")
  private boolean m_bUseNaptrDebug = DEFAULT_NAPTR_DEBUG;

  /**
   * Default constructor.
   */
  public AbstractBDXLURLProvider ()
  {}

  public final boolean isLowercaseValueBeforeHashing ()
  {
    return m_aRWLock.readLockedBoolean ( () -> m_bLowercaseValueBeforeHashing);
  }

  public final void setLowercaseValueBeforeHashing (final boolean bLowercaseValueBeforeHashing)
  {
    m_aRWLock.writeLocked ( () -> m_bLowercaseValueBeforeHashing = bLowercaseValueBeforeHashing);
  }

  public final boolean isAddIdentifierSchemeToZone ()
  {
    return m_aRWLock.readLockedBoolean ( () -> m_bAddIdentifierSchemeToZone);
  }

  public final void setAddIdentifierSchemeToZone (final boolean bAddIdentifierSchemeToZone)
  {
    m_aRWLock.writeLocked ( () -> m_bAddIdentifierSchemeToZone = bAddIdentifierSchemeToZone);
  }

  @Nonnull
  @Nonempty
  public final String getNAPTRServiceName ()
  {
    return m_aRWLock.readLockedGet ( () -> m_sNAPTRServiceName);
  }

  public final void setNAPTRServiceName (@Nonnull @Nonempty final String sNAPTRServiceName)
  {
    ValueEnforcer.notEmpty (sNAPTRServiceName, "NAPTRServiceName");
    m_aRWLock.writeLocked ( () -> m_sNAPTRServiceName = sNAPTRServiceName);
  }

  public final boolean isUseDNSCache ()
  {
    return m_aRWLock.readLockedBoolean ( () -> m_bUseDNSCache);
  }

  /**
   * Enable or disable internal DNS caching. By default it is enabled.
   *
   * @param bUseDNSCache
   *        <code>true</code> to enable caching, <code>false</code> to disable
   *        it.
   */
  public final void setUseDNSCache (final boolean bUseDNSCache)
  {
    m_aRWLock.writeLocked ( () -> m_bUseDNSCache = bUseDNSCache);
  }

  /**
   * Remove all internal DNS cache entries.
   */
  public final void clearDNSCache ()
  {
    m_aRWLock.writeLocked (m_aDNSCache::clear);
  }

  @Nullable
  public final String getDNSCacheEntry (@Nullable final String sName)
  {
    return StringHelper.hasText (sName) ? m_aRWLock.readLockedGet ( () -> m_aDNSCache.get (sName)) : null;
  }

  @Nonnull
  @ReturnsMutableCopy
  public final ICommonsMap <String, String> getAllDNSCacheEntries ()
  {
    return m_aRWLock.readLockedGet (m_aDNSCache::getClone);
  }

  /**
   * Add entries to the cache. This might be helpful when there is a persistent
   * cache (outside this class) and the old cache entries should be re-added.
   *
   * @param aEntries
   *        The entries to be added. May be <code>null</code>.
   */
  public final void addDNSCacheEntries (@Nullable final Map <String, String> aEntries)
  {
    if (aEntries != null && !aEntries.isEmpty ())
      m_aRWLock.writeLocked ( () -> m_aDNSCache.putAll (aEntries));
  }

  public final void addDNSCacheEntry (@Nonnull @Nonempty final String sName, @Nonnull final String sNaptrValue)
  {
    ValueEnforcer.notEmpty (sName, "Name");
    ValueEnforcer.notNull (sNaptrValue, "Value");
    m_aRWLock.writeLocked ( () -> m_aDNSCache.put (sName, sNaptrValue));
  }

  /**
   * @return A mutable list of custom DNS servers to be used for resolving DNS
   *         entries. Never <code>null</code> but maybe empty.
   */
  @Nonnull
  @ReturnsMutableObject
  public final ICommonsList <InetAddress> customDNSServers ()
  {
    return m_aCustomDNSServers;
  }

  public final boolean isUseNaptrDebug ()
  {
    return m_aRWLock.readLockedBoolean ( () -> m_bUseNaptrDebug);
  }

  public final void setUseNaptrDebug (final boolean b)
  {
    m_aRWLock.writeLocked ( () -> m_bUseNaptrDebug = b);
  }

  /**
   * Get the Base32 encoded (without padding), SHA-256
   * hash-string-representation of the passed value using the
   * {@link #URL_CHARSET} encoding.
   *
   * @param sValueToHash
   *        The value to be hashed. May not be <code>null</code>.
   * @return The non-<code>null</code> String containing the hash value.
   */
  @Nonnull
  public static String getHashValueStringRepresentation (@Nonnull final String sValueToHash)
  {
    final byte [] aMessageDigest = MessageDigestValue.create (sValueToHash.getBytes (URL_CHARSET),
                                                              EMessageDigestAlgorithm.SHA_256)
                                                     .bytes ();
    return new Base32Codec ().setAddPaddding (false).getEncodedAsString (aMessageDigest, StandardCharsets.ISO_8859_1);
  }

  @Nonnull
  protected static String internalGetDNSName (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                              final boolean bLowercaseValueBeforeHashing,
                                              final boolean bAddIdentifierSchemeToZone,
                                              @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    // Ensure the DNS zone name ends with a dot!
    if (StringHelper.hasText (sSMLZoneName))
      ValueEnforcer.isTrue (StringHelper.endsWith (sSMLZoneName, '.'),
                            () -> "if an SML zone name is specified, it must end with a dot (.). Value is: " +
                                  sSMLZoneName);

    final StringBuilder ret = new StringBuilder ();

    // Append the hashed identifier part
    {
      String sIdentifierValue = bAddIdentifierSchemeToZone ? aParticipantIdentifier.getValue ()
                                                           : aParticipantIdentifier.getURIEncoded ();
      if (bLowercaseValueBeforeHashing)
        sIdentifierValue = sIdentifierValue.toLowerCase (URL_LOCALE);
      ret.append (getHashValueStringRepresentation (sIdentifierValue)).append ('.');
    }

    // append the identifier scheme
    if (aParticipantIdentifier.hasScheme () && bAddIdentifierSchemeToZone)
    {
      // Check identifier scheme
      String sIdentifierScheme = aParticipantIdentifier.getScheme ();
      if (bLowercaseValueBeforeHashing)
        sIdentifierScheme = sIdentifierScheme.toLowerCase (URL_LOCALE);
      ret.append (sIdentifierScheme).append ('.');
    }

    // append the SML DNS zone name (if available)
    if (StringHelper.hasText (sSMLZoneName))
    {
      // If it is present, it always ends with a dot
      ret.append (sSMLZoneName);
    }

    // in some cases it gives a problem later when trying to retrieve the
    // participant's metadata.
    // That's why we cut of the dot here
    ret.deleteCharAt (ret.length () - 1);
    return ret.toString ();
  }

  @Nonnull
  public String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    return internalGetDNSName (aParticipantIdentifier,
                               isLowercaseValueBeforeHashing (),
                               isAddIdentifierSchemeToZone (),
                               sSMLZoneName);
  }

  @Nonnull
  public URI getSMPURIOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                     @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    // Ensure the DNS zone name ends with a dot!
    if (StringHelper.hasText (sSMLZoneName) && !StringHelper.endsWith (sSMLZoneName, '.'))
      throw new SMPDNSResolutionException ("if an SML zone name is specified, it must end with a dot (.). Value is: " +
                                           sSMLZoneName);

    final String sBuildDomainName = getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName);

    final boolean bUseDNSCache = isUseDNSCache ();
    // Already in cache?
    String sResolvedNAPTR = bUseDNSCache ? getDNSCacheEntry (sBuildDomainName) : null;
    if (sResolvedNAPTR == null)
    {
      // Now do the NAPTR resolving
      final String sServiceName = getNAPTRServiceName ();
      try
      {
        sResolvedNAPTR = NaptrResolver.builder ()
                                      .domainName (sBuildDomainName)
                                      .naptrRecords (NaptrLookup.builder ()
                                                                .domainName (sBuildDomainName)
                                                                .customDNSServers (customDNSServers ())
                                                                .maxRetries (1)
                                                                .debugMode (m_bUseNaptrDebug))
                                      .serviceName (sServiceName)
                                      .build ()
                                      .resolveUNAPTR ();
      }
      catch (final TextParseException ex)
      {
        throw new SMPDNSResolutionException ("Failed to parse '" + sBuildDomainName + "'", ex);
      }

      if (sResolvedNAPTR == null)
      {
        // Since 6.2.0 this a checked exception
        throw new SMPDNSResolutionException ("Failed to resolve '" +
                                             sBuildDomainName +
                                             "' and service '" +
                                             sServiceName +
                                             "' to a DNS U-NAPTR");
      }

      if (LOGGER.isInfoEnabled ())
        LOGGER.info ("Resolved domain name '" +
                     sBuildDomainName +
                     "' and service '" +
                     sServiceName +
                     "' to URL '" +
                     sResolvedNAPTR +
                     "'");

      if (bUseDNSCache)
      {
        // Put in cache
        addDNSCacheEntry (sBuildDomainName, sResolvedNAPTR);
      }
    }

    try
    {
      return new URI (sResolvedNAPTR);
    }
    catch (final URISyntaxException ex)
    {
      throw new SMPDNSResolutionException ("Error building SMP URI from string '" + sResolvedNAPTR + "'", ex);
    }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("LowercaseValueBeforeHashing", m_bLowercaseValueBeforeHashing)
                                       .append ("AddIdentifierSchemeToZone", m_bAddIdentifierSchemeToZone)
                                       .append ("NAPTRServiceName", m_sNAPTRServiceName)
                                       .append ("UseDNSCache", m_bUseDNSCache)
                                       .append ("DNSCache", m_aDNSCache)
                                       .append ("CustomDNSServers", m_aCustomDNSServers)
                                       .getToString ();
  }
}
