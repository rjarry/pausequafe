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
 * An exception which is thrown when something went wrong initializing the configuration.
 * 
 * @version 1.0
 * @author Sven Meys
 * 
 */
@SuppressWarnings("serial")
public class JEveConfigurationException extends JEveException {

	public JEveConfigurationException() {
	}

	public JEveConfigurationException(String message) {
		super(message);
	}

	public JEveConfigurationException(Throwable cause) {
		super(cause);
	}

	public JEveConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
