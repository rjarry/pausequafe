package org.jevemon.model.items.skilltree;

import java.util.TreeMap;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.model.items.ItemGroup;

public class SkillTree {
	
	////////////////////
	// private fields //
	////////////////////
	private static SkillTree tree = null;
	private TreeMap<Integer, ItemGroup> groups = null;
	
	/////////////////
	// constructor //
	/////////////////
	private SkillTree() throws JEVEMonException{
		buildTree();
	}

	////////////////////
	// public methods //
	////////////////////
	public void addGroup(ItemGroup group){
		groups.put(Integer.valueOf(group.getGroupID()), group);
	}
	
	public static SkillTree getInstance() throws JEVEMonException{
		if (tree == null){
			tree = new SkillTree();
			return tree;
		} else {
			return tree;
		}
	}
	
	public String toString(){
		String print = "----------SKILL TREE----------\n\n";
		
		for(ItemGroup group : this.groups.values()){
			print += group.toString() + "\n\n";
		}

		return print;
	}
	
	/////////////
	// getters //
	/////////////
	public ItemGroup getGroup(int groupID){
		return groups.get(Integer.valueOf(groupID));
	}

	public TreeMap<Integer, ItemGroup> getAllGroups(){
		return groups;
	}
		
	/////////////////////
	// private methods //
	/////////////////////
	private void buildTree() throws JEVEMonException {
		groups = SkillTreeParser.parse(Constants.SKILL_TREE_FILE_LOCATION);
	}
	

}
