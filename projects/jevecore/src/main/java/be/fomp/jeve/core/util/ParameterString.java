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
package be.fomp.jeve.core.util;

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
 * This util class is used to generate the parameters that need to be added to
 * the url. You can start clean or give the constructor some parameters to start
 * with.
 * 
 * @author meyssven
 * 
 */
public class ParameterString implements Encodeable {
	/**
	 * Contains the value to be kept
	 */
	private StringBuilder value = new StringBuilder();
	
	/**
	 * Default constructor
	 */
	public ParameterString() {
	}
	
	/**
	 * Constructor which appends the given parameter to value.
	 * @param parameters Parameters to set as starting value
	 */
	public ParameterString(Encodeable... parameters) {
		for (Encodeable e : parameters) {
			value.append(e.encode()).append('&');
		}
	}
	
	/**
	 * Constructor which converts a key and value to a url parameter and appends it to value.
	 * @param key The key string of the parameter
	 * @param val The value of the parameter
	 */
	public ParameterString(String key, String val) {
		key = key.replaceAll("&", "");
		val = val.replaceAll("&", "");
		value.append(key).append('=').append(val);
	}
	
	/**
	 * Constructor which converts a key and value to a url parameter and appends it to value.
	 * @param key The key string of the parameter
	 * @param val The value of the parameter
	 */
	public ParameterString(String key, int val) {
		key = key.replaceAll("&", "");
		value.append(key).append('=').append(val);
	}

	/**
	 * Adds a new parameter generated from a key-value pair to the ParameterString's value.
	 * 
	 * @param key The key string of the parameter
	 * @param val The value of the parameter
	 * @return The parameterString instance itself. Allows for chaining.
	 */
	public ParameterString addParameter(String key, String val) {
		key = key.replaceAll("&", "");
		val = val.replaceAll("&", "");
		
		if (value.length() > 0 && value.charAt(value.length() - 1) != '&')
			value.append('&');

		value.append(key).append('=').append(val);
		return this;
	}
	
	/**
	 * Adds a new parameter generated from a key-value pair to the ParameterString's value.
	 * 
	 * @param key The key string of the parameter
	 * @param val The value of the parameter
	 * @return The parameterString instance itself. Allows for chaining.
	 */
	public ParameterString addParameter(String key, int val) {
		key = key.replaceAll("&", "");
		
		if (value.length() > 0 && value.charAt(value.length() - 1) != '&')
			value.append('&');

		value.append(key).append('=').append(val);
		return this;
	}

	/**
	 * Adds a new parameter generated from an Encodeable object to the ParameterString's value.
	 * 
	 * @param encodeable an object which contains a set of parameters
	 * @return The parameterString instance itself. Allows for chaining.
	 */
	public ParameterString addParameter(Encodeable parameters) {
		StringBuilder encoded = parameters.encode();
		while(encoded != null && encoded.length()>0 && encoded.charAt(0) == '&')
			encoded.deleteCharAt(0);
		
		if (value.length() > 0 && value.charAt(value.length() - 1) != '&')
			value.append('&');

		value.append(encoded);
		return this;
	}


	public String toString() {
		return value.toString();
	}

	public StringBuilder encode() {
		return value;
	}

	public StringBuilder encode(Encodeable parameters) {
		addParameter(parameters);
		return value;
	}
}
