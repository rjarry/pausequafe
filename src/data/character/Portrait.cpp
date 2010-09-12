/*
 * Portrait.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "data/character/Portrait.h"
#include <QMetaType>

Portrait::Portrait(QString filePath) :
    filePath(filePath)
{
    qRegisterMetaType<Portrait>("Portrait");
}

Portrait::~Portrait() {
}

APIObject::Function Portrait::function() {
    return APIObject::PORTRAIT;
}

QString Portrait::toString() {
    return filePath;
}

QString Portrait::getFilePath() const {
    return filePath;
}

void Portrait::setFilePath(QString filePath) {
    this->filePath = filePath;
}
