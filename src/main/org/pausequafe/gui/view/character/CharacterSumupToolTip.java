package org.pausequafe.gui.view.character;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.pausequafe.data.business.ServerStatus;
import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QWidget;


public class CharacterSumupToolTip extends QWidget {


	
	
	
	
	////////////
	// fields //
	////////////
	Ui_CharacterSumupToolTip ui = new Ui_CharacterSumupToolTip();
	private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private QTimer eveTimeTimer;
    
    
    //////////////////
    // constructors //
    //////////////////
    public CharacterSumupToolTip() {
        this(null);
    }

    public CharacterSumupToolTip(QWidget parent) {
        super(parent);
        setupUi();
    }
    
    private void setupUi(){
    	ui.setupUi(this);
    	
    	TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    	eveTimeTimer = new QTimer(this);
    	eveTimeTimer.timeout.connect(this, "updateTime()");
    	eveTimeTimer.start(Constants.MINUTE);
    }
    
    ////////////////////
    // public methods //
    ////////////////////
    public void addCharacter(CharacterSumup character){
    	ui.characters.addWidget(character);
    }
    
    public void setServerStatus(ServerStatus status){
    	if(status.isOnLine()){
    		ui.serverStatus.setText("Tranquility Server Status: Online (" + status.getPlayerCount() + " pilots)");
		} else {
			if(status.isUnknown()){
				ui.serverStatus.setText("Tranquility Server Status: Unknown");
			} else {
				ui.serverStatus.setText("Tranquility Server Status: Offline");
			}
		}
    }
    
    
    ///////////
    // slots //
    ///////////
	@SuppressWarnings("unused")
	private void updateTime(){
		Date gmt = Calendar.getInstance().getTime();
		ui.eveTime.setText("Current EVE Time : " + TIME_FORMAT.format(gmt));
	}
    
}
