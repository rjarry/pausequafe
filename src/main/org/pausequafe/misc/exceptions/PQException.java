package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQException extends Exception {

	public PQException() {
	}

	public PQException(String message) {
		super(message);
	}

	public PQException(Throwable cause) {
		super(cause);
	}

	public PQException(String message, Throwable cause) {
		super(message, cause);
	}

}
