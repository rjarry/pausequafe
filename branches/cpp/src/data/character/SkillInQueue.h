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

#ifndef SKILLINQUEUE_H
#define SKILLINQUEUE_H

#include <QString>

class SkillInQueue
{
private:
    uint queuePosition;

protected:
    ulong endTime;
    ulong startTime;
    uint typeID;
    uint startSP;
    uint endSP;
    uint level;

public:
    SkillInQueue();

    ulong trainingTime();
    QString toString();

    // getters //
    ulong getEndTime();
    ulong getStartTime();
    uint getTypeID();
    uint getStartSP();
    uint getEndSP();
    uint getLevel();
    uint getQueuePosition();

    // setters //
    void setEndTime(ulong endTime);
    void setStartTime(ulong startTime);
    void setTypeID(uint typeID);
    void setStartSP(uint startSP);
    void setEndSP(uint endSP);
    void setLevel(uint level);
    void setQueuePosition(uint pos);

};

#endif // SKILLINQUEUE_H
