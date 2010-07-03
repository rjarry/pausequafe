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

#include "core/network/APIConnection.h"


APIConnection::APIConnection() {
}

APIConnection::~APIConnection() {
}


QNetworkReply* APIConnection::get(const APIData & data, const APIObject::Function function) {

    QUrl url;
    url.setUrl(URL_VALUES[function]);

    switch (function) {
    case APIObject::SERVER_STATUS:
        /* no parameters needed */
        break;
    case APIObject::PORTRAIT:
        url.addQueryItem("s", QString::number(PORTRAIT_SIZE));
        url.addQueryItem("c", QString::number(data.getCharacterID()));
        break;
    case APIObject::CHARACTERS:
        url.addQueryItem("userID", QString::number(data.getUserID()));
        url.addQueryItem("apiKey", data.getApiKey());
        break;
    default:
        url.addQueryItem("userID", QString::number(data.getUserID()));
        url.addQueryItem("characterID", QString::number(data.getCharacterID()));
        url.addQueryItem("apiKey", data.getApiKey());
    }

    QNetworkRequest httpRequest(url);
    httpRequest.setRawHeader("User-Agent", "PauseQuafe version "PQ_VERSION);

    return manager.get(httpRequest);
}

void APIConnection::setProxy(QNetworkProxy proxy) {
    manager.setProxy(proxy);
}

QNetworkProxy APIConnection::getProxy() {
    return this->manager.proxy();
}
