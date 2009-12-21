/*****************************************************************************
 * Pause Quaf� - An Eve-Online� character assistance application             *
 * Copyright � 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quaf�.                                         *
 *                                                                           *
 * Pause Quaf� is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quaf� is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quaf�.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.view.misc;

import java.io.File;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.dao.MonitoredCharacterDAO;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class ErrorAPICorrupted extends QDialog {

    Ui_ErrorAPICorrupted ui = new Ui_ErrorAPICorrupted();
    private MonitoredCharacter character;

    public ErrorAPICorrupted(MonitoredCharacter character) {
        this(null, character);
    }

    public ErrorAPICorrupted(QWidget parent, MonitoredCharacter character) {
        super(parent);
        this.character = character;
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);
        this.setWindowTitle("API Error");
        ui.icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));

        String text = ui.mainTextLabel.text().replace("?",
                "<b>" + character.getApi().getCharacterName() + "</b>");
        ui.mainTextLabel.setText(text);

        ui.apiKeyText.setText(character.getApi().getApiKey());

        ui.cancelButton.clicked.connect(this, "reject()");
        ui.updateButton.clicked.connect(this, "accept()");
    }

    @Override
    public void accept() {
        character.getApi().setApiKey(ui.apiKeyText.text());
        try {
            MonitoredCharacterDAO.getInstance().changeApiKey(character.getApi());
        } catch (PQSQLDriverNotFoundException e) {
            ErrorMessage error = new ErrorMessage(this, tr(Constants.DRIVER_NOT_FOUND_ERROR));
            error.exec();
        } catch (PQUserDatabaseFileCorrupted e) {
            ErrorQuestion error = new ErrorQuestion(this, tr(Constants.USER_DB_CORRUPTED_ERROR));
            error.exec();
            if (error.result() == QDialog.DialogCode.Accepted.value()) {
                File userDb = new File(SQLConstants.USER_DATABASE_FILE);
                userDb.delete();
            }
        }
        super.accept();
    }

}