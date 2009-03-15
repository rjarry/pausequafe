package org.jevemon.model.skillintraining;

import java.util.Scanner;

import org.jevemon.data.business.CharacterList;
import org.jevemon.data.business.CharacterLtd;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterListFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.misc.exceptions.JEVEMonException;

public class TestSkillInTraining {
	public static void main(String[] args){
		
		CharacterList list = null;
		try {
			System.out.print("fetching character list... ");
			list = CharacterListFactory.getCharList(3173522, 
					"39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB");
			System.out.println("success");
		} catch (JEVEMonException e) {
			System.out.println("error, could not get character list");
			System.exit(0);
		}
		
		int charCount = 0;
		for (CharacterLtd characterLtd : list.getList()) {
			charCount++;
			System.out.println(charCount + " - " + characterLtd.getName());
		}
		Scanner in = new Scanner(System.in);
		System.out.print("Choose a character : ");
		int index = in.nextInt();
		
		int characterID = 0;
		String characterName = null;
		try {
			characterID = list.getCharcterAt(index - 1).getCharacterId();
			characterName = list.getCharcterAt(index - 1).getName();
		} catch (JEVEMonException e1) {
			System.out.println("wrong character");
			System.exit(0);
		}
		
		
		
		
		
		
		SkillInTraining train = null;
		
		try{
			train = SkillInTrainingFactory.getSkillInTraining(3173522, 
			"39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB", 
			characterID,characterName);
			System.out.println("success");
		} catch (JEVEMonException e) {
			System.out.println("error, could not get character list");
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		System.out.println(train);
		
		
		
		
		
	}
}
