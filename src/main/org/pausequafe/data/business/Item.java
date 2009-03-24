package org.pausequafe.data.business;

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
	protected String description;
	protected float basePrice;
	protected String icon;
	protected int metaGroupID;
	protected int metaLevel;
	
	protected List<ItemAttribute> attributeList = new ArrayList<ItemAttribute>();
	
	protected List<PreRequisite> preReqs = null;
	private PreRequisite preRequisite1 = new PreRequisite();
	private PreRequisite preRequisite2 = new PreRequisite();
	private PreRequisite preRequisite3= new PreRequisite();
	

    /////////////////
    // constructor //
    /////////////////
    public Item(){
    }
	


	public Item(int typeID, String typeName, String description,
								float basePrice, float radius, float mass,
								float volume, float capacity) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
		this.description = description;
		this.basePrice = basePrice;
		
		attributeList.add(new ItemAttribute("Radius", "Structure", radius, "m"));
		attributeList.add(new ItemAttribute("Mass", "Structure", mass, "kg"));
		attributeList.add(new ItemAttribute("Volume", "Structure", volume, "m3"));
		attributeList.add(new ItemAttribute("Capacity", "Structure", capacity, "m3"));
		
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
    public String getDescription() {
    	return description;
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
    
    public float getBasePrice() {
    	return basePrice;
    }
    public String getIcon() {
    	return icon;
    }
    public int getMetaGroupID(){
    	return metaGroupID;
    }
    
    public List<ItemAttribute> getAttributeList(){
    	return attributeList;
    }
	/////////////
    // setters //
    /////////////


	public void addAttribute(ItemAttribute attribute) {
		attributeList.add(attribute);
	}
	
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public void setMetaGroupID(int metaGroupID){
		this.metaGroupID = metaGroupID;
	}



	public int getMetaLevel() {
		return metaLevel;
	}



	public void setMetaLevel(int metaLevel) {
		this.metaLevel = metaLevel;
	}

}
