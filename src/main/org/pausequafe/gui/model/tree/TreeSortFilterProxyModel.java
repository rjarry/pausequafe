package org.pausequafe.gui.model.tree;


import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QSortFilterProxyModel;
import com.trolltech.qt.gui.QTreeModel;

public class TreeSortFilterProxyModel extends QSortFilterProxyModel {

	///////////////
	// constants //
	///////////////
	public static final int SORT_BY_NAME = 0;
	public static final int SORT_BY_META_LEVEL = 1;

//	private static final int TECH1_ID = 0;
//	private static final int NAMED_ID = 1;
//	private static final int TECH2_ID = 2;
//	private static final int STORYLINE_ID = 3;
//	private static final int FACTION_ID = 4;
//	private static final int OFFICER_ID = 5;
//	private static final int DEADSPACE_ID = 6;
//	private static final int TECH3_ID = 7;

	////////////////////
	// private fields //
	////////////////////
	private QTreeModel sourceModel;
	private int sortMode = 0;

	private boolean tech1Shown = true;
	private boolean namedShown = true;
	private boolean tech2Shown = true;
	private boolean storylineShown = true;
	private boolean factionShown = true;
	private boolean officerShown = true;
	private boolean deadspaceShown = true;
	private boolean tech3Shown = true;


	public TreeSortFilterProxyModel() {
		super();
	}

	public QTreeModel getSourceModel() {
		return sourceModel;
	}

	public void setSourceModel(QTreeModel sourceModel) {
		this.sourceModel=sourceModel;
		super.setSourceModel(sourceModel);
	}

	public TreeElement indexToValue(QModelIndex index) {
		return (TreeElement) sourceModel.indexToValue(mapToSource(index));
	}

	//////////////////////////////////////////////////
	// overridden method from QSortFilterProxyModel //
	//////////////////////////////////////////////////
//	@Override
//	protected boolean lessThan(QModelIndex left, QModelIndex right) {
//		boolean result;
//		TreeElement leftElement = (TreeElement) sourceModel.indexToValue(left);
//		TreeElement rightElement = (TreeElement) sourceModel.indexToValue(right);
//
//		switch(sortMode){
//			case SORT_BY_META_LEVEL :
//				if(leftElement instanceof ItemElement && rightElement instanceof ItemElement){
//					result = leftElement.getItem().getMetaLevel() < rightElement.getItem().getMetaLevel();
//				} else {
//					result = leftElement.getName().compareTo(rightElement.getName()) < 0;
//				}
//				break;
//	
//			default :
//				result = leftElement.getName().compareTo(rightElement.getName()) < 0;
//				break;
//		}
//		return result;
//	}

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
	}


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
