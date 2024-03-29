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

package org.pausequafe.gui.view.browsers;

import static org.pausequafe.misc.util.Constants.BATCH_ICON;
import static org.pausequafe.misc.util.Constants.COPY_ICON;
import static org.pausequafe.misc.util.Constants.EVE_ICONS_PATH;
import static org.pausequafe.misc.util.Constants.INVENTION_ICON;
import static org.pausequafe.misc.util.Constants.MAXRUNS_ICON;
import static org.pausequafe.misc.util.Constants.METAGROUP_ICONS_TAG;
import static org.pausequafe.misc.util.Constants.ME_ICON;
import static org.pausequafe.misc.util.Constants.NO_ITEM_SELECTED_ICON;
import static org.pausequafe.misc.util.Constants.PE_ICON;
import static org.pausequafe.misc.util.Constants.PRICE_ICON;
import static org.pausequafe.misc.util.Constants.PROD_ICON;
import static org.pausequafe.misc.util.Constants.WASTE_ICON;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.item.BPActivity;
import org.pausequafe.data.item.Blueprint;
import org.pausequafe.data.item.BlueprintDetailed;
import org.pausequafe.data.item.Item;
import org.pausequafe.data.item.MarketGroup;
import org.pausequafe.data.item.PreRequisite;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.gui.model.browsers.MarketGroupElement;
import org.pausequafe.gui.model.browsers.MaterialsTableModel;
import org.pausequafe.gui.view.misc.Errors;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class BrowserBlueprintTab extends QWidget {

    private static final int INDUSTRY = 3380;
    private static final int METALLURGY = 3409;
    private static final int RESEARCH = 3403;
    private static final int SCIENCE = 3402;
    // /////////
    // FIELDS //
    // /////////
    Ui_BrowserBlueprintTab ui;
    private QTimer delayBeforeUpdtingFilter;

    private BlueprintDetailed selectedBP;
    private ItemTreeSortFilterProxyModel proxyModel = new ItemTreeSortFilterProxyModel();
    private int selectedActivityID = BPActivity.MANUFACTURING;
    private MaterialsTableModel materialsTableModel = new MaterialsTableModel();
    private ItemTreeModel prereqModel = new ItemTreeModel();
    private CharacterSheet sheet = null;

    // ///////////////
    // CONSTRUCTORS //
    // ///////////////
    public BrowserBlueprintTab(int marketGroupID) {
        this(null, marketGroupID);
    }

    public BrowserBlueprintTab(QWidget parent, int marketGroupID) {
        super(parent);

        setupUi();
        initModels(marketGroupID);
        initConnection();
    }

    private void setupUi() {
        ui = new Ui_BrowserBlueprintTab();
        ui.setupUi(this);

        delayBeforeUpdtingFilter = new QTimer();
        delayBeforeUpdtingFilter.setInterval(200);
        delayBeforeUpdtingFilter.setSingleShot(true);

        ui.prereqTree.setHeaderHidden(true);
        ui.materialsTable.verticalHeader().setVisible(false);

        QFont font = new QFont("Arial", 24);
        font.setBold(true);
        ui.meSpinBox.setFont(font);
        ui.peSpinBox.setFont(font);
        ui.iconLabel.setPixmap(new QPixmap(NO_ITEM_SELECTED_ICON));
        ui.itemNameLabel.setText("<font size=5>No item selected...</font>");

        fillActivityComboBox();

        ui.wasteIcon.setPixmap(new QPixmap(WASTE_ICON));
        ui.prodIcon.setPixmap(new QPixmap(PROD_ICON));
        ui.peIcon.setPixmap(new QPixmap(PE_ICON));
        ui.meIcon.setPixmap(new QPixmap(ME_ICON));
        ui.copyIcon.setPixmap(new QPixmap(COPY_ICON));
        ui.maxrunsIcon.setPixmap(new QPixmap(MAXRUNS_ICON));
        ui.batchIcon.setPixmap(new QPixmap(BATCH_ICON));
        ui.inventionIcon.setPixmap(new QPixmap(INVENTION_ICON));
        ui.priceIcon.setPixmap(new QPixmap(PRICE_ICON));
        ui.genInfo.setEnabled(false);
        ui.prereqs.setEnabled(false);

    }

    private void initConnection() {
        ui.itemTree.selectionModel().currentChanged.connect(this,
                "currentItemSelected(QModelIndex)");

        ui.lineEdit.textChanged.connect(delayBeforeUpdtingFilter, "start()");
        delayBeforeUpdtingFilter.timeout.connect(this, "changeFilteringText()");
        ui.tech1CheckBox.toggled.connect(proxyModel, "setTech1Shown(boolean)");
        ui.tech2CheckBox.toggled.connect(proxyModel, "setTech2Shown(boolean)");
        ui.tech3CheckBox.toggled.connect(proxyModel, "setTech3Shown(boolean)");
        ui.factionCheckBox.toggled.connect(proxyModel, "setFactionShown(boolean)");
        ui.factionCheckBox.toggled.connect(proxyModel, "setStorylineShown(boolean)");

        ui.activityComboBox.activatedIndex.connect(this, "fillActivity(Integer)");
        ui.ignoreSkillsBox.toggled.connect(materialsTableModel, "setIgnoreSkills(Boolean)");
        ui.ignoreSkillsBox.toggled.connect(this, "updateTimes()");
        ui.meSpinBox.valueChanged.connect(materialsTableModel, "setMe(Integer)");
        ui.peSpinBox.valueChanged.connect(this, "setPe(Integer)");

        QAction enterFilterArea = new QAction(this);
        enterFilterArea.triggered.connect(ui.lineEdit, "setFocus()");
        enterFilterArea.setShortcut("Ctrl+F");
    }

    private void initModels(int marketGroupID) {
        MarketGroup group = null;
        try {
            group = MarketGroupDAO.getInstance().findMarketGroupById(marketGroupID);
        } catch (PQSQLDriverNotFoundException e) {
            Errors.popSQLDriverError(this, e);
        } catch (PQUserDatabaseFileCorrupted e) {
            Errors.popUserDBCorrupt(this, e);
        } catch (PQEveDatabaseCorrupted e) {
            Errors.popEveDbCorrupted(this, e);
        }
        ItemTreeElement root = new MarketGroupElement(group);
        ItemTreeModel browserTreeModel = new ItemTreeModel(root);
        proxyModel.setSourceModel(browserTreeModel);
        ui.itemTree.setModel(proxyModel);
        ui.prereqTree.setModel(prereqModel);
        proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
        ui.materialsTable.setModel(materialsTableModel);
        ui.materialsTable.setSortingEnabled(true);
    }

    // /////////////////
    // PUBLIC METHODS //
    // /////////////////
    public void setSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        proxyModel.setSheet(sheet);
        materialsTableModel.setCharacter(sheet);
        prereqModel.setSheet(sheet);
        updateTimes();
    }

    private void updateTimes() {
        if (selectedBP != null) {
            ui.peLabel.setText(printPETime(selectedBP.getResearchPETime()));
            ui.meLabel.setText(printMETime(selectedBP.getResearchMETime()));
            ui.copyLabel.setText(printCopyTime(selectedBP.getCopyTime()));
            ui.prodLabel.setText(printProdTime(selectedBP.getProductionTime()));
        }
    }

    // ////////
    // SLOTS //
    // ////////
    @SuppressWarnings("unused")
    private void changeFilteringText() {
        String filteringText = ui.lineEdit.text();
        proxyModel.setFilterString(filteringText);
        if (!filteringText.equals("")) {
            ui.itemTree.expandAll();
        } else {
            ui.itemTree.collapseAll();
        }
    }

    @SuppressWarnings("unused")
    private void currentItemSelected(QModelIndex index) {
        ItemTreeElement element = proxyModel.indexToValue(index);

        try {
            if (element != null && element.getItem() != null) {
                Blueprint bp = (Blueprint) element.getItem();
                selectedBP = ItemDAO.getInstance().getBlueprintDetails(bp);
            }

            if (selectedBP != null) {
                ui.genInfo.setEnabled(true);
                ui.materialsTable.setEnabled(true);
                ui.mepePanel.setEnabled(true);
                ui.prereqs.setEnabled(true);
                ui.itemNameLabel.setText("<font size=5>" + selectedBP.getTypeName() + "</font>");

                String icon = EVE_ICONS_PATH + selectedBP.getIcon() + ".png";
                File iconFile = new File(icon);
                if (!iconFile.exists()){
                    icon = NO_ITEM_SELECTED_ICON;
                } 
                ui.iconLabel.setPixmap(mergeBlueprintBG(icon));
                ui.iconLabel.raise();

                String tagIconFile = METAGROUP_ICONS_TAG[selectedBP.getMetaGroupID()];
                ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
                ui.metaGroupIcon.raise();

                ui.batchLabel.setText(String.valueOf(selectedBP.getBatchSize()));
                ui.wasteLabel.setText(selectedBP.getWasteFactor() + "%");
                ui.peLabel.setText(printPETime(selectedBP.getResearchPETime()));
                ui.meLabel.setText(printMETime(selectedBP.getResearchMETime()));
                ui.copyLabel.setText(printCopyTime(selectedBP.getCopyTime()));
                ui.prodLabel.setText(printProdTime(selectedBP.getProductionTime()));
                ui.maxrunsLabel.setText(String.valueOf(selectedBP.getMaxRuns()));
                if (selectedBP.getTechLevel() < 2) {
                    ui.priceLabel.setText(Formater.printDouble(selectedBP.getBasePrice() * 0.9)
                            + " ISK");
                } else {
                    ui.priceLabel.setText("N/A");
                }
                if (selectedBP.getActivities().containsKey(BPActivity.INVENTION)) {
                    ui.inventionLabel.setText(Formater.printTimeCondensed(selectedBP.getInventionTime()));
                } else {
                    ui.inventionLabel.setText("N/A");
                }
                materialsTableModel.setWaste(selectedBP.getWasteFactor());
                fillActivityComboBox();
            } else {
                ui.genInfo.setEnabled(false);
                ui.materialsTable.setEnabled(false);
                ui.mepePanel.setEnabled(false);
                ui.prereqs.setEnabled(false);
            }
        } catch (PQSQLDriverNotFoundException e) {
            Errors.popSQLDriverError(this, e);
        } catch (PQUserDatabaseFileCorrupted e) {
            Errors.popUserDBCorrupt(this, e);
        } catch (PQEveDatabaseCorrupted e) {
            Errors.popEveDbCorrupted(this, e);
        }
    }

    private static QPixmap mergeBlueprintBG(String icon) {
        QSize resultSize = new QSize(64, 64);
        QImage blueprintBG = new QImage(Constants.BLUEPRINTBG_ICON);
        QImage itemIcon = new QImage(icon);
        QImage resultImage = new QImage(resultSize, QImage.Format.Format_ARGB32_Premultiplied);
        
        QPainter painter = new QPainter(resultImage);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_Source);
        painter.fillRect(resultImage.rect(), Qt.GlobalColor.transparent);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_SourceOver);
        painter.drawImage(0, 0, itemIcon);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_Multiply);
        painter.drawImage(0, 0, blueprintBG);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_DestinationOver);
        painter.fillRect(resultImage.rect(), Qt.GlobalColor.white);
        painter.end();

        return QPixmap.fromImage(resultImage);
    }

    private String printCopyTime(long baseTime) {
        if (ui.ignoreSkillsBox.isChecked()) {
            baseTime = (long) (0.75 * baseTime);
        } else {
            if (sheet != null && sheet.getSkills().containsKey(SCIENCE)) {
                baseTime = (long) (baseTime * (1.0 - 0.05 * sheet.getSkill(SCIENCE).getLevel()));
            }
        }
        return Formater.printTimeCondensed(baseTime);
    }

    private String printPETime(long baseTime) {
        if (ui.ignoreSkillsBox.isChecked()) {
            baseTime = (long) (0.75 * baseTime);
        } else {
            if (sheet != null && sheet.getSkills().containsKey(RESEARCH)) {
                baseTime = (long) (baseTime * (1.0 - 0.05 * sheet.getSkill(RESEARCH).getLevel()));
            }
        }
        return Formater.printTimeCondensed(baseTime);
    }

    private String printMETime(long baseTime) {
        if (ui.ignoreSkillsBox.isChecked()) {
            baseTime = (long) (0.75 * baseTime);
        } else {
            if (sheet != null && sheet.getSkills().containsKey(METALLURGY)) {
                baseTime = (long) (baseTime * (1.0 - 0.05 * sheet.getSkill(METALLURGY).getLevel()));
            }
        }
        return Formater.printTimeCondensed(baseTime);
    }

    private String printProdTime(long baseTime) {
        if (ui.ignoreSkillsBox.isChecked()) {
            baseTime = (long) (0.8 * baseTime);
        } else {
            if (sheet != null && sheet.getSkills().containsKey(INDUSTRY)) {
                baseTime = (long) (baseTime * (1.0 - 0.04 * sheet.getSkill(INDUSTRY).getLevel()));
            }
        }
        double pe = ui.peSpinBox.value();
        if (pe >= 0) {
            baseTime = (long) (baseTime * (1.0 - (0.2 * (pe / (1.0 + pe)))));
        } else {
            baseTime = (long) (baseTime * (1.0 - 0.1 * pe));
        }
        return Formater.printTimeCondensed(baseTime);
    }

    @SuppressWarnings("unused")
    private void setPe(Integer pe) {
        if (selectedBP != null) {
            ui.prodLabel.setText(printProdTime(selectedBP.getProductionTime()));
        }
    }

    private void fillActivityComboBox() {
        ui.activityComboBox.clear();
        if (selectedBP != null) {
            List<BPActivity> activities = new ArrayList<BPActivity>(selectedBP.getActivities()
                    .values());
            Collections.sort(activities);
            for (BPActivity activity : activities) {
                ui.activityComboBox.addItem(activity.getName(), activity.getActivityID());
            }
            // if the new selected blueprint supports the last selected activity
            // we keep it and select it
            if (selectedBP.getActivities().get(selectedActivityID) == null) {
                // else we select "Manufacturing" which is supported by all
                // blueprints
                selectedActivityID = BPActivity.MANUFACTURING;
            }
            String name = selectedBP.getActivities().get(selectedActivityID).getName();
            ui.activityLabel.setText("<i>" + name + "</i>");
            int comboIndex = ui.activityComboBox.findText(name);
            // this triggers the update of the materials table and the
            // prerequisite tree
            ui.activityComboBox.setCurrentIndex(comboIndex);
            fillActivity(comboIndex);
        }
    }

    private void fillActivity(Integer comboIndex) {
        selectedActivityID = (Integer) ui.activityComboBox.itemData(comboIndex);
        BPActivity activity = selectedBP.getActivities().get(selectedActivityID);
        ui.activityLabel.setText("<i>" + activity.getName() + "</i>");
        ui.prereqLabel.setText("Required Skills for " + activity.getName());
        if (activity != null) {
            // we have to construct a fictive item to pass it to the prereq tree
            // model
            Item dummyPrereqItem = new Item(0, "", 0, 0);
            for (PreRequisite req : activity.getPrereqs()) {
                dummyPrereqItem.getPreReqs().add(req);
            }
            prereqModel.setRoot(new ItemPrerequisiteElement(dummyPrereqItem));
            prereqModel.forceReset();
            ui.prereqTree.expandAll();
            materialsTableModel.setMaterials(activity.getMaterials());
            ui.materialsTable.resizeColumnsToContents();
        }
    }

}
