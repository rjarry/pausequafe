package org.jevemon.model.items.skilltreemodel;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.character.sheet.CharacterSheet;

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
	public SkillTreeGroup(String groupName, CharacterSheet sheet) throws JEVEMonException{
		this.groupName = groupName;
		skillList = SkillTreeItem.buildGroup(groupName, sheet);
	}

	public int getTotalGroupSP(){
		int groupSP = 0;
		
		for(SkillTreeItem skill : skillList){
			if (skill.isKnown()){
				groupSP += skill.getCharSkill().getSkillPoints();
			}
		}
			
		return groupSP;
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
