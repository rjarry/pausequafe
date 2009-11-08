package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;


public class MonitoredCharacter {

	////////////
	// fields //
	////////////
	private APIData api;
	private CharacterSheet sheet;
	private List<SkillPlan> skillPlanList;
	
	private SkillInTraining inTraining;
	private SkillQueue queue;

	private String imageLocation;
	
	/////////////////
	// constructor //
	/////////////////
	public MonitoredCharacter(APIData api) {
		super();
		this.api = api;
		this.skillPlanList = new ArrayList<SkillPlan>();
	}


	////////////////////
	// public methods //
	////////////////////
	@Override
	public String toString() {
		return api.getCharacterName();
	}

	public int skillPlanCount(){
		return skillPlanList.size();
	}
	
	public void addSkillPlan(SkillPlan plan){
		skillPlanList.add(plan);
	}
	
	public void deletePlan(int index){
		skillPlanList.remove(index);
	}

	/////////////
	// getters //
	/////////////
	public APIData getApi() {
		return api;
	}
	public CharacterSheet getSheet() {
		return sheet;
	}
	public SkillPlan getSkillPlanAt(int index) {
		return skillPlanList.get(index);
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
