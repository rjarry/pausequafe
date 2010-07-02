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

package org.pausequafe.core.threads;

import java.io.IOException;

import org.pausequafe.core.factory.CharacterSheetFactory;
import org.pausequafe.core.factory.SkillInTrainingFactory;
import org.pausequafe.core.factory.SkillQueueFactory;
import org.pausequafe.data.character.APIData;
import org.pausequafe.data.character.CharacterSheet;
import org.pausequafe.data.character.SkillInTraining;
import org.pausequafe.data.character.SkillQueue;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.QSignalEmitter;

public class ApiRequest extends QSignalEmitter implements Runnable {

    public static final int OK = 200;
    public static final int CONNECTION_ERROR = 404;
    public static final int AUTHENTICATION_ERROR = 500;

    // //////////////////
    // private fields //
    // //////////////////

    private APIData data;

    public Signal0 requestStarted;
    public Signal4<CharacterSheet, SkillInTraining, SkillQueue, String> dataRetrieved;

    // ///////////////
    // constructor //
    // ///////////////
    public ApiRequest(APIData data) {
        super();
        this.data = data;
        requestStarted = new Signal0();
        dataRetrieved = new Signal4<CharacterSheet, SkillInTraining, SkillQueue, String>();
    }

    public void run() {
        try {
            requestStarted.emit();
            CharacterSheet sheet = CharacterSheetFactory.getCharacterSheet(data);
            SkillInTraining inTraining = SkillInTrainingFactory.getSkillInTraining(data);
            SkillQueue queue = SkillQueueFactory.getSkillQueue(data);
            String imageLocation = CharacterSheetFactory.getPortrait(data, false);
            dataRetrieved.emit(sheet, inTraining, queue, imageLocation);
        } catch (PQException e) { // api details are incorrect
            CharacterSheet sheet = new CharacterSheet();
            sheet.setName(data.getCharacterName());
            sheet.setCharacterID(AUTHENTICATION_ERROR);
            dataRetrieved.emit(sheet, null, null, null);
        } catch (IOException e) { // connection failed and there's no local cache file
            CharacterSheet sheet = new CharacterSheet();
            sheet.setName(data.getCharacterName());
            sheet.setCharacterID(CONNECTION_ERROR);
            dataRetrieved.emit(sheet, null, null, null);
        }
    }

}
