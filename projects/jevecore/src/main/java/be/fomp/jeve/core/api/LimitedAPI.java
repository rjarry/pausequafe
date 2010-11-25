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
package be.fomp.jeve.core.api;

import org.jdom.Document;

import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

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
 * Interface containing the accessor methods for the EVE-Online API 
 * which require the limited access API key.
 * 
 * @author Sven Meys
 *
 */
public interface LimitedAPI extends StandardAPI {

	
	/*--[ API V1 ]--*/
	
		//Character List (on account) 	 /account/Characters.xml.aspx
	 	Document getCharacters(int userID, String apiKey) throws JEveConnectionException, JEveParseException;
	 	
		//Character Sheet 	/char/CharacterSheet.xml.aspx
	 	Document getCharacterSheet(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
	 	
		//Skill in Training 	 /char/SkillInTraining.xml.aspx
	 	Document getSkillInTraining(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
	 	
	 	
	/*--[ API V2 ]--*/	
	
		//Corporation Sheet 	 /corp/CorporationSheet.xml.aspx
	 	Document getCorporationSheet(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
	
	 	Document getSkillQueue(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
	/*--[ API V3 ]--*/	
	
}
