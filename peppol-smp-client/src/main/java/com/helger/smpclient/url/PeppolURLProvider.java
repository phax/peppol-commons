/**
 * Copyright (C) 2015-2020 Philip Helger
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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.security.messagedigest.EMessageDigestAlgorithm;
import com.helger.security.messagedigest.MessageDigestValue;

/**
 * The default implementation of {@link IPeppolURLProvider} suitable for the
 * Peppol network.<br>
 * Layout:
 * <code>"B-"+hexstring(md5(lowercase(ID-VALUE)))+"."+ID-SCHEME+"."+SML-ZONE-NAME</code>
 *
 * @author Philip Helger
 */
@Immutable
public class PeppolURLProvider implements IPeppolURLProvider
{
  public static final PeppolURLProvider MUTABLE_INSTANCE = new PeppolURLProvider ();
  public static final IPeppolURLProvider INSTANCE = MUTABLE_INSTANCE;
  public static final Charset URL_CHARSET = StandardCharsets.UTF_8;
  public static final Locale URL_LOCALE = Locale.US;

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolURLProvider.class);

  /**
   * Default constructor.
   */
  public PeppolURLProvider ()
  {}

  /**
   * Get the MD5-hash-string-representation of the passed value using the
   * {@link #URL_CHARSET} encoding. Each hash byte is represented as 2
   * characters in the range [0-9a-f]. Note: the hash value creation is done
   * case sensitive! The caller needs to ensure that the value to hash is lower
   * case!
   *
   * @param sValueToHash
   *        The value to be hashed. May not be <code>null</code>.
   * @return The non-<code>null</code> String containing the hash value.
   */
  @Nonnull
  public static String getHashValueStringRepresentation (@Nonnull final String sValueToHash)
  {
    // Create the MD5 hash
    // Convert to hex-encoded string
    return MessageDigestValue.create (sValueToHash.getBytes (URL_CHARSET), EMessageDigestAlgorithm.MD5)
                             .getHexEncodedDigestString ();
  }

  @Nonnull
  public String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nullable final String sSMLZoneName) throws PeppolDNSResolutionException
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");

    // Ensure the DNS zone name ends with a dot!
    if (StringHelper.hasText (sSMLZoneName) && !StringHelper.endsWith (sSMLZoneName, '.'))
      throw new PeppolDNSResolutionException ("if an SML zone name is specified, it must end with a dot (.). Value is: " +
                                              sSMLZoneName);

    // Check identifier scheme (must be lowercase for the URL later on!)
    final String sIdentifierScheme = StringHelper.getNotNull (aParticipantIdentifier.getScheme ())
                                                 .toLowerCase (URL_LOCALE);

    // Was previously an error, but to be more flexible just emit a warning
    if (!PeppolIdentifierFactory.INSTANCE.isParticipantIdentifierSchemeValid (sIdentifierScheme))
      if (LOGGER.isWarnEnabled ())
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
      ret.append (PeppolIdentifierHelper.DNS_HASHED_IDENTIFIER_PREFIX)
         .append (getHashValueStringRepresentation (sValue.toLowerCase (URL_LOCALE)))
         .append ('.');
    }

    // append the identifier scheme (if present)
    if (!sIdentifierScheme.isEmpty ())
      ret.append (sIdentifierScheme).append ('.');

    // append the SML DNS zone name (if available)
    if (StringHelper.hasText (sSMLZoneName))
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
}
