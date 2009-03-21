package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonDatabaseFileCorrupted extends JEVEMonException {

	public JEVEMonDatabaseFileCorrupted() {
	}

	public JEVEMonDatabaseFileCorrupted(String message) {
		super(message);
	}

	public JEVEMonDatabaseFileCorrupted(Throwable cause) {
		super(cause);
	}

	public JEVEMonDatabaseFileCorrupted(String message, Throwable cause) {
		super(message, cause);
	}

}
