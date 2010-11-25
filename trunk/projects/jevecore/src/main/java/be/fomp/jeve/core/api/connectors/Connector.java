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

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.jdom.Document;

import be.fomp.jeve.core.api.url.ApiUrl;
import be.fomp.jeve.core.config.Configuration;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.exceptions.JEveParseException;
import be.fomp.jeve.core.util.ParameterString;
import be.fomp.jeve.core.util.Parser;
import be.fomp.jeve.core.util.Proxy;
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
 * This is the main connector class which connects to the eve api. 
 * All other connectors inherit from it.
 * 
 * @author Sven Meys
 */
abstract class Connector {
	
	/**
	 * Constructor which takes a proxy setting. This configures the correct 
	 * proxy settings to ensure the client can access the internet.
	 * 
	 * @param proxy The proxy settings
	 * @throws JEveException
	 */
	Connector(Configuration config) {
		
		Proxy proxy = null;
		String proxyHost = "";
		String proxyPort = "";
		
		if(config != null) {
			//[Fetch the proxy settings]
			proxy = config.getProxyConfiguration();
			
		    if (proxy != null){
		    	//If null gives back an empty string
		    	proxyHost = proxy.getHost() == null?"":proxy.getHost();
		    	//If equal or below zero gives back an empty string
		    	proxyPort = proxy.getPort() <= 0   ?"":String.format("%d",proxy.getPort());
		    
		    
			    //[Use the proxy settings]
			    //Can be null to remove settings
			    Authenticator.setDefault(proxy.getAuthenticator());
		    } else {
		    	Authenticator.setDefault(null);
		    }
		//If config == null proxy == null and auth == null
		} else {
	    	Authenticator.setDefault(null);
	    }
	    
	    Properties props = System.getProperties();
	    props.setProperty("http.proxyHost",proxyHost);
	    props.setProperty("http.proxyPort",proxyPort);
	}

	/**
	 * Makes a call to the EVE-Online API that returns an XML document
	 * @param urlBase The API url
	 * @param parameters The parameters to be added to the url
	 * @return An XML document
	 * @throws JEveConnectionException When the connection fails
	 * @throws JEveException When the parsing goes bad
	 */
	protected final Document getDocumentData(ApiUrl urlBase, ParameterString parameters) throws JEveConnectionException, JEveParseException
	{
		InputStream in = connect(urlBase, parameters);
		
		return Parser.parseDocument(in);
	}
	
	/**
	 * Makes a call to the EVE-Online API that returns an XML document
	 * @param urlBase The API url
	 * @return An XML document
	 * @throws JEveConnectionException When the connection fails
	 * @throws JEveException When the parsing goes bad
	 */
	protected final Document getDocumentData(ApiUrl urlBase) throws JEveConnectionException, JEveParseException
	{
		return getDocumentData(urlBase, null);
	}
	
	/**
	 * Makes a call to the EVE-Online API that returns an image
	 * @param urlBase The API url
	 * @param parameters The parameters to be added to the url
	 * @return An image
	 * @throws JEveConnectionException When the connection fails
	 * @throws JEveException When the parsing goes bad
	 */
	protected final Image getImageData(ApiUrl urlBase, ParameterString parameters) throws JEveConnectionException, JEveParseException
	{
		try {
			InputStream in = connect(urlBase, parameters);
			
			return ImageIO.read(in);
		} catch (IOException ioe){
			throw new JEveParseException("Unable to read image",ioe);
		}
	}
	
	/**
	 * Retrieves data from the web using the specified url.
	 * This method can take parameters to be put in the url 
	 * but it is not required.
	 * 
	 * @param urlBase The url to retrieve data from
	 * @param parameters Parameters to be added to the url
	 * @return An InputStream containing the retrieved content
	 * @throws JEveConnectionException
	 */
	 private final InputStream connect(ApiUrl urlBase, ParameterString parameters) throws JEveConnectionException{
	
		// Set up the reference variables
		URL url = null;
		URLConnection connection = null;
		Writer out = null;
		
		try {
			// Initialize the url
			url = new URL(urlBase.getUrl());
			
			// Open a connection
		    connection = url.openConnection();
		    		    
		    // If parameters are given, they are written as output here
		    if(parameters != null)
		    {
			    connection.setDoOutput(true);
			    connection.setConnectTimeout(5000);
			    
			    connection.connect();
			    
			    out = new OutputStreamWriter(connection.getOutputStream());
			    
			    //Writing the parameters to the url
			    out.write(parameters.toString());
			    
			    //Flushing the stream. This must be done in order to ensure that the parameters all went through.
			    out.flush();
		    }
		    return connection.getInputStream();
		    
		} catch (MalformedURLException mue)  {
			throw new JEveConnectionException("Invalid url",mue); 
		} catch (SocketTimeoutException ste)            {
			throw new JEveConnectionException("Proxy configuration error",ste);	
		} catch (IOException ioe)            {
			throw new JEveConnectionException("Unable to connect to website",ioe);
		} finally {
			//Closing the streams
			try {
				if(out != null) out.close();
			} catch (Exception e) {
				System.err.println("Failed to close stream\n" + e.getMessage());
			}
		}
	}	
}
