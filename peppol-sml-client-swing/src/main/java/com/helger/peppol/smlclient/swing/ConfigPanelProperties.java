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

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.helger.commons.io.file.FilenameHelper;
import com.helger.peppol.smlclient.swing.utils.FileFilterProperties;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * Content Panel
 * 
 * @author PEPPOL.AT, BRZ, Jakob Frohnwieser
 */
final class ConfigPanelProperties extends JPanel {
  private final Checkbox m_aCBEnable;
  private final JTextField m_aTFPropertiesPath;
  private final JButton m_aBtnBrowse;

  public ConfigPanelProperties () {
    setLayout (new MigLayout (new LC ().fill (), new AC ().size ("label").gap ().align ("left"), new AC ()));
    setBorder (BorderFactory.createTitledBorder ("Client properties"));

    m_aCBEnable = new Checkbox ("Use properties: ", AppProperties.getInstance ().isPropertiesEnabled ());
    m_aCBEnable.addItemListener (new ItemListener () {
      public void itemStateChanged (final ItemEvent e) {
        _setPropertiesEnabled (m_aCBEnable.getState ());
      }
    });
    m_aTFPropertiesPath = new JTextField (30);
    m_aTFPropertiesPath.setText (FilenameHelper.getCleanPath (AppProperties.getInstance ()
                                                                           .getPropertiesFilename ()
                                                                           .getAbsoluteFile ()));

    // Client properties file path
    m_aBtnBrowse = new JButton ("Browse");
    m_aBtnBrowse.addActionListener (new ActionListener () {
      public void actionPerformed (final ActionEvent e) {
        final JFileChooser aFileChooser = new JFileChooser (AppProperties.getInstance ()
                                                                         .getPropertiesFilename ()
                                                                         .getParentFile ());
        aFileChooser.setAcceptAllFileFilterUsed (false);
        aFileChooser.setFileFilter (new FileFilterProperties ());
        aFileChooser.showOpenDialog (null);
        final File aSelectedFile = aFileChooser.getSelectedFile ();
        if (aSelectedFile != null) {
          m_aTFPropertiesPath.setText (aSelectedFile.getAbsolutePath ());
          AppProperties.getInstance ().setPropertiesFilename (m_aTFPropertiesPath.getText ());
          if (AppProperties.getInstance ().readProperties ().isSuccess ())
            MainStatusBar.setStatus ("Loaded configuration file " + aSelectedFile.getPath ());
          else
            MainStatusBar.setStatusError ("Failed to load configuration file " + aSelectedFile.getPath ());
        }
      }
    });

    add (m_aCBEnable);
    add (m_aTFPropertiesPath, "width 100%");
    add (m_aBtnBrowse, "right, wrap");

    _setPropertiesEnabled (AppProperties.getInstance ().isPropertiesEnabled ());
  }

  private void _setPropertiesEnabled (final boolean bEnabled) {
    m_aTFPropertiesPath.setEditable (bEnabled);
    m_aBtnBrowse.setEnabled (bEnabled);
    AppProperties.getInstance ().setPropertiesEnabled (bEnabled);

    if (bEnabled)
      AppProperties.getInstance ().readProperties ();
    else
      AppProperties.getInstance ().clear ();
  }

  public void saveData () {
    AppProperties.getInstance ().setPropertiesFilename (m_aTFPropertiesPath.getText ());
    if (m_aCBEnable.getState ())
      AppProperties.getInstance ().writeProperties ();
  }
}
