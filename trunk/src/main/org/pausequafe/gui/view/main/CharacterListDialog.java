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

package org.pausequafe.gui.view.main;

import java.util.List;

import org.pausequafe.data.character.APIData;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class CharacterListDialog extends QDialog {

    Ui_CharacterListDialog ui = new Ui_CharacterListDialog();

    List<APIData> characterList;

    private int chosenCharacterIndex = -1;

    public CharacterListDialog(List<APIData> characterList) {
        this(null, characterList);
    }

    public CharacterListDialog(QWidget parent, List<APIData> characterList) {
        super(parent);
        this.characterList = characterList;
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);
        ui.chooseButton.clicked.connect(this, "accept()");
        ui.chooseButton.setEnabled(false);

        for (APIData character : characterList) {
            ui.charListWidget.addItem(character.getCharacterName());
        }

        ui.charListWidget.currentRowChanged.connect(this, "updateIndex()");
        ui.charListWidget.doubleClicked.connect(this, "accept()");

    }

    @SuppressWarnings("unused")
    private void updateIndex() {
        chosenCharacterIndex = ui.charListWidget.currentIndex().row();
        ui.chooseButton.setEnabled(true);
    }

    public int getChosenCharacterIndex() {
        return chosenCharacterIndex;
    }
}
