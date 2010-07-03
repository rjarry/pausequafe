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

#include "ServerStatus.h"

ServerStatus::ServerStatus(uint playerCount, bool onLine, bool unknown) :
        playerCount(playerCount),
        onLine(onLine),
        unknown(unknown)
{
}

APIObject::Function ServerStatus::function() {
    return APIObject::SERVER_STATUS;
}


bool ServerStatus::isOnLine() const {
    return onLine;
}

bool ServerStatus::isUnknown() const {
    return unknown;
}

uint ServerStatus::getPlayerCount() const {
    return playerCount;
}

void ServerStatus::setOnLine(bool onLine) {
    this->onLine = onLine;
}

void ServerStatus::setUnknown(bool unknown) {
    this->unknown = unknown;
}


void ServerStatus::setPlayerCount(uint playerCount) {
    this->playerCount = playerCount;
}
