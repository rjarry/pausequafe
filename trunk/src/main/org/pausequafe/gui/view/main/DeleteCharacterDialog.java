package org.pausequafe.gui.view.main;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class DeleteCharacterDialog extends QDialog {

    Ui_DeleteCharacterDialog ui = new Ui_DeleteCharacterDialog();

    public DeleteCharacterDialog(String characterName) {
        this(null, characterName);
    }

    public DeleteCharacterDialog(QWidget parent, String characterName) {
        super(parent);
        setupUi();
        ui.textLabel.setText("Are you sure you want to remove <br><b>" + characterName + "</b>?");
    }
    
    
    
    private void setupUi(){
    	ui.setupUi(this);
    	this.setWindowTitle("Delete Character?");
    	
    	ui.textLabel.setAlignment(Qt.AlignmentFlag.AlignCenter);
    	
    	ui.removeButton.clicked.connect(this, "accept()");
    	ui.cancelButton.clicked.connect(this, "reject()");
    	
    	ui.iconLabel.setPixmap(new QPixmap(Constants.QUESTION_ICON_FILE));    	
    }
}
