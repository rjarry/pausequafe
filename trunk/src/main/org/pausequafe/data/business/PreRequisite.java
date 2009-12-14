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

package org.pausequafe.data.business;

public class PreRequisite {

    // //////////////////
    // private fields //
    // //////////////////
    private int typeID = -1;
    private int requiredLevel = -1;

    // ////////////////
    // constructors //
    // ////////////////
    public PreRequisite() {

    }

    public PreRequisite(int typeID, int requiredLevel) {
        this.typeID = typeID;
        this.requiredLevel = requiredLevel;
    }

    // //////////////////
    // public methods //
    // //////////////////

    // ///////////
    // getters //
    // ///////////
    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    // ///////////
    // setters //
    // ///////////
    public void setRequiredLevel(int levelRequired) {
        this.requiredLevel = levelRequired;
    }

    public int getTypeID() {
        return typeID;
    }

}
