package org.pausequafe.gui.view.browsers;

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
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QTreeView;
import com.trolltech.qt.gui.QWidget;

public abstract class AbstractBrowserTab extends QWidget {
	
	protected ItemDetailed currentItemSelected;
	protected CharacterSheet sheet;
	
	protected TreeModel prereqModel;
	protected TreeSortFilterProxyModel proxyModel = new TreeSortFilterProxyModel();
	protected TreeModel browserTreeModel;
	
	protected QTreeView itemTree;
	
	//////////////////
	// constructors //
	//////////////////

    public AbstractBrowserTab(int marketGroupID) {
        this(null, marketGroupID);
    }

    public AbstractBrowserTab(QWidget parent, int marketGroupID) {
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
        proxyModel.setSourceModel(browserTreeModel);
        proxyModel.setSortMode(TreeSortFilterProxyModel.SORT_BY_META_LEVEL);
        proxyModel.setDynamicSortFilter(true);
        itemTree.setModel(proxyModel);
    }


	/////////////
    // setters //
    /////////////
    public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
		browserTreeModel.setSheet(sheet);
		if(prereqModel != null){
			prereqModel.setSheet(sheet);
		}
	}
    
    @SuppressWarnings("unused")
	private void currentItemSelected(QModelIndex index){
    
    	TreeElement element = proxyModel.indexToValue(index);
    	
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
    		
			changeItemSelected();
    	}
    }
    
	//////////////
    // abstract //
    //////////////
	public abstract void changeItemSelected();
	protected abstract void setupUi();
    
}
