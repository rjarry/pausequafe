package org.jevemon.data.dao;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.FileHandler;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

public class SkillInTrainingFactory {
	
	public static SkillInTraining getSkillInTraining(int userID, String apiKey, int characterID, String name) 
											throws JEVEMonException
	{
		LimitedAPI con = null;
		boolean isCached = false;
		
		// create limited connection
		try {
			con = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonException("Configuration error");
		}
		
		// get skill in training and cache it
		Document doc;
		try {
			doc = con.getSkillInTraining(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, name + "InTraining.xml");
			doc = readCachedFile(name);
		// if the connection failed, gets one previously cached file.
		} catch (JEveConnectionException e) {
			doc = readCachedFile(name);
			isCached = true;
		} catch (JEveParseException e) {
			doc = readCachedFile(name);
			isCached = true;
		}
		
		// create skill in training from XML parse
		SkillInTraining result = SkillInTrainingParser.parse(doc, isCached);
		return result;
	}

	private static Document readCachedFile(String name) throws JEVEMonException {
		File file = new File(Constants.CHAR_SHEET_PATH + name + "InTraining.xml");
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
