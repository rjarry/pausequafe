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

#ifndef ATTRIBUTEENHANCER_H
#define ATTRIBUTEENHANCER_H

#include <QString>

#include "data/character/Attribute.h"

class AttributeEnhancer
{
private:
    Attribute attribute;
    uint augmentatorValue;
    QString augmentatorName;

public:
    // constructors
    AttributeEnhancer();
    AttributeEnhancer(Attribute attribute, uint bonus, QString name);

    // getters
    uint getAugmentatorValue() const;
    Attribute getAttribute() const;
    QString getAugmentatorName() const;

    // setters
    void setAugmentatorValue(uint augmentatorValue);
    void setAttribute(Attribute attribute);
    void setAugmentatorName(QString augmentatorName);
};

#endif // ATTRIBUTEENHANCER_H
