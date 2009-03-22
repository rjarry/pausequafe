package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQDatabaseFileCorrupted extends PQException {

	public PQDatabaseFileCorrupted() {
	}

	public PQDatabaseFileCorrupted(String message) {
		super(message);
	}

	public PQDatabaseFileCorrupted(Throwable cause) {
		super(cause);
	}

	public PQDatabaseFileCorrupted(String message, Throwable cause) {
		super(message, cause);
	}

}
