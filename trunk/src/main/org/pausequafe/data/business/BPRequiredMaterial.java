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

import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.util.Formater;

public class BPRequiredMaterial {

    // /////////
    // FIELDS //
    // /////////
    private int typeID;
    private int quantity;
    private double damagePerJob = 1.0;

    // //////////////
    // CONSTRUCTOR //
    // //////////////
    public BPRequiredMaterial(int typeID, int quantity) {
        this.typeID = typeID;
        this.quantity = quantity;
    }

    public BPRequiredMaterial(int typeID, int quantity, double damagePerJob) {
        this.typeID = typeID;
        this.quantity = quantity;
        this.damagePerJob = damagePerJob;
    }

    public BPRequiredMaterial(BPRequiredMaterial mat) {
        this.typeID = mat.typeID;
        this.quantity = mat.quantity;
        this.damagePerJob = mat.damagePerJob;
    }

    // ////////////
    // ACCESSORS //
    // ////////////
    public int getTypeID() {
        return typeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDamagePerJob() {
        return damagePerJob;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDamagePerJob(double damagePerJob) {
        this.damagePerJob = damagePerJob;
    }

    @Override
    public String toString() {
        String name;
        try {
            name = ItemDAO.getInstance().findItemById(typeID).getTypeName();
        } catch (Exception e) {
            name = String.valueOf(typeID);
        }

        return name + " x" + quantity + " (" + Formater.printPercent(damagePerJob) + ")";
    }

}
