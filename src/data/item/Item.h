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

#ifndef ITEM_H
#define ITEM_H


#include <QString>
#include <QList>

#include "data/item/PreRequisite.h"
#include "misc/util/Constants.h"

class Item
{
protected:
    uint typeID;
    QString typeName;
    uint categoryID;
    uint metaGroupID;
    uint metaLevel;
    QList<PreRequisite*> preReqs;

public:
    // constructors
    Item();
    Item(uint id, QString name, uint category,
         uint metaGroup = TECH1_METAGROUP, uint metaLvl = 0);
    Item(const Item & item);
    // getters
    uint getCategoryID() const;
    uint getMetaGroupID() const;
    uint getMetaLevel() const;
    QList<PreRequisite*> getPreReqs() const;
    uint getTypeID() const;
    QString getTypeName() const;
    // setters
    void setCategoryID(uint categoryID);
    void setMetaGroupID(uint metaGroupID);
    void setMetaLevel(uint metaLevel);
    void setPreReqs(QList<PreRequisite*> preReqs);
    void setTypeID(uint typeID);
    void setTypeName(QString typeName);
};

#endif // ITEM_H
