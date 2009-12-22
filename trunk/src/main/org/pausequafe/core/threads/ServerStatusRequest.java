/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.core.threads;

import org.pausequafe.core.factory.ServerStatusFactory;
import org.pausequafe.data.misc.ServerStatus;

import com.trolltech.qt.QSignalEmitter;

public class ServerStatusRequest extends QSignalEmitter implements Runnable {

    private ServerStatus status;
    public Signal0 requestStarted = new Signal0();
    public Signal1<ServerStatus> requestFinished;

    public ServerStatusRequest() {
        requestFinished = new Signal1<ServerStatus>();
    }

    public void run() {
        requestStarted.emit();
        status = ServerStatusFactory.getStatus();
        requestFinished.emit(status);
    }

}
