package org.pausequafe.data.business;

import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.misc.util.Formater;

public class SkillInQueue {

	// //////////////////
	// private fields //
	// //////////////////
	protected long endTime;
	protected long startTime;
	protected int typeID;
	protected int startSP;
	protected int endSP;
	protected int level;
	private int queuePosition;

	public long calculateTrainingTime() {
		return endTime - startTime;
	}

	// ///////////
	// getters //
	// ///////////
	public long getEndTime() {
		return endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public int getTypeID() {
		return typeID;
	}

	public int getStartSP() {
		return startSP;
	}

	public int getEndSP() {
		return endSP;
	}

	public int getLevel() {
		return level;
	}

	public int getQueuePosition() {
		return queuePosition;
	}

	// ///////////
	// getters //
	// ///////////
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public void setStartSP(int startSP) {
		this.startSP = startSP;
	}

	public void setEndSP(int endSP) {
		this.endSP = endSP;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setQueuePosition(int queuePosition) {
		this.queuePosition = queuePosition;
	}

	@Override
	public String toString() {
		String typeName;
		try {
			typeName = ItemDAO.getInstance().findItemById(typeID).getTypeName();
		} catch (Exception e) {
			typeName = typeID + "";
		}
		String level = Formater.printLevel(this.level);
		String trainingTime = Formater.printTime(calculateTrainingTime());
		
		return typeName+" "+level+" <i>("+trainingTime+")</i>";
	}
	
	
}
