package org.jevemon.model.charactersheet;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Scanner;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.characterlist.CharacterList;
import org.jevemon.model.characterlist.CharacterListFactory;
import org.jevemon.model.characterlist.CharacterLtd;

public class TestCharacterList {
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
		
		try {
			characterID = list.getCharcterAt(index - 1).getCharacterId();
		} catch (JEVEMonException e1) {
			System.out.println("wrong character");
			System.exit(0);
		}
		
		
		CharacterSheet sheet = null;
		
		try {
			System.out.print("fetching character sheet... ");
			sheet = CharacterSheetFactory.getCharacterSheet(3173522, 
					"39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB", 
					characterID);
			System.out.println("success");
		} catch (JEVEMonException e) {
			System.out.println("error, could not get character list");
			System.out.println(e.getMessage());
			System.exit(0);
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
			System.out.print("typeID=" + skill.getTypeID() + " ");
			System.out.print("skillPoints=" + skill.getSkillPoints() + " ");
			System.out.println("level=" + skill.getLevel() + " ");
		}
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
