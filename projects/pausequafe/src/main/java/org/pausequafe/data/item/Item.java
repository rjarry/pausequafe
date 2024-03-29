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

package org.pausequafe.data.item;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.character.CharacterSheet;

/**
 * This is a generic class describing all Eve items.<br>
 * <br>
 * There's inheritage to add specific attributes to the different categories of items :
 * 
 * <ul>
 * <li>Skills
 * <li>Modules
 * <li>Ships
 * </ul>
 * 
 * @author diabeteman
 */
public class Item {

    // ////////////////////
    // protected fields //
    // ////////////////////
    protected int typeID;
    protected String typeName;
    protected int metaGroupID;
    protected int metaLevel;
    protected int categoryID;

    protected List<PreRequisite> preReqs = null;

    // ///////////////
    // constructor //
    // ///////////////
    public Item() {
        preReqs = new ArrayList<PreRequisite>();
    }

    public Item(int typeID, String typeName, int metaGroupID, int categoryID) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.metaGroupID = metaGroupID;
        this.categoryID = categoryID;
        preReqs = new ArrayList<PreRequisite>();
    }

    public Item(Item item) {
        this.typeID = item.typeID;
        this.typeName = item.typeName;
        this.metaGroupID = item.metaGroupID;
        this.metaLevel = item.metaLevel;
        this.categoryID = item.categoryID;
        preReqs = item.getPreReqs();
    }

    // //////////////////
    // public methods //
    // //////////////////
    public void addPreRequisite(PreRequisite preReq) {
        preReqs.add(preReq);
    }

    public int getRemainingTrainingTime(CharacterSheet sheet) {
        // TODO implement to sort by training time
        return 0;
    }

    @Override
    public String toString() {
        String result = "";
        result += typeID + " : " + typeName + " : ";
        return result;
    }

    // ///////////
    // getters //
    // ///////////
    public int getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<PreRequisite> getPreReqs() {
        return preReqs;
    }

    public int getMetaGroupID() {
        return metaGroupID;
    }

    public int getMetaLevel() {
        return metaLevel;
    }

    // ///////////
    // setters //
    // ///////////
    public void setMetaGroupID(int metaGroupID) {
        this.metaGroupID = metaGroupID;
    }

    public void setMetaLevel(int metaLevel) {
        this.metaLevel = metaLevel;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

}
