package org.jevemon.data.dao;

import java.util.List;

import org.jdom.Document;
import org.jevemon.data.business.APIData;
import org.jevemon.misc.exceptions.JEVEMonConfigException;
import org.jevemon.misc.exceptions.JEVEMonConnectionException;
import org.jevemon.misc.exceptions.JEVEMonParseException;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

public class CharacterListFactory {
	
	public static List<APIData> getCharList(int userID, String apiKey) throws 
																JEVEMonConfigException, 
																JEVEMonConnectionException, 
																JEVEMonParseException {
		LimitedAPI connection = null;
		Document doc = null;
		
		// create limited connection
		try {
			connection = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonConfigException("Configuration error");
		}
		// get character list and put it into a Document
		try {
			doc = connection.getCharacters(userID, apiKey);
		} catch (JEveConnectionException e) {
			throw new JEVEMonConnectionException("Connection error");
		} catch (JEveParseException e) {
			throw new JEVEMonParseException("Parsing error");
		}
		
		// parse the character list and return it
		return CharacterListParser.getList(doc, userID, apiKey);
	}
}
