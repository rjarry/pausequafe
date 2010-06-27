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

#ifndef MONITOREDCHARACTER_H
#define MONITOREDCHARACTER_H

#include <QList>
#include <QString>

#include "data/character/APIData.h"
#include "data/character/CharacterSheet.h"
#include "data/character/SkillPlan.h"
#include "data/character/SkillInTraining.h"
#include "data/character/SkillQueue.h"

class MonitoredCharacter
{
private:
    APIData api;
    CharacterSheet sheet;
    QList<SkillPlan*> skillPlanList;
    SkillInTraining inTraining;
    SkillQueue queue;
    QString imageLocation;

public:
    MonitoredCharacter(APIData api);

    // getters //
    APIData & getApi();
    CharacterSheet & getSheet();
    SkillPlan* getSkillPlanAt(unsigned index);
    SkillInTraining & getInTraining();
    SkillQueue & getQueue();
    QString getImageLocation();

    // setters //
    void setApi(APIData api);
    void setSheet(CharacterSheet sheet);
    void setSkillPlanAt(unsigned index, SkillPlan* newPlan);
    void setInTraining(SkillInTraining inTraining);
    void setQueue(SkillQueue queue);
    void setImageLocation(QString imageLocation);

};

#endif // MONITOREDCHARACTER_H
