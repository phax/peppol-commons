package com.helger.peppol.url;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.sml.ISMLInfo;

/**
 * Base interface for a customizable URL provider so that different URL encoding
 * schemes can be used.
 *
 * @author Philip Helger
 */
public interface IPeppolURLProvider extends Serializable
{
  /**
   * Get DNS record from ParticipantIdentifier.<br>
   * Example PEPPOL PI <code>iso6523-actorid-upis::0010:1234</code> using BDX
   * scheme would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code>
   * . This method ensures that the hash value is created from the UTF-8 lower
   * case value of the identifier. The result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param aSMLInfo
   *        The SML information object to be used. May not be <code>null</code>.
   * @return DNS record
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @Nonnull
  default String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                          @Nonnull final ISMLInfo aSMLInfo)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");
    return getDNSNameOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  /**
   * Get DNS record from ParticipantIdentifier.<br>
   * Example PEPPOL PI <code>iso6523-actorid-upis::0010:1234</code> using BDX
   * scheme would result in
   * <code>B-&lt;hash over PI-Value&gt;.&lt;PI-Scheme&gt;.&lt;sml-zone-name&gt;</code>
   * . This method ensures that the hash value is created from the UTF-8 lower
   * case value of the identifier. The result string never ends with a dot!
   *
   * @param aParticipantIdentifier
   *        Participant identifier. May not be <code>null</code>.
   * @param sSMLZoneName
   *        e.g. "sml.peppolcentral.org.". May be empty. If it is not empty, it
   *        must end with a dot!
   * @return DNS record. It does not contain any prefix like http:// or any path
   *         suffix. It is the plain DNS host name. Since version 1.1.4 this
   *         method returns the DNS name without the trailing dot!
   * @throws IllegalArgumentException
   *         In case one argument is invalid
   */
  @Nonnull
  String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                  @Nullable final String sSMLZoneName);

  @Nonnull
  default URI getSMPURIOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nonnull final ISMLInfo aSMLInfo)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");
    return getSMPURIOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  @Nonnull
  default URI getSMPURIOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    final String sURIString = "http://" + getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName);

    try
    {
      return new URI (sURIString);
    }
    catch (final URISyntaxException ex)
    {
      throw new IllegalArgumentException ("Error building SMP URI from string '" + sURIString + "'", ex);
    }
  }

  @Nonnull
  default URL getSMPURLOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nonnull final ISMLInfo aSMLInfo)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notNull (aSMLInfo, "SMLInfo");

    return getSMPURLOfParticipant (aParticipantIdentifier, aSMLInfo.getDNSZone ());
  }

  @Nonnull
  default URL getSMPURLOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                      @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    final URI aURI = getSMPURIOfParticipant (aParticipantIdentifier, sSMLZoneName);
    try
    {
      return aURI.toURL ();
    }
    catch (final MalformedURLException ex)
    {
      throw new IllegalArgumentException ("Error building SMP URL from URI: " + aURI, ex);
    }
  }
}
