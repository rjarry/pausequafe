package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.business.Skill;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreeSkillGroup extends TreeElement {

	private MarketGroup marketGroup;
	private CharacterSheet sheet;
	

	public TreeSkillGroup(MarketGroup marketGroup, CharacterSheet sheet) {
		super();
		this.marketGroup = marketGroup;
		this.sheet = sheet;
	}

	@Override
	public int childCount(){
		return marketGroup.childCount();
	}

	@Override
	public TreeElement childAt(int position) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		TreeElement child = null;
		Integer childId = marketGroup.childAt(position);
		
		if(!marketGroup.isItemContainer()){
			MarketGroup group = MarketGroupDAO.getInstance().findMarketGroupById(childId);
			child = new TreeSkillGroup(group,sheet);
		} else {
			Skill skill = ItemDAO.getInstance().findSkillById(childId);
			child = new TreeSkill(skill, this.sheet);
		}
			
		return child;
	}
	
	
	@Override
	public QIcon getIcon() {
		return null;
	}

	@Override
	public QFont getFont() {
		QFont font = new QFont();
		font.setBold(true);
		return font;
	}

	@Override
	public String getName() {
		return marketGroup.getGroupName();
	}

	@Override
	public String getTooltip() {
		return null;
	}

	@Override
	public Item getItem() {
		return null;
	}

	@Override
	public String toString() {
		return marketGroup.getGroupName();
	}

}
