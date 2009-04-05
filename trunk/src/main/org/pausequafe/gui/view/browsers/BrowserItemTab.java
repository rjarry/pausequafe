package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.PrerequisiteElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.model.tree.TreeSortFilterProxyModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class BrowserItemTab extends AbstractBrowserTab {

	////////////////////
	// private fields //
	////////////////////
    private Ui_BrowserItemTab ui;
    

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
    	itemTree.clicked.connect(this, "currentItemSelected(QModelIndex)");
    	itemTree.setSortingEnabled(true);
    	itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
		
		ui.itemDescription.setAcceptRichText(true);
		
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
		
		ui.attributesTable.verticalHeader().setVisible(false);
		
		ui.sortComboBox.currentIndexChanged.connect(this, "setSortMode(int)");
//	    tech13CheckBox.toggled.connect(proxyModel, "setTech1Shown(boolean)");
//	    tech13CheckBox.toggled.connect(proxyModel, "setTech3Shown(boolean)");
//	    namedCheckBox.toggled.connect(proxyModel, "setNamedShown(boolean)");
//	    tech2CheckBox.toggled.connect(proxyModel, "setTech2Shown(boolean)");
//	    factionCheckBox.toggled.connect(proxyModel, "setFactionShown(boolean)");
//	    factionCheckBox.toggled.connect(proxyModel, "setStorylineShown(boolean)");
//	    deadspaceCheckBox.toggled.connect(proxyModel, "setDeadspaceShown(boolean)");
//	    officerCheckBox.toggled.connect(proxyModel, "setOfficerShown(boolean)");
    }
    
	///////////
	// slots //
	///////////
 
    
    @SuppressWarnings("unused")
	private void setSortMode(int sortMode){
    	switch(sortMode){
    	case 1 :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	case 2 :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_META_LEVEL);
    		break;
    	default :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	}
    }
    
	@Override
	public void changeItemSelected() {
		ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");
		
		String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
		File iconFile = new File(icon);
		if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
		ui.iconLabel.setPixmap(new QPixmap(icon));
		
		String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
		ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
		ui.metaGroupIcon.raise();
		
		ui.itemDescription.setText(currentItemSelected.getDescription());
		
		TreeElement root = new PrerequisiteElement(currentItemSelected);
		prereqModel = new TreeModel(root);
		prereqModel.setSheet(sheet);
		ui.prereqTree.setModel(prereqModel);
		
		AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
		ui.attributesTable.setModel(tableModel);
		ui.attributesTable.resizeColumnsToContents();
		ui.attributesTable.resizeRowsToContents();
		
	}

}
