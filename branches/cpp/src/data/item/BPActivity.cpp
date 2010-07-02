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

#include "BPActivity.h"

BPActivity::BPActivity(Activity act) :
        activity(act)
{
}

BPActivity::BPActivity(const BPActivity & other) :
    activity(other.activity),
    prereqs(other.prereqs),
    materials(other.materials)
{
}


/////////////////
/// ACCESSORS ///
/////////////////
Activity BPActivity::getActivityID() {
    return activity;
}

QList<PreRequisite> BPActivity::getPrereqs() {
    return prereqs;
}

QList<BPRequiredMaterial> BPActivity::getMaterials() {
    return materials;
}

void BPActivity::setActivityID(Activity activity) {
    this->activity = activity;
}

void BPActivity::setPrereqs(QList<PreRequisite> prereqs) {
    this->prereqs = prereqs;
}

void BPActivity::setMaterials(QList<BPRequiredMaterial> materials) {
    this->materials = materials;
}

QString BPActivity::getName() {
    return ACTIVITY_NAMES[activity];
}
