/*
 * CharacterList.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "data/character/CharacterList.h"

CharacterList::CharacterList() {
}

CharacterList::~CharacterList() {
}

APIObject::Function CharacterList::function() {
    return APIObject::CHARACTERS;
}


QList<APIData> CharacterList::getList() const
{
    return list;
}

void CharacterList::setList(QList<APIData> list)
{
    this->list = list;
}
