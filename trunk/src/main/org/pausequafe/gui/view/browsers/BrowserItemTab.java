package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.ItemDetailed;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeMarketGroup;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.model.tree.TreePrerequisite;
import org.pausequafe.gui.model.tree.TreeSortFilterProxyModel;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class BrowserItemTab extends QWidget {

	////////////////////
	// private fields //
	////////////////////
	
    Ui_BrowserItemTab ui = new Ui_BrowserItemTab();
    
    private ItemDetailed currentItemSelected;
    private TreeSortFilterProxyModel proxyModel = new TreeSortFilterProxyModel();
	private CharacterSheet sheet = null;


	//////////////////
	// constructors //
	//////////////////
    public BrowserItemTab(int marketGroupID) {
        this(null, marketGroupID);
    }

    public BrowserItemTab(QWidget parent, int marketGroupID) {
        super(parent);
        setupUi();

        MarketGroup group=null;
        try {
        	group = MarketGroupDAO.getInstance().findMarketGroupById(marketGroupID);
        } catch (PQSQLDriverNotFoundException e) {
        	ErrorMessage message = new ErrorMessage(tr(Constants.DRIVER_NOT_FOUND_ERROR));
        	message.exec();
        } catch (PQUserDatabaseFileCorrupted e) {
        	ErrorMessage message = new ErrorMessage(tr(Constants.EVE_DB_CORRUPTED_ERROR));
        	message.exec();
        }
        TreeElement root = new TreeMarketGroup(group);
        TreeModel itemTreeModel = new TreeModel(root);
        proxyModel.setSourceModel(itemTreeModel);
        proxyModel.setDynamicSortFilter(true);
        
        ui.itemTree.setModel(proxyModel);
    }
	//////////////////
	// widget setup //
	//////////////////
    private void setupUi(){
    	ui.setupUi(this);
    	
    	ui.itemTree.clicked.connect(this, "currentItemSelected(QModelIndex)");
    	ui.itemTree.setSortingEnabled(true);
    	ui.itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
		
		ui.itemDescription.setAcceptRichText(true);
		
		ui.iconLabel.setPixmap(new QPixmap(Constants.NO_ITEM_SELECTED_ICON));
		ui.itemNameLabel.setText("<b><font size=5>No item selected...</font></b>");
		
		ui.attributesTable.verticalHeader().setVisible(false);
		
		ui.sortComboBox.activatedIndex.connect(this, "setSortMode(Integer)");
		
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
	private void currentItemSelected(QModelIndex index){
    
    	TreeElement element = (TreeElement) ui.itemTree.model().data(index, TreeModel.ValueRole);
    	
    	if (element.getItem() != null){
    		try {
				currentItemSelected = ItemDAO.getInstance().getItemDetails(element.getItem());
			} catch (PQUserDatabaseFileCorrupted e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PQEveDatabaseNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PQSQLDriverNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			ui.itemNameLabel.setText("<b><font size=5>" + currentItemSelected.getTypeName() + "</font></b>");
    		
    		String icon = Constants.EVE_ICONS_PATH + currentItemSelected.getIcon() + ".png";
    		File iconFile = new File(icon);
    		if(!iconFile.exists()) icon = Constants.NO_ITEM_SELECTED_ICON;
    		ui.iconLabel.setPixmap(new QPixmap(icon));
    		
    		String tagIconFile = Constants.METAGROUP_ICONS_TAG[currentItemSelected.getMetaGroupID()];
    		ui.metaGroupIcon.setPixmap(new QPixmap(tagIconFile));
    		ui.metaGroupIcon.raise();
    		
    		ui.itemDescription.setText(currentItemSelected.getDescription());
    		
    		TreeElement root = new TreePrerequisite(currentItemSelected,sheet);
    		TreeModel prereqModel = new TreeModel(root);
    		ui.prereqTree.setModel(prereqModel);
			
			AttributesTableModel tableModel = new AttributesTableModel(currentItemSelected);
			ui.attributesTable.setModel(tableModel);
			ui.attributesTable.resizeColumnsToContents();
			ui.attributesTable.resizeRowsToContents();
			
    	}
    }
    
    @SuppressWarnings("unused")
	private void setSortMode(Integer sortMode){
    	switch(sortMode){
    	case 0 :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_NAME);
    		break;
    	case 1 :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_META_LEVEL);
    		break;
    	default :
    		proxyModel.setSortRole(TreeSortFilterProxyModel.SORT_BY_NAME);
    	}
    	
        ui.itemTree.setModel(proxyModel);
    	ui.itemTree.sortByColumn(0, Qt.SortOrder.AscendingOrder);
    	ui.itemTree.update();
    }

    
    /////////////
    // setters //
    /////////////	
    public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
		if(currentItemSelected != null){
			TreeElement root = new TreePrerequisite(currentItemSelected,sheet);
			TreeModel prereqModel = new TreeModel(root);
			ui.prereqTree.setModel(prereqModel);
		}
	}
    
    
}
