package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.Item;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

/**
 * This is the abstract class used as the base class
 * for every object referenced by a TreeModel
 * 
 * @author Gobi
 */
public abstract class TreeElement implements Comparable<TreeElement>{
	
	public int childCount(){
		return 0;
	}
	
	public TreeElement ChildAt(int position) throws PQException{
		//TODO create new excepton class
		throw new PQException("Leafs don't have children");
	}
	
	public abstract String getName();
	public abstract QFont getFont();
	public abstract String getTooltip();
	public abstract QIcon getIcon();
	
	public int compareTo(TreeElement o) {
		return this.getName().compareTo(o.getName());
	}

	public abstract Item getItem();
	
	public abstract String toString();

}
