package org.pausequafe;

import java.io.File;

import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import be.fomp.jeve.core.config.Configuration;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice.OpenModeFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSplashScreen;
import com.trolltech.qt.gui.QSystemTrayIcon;

public class PQLauncher {

	public static void main(String[] args) {
		QApplication.initialize(args);
		QApplication.setOrganizationName("Diabete© Studios");
		QApplication.setOrganizationDomain("diabetestudios.org");
		QApplication.setApplicationName("Pause Quafé");
		QApplication.setApplicationVersion(Constants.PQ_VERSION);

		QPixmap pix = new QPixmap(Constants.SPLASH_SCREEN);

		QFile file = new QFile("resources/ui/quafeStyleSheet.qss");
		file.open(OpenModeFlag.ReadOnly);
		String styleSheet = file.readAll().toString();

		QSplashScreen splash = new QSplashScreen(pix);
		splash.setStyleSheet(styleSheet);
		splash.show();
		QApplication.processEvents();
		QApplication.setQuitOnLastWindowClosed(false);

		try {
			splash.showMessage("Loading item database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(4);
			splash.showMessage("Loading ship database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(9);
			splash.showMessage("Loading skill database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(150);
			splash.showMessage("Loading proxy configuration...", 120, QColor.white);
			Configuration.setConfigurationFilePath(Constants.PROXY_CONFIG_FILE_PATH);
		} catch (PQSQLDriverNotFoundException e) {
			ErrorMessage error = new ErrorMessage(null, Constants.DRIVER_NOT_FOUND_ERROR);
			error.exec();
			QApplication.quit();
		} catch (PQUserDatabaseFileCorrupted e) {
			ErrorQuestion error = new ErrorQuestion(null, Constants.USER_DB_CORRUPTED_ERROR);
			error.exec();
			if (error.result() == QDialog.DialogCode.Accepted.value()) {
				File userDb = new File(SQLConstants.USER_DATABASE_FILE);
				userDb.delete();
			}
			QApplication.quit();
		} catch (PQEveDatabaseNotFound e) {
			ErrorMessage error = new ErrorMessage(null, Constants.EVE_DB_CORRUPTED_ERROR);
			error.exec();
			QApplication.quit();
		}

		MainWindow mainWindow = new MainWindow();

		QApplication.setActiveWindow(mainWindow);

		QSystemTrayIcon tray = new QSystemTrayIcon(new QIcon(Constants.WINDOW_ICON));
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

		splash.finish(mainWindow);
		mainWindow.show();
		tray.show();

		QApplication.exec();
	}

}
