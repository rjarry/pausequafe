package org.jevemon.model.characterlist;

import org.jdom.Document;
import org.jevemon.misc.exceptions.JEVEMonException;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

public class CharacterListFactory {
	
	public static CharacterList getCharList(int userID, String apiKey) throws JEVEMonException {
		LimitedAPI connection = null;
		Document doc = null;
		
		// create limited connection
		try {
			connection = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonException("Configuration error");
		}
		// get character list and put it into a Document
		try {
			doc = connection.getCharacters(userID, apiKey);
		} catch (JEveConnectionException e) {
			throw new JEVEMonException("Connection error");
		} catch (JEveParseException e) {
			throw new JEVEMonException("Parsing error");
		}
		
		// parse the character list and return it
		return CharacterListParser.getList(doc);
	}
}