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

package org.pausequafe.gui.view.misc;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class ErrorQuestion extends QDialog {

    Ui_ErrorQuestion ui = new Ui_ErrorQuestion();

    private QLabel icon;
    private QLabel text;
    private QPushButton okPushButton;
    private QPushButton cancelPushButton;

    public ErrorQuestion(String message) {
        this(null, message);
    }

    public ErrorQuestion(QWidget parent, String message) {
        super(parent);
        setupUi();
        this.setWindowTitle("Error");
        text.setText(message);
    }

    private void setupUi() {
        ui.setupUi(this);

        icon = (QLabel) this.findChild(QLabel.class, "icon");
        icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));

        text = (QLabel) this.findChild(QLabel.class, "text");
        text.setAlignment(Qt.AlignmentFlag.AlignCenter);

        cancelPushButton = (QPushButton) this.findChild(QPushButton.class, "cancelPushButton");
        cancelPushButton.clicked.connect(this, "reject()");

        okPushButton = (QPushButton) this.findChild(QPushButton.class, "okPushButton");
        okPushButton.clicked.connect(this, "accept()");
    }
}
