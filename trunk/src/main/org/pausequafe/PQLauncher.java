package org.pausequafe;

import org.pausequafe.gui.view.main.MainWindow;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFont;



public class PQLauncher {

    public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setFont(new QFont("sans serif", 9));
        QApplication.setStyle("plastique");
        
        
        MainWindow mainWindow = new MainWindow();
        mainWindow.show();

        QApplication.exec();
    }

}
