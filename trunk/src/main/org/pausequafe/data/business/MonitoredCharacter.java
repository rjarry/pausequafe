package org.pausequafe.data.business;


public class MonitoredCharacter {
	
	////////////
	// fields //
	////////////
	private APIData api;
	private CharacterSheet sheet;
	private SkillPlanList skillPlanList;
	
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
	
	
}
