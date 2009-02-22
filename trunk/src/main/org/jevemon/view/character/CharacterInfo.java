package org.jevemon.view.character;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Formater;
import org.jevemon.model.charactersheet.CharacterSheet;
import org.jevemon.model.items.skillmap.SkillMap;
import org.jevemon.model.skillintraining.SkillInTraining;
import org.jevemon.view.Ui_CharacterInfo;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class CharacterInfo extends QFrame {

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

	public CharacterInfo() {
		this(null);
	}

	public CharacterInfo(QWidget parent) {
		super(parent);
		ui.setupUi(this);
		setupUi();
	}

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

	}

	public void loadPortrait(String fileName) {
		QPixmap image = new QPixmap();
		image.load(fileName);

		int size = portrait.height();
		portrait.setPixmap(image.scaledToHeight(size));
	}

	public void loadInfo(CharacterSheet sheet) {
		String textInfo = "<b><font size=5>" + sheet.getName() + "</font></b><br>";
		//textInfo += sheet.getGender() + " " + sheet.getRace() 
		//										+ " " + sheet.getBloodLine() + "<br>";
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
	
	public void loadAttributes(CharacterSheet sheet) {
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
	
	public void loadSkills(CharacterSheet sheet){
		skillPoints.setText("<b>" 
				+ Formater.printLong(sheet.getSkillPoints()) + " total SP</b>");
		skillPoints.setToolTip(sheet.getCloneName() 
				+ " (" + Formater.printLong(sheet.getCloneSkillPoints()) + " SP)");
		skills.setText("<b>" + sheet.getSkillCount() + " known skills</b>");
		skills.setToolTip(	  sheet.getLevel1Skills() + " skills at level I\n"
							+ sheet.getLevel2Skills() + " skills at level II\n"
							+ sheet.getLevel3Skills() + " skills at level III\n"
							+ sheet.getLevel4Skills() + " skills at level IV\n"
							+ sheet.getLevel5Skills() + " skills at level V");
		levelVSkills.setText("<b>" 
				+ sheet.getLevel5Skills() + " skills at level V</b>");
	}
	
	public void loadSkillInTrraining(SkillInTraining inTraining) throws JEVEMonException{
		
		
		String trainingText = "<b>Training: </b>";
		String timeText = "";
		StringBuffer endText = new StringBuffer("");
		SimpleDateFormat dateFormat 
					= new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss", Locale.getDefault());
		FieldPosition fieldPos = new FieldPosition(1);
		
		if (inTraining.skillInTraining() == 0){
			trainingText += "no skill in training!";
		} else {
			trainingText += SkillMap.getInstance()
									.getSkill(inTraining.getTrainingTypeID()).getTypeName();
			switch (inTraining.getTrainingToLevel()){
				case 1 : trainingText += " I "; break;
				case 2 : trainingText += " II "; break;
				case 3 : trainingText += " III "; break;
				case 4 : trainingText += " IV "; break;
				case 5 : trainingText += " V "; break;
				default : trainingText += " 0 ";
			}
			trainingText += Formater.printPercent(inTraining.calculateCompletion()) + "%";
			
			
			dateFormat.format(new Date(inTraining.getTrainingEndTime()), endText, fieldPos);
			long now = Calendar.getInstance(TimeZone.getTimeZone("GMT") 
													,Locale.getDefault()).getTimeInMillis();
			long time = inTraining.getTrainingEndTime() - now;
			
			timeText += Formater.printTime(time);
			
		}

		skillInTraining.setText(trainingText);
		timeLeft.setText(timeText);
		trainingEnd.setText(endText.toString());
	}

}
