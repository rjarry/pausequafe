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

#ifndef SKILLINTRAINING_H
#define SKILLINTRAINING_H

#include "data/character/SkillInQueue.h"
#include "data/APIObject.h"

class SkillInTraining : public APIObject,
                        public SkillInQueue

{
private:
    uint currentTime;
    uint currentTQTime;
    uint cachedUntil;
    bool cached;

    unsigned calcRank();

public:
    SkillInTraining();

    APIObject::Function function();

    QString toString();

    /**
     * Calculates the training speed for this skill in SP per second
     *
     * @return the training speed
     */
    double calcTrainingSpeed();
    /**
     * Calculates the current SP at the method's calling time (GMT)
     *
     * @return the current SP
     */
    uint calcCurrentSP();
    /**
     * Calculates SP in this skill when started skilling current level
     *
     * @return SP when started skilling current level
     */
    uint calcLevelStartSP();
    /**
     * Calculates the completion by a number from 0.0 to 1.0
     *
     * @return the completion
     */
    double calcCompletion();

    // getters //
    uint getCurrentTime() { return currentTime; }
    uint getCurrentTQTime() { return currentTQTime; }
    uint getCachedUntil() { return cachedUntil; }
    bool isCached() { return cached; }

    // setters //
    void setCurrentTime(uint currentTime) { this->currentTime = currentTime; }
    void setCurrentTQTime(uint currentTQTime) { this->currentTQTime = currentTQTime; }
    void setCachedUntil(uint cachedUntil) { this->cachedUntil = cachedUntil; }
    void setCached(bool cached) { this->cached = cached; }

};

#endif // SKILLINTRAINING_H
