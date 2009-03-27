package org.pausequafe.gui.view.character;

import java.util.List;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPalette.ColorRole;

/**
 * This widget gathers all the information about a character.
 * 
 * @author diabeteman
 */
public class CharacterInfo extends QFrame {

	////////////////////
	// private fields //
	////////////////////
	private Ui_CharacterInfo ui = new Ui_CharacterInfo();
	
	private QLabel portrait;
	
	private QLabel info;
	
	private QLabel charisma;
	private QLabel intelligence;
	private QLabel memory;
	private QLabel perception;
	private QLabel willpower;
	private QPushButton refreshButton;
	
	private QLabel clock;
	
	private QTimer timerHour;
	private QTimer timerSecond;
	private CountdownTimer timeLeft;
	
	public Signal0 requestNeeded;

	//////////////////
	// constructors //
	//////////////////
	
	public CharacterInfo() {
		this(null);
	}

	public CharacterInfo(QWidget parent) {
		super(parent);
		ui.setupUi(this);
		setupUi();
	}

	//////////////////
	// widget setup //
	//////////////////
	private void setupUi() {
		
		portrait = (QLabel) this.findChild(QLabel.class, "portrait");
		info = (QLabel) this.findChild(QLabel.class, "info");
		charisma = (QLabel) this.findChild(QLabel.class, "charisma");
		intelligence = (QLabel) this.findChild(QLabel.class, "intelligence");
		memory = (QLabel) this.findChild(QLabel.class, "memory");
		perception = (QLabel) this.findChild(QLabel.class, "perception");
		willpower = (QLabel) this.findChild(QLabel.class, "willpower");
		refreshButton = (QPushButton) this.findChild(QPushButton.class, "refreshButton");
		refreshButton.clicked.connect(this, "emitRequestSignal()");
		refreshButton.setEnabled(false);
		clock = (QLabel) this.findChild(QLabel.class, "clock");
		clock.setAlignment(Qt.AlignmentFlag.AlignRight);
		clock.setForegroundRole(ColorRole.Mid);
		clock.setText("00:00");
		
		QPixmap image = new QPixmap();
		image.load(Constants.BLANK_PORTRAIT);
		
		int size = portrait.size().height();
		portrait.setPixmap(image.scaledToHeight(size));
		
		timerHour = new QTimer();
		timerHour.timeout.connect(this, "emitRequestSignal()");
		requestNeeded = new Signal0();
		
		timerSecond = new QTimer();
		timerSecond.timeout.connect(this, "decrementOneSecond()");
		
		timeLeft = new CountdownTimer(60,0);
	}

	////////////////////
	// public methods //
	////////////////////
	/**
	 * Loads the portrait of the character.
	 * 
	 * @param fileName
	 * 			portrait image file location
	 */
	public void loadPortrait(String fileName) {
		QPixmap image = new QPixmap();
		image.load(fileName);

		int size = portrait.size().height();
		portrait.setPixmap(image.scaledToHeight(size));
	}

	/**
	 * Sets up basic info about the character
	 * 
	 * @param sheet
	 * 			the character
	 */
	public void loadInfo(CharacterSheet sheet) {
		String textInfo = "<b><font size=5>" + sheet.getName() + "</font></b><br>";
		textInfo += "Corporation: " + sheet.getCorporationName() + "<br>";
		textInfo += "Balance: " + Formater.printDouble(sheet.getBalance()) + " ISK";
				
		List<String> titles = sheet.getCorporationTitles();
		
		if (titles.size() != 0){
			String corpTitles = "";
			for(int i=0 ; i<titles.size()-1 ; i++){
				corpTitles += titles.get(i) + "\n";
			}
			corpTitles += titles.get(titles.size() - 1);
			info.setToolTip(corpTitles);
		}
		info.setText(textInfo);
	}
	
	/**
	 * Sets up the effective attribute values of the character.
	 * 
	 * @param sheet
	 * 			the character
	 */
	public void loadAttributes(CharacterSheet sheet) {
		
		// building of the labels' text
		String charismaText = "Charisma: " 
			+ Formater.printDouble(sheet.getEffectiveAttributeValue("charisma"));
		String intelligenceText = "Intelligence: " 
			+ Formater.printDouble(sheet.getEffectiveAttributeValue("intelligence"));
		String memoryText = "Memory: " 
			+ Formater.printDouble(sheet.getEffectiveAttributeValue("memory"));
		String perceptionText = "Perception: " 
			+ Formater.printDouble(sheet.getEffectiveAttributeValue("perception"));
		String willpowerText = "Willpower: " 
			+ Formater.printDouble(sheet.getEffectiveAttributeValue("willpower"));
		
		charisma.setText(charismaText);
		intelligence.setText(intelligenceText);
		memory.setText(memoryText);
		perception.setText(perceptionText);
		willpower.setText(willpowerText);
		
		// building of the tooltips
		charismaText += " = [("	+ sheet.getCharisma() + " base + "
						+ sheet.getSkillsBonus("charisma") + " skills + "
						+ sheet.getAttributeEnhancer("charisma").getAugmentatorValue() 
						+ " implants) * " + Formater.printDouble(sheet.getLearningBonus())
						+ " from learning bonus]";
		intelligenceText += " = [("	+ sheet.getIntelligence() + " base + "
						+ sheet.getSkillsBonus("intelligence") + " skills + "
						+ sheet.getAttributeEnhancer("intelligence").getAugmentatorValue() 
						+ " implants) * " + Formater.printDouble(sheet.getLearningBonus())
						+ " from learning bonus]";
		memoryText += " = [("	+ sheet.getMemory() + " base + "
						+ sheet.getSkillsBonus("memory") + " skills + "
						+ sheet.getAttributeEnhancer("memory").getAugmentatorValue() 
						+ " implants) * " + Formater.printDouble(sheet.getLearningBonus())
						+ " from learning bonus]";
		perceptionText += " = [("	+ sheet.getPerception() + " base + "
						+ sheet.getSkillsBonus("perception") + " skills + "
						+ sheet.getAttributeEnhancer("perception").getAugmentatorValue() 
						+ " implants) * " + Formater.printDouble(sheet.getLearningBonus())
						+ " from learning bonus]";
		willpowerText += " = [("	+ sheet.getWillpower() + " base + "
						+ sheet.getSkillsBonus("willpower") + " skills + "
						+ sheet.getAttributeEnhancer("willpower").getAugmentatorValue() 
						+ " implants) * " + Formater.printDouble(sheet.getLearningBonus())
						+ " from learning bonus]";
		
		charisma.setToolTip(charismaText);
		intelligence.setToolTip(intelligenceText);
		memory.setToolTip(memoryText);
		perception.setToolTip(perceptionText);
		willpower.setToolTip(willpowerText);
	}
	
	public void resetTimers(){
		timeLeft.minutes = 60;
		timeLeft.seconds = 0;
		timerHour.start(Constants.HOUR);
		timerSecond.start(Constants.SECOND);
		refreshButton.setEnabled(true);
	}
	
	///////////
	// slots //
	///////////
	
	@SuppressWarnings("unused")
	private void emitRequestSignal(){
		refreshButton.setEnabled(false);
		requestNeeded.emit();
	}
	
	@SuppressWarnings("unused")
	private void decrementOneSecond(){
		timeLeft.decrementOneSecond();
		clock.setText(timeLeft.printTime());
	}
	
	private class CountdownTimer {
		private int minutes;
		private int seconds;
		
		public CountdownTimer(int minutes, int seconds) {
			this.minutes = minutes;
			this.seconds = seconds;
		}
		
		public boolean decrementOneSecond(){
			if(seconds == 0){
				if(minutes == 0){
					return false;
				} else {
					seconds = 59;
					minutes--;
				}
			} else {
				seconds--;
			}
			return true;
		}
		
		public String printTime(){
			String time = "";
			if(minutes < 10){
				time += "0" + minutes;
			} else {
				time += minutes;
			}
			if(seconds < 10){
				time += ":0" + seconds;
			} else {
				time += ":" + seconds;
			}
			return time;
		}
	}
	
	
	
	
	
}
