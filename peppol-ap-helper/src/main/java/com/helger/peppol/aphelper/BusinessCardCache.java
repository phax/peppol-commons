package com.helger.peppol.aphelper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.cache.MappedCache;
import com.helger.commons.datetime.PDTFactory;
import com.helger.httpclient.HttpClientManager;
import com.helger.httpclient.HttpClientSettings;
import com.helger.httpclient.response.ResponseHandlerByteArray;
import com.helger.peppol.businesscard.generic.PDBusinessCard;
import com.helger.peppol.businesscard.helper.PDBusinessCardHelper;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.smpclient.url.PeppolURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;

final class BusinessCardWithFetchDate
{
  private final PDBusinessCard m_aBC;
  private final LocalDateTime m_aExpirationDT;

  BusinessCardWithFetchDate (@Nullable final PDBusinessCard aBC, @Nonnull final LocalDateTime aExpirationDT)
  {
    m_aBC = aBC;
    m_aExpirationDT = aExpirationDT;
  }

  @Nullable
  public PDBusinessCard getBusinessCard ()
  {
    return m_aBC;
  }

  @Nullable
  public String getCountryCode ()
  {
    if (m_aBC == null)
      return null;
    if (m_aBC.businessEntities ().isEmpty ())
      return null;
    return m_aBC.businessEntities ().getFirstOrNull ().getCountryCode ();
  }

  public boolean isExpired ()
  {
    return m_aExpirationDT.isBefore (PDTFactory.getCurrentLocalDateTime ());
  }
}

/**
 * Cache for BusinessCards of participants queried from an SMP.
 *
 * @author Philip Helger
 */
public class BusinessCardCache
{
  private static final Logger LOGGER = LoggerFactory.getLogger (BusinessCardCache.class);

  @Nonnull
  private static BusinessCardWithFetchDate _fetchBC (@Nonnull final ISMLInfo aSMLInfo,
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
      return new BusinessCardWithFetchDate (aBC, PDTFactory.getCurrentLocalDateTime ().plus (aCachingDuration));
    }
    catch (final SMPDNSResolutionException ex)
    {
      throw new IllegalStateException (ex);
    }
  }

  private final MappedCache <IParticipantIdentifier, String, BusinessCardWithFetchDate> m_aCache;

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
                                   "BusinessCardCache",
                                   true);
  }

  @Nullable
  private BusinessCardWithFetchDate _getActive (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    BusinessCardWithFetchDate aBC = m_aCache.getFromCache (aParticipantID);
    if (aBC.isExpired ())
    {
      // Read from SMP again
      m_aCache.removeFromCache (aParticipantID);
      aBC = m_aCache.getFromCache (aParticipantID);
    }
    return aBC;
  }

  @Nullable
  public PDBusinessCard getBusinessCard (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    return _getActive (aParticipantID).getBusinessCard ();
  }

  @Nullable
  public String getCountryCode (@Nonnull final IParticipantIdentifier aParticipantID)
  {
    return _getActive (aParticipantID).getCountryCode ();
  }
}
