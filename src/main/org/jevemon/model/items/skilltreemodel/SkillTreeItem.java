package org.jevemon.model.items.skilltreemodel;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.charactersheet.CharacterSheet;
import org.jevemon.model.charactersheet.CharacterSkill;
import org.jevemon.model.items.skillmap.Skill;
import org.jevemon.model.items.skillmap.SkillMap;

public class SkillTreeItem {
	
	////////////////////
	// private fields //
	////////////////////
	private Skill skill;
	private CharacterSkill charSkill = null;
	
	/////////////////
	// constructor //
	/////////////////
	public SkillTreeItem(Skill skill) {
		this.skill = skill;
	}

	
	public static LinkedList<SkillTreeItem> buildGroup(String groupName, 
									CharacterSheet sheet) throws JEVEMonException {
		
		LinkedList<SkillTreeItem> group = new LinkedList<SkillTreeItem>();
		for(Skill skill : SkillMap.getInstance().getGroup(groupName)){
			SkillTreeItem s = new SkillTreeItem(skill);
			
			if (sheet != null && sheet.getSkills().containsKey(skill.getTypeID())){
				s.setCharSkill(sheet.getSkill(skill.getTypeID()));
			}
			
			group.add(s);
		}

		return group;
	}

	
	/////////////
	// getters //
	/////////////	
	public Skill getSkill() {
		return skill;
	}

	public CharacterSkill getCharSkill(){
		return charSkill;
	}
	
	public boolean isKnown() {
		return (charSkill != null);
	}
	
	
	/////////////
	// setters //
	/////////////
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public void setCharSkill(CharacterSkill charSkill) {
		this.charSkill = charSkill;
	}
	
}