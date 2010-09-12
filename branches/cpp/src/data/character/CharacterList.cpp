/*
 * CharacterList.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "data/character/CharacterList.h"
#include <QMetaType>

CharacterList::CharacterList() {
    qRegisterMetaType<CharacterList>("CharacterList");
}

CharacterList::~CharacterList() {
}

APIObject::Function CharacterList::function() {
    return APIObject::CHARACTERS;
}

QString CharacterList::toString() {
    QString out;
    foreach(APIData data, list) {
        out.append(data.toString());
    }
    return out;
}

QList<APIData> CharacterList::getList() const {
    return list;
}

void CharacterList::setList(QList<APIData> list) {
    this->list = list;
}

