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
	public Skill(int typeID, String typeName) {
		super(typeID,typeName,-1);
	}

    ////////////////////
    // public methods //
    ////////////////////

	
	/////////////
    // getters //
    /////////////	
	public int getRank() {
		return rank;
	}
	public void setPrimaryAttribute(String primaryAttribute) {
		this.primaryAttribute = primaryAttribute;
	}
	public void setSecondaryAttribute(String secondaryAttribute) {
		this.secondaryAttribute = secondaryAttribute;
	}
	
	/////////////
    // setters //
    /////////////
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getPrimaryAttribute() {
		return this.primaryAttribute;
	}
	public String getSecondaryAttribute() {
		return this.secondaryAttribute;
	}

}
