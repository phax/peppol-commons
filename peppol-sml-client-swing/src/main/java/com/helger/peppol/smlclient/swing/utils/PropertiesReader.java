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
package com.helger.peppol.smlclient.swing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nonnull;

import com.helger.commons.lang.NonBlockingProperties;
import com.helger.commons.state.ESuccess;

/**
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 */
final class PropertiesReader
{
  private static final String KEY_SERVICE_URL = "service_url";
  private static final String KEY_SMP_ID = "smp_id";
  private static final String KEY_KEYSTORE_PATH = "keystore_path";
  private static final String KEY_KEYSTORE_PASSWORD = "keystore_password";

  private NonBlockingProperties m_aProperties;

  PropertiesReader ()
  {}

  @Nonnull
  public ESuccess readProperties (final File aPath)
  {
    try
    {
      m_aProperties = new NonBlockingProperties ();
      if (aPath != null && aPath.exists ())
        m_aProperties.load (new FileInputStream (aPath));

      return ESuccess.SUCCESS;
    }
    catch (final IOException e)
    {
      e.printStackTrace ();
    }

    return ESuccess.FAILURE;
  }

  @Nonnull
  public ESuccess writeProperties (final File aPath)
  {
    if (m_aProperties != null)
    {
      try
      {
        m_aProperties.store (new FileOutputStream (aPath), "PEPPOL SML Client properties file\n");
        return ESuccess.SUCCESS;
      }
      catch (final IOException e)
      {
        e.printStackTrace ();
      }
    }
    return ESuccess.FAILURE;
  }

  public String getServiceURL ()
  {
    return m_aProperties.getProperty (KEY_SERVICE_URL);
  }

  public String getSMPID ()
  {
    return m_aProperties.getProperty (KEY_SMP_ID);
  }

  public String getKeyStorePath ()
  {
    return m_aProperties.getProperty (KEY_KEYSTORE_PATH);
  }

  public String getKeyStorePassword ()
  {
    return m_aProperties.getProperty (KEY_KEYSTORE_PASSWORD);
  }

  public void setServiceURL (final String sServiceURL)
  {
    m_aProperties.setProperty (KEY_SERVICE_URL, sServiceURL);
  }

  public void setSMPID (final String sSMPID)
  {
    m_aProperties.setProperty (KEY_SMP_ID, sSMPID);
  }

  public void setKeyStorePath (final String sKeyStorePath)
  {
    m_aProperties.setProperty (KEY_KEYSTORE_PATH, sKeyStorePath);
  }

  public void setKeyStorePassword (final String sKeyStorePassword)
  {
    m_aProperties.setProperty (KEY_KEYSTORE_PASSWORD, sKeyStorePassword);
  }
}
