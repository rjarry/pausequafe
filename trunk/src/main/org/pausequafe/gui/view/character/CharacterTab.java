package org.pausequafe.gui.view.character;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.data.business.SkillQueue;
import org.pausequafe.misc.util.ApiRequest;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class CharacterTab extends QWidget {

	// //////////////////
	// private fields //
	// //////////////////
	Ui_CharacterTab ui = new Ui_CharacterTab();

	private MonitoredCharacter monChar;

	private CharacterInfo infoWidget;
	private CharacterSkills skillsWidget;
	private SkillPlanListView plansWidget;
	private ApiRequest request;

	public Signal0 requestStarted = new Signal0();
	public Signal2<Integer, MonitoredCharacter> requestFinished = new Signal2<Integer, MonitoredCharacter>();

	// ////////////////
	// constructors //
	// ////////////////
	public CharacterTab(MonitoredCharacter character) {
		setupUi();

		monChar = character;

		request = new ApiRequest(character.getApi());

		request.requestStarted.connect(this, "emitRequestStarted()");
		request.dataRetrieved.connect(infoWidget, "resetTimers()");
		request.dataRetrieved.connect(this,
				"updateCharacterInfo(CharacterSheet, SkillInTraining, SkillQueue, String)");

		infoWidget.requestNeeded.connect(this, "requestInfo()");
		requestInfo();
	}

	// ////////////////
	// widget setup //
	// ////////////////
	private void setupUi() {
		ui.setupUi(this);

		infoWidget = new CharacterInfo(this);
		skillsWidget = new CharacterSkills(this, null, null, null);
		plansWidget = new SkillPlanListView(this);

		ui.verticalLayout.insertWidget(0, infoWidget);

		QVBoxLayout layout1 = new QVBoxLayout();
		layout1.setContentsMargins(0, 0, 0, 0);

		QVBoxLayout layout2 = new QVBoxLayout();
		layout2.setContentsMargins(0, 0, 0, 0);

		ui.skillsFrame.setLayout(layout1);
		ui.plansFrame.setLayout(layout2);

		ui.skillsFrame.layout().addWidget(skillsWidget);
		ui.plansFrame.layout().addWidget(plansWidget);

	}

	// //////////////////
	// public methods //
	// //////////////////
	public void updateCharacterInfo(CharacterSheet sheet, SkillInTraining inTraining,
			SkillQueue queue, String imageLocation) {

		monChar.setSheet(sheet);
		monChar.setInTraining(inTraining);
		monChar.setImageLocation(imageLocation);
		monChar.setQueue(queue);

		infoWidget.loadInfo(monChar.getSheet());
		infoWidget.loadAttributes(monChar.getSheet());
		infoWidget.loadPortrait(monChar.getImageLocation());

		skillsWidget.loadSkills(monChar.getSheet());
		skillsWidget.loadSkillInTraining(monChar.getSheet(), monChar.getInTraining(), monChar
				.getQueue());

		int returnCode = ApiRequest.OK;
		if (sheet != null && sheet.getCharacterID() == ApiRequest.AUTHENTICATION_ERROR) {
			returnCode = ApiRequest.AUTHENTICATION_ERROR;
		} else if (sheet == null
				|| (sheet != null && sheet.getCharacterID() == ApiRequest.CONNECTION_ERROR)) {
			returnCode = ApiRequest.CONNECTION_ERROR;
		}
		requestFinished.emit(returnCode, monChar);
	}

	// /////////
	// slots //
	// /////////
	public void requestInfo() {
		QThread thread = new QThread(request);
		thread.start();
	}

	@SuppressWarnings("unused")
	private void emitRequestStarted() {
		requestStarted.emit();
	}

	// ///////////
	// getters //
	// ///////////
	public MonitoredCharacter getCharacter() {
		return monChar;
	}

}
