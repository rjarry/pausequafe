package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;

import com.trolltech.qt.gui.QIcon;


/**
 * This is the abstract class used as the base class
 * for every class that implements a tree node
 * 
 * @author Gobi
 */
public abstract class GroupElement implements TreeElement {
	
	@Override
	public String getTooltip() {
		return null;
	}
	
	@Override
	public Item getItem() {
		return null;
	}
	
	@Override
	public QIcon getIcon() {
		return getIcon(null);
		
	}

	@Override
	public QIcon getIcon(CharacterSheet sheet) {
		return null;
	}

}
