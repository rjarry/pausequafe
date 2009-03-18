package org.jevemon.gui.model.tree;

import org.jevemon.misc.exceptions.JEVEMonException;

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
	
	public TreeElement ChildAt(int position) throws JEVEMonException{
		//TODO create new excepton class
		throw new JEVEMonException("Leafs don't have children");
	}
	
	public abstract String getName();
	public abstract QFont getFont();
	public abstract String getTooltip();
	public abstract QIcon getIcon();
	
	public int compareTo(TreeElement o) {
		return this.getName().compareTo(o.getName());
	}
	

}
