package org.pausequafe.gui.view.main;

import java.io.File;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.dao.CharacterListFactory;
import org.pausequafe.data.dao.SessionDAO;
import org.pausequafe.gui.view.misc.ErrorMessage;
import org.pausequafe.gui.view.misc.ErrorQuestion;
import org.pausequafe.misc.exceptions.PQConfigException;
import org.pausequafe.misc.exceptions.PQConnectionException;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQParseException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class AddCharacterDialog extends QDialog {

    Ui_AddCharacterDialog ui = new Ui_AddCharacterDialog();
    
    private APIData chosenCharacter;
    
    public AddCharacterDialog() {
        this(null);
    }

    public AddCharacterDialog(QWidget parent) {
        super(parent);
        setupUi();
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	this.setWindowTitle("Add New Character");
    	
    	ui.characterLabel.setText("<i>no character chosen...</i>");
    	
    	ui.cancelButton.clicked.connect(this, "reject()");
    	ui.addButton.clicked.connect(this, "accept()");
    	ui.chooseCharButton.clicked.connect(this, "fetchCharacters()");
    	
    	ui.addButton.setEnabled(false);
    	
    	fillUserIDCombo();
    }

	private void fillUserIDCombo() {
	    List<APIData> list = null;
	    
		try {
			list = SessionDAO.getInstance().getDistinctApiData();
		} catch (PQSQLDriverNotFoundException e) {
			ErrorMessage error = new ErrorMessage(this,tr(Constants.DRIVER_NOT_FOUND_ERROR));
			error.exec();
		} catch (PQUserDatabaseFileCorrupted e) {
			ErrorQuestion error = new ErrorQuestion(this,tr(Constants.USER_DB_CORRUPTED_ERROR));
			error.exec();
			if(error.result() == QDialog.DialogCode.Accepted.value()){
				File userDb = new File(SQLConstants.USER_DATABASE_FILE);
				userDb.delete();
			}
		}
		ui.userIDCombo.addItem("");
		for(APIData data : list){
			ui.userIDCombo.addItem("" + data.getUserID(), data);
		}
		
		ui.userIDCombo.currentIndexChanged.connect(this, "updateApiKey()");
		
	}
	

    @SuppressWarnings("unused")
	private void updateApiKey(){
    	try{
    		if(ui.userIDCombo.currentIndex() == 0){
    			ui.apiKeyText.setText("");
    		} else {
    			ui.apiKeyText.setText(((APIData) ui.userIDCombo
    					.itemData(ui.userIDCombo.currentIndex())).getApiKey());
    		}
    	} catch (IndexOutOfBoundsException e){
    		ui.apiKeyText.setText("");
    	}
    }
    
    @SuppressWarnings("unused")
	private void fetchCharacters() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
    	int userID;
    	String apiKey;

    	try {
			if (ui.userIDCombo.currentText().equals("") || ui.apiKeyText.text().equals(""))
				throw new PQException();
			userID = Integer.parseInt(ui.userIDCombo.currentText());
			apiKey = ui.apiKeyText.text();
		} catch (NumberFormatException e1) {
			ErrorMessage error = new ErrorMessage(this, "Please enter correct API details.");
			error.exec();
			return;
		} catch (PQException e) {
			ErrorMessage error = new ErrorMessage(this, "Please enter correct API details.");
			error.exec();
			return;
		}
		
    	List<APIData> characterList;
    	
    	try {
			characterList = CharacterListFactory.getCharList(userID, apiKey);
		} catch (PQConfigException e) {
			ErrorMessage error = new ErrorMessage(this, "Configuration Error.");
			error.exec();
			return;
		} catch (PQConnectionException e) {
			ErrorMessage error = new ErrorMessage(this, "Could not connect to EVE-Online API server.");
			error.exec();
			return;
		} catch (PQParseException e) {
			ErrorMessage error = new ErrorMessage(this, "Wrong API Key.");
			error.exec();
			return;
		}
		
		CharacterListDialog dialog = new CharacterListDialog(this, characterList);
		dialog.exec();
		
		if(dialog.result() == QDialog.DialogCode.Accepted.value()){
			int index = dialog.getChosenCharacterIndex();
			if(index == -1){
				ErrorMessage error = new ErrorMessage(this, "Character not in the list.");
				error.exec();
				return;
			}
			if(SessionDAO.getInstance().isMonitored(characterList.get(index))){
				ErrorMessage error = new ErrorMessage(this, "This character is already monitored.");
				error.exec();
				return;
			}
			chosenCharacter = characterList.get(index);
			ui.characterLabel.setText("<b>" + chosenCharacter.getCharacterName() + "</b>");
			ui.addButton.setEnabled(true);
		}
    }

	public APIData getChosenCharacter() {
		return chosenCharacter;
	}
    
}
