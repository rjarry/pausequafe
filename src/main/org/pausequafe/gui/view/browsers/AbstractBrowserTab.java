package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.ItemDetailed;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.model.tree.MarketGroupElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.model.tree.TreeSortFilterProxyModel;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public abstract class AbstractBrowserTab extends QWidget {

	protected ItemDetailed currentItemSelected;
	protected QModelIndex currentIndexSelected;
	protected CharacterSheet sheet;

	protected TreeModel prereqModel;
	protected TreeSortFilterProxyModel proxyModel;

	protected QTreeView itemTree;
	protected QTextBrowser itemDescription;

	protected QLineEdit filterLineEdit;

	private QTimer delayBeforeUpdtingFilter;

	// ////////////////
	// constructors //
	// ////////////////
	public AbstractBrowserTab(int marketGroupID) {
		this(null, marketGroupID);
	}

	public AbstractBrowserTab(QWidget parent, int marketGroupID) {
		super(parent);
		delayBeforeUpdtingFilter = new QTimer();
		delayBeforeUpdtingFilter.setInterval(200);
		delayBeforeUpdtingFilter.setSingleShot(true);

		setupUi();
		initModels(marketGroupID);
		initConnection();
	}

	// /////////////////////////////
	// protected (to be overiden //
	// /////////////////////////////
	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
		proxyModel.setSheet(sheet);
		if (prereqModel != null) {
			prereqModel.setSheet(sheet);
		}
	}

	protected abstract void setupUi();

	protected void initModels(int marketGroupID) {
		MarketGroup group = null;
		try {
			group = MarketGroupDAO.getInstance().findMarketGroupById(marketGroupID);
		} catch (PQSQLDriverNotFoundException e) {
			ErrorMessage message = new ErrorMessage(tr(Constants.DRIVER_NOT_FOUND_ERROR));
			message.exec();
		} catch (PQUserDatabaseFileCorrupted e) {
			ErrorMessage message = new ErrorMessage(tr(Constants.USER_DB_CORRUPTED_ERROR));
			message.exec();
		} catch (PQEveDatabaseNotFound e) {
			ErrorMessage message = new ErrorMessage(tr(Constants.EVE_DB_CORRUPTED_ERROR));
			message.exec();
		}
		proxyModel = new TreeSortFilterProxyModel();
		TreeElement root = new MarketGroupElement(group);
		TreeModel browserTreeModel = new TreeModel(root);
		proxyModel.setSourceModel(browserTreeModel);
		itemTree.setModel(proxyModel);
		proxyModel.setSortMode(TreeSortFilterProxyModel.SORT_BY_META_LEVEL);
		sort();
	}

	protected void initConnection() {
		itemTree.selectionModel().currentChanged.connect(this, "currentItemSelected(QModelIndex)");
		filterLineEdit.textChanged.connect(delayBeforeUpdtingFilter, "start()");
		delayBeforeUpdtingFilter.timeout.connect(this, "changeFilteringText()");
		// filterLineEdit.textChanged.connect(this,"changeFilteringText()");
	}

	// /////////
	// slots //
	// /////////
	protected abstract void sort();

	protected void changeFilteringText() {
		String filteringText = filterLineEdit.text();
		proxyModel.setFilterString(filteringText);
		if (!filteringText.equals("")) {
			itemTree.expandAll();
		} else {
			itemTree.collapseAll();
		}
	}

	protected void currentItemSelected(QModelIndex index) {
		currentIndexSelected = index;
		TreeElement element = proxyModel.indexToValue(index);

		if (element != null && element.getItem() != null) {
			try {
				currentItemSelected = ItemDAO.getInstance().getItemDetails(element.getItem());
			} catch (PQSQLDriverNotFoundException e) {
				ErrorMessage error = new ErrorMessage(this, Constants.DRIVER_NOT_FOUND_ERROR);
				error.exec();
			} catch (PQUserDatabaseFileCorrupted e) {
				ErrorQuestion error = new ErrorQuestion(this, Constants.USER_DB_CORRUPTED_ERROR);
				error.exec();
				if (error.result() == QDialog.DialogCode.Accepted.value()) {
					File userDb = new File(SQLConstants.USER_DATABASE_FILE);
					userDb.delete();
				}
			} catch (PQEveDatabaseNotFound e) {
				ErrorMessage error = new ErrorMessage(this, Constants.EVE_DB_CORRUPTED_ERROR);
				error.exec();
			}
		}
	}
}
