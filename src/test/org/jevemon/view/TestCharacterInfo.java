package org.jevemon.view;

import java.io.IOException;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterSheetFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.gui.view.character.CharacterInfo;
import org.jevemon.gui.view.skillbrowser.SkillBrowser;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonFileNotFoundException;

import com.trolltech.qt.gui.QApplication;

public class TestCharacterInfo {

	private static int characterID = 614763546;
	private static String name = "Salys Groumf";
	private static int userID = 909108;
	private static String apiKey = "AEE374E2E2D04D178A1C18A30CEC54FF538E74254D0649CEBFD2E79DAA215E40";
	private static APIData data = new APIData(characterID,name,userID,apiKey);
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
        	imageLocation = CharacterSheetFactory.getPortrait(data, false);
		} catch (JEVEMonFileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.loadPortrait(imageLocation);

		CharacterSheet sheet = null;
		try {
			sheet = CharacterSheetFactory.getCharacterSheet(data);
		} catch (JEVEMonException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SkillInTraining training = null;
		try{
			training = SkillInTrainingFactory.getSkillInTraining(data);
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
