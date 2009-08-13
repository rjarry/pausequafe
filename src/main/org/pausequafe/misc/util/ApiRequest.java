package org.pausequafe.misc.util;

import java.io.IOException;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.data.dao.CharacterSheetFactory;
import org.pausequafe.data.dao.SkillInTrainingFactory;
import org.pausequafe.misc.exceptions.PQException;

import com.trolltech.qt.QSignalEmitter;

public class ApiRequest extends QSignalEmitter implements Runnable {

	////////////////////
	// private fields //
	////////////////////
	
	private APIData data;
	
	public Signal0 requestStarted;
	public Signal3<CharacterSheet, SkillInTraining, String> dataRetrieved;
	
	/////////////////
	// constructor //
	/////////////////
	public ApiRequest(APIData data) {
		super();
		this.data = data;
		requestStarted = new Signal0();
		dataRetrieved = new Signal3<CharacterSheet, SkillInTraining, String>();
	}
	
	

	public void run(){
		try {
			requestStarted.emit();
			CharacterSheet sheet = CharacterSheetFactory.getCharacterSheet(data);
			String imageLocation = CharacterSheetFactory.getPortrait(data, false);
			SkillInTraining inTraining = SkillInTrainingFactory.getSkillInTraining(data);
			dataRetrieved.emit(sheet ,inTraining, imageLocation);
		} catch (PQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
