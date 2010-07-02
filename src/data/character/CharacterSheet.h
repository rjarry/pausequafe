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

#ifndef CHARACTERSHEET_H
#define CHARACTERSHEET_H

#include <QString>
#include <QStringList>
#include <QMap>
#include <QDateTime>

#include "data/character/AttributeEnhancer.h"
#include "data/character/CharacterSkill.h"
#include "data/item/Skill.h"
#include "data/APIObject.h"

class CharacterSheet : public APIObject {

private:
    uint characterID;
    QString name;
    QString race;
    QString bloodLine;
    QString gender;
    QString corporationName;
    uint corporationID;
    QString cloneName;
    uint cloneSkillPoints;
    double balance;
    QStringList corporationTitles;

    QMap<Attribute, AttributeEnhancer*> attributeEnhancers;

    uint _intelligence;
    uint _memory;
    uint _charisma;
    uint _perception;
    uint _willpower;

    QMap<unsigned, CharacterSkill*> skills;

    QDateTime cachedAt;
    bool cached;

public:
    // constructor
    CharacterSheet();
    ~CharacterSheet();

    APIFunction function();

    // methods
    void addAttributeEnhancer(AttributeEnhancer *e);
    void addSkill(CharacterSkill *skill);
    void addTitle(QString & title);
    double getEffectiveAttributeValue(Attribute attribute);
    uint getSkillsBonus(Attribute attribute);
    double getLearningBonus();
    double getTrainingSpeed(Attribute primAttr, Attribute secAttr);
    double getTrainingSpeed(Skill & skill);
    long getSkillPoints();
    uint getSkillCount();
    uint getSkillCount(uint level);
    AttributeEnhancer* getAttributeEnhancer(Attribute attribute);
    CharacterSkill* getSkill(uint typeID);



    // getters
    uint getCharacterID() const                                   { return characterID; }
    QString getName() const                                       { return name; }
    QString getRace() const                                       { return race; }
    QString getBloodLine() const                                  { return bloodLine; }
    QString getGender() const                                     { return gender; }
    QString getCorporationName() const                            { return corporationName; }
    uint getCorporationID() const                                 { return corporationID; }
    QString getCloneName() const                                  { return cloneName; }
    uint getCloneSkillPoints() const                              { return cloneSkillPoints; }
    double getBalance() const                                     { return balance; }
    QStringList & getCorporationTitles()                          { return corporationTitles; }
    QMap<Attribute, AttributeEnhancer*> & getAttributeEnhancers() { return attributeEnhancers; }
    uint getIntelligence() const                                  { return _intelligence; }
    uint getMemory() const                                        { return _memory; }
    uint getCharisma() const                                      { return _charisma; }
    uint getPerception() const                                    { return _perception; }
    uint getWillpower() const                                     { return _willpower; }
    QMap<unsigned, CharacterSkill*> & getSkills()                 { return skills; }
    bool isCached() const                                         { return cached; }
    QDateTime getCachedDate() const                               { return cachedAt; }

    // setters
    void setCharacterID(uint characterID)            { this->characterID = characterID; }
    void setName(QString name)                       { this->name = name; }
    void setRace(QString race)                       { this->race = race; }
    void setBloodLine(QString bloodLine)             { this->bloodLine = bloodLine; }
    void setGender(QString gender)                   { this->gender = gender; }
    void setCorporationName(QString corporationName) { this->corporationName = corporationName; }
    void setCorporationID(uint corporationID)        { this->corporationID = corporationID; }
    void setCloneName(QString cloneName)             { this->cloneName = cloneName; }
    void setCloneSkillPoints(uint cloneSkillPoints)  { this->cloneSkillPoints = cloneSkillPoints; }
    void setBalance(double balance)                  { this->balance = balance; }
    void setIntelligence(uint value)                 { this->_intelligence = value; }
    void setMemory(uint value)                       { this->_memory = value; }
    void setCharisma(uint value)                     { this->_charisma = value; }
    void setPerception(uint value)                   { this->_perception = value; }
    void setWillpower(uint value)                    { this->_willpower = value; }
    void setCached(bool cached)                      { this->cached = cached; }
    void setCachedDate(QDateTime date)               { this->cachedAt = date; }

};

#endif // CHARACTERSHEET_H
