package org.jevemon.data.business;

public class ItemGroup {
	private String groupName;
	private int groupID;

	
	public ItemGroup(String groupName) {
		this.groupName = groupName;
	}
	
	
	public ItemGroup(String groupName, int groupID) {
		this.groupName = groupName;
		this.groupID = groupID;
	}
	
	
	

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getGroupID() {
		return groupID;
	}
}
