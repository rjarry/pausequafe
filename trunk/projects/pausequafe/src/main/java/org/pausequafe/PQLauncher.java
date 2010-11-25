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

package org.pausequafe;

import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.gui.view.main.PQSystemTray;
import org.pausequafe.gui.view.misc.Errors;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import be.fomp.jeve.core.config.Configuration;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice.OpenModeFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSplashScreen;

public class PQLauncher {

    private static final int BOTTOM_LEFT = 120;

    public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setOrganizationName("Diabete© Studios");
        QApplication.setOrganizationDomain("diabetestudios.org");
        QApplication.setApplicationName("Pause Quafé");
        QApplication.setApplicationVersion(Constants.PQ_VERSION);
        QApplication.setWindowIcon(new QIcon(Constants.WINDOW_ICON));

        QPixmap pix = new QPixmap(Constants.SPLASH_SCREEN);
        QFile file = new QFile("resources/ui/quafeStyleSheet.qss");
        file.open(OpenModeFlag.ReadOnly);
        String styleSheet = file.readAll().toString();
        file.close();

        QSplashScreen splash = new QSplashScreen(pix);
        splash.setStyleSheet(styleSheet);
        splash.show();
        QApplication.processEvents();
        QApplication.setQuitOnLastWindowClosed(false);

        try {
            splash.showMessage("Loading item database...", BOTTOM_LEFT, QColor.white);
            MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.ITEMS_MKTGRPID);
            splash.showMessage("Loading ship database...", BOTTOM_LEFT, QColor.white);
            MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.SHIPS_MKTGRPID);
            splash.showMessage("Loading skill database...", BOTTOM_LEFT, QColor.white);
            MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.SKILLS_MKTGRPID);
            splash.showMessage("Loading blueprint database...", BOTTOM_LEFT, QColor.white);
            MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.BLUEPRINTS_MKTGRPID);
            splash.showMessage("Loading proxy configuration...", BOTTOM_LEFT, QColor.white);
            Configuration.setConfigurationFilePath(Constants.PROXY_CONFIG_FILE_PATH);
        } catch (PQSQLDriverNotFoundException e) {
            Errors.popError(splash, Constants.DRIVER_NOT_FOUND_ERROR);
            System.exit(1);
        } catch (PQUserDatabaseFileCorrupted e) {
            Errors.popUserDBCorrupt(splash, e);
            System.exit(1);
        } catch (PQEveDatabaseCorrupted e) {
            Errors.popEveDbCorrupted(splash, e);
            System.exit(1);
        }

        MainWindow mainWindow = MainWindow.getInstance();

        QApplication.setActiveWindow(mainWindow);

        PQSystemTray tray = new PQSystemTray(new QIcon(Constants.WINDOW_ICON));
        // différenciation pour le clic gauche
        // j'ai été obligé de faire une méthode dans MainWindow car ici on est
        // en static -> pas de slot
        tray.activated.connect(mainWindow,
                "showWindow(com.trolltech.qt.gui.QSystemTrayIcon$ActivationReason)");

        // system tray menu
        QMenu menu = new QMenu();
        menu.addAction("Show Main Window...", mainWindow, "show()");
        menu.addAction("Quit", QApplication.instance(), "quit()");
        tray.setContextMenu(menu);

        mainWindow.setStyleSheet(styleSheet);
        mainWindow.aboutToQuit.connect(tray, "dispose()");
        mainWindow.aboutToQuit.connect(QApplication.instance(), "quit()");
        mainWindow.dataUpdated.connect(tray, "changeTrayToolTip(ServerStatus)");

        splash.finish(mainWindow);
        mainWindow.show();
        tray.show();

        QApplication.exec();
    }
}
