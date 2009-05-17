package org.pausequafe.gui.view.character;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.misc.util.ApiRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {

	////////////////////
	// private fields //
	////////////////////
    Ui_CharacterTab ui = new Ui_CharacterTab();

	private MonitoredCharacter monChar;
	private SkillInTraining inTraining;
	private String imageLocation;
	
	private CharacterInfo infoWidget;
	private CharacterSkills skillsWidget;
	private SkillPlanListView plansWidget;
	private ApiRequest request;
	
	public Signal0 requestStarted = new Signal0();
	public Signal0 requestFinished = new Signal0();
	
	//////////////////
	// constructors //
	//////////////////
	public CharacterTab(MonitoredCharacter character){
		setupUi();
		
		monChar = character;
		
		request = new ApiRequest(character.getApi());
		
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
		plansWidget = new SkillPlanListView(this);

		ui.verticalLayout.insertWidget(0,infoWidget);
		
		QVBoxLayout layout1 = new QVBoxLayout();
		layout1.setContentsMargins(0, 0, 0, 0);
		
		QVBoxLayout layout2 = new QVBoxLayout();
		layout2.setContentsMargins(0, 0, 0, 0);
		
		ui.skillsFrame.setLayout(layout1);
		ui.plansFrame.setLayout(layout2);
		
		ui.skillsFrame.layout().addWidget(skillsWidget);
		ui.plansFrame.layout().addWidget(plansWidget);
		
	}
	
	////////////////////
	// public methods //
	////////////////////
	public void updateCharacterInfo(CharacterSheet sheet, SkillInTraining inTraining, String imageLocation){
		this.monChar.setSheet(sheet);
		this.inTraining = inTraining;
		this.imageLocation = imageLocation;
		
		infoWidget.loadInfo(this.monChar.getSheet());
		infoWidget.loadAttributes(this.monChar.getSheet());
		infoWidget.loadPortrait(this.imageLocation);

		skillsWidget.loadSkills(this.monChar.getSheet());
		skillsWidget.loadSkillInTraining(this.monChar.getSheet(), this.inTraining);
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
	public MonitoredCharacter getCharacter() {
		return monChar;
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
	public void setSheet(MonitoredCharacter character) {
		this.monChar = character;
	}
	public void setInTraining(SkillInTraining inTraining) {
		this.inTraining = inTraining;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
}
