package org.pausequafe.data.business;

public class SkillPlan {

	public final static int NOID=-1;
	public final static String NONAME = "No name";
	
	////////////
	// fields //
	////////////
	int characterID;
	int id;
	int index;
	String name;

	/////////////////
	// constructor //
	/////////////////
	public SkillPlan(int characterID, int id, int index, String name) {
		super();
		this.characterID = characterID;
		this.id = id;
		this.index = index;
		this.name = name;
	}


	////////////////////
	// public methods //
	////////////////////
	@Override
	public String toString() {
		return name;
	}

	/////////////
	// getters //
	/////////////
	public int getCharacterID() {
		return characterID;
	}

	public int getId() {
		return id;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	/////////////
	// setters //
	/////////////
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

}
