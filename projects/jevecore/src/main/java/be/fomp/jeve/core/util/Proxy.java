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

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy.Type;
import java.security.InvalidParameterException;
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
 * Util class used to store Proxy parameters.
 * 
 * @version 1.0
 * @author Sven Meys
 * 
 */
public class Proxy {
	/**
	 * The host URL
	 */
	private String host;
	/**
	 * The port used to connect to the internet
	 */
	private int port;
	/**
	 * The proxy type
	 */
	private Type type;
	/**
	 * Authentication data. contains username and password
	 */
	private SimpleAuthenticator authenticator = null;
	
	/**
	 * Creates a proxy that does not require any authentication data.
	 * @param type The proxy type
	 * @param host The proxy host
	 * @param port The proxy port
	 */
	public Proxy(Type type, String host, int port)
	{
		if(type == null || host == null || port < 0)
			throw new InvalidParameterException("Parameters invalid");
		
		this.type = type;
		this.host = host;
		this.port = port;
	}
	
	/**
	 * Creates a proxy with authentication data.
	 * @param type The proxy type
	 * @param host The proxy host
	 * @param port The proxy port
	 * @param username The proxy username
	 * @param password The proxy password
	 */
	public Proxy(Type type, String host, int port, String username, String password)
	{
		if(type == null || host == null || port < 0)
			throw new InvalidParameterException("Parameters invalid");
		
		if(username == null || password == null)
			throw new InvalidParameterException("Username or password can not be null");
		
		this.type = type;
		this.host = host;
		this.port = port;
		
		authenticator = new SimpleAuthenticator(username, password);
	}

	/**
	 * Getter for host
	 * @return The proxy host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Getter for port
	 * @return The proxy port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Getter for type
	 * @return The proxy type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Getter for authenticator
	 * @return The proxy authenticator
	 */
	public Authenticator getAuthenticator() {
		return authenticator;
	}
	
	public String getUsername() {
		return authenticator.username;
	}

	public String getPassword() {
		return authenticator.password;
	}
	
	
	/**
	 * Authenticator inner class which stores username and password.
	 * This is used to provide login and password when connecting to the internet.
	 * @author Sven Meys
	 */
	private static class SimpleAuthenticator extends Authenticator
	{
	   private String username, password;
	                     
	   public SimpleAuthenticator(String username,String password)
	   {
	      this.username = username;
	      this.password = password;
	   }
	   
	   protected PasswordAuthentication getPasswordAuthentication()
	   {
	      return new PasswordAuthentication(
	             username,password.toCharArray());
	   }

	}
}
