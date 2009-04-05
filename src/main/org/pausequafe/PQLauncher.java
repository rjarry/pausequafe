package org.pausequafe;

import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice.OpenModeFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSplashScreen;



public class PQLauncher {

	public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setOrganizationName("Diabete© Studios");
        QApplication.setOrganizationDomain("diabetestudios.org");
        QApplication.setApplicationName("Pause Quafé");
        QApplication.setApplicationVersion(Constants.PQ_VERSION);
               
		QPixmap pix = new QPixmap(Constants.SPLASH_SCREEN);
		
		QSplashScreen splash = new QSplashScreen(pix);
        splash.show();
        QApplication.processEvents();

        try {
        	splash.showMessage("Loading item database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(4);
			splash.showMessage("Loading ship database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(9);
			splash.showMessage("Loading skill database...", 120, QColor.white);
			MarketGroupDAO.getInstance().findMarketGroupById(150);
		} catch (PQSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (PQUserDatabaseFileCorrupted e) {
			e.printStackTrace();
		} catch (PQEveDatabaseNotFound e) {
			e.printStackTrace();
		}
		
		MainWindow mainWindow = new MainWindow();
		
		QFile file = new QFile("resources/ui/quafeStyleSheet.qss");
		file.open(OpenModeFlag.ReadOnly);
		String styleSheet = file.readAll().toString();
		mainWindow.setStyleSheet(styleSheet);
//		StyleSheetEditor styleSheetEditor = new StyleSheetEditor(mainWindow);
//    	styleSheetEditor.show();

		splash.finish(mainWindow);
		mainWindow.show();

        QApplication.exec();
    }
    
  }
