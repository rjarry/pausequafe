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

#ifndef BPACTIVITY_H
#define BPACTIVITY_H

#include <QList>
#include <QString>

#include "data/item/PreRequisite.h"
#include "data/item/BPRequiredMaterial.h"

enum Activity {
    NONE = 0,
    MANUFACTURING = 1,
    RESEARCH_TECH = 2,
    RESEARCH_PE = 3,
    RESEARCH_ME = 4,
    COPY = 5,
    DUPLICATING = 6,
    REVERSE_ENGINEERING = 7,
    INVENTION = 8
};

QString ACTIVITY_NAME[] = {
    "",
    "Manufacturing",
    "Researching Technology",
    "Time Efficiency Research",
    "Material Efficiency Research",
    "Copying",
    "Duplicating",
    "Reverse Engineering",
    "Invention"
};


class BPActivity
{
private:
    Activity activity;
    QList<PreRequisite> prereqs;
    QList<BPRequiredMaterial> materials;
public:
    BPActivity(Activity act);
    BPActivity(const BPActivity & other);
    ~BPActivity();

    QString getName();

    Activity getActivityID();
    QList<PreRequisite> getPrereqs();
    QList<BPRequiredMaterial> getMaterials();

    void setActivityID(Activity activity);
    void setPrereqs(QList<PreRequisite> prereqs);
    void setMaterials(QList<BPRequiredMaterial> materials);
};

#endif // BPACTIVITY_H
