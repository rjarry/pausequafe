package org.pausequafe.data.dao;

import java.util.List;

import org.jdom.Document;
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
	
	public static List<APIData> getCharList(int userID, String apiKey) throws 
																PQException {
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
