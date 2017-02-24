/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
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
    _create (PeppolKeyStoreHelper.TRUSTSTORE_PRODUCTION_CLASSPATH);
    _create (PeppolKeyStoreHelper.TRUSTSTORE_PILOT_CLASSPATH);
    _create (PeppolKeyStoreHelper.TRUSTSTORE_COMPLETE_CLASSPATH);
  }
}
