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
package com.helger.peppol.smpclient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemProperties;
import com.helger.peppol.utils.ConfigFile;
import com.helger.peppol.utils.KeyStoreHelper;

/**
 * This class manages the configuration properties of the SMP client. The order
 * of the properties file resolving is as follows:
 * <ol>
 * <li>Check for the value of the system property
 * <code>peppol.smp.client.properties.path</code></li>
 * <li>Check for the value of the system property
 * <code>smp.client.properties.path</code></li>
 * <li>The filename <code>private-smp-client.properties</code> in the root of
 * the classpath</li>
 * <li>The filename <code>smp-client.properties</code> in the root of the
 * classpath</li>
 * </ol>
 *
 * @author Philip Helger
 */
@Immutable
public final class SMPClientConfiguration
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (SMPClientConfiguration.class);
  private static final ConfigFile s_aConfigFile;

  static
  {
    final ICommonsList <String> aFilePaths = new CommonsArrayList <> ();
    // Check if the system property is present
    String sPropertyPath = SystemProperties.getPropertyValue ("peppol.smp.client.properties.path");
    if (StringHelper.hasText (sPropertyPath))
      aFilePaths.add (sPropertyPath);
    sPropertyPath = SystemProperties.getPropertyValue ("smp.client.properties.path");
    if (StringHelper.hasText (sPropertyPath))
      aFilePaths.add (sPropertyPath);

    // Use the default paths
    aFilePaths.add ("private-smp-client.properties");
    aFilePaths.add ("smp-client.properties");

    s_aConfigFile = new ConfigFile (ArrayHelper.newArray (aFilePaths, String.class));
    if (s_aConfigFile.isRead ())
      s_aLogger.info ("Read SMP client properties from " + s_aConfigFile.getReadResource ().getPath ());
    else
      s_aLogger.warn ("Failed to read SMP client properties from any of the paths: " + aFilePaths);
  }

  private SMPClientConfiguration ()
  {}

  /**
   * @return The global config file for the SMP client.
   */
  @Nonnull
  public static ConfigFile getConfigFile ()
  {
    return s_aConfigFile;
  }

  /**
   * @return The truststore location as specified in the configuration file by
   *         the key <code>truststore.location</code>. If none is present
   *         {@link KeyStoreHelper#TRUSTSTORE_COMPLETE_CLASSPATH} is returned as
   *         a default.
   */
  @Nonnull
  public static String getTruststoreLocation ()
  {
    return s_aConfigFile.getString ("truststore.location", KeyStoreHelper.TRUSTSTORE_COMPLETE_CLASSPATH);
  }

  /**
   * @return The truststore password as specified in the configuration file by
   *         the key <code>truststore.password</code>. If none is present
   *         {@link KeyStoreHelper#TRUSTSTORE_PASSWORD} is returned as a
   *         default.
   */
  @Nonnull
  public static String getTruststorePassword ()
  {
    return s_aConfigFile.getString ("truststore.password", KeyStoreHelper.TRUSTSTORE_PASSWORD);
  }

  /**
   * @return The HttpProxy object to be used by SMP clients based on the Java
   *         System properties "http.proxyHost" and "http.proxyPort". Note:
   *         https is not needed, because SMPs must run on http only.
   */
  @Nullable
  public static HttpHost getHttpProxy ()
  {
    final String sProxyHost = s_aConfigFile.getString ("http.proxyHost");
    final int nProxyPort = s_aConfigFile.getInt ("http.proxyPort", 0);
    if (sProxyHost != null && nProxyPort > 0)
      return new HttpHost (sProxyHost, nProxyPort);

    return null;
  }
}
