package be.fomp.jeve.core.exceptions;
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
 * An exception which is thrown when parsing an inputstream to documents failed.
 * 
 * @version 1.0
 * @author Sven Meys
 * 
 */
@SuppressWarnings("serial")
public class JEveParseException extends JEveException {

	public JEveParseException() {
	}

	public JEveParseException(String message) {
		super(message);
	}

	public JEveParseException(Throwable cause) {
		super(cause);
	}

	public JEveParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
