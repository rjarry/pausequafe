package org.pausequafe.misc.util;

import java.io.IOException;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.data.business.SkillQueue;
import org.pausequafe.data.dao.CharacterSheetFactory;
import org.pausequafe.data.dao.SkillInTrainingFactory;
import org.pausequafe.data.dao.SkillQueueFactory;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.QSignalEmitter;

public class ApiRequest extends QSignalEmitter implements Runnable {

	public static final int OK = 200;
	public static final int CONNECTION_ERROR = 404;
	public static final int AUTHENTICATION_ERROR = 500;
	
	////////////////////
	// private fields //
	////////////////////
	
	private APIData data;
	
	public Signal0 requestStarted;
	public Signal4<CharacterSheet, SkillInTraining, SkillQueue, String> dataRetrieved;
	
	/////////////////
	// constructor //
	/////////////////
	public ApiRequest(APIData data) {
		super();
		this.data = data;
		requestStarted = new Signal0();
		dataRetrieved = new Signal4<CharacterSheet, SkillInTraining, SkillQueue, String>();
	}
	
	

	public void run(){
		try {
			requestStarted.emit();
			CharacterSheet sheet = CharacterSheetFactory.getCharacterSheet(data);
			SkillInTraining inTraining = SkillInTrainingFactory.getSkillInTraining(data);
			SkillQueue queue = SkillQueueFactory.getSkillQueue(data);
			String imageLocation = CharacterSheetFactory.getPortrait(data, false);
			dataRetrieved.emit(sheet ,inTraining, queue, imageLocation);
		} catch (PQException e) { // api details are incorrect
			CharacterSheet sheet = new CharacterSheet();
			sheet.setName(data.getCharacterName());
			sheet.setCharacterID(AUTHENTICATION_ERROR);
			dataRetrieved.emit(sheet ,null, null, null);
		} catch (IOException e) { // connection failed and there's no local cache file
			CharacterSheet sheet = new CharacterSheet();
			sheet.setName(data.getCharacterName());
			sheet.setCharacterID(CONNECTION_ERROR);
			dataRetrieved.emit(sheet ,null, null, null);
		}
	}

}
