/*****************************************************************************
 * Pause Quaf� - An Eve-Online� character assistance application             *
 * Copyright � 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quaf�.                                         *
 *                                                                           *
 * Pause Quaf� is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quaf� is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quaf�.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQConnectionException extends PQException {

    public PQConnectionException() {
    }

    public PQConnectionException(String message) {
        super(message);
    }

    public PQConnectionException(Throwable cause) {
        super(cause);
    }

    public PQConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}