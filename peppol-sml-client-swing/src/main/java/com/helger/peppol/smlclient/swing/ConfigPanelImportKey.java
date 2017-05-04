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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.helger.peppol.smlclient.swing.utils.FileFilterJKS;

import net.miginfocom.swing.MigLayout;

/**
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 */
final class ConfigPanelImportKey extends JPanel {
  private final JTextField m_aTFPath;
  private final JTextField m_aTFPassword;
  private final JButton m_aBtnBrowse;

  public ConfigPanelImportKey () {
    setLayout (new MigLayout ("fill", "[label][left]", ""));
    // setPreferredSize (mainFrame.getContentPanelDimension ());
    setBorder (BorderFactory.createTitledBorder ("Import Key"));

    final JLabel lPath = new JLabel ("Path to key store: ");
    lPath.setAlignmentX (RIGHT_ALIGNMENT);
    m_aTFPath = new JTextField (30);
    final JLabel lPassword = new JLabel ("Key store password: ");
    m_aTFPassword = new JPasswordField (15);
    m_aBtnBrowse = new JButton ("Browse");
    m_aBtnBrowse.addActionListener (new ActionListener () {
      public void actionPerformed (final ActionEvent e) {
        final JFileChooser aFileChooser = new JFileChooser ();
        aFileChooser.setAcceptAllFileFilterUsed (false);
        aFileChooser.setFileFilter (new FileFilterJKS ());
        aFileChooser.showOpenDialog (null);
        final File aSelectedFile = aFileChooser.getSelectedFile ();
        if (aSelectedFile != null)
          m_aTFPath.setText (aSelectedFile.getAbsolutePath ());
      }
    });

    add (lPath);
    add (m_aTFPath, "width 100%");
    add (m_aBtnBrowse, "right,wrap");
    add (lPassword);
    add (m_aTFPassword, "span 2,width 100%,wrap");

    initData ();
  }

  public void initData () {
    m_aTFPath.setText (AppProperties.getInstance ().getKeyStorePath ());
    m_aTFPassword.setText (AppProperties.getInstance ().getKeyStorePassword ());
  }

  public void saveData () {
    MainFrame.setKeyStore (m_aTFPath.getText (), m_aTFPassword.getText ());
  }
}
