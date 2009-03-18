package org.jevemon.gui.model.tree;

import org.jevemon.data.business.Item;
import org.jevemon.data.business.MarketGroup;
import org.jevemon.data.dao.ItemDAO;
import org.jevemon.data.dao.MarketGroupDAO;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This class implements a tree node.
 * It's used by the TreeModel to access the data 
 * from a MarketGroup instance
 * 
 * @author Gobi
 */
public class TreeMarketGroup extends TreeGroup {

	private MarketGroup marketGroup;
	

	public TreeMarketGroup(MarketGroup marketGroup) {
		super();
		this.marketGroup = marketGroup;
	}

	@Override
	public int childCount(){
		return marketGroup.childCount();
	}

	@Override
	public TreeElement ChildAt(int position) {
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
		// TODO implements
		return null;
	}

	@Override
	public QFont getFont() {
		// TODO implements
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
		// TODO implements
		return null;
	}


}
