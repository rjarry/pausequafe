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

import java.io.Serializable;

@SuppressWarnings("serial")
public class SkillPlan implements Serializable {

    public final static int NOID = -1;
    public final static String NONAME = "No name";

    // //////////
    // fields //
    // //////////
    int characterID;
    int id;
    int index;
    String name;

    // ///////////////
    // constructor //
    // ///////////////
    public SkillPlan(int characterID, int id, int index, String name) {
        super();
        this.characterID = characterID;
        this.id = id;
        this.index = index;
        this.name = name;
    }

    // //////////////////
    // public methods //
    // //////////////////
    @Override
    public String toString() {
        return name;
    }

    // ///////////
    // getters //
    // ///////////
    public int getCharacterID() {
        return characterID;
    }

    public int getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    // ///////////
    // setters //
    // ///////////
    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

}
