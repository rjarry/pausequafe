/*	
   ______   ______    
  /\__  __\/\ \___\ 
  \/_/\ \_/\ \ \__/    __   __     __       
     \ \ \  \ \ \__\  /\ \ /\ \  /'__`\  
     _\_\ \  \ \ \_/_ \ \ \\_\ \/\  __/       
    /\____/   \ \_____\\ \_____/\ \____\   
    \_/__/     \/_____/ \/____/  \/____/                   
 
 	This file is part of the JEVE core API.

    JEve is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    JEve is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Copyright 2008 JEve Project (JEveProject@gmail.com)
 */
package be.fomp.jeve.core.api.connectors;

import org.jdom.Document;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.url.LimitedUrl;
import be.fomp.jeve.core.config.Configuration;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.exceptions.JEveParseException;
import be.fomp.jeve.core.util.ParameterString;

/**
 * <pre>
 *  ______   ______    
 * /\__  __\/\ \___\ 
 * \/_/\ \_/\ \ \__/    __   __     __       
 *    \ \ \  \ \ \__\  /\ \ /\ \  /'__`\  
 *    _\_\ \  \ \ \_/_ \ \ \\_\ \/\  __/       
 *   /\____/   \ \_____\\ \_____/\ \____\   
 *   \_/__/     \/_____/ \/____/  \/____/                   
 *</pre>
 *	This file is part of the JEVE core API.<br />
 *	<br />
 * 
 * This class contains the accessor methods for the EVE-Online API 
 * which require a limited API key.
 * 
 * @author Sven Meys
 */
class LimitedConnector extends StandardConnector implements LimitedAPI {

	/**
	 * Constructor which takes a configuration setting. This configures the correct 
	 * proxy settings to ensure the client can access the internet.
	 * 
	 * @param proxy The proxy settings
	 * @throws JEveException
	 */
	LimitedConnector(Configuration config) {super(config);}
	
	/**
	 * This method retrieves the character sheet of the corresponding character ID
	 * from http://api.eve-online.com/char/CharacterSheet.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A character sheet
	 * @throws JEveBadargumentException when proxy is null
	 * 
	 * @author Sven Meys
	 */
	public final Document getCharacterSheet(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(LimitedUrl.CHARACTER_SHEET, parameters);
	}

	/**
	 * This method retrieves the characters on the given account
	 * from http://api.eve-online.com/account/Characters.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @return A list of characters
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCharacters(int userID, String apiKey) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey);
		
		return getDocumentData(LimitedUrl.CHARACTERS, parameters);
	}

	/**
	 * This method retrieves the corporation sheet
	 * from http://api.eve-online.com/corp/CorporationSheet.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @return A corporation sheet
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorporationSheet(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(LimitedUrl.CORPORATION_SHEET, parameters);
	}

	/**
	 * This method retrieves the skill which your character is currently training
	 * from http://api.eve-online.com/char/SkillInTraining.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The character ID
	 * @return A corporation sheet
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getSkillInTraining(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(LimitedUrl.SKILL_IN_TRAINING, parameters);
	}

	
	
	
	/**
	 * This method retrieves the skillqueue currently active on your character
	 * from http://api.eve-online.com/char/SkillQueue.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The character ID
	 * @return A corporation sheet
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getSkillQueue(int userID, String apiKey, int characterID)
			throws JEveConnectionException, JEveParseException {
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(LimitedUrl.SKILL_QUEUE, parameters);
	}
}
