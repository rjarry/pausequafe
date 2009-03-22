package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQEveDatabaseNotFound extends PQException {

	public PQEveDatabaseNotFound() {
	}

	public PQEveDatabaseNotFound(String message) {
		super(message);
	}

	public PQEveDatabaseNotFound(Throwable cause) {
		super(cause);
	}

	public PQEveDatabaseNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
