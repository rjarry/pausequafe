package org.pausequafe.data.business;

import java.util.LinkedList;
import java.util.List;

public class SkillPlanList {
	
	////////////
	// fields //
	////////////
	private List<SkillPlan> list;
	
	public SkillPlanList() {
		list = new LinkedList<SkillPlan>();
	}
	
	public void addPlan(SkillPlan plan){
		list.add(plan);
		
		// TODO : ajout dans la base
	}
	
	public void deletePlan(int index){
		list.remove(index);
		
		// TODO : suppresion de la base
	}
	
	public void swapPlans(int index1, int index2){
		
		SkillPlan plan1 = list.get(index1);
		SkillPlan plan2 = list.get(index2);
		
		list.remove(index1);
		list.remove(index2);
		list.add(index1, plan2);
		list.add(index2, plan1);
	}
	
	public SkillPlan getPlan(int index){
		return list.get(index);
	}
	
}
