package org.pausequafe.data.business;



public class PreRequisite {
	
	////////////////////
	// private fields //
	////////////////////
	private int typeID = -1;
	private int requiredLevel = -1;
	
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
