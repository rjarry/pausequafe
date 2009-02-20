package org.jevemon.model.items;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.items.skillmap.SkillMap;

public class PreRequisiteTree {
	
	////////////////////
	// private fields //
	////////////////////
	private LinkedList<PreRequisiteTree> preReqs = null;
	private int typeID;
	private int requiredLevel;
	
	//////////////////
	// constructors //
	//////////////////
	public PreRequisiteTree(){
		
	}
	public PreRequisiteTree(int typeID, int requiredLevel){
		this.typeID = typeID;
		this.requiredLevel = requiredLevel;
	}	
	
	////////////////////
	// public methods //
	////////////////////
	/**
	 * builds the tree
	 * 
	 * !!!!! NOT WORKING !!!!!
	 */
	public PreRequisiteTree buildTree(){
		PreRequisiteTree tree = this;
		try {
			while (true){
				tree.preReqs = SkillMap.getInstance().getSkill(tree.typeID).getPreReqs();
				if (tree.preReqs.size() == 0){
					break; // if the prerequisites for this skill are null
				} else {
					for(PreRequisiteTree child : tree.preReqs){
						child.buildTree(); // we get the prerequisites for the prerequistes
					}
				}
			}
		} catch (JEVEMonException e) {
			// we do nothing here as the exception has normaly already been handled
		}
		return tree;
	}

	/////////////
	// getters //
	/////////////
	public int getRequiredLevel() {
		return requiredLevel;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	
	
	
	/////////////
	// setters //
	/////////////	
	public void setRequiredLevel(int levelRequired) {
			this.requiredLevel = levelRequired;
	}
	public int getTypeID() {
		return typeID;
	}
	
	
	
}
