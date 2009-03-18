package org.jevemon.gui.view.main;

import org.jevemon.misc.util.Constants;

import com.trolltech.qt.gui.*;

public class DeleteCharacterDialog extends QDialog {

    Ui_DeleteCharacterDialog ui = new Ui_DeleteCharacterDialog();

    private QLabel iconLabel;
    private QLabel textLabel;
    private QPushButton removeButton;
    private QPushButton cancelButton;

    public DeleteCharacterDialog(String characterName) {
        this(null, characterName);
    }

    public DeleteCharacterDialog(QWidget parent, String characterName) {
        super(parent);
        setupUi();
        textLabel.setText("Are you sure you want to remove <b>" + characterName + "</b>?");
    }
    
    
    
    private void setupUi(){
    	ui.setupUi(this);
    	removeButton = (QPushButton) this.findChild(QPushButton.class, "removeButton");
    	cancelButton = (QPushButton) this.findChild(QPushButton.class, "cancelButton");
    	iconLabel = (QLabel) this.findChild(QLabel.class, "iconLabel");
    	textLabel = (QLabel) this.findChild(QLabel.class, "textLabel");
    	
    	removeButton.clicked.connect(this, "accept()");
    	cancelButton.clicked.connect(this, "reject()");
    	
    	iconLabel.setPixmap(new QPixmap(Constants.QUESTION_ICON_FILE));    	
    }
}
