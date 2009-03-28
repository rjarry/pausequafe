package org.pausequafe.gui.model.tree;

import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QSortFilterProxyModel;
@SuppressWarnings("unused")
public class TreeSortFilterProxyModel extends QSortFilterProxyModel {
	
	///////////////
	// constants //
	///////////////
	public static final int SORT_BY_META_LEVEL = 0;
	public static final int SORT_BY_NAME_NORMAL = 1;
	public static final int SORT_BY_NAME_REVERSE = 2;
	
	private static final int TECH1_ID = 0;
	private static final int NAMED_ID = 1;
	private static final int TECH2_ID = 2;
	private static final int STORYLINE_ID = 3;
	private static final int FACTION_ID = 4;
	private static final int OFFICER_ID = 5;
	private static final int DEADSPACE_ID = 6;
	private static final int TECH3_ID = 7;
	
	////////////////////
	// private fields //
	////////////////////
	private int sortMode = 1;
	private boolean tech1Shown = true;
	private boolean namedShown = true;
	private boolean tech2Shown = true;
	private boolean storylineShown = true;
	private boolean factionShown = true;
	private boolean officerShown = true;
	private boolean deadspaceShown = true;
	private boolean tech3Shown = true;
	
	
	//////////////////////////////////////////////////
	// overridden method from QSortFilterProxyModel //
	//////////////////////////////////////////////////
	@Override
	protected boolean lessThan(QModelIndex left, QModelIndex right) {
		String leftString = ((TreeElement) sourceModel().data(left)).getName();
		String rightString = ((TreeElement) sourceModel().data(right)).getName();
        
		switch(sortMode){
		case SORT_BY_NAME_REVERSE :
	        return leftString.compareTo(rightString) > 0;
		
		case SORT_BY_META_LEVEL :
			Object leftData = (TreeElement) sourceModel().data(left);
			Object rightData = (TreeElement) sourceModel().data(right);
			if(leftData instanceof TreeItem && rightData instanceof TreeItem){
				int rankLeft = ((TreeElement) leftData).getItem().getMetaLevel();
				int rankRight = ((TreeElement) rightData).getItem().getMetaLevel();
				return rankLeft < rankRight;
			} else {
				return leftString.compareTo(rightString) < 0;
			}
		
		default :
			return leftString.compareTo(rightString) < 0;
		}
    }

	//////////////////////////////////////////////////
	// overridden method from QSortFilterProxyModel //
	//////////////////////////////////////////////////
	/*
	@Override
	protected boolean filterAcceptsRow(int source_row, QModelIndex source_parent) {
		TreeElement element = (TreeElement) sourceModel().data(source_parent);
		
		System.out.println(element.getClass());
		
		if(element.childCount() == 0){
			try {
				int metaGroupID;
				metaGroupID = ((TreeElement) element).childAt(source_row).getItem().getMetaGroupID();
				return shown(metaGroupID);
			} catch (PQException e) {
				e.printStackTrace();
				return true;
			}
		} else {
			return true;
		}
	}
	
	@Override
	protected boolean filterAcceptsColumn(int source_column, QModelIndex source_parent) {
		return true;
	}

	// private conveniance method
	private boolean shown(int metaGroupID){
		switch(metaGroupID){
			case TECH1_ID :
				return tech1Shown;
			case NAMED_ID :
				return namedShown;
			case TECH2_ID :
				return tech2Shown;
			case STORYLINE_ID :
				return storylineShown;
			case FACTION_ID :
				return factionShown;
			case OFFICER_ID :
				return officerShown;
			case DEADSPACE_ID :
				return deadspaceShown;
			case TECH3_ID :
				return tech3Shown;
		}
		return true;
	}

	/////////////
	// getters //
	/////////////
	public int getSortMode() {
		return sortMode;
	}
	public boolean isTech1Shown() {
		return tech1Shown;
	}
	public boolean isNamedShown() {
		return namedShown;
	}
	public boolean isTech2Shown() {
		return tech2Shown;
	}
	public boolean isStorylineShown() {
		return storylineShown;
	}
	public boolean isFactionShown() {
		return factionShown;
	}
	public boolean isOfficerShown() {
		return officerShown;
	}
	public boolean isDeadspaceShown() {
		return deadspaceShown;
	}
	public boolean isTech3Shown() {
		return tech3Shown;
	}*/

	
	/////////////
	// setters //
	/////////////
	public void setSortMode(int sortMode) {
		this.sortMode = sortMode;
	}

	public void setTech1Shown(boolean tech1Shown) {
		this.tech1Shown = tech1Shown;
	}

	public void setNamedShown(boolean namedShown) {
		this.namedShown = namedShown;
	}

	public void setTech2Shown(boolean tech2Shown) {
		this.tech2Shown = tech2Shown;
	}

	public void setStorylineShown(boolean storylineShown) {
		this.storylineShown = storylineShown;
	}

	public void setFactionShown(boolean factionShown) {
		this.factionShown = factionShown;
	}

	public void setOfficerShown(boolean officerShown) {
		this.officerShown = officerShown;
	}

	public void setDeadspaceShown(boolean deadspaceShown) {
		this.deadspaceShown = deadspaceShown;
	}

	public void setTech3Shown(boolean tech3Shown) {
		this.tech3Shown = tech3Shown;
	}

}
