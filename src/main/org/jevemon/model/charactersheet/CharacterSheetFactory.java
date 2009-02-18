package org.jevemon.model.charactersheet;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.Constants;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.exceptions.JEveParseException;



public class CharacterSheetFactory {

	public static CharacterSheet getCharacterSheet(int userID, String apiKey, int characterID) 
										throws JEVEMonException
	{
		LimitedAPI con = null;
		String filePath = Constants.CHAR_SHEET_PATH + characterID + "/";
		boolean isCached = false;
		
		// create limited connection
		try {
			con = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonException("Configuration error");
		}
		
		// get character sheet and cache it
		Document doc;
		try {
			doc = con.getCharacterSheet(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, filePath, "CharacterSheet.xml");
		
		// if the connection failed, gets one previously cached file.
		} catch (JEveConnectionException e) {
			doc = readCachedFile(characterID);
			isCached = true;
		} catch (JEveParseException e) {
			doc = readCachedFile(characterID);
			isCached = true;
		} catch (JEveException e) {
			doc = readCachedFile(characterID);
			isCached = true;
		}
		
		// create character sheet from XML parse
		doc = readCachedFile(characterID);
		CharacterSheet result = CharacterSheetParser.parse(doc, isCached);
		return result;
	}
	
	public static Document readCachedFile(int characterID) throws JEVEMonException{
		String filePath = Constants.CHAR_SHEET_PATH + characterID + "/";
		File file = new File(filePath+"CharacterSheet.xml");
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(file);
		} catch (JDOMException e) {
			throw new JEVEMonException("parsing error");
		} catch (IOException e) {
			throw new JEVEMonException("file not present");
		}
		return doc;
	}
}
