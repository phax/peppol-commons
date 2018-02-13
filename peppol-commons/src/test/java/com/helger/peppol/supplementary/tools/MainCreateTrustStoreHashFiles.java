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
package com.helger.peppol.supplementary.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.file.SimpleFileIO;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.peppol.utils.PeppolKeyStoreHelper;
import com.helger.security.messagedigest.EMessageDigestAlgorithm;
import com.helger.security.messagedigest.MessageDigestValue;

/**
 * Utility class to create hash codes of the global trust store to verify if it
 * is valid or not.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class MainCreateTrustStoreHashFiles
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (MainCreateTrustStoreHashFiles.class);

  private static void _create (@Nonnull final String sTruststorePath) throws IOException
  {
    final IReadableResource aTrustStore = new ClassPathResource (sTruststorePath);

    final String sMD5 = MessageDigestValue.create (aTrustStore.getInputStream (), EMessageDigestAlgorithm.MD5)
                                          .getHexEncodedDigestString ();
    SimpleFileIO.writeFile (new File ("src/main/resources/" + sTruststorePath + ".md5"),
                            sMD5,
                            StandardCharsets.ISO_8859_1);
    final String sSHA1 = MessageDigestValue.create (aTrustStore.getInputStream (), EMessageDigestAlgorithm.SHA_1)
                                           .getHexEncodedDigestString ();
    SimpleFileIO.writeFile (new File ("src/main/resources/" + sTruststorePath + ".sha1"),
                            sSHA1,
                            StandardCharsets.ISO_8859_1);

    s_aLogger.info ("Done creating hash values for " + sTruststorePath);
  }

  public static void main (final String [] args) throws IOException
  {
    _create (PeppolKeyStoreHelper.TRUSTSTORE_COMPLETE_CLASSPATH);
    _create (PeppolKeyStoreHelper.Config2010.TRUSTSTORE_PRODUCTION_CLASSPATH);
    _create (PeppolKeyStoreHelper.Config2010.TRUSTSTORE_PILOT_CLASSPATH);
    _create (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PRODUCTION_CLASSPATH);
    _create (PeppolKeyStoreHelper.Config2018.TRUSTSTORE_PILOT_CLASSPATH);
  }
}
