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

package org.pausequafe.data.business;

public class CharacterSkill {

    // //////////////////
    // private fields //
    // //////////////////
    private int typeID = 0;
    private int skillPoints = 0;
    private int level = 0;

    // ////////////////
    // constructors //
    // ////////////////
    public CharacterSkill() {
    }

    // ///////////
    // getters //
    // ///////////
    public int getTypeID() {
        return typeID;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getLevel() {
        return level;
    }

    // ///////////
    // setters //
    // ///////////
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // //////////////////
    // public methods //
    // //////////////////

    public String toString() {
        return "typeID=" + typeID + " skillPoints=" + skillPoints + " level=" + level;
    }
}
