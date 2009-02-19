package org.jevemon.model.skillintraining;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SkillInTraining {
	
	////////////////////
	// private fields //
	////////////////////
	private Date currentTime;
	private Date currentTQTime;
	private Date trainingEndTime;
	private Date trainingStartTime;
    private int trainingTypeID;
    private int trainingStartSP;
    private int trainingDestinationSP;
    private int trainingToLevel;
    private int skillInTraining;
    private Date cachedUntil;
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
     * calculates the training speed for this skill in SP per millisecond
     * 
     * @return the training speed
     */
    public double trainingSpeed(){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(trainingEndTime);
    	long end = cal.getTimeInMillis();
    	cal.setTime(trainingStartTime);
    	long start = cal.getTimeInMillis();
    	    	
    	return (trainingDestinationSP - trainingStartSP)/(end - start);
    }
    
    /**
     * calculates the current SP at the method's calling time (GMT)
     * 
     * @return the current SP
     */
    public int currentSP(){
    	TimeZone gmt = TimeZone.getTimeZone("GMT");
    	Locale here = Locale.getDefault();
    	Calendar calNow = Calendar.getInstance(gmt, here);
    	long now = calNow.getTimeInMillis();
    	
    	Calendar calStart = Calendar.getInstance();
    	calStart.setTime(trainingStartTime);
    	long start = calStart.getTimeInMillis();
    	
    	return (int) Math.round((now - start)*trainingSpeed());
    }

    /////////////
    // getters //
    /////////////
    public Date getCurrentTime() {
    	return currentTime;
    }

    public Date getCurrentTQTime() {
		return currentTQTime;
	}

	public Date getTrainingEndTime() {
		return trainingEndTime;
	}

	public Date getTrainingStartTime() {
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

	public Date getCachedUntil() {
		return cachedUntil;
	}
	
	public boolean isCached() {
		return cached;
	}

	/////////////
    // setters //
    /////////////
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public void setCurrentTQTime(Date currentTQTime) {
		this.currentTQTime = currentTQTime;
	}

	public void setTrainingEndTime(Date trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public void setTrainingStartTime(Date trainingStartTime) {
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

	public void setCachedUntil(Date cachedUntil) {
		this.cachedUntil = cachedUntil;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}

	public void setSkillInTraining(int skillCount) {
		this.skillInTraining = skillCount;
		
	}




}
