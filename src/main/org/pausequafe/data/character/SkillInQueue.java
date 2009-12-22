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

package org.pausequafe.data.character;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.misc.util.Formater;

public class SkillInQueue {

    // //////////////////
    // private fields //
    // //////////////////
    protected long endTime;
    protected long startTime;
    protected int typeID;
    protected int startSP;
    protected int endSP;
    protected int level;
    private int queuePosition;

    public long calculateTrainingTime() {
        return endTime - startTime;
    }

    // ///////////
    // getters //
    // ///////////
    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTypeID() {
        return typeID;
    }

    public int getStartSP() {
        return startSP;
    }

    public int getEndSP() {
        return endSP;
    }

    public int getLevel() {
        return level;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    // ///////////
    // getters //
    // ///////////
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setStartSP(int startSP) {
        this.startSP = startSP;
    }

    public void setEndSP(int endSP) {
        this.endSP = endSP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    @Override
    public String toString() {
        String typeName;
        try {
            typeName = ItemDAO.getInstance().findItemById(typeID).getTypeName();
        } catch (Exception e) {
            typeName = typeID + "";
        }
        String level = Formater.printLevel(this.level);
        String trainingTime = Formater.printTime(calculateTrainingTime());

        return typeName + " " + level + " <i>(" + trainingTime + ")</i>";
    }

}
