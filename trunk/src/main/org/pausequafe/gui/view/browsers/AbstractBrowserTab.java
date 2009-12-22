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

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.item.ItemDetailed;
import org.pausequafe.data.item.MarketGroup;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.gui.model.browsers.MarketGroupElement;
import org.pausequafe.gui.view.misc.Errors;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public abstract class AbstractBrowserTab extends QWidget {

    protected ItemDetailed currentItemSelected;
    protected QModelIndex currentIndexSelected;
    protected CharacterSheet sheet;

    protected ItemTreeModel prereqModel;
    protected ItemTreeSortFilterProxyModel proxyModel;

    protected QTreeView itemTree;
    protected QTextBrowser itemDescription;

    protected QLineEdit filterLineEdit;

    private QTimer delayBeforeUpdtingFilter;

    // ////////////////
    // constructors //
    // ////////////////
    public AbstractBrowserTab(int marketGroupID) {
        this(null, marketGroupID);
    }

    public AbstractBrowserTab(QWidget parent, int marketGroupID) {
        super(parent);
        delayBeforeUpdtingFilter = new QTimer();
        delayBeforeUpdtingFilter.setInterval(200);
        delayBeforeUpdtingFilter.setSingleShot(true);

        setupUi();
        initModels(marketGroupID);
        initConnection();
    }

    // /////////////////////////////
    // protected (to be overiden) //
    // /////////////////////////////
    public void setSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        proxyModel.setSheet(sheet);
        if (prereqModel != null) {
            prereqModel.setSheet(sheet);
        }
    }

    protected abstract void setupUi();

    protected void initModels(int marketGroupID) {
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
        proxyModel = new ItemTreeSortFilterProxyModel();
        ItemTreeElement root = new MarketGroupElement(group);
        ItemTreeModel browserTreeModel = new ItemTreeModel(root);
        proxyModel.setSourceModel(browserTreeModel);
        itemTree.setModel(proxyModel);
        proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_META_LEVEL);
        sort();
    }

    protected void initConnection() {
        itemTree.selectionModel().currentChanged.connect(this, "currentItemSelected(QModelIndex)");
        filterLineEdit.textChanged.connect(delayBeforeUpdtingFilter, "start()");
        delayBeforeUpdtingFilter.timeout.connect(this, "changeFilteringText()");
    }

    // /////////
    // slots //
    // /////////
    protected abstract void sort();

    protected void changeFilteringText() {
        String filteringText = filterLineEdit.text();
        proxyModel.setFilterString(filteringText);
        if (!filteringText.equals("")) {
            itemTree.expandAll();
        } else {
            itemTree.collapseAll();
        }
    }

    protected void currentItemSelected(QModelIndex index) {
        currentIndexSelected = index;
        ItemTreeElement element = proxyModel.indexToValue(index);

        if (element != null && element.getItem() != null) {
            try {
                currentItemSelected = ItemDAO.getInstance().getItemDetails(element.getItem());
            } catch (PQSQLDriverNotFoundException e) {
                Errors.popSQLDriverError(this, e);
            } catch (PQUserDatabaseFileCorrupted e) {
                Errors.popUserDBCorrupt(this, e);
            } catch (PQEveDatabaseCorrupted e) {
                Errors.popEveDbCorrupted(this, e);
            }
        }
    }



}
