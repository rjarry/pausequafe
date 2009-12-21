/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
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

package org.pausequafe.gui.view.misc;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPalette.ColorRole;

public class AboutPQ extends QDialog {

    Ui_AboutPQ ui = new Ui_AboutPQ();

    private QPushButton closeButton;
    private QLabel image;
    private QLabel textUp;
    private QLabel textDown;

    public AboutPQ() {
        this(null);
    }

    public AboutPQ(QWidget parent) {
        super(parent);
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);

        this.setWindowTitle("About Pause Quafé...");

        closeButton = (QPushButton) this.findChild(QPushButton.class, "closeButton");
        closeButton.clicked.connect(this, "accept()");

        image = (QLabel) this.findChild(QLabel.class, "image");
        image.setPixmap(new QPixmap(Constants.SPLASH_SCREEN));

        textUp = (QLabel) this.findChild(QLabel.class, "textUp");
        textUp.setAlignment(Qt.AlignmentFlag.AlignRight);
        textUp.setForegroundRole(ColorRole.Base);

        textUp.setText(Constants.ABOUT_TEXT);

        textDown = (QLabel) this.findChild(QLabel.class, "textDown");
        textDown.setAlignment(Qt.AlignmentFlag.AlignLeft);
        textDown.setForegroundRole(ColorRole.Base);

        textDown.setText(Constants.SUGGESTIONS_TEXT);
    }

}
