package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class ItemPrerequisiteElement extends GroupElement {

	private Item item;
	private int requiredLevel = -1;
	private boolean isRoot = true;
	
	public ItemPrerequisiteElement(Item toSkill) {
		super();
		this.item = toSkill;
	}
	
	private ItemPrerequisiteElement(Item toSkill,int requiredLevel, boolean isRoot) {
		super();
		this.item = toSkill;
		this.requiredLevel = requiredLevel;
		this.isRoot = isRoot;
	}

	public ItemTreeElement childAt(int position) throws PQException {
		ItemTreeElement child = null;
		int childId = item.getPreReqs().get(position).getTypeID();
		int childReqLvl = item.getPreReqs().get(position).getRequiredLevel();
		
		Item childItem = ItemDAO.getInstance().findItemById(childId);
		
		if(requiredLevel == -1){
			child = new ItemPrerequisiteElement(childItem,childReqLvl,true);
		} else {
			child = new ItemPrerequisiteElement(childItem,childReqLvl,false);
		}
		
		return child;
	}

	public int childCount() {
		return item.getPreReqs().size();
	}

	public QFont getFont() {
		QFont font = new QFont();
		if(isRoot) font.setBold(true); 
		else font.setItalic(true); 
		
		return font;
	}
	
	public QIcon getIcon(CharacterSheet sheet) {
		QIcon result;
		if(sheet != null && sheet.getSkills().containsKey(item.getTypeID())){
			if(sheet.getSkill(item.getTypeID()).getLevel() >= requiredLevel){
				result = new QIcon(Constants.SKILL_OK);
			} else {
				result = new QIcon(Constants.SKILL_KNOWN);
			}
		} else {
			result = new QIcon(Constants.SKILL_NOT_KNOWN);
		}
		return result;
	}

	public String getName() {
		return item.getTypeName() + " " + Formater.printLevel(requiredLevel);
	}

	@Override
	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		return getName();
	}

}
