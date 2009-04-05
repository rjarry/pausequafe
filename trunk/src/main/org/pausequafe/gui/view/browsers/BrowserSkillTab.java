package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.gui.model.table.AttributesTableModel;
import org.pausequafe.gui.model.tree.MarketGroupElement;
import org.pausequafe.gui.model.tree.PrerequisiteElement;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class BrowserSkillTab extends AbstractBrowserTab {

    Ui_BrowserSkillTab ui = new Ui_BrowserSkillTab();

//    private TreeSortFilterProxyModel proxyModel = new TreeSortFilterProxyModel();

    public BrowserSkillTab(int marketGroupID) {
    	this(null, marketGroupID);
    }

    public BrowserSkillTab(QWidget parent, int marketGroupID) {
        super(parent);
        setupUi();

        MarketGroup group=null;
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
        TreeElement root = new MarketGroupElement(group);
        browserTreeModel = new TreeModel(root);
//        proxyModel.setSourceModel(itemTreeModel);
//        proxyModel.setDynamicSortFilter(true);
//        
//        ui.itemTree.setModel(proxyModel);
        ui.itemTree.setModel(browserTreeModel);
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