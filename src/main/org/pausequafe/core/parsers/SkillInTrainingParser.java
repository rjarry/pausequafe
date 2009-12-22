/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
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
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.character.SkillInTraining;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

public class SkillInTrainingParser {

    public static SkillInTraining parse(Document doc, boolean isCached) throws PQException {

        Element root = doc.getRootElement();

        if (!root.getAttributeValue("version").equals(Constants.API_VERSION)) {
            throw new PQException("wrong API version");
        }

        SkillInTraining inTraining = new SkillInTraining();

        // if the sheet was retrieved from a cached file
        inTraining.setCached(isCached);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance();
        Date cachedDate = null;

        try {
            cachedDate = dateFormat.parse(root.getChild("currentTime").getValue());
        } catch (ParseException e) {
            // if there's an error during parsing the cached date we take the
            // current Eve time
            cachedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();

        }
        cal.setTime(cachedDate);
        inTraining.setCurrentTime(cal.getTimeInMillis());

        try {
            cachedDate = dateFormat.parse(root.getChild("cachedUntil").getValue());
        } catch (ParseException e) {
            // if there's an error during parsing the cached date we take the
            // current Eve time
            cachedDate = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        }
        cal.setTime(cachedDate);
        inTraining.setCachedUntil(cal.getTimeInMillis());

        Element result = root.getChild("result");

        inTraining.setSkillInTraining(Integer.parseInt(result.getChild("skillInTraining")
                .getValue()));

        // if there's no skill in training we don't try to parse the file
        // further.
        if (inTraining.skillInTraining() == 0) {
            return inTraining;
        }

        try {
            cal.setTime(dateFormat.parse(result.getChild("currentTQTime").getValue()));
            inTraining.setCurrentTQTime(cal.getTimeInMillis());
            cal.setTime(dateFormat.parse(result.getChild("trainingEndTime").getValue()));
            inTraining.setEndTime(cal.getTimeInMillis());
            cal.setTime(dateFormat.parse(result.getChild("trainingStartTime").getValue()));
            inTraining.setStartTime(cal.getTimeInMillis());
        } catch (ParseException e) {
            throw new PQException("date parsing error");
        }

        inTraining.setTypeID(Integer.parseInt(result.getChild("trainingTypeID").getValue()));
        inTraining.setStartSP(Integer.parseInt(result.getChild("trainingStartSP").getValue()));
        inTraining.setEndSP(Integer.parseInt(result.getChild("trainingDestinationSP").getValue()));
        inTraining.setLevel(Integer.parseInt(result.getChild("trainingToLevel").getValue()));

        return inTraining;
    }

}
