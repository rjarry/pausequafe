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

package org.pausequafe.data.character;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.item.SkillQueue;

public class MonitoredCharacter {

    // //////////
    // fields //
    // //////////
    private APIData api;
    private CharacterSheet sheet;
    private List<SkillPlan> skillPlanList;

    private SkillInTraining inTraining;
    private SkillQueue queue;

    private String imageLocation;

    // ///////////////
    // constructor //
    // ///////////////
    public MonitoredCharacter(APIData api) {
        super();
        this.api = api;
        this.skillPlanList = new ArrayList<SkillPlan>();
    }

    // //////////////////
    // public methods //
    // //////////////////
    @Override
    public String toString() {
        return api.getCharacterName();
    }

    public int skillPlanCount() {
        return skillPlanList.size();
    }

    public void addSkillPlan(int index, SkillPlan plan) {
        skillPlanList.add(index, plan);
    }

    public void removePlan(int index) {
        skillPlanList.remove(index);
    }

    // ///////////
    // getters //
    // ///////////
    public APIData getApi() {
        return api;
    }

    public CharacterSheet getSheet() {
        return sheet;
    }

    public SkillPlan getSkillPlanAt(int index) {
        return skillPlanList.get(index);
    }

    public SkillInTraining getInTraining() {
        return inTraining;
    }

    public SkillQueue getQueue() {
        return queue;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    // ///////////
    // setters //
    // ///////////
    public void setApi(APIData api) {
        this.api = api;
    }

    public void setSheet(CharacterSheet sheet) {
        this.sheet = sheet;
    }

    public void setSkillPlanAt(int index, SkillPlan newPlan) {
        skillPlanList.set(index, newPlan);
    }

    public void setInTraining(SkillInTraining inTraining) {
        this.inTraining = inTraining;
    }

    public void setQueue(SkillQueue queue) {
        this.queue = queue;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

}
