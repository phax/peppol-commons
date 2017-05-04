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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Content Panel
 * 
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 */
final class MainContentPanelConfig extends JPanel {
  private final ConfigPanelConfig m_aConfigPanel = new ConfigPanelConfig ();
  private final ConfigPanelImportKey m_aImportKeyPanel = new ConfigPanelImportKey ();
  private final ConfigPanelProperties m_aPropertiesPanel = new ConfigPanelProperties ();

  public MainContentPanelConfig (final MainFrame aMainFrame) {
    setLayout (new MigLayout ("fill,insets 0"));
    add (m_aConfigPanel, "width 100%, wrap");
    add (m_aImportKeyPanel, "width 100%,wrap");
    add (m_aPropertiesPanel, "width 100%,wrap");

    // Next button
    final JButton aBtnNext = new JButton ("Next >>");
    aBtnNext.addActionListener (new ActionListener () {
      public void actionPerformed (final ActionEvent e) {
        m_aConfigPanel.saveData ();
        m_aImportKeyPanel.saveData ();
        m_aPropertiesPanel.saveData ();
        MainStatusBar.setStatus ("Setting saved");

        if (AppProperties.getInstance ().areAllSet ()) {
          // Switch to action panel
          aMainFrame.setActionContent ();
        }
        else {
          // Not all fields are set
          JOptionPane.showMessageDialog (aMainFrame,
                                         "Not all fields are filled. Please ensure that SML, SMP-ID and keystore parameters are set!",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
          MainStatusBar.setStatusError ("Not all fields are filled.");
        }
      }
    });
    add (aBtnNext, "gapright 20,align right,wrap");

    m_aConfigPanel.initData ();
    m_aImportKeyPanel.initData ();
  }
}
