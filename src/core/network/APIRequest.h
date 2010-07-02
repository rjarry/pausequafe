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

#ifndef APIREQUEST_H
#define APIREQUEST_H

#include <QByteArray>
#include <QObject>
#include <QNetworkReply>

#include "data/APIObject.h"
#include "data/character/APIData.h"
#include "core/network/APIConnection.h"
#include "data/misc/APIError.h"

#include "core/parsers/ServerStatusParser.h"
#include "core/parsers/PortraitParser.h"
#include "core/parsers/CharacterListParser.h"
#include "core/parsers/CharacterSheetParser.h"
#include "core/parsers/SkillInTrainingParser.h"
#include "core/parsers/SkillQueueParser.h"

class APIRequest : public QObject
{
    Q_OBJECT

private:
    APIData data;
    APIFunction function;
    QNetworkReply* reply;

public:
    // constructor
    APIRequest(APIData data, APIFunction function);

    // public methods
    void go();

    // getters
    APIData getData() const;
    APIFunction getFunction() const;
    // setters
    void setData(APIData data);
    void setFunction(APIFunction function);

private slots:
    void handleReply();

signals:
    void responseReady(APIObject* obj);
};

#endif // APIREQUEST_H
