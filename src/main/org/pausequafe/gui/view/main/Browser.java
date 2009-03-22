package org.pausequafe.gui.view.main;

import org.jevemon.gui.view.main.Ui_Browser;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QWidget;

public class Browser extends QWidget {

    Ui_Browser ui = new Ui_Browser();

    public static void main(String[] args) {
        QApplication.initialize(args);

        Browser testBrowser = new Browser();
        testBrowser.show();

        QApplication.exec();
    }

    public Browser() {
        ui.setupUi(this);
    }

    public Browser(QWidget parent) {
        super(parent);
        ui.setupUi(this);
    }
}
