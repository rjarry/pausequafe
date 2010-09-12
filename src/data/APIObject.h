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

#ifndef APIOBJECT_H_
#define APIOBJECT_H_

#include <QString>
#include <QVariant>

class APIObject : public QVariant {

public:
    enum Function {
        SERVER_STATUS = 0,
        PORTRAIT,
        CHARACTERS,
        CHARACTER_SHEET,
        SKILL_IN_TRAINING,
        SKILL_QUEUE,
        ERROR
    };

    static const int META_TYPE;

    APIObject() {}
    ~APIObject() {}

    virtual APIObject::Function function() = 0;
    virtual QString toString() = 0;
};

#endif /* APIOBJECT_H_ */
