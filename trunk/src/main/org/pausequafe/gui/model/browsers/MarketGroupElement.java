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
import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.data.item.Item;
import org.pausequafe.data.item.MarketGroup;
import org.pausequafe.data.item.Skill;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QFont;

/**
 * This class implements a tree node. It's used by the TreeModel to access the data from a
 * MarketGroup instance
 * 
 * @author Gobi
 */
public class MarketGroupElement extends GroupElement {

    protected MarketGroup marketGroup;

    public MarketGroupElement(MarketGroup marketGroup) {
        super();
        this.marketGroup = marketGroup;
    }

    public int childCount() {
        return marketGroup.childCount();
    }

    public ItemTreeElement childAt(int position) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {
        ItemTreeElement child = null;
        Integer childId = marketGroup.childAt(position);

        if (!marketGroup.isItemContainer()) {
            MarketGroup group = MarketGroupDAO.getInstance().findMarketGroupById(childId);
            child = new MarketGroupElement(group);
        } else {
            Item item = ItemDAO.getInstance().findItemById(childId);
            if (Skill.class.isInstance(item)) {
                child = new SkillElement((Skill) item);
            } else {
                child = new ItemElement(item);
            }
        }

        return child;
    }

    public QFont getFont() {
        QFont font = new QFont();
        font.setBold(true);
        return font;
    }

    public String getName() {
        return marketGroup.getGroupName();
    }

    public String toString() {
        return marketGroup.getGroupName();
    }

}
