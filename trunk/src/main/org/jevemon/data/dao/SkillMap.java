package org.jevemon.data.dao;

import java.util.HashMap;
import java.util.TreeSet;

import org.jevemon.data.business.Skill;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
/**
 * This is the skill database generated from an XML file.
 * 
 * It can be accessed via the method <code>getInstance()</code> which returns the only instance of the class (generating it from the XML file if it hasn't already been). Every skill in the database is mapped with it's typeID.
 * 
 * @author diabeteman
 */
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
	/**
	 * Returns the only instance of the class and creates it if it doesn't exists.
	 * 
	 * @return
	 * 		the instance of SkillMap
	 */
	public static SkillMap getInstance() throws JEVEMonException{
		if (instance == null){
			instance = new SkillMap();
			return instance;
		} else {
			return instance;
		}
	}
	
	/**
	 * Outputs a sorted set of skills (sorted by name) belonging to the same skill group.
	 * @param groupName
	 * 				the skill group name
	 * @return
	 * 		a sorted set of skills from the groupName
	 * @throws JEVEMonException
	 */
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
	/**
	 * Outputs a sorted set of skills (sorted by name) belonging to the same skill group.
	 * @param groupID
	 * 				the skill group ID
	 * @return
	 * 		a sorted set of skills from the groupID
	 * @throws JEVEMonException
	 */
	public TreeSet<Skill> getGroup(int groupID) throws JEVEMonException{
		TreeSet<Skill> group = new TreeSet<Skill>();
		if (instance == null){
			instance = new SkillMap();
		}
		for(Skill skill : skills.values()){
			if(skill.getMarketGroupID() == groupID){
				group.add(skill);
			}
		}
		return group;
	}
	
	// just for testing
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
	/**
	 * Used only to build the database from the data file.
	 */
	private void buildMap() throws JEVEMonException {
		skills = SkillMapParser.parse(Constants.SKILL_TREE_FILE);
	}
	

}
