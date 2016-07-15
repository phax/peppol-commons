/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
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
package com.helger.peppol.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.text.util.TextHelper;
import com.helger.security.keystore.EKeyStoreLoadError;
import com.helger.security.keystore.LoadedKey;
import com.helger.security.keystore.LoadedKeyStore;

/**
 * Helper methods to access Java key stores of type JKS (Java KeyStore).
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class PeppolKeyStoreHelper
{
  /**
   * The classpath entry referencing the global truststore with all OpenPEPPOL
   * production entries
   */
  public static final String TRUSTSTORE_PRODUCTION_CLASSPATH = "truststore/global-truststore.jks";

  /** The truststore alias for the OpenPEPPOL production root certificate */
  public static final String TRUSTSTORE_PRODUCTION_ALIAS_ROOT = "peppol root ca";

  /** The truststore alias for the OpenPEPPOL production AP certificate */
  public static final String TRUSTSTORE_PRODUCTION_ALIAS_AP = "peppol access point ca (peppol root ca)";

  /** The truststore alias for the OpenPEPPOL production SMP certificate */
  public static final String TRUSTSTORE_PRODUCTION_ALIAS_SMP = "peppol service metadata publisher ca (peppol root ca)";

  /**
   * The classpath entry referencing the global truststore with all OpenPEPPOL
   * pilot entries
   */
  public static final String TRUSTSTORE_PILOT_CLASSPATH = "truststore/pilot-truststore.jks";

  /** The truststore alias for the OpenPEPPOL pilot root certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_ROOT = "peppol root test ca";

  /** The truststore alias for the OpenPEPPOL pilot AP certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_AP = "peppol access point test ca (peppol root test ca)";

  /** The truststore alias for the OpenPEPPOL pilot SMP certificate */
  public static final String TRUSTSTORE_PILOT_ALIAS_SMP = "peppol service metadata publisher test ca (peppol root test ca)";

  /**
   * The classpath entry referencing the complete truststore with all OpenPEPPOL
   * production AND pilot entries
   */
  public static final String TRUSTSTORE_COMPLETE_CLASSPATH = "truststore/complete-truststore.jks";

  /** The password used to access the truststores */
  public static final String TRUSTSTORE_PASSWORD = "peppol";

  @PresentForCodeCoverage
  private static final PeppolKeyStoreHelper s_aInstance = new PeppolKeyStoreHelper ();

  private PeppolKeyStoreHelper ()
  {}

  @Nullable
  private static String _getLoadError (@Nonnull final EKeyStoreLoadError eError, @Nullable final String [] aParams)
  {
    switch (eError)
    {
      case KEYSTORE_NO_PATH:
        return "No keystore path provided.";
      case KEYSTORE_LOAD_ERROR_NON_EXISTING:
        return TextHelper.getFormattedText ("Failed to locate keystore path ''{0}''. Technical details: {1}",
                                            (Object []) aParams);
      case KEYSTORE_INVALID_PASSWORD:
        return TextHelper.getFormattedText ("Invalid password provided for keystore ''{0}''. Technical details: {1}",
                                            (Object []) aParams);
      case KEYSTORE_LOAD_ERROR_FORMAT_ERROR:
        return TextHelper.getFormattedText ("Failed to load the keystore ''{0}'' - invalid format. Technical details: {1}",
                                            (Object []) aParams);
      case KEY_NO_ALIAS:
        return "No alias for keystore entry provided.";
      case KEY_NO_PASSWORD:
        return "No alias for keystore entry provided.";
      case KEY_INVALID_ALIAS:
        return TextHelper.getFormattedText ("Failed to find alias ''{0}'' in keystore ''{1}''.", (Object []) aParams);
      case KEY_INVALID_TYPE:
        return TextHelper.getFormattedText ("Alias ''{0}'' in keystore ''{1}'' has an invalid type. The effective technical type is {2}.",
                                            (Object []) aParams);
      case KEY_INVALID_PASSWORD:
        return TextHelper.getFormattedText ("Invalid password provided for alias ''{0}'' in keystore ''{1}''. Technical details: {2}.",
                                            (Object []) aParams);
      case KEY_LOAD_ERROR:
        return TextHelper.getFormattedText ("Generic error loading alias ''{0}'' in keystore ''{1}''. Technical details: {2}.",
                                            (Object []) aParams);
      default:
        return "Unknown error: " + eError;
    }
  }

  @Nullable
  public static String getLoadError (@Nonnull final LoadedKeyStore aLKS)
  {
    if (aLKS == null || aLKS.isSuccess ())
      return null;
    return _getLoadError (aLKS.getError (), aLKS.getErrorParams ());
  }

  @Nullable
  public static String getLoadError (@Nonnull final LoadedKey <?> aLK)
  {
    if (aLK == null || aLK.isSuccess ())
      return null;
    return _getLoadError (aLK.getError (), aLK.getErrorParams ());
  }
}
