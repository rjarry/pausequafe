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

#include "data/character/SkillInQueue.h"

SkillInQueue::SkillInQueue()
{
}

ulong SkillInQueue::trainingTime() {
    return endTime - startTime;
}




// getters //
ulong SkillInQueue::getEndTime() {
    return endTime;
}
ulong SkillInQueue::getStartTime() {
    return startTime;
}
uint SkillInQueue::getTypeID() {
    return typeID;
}
uint SkillInQueue::getStartSP() {
    return startSP;
}
uint SkillInQueue::getEndSP() {
    return endSP;
}
uint SkillInQueue::getLevel() {
    return level;
}
uint SkillInQueue::getQueuePosition() {
    return queuePosition;
}

// setters //
void SkillInQueue::setEndTime(ulong endTime) {
    this->endTime = endTime;
}
void SkillInQueue::setStartTime(ulong startTime) {
    this->startTime = startTime;
}
void SkillInQueue::setTypeID(uint typeID) {
    this->typeID = typeID;
}
void SkillInQueue::setStartSP(uint startSP) {
    this->startSP = startSP;
}
void SkillInQueue::setEndSP(uint endSP) {
    this->endSP = endSP;
}
void SkillInQueue::setLevel(uint level) {
    this->level = level;
}
void SkillInQueue::setQueuePosition(uint pos) {
    this->queuePosition = pos;
}
