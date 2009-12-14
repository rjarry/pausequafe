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

public class AttributeEnhancer {

    // //////////////////
    // private fields //
    // //////////////////
    private String attribute = "";
    private String augmentatorName = "";
    private int augmentatorValue = 0;

    // ////////////////
    // constructors //
    // ////////////////
    public AttributeEnhancer() {
    }

    // ///////////
    // getters //
    // ///////////
    public String getAttribute() {
        return attribute;
    }

    public String getAugmentatorName() {
        return augmentatorName;
    }

    public int getAugmentatorValue() {
        return augmentatorValue;
    }

    // ///////////
    // setters //
    // ///////////
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAugmentatorName(String augmentatorName) {
        this.augmentatorName = augmentatorName;
    }

    public void setAugmentatorValue(int augmentatorValue) {
        this.augmentatorValue = augmentatorValue;
    }

}
