package org.pausequafe.data.business;

/**
 * A specific Item which has 3 additional attributes.
 * <ul><li>the rank (or time multiplier)
 * <li>a primary attribute (used for training speed calculation)
 * <li>a secondary attribute (used for training speed calculation)
 * </ul>
 * @author diabeteman
 *
 */
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
    // for test purposes
	public String toString(){
		String print = "";
		print += typeName + " (rank " + rank + ") - " + "\n";
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
