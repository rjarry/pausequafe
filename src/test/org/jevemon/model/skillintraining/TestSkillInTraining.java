package org.jevemon.model.skillintraining;

import java.util.Scanner;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.character.list.CharacterList;
import org.jevemon.model.character.list.CharacterListFactory;
import org.jevemon.model.character.list.CharacterLtd;
import org.jevemon.model.character.skillintraining.SkillInTraining;
import org.jevemon.model.character.skillintraining.SkillInTrainingFactory;

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
