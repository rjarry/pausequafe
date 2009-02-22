package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonException extends Exception {

	public JEVEMonException() {
	}

	public JEVEMonException(String message) {
		super(message);
	}

	public JEVEMonException(Throwable cause) {
		super(cause);
	}

	public JEVEMonException(String message, Throwable cause) {
		super(message, cause);
	}

}
