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

package org.pausequafe.gui.model.browsers;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.item.Item;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class ItemPrerequisiteElement extends GroupElement {

    private Item item;
    private int requiredLevel = -1;
    private boolean isRoot = true;

    public ItemPrerequisiteElement(Item toSkill) {
        super();
        this.item = toSkill;
    }

    private ItemPrerequisiteElement(Item toSkill, int requiredLevel, boolean isRoot) {
        super();
        this.item = toSkill;
        this.requiredLevel = requiredLevel;
        this.isRoot = isRoot;
    }

    public ItemTreeElement childAt(int position) throws PQException {
        ItemTreeElement child = null;
        int childId = item.getPreReqs().get(position).getTypeID();
        int childReqLvl = item.getPreReqs().get(position).getRequiredLevel();

        Item childItem = ItemDAO.getInstance().findItemById(childId);

        if (requiredLevel == -1) {
            child = new ItemPrerequisiteElement(childItem, childReqLvl, true);
        } else {
            child = new ItemPrerequisiteElement(childItem, childReqLvl, false);
        }

        return child;
    }

    public int childCount() {
        return item.getPreReqs().size();
    }

    public QFont getFont() {
        QFont font = new QFont();
        if (isRoot)
            font.setBold(true);
        else
            font.setItalic(true);

        return font;
    }

    public QIcon getIcon(CharacterSheet sheet) {
        QIcon result;
        if (sheet != null && sheet.getSkills().containsKey(item.getTypeID())) {
            if (sheet.getSkill(item.getTypeID()).getLevel() >= requiredLevel) {
                result = new QIcon(Constants.SKILL_OK);
            } else {
                result = new QIcon(Constants.SKILL_KNOWN);
            }
        } else {
            result = new QIcon(Constants.SKILL_NOT_KNOWN);
        }
        return result;
    }

    public String getName() {
        return item.getTypeName() + " " + Formater.printLevel(requiredLevel);
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return getName();
    }

}
