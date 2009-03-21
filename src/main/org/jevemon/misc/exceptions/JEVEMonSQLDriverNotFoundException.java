package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonSQLDriverNotFoundException extends JEVEMonException {
	public JEVEMonSQLDriverNotFoundException() {
	}

	public JEVEMonSQLDriverNotFoundException(String message) {
		super(message);
	}

	public JEVEMonSQLDriverNotFoundException(Throwable cause) {
		super(cause);
	}

	public JEVEMonSQLDriverNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
