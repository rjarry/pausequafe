package org.jevemon.data.business;

import java.util.ArrayList;
import java.util.List;
/**
 * This is a generic class describing all Eve items.<br><br>
 * There's inheritage to add specific attributes to the differents categories of items :
 * 
 * <ul><li>Skills
 * <li>Modules
 * <li>Ships
 * </ul>
 * 
 * @author diabeteman
 */
public class Item implements Comparable<Item> {
	
	//////////////////////
	// protected fields //
	//////////////////////	
	protected int typeID;
	protected String typeName;
	protected int marketGroupID;
	protected String description;
	protected final List<PreRequisite> preReqs = new ArrayList<PreRequisite>();
	protected String groupName; //TODO à mon avis inutile 

    /////////////////
    // constructor //
    /////////////////
    public Item(){
    }
	
    public Item(int typeID, String typeName, int marketGroupID, String description) {
		super();
		this.marketGroupID = marketGroupID;
		this.typeID = typeID;
		this.typeName = typeName;
		this.description = description;
	}

	////////////////////
    // public methods //
    ////////////////////
    public void addPreRequisite(PreRequisite preReq){
    	preReqs.add(preReq);
    }
    
    /**
     * Used for lexical sorting of the items when exporting sub-parts of the database.
     * 
     * @param o 
     * 		The item to be compared with.
     * 
     * @return
     * 		
     * 		<li>A positive value if the Item o is "before" (in alphabetical order) the caller of the method.
     * 		<li>A negative value if the Item o is "after" (in alphabetical order) the caller of the method.
     * 		<li>0 if the Items have the same name.
     */
	public int compareTo(Item o) {
		return this.typeName.compareTo(o.getTypeName());
	}
	
	
    @Override
	public String toString() {
    	String result = "";
    	result += typeID + " : " + typeName + " : ";
    	result += marketGroupID + " : " + description;
		return result;
	}

	/////////////
    // getters //
    /////////////
    public int getMarketGroupID() {
    	return marketGroupID;
    }
    public int getTypeID() {
    	return typeID;
    }
    public String getTypeName() {
    	return typeName;
    }
	public String getGroupName() {
		return groupName;
	}
    public String getDescription() {
    	return description;
    }
    public List<PreRequisite> getPreReqs() {
    	return preReqs;
    }
    
	/////////////
    // setters //
    /////////////
    public void setMarketGroupID(int groupID) {
    	this.marketGroupID = groupID;
    }
    public void setTypeID(int typeID) {
    	this.typeID = typeID;
    }
    public void setTypeName(String typeName) {
    	this.typeName = typeName;
    }
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    public void setDescription(String description) {
    	this.description = description;
    }
}
