package org.pausequafe.data.business;

public class ItemAttribute {
	
	
	private String attributeName;
	private String attributeCategory;
	private float value;
	private String unit;
	
	
	
	public ItemAttribute(String attributeName, String attributeCategory,float value,String unit) {
		this.attributeName = attributeName;
		this.attributeCategory = attributeCategory;
		this.value = value;
		this.unit = unit;
	}

	
	
	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeCategory() {
		return attributeCategory;
	}

	public float getValue() {
		return value;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public void setValue(float value) {
		this.value = value;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}
}
