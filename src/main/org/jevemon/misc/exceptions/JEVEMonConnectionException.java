package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonConnectionException extends JEVEMonException {

	public JEVEMonConnectionException() {
	}

	public JEVEMonConnectionException(String message) {
		super(message);
	}

	public JEVEMonConnectionException(Throwable cause) {
		super(cause);
	}

	public JEVEMonConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
