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

package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;

public class BrowsersWindow extends QWidget {

    Ui_BrowsersWindow ui = new Ui_BrowsersWindow();

    // /////////////////
    // private fields //
    // /////////////////
    private BrowserSkillTab skillBrowser;
    private BrowserShipTab shipBrowser;
    private BrowserItemTab moduleBrowser;
    private BrowserBlueprintTab blueprintBrowser;

    private QToolBar toolBar;
    private QComboBox sheetCombo;

    private MonitoredCharactersAndSkillPlansModel characterModel;

    // ///////////////
    // constructors //
    // ///////////////
    public BrowsersWindow() {
        this(null);
    }

    public BrowsersWindow(QWidget parent) {
        super(parent);
        setupUi();
        try {
            characterModel = MonitoredCharactersAndSkillPlansModel.getInstance();
        } catch (PQSQLDriverNotFoundException e) {
            popSQLDriverError(e);
        } catch (PQUserDatabaseFileCorrupted e) {
            popUserDBCorrupt(e);
        }
        sheetCombo.setModel(characterModel);

        changeCurrentCharacter(sheetCombo.currentIndex());
    }

    // ///////////////
    // widget setup //
    // ///////////////
    private void setupUi() {
        ui.setupUi(this);

        this.setWindowTitle("Browsers");

        toolBar = new QToolBar(this);
        ui.verticalLayout.insertWidget(0, toolBar);

        sheetCombo = new QComboBox(this);
        toolBar.addWidget(new QLabel(" Active Character : "));
        toolBar.addWidget(sheetCombo);

        sheetCombo.currentIndexChanged.connect(this, "changeCurrentCharacter(int)");

        skillBrowser = new BrowserSkillTab(this, SQLConstants.SKILLS_MKTGRPID);
        shipBrowser = new BrowserShipTab(this, SQLConstants.SHIPS_MKTGRPID);
        moduleBrowser = new BrowserItemTab(this, SQLConstants.ITEMS_MKTGRPID);
        blueprintBrowser = new BrowserBlueprintTab(this, SQLConstants.BLUEPRINTS_MKTGRPID);

        ui.tabWidget.removeTab(0);

        ui.tabWidget.addTab(skillBrowser, "Skills");
        ui.tabWidget.addTab(shipBrowser, "Ships");
        ui.tabWidget.addTab(moduleBrowser, "Ship Equipement");
        ui.tabWidget.addTab(blueprintBrowser, "Blueprints");

        this.resize(1100, 700);
    }

    // //////////////////
    // private methods //
    // //////////////////
    private void changeCurrentCharacter(int index) {
        MonitoredCharacter data = (MonitoredCharacter) sheetCombo.itemData(index,
                ItemDataRole.DisplayRole);
        CharacterSheet sheet;
        if (data == null) {
            sheet = null;
        } else {
            sheet = data.getSheet();
        }
        skillBrowser.setSheet(sheet);
        shipBrowser.setSheet(sheet);
        moduleBrowser.setSheet(sheet);
        blueprintBrowser.setSheet(sheet);
    }

    private void popUserDBCorrupt(Exception e) {
        String message = tr(Constants.USER_DB_CORRUPTED_ERROR);
        message += "\n" + e.getMessage();

        ErrorQuestion error = new ErrorQuestion(this, message);
        error.exec();
        if (error.result() == QDialog.DialogCode.Accepted.value()) {
            File userDb = new File(SQLConstants.USER_DATABASE_FILE);
            userDb.delete();
        }
    }

    private void popSQLDriverError(Exception e) {
        ErrorMessage error = new ErrorMessage(this, tr(Constants.DRIVER_NOT_FOUND_ERROR));
        error.exec();
    }

}
