package org.pausequafe.gui.view.misc;

import org.jevemon.gui.view.misc.Ui_ErrorQuestion;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class ErrorQuestion extends QDialog {

    Ui_ErrorQuestion ui = new Ui_ErrorQuestion();
    
    private QLabel icon;
    private QLabel text;
    private QPushButton okPushButton;
    private QPushButton cancelPushButton;
    
    public ErrorQuestion(String message) {
    	this(null, message);
    }

    public ErrorQuestion(QWidget parent, String message) {
    	super(parent);
        setupUi();
        this.setWindowTitle("Error");
        text.setText(message);
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	
    	icon = (QLabel) this.findChild(QLabel.class, "icon");
    	icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));
    	
    	text = (QLabel) this.findChild(QLabel.class, "text");
    	text.setAlignment(Qt.AlignmentFlag.AlignCenter);
    	
    	cancelPushButton = (QPushButton) this.findChild(QPushButton.class, "cancelPushButton");
    	cancelPushButton.clicked.connect(this, "reject()");
    	
    	okPushButton = (QPushButton) this.findChild(QPushButton.class, "okPushButton");
    	okPushButton.clicked.connect(this, "accept()");
    }
}
