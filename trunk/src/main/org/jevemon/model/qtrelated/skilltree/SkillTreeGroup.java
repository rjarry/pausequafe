package org.jevemon.model.qtrelated.skilltree;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;

public class SkillTreeGroup {

	////////////////////
	// private fields //
	////////////////////
	private LinkedList<SkillTreeItem> skillList = null;
	private String groupName;
	
	//////////////////
	// constructors //
	//////////////////
	public SkillTreeGroup(){
		
	}
	public SkillTreeGroup(String groupName) throws JEVEMonException{
		this.groupName = groupName;
		skillList = SkillTreeItem.buildGroup(this, groupName);
	}
	
	/////////////
	// getters //
	/////////////
	public Object getSkill(int index) {
		return skillList.get(index);
	}
	public int getSkillCount() {
		return skillList.size();
	}
	public String getGroupName(){
		return groupName;
	}
	
	/////////////
	// setters //
	/////////////
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
