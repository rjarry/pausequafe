package org.jevemon.model.skillintraining;

import java.util.List;
import java.util.Scanner;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterListFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.misc.exceptions.JEVEMonException;

public class TestSkillInTraining {
	public static void main(String[] args){
		
		List<APIData> list = null;
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
		for (APIData data : list) {
			charCount++;
			System.out.println(charCount + " - " + data.getCharacterName());
		}
		Scanner in = new Scanner(System.in);
		System.out.print("Choose a character : ");
		int index = in.nextInt();

		SkillInTraining train = null;
		
		try{
			train = SkillInTrainingFactory.getSkillInTraining(list.get(index-1));
			System.out.println("success");
		} catch (JEVEMonException e) {
			System.out.println("error, could not get character list");
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		System.out.println(train);
		
		
		
		
		
	}
}
