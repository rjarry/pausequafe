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

import java.util.List;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.gui.model.characters.CharaterSkillPlansProxyModel;
import org.pausequafe.gui.view.main.MainWindow;
import org.pausequafe.gui.view.skillplans.AddSkillPlanDialog;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QAbstractItemView.DragDropMode;

public class SkillPlanListView extends QWidget {

    Ui_SkillPlanListView ui = new Ui_SkillPlanListView();
    MonitoredCharacter monChar;
    CharaterSkillPlansProxyModel skillPlansModel;

    public SkillPlanListView(MonitoredCharacter monitoredCharacter) {
        this(null, monitoredCharacter);
    }

    public SkillPlanListView(QWidget parent, MonitoredCharacter monitoredCharacter) {
        super(parent);
        monChar = monitoredCharacter;
        skillPlansModel = new CharaterSkillPlansProxyModel(monChar);
        setupUi();
    }

    private void setupUi() {
        ui.setupUi(this);

        QIcon iconAdd = new QIcon(Constants.ADD_ICON);
        QIcon iconDel = new QIcon(Constants.MINUS_ICON);

        ui.addPlanButton.setIcon(iconAdd);
        ui.removePlanButton.setIcon(iconDel);
        ui.skillPlanList.setModel(skillPlansModel);
        ui.skillPlanList.setSelectionMode(QAbstractItemView.SelectionMode.SingleSelection);
        ui.skillPlanList.setDragEnabled(true);
        ui.skillPlanList.setAcceptDrops(true);
        ui.skillPlanList.setDropIndicatorShown(true);
        ui.skillPlanList.setDragDropMode(DragDropMode.InternalMove);
        ui.skillPlanList.doubleClicked.connect(this, "onSkillPlanDoubleClicked(QModelIndex)");

        ui.addPlanButton.clicked.connect(this, "createSkillPlan()");
        ui.removePlanButton.clicked.connect(this, "deleteSkillPlan()");
    }

    @SuppressWarnings("unused")
    private void createSkillPlan() {
        AddSkillPlanDialog dialog = new AddSkillPlanDialog(monChar, this);
        dialog.exec();
        if (dialog.result() == QDialog.DialogCode.Accepted.value()) {
            String spName = dialog.getSkillPlanName();
            SkillPlan createdSP = skillPlansModel.createSkillPlan(spName);

            if (dialog.isOpenWindowChecked()) {
                MainWindow.getInstance().openPlanView(monChar, createdSP);
            }
        }
    }

    @SuppressWarnings("unused")
    private void deleteSkillPlan() {
        List<QModelIndex> selectedRows = ui.skillPlanList.selectionModel().selectedRows();
        if (selectedRows.size() == 1) {
            skillPlansModel.deleteSkillPlan(selectedRows.get(0).row());
        }
    }

    @SuppressWarnings("unused")
    private void onSkillPlanDoubleClicked(QModelIndex index) {
        SkillPlan skillPlan = (SkillPlan) skillPlansModel.data(index, ItemDataRole.DisplayRole);
        MainWindow.getInstance().openPlanView(monChar, skillPlan);
    }
}
