package org.jevemon.misc.util;

import java.io.IOException;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.CharacterSheetFactory;
import org.jevemon.data.dao.SkillInTrainingFactory;
import org.jevemon.misc.exceptions.JEVEMonException;

import com.trolltech.qt.QSignalEmitter;

public class ApiRequest extends QSignalEmitter implements Runnable {

	////////////////////
	// private fields //
	////////////////////
	
	private APIData data;
	private CharacterSheet sheet;
	private SkillInTraining inTraining;
	private String imageLocation;
	
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
			sheet = CharacterSheetFactory.getCharacterSheet(data);
			imageLocation = CharacterSheetFactory.getPortrait(data, false);
			inTraining = SkillInTrainingFactory.getSkillInTraining(data);
			dataRetrieved.emit(sheet ,inTraining, imageLocation);
		} catch (JEVEMonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public APIData getData() {
		return data;
	}

	public CharacterSheet getSheet() {
		return sheet;
	}

	public void setData(APIData data) {
		this.data = data;
	}

	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	
}
