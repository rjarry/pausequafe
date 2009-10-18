package org.pausequafe.data.business;


public class MonitoredCharacter {
	
	////////////
	// fields //
	////////////
	private APIData api;
	private CharacterSheet sheet;
	private SkillPlanList skillPlanList;
	
	private SkillInTraining inTraining;
	private SkillQueue queue;

	private String imageLocation;
	
	/////////////////
	// constructor //
	/////////////////
	public MonitoredCharacter(APIData api) {
		super();
		this.api = api;
	}


	////////////////////
	// public methods //
	////////////////////

	/////////////
	// getters //
	/////////////
	public APIData getApi() {
		return api;
	}
	public CharacterSheet getSheet() {
		return sheet;
	}
	public SkillPlanList getSkillPlanList() {
		return skillPlanList;
	}
	public SkillInTraining getInTraining() {
		return inTraining;
	}
	public SkillQueue getQueue() {
		return queue;
	}
	public String getImageLocation() {
		return imageLocation;
	}
	
	
	/////////////
	// setters //
	/////////////
	public void setApi(APIData api) {
		this.api = api;
	}
	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	public void addSkillPlan(SkillPlan plan){
		
	}
	public void setInTraining(SkillInTraining inTraining) {
		this.inTraining = inTraining;
	}
	public void setQueue(SkillQueue queue) {
		this.queue = queue;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	
	
}
