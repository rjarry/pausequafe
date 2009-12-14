package org.pausequafe.gui.model;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestTreePrerequisite {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	QApplication.initialize(args);
		
		Item item=null;
		try {
			item = ItemDAO.getInstance().findItemById(671);
		} catch (PQSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (PQUserDatabaseFileCorrupted e) {
			e.printStackTrace();
		} catch (PQEveDatabaseCorrupted e) {
			e.printStackTrace();
		}
		ItemTreeElement root = new ItemPrerequisiteElement(item);
		ItemTreeModel preReqTree = new ItemTreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(preReqTree);
		view.show();
		
		QApplication.exec();
	}

}
