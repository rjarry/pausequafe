package org.pausequafe;

import org.pausequafe.gui.view.main.MainWindow;

import com.trolltech.qt.gui.QApplication;



public class PQLauncher {

    public static void main(String[] args) {
        QApplication.initialize(args);

        MainWindow mainWindow = null;
        mainWindow = new MainWindow();
        mainWindow.baseSize();

        QApplication.exec();
    }

}
