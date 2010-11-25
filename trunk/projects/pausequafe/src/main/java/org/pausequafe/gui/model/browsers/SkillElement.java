/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.gui.model.browsers;

import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.item.PreRequisite;
import org.pausequafe.data.item.Skill;
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
