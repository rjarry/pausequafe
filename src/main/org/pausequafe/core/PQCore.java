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

package org.pausequafe.core;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.QTimer;

/**
 * This class handles the scheduling of all the API requests and signal emissions for updating the
 * GUI.
 * 
 * @author diabeteman
 * 
 */
public class PQCore {

    // SINGLETON //////////////////////////////////////////////////////////////////////////////////
    private static PQCore instance;

    // TIMERS /////////////////////////////////////////////////////////////////////////////////////
    private final QTimer serverStatusTimer;
    private final List<CharacterController> controllers;

    // CONSTRUCTOR ////////////////////////////////////////////////////////////////////////////////
    private PQCore() {
        serverStatusTimer = new QTimer();
        controllers = new ArrayList<CharacterController>();
        serverStatusTimer.timeout.connect(this, "");
    }

    public static PQCore getInstance() {
        if (instance == null) {
            instance = new PQCore();
        }
        return instance;
    }

}
