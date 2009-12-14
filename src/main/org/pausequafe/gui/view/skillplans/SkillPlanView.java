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

package org.pausequafe.gui.view.skillplans;

import java.io.File;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.gui.model.characters.CharaterSkillPlansProxyModel;
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

public class SkillPlanView extends QWidget {
    Ui_SkillPlanView ui = new Ui_SkillPlanView();

    // //////////////////
    // private fields //
    // //////////////////
    private QToolBar toolBar;
    private QComboBox characterCombo;
    private QComboBox planCombo;

    private MonitoredCharactersAndSkillPlansModel characterModel;
    private CharaterSkillPlansProxyModel planModel;

    MonitoredCharacter characterSelected;
    SkillPlan planSelected;

    // ////////////////
    // constructors //
    // ////////////////
    public SkillPlanView() {
        this(null);
    }

    public SkillPlanView(QWidget parent) {
        super(parent);
        setupUi();
        try {
            characterModel = MonitoredCharactersAndSkillPlansModel.getInstance();
        } catch (PQSQLDriverNotFoundException e) {
            popSQLDriverError(e);
        } catch (PQUserDatabaseFileCorrupted e) {
            popUserDBCorrupt(e);
        }
        characterCombo.setModel(characterModel);

        // characterSelected = (MonitoredCharacter)
        // characterModel.data(characterCombo.currentIndex(),0,ItemDataRole.DisplayRole);
        // planModel = new CharaterSkillPlansProxyModel(characterSelected);
        // planCombo.setModel(planModel);
        // if(planCombo.currentIndex()!=-1){
        // planSelected = (SkillPlan) planModel.data(planCombo.currentIndex(), 0,
        // ItemDataRole.DisplayRole);
        // } else {
        // planSelected = null;
        // }
        characterCombo.setCurrentIndex(0);
        if (planCombo.model().rowCount() != 0) {
            planCombo.setCurrentIndex(0);
        }
    }

    // ////////////////
    // widget setup //
    // ////////////////
    private void setupUi() {
        ui.setupUi(this);

        this.setWindowTitle("Skill Plan");

        toolBar = new QToolBar(this);
        ui.verticalLayout.insertWidget(0, toolBar);

        characterCombo = new QComboBox(this);
        planCombo = new QComboBox(this);

        toolBar.addWidget(new QLabel(tr("Active Skill Plan : ")));
        toolBar.addWidget(characterCombo);
        toolBar.addWidget(planCombo);

        characterCombo.currentIndexChanged.connect(this, "onCharacterComboChanged(int)");
        planCombo.currentIndexChanged.connect(this, "onPlanComboChanged(int)");
    }

    // //////////////////
    // public methods //
    // //////////////////
    public void changeSelectedCharacter(MonitoredCharacter character) {
        int rowOfCharacter = characterModel.valueToIndex(character).row();
        characterCombo.setCurrentIndex(rowOfCharacter);
    }

    public void changeSelectedSkillPlan(SkillPlan plan) {
        int rowOfPlan = characterModel.valueToIndex(plan).row();
        planCombo.setCurrentIndex(rowOfPlan);
    }

    // //////////////////
    // private methods //
    // //////////////////
    @SuppressWarnings("unused")
    private void onCharacterComboChanged(int index) {
        characterSelected = (MonitoredCharacter) characterModel.data(index, 0,
                ItemDataRole.DisplayRole);
        planModel = new CharaterSkillPlansProxyModel(characterSelected);
        planCombo.setModel(planModel);
    }

    @SuppressWarnings("unused")
    private void onPlanComboChanged(int index) {
        if (planCombo.currentIndex() != -1) {
            planSelected = (SkillPlan) planModel.data(planCombo.currentIndex(), 0,
                    ItemDataRole.DisplayRole);
        } else {
            planSelected = null;
        }
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