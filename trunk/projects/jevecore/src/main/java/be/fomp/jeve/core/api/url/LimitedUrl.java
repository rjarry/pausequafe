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
 * which require the limited access API key.
 * 
 * @author Sven Meys
 *
 */
public enum LimitedUrl implements ApiUrl {
	CHARACTER_SHEET			("http://api.eve-online.com/char/CharacterSheet.xml.aspx"),
	CHARACTERS			("http://api.eve-online.com/account/Characters.xml.aspx"),
	CORPORATION_SHEET("http://api.eve-online.com/corp/CorporationSheet.xml.aspx"),
	SKILL_IN_TRAINING		("http://api.eve-online.com/char/SkillInTraining.xml.aspx"),
	SKILL_QUEUE                ("http://api.eve-online.com/char/SkillQueue.xml.aspx"),
	;
	
	private String url;
	
	private LimitedUrl(String url)	{ this.url = url; }
	
	public String getUrl()
	{
		return url;
	}
}
