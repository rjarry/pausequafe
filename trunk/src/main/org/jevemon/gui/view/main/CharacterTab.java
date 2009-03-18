package org.jevemon.gui.view.main;

import java.io.IOException;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterSheetFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.gui.view.character.CharacterInfo;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonFileNotFoundException;

import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {
	
	CharacterSheet sheet;
	SkillInTraining inTraining;
	

	CharacterInfo infoWidget;

	public CharacterTab(APIData data){
		try {
			sheet = CharacterSheetFactory.getCharacterSheet(data);
			inTraining = SkillInTrainingFactory.getSkillInTraining(data);
		} catch (JEVEMonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoWidget = new CharacterInfo(this);
		infoWidget.loadInfo(sheet);
		infoWidget.loadAttributes(sheet);
		infoWidget.loadSkills(sheet);
		try {
			infoWidget.loadSkillInTraining(sheet, inTraining);
		} catch (JEVEMonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String imageLocation = null;
        try {
        	imageLocation = CharacterSheetFactory.getPortrait(data, false);
		} catch (JEVEMonFileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		infoWidget.loadPortrait(imageLocation);
		infoWidget.show();
	}

	
	
	
	public CharacterSheet getSheet() {
		return sheet;
	}
	
	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	
	public SkillInTraining getInTraining() {
		return inTraining;
	}
	
	public void setInTraining(SkillInTraining inTraining) {
		this.inTraining = inTraining;
	}
}
