package org.pausequafe.gui.view.main;

import java.util.List;

import org.pausequafe.data.business.APIData;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class CharacterListDialog extends QDialog {

    Ui_CharacterListDialog ui = new Ui_CharacterListDialog();
    
    List<APIData> characterList;
    
    private int chosenCharacterIndex = -1;


    public CharacterListDialog(List<APIData> characterList) {
        this(null, characterList);
    }

    public CharacterListDialog(QWidget parent, List<APIData> characterList) {
        super(parent);
        this.characterList = characterList;
        setupUi();
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	ui.chooseButton.clicked.connect(this, "accept()");
    	ui.chooseButton.setEnabled(false);
    	
    	for(APIData character : characterList){
    		ui.charListWidget.addItem(character.getCharacterName());
    	}
    	
    	ui.charListWidget.currentRowChanged.connect(this, "updateIndex()");
    	ui.charListWidget.doubleClicked.connect(this, "accept()");
    	
    }
    
    @SuppressWarnings("unused")
	private void updateIndex(){
    	chosenCharacterIndex = ui.charListWidget.currentIndex().row();
    	ui.chooseButton.setEnabled(true);
    }
    
    public int getChosenCharacterIndex(){
    	return chosenCharacterIndex;
    }
}
