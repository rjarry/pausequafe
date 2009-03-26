package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.Item;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreeSkill extends TreeElement {

	private Item item;
	
	public TreeSkill(Item item) {
		super();
		this.item = item;
	}

	@Override
	public QFont getFont() {
		return null;
	}

	@Override
	public QIcon getIcon() {
		return new QIcon(Constants.METAGROUP_ICONS_SMALL[item.getMetaGroupID()]);
	}

	@Override
	public String getName() {
		return "" + item.getMetaLevel();
	}

	@Override
	public String getTooltip() {
		// TODO skill tooltip
		return null;
	}

	@Override
	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		return item.getTypeName();
	}

}
