package org.jevemon.misc.exceptions;

@SuppressWarnings("serial")
public class JEVEMonConfigException extends JEVEMonException {

	public JEVEMonConfigException() {
	}

	public JEVEMonConfigException(String message) {
		super(message);
	}

	public JEVEMonConfigException(Throwable cause) {
		super(cause);
	}

	public JEVEMonConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
