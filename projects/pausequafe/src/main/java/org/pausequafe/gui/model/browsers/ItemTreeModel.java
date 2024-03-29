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

package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QTreeModel;

/**
 * This class implements a tree model that can be used to show items sorted by groups.
 * 
 * @author Gobi
 */
public class ItemTreeModel extends QTreeModel {

    private ItemTreeElement root = null;

    private CharacterSheet sheet = null;

    public ItemTreeModel() {
        super();
    }

    public ItemTreeModel(ItemTreeElement root) {
        super();
        this.root = root;
    }

    public void setSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        this.dataChanged.emit(null, null);
    }

    public CharacterSheet getSheet() {
        return sheet;
    }

    public ItemTreeElement getRoot() {
        return root;
    }

    public void setRoot(ItemTreeElement root) {
        this.root = root;
        this.dataChanged.emit(null, null);
    }

    @Override
    public ItemTreeElement child(Object parent, int index) {
        ItemTreeElement treeElement;
        if (parent == null) {
            treeElement = root;
        } else {
            treeElement = (ItemTreeElement) parent;
        }

        ItemTreeElement child = null;
        try {
            child = treeElement.childAt(index);
        } catch (PQException e) {
            e.printStackTrace();
        }
        return child;

    }

    @Override
    public int childCount(Object parent) {
        ItemTreeElement treeElement;
        if (parent == null) {
            treeElement = root;
            if (root == null) {
                return 0;
            }
        } else {
            treeElement = (ItemTreeElement) parent;
        }

        return treeElement.childCount();
    }

    public void forceReset() {
        this.reset();
    }

    @Override
    public String text(Object value) {
        String s = ((ItemTreeElement) value).getName();
        return s;
    }

    @Override
    public Object data(Object value, int role) {
        ItemTreeElement elt = (ItemTreeElement) value;

        Object result = null;
        switch (role) {
        // text to display
        case ItemDataRole.DisplayRole:
            result = elt.getName();
            break;
        // display font
        case ItemDataRole.FontRole:
            result = elt.getFont();
            break;
        // the icon before the skills
        case ItemDataRole.DecorationRole:
            result = elt.getIcon(sheet);
            break;
        // tool tips on the skills
        case ItemDataRole.ToolTipRole:
            result = elt.getTooltip();
            break;

        default:
            result = null;
        }

        return result;
    }
}
