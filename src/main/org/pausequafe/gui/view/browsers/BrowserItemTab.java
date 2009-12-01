package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserItemTab extends AbstractBrowserTab {

	////////////////////
	// private fields //
	////////////////////
	private QTreeView prereqTree;
	private Ui_BrowserItemTab ui;

	////////////////////
	// constructors   //
	////////////////////
	public BrowserItemTab(int marketGroupID) {
		super(marketGroupID);
	}

	public BrowserItemTab(QWidget parent, int marketGroupID) {
		super(parent, marketGroupID);
	}

	//////////////////
	// widget setup //
	//////////////////
	
    protected  void setupUi(){
    	ui = new Ui_BrowserItemTab();
    	ui.setupUi(this);
    	
    	itemTree=ui.itemTree;
    	itemDescription = ui.itemDescription;
    	prereqTree = ui.prereqTree;
    	filterLineEdit = ui.lineEdit;

		ui.itemDescription.setAcceptRichText(true);
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
		ui.attributesTable.verticalHeader().setVisible(false);
		
    }
    
	protected void initConnection() {
    	super.initConnection();
    	ui.sortComboBox.currentIndexChanged.connect(this, "sort()");
    	ui.tech13CheckBox.toggled.connect(proxyModel, "setTech1Shown(boolean)");
    	ui.tech13CheckBox.toggled.connect(proxyModel, "setTech3Shown(boolean)");
    	ui.namedCheckBox.toggled.connect(proxyModel, "setNamedShown(boolean)");
    	ui.tech2CheckBox.toggled.connect(proxyModel, "setTech2Shown(boolean)");
    	ui.factionCheckBox.toggled.connect(proxyModel, "setFactionShown(boolean)");
    	ui.factionCheckBox.toggled.connect(proxyModel, "setStorylineShown(boolean)");
    	ui.deadspaceCheckBox.toggled.connect(proxyModel, "setDeadspaceShown(boolean)");
    	ui.officerCheckBox.toggled.connect(proxyModel, "setOfficerShown(boolean)");
    	
    	QAction enterFilterArea = new QAction(this);
		enterFilterArea.triggered.connect(filterLineEdit, "setFocus()");
		enterFilterArea.setShortcut("Ctrl+F");
    }
    
	///////////
	// slots //
	///////////
	protected void sort(){
    	int sortMode = ui.sortComboBox.currentIndex();
    	switch(sortMode){
    	case 0 :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	case 1 :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_META_LEVEL);
    		break;
    	default :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	}
    }
    
	protected void currentItemSelected(QModelIndex index) {
		super.currentItemSelected(index);

		if(currentItemSelected != null){
			ui.prereqFrame.setEnabled(true);
			ui.descriptionFrame.setEnabled(true);
			ui.attributesTable.setEnabled(true);
			ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");
			
			String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
			File iconFile = new File(icon);
			if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
			ui.iconLabel.setPixmap(new QPixmap(icon));
			
			String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
			ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
			ui.metaGroupIcon.raise();
			
			itemDescription.setText(currentItemSelected.getDescription());
			
			ItemTreeElement root = new ItemPrerequisiteElement(currentItemSelected);
			prereqModel = new ItemTreeModel(root);
			prereqModel.setSheet(sheet);
			prereqTree.setModel(prereqModel);
			prereqTree.expandAll();
			
			AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
			ui.attributesTable.setModel(tableModel);
			ui.attributesTable.resizeColumnsToContents();
		}
		ui.attributesTable.resizeRowsToContents();
	}

}
