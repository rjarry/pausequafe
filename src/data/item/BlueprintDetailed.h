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

#ifndef BLUEPRINTDETAILED_H
#define BLUEPRINTDETAILED_H

#include <QString>

#include "data/item/Blueprint.h"

class BlueprintDetailed : public Blueprint
{
private:
    uint productTypeID;
    QString icon;
    uint techLevel;
    ulong productionTime;
    ulong researchMETime;
    ulong researchPETime;
    ulong inventionTime;
    ulong copyTime;
    uint wasteFactor;
    uint batchSize;
    uint maxRuns;
    double basePrice;

public:
    BlueprintDetailed(const Blueprint & bp);
    BlueprintDetailed(const BlueprintDetailed & other);
};

#endif // BLUEPRINTDETAILED_H