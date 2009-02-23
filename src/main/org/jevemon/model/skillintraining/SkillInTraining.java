package org.jevemon.model.skillintraining;

import java.util.Calendar;
import java.util.TimeZone;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.model.items.skillmap.SkillMap;

public class SkillInTraining {
	
	////////////////////
	// private fields //
	////////////////////
	private long currentTime;
	private long currentTQTime;
	private long trainingEndTime;
	private long trainingStartTime;
    private int trainingTypeID;
    private int trainingStartSP;
    private int trainingDestinationSP;
    private int trainingToLevel;
    private int skillInTraining;
    private long cachedUntil;
    private boolean cached;
    
    
    /////////////////
    // constructor //
    /////////////////
    public SkillInTraining(){
    	
    }
    
    ////////////////////
    // public methods //
    ////////////////////
    /**
     * calculates the training speed for this skill in SP per milliseconds
     * 
     * @return the training speed
     */
    public double trainingSpeed(){
    	return ((double)(trainingDestinationSP - trainingStartSP)
    			/(double)(trainingEndTime - trainingStartTime));
    }
    
    /**
     * calculates the current SP at the method's calling time (GMT)
     * 
     * @return the current SP
     */
    public int currentSP(){
    	long now = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
    	return (int) (Math.round((now - trainingStartTime)
    						*trainingSpeed())+trainingStartSP);
    }
    
    /**
     * calculate SP in this skill when started skilling current level
     * 
     * @return SP when started skilling current level
     */
    public int calculateLevelStartSP(){
    	return Constants.SKILL_LEVEL_REQS[trainingToLevel-1]*calculateRank();
    }
    
    public double calculateCompletion(){
    	double completion = (double)(currentSP() - calculateLevelStartSP())
    					   /(double)(trainingDestinationSP - calculateLevelStartSP());
    	
    	if (completion > 1){
    		return 1;
    	} else {
    		return completion;
    	}
    	
    }
    
    public String toString(){
    	String print = "";
    	
    	try {
			print += SkillMap.getInstance().getSkill(trainingTypeID).getTypeName();
		} catch (JEVEMonException e) {
			print += trainingTypeID;
		}
		
		print += " training to level " + trainingToLevel;
		print += "\nCurrent SP : " + currentSP();
		print += "\nDone : " + calculateCompletion()*100 + " %";
		System.out.println(trainingSpeed()*1000*60*60 + " SP / h");
		
    	return print;
    }
    
    /////////////////////
    // private methods //
    /////////////////////
    private int calculateRank(){
    	return (int) Math.round((double)trainingDestinationSP
    				/(double)Constants.SKILL_LEVEL_REQS[trainingToLevel]);
    }
    

    /////////////
    // getters //
    /////////////
    public long getCurrentTime() {
    	return currentTime;
    }

    public long getCurrentTQTime() {
		return currentTQTime;
	}

	public long getTrainingEndTime() {
		return trainingEndTime;
	}

	public long getTrainingStartTime() {
		return trainingStartTime;
	}

	public int getTrainingTypeID() {
		return trainingTypeID;
	}

	public int getTrainingStartSP() {
		return trainingStartSP;
	}

	public int getTrainingDestinationSP() {
		return trainingDestinationSP;
	}

	public int getTrainingToLevel() {
		return trainingToLevel;
	}
	
	public int skillInTraining(){
		return skillInTraining;
	}

	public long getCachedUntil() {
		return cachedUntil;
	}
	
	public boolean isCached() {
		return cached;
	}

	/////////////
    // setters //
    /////////////
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public void setCurrentTQTime(long currentTQTime) {
		this.currentTQTime = currentTQTime;
	}

	public void setTrainingEndTime(long trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public void setTrainingStartTime(long trainingStartTime) {
		this.trainingStartTime = trainingStartTime;
	}

	public void setTrainingTypeID(int trainingTypeID) {
		this.trainingTypeID = trainingTypeID;
	}

	public void setTrainingStartSP(int trainingStartSP) {
		this.trainingStartSP = trainingStartSP;
	}

	public void setTrainingDestinationSP(int trainingDestinationSP) {
		this.trainingDestinationSP = trainingDestinationSP;
	}

	public void setTrainingToLevel(int trainingToLevel) {
		this.trainingToLevel = trainingToLevel;
	}

	public void setCachedUntil(long cachedUntil) {
		this.cachedUntil = cachedUntil;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}

	public void setSkillInTraining(int skillCount) {
		this.skillInTraining = skillCount;
		
	}




}
