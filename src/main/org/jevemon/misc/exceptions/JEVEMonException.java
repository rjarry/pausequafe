package org.jevemon.misc.exceptions;

public class JEVEMonException extends Exception {
	
	private String message;

	public JEVEMonException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
