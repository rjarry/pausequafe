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

import java.util.List;

import org.pausequafe.core.threads.ApiRequest;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.CountdownTimer;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPalette.ColorRole;

/**
 * This widget gathers all the information about a character.
 * 
 * @author diabeteman
 */
public class CharacterInfo extends QFrame {

    // /////////////////
    // private fields //
    // /////////////////
    private Ui_CharacterInfo ui = new Ui_CharacterInfo();

    private QTimer timerHour;
    private QTimer timerSecond;
    private CountdownTimer timeLeft;

    public Signal0 requestNeeded;

    // ///////////////
    // constructors //
    // ///////////////

    public CharacterInfo() {
        this(null);
    }

    public CharacterInfo(QWidget parent) {
        super(parent);
        ui.setupUi(this);
        setupUi();
    }

    // ///////////////
    // widget setup //
    // ///////////////
    private void setupUi() {

        ui.refreshButton.clicked.connect(this, "emitRequestSignal()");
        ui.refreshButton.setEnabled(false);
        ui.clock.setAlignment(Qt.AlignmentFlag.AlignRight);
        ui.clock.setForegroundRole(ColorRole.Mid);
        ui.clock.setText("00:00");
        ui.cachedLabel.setAlignment(Qt.AlignmentFlag.AlignRight);
        ui.cachedLabel.setText("<b>(Cached)</b>");
        ui.cachedLabel.setVisible(false);

        QPixmap image = new QPixmap(Constants.BLANK_PORTRAIT);
        image = image.scaled(ui.portrait.size());
        ui.portrait.setPixmap(image);

        timerHour = new QTimer();
        timerHour.timeout.connect(this, "emitRequestSignal()");
        requestNeeded = new Signal0();

        timerSecond = new QTimer();
        timerSecond.timeout.connect(this, "decrementOneSecond()");

        timeLeft = new CountdownTimer(60, 0);
    }

    // /////////////////
    // public methods //
    // //////////////////
    /**
     * Loads the portrait of the character.
     * 
     * @param fileName
     *            portrait image file location
     */
    public void loadPortrait(String fileName) {
        if (fileName != null) {
            QPixmap image = new QPixmap(fileName);
            image = image.scaled(ui.portrait.size());
            ui.portrait.setPixmap(image);
        }
    }

    /**
     * Sets up basic info about the character
     * 
     * @param sheet
     *            the character
     */
    public void loadInfo(CharacterSheet sheet) {
        if (sheet == null || sheet.getCharacterID() == ApiRequest.CONNECTION_ERROR
                || sheet.getCharacterID() == ApiRequest.AUTHENTICATION_ERROR) {
            String textInfo = "<b><font size=5>" + sheet.getName() + "</font></b><br>";
            ui.info.setText(textInfo);
        } else {
            String textInfo = "<b><font size=5>" + sheet.getName() + "</font></b><br>";
            textInfo += "Corporation: " + sheet.getCorporationName() + "<br>";
            textInfo += "Balance: " + Formater.printDouble(sheet.getBalance()) + " ISK";

            List<String> titles = sheet.getCorporationTitles();

            if (titles.size() != 0) {
                String corpTitles = "";
                for (int i = 0; i < titles.size() - 1; i++) {
                    corpTitles += titles.get(i) + "\n";
                }
                corpTitles += titles.get(titles.size() - 1);
                ui.info.setToolTip(corpTitles);
            }
            ui.info.setText(textInfo);
            ui.cachedLabel.setVisible(sheet.isCached());
        }
    }

    /**
     * Sets up the effective attribute values of the character.
     * 
     * @param sheet
     *            the character
     */
    public void loadAttributes(CharacterSheet sheet) {
        if (sheet != null) {
            // building of the labels' text
            String charismaText = "Charisma: "
                    + Formater.printDouble(sheet.getEffectiveAttributeValue("charisma"));
            String intelligenceText = "Intelligence: "
                    + Formater.printDouble(sheet.getEffectiveAttributeValue("intelligence"));
            String memoryText = "Memory: "
                    + Formater.printDouble(sheet.getEffectiveAttributeValue("memory"));
            String perceptionText = "Perception: "
                    + Formater.printDouble(sheet.getEffectiveAttributeValue("perception"));
            String willpowerText = "Willpower: "
                    + Formater.printDouble(sheet.getEffectiveAttributeValue("willpower"));
            ui.charisma.setText(charismaText);
            ui.intelligence.setText(intelligenceText);
            ui.memory.setText(memoryText);
            ui.perception.setText(perceptionText);
            ui.willpower.setText(willpowerText);
            // building of the tooltips
            charismaText += " = [(" + sheet.getCharisma() + " base + "
                    + sheet.getSkillsBonus("charisma") + " skills + "
                    + sheet.getAttributeEnhancer("charisma").getAugmentatorValue()
                    + " implants) * " + Formater.printDouble(sheet.getLearningBonus())
                    + " from learning bonus]";
            intelligenceText += " = [(" + sheet.getIntelligence() + " base + "
                    + sheet.getSkillsBonus("intelligence") + " skills + "
                    + sheet.getAttributeEnhancer("intelligence").getAugmentatorValue()
                    + " implants) * " + Formater.printDouble(sheet.getLearningBonus())
                    + " from learning bonus]";
            memoryText += " = [(" + sheet.getMemory() + " base + " + sheet.getSkillsBonus("memory")
                    + " skills + " + sheet.getAttributeEnhancer("memory").getAugmentatorValue()
                    + " implants) * " + Formater.printDouble(sheet.getLearningBonus())
                    + " from learning bonus]";
            perceptionText += " = [(" + sheet.getPerception() + " base + "
                    + sheet.getSkillsBonus("perception") + " skills + "
                    + sheet.getAttributeEnhancer("perception").getAugmentatorValue()
                    + " implants) * " + Formater.printDouble(sheet.getLearningBonus())
                    + " from learning bonus]";
            willpowerText += " = [(" + sheet.getWillpower() + " base + "
                    + sheet.getSkillsBonus("willpower") + " skills + "
                    + sheet.getAttributeEnhancer("willpower").getAugmentatorValue()
                    + " implants) * " + Formater.printDouble(sheet.getLearningBonus())
                    + " from learning bonus]";
            ui.charisma.setToolTip(charismaText);
            ui.intelligence.setToolTip(intelligenceText);
            ui.memory.setToolTip(memoryText);
            ui.perception.setToolTip(perceptionText);
            ui.willpower.setToolTip(willpowerText);
        }
    }

    public void resetTimers() {
        timeLeft.setMinutes(60);
        timeLeft.setSeconds(0);
        timerHour.start(Constants.HOUR);
        timerSecond.start(Constants.SECOND);
        ui.refreshButton.setEnabled(true);
    }

    // /////////
    // slots //
    // /////////

    @SuppressWarnings("unused")
    private void emitRequestSignal() {
        ui.refreshButton.setEnabled(false);
        requestNeeded.emit();
    }

    @SuppressWarnings("unused")
    private void decrementOneSecond() {
        timeLeft.decrementOneSecond();
        ui.clock.setText(timeLeft.printTime());
    }

}
