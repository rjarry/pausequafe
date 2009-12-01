package org.pausequafe.data.business;

import java.util.HashMap;
import java.util.Map;

public class Blueprint extends Item {

	public static final int ATTRIBUTE_COUNT = 8;

	// /////////
	// FIELDS //
	// /////////
	private int productTypeID;

	private String icon;
	private int techLevel;

	private long productionTime;
	private long researchMETime;
	private long researchPETime;
	private long inventionTime;
	private long copyTime;
	private int wasteFactor;
	private int batchSize;
	private int maxRuns;
	private double basePrice;

	private Map<Integer, BPActivity> activities;

	// //////////////
	// CONSTRUCTOR //
	// //////////////
	public Blueprint(int typeID, String typeName, String icon, int metaGroupID, int productTypeID,
			int techLevel, long productionTime, long researchMETime, long researchPETime,
			long inventionTime, long copyTime, int wasteFactor, int batchSize, double basePrice,
			int categoryID, int maxRuns) {

		super(typeID, typeName, metaGroupID, categoryID);

		this.icon = icon;
		this.productTypeID = productTypeID;
		this.techLevel = techLevel;
		this.productionTime = productionTime;
		this.researchMETime = researchMETime;
		this.researchPETime = researchPETime;
		this.inventionTime = inventionTime;
		this.copyTime = copyTime;
		this.wasteFactor = wasteFactor;
		this.batchSize = batchSize;
		this.basePrice = basePrice;
		this.setMaxRuns(maxRuns);

		this.activities = new HashMap<Integer, BPActivity>();
	}

	// ////////////
	// ACCESSORS //
	// ////////////
	public int getProductTypeID() {
		return productTypeID;
	}

	public int getTechLevel() {
		return techLevel;
	}

	public long getProductionTime() {
		return productionTime;
	}

	public long getResearchMETime() {
		return researchMETime;
	}

	public long getResearchPETime() {
		return researchPETime;
	}

	public long getInventionTime() {
		return inventionTime;
	}

	public long getCopyTime() {
		return copyTime;
	}

	public int getWasteFactor() {
		return wasteFactor;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public Map<Integer, BPActivity> getActivities() {
		return activities;
	}

	public void setProductTypeID(int productTypeID) {
		this.productTypeID = productTypeID;
	}

	public void setTechLevel(int techLevel) {
		this.techLevel = techLevel;
	}

	public void setProductionTime(long productionTime) {
		this.productionTime = productionTime;
	}

	public void setResearchMETime(long researchMETime) {
		this.researchMETime = researchMETime;
	}

	public void setResearchPETime(long researchPETime) {
		this.researchPETime = researchPETime;
	}

	public void setInventionTime(long inventionTime) {
		this.inventionTime = inventionTime;
	}

	public void setCopyTime(long copyTime) {
		this.copyTime = copyTime;
	}

	public void setWasteFactor(int wasteFactor) {
		this.wasteFactor = wasteFactor;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public void setActivities(Map<Integer, BPActivity> activities) {
		this.activities = activities;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setMaxRuns(int maxRuns) {
		this.maxRuns = maxRuns;
	}

	public int getMaxRuns() {
		return maxRuns;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(typeName + " :\n");
		for (BPActivity act : activities.values()) {
			sb.append(act.toString());
		}
		sb.append('\n');
		return sb.toString();
	}

}
