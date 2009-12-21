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
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class ErrorMessage extends QDialog {

    Ui_ErrorMessage ui = new Ui_ErrorMessage();

    private String message;

    public ErrorMessage(String message) {
        this(null, message);
    }

    public ErrorMessage(QWidget parent, String message) {
        super(parent);
        this.message = message;
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);
        this.setWindowTitle("Error");
        ui.icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));
        ui.text.setText(message);
        ui.text.setAlignment(Qt.AlignmentFlag.AlignCenter);
        ui.pushButton.clicked.connect(this, "reject()");
    }
}
