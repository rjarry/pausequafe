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

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QIODevice.OpenModeFlag;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QWidget;

public class StyleSheetEditor extends QDialog {

    Ui_StyleSheetEditor ui = new Ui_StyleSheetEditor();
    QWidget main;

    public StyleSheetEditor() {
        this(null);
    }

    public StyleSheetEditor(QWidget parent) {
        super(parent);
        main = parent;
        ui.setupUi(this);
        ui.styleTextEdit.textChanged.connect(this, "on_styleTextEdit_textChanged()");
        ui.applyButton.clicked.connect(this, "on_applyButton_clicked()");
        ui.saveButton.clicked.connect(this, "saveStyleSheet()");
        ui.saveButton.setShortcut("Ctrl+s");
        this.setWindowTitle("StyleSheet");

        QFile file = new QFile("resources/ui/quafeStyleSheet.qss");
        file.open(OpenModeFlag.ReadOnly);
        String styleSheet = file.readAll().toString();
        ui.styleTextEdit.setPlainText(styleSheet);
        main.setStyleSheet(styleSheet);
        ui.applyButton.setEnabled(false);
        file.close();

        ui.styleTextEdit.setFont(new QFont("Courier", 11));

    }

    void on_styleTextEdit_textChanged() {
        ui.applyButton.setEnabled(true);
        this.setWindowTitle("*StyleSheet (not saved)");
    }

    void on_applyButton_clicked() {
        main.setStyleSheet(ui.styleTextEdit.toPlainText());
        ui.styleTextEdit.setFont(new QFont("Courier", 11));
        ui.applyButton.setEnabled(false);
    }

    void saveStyleSheet() {
        QFile file = new QFile("resources/ui/quafeStyleSheet.qss");
        file.open(OpenModeFlag.WriteOnly);
        String styleSheet = ui.styleTextEdit.toPlainText();
        QByteArray data = new QByteArray(styleSheet);
        file.write(data);
        file.close();
        this.setWindowTitle("StyleSheet");
    }
}
