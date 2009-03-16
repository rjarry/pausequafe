package org.jevemon.data.business;

public class APIData {
	
	////////////////////
	// private fields //
	////////////////////	
	private int characterID = -1;
	private String characterName = "";
	private int userID;
	private String apiKey;

	//////////////////
    // constructors //
    //////////////////
	public APIData(int userID, String apiKey) {
		this.userID = userID;
		this.apiKey = apiKey;
	}
	
	public APIData(int characterID, int userID, String apiKey) {
		this.characterID = characterID;
		this.userID = userID;
		this.apiKey = apiKey;
	}

	public APIData(int characterID, String characterName, int userID,
			String apiKey) {
		this.characterID = characterID;
		this.characterName = characterName;
		this.userID = userID;
		this.apiKey = apiKey;
	}

	/////////////
    // getters //
    /////////////	
	public int getCharacterID() {
		return characterID;
	}
	public String getCharacterName() {
		return characterName;
	}
	public int getUserID() {
		return userID;
	}
	public String getApiKey() {
		return apiKey;
	}
	/////////////
    // setters //
    /////////////	
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
