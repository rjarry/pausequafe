package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.PrerequisiteElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class BrowserShipTab extends AbstractBrowserTab {

    private Ui_BrowserShipTab ui;

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
    	itemTree.clicked.connect(this, "currentItemSelected(QModelIndex)");
    	itemTree.setSortingEnabled(true);
    	itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
		
		ui.itemDescription.setAcceptRichText(true);
		
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<font size=5>No item selected...</font>");
		
		ui.attributesTable.verticalHeader().setVisible(false);
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
