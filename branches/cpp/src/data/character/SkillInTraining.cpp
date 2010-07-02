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

#include "data/character/SkillInTraining.h"

#include <QDateTime>

#include "misc/util/Constants.h"
#include "misc/util/Functions.h"

SkillInTraining::SkillInTraining() :
        SkillInQueue()
{
}

APIFunction SkillInTraining::function() {
    return SKILL_IN_TRAINING;
}


inline uint SkillInTraining::calcRank() {
    return round((double) endSP / (double) SKILL_LEVEL_REQS[level]);
}

inline double SkillInTraining::calcTrainingSpeed() {
    return ((double) (endSP - startSP) / (double) (endTime - startTime));
}

uint SkillInTraining::calcCurrentSP() {
    ulong now = QDateTime::currentDateTime().toUTC().toTime_t();
    return (int) (round((now - startTime) * calcTrainingSpeed()) + startSP);
}

inline uint SkillInTraining::calcLevelStartSP() {
    return SKILL_LEVEL_REQS[level - 1] * calcRank();
}

double SkillInTraining::calcCompletion() {
    double completion = (double) (calcCurrentSP() - calcLevelStartSP())
                      / (double) (endSP - calcLevelStartSP());
    return min(1.0, completion);
}

