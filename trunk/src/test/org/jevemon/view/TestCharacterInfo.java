package org.jevemon.view;

import java.io.IOException;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonFileNotFoundException;
import org.jevemon.model.charactersheet.CharacterSheet;
import org.jevemon.model.charactersheet.CharacterSheetFactory;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class TestCharacterInfo {

	private static int characterID = 1589516577;
	private static String name = "diabetelord";
	private static int userID = 3173522;
	private static String apiKey = "39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB";

	/**
	 * @param args
	 */
    public static void main(String[] args) {
    	QApplication.initialize(args);
    	
    	CharacterInfo view = new CharacterInfo();
    	
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
		view.loadInfo(sheet);
		
        view.show();
        
        QApplication.exec();
    }

}
