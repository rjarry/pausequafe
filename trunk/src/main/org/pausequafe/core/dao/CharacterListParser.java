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

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.business.APIData;

public class CharacterListParser {

    /**
     * Generates a character list from a JDOM Document.
     * 
     * @param doc
     * @param apiKey
     * @param userID
     * @return a character list
     */
    @SuppressWarnings("unchecked")
    public static List<APIData> getList(Document doc, int userID, String apiKey) {

        Element root = doc.getRootElement();
        List<Element> xmlList = root.getChild("result").getChild("rowset").getChildren("row");

        List<APIData> list = new ArrayList<APIData>();

        for (Element row : xmlList) {
            String chrName = row.getAttributeValue("name");
            int charID = Integer.parseInt(row.getAttributeValue("characterID"));

            list.add(new APIData(charID, chrName, userID, apiKey));
        }
        return list;
    }

    // /**
    // * parses an XML file to a document
    // * @param fileName
    // * @return a JDOM Document
    // * @throws JEVEMonParseException
    // */
    // public static Document parse(String fileName) throws
    // JEVEMonParseException {
    // Document doc = null;
    // File file = new File(fileName);
    // try {
    // doc = new SAXBuilder().build(file);
    // if(doc.getRootElement().getChildren("error").size() > 0){
    // throw new JEVEMonParseException("Corrupted XML file");
    // }
    // } catch (JDOMException e) {
    // throw new JEVEMonParseException("Parsing error");
    // } catch (IOException i) {
    // throw new JEVEMonParseException("I/O error");
    // }
    // return doc;
    // }

}
