package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreePrerequisite extends TreeGroup {

	Item item;
	int requiredLevel;
	
	public TreePrerequisite(Item toSkill,int requiredLevel) {
		super();
		this.item = toSkill;
		this.requiredLevel = requiredLevel;
	}

	@Override
	public TreeElement ChildAt(int position) throws PQException {
		TreeElement child = null;
		Integer childId = item.getPreReqs().get(position).getTypeID();
		int childReqLvl = item.getPreReqs().get(position).getRequiredLevel();
		
		Item childItem = ItemDAO.getInstance().findItemById(childId);
		child = new TreePrerequisite(childItem,childReqLvl);
		
		return child;
	}

	@Override
	public int childCount() {
		return item.getPreReqs().size();
	}

	@Override
	public QFont getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		String name = item.getTypeName();
		switch(requiredLevel){
			case 1: name += " I"; break;
			case 2: name += " II"; break;
			case 3: name += " III"; break;
			case 4: name += " IV"; break;
			case 5: name += " V"; break;
		}
		return name;
	}

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}

}
