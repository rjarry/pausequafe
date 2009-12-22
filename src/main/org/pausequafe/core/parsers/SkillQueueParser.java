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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.character.SkillInQueue;
import org.pausequafe.data.item.SkillQueue;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

public class SkillQueueParser {

    @SuppressWarnings("unchecked")
    public static SkillQueue parse(Document doc, boolean isCached) throws PQException {
        Element root = doc.getRootElement();

        if (!root.getAttributeValue("version").equals(Constants.API_VERSION)) {
            throw new PQException("wrong API version");
        }
        SkillQueue queue = new SkillQueue();

        // if the sheet was retrieved from a cached file
        queue.setCached(isCached);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;

        try {
            date = dateFormat.parse(root.getChild("currentTime").getValue());
        } catch (ParseException e) {
            // if there's an error during parsing the cached date we take the
            // current time
            date = new Date();
        }
        queue.setCurrentTime(date.getTime());

        try {
            date = dateFormat.parse(root.getChild("cachedUntil").getValue());
        } catch (ParseException e) {
            // if there's an error during parsing the cached date we take the
            // current time
            date = new Date();
        }
        queue.setCachedUntil(date.getTime());

        Element result = root.getChild("result");
        List<Element> skills = result.getChild("rowset").getChildren();
        for (Element row : skills) {
            SkillInQueue skill = new SkillInQueue();

            skill.setQueuePosition(Integer.parseInt(row.getAttributeValue("queuePosition")));
            skill.setTypeID(Integer.parseInt(row.getAttributeValue("typeID")));
            skill.setLevel(Integer.parseInt(row.getAttributeValue("level")));
            skill.setStartSP(Integer.parseInt(row.getAttributeValue("startSP")));
            skill.setEndSP(Integer.parseInt(row.getAttributeValue("endSP")));
            try {
                date = dateFormat.parse(row.getAttributeValue("startTime"));
            } catch (ParseException e) {
                date = new Date();
            }
            skill.setStartTime(date.getTime());
            try {
                date = dateFormat.parse(row.getAttributeValue("endTime"));
            } catch (ParseException e) {
                date = new Date();
            }
            skill.setEndTime(date.getTime());

            queue.getSkillList().add(skill);
        }

        return queue;
    }
}
