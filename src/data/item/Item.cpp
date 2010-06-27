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

#include "Item.h"

Item::Item() :
    preReqs(QList<PreRequisite*>())
{
}

Item::Item(uint id, QString name, uint category, uint metaGroup, uint metaLvl) :
    typeID(id),
    typeName(name),
    categoryID(category),
    metaGroupID(metaGroup),
    metaLevel(metaLvl),
    preReqs(QList<PreRequisite*>())
{
}

Item::Item(const Item & item) :
    typeID(item.typeID),
    typeName(item.typeName),
    categoryID(item.categoryID),
    metaGroupID(item.metaGroupID),
    metaLevel(item.metaLevel),
    preReqs(QList<PreRequisite*>())
{
    foreach(PreRequisite* req, item.preReqs) {
        preReqs.append(new PreRequisite(*req));
    }
}

/////////////
// getters //
/////////////
uint Item::getCategoryID() const {
    return categoryID;
}

uint Item::getMetaGroupID() const {
    return metaGroupID;
}

uint Item::getMetaLevel() const {
    return metaLevel;
}

QList<PreRequisite*> Item::getPreReqs() const {
    return preReqs;
}

uint Item::getTypeID() const {
    return typeID;
}

QString Item::getTypeName() const {
    return typeName;
}

/////////////
// setters //
/////////////
void Item::setCategoryID(uint categoryID) {
    this->categoryID = categoryID;
}

void Item::setMetaGroupID(uint metaGroupID) {
    this->metaGroupID = metaGroupID;
}

void Item::setMetaLevel(uint metaLevel) {
    this->metaLevel = metaLevel;
}

void Item::setPreReqs(QList<PreRequisite*> preReqs) {
    this->preReqs = preReqs;
}

void Item::setTypeID(uint  typeID) {
    this->typeID = typeID;
}

void Item::setTypeName(QString typeName) {
    this->typeName = typeName;
}

