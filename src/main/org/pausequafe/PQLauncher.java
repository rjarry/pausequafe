package org.pausequafe;

import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice.OpenModeFlag;
import com.trolltech.qt.gui.QApplication;



public class PQLauncher {

    public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setOrganizationName("Diabete© Studios");
        QApplication.setOrganizationDomain("diabetestudios.org");
        QApplication.setApplicationName("Pause Quafé");
        QApplication.setApplicationVersion(Constants.PQ_VERSION);
        
//        QApplication.setStyle("plastique");
        
        QFile file = new QFile("D:/Eclipse Workspace/PauseQuafé/resources/ui/quafeStyleSheet.qss");
        file.open(OpenModeFlag.ReadOnly);
        String styleSheet = file.readAll().toString();
                
        MainWindow mainWindow = new MainWindow();
        mainWindow.setStyleSheet(styleSheet);
        
        mainWindow.show();
        StyleSheetEditor styleSheetEditor = new StyleSheetEditor(mainWindow);
        styleSheetEditor.show();

        QApplication.exec();
    }
}
