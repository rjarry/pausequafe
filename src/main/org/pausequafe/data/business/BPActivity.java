package org.pausequafe.data.business;

import java.util.ArrayList;
import java.util.List;

public class BPActivity implements Comparable<BPActivity> {

	// ////////////
	// CONSTANTS //
	// ////////////
	public static final int MANUFACTURING = 1;
	public static final int RESEARCH_PE = 3;
	public static final int RESEARCH_ME = 4;
	public static final int COPY = 5;
	public static final int DUPLICATING = 6;
	public static final int REVERSE_ENGINEERING = 7;
	public static final int INVENTION = 8;

	private static final String[] ACTIVITY_NAME = { "", "Manufacturing", "Researching Technology", 
			"Time Efficiency Research", "Material Efficiency Research", "Copying", "Duplicating",
			"Reverse Engineering", "Invention" };

	// /////////
	// FIELDS //
	// /////////
	private int activityID;
	private List<PreRequisite> prereqs = new ArrayList<PreRequisite>();
	private List<BPRequiredMaterial> materials = new ArrayList<BPRequiredMaterial>();

	// //////////////
	// CONSTRUCTOR //
	// //////////////
	public BPActivity(int activityID) {
		this.activityID = activityID;
	}

	// ////////////
	// ACCESSORS //
	// ////////////
	public int getActivityID() {
		return activityID;
	}

	public List<PreRequisite> getPrereqs() {
		return prereqs;
	}

	public List<BPRequiredMaterial> getMaterials() {
		return materials;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public void setPrereqs(List<PreRequisite> prereqs) {
		this.prereqs = prereqs;
	}

	public void setMaterials(List<BPRequiredMaterial> materials) {
		this.materials = materials;
	}

	public String getName() {
		return ACTIVITY_NAME[activityID];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(ACTIVITY_NAME[activityID] + " :\n");
		
		for(BPRequiredMaterial mat : materials){
			sb.append("\t" + mat.toString() + '\n');
		}
		
		return sb.toString();
	}

	public int compareTo(BPActivity o) {
		return activityID - o.activityID;
	}

}
