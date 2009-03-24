package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.model.table.TableModel;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeMarketGroup;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.model.tree.TreePrerequisite;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.misc.exceptions.PQDatabaseFileCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class Browser extends QWidget {

    Ui_Browser ui = new Ui_Browser();
    
    private QTreeView itemTree;
    private QLabel iconLabel;
    private QLabel metaGroupIcon;
    private QLabel itemNameLabel;
    private QTextBrowser itemDescription;
    private QTreeView prereqTree;
    private QTableView attributesTable;
    
    private Item currentItemSelected;

    public static void main(String[] args) {
        QApplication.initialize(args);

        Browser testBrowser = new Browser();
        testBrowser.show();

        QApplication.exec();
    }

    public Browser() {
        this(null);
    }

    public Browser(QWidget parent) {
        super(parent);
        setupUi();

        MarketGroup group=null;
        try {
        	group = MarketGroupDAO.getInstance().findMarketGroupById(9);
        } catch (PQSQLDriverNotFoundException e) {
        	ErrorMessage message = new ErrorMessage(tr(Constants.DRIVER_NOT_FOUND_ERROR));
        	message.exec();
        } catch (PQDatabaseFileCorrupted e) {
        	ErrorMessage message = new ErrorMessage(tr(Constants.EVE_DB_CORRUPTED_ERROR));
        	message.exec();
        }
        TreeElement root = new TreeMarketGroup(group);
        TreeModel itemTreeModel = new TreeModel(root);
        itemTree.setModel(itemTreeModel);
        itemTree.collapsed.connect(itemTreeModel, "releaseChildren(QModelIndex)");
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	
    	itemTree = (QTreeView) this.findChild(QTreeView.class, "itemTree");
		itemTree.clicked.connect(this, "currentItemSelected(QModelIndex)");
		itemTree.setSortingEnabled(true);
		itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
		
		prereqTree = (QTreeView) this.findChild(QTreeView.class, "prereqTree");
		
		itemDescription = (QTextBrowser) this.findChild(QTextBrowser.class, "itemDescription");
		itemDescription.setAcceptRichText(true);
		
		iconLabel = (QLabel) this.findChild(QLabel.class, "iconLabel");
		iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		metaGroupIcon = (QLabel) this.findChild(QLabel.class, "metaGroupIcon");
		itemNameLabel = (QLabel) this.findChild(QLabel.class, "itemNameLabel");
		itemNameLabel.setText("No item selected...");
		
		attributesTable = (QTableView) this.findChild(QTableView.class, "attributesTable");
		attributesTable.setVerticalHeader(null);
		
		
		this.resize(1100, 700);
    }
    
    @SuppressWarnings("unused")
	private void currentItemSelected(QModelIndex index){
    	TreeElement element = (TreeElement) itemTree.model().data(index);
    	
    	if (element.getItem() != null){
    		currentItemSelected = element.getItem();
    		
    		itemNameLabel.setText(currentItemSelected.getTypeName());
    		
    		String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon();
    		File iconFile = new File(icon);
    		if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
    		iconLabel.setPixmap(new QPixmap(icon));
    		metaGroupIcon.setPixmap(new QPixmap(
    				Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()]));
    		
    		itemDescription.setText(currentItemSelected.getDescription());
    		
    		TreeElement root = new TreePrerequisite(currentItemSelected,-1);
    		TreeModel prereqModel = new TreeModel(root);
			prereqTree.setModel(prereqModel);
			prereqTree.expandAll();
			
			TableModel tableModel = new TableModel(currentItemSelected);
			attributesTable.setModel(tableModel);
			attributesTable.reset();
    	}
    	
    }
    
}
