package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreeSkillGroup extends TreeElement {

	private MarketGroup marketGroup;
	

	public TreeSkillGroup(MarketGroup marketGroup) {
		super();
		this.marketGroup = marketGroup;
	}

	@Override
	public int childCount(){
		return marketGroup.childCount();
	}

	@Override
	public TreeElement childAt(int position) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		TreeElement child = null;
		Integer childId = marketGroup.childAt(position);
		
		if(!marketGroup.isItemContainer()){
			MarketGroup group = MarketGroupDAO.getInstance().findMarketGroupById(childId);
			child = new TreeMarketGroup(group);
		} else {
			Item item = ItemDAO.getInstance().findItemById(childId);
			child = new TreeItem(item);
		}
			
		return child;
	}
	
	
	@Override
	public QIcon getIcon() {
		return null;
	}

	@Override
	public QFont getFont() {
		QFont font = new QFont();
		font.setBold(true);
		return font;
	}

	@Override
	public String getName() {
		return marketGroup.getGroupName();
	}

	@Override
	public String getTooltip() {
		// TODO skill group tooltip 
		return null;
	}

	@Override
	public Item getItem() {
		return null;
	}

	@Override
	public String toString() {
		return marketGroup.getGroupName();
	}

}
