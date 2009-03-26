package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeMarketGroup;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.model.tree.TreePrerequisite;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QStyleFactory;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class Browser extends QWidget {

    Ui_Browser ui = new Ui_Browser();
    
    private QTreeView itemTree;
    private QLabel iconLabel;
    private QLabel metaGroupIcon;
    private QLabel itemNameLabel;
    private QTextBrowser itemDescription;
    private QTreeView prereqTree;
    private TableView attributesTable;
    
//    private QCheckBox tech13CheckBox;
//    private QCheckBox tech2CheckBox;
//    private QCheckBox factionCheckBox;
//    private QCheckBox deadspaceCheckBox;
//    private QCheckBox officerCheckBox;
//    private QCheckBox namedCheckBox;
    
    private Item currentItemSelected;
    
//    TreeSortFilterProxyModel proxyModel = new TreeSortFilterProxyModel();

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
        } catch (PQUserDatabaseFileCorrupted e) {
        	ErrorMessage message = new ErrorMessage(tr(Constants.EVE_DB_CORRUPTED_ERROR));
        	message.exec();
        }
        TreeElement root = new TreeMarketGroup(group);
        TreeModel itemTreeModel = new TreeModel(root);
//        proxyModel.setSourceModel(itemTreeModel);
//        proxyModel.setDynamicSortFilter(true);
        
        itemTree.setModel(itemTreeModel);
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	
    	itemTree = (QTreeView) this.findChild(QTreeView.class, "itemTree");
		itemTree.clicked.connect(this, "currentItemSelected(QModelIndex)");
		itemTree.setSortingEnabled(true);
		itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
		itemTree.setStyle(QStyleFactory.create("WindowsXP"));
		
		prereqTree = (QTreeView) this.findChild(QTreeView.class, "prereqTree");
		prereqTree.setStyle(QStyleFactory.create("WindowsXP"));
		
		itemDescription = (QTextBrowser) this.findChild(QTextBrowser.class, "itemDescription");
		itemDescription.setAcceptRichText(true);
		
		iconLabel = (QLabel) this.findChild(QLabel.class, "iconLabel");
		iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		metaGroupIcon = (QLabel) this.findChild(QLabel.class, "metaGroupIcon");
		itemNameLabel = (QLabel) this.findChild(QLabel.class, "itemNameLabel");
		itemNameLabel.setText("No item selected...");
		
		QFrame attributeTableFrame = (QFrame) this.findChild(QFrame.class, "attributeTableFrame");
		QVBoxLayout tableLayout = new QVBoxLayout();
		tableLayout.setContentsMargins(0, 0, 0, 0);
		attributeTableFrame.setLayout(tableLayout);
		
		attributesTable = new TableView();
		attributesTable.verticalHeader().setVisible(false);
		tableLayout.addWidget(attributesTable);
		
//		tech13CheckBox = (QCheckBox) this.findChild(QCheckBox.class, "tech13CheckBox");
//	    tech2CheckBox = (QCheckBox) this.findChild(QCheckBox.class, "tech2CheckBox");
//	    factionCheckBox = (QCheckBox) this.findChild(QCheckBox.class, "factionCheckBox");
//	    deadspaceCheckBox = (QCheckBox) this.findChild(QCheckBox.class, "deadspaceCheckBox");
//	    officerCheckBox = (QCheckBox) this.findChild(QCheckBox.class, "officerCheckBox");
//	    namedCheckBox = (QCheckBox) this.findChild(QCheckBox.class, "namedCheckBox");
//		
//	    tech13CheckBox.toggled.connect(proxyModel, "setTech1Shown(boolean)");
//	    tech13CheckBox.toggled.connect(proxyModel, "setTech3Shown(boolean)");
//	    namedCheckBox.toggled.connect(proxyModel, "setNamedShown(boolean)");
//	    tech2CheckBox.toggled.connect(proxyModel, "setTech2Shown(boolean)");
//	    factionCheckBox.toggled.connect(proxyModel, "setFactionShown(boolean)");
//	    factionCheckBox.toggled.connect(proxyModel, "setStorylineShown(boolean)");
//	    deadspaceCheckBox.toggled.connect(proxyModel, "setDeadspaceShown(boolean)");
//	    officerCheckBox.toggled.connect(proxyModel, "setOfficerShown(boolean)");
		
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
    		
    		TreeElement root = new TreePrerequisite(currentItemSelected,null);
    		TreeModel prereqModel = new TreeModel(root);
			prereqTree.setModel(prereqModel);
			prereqTree.expandAll();
			
			AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
			attributesTable.setModel(tableModel);
			attributesTable.resizeColumnsToContents();
			attributesTable.resizeRowsToContents();
			
    	}
    }
    
    
}
