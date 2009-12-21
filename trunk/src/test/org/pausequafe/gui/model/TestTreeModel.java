package org.pausequafe.gui.model;

import org.pausequafe.core.dao.MarketGroupDAO;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.MarketGroupElement;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

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
			group = MarketGroupDAO.getInstance().findMarketGroupById(9);
		} catch (PQSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (PQUserDatabaseFileCorrupted e) {
			e.printStackTrace();
		} catch (PQEveDatabaseCorrupted e) {
			e.printStackTrace();
		}
		ItemTreeElement root = new MarketGroupElement(group);
		ItemTreeModel ships = new ItemTreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(ships);
		view.show();
		QApplication.exec();
	}

}
