/*****************************************************************************
 * This file is part of Pause Quafe.                                         *
 *                                                                           *
 * Pause Quafe - An Eve-Online(TM) character assistance application          *
 * Copyright (c) 2009  diabeteman & Kios Askoner                             *
 *                                                                           *
 * Pause Quafe is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafe is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#include "MonitoredCharacter.h"

MonitoredCharacter::MonitoredCharacter(APIData data) :
        api(data),
        skillPlanList(QList<SkillPlan*>())
{
}


/////////////
// getters //
/////////////
APIData & MonitoredCharacter::getApi() {
    return api;
}

CharacterSheet & MonitoredCharacter::getSheet() {
    return sheet;
}

SkillPlan* MonitoredCharacter::getSkillPlanAt(unsigned index) {
    return skillPlanList[index];
}

SkillInTraining & MonitoredCharacter::getInTraining() {
    return inTraining;
}

SkillQueue & MonitoredCharacter::getQueue() {
    return queue;
}

QString MonitoredCharacter::getImageLocation() {
    return imageLocation;
}

/////////////
// setters //
/////////////
void MonitoredCharacter::setApi(APIData api) {
    this->api = api;
}

void MonitoredCharacter::setSheet(CharacterSheet sheet) {
    this->sheet = sheet;
}

void MonitoredCharacter::setSkillPlanAt(unsigned index, SkillPlan* newPlan) {
    skillPlanList.insert(index, newPlan);
}

void MonitoredCharacter::setInTraining(SkillInTraining inTraining) {
    this->inTraining = inTraining;
}

void MonitoredCharacter::setQueue(SkillQueue queue) {
    this->queue = queue;
}

void MonitoredCharacter::setImageLocation(QString imageLocation) {
    this->imageLocation = imageLocation;
}
