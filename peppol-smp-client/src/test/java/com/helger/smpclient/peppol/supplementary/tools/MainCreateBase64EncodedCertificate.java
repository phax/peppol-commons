/**
 * Copyright (C) 2015-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.smpclient.peppol.supplementary.tools;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.base64.Base64;
import com.helger.commons.io.file.SimpleFileIO;

public class MainCreateBase64EncodedCertificate
{
  private static final Logger LOGGER = LoggerFactory.getLogger (MainCreateBase64EncodedCertificate.class);

  public static void main (final String [] args)
  {
    final File aFile = new File ("src/test/resources/SMP_PEPPOL_SML_PEPPOL_SERVICE_METADATA_PUBLISHER_TEST_CA.cer");
    LOGGER.info (Base64.encodeBytes (SimpleFileIO.getAllFileBytes (aFile)));
  }
}
