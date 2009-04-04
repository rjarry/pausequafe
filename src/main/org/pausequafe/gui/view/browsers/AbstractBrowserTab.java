package org.pausequafe.gui.view.browsers;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.ItemDetailed;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.gui.model.tree.TreeElement;
import org.pausequafe.gui.model.tree.TreeModel;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QWidget;

public abstract class AbstractBrowserTab extends QWidget {
	
	protected ItemDetailed currentItemSelected;
	protected CharacterSheet sheet;
	
	protected TreeModel prereqModel;
	protected TreeModel browserTreeModel;
	
	public AbstractBrowserTab(QWidget parent){
		super(parent);
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
    
    	TreeElement element = (TreeElement) browserTreeModel.indexToValue(index);
    	
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

	public abstract void changeItemSelected();
    
}
