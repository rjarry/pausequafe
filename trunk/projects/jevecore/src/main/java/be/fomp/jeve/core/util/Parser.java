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

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import be.fomp.jeve.core.exceptions.JEveException;
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
 * Util class used to convert inputstreams to the required Object format.
 * 
 * @version 1.0
 * @author Sven Meys
 * 
 */
public class Parser {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	
	static {
		dateFormat.setCalendar(calendar);
	}
	private Parser() {}

	/**
	 * Reads an xml document from a stream and parses it to a Document object.
	 * 
	 * @param aStream The inputstream
	 * @return The parsed Document
	 * 
	 * @throws JEveException
	 * @author Sven Meys
	 */
	public static Document parseDocument(InputStream aStream) throws JEveParseException {
		try {
			// Parse the object to a document object
			Document d = new SAXBuilder().build(aStream);
			
			// Close the stream
			aStream.close();
			
			// Check for error
			if(d.getRootElement().getChildren("error").size() > 0)
				throw new JEveParseException(((Element)d.getRootElement().getChildren("error").get(0)).getValue());
			return d;

		} catch (JDOMException e) {throw new JEveParseException(e);} 
		  catch (IOException e)  {throw new JEveParseException(e);} 
	}
	
	public static Date parseDate(String date) throws JEveParseException
	{
		try {
			return dateFormat.parse(date);
		} catch (ParseException pe) {
			throw new JEveParseException("Unable to parse date", pe);
		}
	}
}
