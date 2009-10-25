package org.pausequafe.gui.view.misc;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class ErrorMessage extends QDialog {

    Ui_ErrorMessage ui = new Ui_ErrorMessage();

    private String message;
    
    
    public ErrorMessage(String message) {
        this(null, message);
    }

    public ErrorMessage(QWidget parent, String message) {
        super(parent);
        this.message = message;
        setupUi();
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	this.setWindowTitle("Error");
    	ui.icon.setPixmap(new QPixmap(Constants.ERROR_ICON_FILE));
    	ui.text.setText(message);
    	ui.text.setAlignment(Qt.AlignmentFlag.AlignCenter);
    	ui.pushButton.clicked.connect(this, "reject()");
    }
}
