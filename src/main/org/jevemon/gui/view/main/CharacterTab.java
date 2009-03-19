package org.jevemon.gui.view.main;

import java.io.IOException;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterSheetFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.gui.view.character.CharacterInfo;
import org.jevemon.misc.exceptions.JEVEMonException;

import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {
	
	CharacterSheet sheet;
	SkillInTraining inTraining;
	

	CharacterInfo infoWidget;

	public CharacterTab(){
		infoWidget = new CharacterInfo(this);
		infoWidget.show();
	}
	
	
	public void updateCharacterInfo(APIData data) throws IOException, JEVEMonException{
		sheet = CharacterSheetFactory.getCharacterSheet(data);
		inTraining = SkillInTrainingFactory.getSkillInTraining(data);
		infoWidget.loadInfo(sheet);
		infoWidget.loadAttributes(sheet);
		infoWidget.loadSkills(sheet);
		infoWidget.loadSkillInTraining(sheet, inTraining);
		String imageLocation = null;
        imageLocation = CharacterSheetFactory.getPortrait(data, false);
		infoWidget.loadPortrait(imageLocation);
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
