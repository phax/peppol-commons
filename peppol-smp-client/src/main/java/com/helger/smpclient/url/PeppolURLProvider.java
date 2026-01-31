/*
 * Copyright (C) 2015-2026 Philip Helger
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

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.security.messagedigest.EMessageDigestAlgorithm;
import com.helger.security.messagedigest.MessageDigestValue;
import com.helger.smpclient.url.SMPDNSResolutionException.EErrorCode;

/**
 * The implementation of {@link IPeppolURLProvider} suitable for the Peppol Network to resolve CNAME
 * records.<br>
 * Layout: <code>"B-"+hexstring(md5(lowercase(ID-VALUE)))+"."+ID-SCHEME+"."+SML-ZONE-NAME</code><br>
 * This class was replaced by {@link PeppolNaptrURLProvider} as per Policy for use of Identifiers
 * v4.4.0.
 *
 * @author Philip Helger
 */
@Immutable
@Deprecated (since = "10.3.1", forRemoval = true)
public class PeppolURLProvider implements IPeppolURLProvider
{
  /** The writable API of the default instance */
  @Deprecated
  public static final PeppolURLProvider MUTABLE_INSTANCE = new PeppolURLProvider ();
  /** The default instance that should be used */
  @Deprecated
  public static final IPeppolURLProvider INSTANCE = MUTABLE_INSTANCE;

  @Deprecated
  public static final Charset URL_CHARSET = StandardCharsets.UTF_8;
  @Deprecated
  public static final Locale URL_LOCALE = Locale.US;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolURLProvider.class);

  /**
   * Default constructor.
   */
  @Deprecated
  public PeppolURLProvider ()
  {}

  /**
   * Get the MD5-hash-string-representation of the passed value using the {@link #URL_CHARSET}
   * encoding. Each hash byte is represented as 2 characters in the range [0-9a-f]. Note: the hash
   * value creation is done case sensitive! The caller needs to ensure that the value to hash is
   * lower case!
   *
   * @param sValueToHash
   *        The value to be hashed. May not be <code>null</code>.
   * @return The non-<code>null</code> String containing the hash value.
   */
  @Deprecated
  @NonNull
  public static String getHashValueStringRepresentation (@NonNull final String sValueToHash)
  {
    // Create the MD5 hash
    // Convert to hex-encoded string
    return MessageDigestValue.create (sValueToHash.getBytes (URL_CHARSET), EMessageDigestAlgorithm.MD5)
                             .getHexEncodedDigestString ();
  }

  @Deprecated
  @NonNull
  public String getDNSNameOfParticipant (@NonNull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    // Ensure the DNS zone name ends with a dot!
    if (StringHelper.isNotEmpty (sSMLZoneName) && !StringHelper.endsWith (sSMLZoneName, '.'))
      throw new SMPDNSResolutionException (EErrorCode.DOMAIN_NAME_SYNTAX_ERROR,
                                           "If an SML zone name is specified, it must end with a dot (.). Value is: " +
                                                                                sSMLZoneName);

    // Check identifier scheme (must be lowercase for the URL later on!)
    final String sIdentifierScheme = StringHelper.getNotNull (aParticipantIdentifier.getScheme ())
                                                 .toLowerCase (URL_LOCALE);

    // Was previously an error, but to be more flexible just emit a warning
    if (!PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sIdentifierScheme))
      LOGGER.warn ("Invalid Peppol participant identifier scheme '" + sIdentifierScheme + "' used");

    // Get the identifier value
    final String sValue = StringHelper.getNotNull (aParticipantIdentifier.getValue ());
    final StringBuilder ret = new StringBuilder ();
    if ("*".equals (sValue))
    {
      // Wild card registration
      ret.append ("*.");
    }
    else
    {
      // Important: create hash from lowercase string!
      // Here the "B-0011223344..." string is assembled!
      ret.append ("B-").append (getHashValueStringRepresentation (sValue.toLowerCase (URL_LOCALE))).append ('.');
    }

    // append the identifier scheme (if present)
    if (!sIdentifierScheme.isEmpty ())
      ret.append (sIdentifierScheme).append ('.');

    // append the SML DNS zone name (if available)
    if (StringHelper.isNotEmpty (sSMLZoneName))
    {
      // If it is present, it always ends with a dot
      ret.append (sSMLZoneName);
    }

    // in some cases it gives a problem later when trying to retrieve the
    // participant's metadata ex:
    // http://B-51538b9890f1999ca08302c65f544719.iso6523-actorid-upis.sml.peppolcentral.org./iso6523-actorid-upis%3A%3A9917%3A550403315099
    // That's why we cut of the dot here
    ret.deleteCharAt (ret.length () - 1);

    // We're fine and done
    return ret.toString ();
  }

  @Deprecated
  @NonNull
  public URI getSMPURIOfParticipant (@NonNull final IParticipantIdentifier aParticipantIdentifier,
                                     @Nullable final String sSMLZoneName) throws SMPDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    // MUST always be http, port 80 and the root path
    final String sURIString = "http://" + getDNSNameOfParticipant (aParticipantIdentifier, sSMLZoneName);

    try
    {
      return new URI (sURIString);
    }
    catch (final URISyntaxException ex)
    {
      throw new SMPDNSResolutionException (EErrorCode.RESOLVED_URI_SYNTAX_ERROR,
                                           "Error building SMP URI from string '" + sURIString + "'",
                                           ex);
    }
  }
}
