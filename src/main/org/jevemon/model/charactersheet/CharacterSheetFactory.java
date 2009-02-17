package org.jevemon.model.charactersheet;

import org.jdom.Document;
import org.jevemon.model.Constants;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.*;



public class CharacterSheetFactory {

	public static CharacterSheet createLimitedCharacterSheet( int userID, String apiKey, int characterID){
		
		LimitedAPI con = null;
		
		// create limited connection
		try {
			con = JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveConfigurationException e) {
			// TODO handle this exception
			e.printStackTrace();
		}
		
		// get character sheet and cache it
		try {
			System.out.print("Fetching Character Sheet...");
			Document doc = con.getCharacterSheet(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, Constants.CHAR_SHEET_PATH, "CharacterSheet.xml");
			System.out.println("success");
		} catch (JEveConnectionException e) {
			// TODO handle this exception
			e.printStackTrace();
		} catch (JEveParseException e) {
			// TODO handle this exception
			e.printStackTrace();
		} catch (JEveException e) {
			// TODO handle this exception
			e.printStackTrace();
		}
		
		// TODO create character sheet from XML parse
		CharacterSheetParser p = new CharacterSheetParser();
		CharacterSheet result = p.parse();
		return result;
	}
}
