/*
 * Portrait.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "Portrait.h"

Portrait::Portrait(QString filePath) :
    filePath(filePath)
{
}

Portrait::~Portrait() {
}

APIObject::Function Portrait::function() {
    return APIObject::PORTRAIT;
}


QString Portrait::getFilePath() const
{
    return filePath;
}

void Portrait::setFilePath(QString filePath)
{
    this->filePath = filePath;
}
