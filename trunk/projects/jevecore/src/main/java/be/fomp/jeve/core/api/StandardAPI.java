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

import java.awt.Image;

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
 * which do not require an API key.
 * 
 * @version 1.0
 * @author Sven Meys
 * 
 */
public interface StandardAPI {
	
	/*--[ API V1 ]--*/
	
		// Alliance List 	 /eve/AllianceList.xml.aspx
		Document getAllianceList() throws JEveConnectionException, JEveParseException;
		
		//Map: Sovereignty 	/map/Sovereignty.xml.aspx
		Document getSovereignty() throws JEveConnectionException, JEveParseException;
		
		 //Name to ID Conversion 	 /eve/CharacterID.xml.aspx
		Document getCharacterID(String names) throws JEveConnectionException, JEveParseException;
		
		 //ID to Character Portrait 	http://img.eve.is/serv.asp
		Image getPortrait(int size, int characterID) throws JEveConnectionException, JEveParseException;
		
		 //RefTypes List 	/eve/RefTypes.xml.aspx
		Document getRefTypes() throws JEveConnectionException, JEveParseException;
		
		// Skill Tree 	 /eve/SkillTree.xml.aspx
		Document getSkillTree() throws JEveConnectionException, JEveParseException;
	
	
	/*--[ API V2 ]--*/

		//Conquerable Station/Outpost List 	 /eve/ConquerableStationList.xml.aspx
		Document getConquerableStationList() throws JEveConnectionException, JEveParseException;
		
		// Corporation Sheet 	 /corp/CorporationSheet.xml.aspx
		Document getCorporationSheet(int userID, int corporationID) throws JEveConnectionException, JEveParseException;
		
		//Error List 	 /eve/ErrorList.xml.aspx
		Document getErrorList() throws JEveConnectionException, JEveParseException;
		
		//Map: Jumps 	 /map/Jumps.xml.aspx
		Document getJumps() throws JEveConnectionException, JEveParseException;
		
		//Map: Kills 	/map/Kills.xml.aspx
		Document getKills() throws JEveConnectionException, JEveParseException;
		
		//Server: ServerStatus 	/Server/ServerStatus.xml.aspx
		Document getServerStatus() throws JEveConnectionException, JEveParseException;
	
	
	/*--[ API V3 ]--*/
		
		//Not out yet....
}
