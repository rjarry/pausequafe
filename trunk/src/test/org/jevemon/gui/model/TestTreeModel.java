package org.jevemon.gui.model;

import org.jevemon.data.business.MarketGroup;
import org.jevemon.data.dao.MarketGroupDAO;
import org.jevemon.gui.model.tree.*;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestTreeModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	QApplication.initialize(args);
		
		MarketGroup group = MarketGroupDAO.getInstance().findMarketGroupById(2);
		TreeElement root = new TreeMarketGroup(group);
		TreeModel ships = new TreeModel(root);
		
		QTreeView view = new QTreeView();
		view.setModel(ships);
		view.show();
		
		QApplication.exec();
	}

}
