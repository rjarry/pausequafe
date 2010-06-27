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

#ifndef APIDATA_H
#define APIDATA_H

#include <QString>

class APIData
{
private:
    uint characterID;
    QString characterName;
    uint userID;
    QString apiKey;

public:
    // constructors
    APIData(uint userID, QString apiKey);
    APIData(uint characterID, uint userID, QString apiKey);
    APIData(uint characterID, QString characterName, uint userID, QString apiKey);

    // getters
    QString getApiKey() const;
    uint getCharacterID() const;
    QString getCharacterName() const;
    uint getUserID() const;

    // setters
    void setApiKey(QString apiKey);
    void setCharacterID(uint characterID);
    void setCharacterName(QString characterName);
    void setUserID(uint userID);
};

#endif // APIDATA_H
