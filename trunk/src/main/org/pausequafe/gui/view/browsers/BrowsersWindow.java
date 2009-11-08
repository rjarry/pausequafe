package org.pausequafe.gui.view.browsers;

import java.io.File;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.gui.model.characters.MonitoredCharactersAndSkillPlansModel;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;

public class BrowsersWindow extends QWidget {

	Ui_BrowsersWindow ui = new Ui_BrowsersWindow();

	// //////////////////
	// private fields //
	// //////////////////
	private BrowserSkillTab skillBrowser;
	private BrowserShipTab shipBrowser;
	private BrowserItemTab moduleBrowser;

	private QToolBar toolBar;
	private QComboBox sheetCombo;

	// ////////////////
	// constructors //
	// ////////////////
	public BrowsersWindow() {
		this(null);
	}

	public BrowsersWindow(QWidget parent) {
		super(parent);
		setupUi();
		updateSheetList();
		try {
			MonitoredCharactersAndSkillPlansModel.getInstance().listUpdated.connect(this, "updateSheetList()");
		} catch (PQSQLDriverNotFoundException e) {
			popSQLDriverError();
		} catch (PQUserDatabaseFileCorrupted e) {
			popUserDBCorrupt();
		}
	}

	// ////////////////
	// widget setup //
	// ////////////////
	private void setupUi() {
		ui.setupUi(this);

		this.setWindowTitle("Browsers");

		toolBar = new QToolBar(this);
		ui.verticalLayout.insertWidget(0, toolBar);

		sheetCombo = new QComboBox(this);
		toolBar.addWidget(new QLabel(" Active Character : "));
		toolBar.addWidget(sheetCombo);

		sheetCombo.addItem("no character");
		sheetCombo.currentIndexChanged.connect(this, "changeCurrentCharacter(int)");

		skillBrowser = new BrowserSkillTab(this, Constants.SKILLS_MARKETGROUPID);
		shipBrowser = new BrowserShipTab(this, Constants.SHIPS_MARKETGROUPID);
		moduleBrowser = new BrowserItemTab(this, Constants.MODULES_MARKETGROUPID);

		ui.tabWidget.removeTab(0);

		ui.tabWidget.addTab(skillBrowser, "Skills");
		ui.tabWidget.addTab(shipBrowser, "Ships");
		ui.tabWidget.addTab(moduleBrowser, "Ship Equipement");

		this.resize(1100, 700);
	}

	// /////////
	// slots //
	// /////////
	@SuppressWarnings("unused")
	private void changeCurrentCharacter(int index) {
		int i = index;
		skillBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
		shipBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
		moduleBrowser.setSheet((CharacterSheet) sheetCombo.itemData(index));
	}

	private void updateSheetList() {
		sheetCombo.clear();
		sheetCombo.addItem("no character");
		try {
			for (MonitoredCharacter character : MonitoredCharactersAndSkillPlansModel.getInstance().getList()) {
				sheetCombo.addItem(character.getSheet().getName(), character.getSheet());
			}
		} catch (PQSQLDriverNotFoundException e) {
			popSQLDriverError();
		} catch (PQUserDatabaseFileCorrupted e) {
			popUserDBCorrupt();
		}
	}

	private void popUserDBCorrupt() {
		ErrorQuestion error = new ErrorQuestion(this, tr(Constants.USER_DB_CORRUPTED_ERROR));
		error.exec();
		if (error.result() == QDialog.DialogCode.Accepted.value()) {
			File userDb = new File(SQLConstants.USER_DATABASE_FILE);
			userDb.delete();
		}
	}

	private void popSQLDriverError() {
		ErrorMessage error = new ErrorMessage(this, tr(Constants.DRIVER_NOT_FOUND_ERROR));
		error.exec();
	}

}
