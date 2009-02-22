package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonFileNotFoundException extends JEVEMonException {

	public JEVEMonFileNotFoundException() {
	}

	public JEVEMonFileNotFoundException(String message) {
		super(message);
	}

	public JEVEMonFileNotFoundException(Throwable cause) {
		super(cause);
	}

	public JEVEMonFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
