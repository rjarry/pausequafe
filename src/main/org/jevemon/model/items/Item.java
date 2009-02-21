package org.jevemon.model.items;

import java.util.LinkedList;

public abstract class Item implements Comparable<Item> {
	
	//////////////////////
	// protected fields //
	//////////////////////	
	protected int groupID;
	protected String groupName;
	protected int typeID;
	protected String typeName;
	protected String description;
	protected final LinkedList<PreRequisite> preReqs = new LinkedList<PreRequisite>();

    /////////////////
    // constructor //
    /////////////////
    public Item(){
    }
	
    ////////////////////
    // public methods //
    ////////////////////
    public void addPreRequisite(PreRequisite preReq){
    	preReqs.add(preReq);
    }
    
	@Override
	public int compareTo(Item o) {
		return this.typeName.compareTo(o.getTypeName());
	}
	
    /////////////
    // getters //
    /////////////
    public int getGroupID() {
    	return groupID;
    }
    public String getGroupName() {
    	return groupName;
    }
    public int getTypeID() {
    	return typeID;
    }
    public String getTypeName() {
    	return typeName;
    }
    public String getDescription() {
    	return description;
    }
    public LinkedList<PreRequisite> getPreReqs() {
    	return preReqs;
    }
    
	/////////////
    // setters //
    /////////////
    public void setGroupID(int groupID) {
    	this.groupID = groupID;
    }
    public void setGroupName(String groupName) {
    	this.groupName = groupName;
    }
    public void setTypeID(int typeID) {
    	this.typeID = typeID;
    }
    public void setTypeName(String typeName) {
    	this.typeName = typeName;
    }
    public void setDescription(String description) {
    	this.description = description;
    }
}
