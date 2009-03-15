package org.jevemon.gui.view.character;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.business.SkillInTraining;
import org.jevemon.data.dao.SkillMap;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.Formater;
import org.jevemon.view.character.Ui_CharacterInfo;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

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
	
	private QLabel skillPoints;
	private QLabel skills;
	private QLabel levelVSkills;
	
	private QLabel skillInTraining;
	private QLabel timeLeft;
	private QLabel trainingEnd;
	
	private double currentSP = 0;
	private long trainingTimeLeft = 0;
	private double trainingSpeed = 0;

	private boolean thereIsASkillTraining = false;
	
	private QTimer timer;

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
		setFrameShape(QFrame.Shape.Panel);
		setFrameShadow(QFrame.Shadow.Sunken);
		QRect rect = new QRect(
				5,5,this.width()-10,this.height()-10);
		setFrameRect(rect);
		
		portrait = (QLabel) this.findChild(QLabel.class, "portrait");
		info = (QLabel) this.findChild(QLabel.class, "info");
		charisma = (QLabel) this.findChild(QLabel.class, "charisma");
		intelligence = (QLabel) this.findChild(QLabel.class, "intelligence");
		memory = (QLabel) this.findChild(QLabel.class, "memory");
		perception = (QLabel) this.findChild(QLabel.class, "perception");
		willpower = (QLabel) this.findChild(QLabel.class, "willpower");
		skillPoints = (QLabel) this.findChild(QLabel.class, "skillPoints");
		skills = (QLabel) this.findChild(QLabel.class, "skills");
		levelVSkills = (QLabel) this.findChild(QLabel.class, "levelVSkills");
		
		skillInTraining = (QLabel) this.findChild(QLabel.class, "skillInTraining");
		timeLeft = (QLabel) this.findChild(QLabel.class, "timeLeft");
		trainingEnd = (QLabel) this.findChild(QLabel.class, "trainingEnd");
		
		this.findChild(QGroupBox.class, "groupBox");
		
		timer = new QTimer(this);
		timer.setObjectName("timer");
		timer.timeout.connect(this, "updateValues()");
		
		QPixmap image = new QPixmap();
		image.load(Constants.BLANK_PORTRAIT);
		
		int size = portrait.size().height();
		portrait.setPixmap(image.scaledToHeight(size));
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
	
	/**
	 * Sets up the skills info of the character
	 * 
	 * @param sheet
	 * 			the character sheet
	 */
	public void loadSkills(CharacterSheet sheet){
		// the amount of SP here is only calculated by summing the SP from all skills.
		// it represents the total SP of character at the time of the XML file generation.
		currentSP = sheet.getSkillPoints();
		skillPoints.setText("<b>" 
				+ Formater.printLong(Math.round(currentSP)) + " total SP</b>");
		skillPoints.setToolTip(sheet.getCloneName() 
				+ " (" + Formater.printLong(sheet.getCloneSkillPoints()) + " SP)");
		
		String levelVText = sheet.getLevel1Skills() + " skills at level I\n"
							+ sheet.getLevel2Skills() + " skills at level II\n"
							+ sheet.getLevel3Skills() + " skills at level III\n"
							+ sheet.getLevel4Skills() + " skills at level IV\n"
							+ sheet.getLevel5Skills() + " skills at level V";
		
		skills.setText("<b>" + sheet.getSkillCount() + " known skills</b>");
		skills.setToolTip(levelVText);
		
		levelVSkills.setText("<b>" 
				+ sheet.getLevel5Skills() + " skills at level V</b>");
		levelVSkills.setToolTip(levelVText);
	}
	
	/**
	 * Sets up the skill in training info
	 * 
	 * @param inTraining
	 * 				the skill actually in training
	 * @throws JEVEMonException
	 * 					if there's a problem accessing the skills database
	 */
	public void loadSkillInTraining(CharacterSheet sheet, SkillInTraining inTraining) throws JEVEMonException{
		
		String trainingText = "";
		String timeText = "";
		StringBuffer endText = new StringBuffer("");
		String toolTipText = "";
		SimpleDateFormat dateFormat 
					= new SimpleDateFormat("EEE dd MMMMMMMMMM HH:mm:ss");
		
		

		
		if (inTraining.skillInTraining() == 0){
			// if there's no skill in training
			trainingText += "<b>no skill in training!</b>";
			thereIsASkillTraining  = false;
		} else {
			// else we fill the fields
			thereIsASkillTraining  = true;
			trainingText += "<b>" + SkillMap.getInstance()
							.getSkill(inTraining.getTrainingTypeID()).getTypeName();
			switch (inTraining.getTrainingToLevel()){
				case 1 : trainingText += " I </b>("; break;
				case 2 : trainingText += " II </b>("; break;
				case 3 : trainingText += " III </b>("; break;
				case 4 : trainingText += " IV </b>("; break;
				case 5 : trainingText += " V </b>("; break;
				default : trainingText += " 0 </b>(";
			}
			trainingText += Formater.printPercent(inTraining.calculateCompletion()) + "%)";
			
			// training end date
			dateFormat.format(new Date(inTraining.getTrainingEndTime()), endText, new FieldPosition(0));
			
			// time left
			long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
			trainingTimeLeft = inTraining.getTrainingEndTime() - now;
			if (trainingTimeLeft <= 0){
				trainingTimeLeft = 0;
			}
			timeText += Formater.printTime(trainingTimeLeft);
			
			// calculation of the current SP.
			// here, the correct amount of SP is calculated 
			// by deducting the "last known" SP of skill in training (from the character sheet)
			// and adding the current SP of it.
			currentSP -= sheet.getSkill(inTraining.getTrainingTypeID()).getSkillPoints();
			currentSP += inTraining.currentSP();

			// setup of the current training speed (in SP / millisecond)
			trainingSpeed = inTraining.trainingSpeed();
			
			// training speed tooltip
			toolTipText += "Training at: " 
				+ Math.round(trainingSpeed * Constants.HOUR) + " SP/hour";

			// we start the timer only if there's a skill in training
			timer.start(Constants.SECOND);
		}
		
		skillInTraining.setText(trainingText);
		timeLeft.setText(timeText);
		trainingEnd.setText(endText.toString());
		skillInTraining.setToolTip(toolTipText);
		timeLeft.setToolTip(toolTipText);
		trainingEnd.setToolTip(toolTipText);
		
	}
	
	///////////
	// slots //
	///////////
	/**
	 * Updates total SP and time left fields every second
	 */
	@SuppressWarnings("unused")
	private void updateValues(){
		if (thereIsASkillTraining) {
			if (trainingTimeLeft >= Constants.SECOND){
				currentSP += trainingSpeed * Constants.SECOND;			
				skillPoints.setText("<b>" 
						+ Formater.printLong(Math.round(currentSP)) + " total SP</b>");
				
				trainingTimeLeft -= Constants.SECOND;
				timeLeft.setText(Formater.printTime(trainingTimeLeft));
			} else {
				timer.stop();
				trainingTimeLeft = 0;
				thereIsASkillTraining = false;
				timeLeft.setText(Formater.printTime(trainingTimeLeft));
			}
		}
	}
}
