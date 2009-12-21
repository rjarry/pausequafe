/*****************************************************************************
 * Pause Quaf� - An Eve-Online� character assistance application              *
 * Copyright � 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quaf�.                                         *
 *                                                                           *
 * Pause Quaf� is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quaf� is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quaf�.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This is the abstract class used as the base class for every object referenced by a TreeModel
 * 
 * @author Gobi
 */
public interface ItemTreeElement {

    public int childCount();

    public ItemTreeElement childAt(int position) throws PQException;

    public String getName();

    public QFont getFont();

    public String getTooltip();

    public QIcon getIcon();

    public QIcon getIcon(CharacterSheet sheet);

    public Item getItem();

}
