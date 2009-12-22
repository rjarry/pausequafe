package org.pausequafe.gui.model;

import org.pausequafe.data.character.MonitoredCharacter;
import org.pausequafe.gui.model.characters.CharaterSkillPlansProxyModel;
import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestCharactersSkillPlansProxyModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	QApplication.initialize(args);
    	
		MonitoredCharactersAndSkillPlansModel characters=null;
		try {
			characters = MonitoredCharactersAndSkillPlansModel.getInstance();
		} catch (PQSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (PQUserDatabaseFileCorrupted e) {
			e.printStackTrace();
		}
		
		MonitoredCharacter char1 = characters.getCharacterAt(0);
		CharaterSkillPlansProxyModel model = new CharaterSkillPlansProxyModel(char1);
		
		QTreeView view = new QTreeView();
		view.setModel(model);
		view.show();
		QApplication.exec();
	}

}
