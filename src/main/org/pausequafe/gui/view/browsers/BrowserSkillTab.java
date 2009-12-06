package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.gui.model.browsers.AttributesTableModel;
import org.pausequafe.gui.model.browsers.ItemPrerequisiteElement;
import org.pausequafe.gui.model.browsers.ItemTreeElement;
import org.pausequafe.gui.model.browsers.ItemTreeModel;
import org.pausequafe.gui.model.browsers.ItemTreeSortFilterProxyModel;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public class BrowserSkillTab extends AbstractBrowserTab {

	////////////////////
	// private fields //
	////////////////////
	private QTreeView prereqTree;
	private Ui_BrowserSkillTab ui;

	////////////////////
	// constructors   //
	////////////////////
    public BrowserSkillTab(int marketGroupID) {
    	super(marketGroupID);
    }

    public BrowserSkillTab(QWidget parent, int marketGroupID) {
        super(parent,marketGroupID);
    }
    
	//////////////////
	// widget setup //
	//////////////////
    protected void setupUi(){
    	ui = new Ui_BrowserSkillTab();
    	ui.setupUi(this);

    	// we have to do designer work here
    	// because this bastard can't create
    	// a custom ItemTreeView
    	ui.itemTree.dispose();
    	ui.itemTree = new ItemTreeView(ui.rightFrame);
    	itemTree = ui.itemTree;
        itemTree.setObjectName("itemTree");
        QSizePolicy sizePolicy6 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy6.setHorizontalStretch((byte)0);
        sizePolicy6.setVerticalStretch((byte)0);
        sizePolicy6.setHeightForWidth(itemTree.sizePolicy().hasHeightForWidth());
        itemTree.setSizePolicy(sizePolicy6);
        itemTree.setMinimumSize(new QSize(200, 200));
        itemTree.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);
        itemTree.setHorizontalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        itemTree.setProperty("showDropIndicator", false);
        itemTree.setDragEnabled(true);
        itemTree.setDragDropMode(com.trolltech.qt.gui.QAbstractItemView.DragDropMode.DragOnly);
        itemTree.setIndentation(20);
        itemTree.setRootIsDecorated(true);
        itemTree.setUniformRowHeights(true);
        itemTree.setSortingEnabled(true);
        itemTree.setHeaderHidden(true);
    	ui.verticalLayout.addWidget(ui.itemTree);
    	// end designer work
    	
    	itemTree.setIconSize(new QSize(21,14));
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
    	
    	QAction enterFilterArea = new QAction(this);
		enterFilterArea.triggered.connect(filterLineEdit, "setFocus()");
		enterFilterArea.setShortcut("Ctrl+F");
    }
    
	///////////
	// slots //
	///////////
    protected void sort() {
    	int sortMode = ui.sortComboBox.currentIndex();
    	switch(sortMode){
    	case 0 :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	case 1 :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_REMAINING_TRAINING_TIME);
    		break;
    	default :
    		proxyModel.setSortMode(ItemTreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	}    	
    }
    
	public void currentItemSelected(QModelIndex index) {
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
			ui.attributesTable.resizeRowsToContents();
		}
	}
    
}
