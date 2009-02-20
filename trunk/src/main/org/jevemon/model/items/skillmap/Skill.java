package org.jevemon.model.items.skillmap;

import org.jevemon.model.items.Item;

public class Skill extends Item {

	////////////////////
	// private fields //
	////////////////////	
	private int rank;
	private String primaryAttribute;
	private String secondaryAttribute;
	
	/////////////////
    // constructor //
    /////////////////
    public Skill(){
    	super();
    }

    ////////////////////
    // public methods //
    ////////////////////
	public String toString(){
		String print = "";
		print += typeName + " (rank " + rank + ") - " + groupName + "\n";
		print += description + "\n";
		print += "Primary attribute : " + primaryAttribute + "\n";
		print += "Secondary attribute : " + secondaryAttribute + "\n";
		return print;
	}
	
	/////////////
    // getters //
    /////////////	
	public int getRank() {
		return rank;
	}
	public String getPrimaryAttribute() {
		return primaryAttribute;
	}
	public String getSecondaryAttribute() {
		return secondaryAttribute;
	}
	/////////////
    // setters //
    /////////////
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void setPrimaryAttribute(String primaryAttribute) {
		this.primaryAttribute = primaryAttribute;
	}
	public void setSecondaryAttribute(String secondaryAttribute) {
		this.secondaryAttribute = secondaryAttribute;
	}



}
