/*
 * CharacterList.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "CharacterList.h"

CharacterList::CharacterList() {
}

CharacterList::~CharacterList() {
}

APIFunction function() {
    return CHARACTERS;
}


QList<APIData> CharacterList::getList() const
{
    return list;
}

void CharacterList::setList(QList<APIData> list)
{
    this->list = list;
}
