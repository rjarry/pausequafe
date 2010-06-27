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

#include "AttributeEnhancer.h"

//////////////////
// constructors //
//////////////////
AttributeEnhancer::AttributeEnhancer()
{}

AttributeEnhancer::AttributeEnhancer(Attribute attr, uint bonus, QString name):
    attribute(attr),
    augmentatorValue(bonus),
    augmentatorName(name)
{}

/////////////
// getters //
/////////////
uint AttributeEnhancer::getAugmentatorValue() const
{
    return augmentatorValue;
}
Attribute AttributeEnhancer::getAttribute() const
{
    return attribute;
}
QString AttributeEnhancer::getAugmentatorName() const
{
    return augmentatorName;
}

/////////////
// setters //
/////////////
void AttributeEnhancer::setAugmentatorValue(uint augmentatorValue)
{
    this->augmentatorValue = augmentatorValue;
}
void AttributeEnhancer::setAttribute(Attribute attribute)
{
    this->attribute = attribute;
}
void AttributeEnhancer::setAugmentatorName(QString augmentatorName)
{
    this->augmentatorName = augmentatorName;
}
