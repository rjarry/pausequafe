package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe a market group
 * A market group is an element of the market that contains
 * either other market groups or eve items
 * 
 * @author Gobi
 */
public class MarketGroup {

	// ////////////////////
	// protected fields //
	// ////////////////////
	private int groupID;
	private String groupName;
	private boolean itemContainer;
	private List<Integer> children = new ArrayList<Integer>();

	// ///////////////
	// constructor //
	// ///////////////
	public MarketGroup() {
		super();
	}

	public MarketGroup(int groupID, String groupName, boolean containsItem) {
		super();
		this.groupID = groupID;
		this.groupName = groupName;
		this.itemContainer = containsItem;
	}

	// //////////////////
	// public methods //
	// //////////////////
	public Integer childAt(int position) {
		return children.get(position);
	}

	public int childCount() {
		return children.size();
	}
	
	public void addChild(Integer child) {
		if (!children.contains(child)) {
			children.add(child);
		}
	}

	public void addChildren(List<Integer> newChildren) {
		for (Integer child : newChildren) {
			addChild(child);
		}
	}

	public void removeChild(Integer child) {
		children.remove(child);
	}

	public void removeChildAt(int position) {
		children.remove(position);
	}

	public void clearChildren() {
		children.clear();
	}
	
    @Override
	public String toString() {
    	String result = "";
    	result += groupID + " : " + groupName + " : ";
    	for(Integer child : children){
    		result += child + ";";
    	}
		return result;
	}

	// ///////////
	// getters //
	// ///////////
	public boolean isItemContainer() {
		return itemContainer;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getGroupID() {
		return groupID;
	}
	public List<Integer> getChildren(){
		return children;
	}
	

	// ///////////
	// setters //
	// ///////////
	public void setItemContainer(boolean containsItem) {
		this.itemContainer = containsItem;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
}
