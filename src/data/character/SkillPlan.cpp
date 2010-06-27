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

#include "data/character/SkillPlan.h"


///////////////////
/// constructor ///
///////////////////
SkillPlan::SkillPlan(uint characterID, int id, int index, QString name) :
        characterID(characterID),
        id(id),
        index(index),
        name(name)
{
}


///////////////
/// getters ///
///////////////
uint SkillPlan::getCharacterID() {
    return characterID;
}

int SkillPlan::getId() {
    return id;
}

int SkillPlan::getIndex() {
    return index;
}

QString SkillPlan::getName() {
    return name;
}

///////////////
/// setters ///
///////////////
void SkillPlan::setCharacterID(uint characterID) {
    this->characterID = characterID;
}

void SkillPlan::setId(int id) {
    this->id = id;
}

void SkillPlan::setIndex(int index) {
    this->index = index;
}

void SkillPlan::setName(QString name) {
    this->name = name;
}
