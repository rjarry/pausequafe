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

#include "Blueprint.h"

Blueprint::Blueprint(uint id, QString name, uint category, uint metaGroup) :
        Item(id, name, category, metaGroup),
        activities(QMap<Activity, BPActivity*>())
{
}

Blueprint::~Blueprint() {
    foreach (BPActivity act, activities.values()) {
        delete act;
    }
    activities.clear();
}

QMap<Activity, BPActivity*> & Blueprint::getActivities() {
    return activities;
}
void Blueprint::setActivities(QMap<Activity, BPActivity*> activities) {
    this->activities = activities;
}
