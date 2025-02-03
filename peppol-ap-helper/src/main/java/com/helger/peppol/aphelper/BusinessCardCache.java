/*
 * Copyright (C) 2025 Philip Helger
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
package com.helger.peppol.aphelper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.cache.MappedCache;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.expiration.ExpiringObject;
import com.helger.httpclient.HttpClientManager;
import com.helger.httpclient.HttpClientSettings;
import com.helger.httpclient.response.ResponseHandlerByteArray;
import com.helger.peppol.businesscard.generic.PDBusinessCard;
import com.helger.peppol.businesscard.helper.PDBusinessCardHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

/**
 * Cache for BusinessCards of participants queried from an SMP.
 *
 * @author Philip Helger
 * @since 10.0.0
 */
public class BusinessCardCache
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BusinessCardCache.class);

  @Nonnull
  private static ExpiringObject <PDBusinessCard> _fetchBC (@Nonnull final ISMLInfo aSMLInfo,
                                                           @Nonnull final HttpClientSettings aHCS,
                                                           @Nonnull final Duration aCachingDuration,
                                                           @Nonnull final IParticipantIdentifier aPI)
  {
    try
    {
      final String sBCURL = "http://" +
                            PeppolURLProvider.INSTANCE.getDNSNameOfParticipant (aPI, aSMLInfo) +
                            "/businesscard/" +
                            aPI.getURIPercentEncoded ();
      LOGGER.info ("Fetching Business Card from '" + sBCURL + "'");
      byte [] aData = null;
      try (final HttpClientManager aHttpClientMgr = HttpClientManager.create (aHCS))
      {
        final HttpGet aGet = new HttpGet (sBCURL);
        aData = aHttpClientMgr.execute (aGet, new ResponseHandlerByteArray ());
      }
      catch (final Exception ex)
      {
        // Ignore - means no BC
      }

      PDBusinessCard aBC = null;
      if (aData != null)
      {
        // Try parsing
        aBC = PDBusinessCardHelper.parseBusinessCard (aData, StandardCharsets.UTF_8);
      }

      // Create cached entry
      return ExpiringObject.ofDuration (aBC, aCachingDuration);
    }
    catch (final SMPDNSResolutionException ex)
    {
      throw new IllegalStateException (ex);
    }
  }

  private final MappedCache <IParticipantIdentifier, String, ExpiringObject <PDBusinessCard>> m_aCache;

  /**
   * Constructor. Caches the entries for 1 hour with a maximum of 1000 entries
   *
   * @param aSMLInfo
   *        SML to use. To differentiate between test and production.
   * @param aHCS
   *        The Http client settings to be used. Could be e.g.
   *        {@link com.helger.smpclient.httpclient.SMPHttpClientSettings}
   */
  public BusinessCardCache (@Nonnull final ISMLInfo aSMLInfo, @Nonnull final HttpClientSettings aHCS)
  {
    m_aCache = new MappedCache <> (IParticipantIdentifier::getURIEncoded,
                                   pi -> _fetchBC (aSMLInfo, aHCS, Duration.ofHours (1), pi),
                                   1_000,
                                   "PeppolBusinessCardCache",
                                   true);
  }

  @Nullable
  private ExpiringObject <PDBusinessCard> _getActive (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    ExpiringObject <PDBusinessCard> aBC = m_aCache.getFromCache (aParticipantID);
    if (aBC.isExpiredNow ())
    {
      LOGGER.info ("The cached entry for Participant ID '" +
                   aParticipantID.getURIEncoded () +
                   "' is expired and needs to be re-fetched.");

      // Read from SMP again
      m_aCache.removeFromCache (aParticipantID);
      aBC = m_aCache.getFromCache (aParticipantID);
    }
    return aBC;
  }

  /**
   * Get the cached Business Card of the provided participant ID.
   *
   * @param aParticipantID
   *        The participant ID to query. May not be <code>null</code>.
   * @return <code>null</code> if no Business Card is present.
   */
  @Nullable
  public PDBusinessCard getBusinessCard (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    return _getActive (aParticipantID).getObject ();
  }

  /**
   * Get the country code contained in the Business Card of the provided
   * participant ID.
   *
   * @param aParticipantID
   *        The participant ID to query. May not be <code>null</code>.
   * @return <code>null</code> if no Business Card or no Business Card Entity
   *         with a country code is present.
   */
  @Nullable
  public String getCountryCode (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    final PDBusinessCard aBC = getBusinessCard (aParticipantID);
    if (aBC == null)
      return null;
    if (aBC.businessEntities ().isEmpty ())
      return null;
    return aBC.businessEntities ().getFirstOrNull ().getCountryCode ();
  }

  @Nonnull
  public EChange clearCache ()
  {
    return m_aCache.clearCache ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Cache", m_aCache).getToString ();
  }
}
