package org.jevemon.model.charactersheet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.FileHandler;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.exceptions.JEveParseException;


/**
 * This factory provides a character sheet. It first tries to 
 * @author diabeteman
 *
 */
public class CharacterSheetFactory {

	public static CharacterSheet getCharacterSheet(int userID, String apiKey, int characterID, String name) 
										throws JEVEMonException {
		LimitedAPI con = null;
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
			FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, name + ".xml");
			doc = readCachedFile(name);
		// if the connection failed, gets one previously cached file.
		} catch (JEveConnectionException e) {
			doc = readCachedFile(name);
			isCached = true;
		} catch (JEveParseException e) {
			doc = readCachedFile(name);
			isCached = true;
		}
		
		// create character sheet from XML parse
		CharacterSheet result = CharacterSheetParser.parse(doc, isCached);
		return result;
	}
	
	public static Image getPortrait(int characterID, String name) throws JEVEMonException{
		StandardAPI con = null;
		
		// create standard connection
		try {
			con = JEveConnectionFactory.createFactory().getStandardConnection();
		} catch (JEveConfigurationException e) {
			throw new JEVEMonException("Configuration error");
		}
		Image img = null;
		try {
			img = con.getPortrait(256, characterID);
			FileHandler.writeImage(img, Constants.CHAR_SHEET_PATH, name+".jpg");
			img = readCachedImage(name);
		} catch (JEveException e) {
			img = readCachedImage(name);
		}
		return img;
	}
	
	public static Document readCachedFile(String name) throws JEVEMonException{
		File file = new File(Constants.CHAR_SHEET_PATH + name + ".xml");
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(file);
		} catch (JDOMException e) {
			throw new JEVEMonException("parsing error");
		} catch (IOException e) {
			throw new JEVEMonException("XML file not found");
		}
		return doc;
	}
	
	public static Image readCachedImage(String name) throws JEVEMonException{
		File file = new File(Constants.CHAR_SHEET_PATH + name + ".jpg");
		Image img = null;
		try {
			 img = ImageIO.read(file);
		} catch (IOException e) {
			throw new JEVEMonException("image not found");
		}
		return img;
	}
}
