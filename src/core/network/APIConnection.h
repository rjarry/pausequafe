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

#ifndef APICONNECTION_H_
#define APICONNECTION_H_

#include <QObject>
#include <QNetworkProxy>
#include <QNetworkAccessManager>
#include <QNetworkReply>

#include "misc/util/Constants.h"
#include "core/network/APIRequest.h"
#include "data/APIObject.h"


#define PORTRAIT_URL        "http://img.eve.is/serv.asp"
#define PORTRAIT_SIZE       256

#define BASE_URL            "http://api.eve-online.com"

static QString URL_VALUES[] = {
    BASE_URL"/Server/ServerStatus.xml.aspx",
    PORTRAIT_URL,
    BASE_URL"/account/Characters.xml.aspx",
    BASE_URL"/char/CharacterSheet.xml.aspx",
    BASE_URL"/char/SkillInTraining.xml.aspx",
    BASE_URL"/char/SkillQueue.xml.aspx"
};


class APIConnection : public QObject
{
    Q_OBJECT

public:

    QNetworkReply* get(APIRequest* request);
    void setProxy(QNetworkProxy proxy);
    QNetworkProxy getProxy();

    static APIConnection* getInstance();
    static void killInstance();
private:
    static APIConnection* _INSTANCE;
    QNetworkAccessManager manager;

    APIConnection();
    virtual ~APIConnection();

};

#endif /* APICONNECTION_H_ */
