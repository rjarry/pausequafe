package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.misc.util.Formater;

public class SkillQueue {

	// //////////////////
	// private fields //
	// //////////////////

	private long currentTime;
	private long cachedUntil;
	private boolean cached;
	private List<SkillInQueue> skillList;

	// ///////////////
	// constructor //
	// ///////////////
	public SkillQueue() {
		skillList = new ArrayList<SkillInQueue>();
	}

	// //////////////////
	// public methods //
	// //////////////////
	public long getTotalTrainingTime() {
		long time = 0;
		for (SkillInQueue s : skillList) {
			time += s.calculateTrainingTime();
		}
		return time;
	}

	// ///////////////////
	// private methods //
	// ///////////////////

	// ///////////
	// getters //
	// ///////////
	public long getCurrentTime() {
		return currentTime;
	}

	public long getCachedUntil() {
		return cachedUntil;
	}

	public boolean isCached() {
		return cached;
	}

	public List<SkillInQueue> getSkillList() {
		return skillList;
	}

	// ///////////
	// setters //
	// ///////////

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public void setCachedUntil(long cachedUntil) {
		this.cachedUntil = cachedUntil;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}

	public void setSkillList(List<SkillInQueue> skillList) {
		this.skillList = skillList;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (skillList.size() != 0){
			sb.append(skillList.size() + " skill");
			if(skillList.size() > 1)
				sb.append("s");
			sb.append(" <i>(" + Formater.printTime(getTotalTrainingTime()) + ")</i>");
			int i = 1;
			for(SkillInQueue skill : skillList){
				sb.append("<br>" + i + ": " + skill);
				i++;
			}
		} else {
			sb.append("No Skill in Queue");
		}
		return sb.toString();
	}
}