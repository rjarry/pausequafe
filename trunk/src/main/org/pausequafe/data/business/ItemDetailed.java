package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.misc.util.SQLConstants;

public class ItemDetailed extends Item {

	// ////////////////////
	// protected fields //
	// ////////////////////

	protected String description;
	protected double basePrice;
	protected String icon;
	protected int portionSize;

	protected List<ItemAttribute> attributeList = new ArrayList<ItemAttribute>();

	// ///////////////
	// constructor //
	// ///////////////

	public ItemDetailed() {
		super();
	}

	public ItemDetailed(Item baseItem, String icon, String description, double basePrice,
			double radius, double mass, double volume, double capacity, int portionSize) {
		super(baseItem);

		this.icon = icon;
		this.basePrice = basePrice;
		this.description = description;
		this.portionSize = portionSize;

		attributeList.add(new ItemAttribute(SQLConstants.RADIUS_ATTID, "Radius", "Structure",
				radius, "m", 1));
		attributeList.add(new ItemAttribute(SQLConstants.MASS_ATTID, "Mass", "Structure", mass,
				"kg", 2));
		attributeList.add(new ItemAttribute(SQLConstants.VOLUME_ATTID, "Volume", "Structure",
				volume, "m3", 9));
		attributeList.add(new ItemAttribute(SQLConstants.CAPACITY_ATTID, "Capacity", "Structure",
				capacity, "m3", 9));
	}

	public ItemDetailed(Item baseItem) {
		typeID = baseItem.getTypeID();
		typeName = baseItem.getTypeName();
		metaLevel = baseItem.getMetaLevel();
		metaGroupID = baseItem.getMetaGroupID();

	}

	// //////////////////
	// public methods //
	// //////////////////
	public void addPreRequisite(PreRequisite preReq) {
		preReqs.add(preReq);
	}

	@Override
	public String toString() {
		String result = "";
		result += typeID + " : " + typeName + " : ";
		return result;
	}

	// ///////////
	// getters //
	// ///////////
	public double getBasePrice() {
		return basePrice;
	}

	public String getIcon() {
		return icon;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<ItemAttribute> getAttributeList() {
		return attributeList;
	}

	public int getPortionSize() {
		return portionSize;
	}

	// ///////////
	// setters //
	// ///////////
	public void addAttribute(ItemAttribute attribute) {
		attributeList.add(attribute);
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setPortionSize(int portionSize) {
		this.portionSize = portionSize;
	}


}
