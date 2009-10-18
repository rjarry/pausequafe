package org.pausequafe.data.dao;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.FileHandler;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

public class SkillInTrainingFactory {
	
	public static synchronized SkillInTraining getSkillInTraining(APIData data) throws PQException, IOException {
		LimitedAPI con = null;
		boolean isCached = false;
		
		// create limited connection
		try {
			con = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new PQException("Configuration error");
		}
		
		// get skill in training and cache it
		Document doc;
		try {
			doc = con.getSkillInTraining(data.getUserID(), data.getApiKey(), data.getCharacterID());
			FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, data.getCharacterName() 
															+ "InTraining.xml");
			doc = readCachedFile(data.getCharacterName());
		// if the connection failed, gets one previously cached file.
		} catch (JEveConnectionException e) {
			doc = readCachedFile(data.getCharacterName());
			isCached = true;
		} catch (JEveParseException e) {
			throw new PQException("API Authentication error");
		}
		
		// create skill in training from XML parse
		SkillInTraining result = SkillInTrainingParser.parse(doc, isCached);
		return result;
	}

	private static Document readCachedFile(String name) throws IOException, PQException {
		File file = new File(Constants.CHAR_SHEET_PATH + name + "InTraining.xml");
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(file);
		} catch (JDOMException e) {
			throw new PQException("parsing error");
		}
		return doc;
	}
}
