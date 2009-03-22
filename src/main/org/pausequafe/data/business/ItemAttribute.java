package org.pausequafe.data.business;

public class ItemAttribute {
	
	
	private String attributeName;
	private String attributeCategory;
	private float value;
	
	
	
	public ItemAttribute(String attributeName, String attributeCategory,float value) {
		this.attributeName = attributeName;
		this.attributeCategory = attributeCategory;
		this.value = value;
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
}
