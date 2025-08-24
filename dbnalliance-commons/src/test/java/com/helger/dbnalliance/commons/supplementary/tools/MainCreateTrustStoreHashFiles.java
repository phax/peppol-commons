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
package com.helger.dbnalliance.commons.supplementary.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.dbnalliance.commons.security.DBNAllianceTrustStores;
import com.helger.io.file.SimpleFileIO;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.IReadableResource;
import com.helger.security.messagedigest.EMessageDigestAlgorithm;
import com.helger.security.messagedigest.MessageDigestValue;

import jakarta.annotation.Nonnull;

/**
 * Utility class to create hash codes of the global trust store to verify if it is valid or not.
 *
 * @author Philip Helger
 */
public final class MainCreateTrustStoreHashFiles
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateTrustStoreHashFiles.class);

  private static void _create (@Nonnull final String sTruststorePath) throws IOException
  {
    final IReadableResource aTrustStore = new ClassPathResource (sTruststorePath);

    final String sMD5 = MessageDigestValue.create (aTrustStore.getInputStream (), EMessageDigestAlgorithm.MD5)
                                          .getHexEncodedDigestString ();
    SimpleFileIO.writeFile (new File ("src/main/resources/" + sTruststorePath + ".md5"),
                            sMD5,
                            StandardCharsets.ISO_8859_1);

    final String sSHA1 = MessageDigestValue.create (aTrustStore.getInputStream (), EMessageDigestAlgorithm.SHA_256)
                                           .getHexEncodedDigestString ();
    SimpleFileIO.writeFile (new File ("src/main/resources/" + sTruststorePath + ".sha256"),
                            sSHA1,
                            StandardCharsets.ISO_8859_1);

    LOGGER.info ("Done creating hash values for " + sTruststorePath);
  }

  public static void main (final String [] args) throws IOException
  {
    _create (DBNAllianceTrustStores.Config2023.TRUSTSTORE_PILOT_CLASSPATH);
  }
}
