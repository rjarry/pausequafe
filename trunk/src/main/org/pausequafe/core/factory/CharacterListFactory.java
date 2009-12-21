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

package org.pausequafe.core.factory;

import java.util.List;

import org.jdom.Document;
import org.pausequafe.core.parsers.CharacterListParser;
import org.pausequafe.data.business.APIData;
import org.pausequafe.misc.exceptions.PQConfigException;
import org.pausequafe.misc.exceptions.PQConnectionException;
import org.pausequafe.misc.exceptions.PQException;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

public class CharacterListFactory {

    public static List<APIData> getCharList(int userID, String apiKey) throws PQException {
        LimitedAPI connection = null;
        Document doc = null;

        // create limited connection
        try {
            connection = JEveConnectionFactory.createFactory().getLimitedConnection();
        } catch (JEveConfigurationException e) {
            throw new PQConfigException("Configuration error");
        }
        // get character list and put it into a Document
        try {
            doc = connection.getCharacters(userID, apiKey);
        } catch (JEveConnectionException e) {
            throw new PQConnectionException("Connection error");
        } catch (JEveParseException e) {
            throw new PQException("API Authentication error");
        }

        // parse the character list and return it
        return CharacterListParser.getList(doc, userID, apiKey);
    }
}
