package org.pausequafe.data.dao;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.MarketGroupElement;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QTreeView;

public class TestBlueprintDAO {

	private QTreeView view;
	private ItemTreeModel model;

	public static void main(String[] args){
		QApplication.initialize(args);
		new TestBlueprintDAO();
		QApplication.exec();
		
	}

	public TestBlueprintDAO() {

		try {
			MarketGroup m = MarketGroupDAO.getInstance().findMarketGroupById(SQLConstants.BLUEPRINTS_MKTGRPID);
			ItemTreeElement root = new MarketGroupElement(m);
			model = new ItemTreeModel(root);
			view = new QTreeView(null);
			view.setModel(model);

			view.clicked.connect(this, "println(QModelIndex)");
			view.show();
			view.expandAll();
		} catch (PQSQLDriverNotFoundException e) {
			e.printStackTrace();
		} catch (PQUserDatabaseFileCorrupted e) {
			e.printStackTrace();
		} catch (PQEveDatabaseNotFound e) {
			e.printStackTrace();
		}

	}
	
	void println(QModelIndex index){
		ItemTreeElement element = (ItemTreeElement) model.indexToValue(index);
		if (element instanceof MarketGroupElement){
			System.out.println(element.getClass());
		} else {
			System.out.println(element.getItem());
		}
			
		
	}
	
	
}
