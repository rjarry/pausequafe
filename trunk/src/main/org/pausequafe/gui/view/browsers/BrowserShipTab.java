package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.PrerequisiteElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserShipTab extends AbstractBrowserTab {

	////////////////////
	// private fields //
	////////////////////
	private QTreeView prereqTree;
	private Ui_BrowserShipTab ui;

	////////////////////
	// constructors   //
	////////////////////	
    public BrowserShipTab(int marketGroupID) {
    	super(marketGroupID);
    }

    public BrowserShipTab(QWidget parent, int marketGroupID) {
        super(parent,marketGroupID);
    }
    
	//////////////////
	// widget setup //
	//////////////////
    protected void setupUi(){
    	ui = new Ui_BrowserShipTab();
    	ui.setupUi(this);
    	
    	itemTree=ui.itemTree;
    	itemDescription = ui.itemDescription;
    	prereqTree = ui.prereqTree;
    	filterLineEdit = ui.serachLineEdit;
    	
		ui.itemDescription.setAcceptRichText(true);
		
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
		
		ui.attributesTable.verticalHeader().setVisible(false);
    }

    protected void initConnection() {
    	super.initConnection();
    	
    }


	///////////
	// slots //
	///////////
    protected void sort() {
    	// TODO Auto-generated method stub
    	
    }
    
	public void currentItemSelected(QModelIndex index) {
		super.currentItemSelected(index);
		
		if(currentItemSelected != null){
			ui.itemNameLabel.setText("<font size=5>" + currentItemSelected.getTypeName() + "</font>");

			String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
			File iconFile = new File(icon);
			if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
			ui.iconLabel.setPixmap(new QPixmap(icon));

			String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
			ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
			ui.metaGroupIcon.raise();

			itemDescription.setText(currentItemSelected.getDescription());

			TreeElement root = new PrerequisiteElement(currentItemSelected);
			prereqModel = new TreeModel(root);
			prereqModel.setSheet(sheet);
			prereqTree.setModel(prereqModel);

			AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
			ui.attributesTable.setModel(tableModel);
			ui.attributesTable.resizeColumnsToContents();
			ui.attributesTable.resizeRowsToContents();
		}
	}

}
