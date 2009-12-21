package org.pausequafe.model.charactersheet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.List;
import java.util.Scanner;

import org.pausequafe.core.factory.CharacterListFactory;
import org.pausequafe.core.factory.CharacterSheetFactory;
import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.CharacterSkill;
import org.pausequafe.misc.exceptions.PQException;

public class TestCharacterList {
	private static int userID = 909108;
	private static String apiKey = "AEE374E2E2D04D178A1C18A30CEC54FF538E74254D0649CEBFD2E79DAA215E40";
	
	public static void main(String[] args){
		
		List<APIData> list = null;
		try {
			System.out.print("fetching character list... ");
			list = CharacterListFactory.getCharList(userID, apiKey);
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
		
		APIData data = new APIData(list.get(index - 1).getCharacterID(),
							list.get(index - 1).getCharacterName(),userID,apiKey);
		
		CharacterSheet sheet = null;
		
		try {
			System.out.print("fetching character sheet... ");
			sheet = CharacterSheetFactory.getCharacterSheet(data);
			System.out.println("success");
		} catch (PQException e) {
			System.out.println("error, could not get character list");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		System.out.println();
		System.out.println(sheet.getName());
		System.out.println(sheet.getGender());
		System.out.println(sheet.getRace());
		System.out.println(sheet.getBloodLine());
		System.out.println(sheet.getCorporationName());
		System.out.println("balance = "+printNumber(sheet.getBalance()) + " ISK");
		System.out.println("intelligence = "+printNumber(sheet.getEffectiveAttributeValue("intelligence")));
		System.out.println("memory = "+printNumber(sheet.getEffectiveAttributeValue("memory")));
		System.out.println("charisma = "+printNumber(sheet.getEffectiveAttributeValue("charisma")));
		System.out.println("perception = "+printNumber(sheet.getEffectiveAttributeValue("perception")));
		System.out.println("willpower = "+printNumber(sheet.getEffectiveAttributeValue("willpower")));
		System.out.println();
		System.out.print(sheet.getSkillCount() + " skills known");
		System.out.println("for " + sheet.getSkillPoints() + " skill points");
		System.out.println(sheet.getLevel5Skills() + " skills at level 5");
		System.out.println(sheet.getCloneName() + " : " + sheet.getCloneSkillPoints() + " SP");
		System.out.println();
		System.out.println("SKILL LIST");
		for(CharacterSkill skill : sheet.getSkills().values()){
			System.out.println(skill);
		}
		System.out.println(sheet.getTrainingSpeed("charisma", "memory")*60*60*1000 + " sp/h");
	}
	
	public static String printNumber(double number) {
		StringBuffer buff = new StringBuffer("");
		FieldPosition field = new FieldPosition(1);

		DecimalFormat form = new DecimalFormat();
		form.setGroupingSize(3);
		form.setMaximumFractionDigits(2);
		form.setMinimumFractionDigits(2);

		form.format(number, buff, field);

		return buff.toString();
	}
}
