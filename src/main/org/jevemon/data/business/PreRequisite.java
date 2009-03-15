package org.jevemon.data.business;

import org.jevemon.data.dao.SkillMap;
import org.jevemon.misc.exceptions.JEVEMonException;


public class PreRequisite {
	
	////////////////////
	// private fields //
	////////////////////
	private int typeID;
	private int requiredLevel;
	
	//////////////////
	// constructors //
	//////////////////
	public PreRequisite(){
		
	}
	
	public PreRequisite(int typeID, int requiredLevel){
		this.typeID = typeID;
		this.requiredLevel = requiredLevel;
	}	
	
	////////////////////
	// public methods //
	////////////////////
	public Skill getSkill() throws JEVEMonException{
		return SkillMap.getInstance().getSkill(typeID);
	}

	/////////////
	// getters //
	/////////////
	public int getRequiredLevel() {
		return requiredLevel;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	
	
	
	/////////////
	// setters //
	/////////////	
	public void setRequiredLevel(int levelRequired) {
			this.requiredLevel = levelRequired;
	}
	public int getTypeID() {
		return typeID;
	}
	
	
	
}
