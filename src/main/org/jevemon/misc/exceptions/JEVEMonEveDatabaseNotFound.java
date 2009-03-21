package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonEveDatabaseNotFound extends JEVEMonException {

	public JEVEMonEveDatabaseNotFound() {
	}

	public JEVEMonEveDatabaseNotFound(String message) {
		super(message);
	}

	public JEVEMonEveDatabaseNotFound(Throwable cause) {
		super(cause);
	}

	public JEVEMonEveDatabaseNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
