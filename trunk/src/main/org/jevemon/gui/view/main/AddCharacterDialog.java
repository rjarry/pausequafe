package org.jevemon.gui.view.main;

import java.sql.SQLException;
import java.util.List;

import org.jevemon.data.business.APIData;
import org.jevemon.data.dao.CharacterListFactory;
import org.jevemon.data.dao.SessionDAO;
import org.jevemon.gui.view.misc.ErrorMessage;
import org.jevemon.misc.exceptions.JEVEMonConfigException;
import org.jevemon.misc.exceptions.JEVEMonConnectionException;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.exceptions.JEVEMonParseException;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class AddCharacterDialog extends QDialog {

    Ui_AddCharacterDialog ui = new Ui_AddCharacterDialog();
    
    private APIData chosenCharacter;
    
    private QPushButton addButton;
    private QPushButton cancelButton;
    private QPushButton chooseCharButton;
    
    private QComboBox userIDCombo;
    private QLineEdit apiKeyText;
    
    private QLabel characterLabel;

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
    	
    	addButton = (QPushButton) this.findChild(QPushButton.class, "addButton");
    	cancelButton = (QPushButton) this.findChild(QPushButton.class, "cancelButton");
    	chooseCharButton = (QPushButton) this.findChild(QPushButton.class, "chooseCharButton");
    	
    	userIDCombo = (QComboBox) this.findChild(QComboBox.class, "userIDCombo");
    	apiKeyText = (QLineEdit) this.findChild(QLineEdit.class, "apiKeyText");
    	
    	characterLabel = (QLabel) this.findChild(QLabel.class, "characterLabel");
    	characterLabel.setText("<i>no character chosen...</i>");
    	
    	cancelButton.clicked.connect(this, "reject()");
    	addButton.clicked.connect(this, "accept()");
    	chooseCharButton.clicked.connect(this, "fetchCharacters()");
    	
    	addButton.setEnabled(false);
    	
    	fillUserIDCombo();
    }

	private void fillUserIDCombo() {
	    List<APIData> list = null;
	    
		try {
			list = SessionDAO.getInstance().getDistinctApiData();
		} catch (SQLException e) {
			// TODO g�rer cette exception
			e.printStackTrace();
		}
		userIDCombo.addItem("");
		for(APIData data : list){
			userIDCombo.addItem("" + data.getUserID(), data);
		}
		
		userIDCombo.currentIndexChanged.connect(this, "updateApiKey()");
		
	}
	

    @SuppressWarnings("unused")
	private void updateApiKey(){
    	try{
    		if(userIDCombo.currentIndex() == 0){
    			apiKeyText.setText("");
    		} else {
    			apiKeyText.setText(((APIData) userIDCombo
    					.itemData(userIDCombo.currentIndex())).getApiKey());
    		}
    	} catch (IndexOutOfBoundsException e){
    		apiKeyText.setText("");
    	}
    }
    
    @SuppressWarnings("unused")
	private void fetchCharacters(){
    	int userID;
    	String apiKey;

    	try {
			if (userIDCombo.currentText().equals("") || apiKeyText.text().equals(""))
				throw new JEVEMonException();
			userID = Integer.parseInt(userIDCombo.currentText());
			apiKey = apiKeyText.text();
		} catch (NumberFormatException e1) {
			ErrorMessage error = new ErrorMessage(this, "Please enter correct API details.");
			error.exec();
			return;
		} catch (JEVEMonException e) {
			ErrorMessage error = new ErrorMessage(this, "Please enter correct API details.");
			error.exec();
			return;
		}
		
    	List<APIData> characterList;
    	
    	try {
			characterList = CharacterListFactory.getCharList(userID, apiKey);
		} catch (JEVEMonConfigException e) {
			ErrorMessage error = new ErrorMessage(this, "Configuration Error.");
			error.exec();
			return;
		} catch (JEVEMonConnectionException e) {
			ErrorMessage error = new ErrorMessage(this, "Could not connect to EVE-Online API server.");
			error.exec();
			return;
		} catch (JEVEMonParseException e) {
			ErrorMessage error = new ErrorMessage(this, "Wrong API Key.");
			error.exec();
			return;
		}
		
		CharacterListDialog dialog = new CharacterListDialog(this, characterList);
		dialog.exec();
		int index = dialog.getChosenCharacterIndex();
		if(index == -1){
			ErrorMessage error = new ErrorMessage(this, "Character not in the list.");
			error.exec();
			return;
		}
		
		chosenCharacter = characterList.get(index);
		characterLabel.setText("<b>" + chosenCharacter.getCharacterName() + "</b>");
		addButton.setEnabled(true);
		
    }

	public APIData getChosenCharacter() {
		return chosenCharacter;
	}
    
}
