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

#ifndef SKILLPLAN_H
#define SKILLPLAN_H

#include <QString>

#define NOID    -1
#define NONAME  "No name"

class SkillPlan
{
private:
    uint characterID;
    int id;
    int index;
    QString name;

public:
    /// constructor ///
    SkillPlan(uint characterID, int id, int index, QString name);

    /// getters ///
    uint getCharacterID();
    int getId();
    int getIndex();
    QString getName();

    /// setters ///
    void setCharacterID(uint characterID);
    void setId(int id);
    void setIndex(int index);
    void setName(QString name);

};

#endif // SKILLPLAN_H