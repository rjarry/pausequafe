/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
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

package org.pausequafe.data.dao;

import org.jdom.Document;
import org.pausequafe.data.business.ServerStatus;

import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;

public class ServerStatusFactory {

    public static ServerStatus getStatus() {
        StandardAPI connection = null;
        Document doc = null;

        // FIXME : completement pourri ce truc. Exceptions à gérer correctement svp

        // create limited connection
        try {
            connection = JEveConnectionFactory.createFactory().getStandardConnection();
        } catch (JEveConfigurationException e) {
            return null;
        }
        // get server status and put it into a Document
        try {
            doc = connection.getServerStatus();
        } catch (Exception e) {
            return null;
        }

        // parse the server status and return it
        return ServerStatusParser.getStatus(doc);
    }
}
