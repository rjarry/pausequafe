package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a generic class describing all Eve items.<br><br>
 * There's inheritage to add specific attributes to the different categories of items :
 * 
 * <ul><li>Skills
 * <li>Modules
 * <li>Ships
 * </ul>
 * 
 * @author diabeteman
 */
public class Item {
	
	//////////////////////
	// protected fields //
	//////////////////////	
	protected int typeID;
	protected String typeName;
	protected int metaGroupID;
	protected int metaLevel;
	
	protected List<PreRequisite> preReqs = null;
	protected PreRequisite preRequisite1 = new PreRequisite();
	protected PreRequisite preRequisite2 = new PreRequisite();
	protected PreRequisite preRequisite3 = new PreRequisite();
	

    /////////////////
    // constructor //
    /////////////////
    public Item(){
    }

	public Item(int typeID, String typeName, int metaGroupID) {
		this.typeID = typeID;
		this.typeName = typeName;
		this.metaGroupID = metaGroupID;
	}

	////////////////////
    // public methods //
    ////////////////////
    public void addPreRequisite(PreRequisite preReq){
    	preReqs.add(preReq);
    }
    
    public int getRemainingTrainingTime(CharacterSheet sheet){
    	//TODO implement to sort by training time
    	return 0;
    }
    
    @Override
	public String toString() {
    	String result = "";
    	result += typeID + " : " + typeName + " : ";
		return result;
	}

	/////////////
    // getters //
    /////////////
    public int getTypeID() {
    	return typeID;
    }
    
    public String getTypeName() {
    	return typeName;
    }
    
    public List<PreRequisite> getPreReqs() {
    	if(preReqs == null){
    		preReqs = new ArrayList<PreRequisite>();
    		if(preRequisite1.getTypeID()!=-1){
    			preReqs.add(preRequisite1);
    		}
    		if(preRequisite2.getTypeID()!=-1){
    			preReqs.add(preRequisite2);
    		}
    		if(preRequisite3.getTypeID()!=-1){
    			preReqs.add(preRequisite3);
    		}
    	}
    	return preReqs;
    }
    
    public PreRequisite youDontWantToKnowWhatThisIs(int i){
    	switch(i){
    	case 1 : return preRequisite1 ;
    	case 2 : return preRequisite2 ;
    	case 3 : return preRequisite3 ;
    	default : return null;
    	}
    }
    
    public int getMetaGroupID(){
    	return metaGroupID;
    }
    
    public int getMetaLevel(){
    	return metaLevel;
    }
    
	/////////////
    // setters //
    /////////////
	public void setMetaGroupID(int metaGroupID){
		this.metaGroupID = metaGroupID;
	}
	
	public void setMetaLevel(int metaLevel) {
		this.metaLevel = metaLevel;
	}

}
