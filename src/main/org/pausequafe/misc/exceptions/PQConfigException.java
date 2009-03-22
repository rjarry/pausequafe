package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQConfigException extends PQException {

	public PQConfigException() {
	}

	public PQConfigException(String message) {
		super(message);
	}

	public PQConfigException(Throwable cause) {
		super(cause);
	}

	public PQConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
