package org.jevemon.gui.view.misc;

import org.jevemon.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class ErrorMessage extends QDialog {

    Ui_ErrorMessage ui = new Ui_ErrorMessage();

    private QLabel icon;
    private QLabel text;
    private QPushButton pushButton;
    
    
    public ErrorMessage(String message) {
        this(null, message);
    }

    public ErrorMessage(QWidget parent, String message) {
        super(parent);
        setupUi();
        this.setWindowTitle("Error");
        text.setText("<b>" + message + "</b>");
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	icon = (QLabel) this.findChild(QLabel.class, "icon");
    	icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));
    	text = (QLabel) this.findChild(QLabel.class, "text");
    	text.setAlignment(Qt.AlignmentFlag.AlignCenter);
    	pushButton = (QPushButton) this.findChild(QPushButton.class, "pushButton");
    	pushButton.clicked.connect(this, "reject()");
    }
    
    
    
}
