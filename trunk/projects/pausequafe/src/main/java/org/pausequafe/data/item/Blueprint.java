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

import java.util.HashMap;
import java.util.Map;

public class Blueprint extends Item {

    // /////////
    // FIELDS //
    // /////////

    protected Map<Integer, BPActivity> activities = new HashMap<Integer, BPActivity>();

    // //////////////
    // CONSTRUCTOR //
    // //////////////
    public Blueprint(int typeID, String typeName, int metaGroupID, int categoryID) {
        super(typeID, typeName, metaGroupID, categoryID);
    }

    // ////////////
    // ACCESSORS //
    // ////////////
    public Map<Integer, BPActivity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Integer, BPActivity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(typeName + " :\n");
        for (BPActivity act : activities.values()) {
            sb.append(act.toString());
        }
        sb.append('\n');
        return sb.toString();
    }

}
