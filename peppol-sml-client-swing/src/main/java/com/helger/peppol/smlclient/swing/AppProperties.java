/**
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
package com.helger.peppol.smlclient.swing;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.state.ESuccess;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.sml.ESML;
import com.helger.peppol.sml.ISMLInfo;
import com.helger.peppol.smlclient.swing.utils.WrappedSMLInfo;

final class AppProperties
{
  private static final class SingletonHolder
  {
    static final AppProperties s_aInstance = new AppProperties ();
  }

  private static final String DEFAULT_PROPERTIES_PATH = ".";
  private static final String DEFAULT_PROPERTIES_NAME = "config.properties";
  private static final boolean DEFAULT_PROPERTIES_ENABLED = true;

  private ISMLInfo m_aSMLInfo;
  private String m_sSMPID;
  private String m_sKeyStorePath;
  private String m_sKeyStorePassword;
  private File m_aPropertiesFile = new File (DEFAULT_PROPERTIES_PATH, DEFAULT_PROPERTIES_NAME);
  private final PropertiesReader m_aPropsReader = new PropertiesReader ();
  private boolean m_bPropertiesEnabled = DEFAULT_PROPERTIES_ENABLED;

  private AppProperties ()
  {}

  @Nonnull
  public static AppProperties getInstance ()
  {
    return SingletonHolder.s_aInstance;
  }

  @Nonnull
  public ESuccess readProperties ()
  {
    if (m_aPropsReader.readProperties (m_aPropertiesFile).isFailure ())
      return ESuccess.FAILURE;
    m_aSMLInfo = null;
    final String sHostName = m_aPropsReader.getServiceURL ();
    for (final ESML eSML : ESML.values ())
      if (eSML.getManagementServiceURL ().equals (sHostName))
      {
        m_aSMLInfo = new WrappedSMLInfo (eSML);
        break;
      }
    m_sSMPID = m_aPropsReader.getSMPID ();
    m_sKeyStorePath = m_aPropsReader.getKeyStorePath ();
    m_sKeyStorePassword = m_aPropsReader.getKeyStorePassword ();
    return ESuccess.SUCCESS;
  }

  @Nonnull
  public ESuccess writeProperties ()
  {
    if (m_aSMLInfo == null)
      return ESuccess.FAILURE;
    m_aPropsReader.setServiceURL (m_aSMLInfo.getManagementServiceURL ());
    m_aPropsReader.setSMPID (m_sSMPID);
    m_aPropsReader.setKeyStorePath (m_sKeyStorePath);
    m_aPropsReader.setKeyStorePassword (m_sKeyStorePassword);
    return m_aPropsReader.writeProperties (m_aPropertiesFile);
  }

  public void setSMLInfo (final ISMLInfo aSMLInfo)
  {
    m_aSMLInfo = aSMLInfo;
  }

  @Nullable
  public ISMLInfo getSMLInfo ()
  {
    return m_aSMLInfo;
  }

  public void setSMPID (final String sSMPID)
  {
    m_sSMPID = sSMPID;
  }

  public String getSMPID ()
  {
    return m_sSMPID;
  }

  public void setKeyStorePath (final String sKeyStorePath)
  {
    m_sKeyStorePath = sKeyStorePath;
  }

  public void setKeyStorePassword (final String sKeyStorePassword)
  {
    m_sKeyStorePassword = sKeyStorePassword;
  }

  public String getKeyStorePath ()
  {
    return m_sKeyStorePath;
  }

  public String getKeyStorePassword ()
  {
    return m_sKeyStorePassword;
  }

  @Nonnull
  public File getPropertiesFilename ()
  {
    return m_aPropertiesFile;
  }

  public void setPropertiesFilename (final String sPropertiesPath)
  {
    m_aPropertiesFile = new File (sPropertiesPath);
  }

  public boolean isPropertiesEnabled ()
  {
    return m_bPropertiesEnabled;
  }

  public void setPropertiesEnabled (final boolean bPropertiesEnabled)
  {
    m_bPropertiesEnabled = bPropertiesEnabled;
  }

  public void clear ()
  {
    setSMLInfo (null);
    setSMPID ("");
    setKeyStorePath (null);
    setKeyStorePassword (null);
  }

  public boolean areAllSet ()
  {
    return getSMLInfo () != null &&
           StringHelper.hasText (getSMPID ()) &&
           StringHelper.hasText (getKeyStorePath ()) &&
           StringHelper.hasText (getKeyStorePassword ());
  }
}
