/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.view.main;

import java.net.Proxy.Type;

import be.fomp.jeve.core.config.Configuration;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.util.Proxy;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class SettingsDialog extends QDialog {

    Ui_SettingsDialog ui = new Ui_SettingsDialog();

    // ////////////////
    // constructors //
    // ////////////////
    public SettingsDialog() {
        this(null);
    }

    public SettingsDialog(QWidget parent) {
        super(parent);
        setupUi();
        readProxySettings();
    }

    // ////////////////
    // widget setup //
    // ////////////////
    private void setupUi() {
        ui.setupUi(this);
        this.accepted.connect(this, "saveProxySettings()");
        this.setWindowTitle("Pause Quafé Settings");
        this.setWindowFlags(Qt.WindowType.Window);
        ui.buttonBox.accepted.connect(this, "accept()");
        ui.buttonBox.rejected.connect(this, "reject()");
    }

    private void readProxySettings() {
        Proxy proxy = null;
        try {
            proxy = Configuration.getInstance().getProxyConfiguration();
        } catch (JEveConfigurationException e) {
            // I/O exception when reading config file (shouldn't happen)
            e.printStackTrace();
        }

        if (proxy != null) {
            ui.proxyCustomRadioButton.setChecked(true);

            ui.proxyHostNameLineEdit.setText(proxy.getHost());
            ui.proxyPortLineEdit.setText("" + proxy.getPort());

            if (proxy.getAuthenticator() != null) {
                ui.authenticationCheckBox.setChecked(true);
                ui.proxyLoginLineEdit.setText(proxy.getUsername());
                ui.proxyPassLineEdit.setText(proxy.getPassword());
            } else {
                ui.authenticationCheckBox.setChecked(false);
            }
        }
    }

    @SuppressWarnings("unused")
    private void saveProxySettings() {

        boolean useProxy = ui.proxyCustomRadioButton.isChecked();
        boolean useProxyAuth = ui.authenticationCheckBox.isChecked();

        Type proxyType;
        String proxyHost;
        int proxyPort;

        String proxyUser;
        String proxyPass;

        if (useProxy) {
            proxyType = Type.HTTP;
            proxyHost = ui.proxyHostNameLineEdit.text();
            try {
                proxyPort = Integer.parseInt(ui.proxyPortLineEdit.text());
            } catch (NumberFormatException e) {
                proxyPort = 0;
            }
        } else {
            proxyType = Type.DIRECT;
            proxyHost = "";
            proxyPort = 0;
        }

        if (useProxyAuth) {
            proxyUser = ui.proxyLoginLineEdit.text();
            proxyPass = ui.proxyPassLineEdit.text();
        } else {
            proxyUser = "";
            proxyPass = "";
        }

        try {
            Configuration.getInstance().setProxyconfiguration(useProxy, proxyType, proxyHost,
                    proxyPort, useProxyAuth, proxyUser, proxyPass);
            Configuration.getInstance().storeConfiguration();
            Configuration.getInstance().loadConfiguration();
        } catch (JEveConnectionException e) {
            // not possible JEVECore absurdity
            e.printStackTrace();
        } catch (JEveConfigurationException e) {
            // I/O exception when reading config file (shouldn't happen)
            e.printStackTrace();
        }

    }

}
