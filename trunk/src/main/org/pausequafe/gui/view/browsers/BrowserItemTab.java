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

package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserItemTab extends AbstractBrowserTab {

    // //////////////////
    // private fields //
    // //////////////////
    private QTreeView prereqTree;
    private Ui_BrowserItemTab ui;

    // //////////////////
    // constructors //
    // //////////////////
    public BrowserItemTab(int marketGroupID) {
        super(marketGroupID);
    }

    public BrowserItemTab(QWidget parent, int marketGroupID) {
        super(parent, marketGroupID);
    }

    // ////////////////
    // widget setup //
    // ////////////////

    protected void setupUi() {
        ui = new Ui_BrowserItemTab();
        ui.setupUi(this);

        // we have to do designer work here
        // because this bastard can't create
        // a custom ItemTreeView
        ui.itemTree.dispose();
        ui.itemTree = new ItemTreeView(ui.rightArea);
        itemTree=ui.itemTree;
        itemTree.setObjectName("itemTree");
        QSizePolicy sizePolicy12 = new QSizePolicy(QSizePolicy.Policy.Preferred, 
                                                   QSizePolicy.Policy.Expanding);
        sizePolicy12.setHorizontalStretch((byte)5);
        sizePolicy12.setVerticalStretch((byte)0);
        sizePolicy12.setHeightForWidth(itemTree.sizePolicy().hasHeightForWidth());
        itemTree.setSizePolicy(sizePolicy12);
        itemTree.setMinimumSize(new QSize(0, 200));
        itemTree.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);
        itemTree.setHorizontalScrollBarPolicy(
                com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAlwaysOff);
        itemTree.setProperty("showDropIndicator", false);
        itemTree.setDragEnabled(true);
        itemTree.setDragDropMode(com.trolltech.qt.gui.QAbstractItemView.DragDropMode.DragOnly);
        itemTree.setIndentation(20);
        itemTree.setUniformRowHeights(true);
        itemTree.setSortingEnabled(true);
        itemTree.setHeaderHidden(true);
        ui.verticalLayout.addWidget(itemTree);
        // end designer work
        
        itemDescription = ui.itemDescription;
        prereqTree = ui.prereqTree;
        filterLineEdit = ui.lineEdit;

        ui.itemDescription.setAcceptRichText(true);
        ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
        ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
        ui.attributesTable.verticalHeader().setVisible(false);

    }

    protected void initConnection() {
        super.initConnection();
        ui.sortComboBox.currentIndexChanged.connect(this, "sort()");
        ui.tech13CheckBox.toggled.connect(proxyModel, "setTech1Shown(boolean)");
        ui.tech13CheckBox.toggled.connect(proxyModel, "setTech3Shown(boolean)");
        ui.namedCheckBox.toggled.connect(proxyModel, "setNamedShown(boolean)");
        ui.tech2CheckBox.toggled.connect(proxyModel, "setTech2Shown(boolean)");
        ui.factionCheckBox.toggled.connect(proxyModel, "setFactionShown(boolean)");
        ui.factionCheckBox.toggled.connect(proxyModel, "setStorylineShown(boolean)");
        ui.deadspaceCheckBox.toggled.connect(proxyModel, "setDeadspaceShown(boolean)");
        ui.officerCheckBox.toggled.connect(proxyModel, "setOfficerShown(boolean)");

        QAction enterFilterArea = new QAction(this);
        enterFilterArea.triggered.connect(filterLineEdit, "setFocus()");
        enterFilterArea.setShortcut("Ctrl+F");
    }

    // /////////
    // slots //
    // /////////
    protected void sort() {
        int sortMode = ui.sortComboBox.currentIndex();
        switch (sortMode) {
        case 0:
            proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
            break;
        case 1:
            proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_META_LEVEL);
            break;
        default:
            proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
            break;
        }
    }

    protected void currentItemSelected(QModelIndex index) {
        super.currentItemSelected(index);

        if (currentItemSelected != null) {
            ui.prereqFrame.setEnabled(true);
            ui.descriptionFrame.setEnabled(true);
            ui.attributesTable.setEnabled(true);
            ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");

            String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
            File iconFile = new File(icon);
            if (!iconFile.exists())
                icon = Constants.NO_ITEM_SELECTED_ICON;
            ui.iconLabel.setPixmap(new QPixmap(icon));

            String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
            ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
            ui.metaGroupIcon.raise();

            itemDescription.setText(currentItemSelected.getDescription());

            ItemTreeElement root = new ItemPrerequisiteElement(currentItemSelected);
            prereqModel = new ItemTreeModel(root);
            prereqModel.setSheet(sheet);
            prereqTree.setModel(prereqModel);
            prereqTree.expandAll();

            AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
            ui.attributesTable.setModel(tableModel);
            ui.attributesTable.resizeColumnsToContents();
        }
        ui.attributesTable.resizeRowsToContents();
    }

}
