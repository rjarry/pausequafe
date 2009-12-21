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

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.pausequafe.core.parsers.CharacterSheetParser;
import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.misc.exceptions.PQConfigException;
import org.pausequafe.misc.exceptions.PQConnectionException;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQFileNotFoundException;
import org.pausequafe.misc.exceptions.PQParseException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.FileHandler;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

/**
 * This factory provides a character sheet. It first tries to retrieve it from CCP server and, if
 * not possible, from a previously cached file.
 * 
 * @author diabeteman
 * 
 */
public class CharacterSheetFactory {

    public static synchronized CharacterSheet getCharacterSheet(APIData data) throws PQException,
            IOException {
        LimitedAPI con = null;
        boolean isCached = false;

        // create limited connection
        try {
            con = JEveConnectionFactory.createFactory().getLimitedConnection();
        } catch (JEveConfigurationException e) {
            throw new PQConfigException(e.getMessage());
        }

        // get character sheet and cache it
        Document doc;
        try {
            doc = con.getCharacterSheet(data.getUserID(), data.getApiKey(), data.getCharacterID());
            FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, data.getCharacterName()
                    + ".xml");
            doc = readCachedFile(data.getCharacterName());
            // if the connection failed, gets one previously cached file.
        } catch (JEveConnectionException e) {
            doc = readCachedFile(data.getCharacterName());
            isCached = true;
        } catch (JEveParseException e) {
            throw new PQException("API Authentication error");
        }

        // create character sheet from XML parse
        CharacterSheet result = CharacterSheetParser.parse(doc, isCached);
        return result;
    }

    public static synchronized String getPortrait(APIData data, boolean forceDownload)
            throws IOException, PQFileNotFoundException {

        String portraitFilePath = "";
        boolean downloadFailed = false;

        if (forceDownload) {
            try {
                downloadPortrait(data.getCharacterID(), data.getCharacterName());
            } catch (PQException e) {
                downloadFailed = true;
            }
        }

        try {
            portraitFilePath = getPortraitFileName(data.getCharacterName());
        } catch (PQFileNotFoundException e) {
            if (downloadFailed) {
                throw e;
            } else {
                try {
                    downloadPortrait(data.getCharacterID(), data.getCharacterName());
                    portraitFilePath = getPortraitFileName(data.getCharacterName());
                } catch (PQException e1) {
                    throw e;
                }
            }
        }

        return portraitFilePath;
    }

    private static Document readCachedFile(String name) throws PQException, IOException {
        File file = new File(Constants.CHAR_SHEET_PATH + name + ".xml");
        if (!file.exists()) {
            throw new PQFileNotFoundException("XML file not found");
        }
        SAXBuilder builder = new SAXBuilder();

        Document doc = null;
        try {
            doc = builder.build(file);
        } catch (JDOMException e) {
            throw new PQParseException(e.getMessage());
        }

        return doc;
    }

    private static void downloadPortrait(int characterID, String name) throws IOException,
            PQConfigException, PQConnectionException, PQParseException {

        StandardAPI con = null;
        Image img = null;

        // create standard connection
        try {
            con = JEveConnectionFactory.createFactory().getStandardConnection();
        } catch (JEveConfigurationException e) {
            throw new PQConfigException(e.getMessage());
        }
        try {
            img = con.getPortrait(Constants.PORTRAIT_FILE_SIZE, characterID);
            FileHandler.writeImage(img, Constants.CHAR_SHEET_PATH, name + ".jpg");
        } catch (JEveConnectionException e) {
            throw new PQConnectionException(e.getMessage());
        } catch (JEveParseException e) {
            throw new PQParseException(e.getMessage());
        }
    }

    private static String getPortraitFileName(String name) throws PQFileNotFoundException {

        File file = new File(Constants.CHAR_SHEET_PATH + name + ".jpg");

        if (!file.exists()) {
            throw new PQFileNotFoundException();
        }

        return file.getAbsolutePath();
    }

}
