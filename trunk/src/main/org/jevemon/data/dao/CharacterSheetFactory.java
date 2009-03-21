package org.jevemon.data.dao;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.misc.exceptions.JEVEMonConfigException;
import org.jevemon.misc.exceptions.JEVEMonConnectionException;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonFileNotFoundException;
import org.jevemon.misc.exceptions.JEVEMonParseException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.FileHandler;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

/**
 * This factory provides a character sheet. 
 * It first tries to retrieve it from CCP server and, if not possible, from a previously cached file.
 * 
 * @author diabeteman
 * 
 */
public class CharacterSheetFactory {

	public static synchronized CharacterSheet getCharacterSheet(APIData data) throws JEVEMonException, IOException {
		LimitedAPI con = null;
		boolean isCached = false;

		// create limited connection
		try {
			con = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonConfigException(e.getMessage());
		}

		// get character sheet and cache it
		Document doc;
		try {
			doc = con.getCharacterSheet(data.getUserID(), data.getApiKey(), data.getCharacterID());
			FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, data.getCharacterName() + ".xml");
			doc = readCachedFile(data.getCharacterName());
			// if the connection failed, gets one previously cached file.
		} catch (JEveConnectionException e) {
			doc = readCachedFile(data.getCharacterName());
			isCached = true;
		} catch (JEveParseException e) {
			doc = readCachedFile(data.getCharacterName());
			isCached = true;
		}

		// create character sheet from XML parse
		CharacterSheet result = CharacterSheetParser.parse(doc, isCached);
		return result;
	}

	public static synchronized String getPortrait(APIData data, boolean forceDownload) 
								throws IOException, JEVEMonFileNotFoundException  {

		String portraitFilePath = "";
		boolean downloadFailed = false;

		if (forceDownload) {
			try {
				downloadPortrait(data.getCharacterID(), data.getCharacterName());
			} catch (JEVEMonException e) {
				downloadFailed = true;
			}
		}
		
		try {
			portraitFilePath = getPortraitFileName(data.getCharacterName());
		} catch (JEVEMonFileNotFoundException e) {
			if(downloadFailed) {
				throw e;
			} else {
				try {
					downloadPortrait(data.getCharacterID(), data.getCharacterName());
					portraitFilePath = getPortraitFileName(data.getCharacterName());
				} catch (JEVEMonException e1) {
					throw e;
				}
			}
		}
	
		return portraitFilePath;
	}

	private static Document readCachedFile(String name) throws JEVEMonException, IOException {
		File file = new File(Constants.CHAR_SHEET_PATH + name + ".xml");
		if ( !file.exists() ){
			throw new JEVEMonFileNotFoundException("XML file not found");
		}
		SAXBuilder builder = new SAXBuilder();
		
		Document doc = null;
		try {
			doc = builder.build(file);
		} catch (JDOMException e) {
			throw new JEVEMonParseException(e.getMessage());
		}
		
		return doc;
	}

	private static void downloadPortrait(int characterID, String name)
			throws IOException, JEVEMonConfigException,
			JEVEMonConnectionException, JEVEMonParseException {

		StandardAPI con = null;
		Image img = null;

		// create standard connection
		try {
			con = JEveConnectionFactory.createFactory().getStandardConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonConfigException(e.getMessage());
		}
		try {
			img = con.getPortrait(Constants.PORTRAIT_FILE_SIZE, characterID);
			FileHandler.writeImage(img, Constants.CHAR_SHEET_PATH, name
					+ ".jpg");
		} catch (JEveConnectionException e) {
			throw new JEVEMonConnectionException(e.getMessage());
		} catch (JEveParseException e) {
			throw new JEVEMonParseException(e.getMessage());
		}
	}

	private static String getPortraitFileName(String name)
			throws JEVEMonFileNotFoundException {

		File file = new File(Constants.CHAR_SHEET_PATH + name + ".jpg");

		if (!file.exists()) {
			throw new JEVEMonFileNotFoundException();
		}

		return file.getAbsolutePath();
	}

}
