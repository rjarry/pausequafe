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

#ifndef SKILLQUEUE_H
#define SKILLQUEUE_H

#include <QList>

#include "data/character/SkillInQueue.h"
#include "data/APIObject.h"

class SkillQueue : public APIObject
{
private:
    ulong currentTime;
    ulong cachedUntil;
    bool cached;
    QList<SkillInQueue> skillList;

public:
    /// constructor ///
    SkillQueue();

    /// public methods ///
    APIFunction function();
    ulong getTotalTrainingTime();

    /// getters ///
    ulong getCurrentTime();
    ulong getCachedUntil();
    bool isCached();
    QList<SkillInQueue> & getSkillList();

    /// setters ///
    void setCurrentTime(ulong currentTime);
    void setCachedUntil(ulong cachedUntil);
    void setCached(bool cached);
    void setSkillList(QList<SkillInQueue> skillList);
};

#endif // SKILLQUEUE_H
