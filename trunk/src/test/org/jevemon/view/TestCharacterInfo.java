package org.jevemon.view;

import java.io.IOException;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonFileNotFoundException;
import org.jevemon.model.charactersheet.CharacterSheet;
import org.jevemon.model.charactersheet.CharacterSheetFactory;
import org.jevemon.model.skillintraining.SkillInTraining;
import org.jevemon.model.skillintraining.SkillInTrainingFactory;
import org.jevemon.view.character.CharacterInfo;
import org.jevemon.view.skillbrowser.SkillBrowser;

import com.trolltech.qt.gui.QApplication;

public class TestCharacterInfo {

	private static int characterID = 614763546;
	private static String name = "Salys Groumf";
	private static int userID = 909108;
	private static String apiKey = "AEE374E2E2D04D178A1C18A30CEC54FF538E74254D0649CEBFD2E79DAA215E40";

	/**
	 * @param args
	 */
    public static void main(String[] args) {
    	QApplication.initialize(args);
    	
    	CharacterInfo view = new CharacterInfo();
    	SkillBrowser browser = new SkillBrowser();;
    	
    	view.setWindowTitle("Character Info");
    	browser.setWindowTitle("Skills");
    	
    	
    	String imageLocation = null;
        try {
        	imageLocation = CharacterSheetFactory.getPortrait(characterID , name , false);
		} catch (JEVEMonFileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.loadPortrait(imageLocation);

		CharacterSheet sheet = null;
		try {
			sheet = CharacterSheetFactory.getCharacterSheet(userID, apiKey, characterID, name);
		} catch (JEVEMonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SkillInTraining training = null;
		try{
			training = SkillInTrainingFactory.getSkillInTraining(userID, 
					apiKey, characterID, name);
		} catch (JEVEMonException e) {
			e.printStackTrace();
		}
		
		view.loadInfo(sheet);
		view.loadAttributes(sheet);
		view.loadSkills(sheet);
		try {
			view.loadSkillInTraining(sheet, training);
		} catch (JEVEMonException e) {
			e.printStackTrace();
		}
		
		try {
			browser.loadTree(sheet);
		} catch (JEVEMonException e) {
			e.printStackTrace();
		}
		
		
		browser.show();
		view.show();
		QApplication.exec();
    }

}
