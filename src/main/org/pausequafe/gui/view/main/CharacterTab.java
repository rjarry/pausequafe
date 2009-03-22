package org.pausequafe.gui.view.main;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.gui.view.character.CharacterInfo;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.ApiRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {
	
	private CharacterSheet sheet;
	private SkillInTraining inTraining;
	private String imageLocation;
	private CharacterInfo infoWidget;
	
	public Signal0 requestStarted = new Signal0();
	public Signal0 requestFinished = new Signal0();
	
	public CharacterTab(APIData data){
		infoWidget = new CharacterInfo(this);
		
		ApiRequest request = new ApiRequest(data);
		QThread thread = new QThread(request);
		
		request.requestStarted.connect(this,"emitRequestStarted()");
		request.dataRetrieved.connect(this,"emitRequestFinished()");
		request.dataRetrieved.connect(this, "updateCharacterInfo(CharacterSheet, SkillInTraining, String)");
		thread.start();
		
		infoWidget.show();
	}
	
	public void updateCharacterInfo(CharacterSheet sheet, SkillInTraining inTraining, String imageLocation){
		this.sheet = sheet;
		this.inTraining = inTraining;
		this.imageLocation = imageLocation;
		
		infoWidget.loadInfo(this.sheet);
		infoWidget.loadAttributes(this.sheet);
		infoWidget.loadSkills(this.sheet);
		infoWidget.loadPortrait(this.imageLocation);
		try {
			infoWidget.loadSkillInTraining(this.sheet, this.inTraining);
		} catch (PQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void emitRequestStarted(){
		requestStarted.emit();
	}
	
	@SuppressWarnings("unused")
	private void emitRequestFinished(){
		requestFinished.emit();
	}

	public CharacterSheet getSheet() {
		return sheet;
	}
	
	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	
	public SkillInTraining getInTraining() {
		return inTraining;
	}
	
	public void setInTraining(SkillInTraining inTraining) {
		this.inTraining = inTraining;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
}
