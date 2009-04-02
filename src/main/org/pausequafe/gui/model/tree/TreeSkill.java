package org.pausequafe.gui.model.tree;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.PreRequisite;
import org.pausequafe.data.business.Skill;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class TreeSkill extends TreeElement {

	private Skill skill;
	private CharacterSheet sheet;
	
	public TreeSkill(Skill skill, CharacterSheet sheet) {
		super();
		this.skill = skill;
		this.sheet = sheet;
	}

	@Override
	public QFont getFont() {
		return null;
	}

	@Override
	public QIcon getIcon() {
		if(sheet.getSkills().containsKey(skill.getTypeID())){
			return new QIcon(Constants.SKILL_LEVEL_ICON[sheet.getSkill(skill.getTypeID()).getLevel()]);
		} else {
			if(skillIsTrainable()){
				return new QIcon(Constants.SKILL_TRAINABLE);
			} else {
				return new QIcon(Constants.SKILL_NOT_TRAINABLE);
			}
		}
	}

	private boolean skillIsTrainable() {
		boolean trainable = true;
		for(PreRequisite req : skill.getPreReqs()){
			trainable = sheet.getSkills().containsKey(req.getTypeID()) &&
						sheet.getSkill(req.getTypeID()).getLevel() >= req.getRequiredLevel();
			if(!trainable) break;
		}
		return trainable;
	}

	@Override
	public String getName() {
		return skill.getTypeName() + " (x" + skill.getRank() + ")";
	}

	@Override
	public String getTooltip() {
		// TODO skill tooltip
		return null;
	}

	@Override
	public Item getItem() {
		return skill;
	}

	@Override
	public String toString() {
		return skill.getTypeName();
	}

	@Override
	public boolean acceptRow() {
		return sheet.getSkills().containsKey(skill.getTypeID());
	}
	
	

}
