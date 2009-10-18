package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QTreeModel;

/**
 * This class implements a tree model that can be used to show items sorted by
 * groups.
 * 
 * @author Gobi
 */
public class TreeModel extends QTreeModel {

	private TreeElement root = null;
	private CharacterSheet sheet = null;

	public TreeModel() {
		super();
	}

	public TreeModel(TreeElement root) {
		super();
		this.root = root;
	}

	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
		this.dataChanged.emit(null, null);
	}

	public CharacterSheet getSheet() {
		return sheet;
	}

	@Override
	public TreeElement child(Object parent, int index) {
		TreeElement treeElement;
		if (parent == null) {
			treeElement = root;
		} else {
			treeElement = (TreeElement) parent;
		}

		TreeElement child = null;
		try {
			child = treeElement.childAt(index);
		} catch (PQException e) {
			e.printStackTrace();
		}
		return child;

	}

	@Override
	public int childCount(Object parent) {
		TreeElement treeElement;
		if (parent == null) {
			treeElement = root;
		} else {
			treeElement = (TreeElement) parent;
		}

		return treeElement.childCount();
	}

	@Override
	public String text(Object value) {
		String s = ((TreeElement) value).getName();
		return s;
	}

	@Override
	public Object data(Object value, int role) {
		TreeElement elt = (TreeElement) value;

		Object result = null;
		switch (role) {
		// text to display
		case ItemDataRole.DisplayRole:
			result = elt.getName();
			break;
		// display font
		case ItemDataRole.FontRole:
			result = elt.getFont();
			break;
		// the icon before the skills
		case ItemDataRole.DecorationRole:
			result = elt.getIcon(sheet);
			break;
		// tool tips on the skills
		case ItemDataRole.ToolTipRole:
			result = elt.getTooltip();
			break;

		default:
			result = null;
		}

		return result;
	}

}
