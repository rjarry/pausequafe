package org.jevemon.model.items.skilltree;

import java.util.TreeSet;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.model.items.skillmap.Skill;
import org.jevemon.model.items.skillmap.SkillMap;

public class SkillTreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SkillMap map = null;
		
		try {
			map = SkillMap.getInstance();
		} catch (JEVEMonException e) {
			
			System.out.println("naze");
		}
		
		
		TreeSet<Skill> group = null;
		try {
			for(String groupName : Constants.SKILL_GROUP_NAMES){
				group = map.getGroup(groupName);
				System.out.println("#############################\n"
						+groupName+"\n#############################");
				for(Skill skill : group){
					System.out.println(skill.getTypeName());
				}
			}
		} catch (JEVEMonException e) {
			System.out.println(e.getMessage());
		}
		
		

	}

}
