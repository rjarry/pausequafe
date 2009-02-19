package org.jevemon.model.items.skilltree;

import org.jevemon.misc.exceptions.JEVEMonException;

public class SkillTreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SkillTree tree = null;
		
		try {
			tree = SkillTree.getInstance();
		} catch (JEVEMonException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(tree);

	}

}
