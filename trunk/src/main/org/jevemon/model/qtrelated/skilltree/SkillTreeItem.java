package org.jevemon.model.qtrelated.skilltree;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.items.skillmap.Skill;
import org.jevemon.model.items.skillmap.SkillMap;

public class SkillTreeItem {
	
	////////////////////
	// private fields //
	////////////////////
	private Skill skill;
	private SkillTreeGroup parent = null;
	
	/////////////////
	// constructor //
	/////////////////
	public SkillTreeItem(Skill skill, SkillTreeGroup parent) {
		this.skill = skill;
		this.parent = parent;
	}

	
	public static LinkedList<SkillTreeItem> buildGroup(SkillTreeGroup parent, String groupName) 
																		throws JEVEMonException {
		LinkedList<SkillTreeItem> list = new LinkedList<SkillTreeItem>();

		for(Skill skill : SkillMap.getInstance().getGroup(groupName)){
			list.add(new SkillTreeItem(skill, parent));
		}

		return list;
	}

	
	/////////////
	// getters //
	/////////////	
	public Skill getSkill() {
		return skill;
	}

	public SkillTreeGroup getParent() {
		return parent;
	}
	
	
	/////////////
	// setters //
	/////////////
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public void setParent(SkillTreeGroup parent) {
		this.parent = parent;
	}
}