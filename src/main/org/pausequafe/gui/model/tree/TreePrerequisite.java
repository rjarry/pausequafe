package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreePrerequisite extends TreeGroup {

	private Item item;
	private int requiredLevel = -1;
	private CharacterSheet sheet;
	private boolean isRoot = true;
	
	public TreePrerequisite(Item toSkill, CharacterSheet sheet) {
		super();
		this.item = toSkill;
	}
	
	private TreePrerequisite(Item toSkill,int requiredLevel, CharacterSheet sheet, boolean isRoot) {
		super();
		this.item = toSkill;
		this.requiredLevel = requiredLevel;
		this.isRoot = isRoot;
	}

	@Override
	public TreeElement childAt(int position) throws PQException {
		TreeElement child = null;
		Integer childId = item.getPreReqs().get(position).getTypeID();
		int childReqLvl = item.getPreReqs().get(position).getRequiredLevel();
		
		Item childItem = ItemDAO.getInstance().findItemById(childId);
		
		if(requiredLevel == -1){
			child = new TreePrerequisite(childItem,childReqLvl,sheet,true);
		} else {
			child = new TreePrerequisite(childItem,childReqLvl,sheet,false);
		}
		
		return child;
	}

	@Override
	public int childCount() {
		return item.getPreReqs().size();
	}

	@Override
	public QFont getFont() {
		QFont font = new QFont();
		if(isRoot) font.setBold(true); 
		else font.setItalic(true); 
		
		return font;
	}

	@Override
	public QIcon getIcon() {
		if(sheet != null && sheet.getSkills().containsKey(item.getTypeID())){
			if(sheet.getSkill(item.getTypeID()).getLevel() >= requiredLevel){
				return new QIcon(Constants.SKILL_OK);
			} else {
				return new QIcon(Constants.SKILL_KNOWN);
			}
		} else {
			return new QIcon(Constants.SKILL_NOT_KNOWN);
		}
	}

	@Override
	public String getName() {
		String name = item.getTypeName();
		switch(requiredLevel){
			case 1: name += " I"; break;
			case 2: name += " II"; break;
			case 3: name += " III"; break;
			case 4: name += " IV"; break;
			case 5: name += " V"; break;
		}
		return name;
	}

	@Override
	public String getTooltip() {
		return null;
	}

	@Override
	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		String name = item.getTypeName();
		switch(requiredLevel){
			case 1: name += " I"; break;
			case 2: name += " II"; break;
			case 3: name += " III"; break;
			case 4: name += " IV"; break;
			case 5: name += " V"; break;
		}
		return name;
	}

}
