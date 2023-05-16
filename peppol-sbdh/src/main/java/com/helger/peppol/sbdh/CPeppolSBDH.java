/*
 * Copyright (C) 2014-2023 Philip Helger
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
package com.helger.peppol.sbdh;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.io.resource.ClassPathResource;

/**
 * Constants for the usage of SBDH headers in Peppol.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPeppolSBDH
{
  /** The expected SBDH header version */
  public static final String HEADER_VERSION = "1.0";

  /** Constant for the Scope of the document type identifier */
  public static final String SCOPE_DOCUMENT_TYPE_ID = "DOCUMENTID";

  /** Constant for the Scope of the process identifier */
  public static final String SCOPE_PROCESS_ID = "PROCESSID";

  /** Constant for the Scope of the C1 country code */
  public static final String SCOPE_COUNTRY_C1 = "COUNTRY_C1";

  /** UBL 2.0 constant */
  public static final String TYPE_VERSION_20 = "2.0";

  /** UBL 2.1 constant */
  public static final String TYPE_VERSION_21 = "2.1";

  /** UBL 2.2 constant */
  public static final String TYPE_VERSION_22 = "2.2";

  /** UBL 2.3 constant */
  public static final String TYPE_VERSION_23 = "2.3";

  /** UBL 2.4 constant */
  public static final String TYPE_VERSION_24 = "2.4";

  /** CII D16B constant */
  public static final String TYPE_VERSION_CII_D16B = "D16B";

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return CPeppolSBDH.class.getClassLoader ();
  }

  /** XML Schema resources for special payloads */
  @CodingStyleguideUnaware
  public static final List <ClassPathResource> PEPPOL_SPECIAL_PAYLOADS_XSDS = new CommonsArrayList <> (new ClassPathResource ("/external/schemas/PEPPOL-EDN-Business-Message-Envelope-1.2-2019-02-01.xsd",
                                                                                                                              _getCL ())).getAsUnmodifiable ();

  @PresentForCodeCoverage
  private static final CPeppolSBDH INSTANCE = new CPeppolSBDH ();

  private CPeppolSBDH ()
  {}

  // TODO Change to true (and make public), once it is mandatory
  private static final AtomicBoolean IS_COUNTRY_C1_MANDATORY = new AtomicBoolean (false);
  private static final Logger LOGGER = LoggerFactory.getLogger (CPeppolSBDH.class);

  /**
   * NOTE: this is a temporary API until this field is mandatory everywhere in
   * Peppol. Afterwards this method will be replaced with a constant.
   *
   * @return <code>true</code> if the COUNTRY_C1 field is mandatory or not.
   * @since 9.0.5
   */
  public static boolean isCountryC1Mandatory ()
  {
    return IS_COUNTRY_C1_MANDATORY.get ();
  }

  /**
   * Use this method to make COUNTRY_C1 field mandatory. <br>
   * NOTE: this is a temporary API until this field is mandatory everywhere in
   * Peppol. Afterwards this method will be replaced with a constant.
   *
   * @param bIsMandatory
   *        <code>true</code> to make it mandatory.
   * @since 9.0.5
   */
  public static void setCountryC1Mandatory (final boolean bIsMandatory)
  {
    if (isCountryC1Mandatory () != bIsMandatory)
    {
      // Something changed
      LOGGER.warn (bIsMandatory ? "The COUNTRY_C1 field is now a mandatory field in the Peppol SBDH"
                                : "The COUNTRY_C1 field is NO LONGER a mandatory field in the Peppol SBDH");
    }
    IS_COUNTRY_C1_MANDATORY.set (bIsMandatory);
  }
}
