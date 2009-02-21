package org.jevemon.model.items;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.items.skillmap.Skill;
import org.jevemon.model.items.skillmap.SkillMap;


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
