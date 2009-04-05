package org.pausequafe.gui.view.character;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.misc.util.ApiRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {

	////////////////////
	// private fields //
	////////////////////
    Ui_CharacterTab ui = new Ui_CharacterTab();

	private CharacterSheet sheet;
	private SkillInTraining inTraining;
	private String imageLocation;
	
	private CharacterInfo infoWidget;
	private CharacterSkills skillsWidget;
	private SkillPlanList plansWidget;
	private ApiRequest request;
	
	public Signal0 requestStarted = new Signal0();
	public Signal0 requestFinished = new Signal0();
	
	//////////////////
	// constructors //
	//////////////////
	public CharacterTab(APIData data){
		setupUi();
		
		request = new ApiRequest(data);
		
		request.requestStarted.connect(this,"emitRequestStarted()");
		request.dataRetrieved.connect(this,"emitRequestFinished()");
		request.dataRetrieved.connect(infoWidget,"resetTimers()");
		request.dataRetrieved.connect(this, "updateCharacterInfo(CharacterSheet, SkillInTraining, String)");
		
		infoWidget.requestNeeded.connect(this, "requestInfo()");
		requestInfo();
	}
	
	//////////////////
	// widget setup //
	//////////////////
	private void setupUi(){
		ui.setupUi(this);
		
		infoWidget = new CharacterInfo(this);
		skillsWidget = new CharacterSkills(this, null, null);
		plansWidget = new SkillPlanList(this);

		ui.scrollLayout.addWidget(skillsWidget);
		ui.scrollLayout.addWidget(plansWidget);
		ui.mainLayout.insertWidget(0,infoWidget);
		
		plansWidget.setEnabled(false);
	}
	
	////////////////////
	// public methods //
	////////////////////
	public void updateCharacterInfo(CharacterSheet sheet, SkillInTraining inTraining, String imageLocation){
		this.sheet = sheet;
		this.inTraining = inTraining;
		this.imageLocation = imageLocation;
		
		infoWidget.loadInfo(this.sheet);
		infoWidget.loadAttributes(this.sheet);
		infoWidget.loadPortrait(this.imageLocation);

		skillsWidget.loadSkills(this.sheet);
		skillsWidget.loadSkillInTraining(this.sheet, this.inTraining);
	}
	
	///////////
	// slots //
	///////////
	public void requestInfo(){
		QThread thread = new QThread(request);
		thread.start();
	}

	@SuppressWarnings("unused")
	private void emitRequestStarted(){
		requestStarted.emit();
	}
	
	@SuppressWarnings("unused")
	private void emitRequestFinished(){
		requestFinished.emit();
	}

	/////////////
	// getters //
	/////////////
	public CharacterSheet getSheet() {
		return sheet;
	}
	public SkillInTraining getInTraining() {
		return inTraining;
	}
	public String getImageLocation() {
		return imageLocation;
	}


	/////////////
	// setters //
	/////////////
	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	public void setInTraining(SkillInTraining inTraining) {
		this.inTraining = inTraining;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
}
