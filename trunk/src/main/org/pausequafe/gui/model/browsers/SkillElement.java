package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.PreRequisite;
import org.pausequafe.data.business.Skill;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;

public class SkillElement extends ItemElement {

	public SkillElement(Skill skill) {
		super(skill);
	}

	public QFont getFont() {
		return null;
	}

	public QIcon getIcon(CharacterSheet sheet) {
		Skill skill = (Skill) item;
		QIcon result;
		if (sheet == null) {
			result = new QIcon(Constants.SKILL_NOT_TRAINABLE);
		} else if (sheet.getSkills().containsKey(skill.getTypeID())) {
			result = new QIcon(Constants.SKILL_LEVEL_ICON[sheet.getSkill(skill.getTypeID())
					.getLevel()]);
		} else {
			if (skillIsTrainable(sheet)) {
				result = new QIcon(Constants.SKILL_TRAINABLE);
			} else {
				result = new QIcon(Constants.SKILL_NOT_TRAINABLE);
			}
		}
		return result;
	}

	private boolean skillIsTrainable(CharacterSheet sheet) {
		Skill skill = (Skill) item;
		boolean trainable = true;
		for (PreRequisite req : skill.getPreReqs()) {
			trainable = sheet.getSkills().containsKey(req.getTypeID())
					&& sheet.getSkill(req.getTypeID()).getLevel() >= req.getRequiredLevel();
			if (!trainable)
				break;
		}
		return trainable;
	}

	public String getName() {
		Skill skill = (Skill) item;
		return skill.getTypeName() + " (x" + skill.getRank() + ")";
	}

}
