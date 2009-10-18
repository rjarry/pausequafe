package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This class implements a tree leaf. It's used by the TreeModel to access the
 * data from an Item instance
 * 
 * @author Gobi
 */
public class ItemElement implements TreeElement {

	protected Item item;

	public ItemElement(Item item) {
		super();
		this.item = item;
	}

	public QFont getFont() {
		return null;
	}

	public QIcon getIcon() {
		return getIcon(null);
	}

	public QIcon getIcon(CharacterSheet sheet) {
		return new QIcon(Constants.METAGROUP_ICONS_SMALL[item.getMetaGroupID()]);
	}

	public String getName() {
		return item.getTypeName();
	}

	public String getTooltip() {
		return null;
	}

	public Item getItem() {
		return item;
	}

	public TreeElement childAt(int position) throws PQException {
		throw new PQException("Leafs don't have children");
	}

	public int childCount() {
		return 0;
	}

}
