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

package org.pausequafe.gui.view.skillplans;

import org.pausequafe.data.business.MonitoredCharacter;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class AddSkillPlanDialog extends QDialog {

	Ui_AddSkillPlanDialog ui = new Ui_AddSkillPlanDialog();

    private MonitoredCharacter character;
    private String skillPlanName;

    public AddSkillPlanDialog(MonitoredCharacter character) {
        this(character, null);
    }

    public AddSkillPlanDialog(MonitoredCharacter character, QWidget parent) {
        super(parent);
        this.character = character;
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);
        this.setWindowTitle("New Skill Plan for " + character.getApi().getCharacterName());

        ui.cancelButton.clicked.connect(this, "reject()");
        ui.createButton.clicked.connect(this, "accept()");
        ui.skillPlanNameLineEdit.textChanged.connect(this, "nameChanged(String)");

        ui.createButton.setEnabled(false);
    }

    @SuppressWarnings("unused")
    private void nameChanged(String name) {
        skillPlanName = name;
        boolean isValid = isValid(name);
        ui.createButton.setEnabled(isValid);
    }

    private boolean isValid(String name) {
        boolean result = !("".equals(name));
        for (int i = 0; i < character.skillPlanCount() && result; i++) {
            result = result && !(character.getSkillPlanAt(i).getName().equals(name));
        }
        return result;
    }

	public String getSkillPlanName() {
		return skillPlanName;
	}
	
	public boolean isOpenWindowChecked(){
		return ui.openSPWindowCheckBox.isChecked();
	}
}
