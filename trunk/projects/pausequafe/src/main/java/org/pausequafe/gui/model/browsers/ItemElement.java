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
import org.pausequafe.data.item.Item;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This class implements a tree leaf. It's used by the TreeModel to access the data from an Item
 * instance
 * 
 * @author Gobi
 */
public class ItemElement implements ItemTreeElement {

    protected Item item;

    public ItemElement(Item item) {
        super();
        this.item = item;
    }

    public QFont getFont() {
        return null;
    }

    public QIcon getIcon() {
        return getIcon(null);
    }

    public QIcon getIcon(CharacterSheet sheet) {
        return new QIcon(Constants.METAGROUP_ICONS_SMALL[item.getMetaGroupID()]);
    }

    public String getName() {
        return item.getTypeName();
    }

    public String getTooltip() {
        return null;
    }

    public Item getItem() {
        return item;
    }

    public ItemTreeElement childAt(int position) throws PQException {
        throw new PQException("Leafs don't have children");
    }

    public int childCount() {
        return 0;
    }

}
