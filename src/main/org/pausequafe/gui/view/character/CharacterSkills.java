package org.pausequafe.gui.view.character;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.pausequafe.data.business.CharacterSheet;
import org.pausequafe.data.business.SkillInTraining;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.Formater;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;

public class CharacterSkills extends QWidget {

    private Ui_CharacterSkills ui = new Ui_CharacterSkills();
    
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

	public CharacterSkills(CharacterSheet sheet, SkillInTraining inTraining) {
        this(null, sheet, inTraining);
    }

    public CharacterSkills(QWidget parent, CharacterSheet sheet, SkillInTraining inTraining) {
        super(parent);
        setupUi();
        loadSkills(sheet);
        loadSkillInTraining(sheet, inTraining);
    }
    

	private void setupUi(){
    	ui.setupUi(this);
    	
    	skillPoints = (QLabel) this.findChild(QLabel.class, "skillPoints");
		skills = (QLabel) this.findChild(QLabel.class, "skills");
		levelVSkills = (QLabel) this.findChild(QLabel.class, "levelVSkills");
		
		skillInTraining = (QLabel) this.findChild(QLabel.class, "skillInTraining");
		timeLeft = (QLabel) this.findChild(QLabel.class, "timeLeft");
		trainingEnd = (QLabel) this.findChild(QLabel.class, "trainingEnd");
		
		timer = new QTimer(this);
		timer.setObjectName("timer");
		timer.timeout.connect(this, "updateValues()");
    }
    
	/**
	 * Sets up the skills info of the character
	 * 
	 * @param sheet
	 * 			the character sheet
	 */
	public void loadSkills(CharacterSheet sheet){
		if(sheet == null) return;
		
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
	 * @throws PQException
	 * 					if there's a problem accessing the skills database
	 */
	public void loadSkillInTraining(CharacterSheet sheet, SkillInTraining inTraining) {
		if(sheet == null) return;
		
		String trainingText = "";
		String timeText = "";
		StringBuffer endText = new StringBuffer("");
		String toolTipText = "";
		SimpleDateFormat dateFormat 
					= new SimpleDateFormat("EEE dd MMMMMMMMMM HH:mm:ss");

		if (inTraining == null || inTraining.skillInTraining() == 0){
			// if there's no skill in training
			trainingText += "<b>no skill in training!</b>";
			thereIsASkillTraining  = false;
		} else {
			// else we fill the fields
			thereIsASkillTraining  = true;
			
			try {
				trainingText += "<b>" + ItemDAO.getInstance().findItemById(inTraining.getTrainingTypeID()).getTypeName();
			} catch (PQException e) {
				trainingText += "<b>" +inTraining.getTrainingTypeID();
			}
			
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
