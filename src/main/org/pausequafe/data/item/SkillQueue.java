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

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.misc.util.Formater;

public class SkillQueue {

    // //////////////////
    // private fields //
    // //////////////////

    private long currentTime;
    private long cachedUntil;
    private boolean cached;
    private List<SkillInQueue> skillList;

    // ///////////////
    // constructor //
    // ///////////////
    public SkillQueue() {
        skillList = new ArrayList<SkillInQueue>();
    }

    // //////////////////
    // public methods //
    // //////////////////
    public long getTotalTrainingTime() {
        long time = 0;
        for (SkillInQueue s : skillList) {
            time += s.calculateTrainingTime();
        }
        return time;
    }

    // ///////////////////
    // private methods //
    // ///////////////////

    // ///////////
    // getters //
    // ///////////
    public long getCurrentTime() {
        return currentTime;
    }

    public long getCachedUntil() {
        return cachedUntil;
    }

    public boolean isCached() {
        return cached;
    }

    public List<SkillInQueue> getSkillList() {
        return skillList;
    }

    // ///////////
    // setters //
    // ///////////

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setCachedUntil(long cachedUntil) {
        this.cachedUntil = cachedUntil;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public void setSkillList(List<SkillInQueue> skillList) {
        this.skillList = skillList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (skillList.size() != 0) {
            sb.append(skillList.size() + " skill");
            if (skillList.size() > 1)
                sb.append("s");
            sb.append(" <i>(" + Formater.printTime(getTotalTrainingTime()) + ")</i>");
            int i = 1;
            for (SkillInQueue skill : skillList) {
                sb.append("<br>" + i + ": " + skill);
                i++;
            }
        } else {
            sb.append("No Skill in Queue");
        }
        return sb.toString();
    }
}