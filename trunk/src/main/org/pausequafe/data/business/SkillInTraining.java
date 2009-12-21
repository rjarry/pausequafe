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

import java.util.Calendar;
import java.util.TimeZone;

import org.pausequafe.core.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

public class SkillInTraining extends SkillInQueue {

    // //////////////////
    // private fields //
    // //////////////////
    private long currentTime;
    private long currentTQTime;
    private int skillInTraining;
    private long cachedUntil;
    private boolean cached;

    // ///////////////
    // constructor //
    // ///////////////
    public SkillInTraining() {
    }

    // //////////////////
    // public methods //
    // //////////////////
    /**
     * calculates the training speed for this skill in SP per milliseconds
     * 
     * @return the training speed
     */
    public double calculateTrainingSpeed() {
        return ((double) (endSP - startSP) / (double) (endTime - startTime));
    }

    /**
     * calculates the current SP at the method's calling time (GMT)
     * 
     * @return the current SP
     */
    public int calculateCurrentSP() {
        long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
        return (int) (Math.round((now - startTime) * calculateTrainingSpeed()) + startSP);
    }

    /**
     * calculate SP in this skill when started skilling current level
     * 
     * @return SP when started skilling current level
     */
    public int calculateLevelStartSP() {
        return Constants.SKILL_LEVEL_REQS[level - 1] * calculateRank();
    }

    /**
     * Calculates the completion by a number from 0.0 to 1.0
     * 
     * @return the completion
     */
    public double calculateCompletion() {
        double completion = (double) (calculateCurrentSP() - calculateLevelStartSP())
                / (double) (endSP - calculateLevelStartSP());

        return Math.min(1, completion);
    }

    /**
     * Calculate the training time.
     * 
     * @return the training time for this skill in training
     */
    public long calculateTrainingTime() {
        return endTime - startTime;
    }

    public String toString() {
        String print = "";

        try {
            print += ItemDAO.getInstance().findItemById(typeID).getTypeName();
        } catch (PQException e) {
            print += typeID;
        }

        print += " training to level " + level;
        print += "\nCurrent SP : " + calculateCurrentSP();
        print += "\nDone : " + calculateCompletion() * 100 + " %";
        // System.out.println(trainingSpeed()*1000*60*60 + " SP / h");

        return print;
    }

    // ///////////////////
    // private methods //
    // ///////////////////
    private int calculateRank() {
        return (int) Math.round((double) endSP / (double) Constants.SKILL_LEVEL_REQS[level]);
    }

    // ///////////
    // getters //
    // ///////////
    public long getCurrentTime() {
        return currentTime;
    }

    public long getCurrentTQTime() {
        return currentTQTime;
    }

    public int skillInTraining() {
        return skillInTraining;
    }

    public long getCachedUntil() {
        return cachedUntil;
    }

    public boolean isCached() {
        return cached;
    }

    // ///////////
    // setters //
    // ///////////
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setCurrentTQTime(long currentTQTime) {
        this.currentTQTime = currentTQTime;
    }

    public void setCachedUntil(long cachedUntil) {
        this.cachedUntil = cachedUntil;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public void setSkillInTraining(int skillCount) {
        this.skillInTraining = skillCount;

    }

}
