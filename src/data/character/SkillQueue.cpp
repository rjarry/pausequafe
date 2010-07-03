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
 * aulong with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#include "data/character/SkillQueue.h"


///////////////////
/// constructor ///
///////////////////
SkillQueue::SkillQueue() :
        skillList(QList<SkillInQueue>())
{
}

//////////////////////
/// public methods ///
//////////////////////
APIObject::Function SkillQueue::function() {
    return APIObject::SKILL_QUEUE;
}

ulong SkillQueue::getTotalTrainingTime() {
    ulong time = 0;
    foreach (SkillInQueue s, skillList) {
        time += s.trainingTime();
    }
    return time;
}

///////////////
/// getters ///
///////////////
ulong SkillQueue::getCurrentTime() {
    return currentTime;
}

ulong SkillQueue::getCachedUntil() {
    return cachedUntil;
}

bool SkillQueue::isCached() {
    return cached;
}

QList<SkillInQueue> & SkillQueue::getSkillList() {
    return skillList;
}

///////////////
/// setters ///
///////////////

void SkillQueue::setCurrentTime(ulong currentTime) {
    this->currentTime = currentTime;
}

void SkillQueue::setCachedUntil(ulong cachedUntil) {
    this->cachedUntil = cachedUntil;
}

void SkillQueue::setCached(bool cached) {
    this->cached = cached;
}

void SkillQueue::setSkillList(QList<SkillInQueue> skillList) {
    this->skillList = skillList;
}
