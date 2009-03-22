package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.Item;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This class implements a tree leaf.
 * It's used by the TreeModel to access the data 
 * from an Item instance
 * 
 * @author Gobi
 */
public class TreeItem extends TreeElement {

	private Item item;
	
	public TreeItem(Item item) {
		super();
		this.item = item;
	}

	@Override
	public QFont getFont() {
		// TODO implements
		return null;
	}

	@Override
	public QIcon getIcon() {
		// TODO implements
		return null;
	}

	@Override
	public String getName() {
		return item.getTypeName();
	}

	@Override
	public String getTooltip() {
		// TODO implements
		return null;
	}


}
