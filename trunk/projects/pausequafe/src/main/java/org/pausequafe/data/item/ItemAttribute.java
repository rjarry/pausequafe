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

public class ItemAttribute {

    private int attributeID;
    private String attributeName;
    private String attributeCategory;
    private double value;
    private String unit;
    private int unitID;

    public ItemAttribute(int attributeID, String attributeName, String attributeCategory,
            double value, String unit, int unitID) {
        this.attributeID = attributeID;
        this.attributeName = attributeName;
        this.attributeCategory = attributeCategory;
        this.unit = unit;
        this.value = value;
        this.setUnitID(unitID);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeCategory() {
        return attributeCategory;
    }

    public double getValue() {
        return value;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setAttributeCategory(String attributeCategory) {
        this.attributeCategory = attributeCategory;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setAttributeID(int attributeID) {
        this.attributeID = attributeID;
    }

    public int getAttributeID() {
        return attributeID;
    }
}
