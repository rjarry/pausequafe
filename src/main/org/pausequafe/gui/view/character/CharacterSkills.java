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

package org.pausequafe.gui.view.character;

import java.io.File;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.data.business.SkillQueue;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.gui.model.browsers.MarketGroupElement;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.SortOrder;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class CharacterSkills extends QWidget {

    // //////////////////
    // private fields //
    // //////////////////
    private Ui_CharacterSkills ui = new Ui_CharacterSkills();

    private double currentSP = 0;
    private long trainingTimeLeft = 0;
    private double trainingSpeed = 0;

    private boolean thereIsASkillTraining = false;

    private QTimer timer;

    private ItemTreeSortFilterProxyModel proxyModel;

    // ////////////////
    // constructors //
    // ////////////////
    public CharacterSkills(CharacterSheet sheet, SkillInTraining inTraining, SkillQueue queue) {
        this(null, sheet, inTraining, queue);
    }

    public CharacterSkills(QWidget parent, CharacterSheet sheet, SkillInTraining inTraining,
            SkillQueue queue) {
        super(parent);
        setupUi();

        MarketGroup group = null;
        try {
            group = MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.SKILLS_MKTGRPID);
        } catch (PQSQLDriverNotFoundException e) {
            popSQLDriverError(e);
        } catch (PQUserDatabaseFileCorrupted e) {
            popUserDBCorrupt(e);
        } catch (PQEveDatabaseCorrupted e) {
            popEveDbCorrupted(e);
        }
        MarketGroupElement root = new MarketGroupElement(group);
        ItemTreeModel itemTreeModel = new ItemTreeModel(root);
        proxyModel = new ItemTreeSortFilterProxyModel();
        proxyModel.setSourceModel(itemTreeModel);
        ui.skillTree.setModel(proxyModel);

        loadSkills(sheet);
        loadSkillInTraining(sheet, inTraining, queue);
    }

    // ////////////////
    // widget setup //
    // ////////////////
    private void setupUi() {
        ui.setupUi(this);

        timer = new QTimer(this);
        timer.setObjectName("timer");
        timer.timeout.connect(this, "updateValues()");

        ui.skillTree.setSortingEnabled(true);
        ui.skillTree.sortByColumn(0, SortOrder.AscendingOrder);
        ui.skillTree.setIconSize(new QSize(21, 14));

    }

    // //////////////////
    // public methods //
    // //////////////////
    /**
     * Sets up the skills info of the character
     * 
     * @param sheet
     *            the character sheet
     */
    public void loadSkills(CharacterSheet sheet) {
        if (sheet != null) {
            // the amount of SP here is only calculated by summing the SP from
            // all skills.
            // it represents the total SP of character at the time of the XML
            // file generation.
            currentSP = sheet.getSkillPoints();
            ui.skillPoints.setText("<b>" + Formater.printLong(Math.round(currentSP)) + " total SP</b>");
            ui.skillPoints.setToolTip(sheet.getCloneName() + " ("
                    + Formater.printLong(sheet.getCloneSkillPoints()) + " SP)");

            String skillDetailsText = sheet.getLevel1Skills() + " skills at level I\n"
                                    + sheet.getLevel2Skills() + " skills at level II\n" 
                                    + sheet.getLevel3Skills() + " skills at level III\n" 
                                    + sheet.getLevel4Skills() + " skills at level IV\n"
                                    + sheet.getLevel5Skills() + " skills at level V";

            ui.skills.setText("<b>" + sheet.getSkillCount() + " known skills</b>");
            ui.skills.setToolTip(skillDetailsText);

            ui.levelVSkills.setText("<b>" + sheet.getLevel5Skills() + " skills at level V</b>");
            ui.levelVSkills.setToolTip(skillDetailsText);
            proxyModel.setSheet(sheet);
            proxyModel.setUnknownShown(false);
        }

    }

    /**
     * Sets up the skill in training info
     * 
     * @param inTraining
     *            the skill actually in training
     * @throws PQException
     *             if there's a problem accessing the skills database
     */
    public void loadSkillInTraining(CharacterSheet sheet, SkillInTraining inTraining,
            SkillQueue queue) {
        if (sheet != null) {
            StringBuilder trainingText = new StringBuilder();
            StringBuilder timeText = new StringBuilder();
            StringBuffer endText = new StringBuffer();
            StringBuilder toolTipText = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMMMMMMMMM HH:mm:ss");

            if (inTraining == null || inTraining.skillInTraining() == 0) {
                // if there's no skill in training
                thereIsASkillTraining = false;
                ui.skillInTraining.resize(450, 48);
                ui.skillInTraining.move(127, 3);
                ui.timeLeft.move(165, 13);
                trainingText.append("<img src=\"" + Constants.WARNING_SMALL_ICON + "\" />");
                timeText.append("<font color=\"#CC0000\"><b>NO SKILL IN TRAINING!</b></font>");
                if (queue.getSkillList().size() > 0) {
                    timeText.append(" <b><font color=\"#00CC00\">+" 
                                        + (queue.getSkillList().size())
                                  + " queued</font></b>");
                }
                toolTipText.append("<p style='white-space:pre'>");
                toolTipText.append(printSkillQueue(queue));
                toolTipText.append("</p>");
            } else {
                // else we fill the fields
                thereIsASkillTraining = true;
                ui.skillInTraining.resize(450, 13);
                ui.skillInTraining.move(127, 0);
                ui.timeLeft.move(127, 13);
                trainingText.append("<b>");
                try {
                    trainingText.append(ItemDAO.getInstance().findItemById(inTraining.getTypeID())
                            .getTypeName());
                } catch (PQException e) {
                    trainingText.append(inTraining.getTypeID());
                }
                trainingText.append(" ");
                trainingText.append(Formater.printLevel(inTraining.getLevel()));
                trainingText.append("</b> (");

                trainingText.append(Formater.printPercent(inTraining.calculateCompletion()));
                trainingText.append("%)");

                if (queue.getSkillList().size() > 1) {
                    trainingText.append(" <b><font color=\"#00CC00\">+"
                                        + (queue.getSkillList().size() - 1) 
                                        + " queued</font></b>");
                }

                // training end date
                dateFormat.format(new Date(inTraining.getEndTime()), endText, new FieldPosition(0));

                // time left
                long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
                trainingTimeLeft = inTraining.getEndTime() - now;
                if (trainingTimeLeft <= 0) {
                    trainingTimeLeft = 0;
                }
                timeText.append(Formater.printTime(trainingTimeLeft));

                // calculation of the current SP.
                // here, the correct amount of SP is calculated
                // by deducting the "last known" SP of skill in training (from
                // the character sheet)
                // and adding the current SP of it.
                currentSP -= sheet.getSkill(inTraining.getTypeID()).getSkillPoints();
                currentSP += inTraining.calculateCurrentSP();

                // setup of the current training speed (in SP / millisecond)
                trainingSpeed = inTraining.calculateTrainingSpeed();

                // training speed tooltip
                toolTipText.append("<p style='white-space:pre'><b>Training at: ");
                toolTipText.append(Math.round(trainingSpeed * Constants.HOUR));
                toolTipText.append(" SP/hour</b><br><br>");
                toolTipText.append(printSkillQueue(queue));
                toolTipText.append("</p>");

                // we start the timer only if there's a skill in training
                timer.start(Constants.SECOND);
            }

            ui.skillInTraining.setAlignment(Qt.AlignmentFlag.AlignTop);
            ui.skillInTraining.setAlignment(Qt.AlignmentFlag.AlignLeft);
            ui.skillInTraining.setText(trainingText.toString());
            ui.timeLeft.setText(timeText.toString());
            ui.trainingEnd.setText(endText.toString());
            ui.skillInTraining.setToolTip(toolTipText.toString());
            ui.timeLeft.setToolTip(toolTipText.toString());
            ui.trainingEnd.setToolTip(toolTipText.toString());
        }
    }

    public String printSkillQueue(SkillQueue queue) {
        String out = "";
        if (queue != null) {
            if (thereIsASkillTraining) {
                // we dont display the skill currently in training
                queue.getSkillList().remove(0);
            }
            out += "<b>Skills Queued: </b>";
            out += queue.toString();
        }
        return out;
    }

    // /////////
    // slots //
    // /////////
    /**
     * Updates total SP and time left fields every second
     */
    @SuppressWarnings("unused")
    private void updateValues() {
        if (thereIsASkillTraining) {
            if (trainingTimeLeft >= Constants.SECOND) {
                currentSP += trainingSpeed * Constants.SECOND;
                ui.skillPoints.setText("<b>" + Formater.printLong(Math.round(currentSP))
                        + " total SP</b>");

                trainingTimeLeft -= Constants.SECOND;
                ui.timeLeft.setText(Formater.printTime(trainingTimeLeft));
            } else {
                timer.stop();
                trainingTimeLeft = 0;
                thereIsASkillTraining = false;
                ui.timeLeft.setText(Formater.printTime(trainingTimeLeft));
            }
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

    private void popEveDbCorrupted(Exception e) {
        String message = tr(Constants.EVE_DB_CORRUPTED_ERROR);
        message += "\n" + e.getMessage();

        ErrorMessage error = new ErrorMessage(this, message);
        error.exec();
    }

}
