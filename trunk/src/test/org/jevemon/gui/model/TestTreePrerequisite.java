package org.jevemon.gui.model;

import org.jevemon.data.business.Item;
import org.jevemon.data.dao.ItemDAO;
import org.jevemon.gui.model.tree.TreeElement;
import org.jevemon.gui.model.tree.TreeModel;
import org.jevemon.gui.model.tree.TreePrerequisite;
import org.jevemon.misc.exceptions.JEVEMonDatabaseFileCorrupted;
import org.jevemon.misc.exceptions.JEVEMonEveDatabaseNotFound;
import org.jevemon.misc.exceptions.JEVEMonSQLDriverNotFoundException;

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
		} catch (JEVEMonSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (JEVEMonDatabaseFileCorrupted e) {
			e.printStackTrace();
		} catch (JEVEMonEveDatabaseNotFound e) {
			e.printStackTrace();
		}
		TreeElement root = new TreePrerequisite(item,-1);
		TreeModel preReqTree = new TreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(preReqTree);
		view.show();
		
		QApplication.exec();
	}

}
