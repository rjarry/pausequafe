package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQFileNotFoundException extends PQException {

	public PQFileNotFoundException() {
	}

	public PQFileNotFoundException(String message) {
		super(message);
	}

	public PQFileNotFoundException(Throwable cause) {
		super(cause);
	}

	public PQFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
