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

import org.pausequafe.misc.util.SQLConstants;

/**
 * A specific Item which has 3 additional attributes.
 * <ul>
 * <li>the rank (or time multiplier)
 * <li>a primary attribute (used for training speed calculation)
 * <li>a secondary attribute (used for training speed calculation)
 * </ul>
 * 
 * @author diabeteman
 * 
 */
public class Skill extends Item {

    // //////////////////
    // private fields //
    // //////////////////
    private int rank;
    private String primaryAttribute;

    private String secondaryAttribute;

    // ///////////////
    // constructor //
    // ///////////////
    public Skill(int typeID, String typeName) {
        super(typeID, typeName, 0, SQLConstants.SKILL_CATID);
    }

    // //////////////////
    // public methods //
    // //////////////////

    // ///////////
    // getters //
    // ///////////
    public int getRank() {
        return rank;
    }

    public void setPrimaryAttribute(String primaryAttribute) {
        this.primaryAttribute = primaryAttribute;
    }

    public void setSecondaryAttribute(String secondaryAttribute) {
        this.secondaryAttribute = secondaryAttribute;
    }

    // ///////////
    // setters //
    // ///////////
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPrimaryAttribute() {
        return this.primaryAttribute;
    }

    public String getSecondaryAttribute() {
        return this.secondaryAttribute;
    }

}
