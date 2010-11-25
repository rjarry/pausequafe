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
package be.fomp.jeve.core.config;

import java.util.Properties;

import be.fomp.jeve.core.exceptions.JEveConfigurationException;
import be.fomp.jeve.core.exceptions.JEveException;

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
 * Interface defining a Configuration object for eve.
 * 
 * @author Sven Meys
 *
 */
public interface JEveConfiguration {

	/**
	 * Loads the configuration from the filesystem.
	 * 
	 * @throws JEveException
	 */
	void loadConfiguration() throws JEveConfigurationException;
	
	/**
	 * Stores the cofiguration to the filesystem.
	 * 
	 * @throws JEveException
	 */
	void storeConfiguration() throws JEveConfigurationException;
	
	/**
	 * Fetches the properties contained in the configuration file.
	 * 
	 * @return configuration properties
	 */
	Properties getConfigurationProperties();
	
	public static final String COMMENT =
"##############################################################################\n"+
"#   ______   ______                                                           #\n"+
"#  /\\__  __\\/\\ \\___\\                                                          #\n"+
"#  \\/_/\\ \\_/\\ \\ \\__/    __   __     __                                        #\n"+
"#     \\ \\ \\  \\ \\ \\__\\  /\\ \\ /\\ \\  /'__`\\                                      #\n"+
"#     _\\_\\ \\  \\ \\ \\_/_ \\ \\ \\\\_\\ \\/\\  __/                                      #\n"+
"#    /\\____/   \\ \\_____\\\\ \\_____/\\ \\____\\                                     #\n"+
"#    \\_/__/     \\/_____/ \\/____/  \\/____/                                     #\n"+
"#                                                                             #\n"+
"#    This file is part of the JEVE core API.                                  #\n"+
"#                                                                             #\n"+
"#    JEve is free software; you can redistribute it and/or modify             #\n"+
"#    it under the terms of the GNU General Public License as published by     #\n"+
"#    the Free Software Foundation; either version 3 of the License, or        #\n"+
"#    (at your option) any later version.                                      #\n"+
"#                                                                             #\n"+
"#    JEve is distributed in the hope that it will be useful,                  #\n"+
"#    but WITHOUT ANY WARRANTY; without even the implied warranty of           #\n"+
"#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            #\n"+
"#    GNU General Public License for more details.                             #\n"+
"#                                                                             #\n"+
"#    You should have received a copy of the GNU General Public License        #\n"+
"#    along with this program.  If not, see <http://www.gnu.org/licenses/>.    #\n"+
"#                                                                             #\n"+
"#    Copyright 2008 JEve Project (JEveProject@gmail.com)                      #\n"+
"#                                                                             #\n"+
"###############################################################################";
}
