package org.jevemon.gui.model;

import org.jevemon.data.business.MarketGroup;
import org.jevemon.data.dao.MarketGroupDAO;
import org.jevemon.gui.model.tree.TreeElement;
import org.jevemon.gui.model.tree.TreeMarketGroup;
import org.jevemon.gui.model.tree.TreeModel;
import org.jevemon.misc.exceptions.JEVEMonDatabaseFileCorrupted;
import org.jevemon.misc.exceptions.JEVEMonSQLDriverNotFoundException;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestTreeModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	QApplication.initialize(args);
		
		MarketGroup group=null;
		try {
			group = MarketGroupDAO.getInstance().findMarketGroupById(4);
		} catch (JEVEMonSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (JEVEMonDatabaseFileCorrupted e) {
			e.printStackTrace();
		}
		TreeElement root = new TreeMarketGroup(group);
		TreeModel ships = new TreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(ships);
		view.show();
		
		QApplication.exec();
	}

}
