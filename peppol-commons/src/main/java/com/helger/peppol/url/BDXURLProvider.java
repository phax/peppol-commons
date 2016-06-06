package com.helger.peppol.url;

import java.nio.charset.Charset;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.charset.CCharset;
import com.helger.commons.charset.CharsetManager;
import com.helger.commons.messagedigest.EMessageDigestAlgorithm;
import com.helger.commons.messagedigest.MessageDigestValue;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.participant.IPeppolParticipantIdentifier;

public class BDXURLProvider implements IPeppolURLProvider
{
  public static final Charset URL_CHARSET = CCharset.CHARSET_UTF_8_OBJ;
  public static final Locale URL_LOCALE = Locale.US;

  private static final Logger s_aLogger = LoggerFactory.getLogger (BDXURLProvider.class);

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
    return MessageDigestValue.create (CharsetManager.getAsBytes (sValueToHash, URL_CHARSET),
                                      EMessageDigestAlgorithm.MD5)
                             .getHexEncodedDigestString ();
  }

  @Nonnull
  public String getDNSNameOfParticipant (@Nonnull final IParticipantIdentifier aParticipantIdentifier,
                                         @Nullable final String sSMLZoneName)
  {
    ValueEnforcer.notNull (aParticipantIdentifier, "ParticipantIdentifier");
    ValueEnforcer.notEmpty (aParticipantIdentifier.getScheme (), "ParticipantIdentifier scheme");
    ValueEnforcer.notEmpty (aParticipantIdentifier.getValue (), "ParticipantIdentifier value");

    // Ensure the DNS zone name ends with a dot!
    if (StringHelper.hasText (sSMLZoneName) && !StringHelper.endsWith (sSMLZoneName, '.'))
      throw new IllegalArgumentException ("if an SML zone name is specified, it must end with a dot (.). Value is: " +
                                          sSMLZoneName);

    // Check identifier scheme (must be lowercase for the URL later on!)
    final String sIdentifierScheme = aParticipantIdentifier.getScheme ().toLowerCase (URL_LOCALE);

    // Was previously an error, but to be more flexible just emit a warning
    if (!IPeppolParticipantIdentifier.isValidScheme (sIdentifierScheme))
      s_aLogger.warn ("Invalid PEPPOL participant identifier scheme '" + sIdentifierScheme + "' used");

    // Get the identifier value
    final String sValue = aParticipantIdentifier.getValue ();
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

    // append the identifier scheme
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
