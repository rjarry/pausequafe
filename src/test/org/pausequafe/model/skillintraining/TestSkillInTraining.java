package org.pausequafe.model.skillintraining;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.pausequafe.core.factory.CharacterListFactory;
import org.pausequafe.core.factory.SkillInTrainingFactory;
import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.misc.exceptions.PQException;

public class TestSkillInTraining {
	public static void main(String[] args){
		
		List<APIData> list = null;
		try {
			System.out.print("fetching character list... ");
			list = CharacterListFactory.getCharList(3173522, 
					"39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB");
			System.out.println("success");
		} catch (PQException e) {
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
		} catch (PQException e) {
			System.out.println("error, could not get character list");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(train);
		
		
		
		
		
	}
}
