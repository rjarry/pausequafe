package org.pausequafe.misc.exceptions;

@SuppressWarnings("serial")
public class PQUserDatabaseFileCorrupted extends PQException {

	public PQUserDatabaseFileCorrupted() {
	}

	public PQUserDatabaseFileCorrupted(String message) {
		super(message);
	}

	public PQUserDatabaseFileCorrupted(Throwable cause) {
		super(cause);
	}

	public PQUserDatabaseFileCorrupted(String message, Throwable cause) {
		super(message, cause);
	}

}
