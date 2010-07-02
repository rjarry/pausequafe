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

#include "APIRequest.h"


///////////////////
/// constructor ///
///////////////////
APIRequest::APIRequest(APIData data, APIFunction function) :
    data(data),
    function(function)
{
    connect(reply, SIGNAL(finished()), this, SLOT(handleReply()));
}

//////////////////////
/// public methods ///
//////////////////////
void APIRequest::go() {
    reply = APIConnection::getInstance()->get(this);
}

/////////////////////
/// private slots ///
/////////////////////
void APIRequest::handleReply() {

    QByteArray rawData = reply->readAll();
    APIObject* object;

    if (reply->error()) {
        object = new APIError(reply->errorString(), rawData);
    } else {
        switch (function) {
        case SERVER_STATUS:
            object = ServerStatusParser::parse(rawData);
            break;
        case PORTRAIT:
            object = PortraitParser::parse(rawData);
            break;
        case CHARACTERS:
            object = CharacterListParser::parse(rawData);
            break;
        case CHARACTER_SHEET:
            object = CharacterSheetParser::parse(rawData);
            break;
        case SKILL_IN_TRAINING:
            object = SkillInTrainingParser::parse(rawData);
            break;
        case SKILL_QUEUE:
            object = SkillQueueParser::parse(rawData);
            break;
        default:
            object = NULL;
        }
    }

    emit responseReady(object);
}


///////////////
/// getters ///
///////////////
APIData APIRequest::getData() const {
    return data;
}

APIFunction APIRequest::getFunction() const {
    return function;
}

///////////////
/// setters ///
///////////////
void APIRequest::setData(APIData data) {
    this->data = data;
}

void APIRequest::setFunction(APIFunction function) {
    this->function = function;
}

