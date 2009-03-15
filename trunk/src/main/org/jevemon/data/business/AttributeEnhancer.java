package org.jevemon.data.business;

public class AttributeEnhancer {
	
	////////////////////
	// private fields //
	////////////////////
	private String attribute = "";
	private String augmentatorName = "";
	private int augmentatorValue = 0;
	
	//////////////////
	// constructors //
	//////////////////
	public AttributeEnhancer(){
	}

	/////////////
	// getters //
	/////////////
	public String getAttribute() {
		return attribute;
	}

	public String getAugmentatorName() {
		return augmentatorName;
	}

	public int getAugmentatorValue() {
		return augmentatorValue;
	}

	/////////////
	// setters //
	/////////////
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAugmentatorName(String augmentatorName) {
		this.augmentatorName = augmentatorName;
	}

	public void setAugmentatorValue(int augmentatorValue) {
		this.augmentatorValue = augmentatorValue;
	}


}
