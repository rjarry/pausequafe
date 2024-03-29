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

package org.pausequafe.core.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.character.AttributeEnhancer;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.character.CharacterSkill;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

/**
 * Outputs a CharacterSheet object from an XML file.
 * 
 * @author diabeteman
 */
public class CharacterSheetParser {

    @SuppressWarnings("unchecked")
    public static CharacterSheet parse(Document doc, boolean isCached) throws PQException {
        Element root = doc.getRootElement();

        // tests if the API version from the XML file is ok
        if (!root.getAttributeValue("version").equals(Constants.API_VERSION)) {
            throw new PQException("wrong API version");
        }

        CharacterSheet sheet = new CharacterSheet();

        // if the sheet was retrieved from a cached file
        sheet.setCached(isCached);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date cachedDate = null;
        try {
            cachedDate = dateFormat.parse(root.getChild("currentTime").getValue());
        } catch (ParseException e) {
            // if there's an error during parsing the cached date we take the
            // current Eve time
            cachedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
                    .getTime();
        }
        sheet.setCachedDate(cachedDate);

        root = root.getChild("result");

        sheet.setCharacterID(Integer.parseInt(root.getChild("characterID").getValue()));
        sheet.setName(root.getChild("name").getValue());
        sheet.setRace(root.getChild("race").getValue());
        sheet.setBloodLine(root.getChild("bloodLine").getValue());
        sheet.setGender(root.getChild("gender").getValue());
        sheet.setCorporationName(root.getChild("corporationName").getValue());
        sheet.setCorporationID(Integer.parseInt(root.getChild("corporationID").getValue()));
        sheet.setCloneName(root.getChild("cloneName").getValue());
        sheet.setCloneSkillPoints(Long.parseLong(root.getChild("cloneSkillPoints").getValue()));
        sheet.setBalance(Double.parseDouble(root.getChild("balance").getValue()));

        // adding attribute enhancers
        List<Element> enhancers = root.getChild("attributeEnhancers").getChildren();
        for (Element row : enhancers) {
            String enhancerName = row.getName();
            AttributeEnhancer enhancer = new AttributeEnhancer();
            if (enhancerName.equals("willpowerBonus")) {
                enhancer.setAttribute("willpower");
                enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
                enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue")
                        .getValue()));
            }
            if (enhancerName.equals("memoryBonus")) {
                enhancer.setAttribute("memory");
                enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
                enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue")
                        .getValue()));
            }
            if (enhancerName.equals("perceptionBonus")) {
                enhancer.setAttribute("perception");
                enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
                enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue")
                        .getValue()));
            }
            if (enhancerName.equals("intelligenceBonus")) {
                enhancer.setAttribute("intelligence");
                enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
                enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue")
                        .getValue()));
            }
            if (enhancerName.equals("charismaBonus")) {
                enhancer.setAttribute("charisma");
                enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
                enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue")
                        .getValue()));
            }
            sheet.addAttributeEnhancer(enhancer);
        }

        sheet.setIntelligence(Integer.parseInt(root.getChild("attributes").getChild("intelligence")
                .getValue()));
        sheet.setMemory(Integer.parseInt(root.getChild("attributes").getChild("memory")
                .getValue()));
        sheet.setPerception(Integer.parseInt(root.getChild("attributes").getChild("perception")
                .getValue()));
        sheet.setCharisma(Integer.parseInt(root.getChild("attributes").getChild("charisma")
                .getValue()));
        sheet.setWillpower(Integer.parseInt(root.getChild("attributes").getChild("willpower")
                .getValue()));

        // browsing through all the rowsets
        List<Element> rowsets = root.getChildren("rowset");
        for (Element rowset : rowsets) {
            // adding skills
            if (rowset.getAttributeValue("name").equals("skills")) {
                List<Element> skills = root.getChild("rowset").getChildren();
                for (Element row : skills) {
                    try {
                        CharacterSkill skill = new CharacterSkill();
                        skill.setTypeID(Integer.parseInt(row.getAttributeValue("typeID")));
                        skill.setSkillPoints(Integer.parseInt(row
                                                        .getAttributeValue("skillpoints")));
                        try {
                            skill.setLevel(Integer.parseInt(row.getAttributeValue("level")));
                        } catch (NumberFormatException e) {
                            // the skill is an unpublished one we dont get the level
                            skill.setLevel(0);
                        }
                        sheet.addSkill(skill);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            // adding corporation roles
            if (rowset.getAttributeValue("name").equals("corporationTitles")) {
                List<Element> titles = rowset.getChildren();
                for (Element title : titles) {
                    sheet.addTitle(title.getAttributeValue("titleName"));
                }
            }
        }

        // done parsing
        return sheet;
    }

}
