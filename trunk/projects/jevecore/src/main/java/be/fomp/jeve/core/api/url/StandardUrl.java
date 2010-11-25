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
package be.fomp.jeve.core.api.url;

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
 * Enumeration containing the urls for the EVE-Online API 
 * which require the standard access API key.
 * 
 * @author Sven Meys
 *
 */
public enum StandardUrl implements ApiUrl {
	ALLIANCE_LIST			("http://api.eve-online.com/eve/AllianceList.xml.aspx"),
	CHARACTER_ID			("http://api.eve-online.com/eve/CharacterID.xml.aspx"),
	CONQUERABLE_STATION_LIST("http://api.eve-online.com/eve/ConquerableStationList.xml.aspx"),
	CORPORATION_SHEET		("http://api.eve-online.com/corp/CorporationSheet.xml.aspx"),
	ERROR_LIST				("http://api.eve-online.com/eve/ErrorList.xml.aspx"),
	JUMPS					("http://api.eve-online.com/map/Jumps.xml.aspx"),
	KILLS					("http://api.eve-online.com/map/Kills.xml.aspx"),
	PORTRAIT				("http://img.eve.is/serv.asp"),
	REF_TYPES				("http://api.eve-online.com/eve/RefTypes.xml.aspx"),
	SKILL_TREE				("http://api.eve-online.com/eve/SkillTree.xml.aspx"),
	SOVEREIGNITY			("http://api.eve-online.com/map/Sovereignty.xml.aspx"), 
	SERVER_STATUS			("http://api.eve-online.com/Server/ServerStatus.xml.aspx"),
	;
	
	private String url;
	
	private StandardUrl(String url)	{ this.url = url; }
	
	public String getUrl()
	{
		return url;
	}
}