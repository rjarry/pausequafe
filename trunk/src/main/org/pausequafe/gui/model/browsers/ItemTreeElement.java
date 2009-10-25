package org.pausequafe.gui.model.browsers;

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
public interface ItemTreeElement {

	public int childCount();

	public ItemTreeElement childAt(int position) throws PQException;

	public String getName();

	public QFont getFont();

	public String getTooltip();

	public QIcon getIcon();

	public QIcon getIcon(CharacterSheet sheet);

	public Item getItem();

}
