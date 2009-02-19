package org.jevemon.model.items;

import java.util.TreeMap;

import org.jevemon.model.items.skilltree.Skill;

public class ItemGroup {
	
	private int groupID;
	private String groupName;
	private final TreeMap<Integer, Item> items = new TreeMap<Integer, Item>();
	
	
	//////////////////
	// constructors //
	//////////////////
	public ItemGroup(){	
	}
	
	public ItemGroup(int groupID, String groupName) {
		this.groupID = groupID;
		this.groupName = groupName;
	}

	////////////////////
	// public methods //
	////////////////////
	public void addItem(Item i){
		items.put(Integer.valueOf(i.getTypeID()), i);
	}
	
	public Item getItem(int typeID){
		return items.get(Integer.valueOf(typeID));
	}
	
	public String toString(){
		String print = groupName 
				  + "\n-------------------------------\n";
		
		
		for(Item item : items.values()){
			Skill skill = (Skill) item;
			print += skill.toString() + "\n";
		}

		return print;
	}

	
	/////////////
	// getters //
	/////////////
	public int getGroupID() {
		return groupID;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public TreeMap<Integer, Item> getAllItems(){
		return items;
	}

	/////////////
	// setters //
	/////////////
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
