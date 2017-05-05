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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.helger.commons.random.VerySecureRandom;
import com.helger.commons.ws.TrustManagerTrustAll;
import com.helger.peppol.utils.PeppolKeyStoreHelper;
import com.helger.security.keystore.KeyStoreHelper;
import com.helger.security.keystore.LoadedKeyStore;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 */
final class MainFrame extends JFrame
{
  private static final int FRAME_WIDTH = 530;
  private static final int FRAME_HEIGHT = 360;

  private final JPanel m_aContentPanel;
  private final MainStatusBar m_aStatusBar;

  public MainFrame ()
  {
    setTitle ("PEPPOL SML Client v2");

    final Dimension aScreenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    setLocation ((aScreenSize.width - FRAME_WIDTH) / 2, (aScreenSize.height - FRAME_HEIGHT) / 2);
    setPreferredSize (new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
    setMinimumSize (new Dimension (FRAME_WIDTH + 3, FRAME_HEIGHT + 3));
    setDefaultCloseOperation (EXIT_ON_CLOSE);
    setLayout (new MigLayout (new LC ().fillX ().wrap (),
                              new AC ().fill ().align ("left"),
                              new AC ().gap ().fill ().gap ().grow ().fill ().gap ()));

    m_aStatusBar = new MainStatusBar ();

    m_aContentPanel = new JPanel (new MigLayout (new LC ().fill ().insets ("0")));
    setConfigContent ();

    add (m_aContentPanel, "width 100%,wrap");
    add (m_aStatusBar, new CC ().dockSouth ().height ("23!"));

    pack ();
  }

  public void setConfigContent ()
  {
    m_aContentPanel.removeAll ();
    m_aContentPanel.add (new MainContentPanelConfig (this), "width 100%");
    m_aContentPanel.updateUI ();
  }

  public void setActionContent ()
  {
    m_aContentPanel.removeAll ();
    m_aContentPanel.add (new MainContentPanelAction (this), "width 100%");
    m_aContentPanel.updateUI ();
  }

  public static void setKeyStore (final String sKeyStorePath, final String sKeystorePassword)
  {
    AppProperties.getInstance ().setKeyStorePath (sKeyStorePath);
    AppProperties.getInstance ().setKeyStorePassword (sKeystorePassword);

    try
    {
      // Main key storage
      final LoadedKeyStore aKeyStore = KeyStoreHelper.loadKeyStore (sKeyStorePath, sKeystorePassword);
      if (aKeyStore.isFailure ())
        throw new IllegalStateException (PeppolKeyStoreHelper.getLoadError (aKeyStore));

      // Key manager
      final KeyManagerFactory aKeyManagerFactory = KeyManagerFactory.getInstance ("SunX509");
      aKeyManagerFactory.init (aKeyStore.getKeyStore (), sKeystorePassword.toCharArray ());

      // Assign key manager and empty trust manager to SSL context
      final SSLContext aSSLCtx = SSLContext.getInstance ("TLS");
      aSSLCtx.init (aKeyManagerFactory.getKeyManagers (),
                    new TrustManager [] { new TrustManagerTrustAll () },
                    VerySecureRandom.getInstance ());
      HttpsURLConnection.setDefaultSSLSocketFactory (aSSLCtx.getSocketFactory ());
    }
    catch (final Exception ex)
    {
      MainStatusBar.setStatusError (ex.getMessage ());
    }
  }
}
