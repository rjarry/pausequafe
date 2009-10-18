package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This is the abstract class used as the base class for every object referenced
 * by a TreeModel
 * 
 * @author Gobi
 */
public interface TreeElement {

	public int childCount();

	public TreeElement childAt(int position) throws PQException;

	public String getName();

	public QFont getFont();

	public String getTooltip();

	public QIcon getIcon();

	public QIcon getIcon(CharacterSheet sheet);

	public Item getItem();

}
