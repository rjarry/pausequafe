/*****************************************************************************
 * This file is part of Pause Quafe.                                         *
 *                                                                           *
 * Pause Quafe - An Eve-Online(TM) character assistance application          *
 * Copyright (c) 2009  diabeteman & Kios Askoner                             *
 *                                                                           *
 * Pause Quafe is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafe is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#include "data/character/CharacterSheet.h"
#include "misc/util/Constants.h"

//////////////////
// constructors //
//////////////////
CharacterSheet::CharacterSheet() :
    corporationTitles(QStringList()),
    attributeEnhancers(QMap<Attribute, AttributeEnhancer*> ()),
    skills(QMap<unsigned, CharacterSkill*> ())
{
}

CharacterSheet::~CharacterSheet() {
    foreach(CharacterSkill* skill, skills.values()) {
        delete skill;
    }
    foreach(AttributeEnhancer* attr, attributeEnhancers.values()) {
        delete attr;
    }
    skills.clear();
    attributeEnhancers.clear();
}

////////////////////
// PUBLIC METHODS //
////////////////////
void CharacterSheet::addAttributeEnhancer(AttributeEnhancer *e) {
    attributeEnhancers[e->getAttribute()] = e;
}

void CharacterSheet::addSkill(CharacterSkill *skill) {
    skills[skill->getTypeID()] = skill;
}

void CharacterSheet::addTitle(QString & title) {
    corporationTitles.append(title);
}

double CharacterSheet::getEffectiveAttributeValue(Attribute attribute) {
    uint baseValue = 0;
    switch (attribute) {
    case intelligence:
        baseValue = _intelligence;
    case memory:
        baseValue = _memory;
    case willpower:
        baseValue = _willpower;
    case charisma:
        baseValue = _charisma;
    case perception:
        baseValue = _perception;
    }

    uint skillsBonus = getSkillsBonus(attribute);

    uint implantBonus;
    if (attributeEnhancers.contains(attribute)) {
        implantBonus = attributeEnhancers[attribute]->getAugmentatorValue();
    } else {
        implantBonus = 0;
    }

    double learningBonus = getLearningBonus();

    return (baseValue + skillsBonus + implantBonus) * learningBonus;
}

uint CharacterSheet::getSkillsBonus(Attribute attribute) {
    int skillsBonus = 0;

    switch (attribute) {
    case intelligence:
        if (skills.contains(ANALYTICAL_MIND)) {
            skillsBonus += skills.value(ANALYTICAL_MIND)->getLevel();
        }
        if (skills.contains(LOGIC)) {
            skillsBonus += skills.value(LOGIC)->getLevel();
        }
        break;
    case memory:
        if (skills.contains(INSTANT_RECALL)) {
            skillsBonus += skills.value(INSTANT_RECALL)->getLevel();
        }
        if (skills.contains(EDEITIC_MEMORY)) {
            skillsBonus += skills.value(EDEITIC_MEMORY)->getLevel();
        }
        break;
    case willpower:
        if (skills.contains(IRON_WILL)) {
            skillsBonus += skills.value(IRON_WILL)->getLevel();
        }
        if (skills.contains(FOCUS)) {
            skillsBonus += skills.value(FOCUS)->getLevel();
        }
        break;
    case charisma:
        if (skills.contains(EMPATHY)) {
            skillsBonus += skills.value(EMPATHY)->getLevel();
        }
        if (skills.contains(PRESENCE)) {
            skillsBonus += skills.value(PRESENCE)->getLevel();
        }
        break;
    case perception:
        if (skills.contains(SPATIAL_AWARENESS)) {
            skillsBonus += skills.value(SPATIAL_AWARENESS)->getLevel();
        }
        if (skills.contains(CLARITY)) {
            skillsBonus += skills.value(CLARITY)->getLevel();
        }
        break;
    }
    return skillsBonus;
}

double CharacterSheet::getLearningBonus() {
    if (skills.contains(LEARNING)) {
        return 1.0 + skills.value(LEARNING)->getLevel() * 0.02;
    } else {
        return 1.0;
    }
}

double CharacterSheet::getTrainingSpeed(Attribute primAttr, Attribute secAttr) {
    double att1 = getEffectiveAttributeValue(primAttr);
    double att2 = getEffectiveAttributeValue(secAttr);

    return (att1 + (att2 / 2.0)) / (60.0 * 1000.0);
}

double CharacterSheet::getTrainingSpeed(Skill & skill) {
    return getTrainingSpeed(skill.getPrimaryAttribute(), skill.getSecondaryAttribute());
}

long CharacterSheet::getSkillPoints() {
    long skillPoints = 0;
    foreach (CharacterSkill *skill, skills.values()) {
        skillPoints += skill->getSkillPoints();
    }
    return skillPoints;
}

uint CharacterSheet::getSkillCount() {
    return skills.size();
}

uint CharacterSheet::getSkillCount(uint level) {
    int skillCount = 0;
    foreach (CharacterSkill *skill, skills.values()) {
        if (skill->getLevel() == level) {
            skillCount++;
        }
    }
    return skillCount;
}

AttributeEnhancer* CharacterSheet::getAttributeEnhancer(Attribute attribute) {
    if (attributeEnhancers.contains(attribute)) {
        return attributeEnhancers[attribute];
    } else {
        return NULL;
    }
}

CharacterSkill* CharacterSheet::getSkill(uint typeID) {
    if (skills.contains(typeID)) {
        return skills[typeID];
    } else {
        return NULL;
    }
}

