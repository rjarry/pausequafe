package org.pausequafe.gui.model;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.gui.model.tree.PrerequisiteElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
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
		} catch (PQEveDatabaseNotFound e) {
			e.printStackTrace();
		}
		TreeElement root = new PrerequisiteElement(item);
		TreeModel preReqTree = new TreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(preReqTree);
		view.show();
		
		QApplication.exec();
	}

}
