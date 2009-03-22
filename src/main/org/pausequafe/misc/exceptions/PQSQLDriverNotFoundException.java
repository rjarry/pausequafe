package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQSQLDriverNotFoundException extends PQException {
	public PQSQLDriverNotFoundException() {
	}

	public PQSQLDriverNotFoundException(String message) {
		super(message);
	}

	public PQSQLDriverNotFoundException(Throwable cause) {
		super(cause);
	}

	public PQSQLDriverNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
