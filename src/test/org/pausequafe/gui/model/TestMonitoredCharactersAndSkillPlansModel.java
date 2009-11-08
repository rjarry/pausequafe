package org.pausequafe.gui.model;

import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestMonitoredCharactersAndSkillPlansModel {

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
		
		QTreeView view = new QTreeView();
		view.setModel(characters);
		view.show();
		QApplication.exec();
	}

}
