package org.jevemon;

import org.jevemon.gui.view.main.MainWindow;

import com.trolltech.qt.gui.QApplication;



public class JEVEMonLauncher {

    public static void main(String[] args) {
        QApplication.initialize(args);

        MainWindow mainWindow = null;
        mainWindow = new MainWindow();
        mainWindow.baseSize();

        QApplication.exec();
    }

}
