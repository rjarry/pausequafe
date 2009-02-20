package org.jevemon.model.items.skillmap;

import java.util.HashMap;
import java.util.TreeSet;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;

public class SkillMap {
	
	////////////////////
	// private fields //
	////////////////////
	private static SkillMap instance = null;
	private HashMap<Integer, Skill> skills = null;
	
	/////////////////
	// constructor //
	/////////////////
	private SkillMap() throws JEVEMonException{
		buildMap();
	}

	////////////////////
	// public methods //
	////////////////////

	public static SkillMap getInstance() throws JEVEMonException{
		if (instance == null){
			instance = new SkillMap();
			return instance;
		} else {
			return instance;
		}
	}
	
	public TreeSet<Skill> getGroup(String groupName) throws JEVEMonException{
		TreeSet<Skill> group = new TreeSet<Skill>();
		if (instance == null){
			instance = new SkillMap();
		}
		for(Skill skill : skills.values()){
			if(skill.getGroupName().equals(groupName)){
				group.add(skill);
			}
		}
		return group;
	}
	
	public TreeSet<Skill> getGroup(int groupID) throws JEVEMonException{
		TreeSet<Skill> group = new TreeSet<Skill>();
		if (instance == null){
			instance = new SkillMap();
		}
		for(Skill skill : skills.values()){
			if(skill.getGroupID() == groupID){
				group.add(skill);
			}
		}
		return group;
	}
	
	
	public String toString(){
		String print = "----------SKILL TREE----------\n\n";
		
		for(Skill skill : this.skills.values()){
			print += skill.toString() + "\n\n";
		}

		return print;
	}
	
	/////////////
	// getters //
	/////////////
	public Skill getSkill(int typeID){
		return skills.get(Integer.valueOf(typeID));
	}

	public HashMap<Integer, Skill> getAllSkills(){
		return skills;
	}
		
	/////////////////////
	// private methods //
	/////////////////////
	private void buildMap() throws JEVEMonException {
		skills = SkillMapParser.parse(Constants.SKILL_TREE_FILE);
	}
	

}
