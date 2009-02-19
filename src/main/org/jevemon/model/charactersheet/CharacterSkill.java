package org.jevemon.model.charactersheet;

public class CharacterSkill {
	
	////////////////////
	// private fields //
	////////////////////
	private int typeID = 0;
	private int skillPoints = 0;
	private int level = 0;
	
	//////////////////
	// constructors //
	//////////////////
	public CharacterSkill() {
	}

	/////////////
	// getters //
	/////////////
	public int getTypeID() {
		return typeID;
	}

	public int getSkillPoints() {
		return skillPoints;
	}

	public int getLevel() {
		return level;
	}
	
	/////////////
	// setters //
	/////////////
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	////////////////////
	// public methods //
	////////////////////
	
	public String toString(){
		return "typeID=" + typeID + " skillPoints=" + skillPoints + " level=" + level;
	}
}
