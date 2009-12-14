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

package org.pausequafe.gui.view.character;

import java.util.Calendar;
import java.util.TimeZone;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class CharacterSumup extends QWidget {

    private Ui_CharacterSumup ui = new Ui_CharacterSumup();

    private MonitoredCharacter character;

    private long trainingTimeLeft = 0;

    private QTimer timer;

    private boolean thereIsASkillTraining = false;

    // ////////////////
    // constructors //
    // ////////////////
    public CharacterSumup(MonitoredCharacter character) {
        this(character, null);
    }

    public CharacterSumup(MonitoredCharacter character, QWidget parent) {
        super(parent);

        this.character = character;

        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);

        QPixmap image = new QPixmap();
        image.load(Constants.BLANK_PORTRAIT);

        int size = ui.portraitLabel.size().height();
        ui.portraitLabel.setPixmap(image.scaledToHeight(size));

        timer = new QTimer(this);
        timer.timeout.connect(this, "updateValues()");
    }

    /**
     * Used as an external trigger for updating the fields & the picture
     */
    public void loadInfo() {
        String trainingText = "";
        String timeText = "";

        if (character.getSheet() != null) {
            String charNameText = "<font size=5>" + character.getSheet().getName() + "</font>";
            ui.charNameLabel.setText(charNameText);
        }

        if (character.getImageLocation() != null) {
            QPixmap image = new QPixmap();
            image.load(character.getImageLocation());
            int size = ui.portraitLabel.size().height();
            ui.portraitLabel.setPixmap(image.scaledToHeight(size));
        }

        if (character.getInTraining() != null && character.getInTraining().skillInTraining() != 0) {
            // else we fill the fields
            thereIsASkillTraining = true;

            try {
                trainingText += ItemDAO.getInstance().findItemById(
                        character.getInTraining().getTypeID()).getTypeName();
            } catch (PQException e) {
                trainingText += character.getInTraining().getTypeID();
            }

            switch (character.getInTraining().getLevel()) {
            case 1:
                trainingText += " I (";
                break;
            case 2:
                trainingText += " II (";
                break;
            case 3:
                trainingText += " III (";
                break;
            case 4:
                trainingText += " IV (";
                break;
            case 5:
                trainingText += " V (";
                break;
            default:
                trainingText += " 0 (";
            }
            trainingText += Formater.printPercent(character.getInTraining().calculateCompletion())
                    + "%)";

            // time left
            long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
            trainingTimeLeft = character.getInTraining().getEndTime() - now;
            if (trainingTimeLeft <= 0) {
                trainingTimeLeft = 0;
            }
            timeText += Formater.printTime(trainingTimeLeft);

            // we start the timer only if there's a skill in training
            timer.start(Constants.SECOND);
        } else {
            trainingText += "no skill in training!";
        }

        ui.skillInTrainingLabel.setText(trainingText);
        ui.trainingTimeLabel.setText(timeText);
    }

    // /////////
    // slots //
    // /////////
    /**
     * Updates time left field every second
     */
    @SuppressWarnings("unused")
    private void updateValues() {
        if (thereIsASkillTraining) {
            if (trainingTimeLeft >= Constants.SECOND) {
                trainingTimeLeft -= Constants.SECOND;
                ui.trainingTimeLabel.setText(Formater.printTime(trainingTimeLeft));
            } else {
                timer.stop();
                trainingTimeLeft = 0;
                thereIsASkillTraining = false;
                ui.trainingTimeLabel.setText(Formater.printTime(trainingTimeLeft));
            }
        }
    }
}
