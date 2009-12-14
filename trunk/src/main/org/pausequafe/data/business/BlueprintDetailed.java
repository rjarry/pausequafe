package org.pausequafe.data.business;

public class BlueprintDetailed extends Blueprint {

    // /////////////////
    // private fields //
    // /////////////////
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

    // //////////////
    // constructor //
    // //////////////

    public BlueprintDetailed(Blueprint bp) {
        super(bp.typeID, bp.typeName, bp.metaGroupID, bp.categoryID);

        for (BPActivity a : bp.activities.values()) {
            this.activities.put(a.getActivityID(), new BPActivity(a));
        }
    }

    public BlueprintDetailed(int typeID, String typeName, int metaGroupID, int categoryID,
            int productTypeID, String icon, int techLevel, long productionTime,
            long researchMETime, long researchPETime, long inventionTime, long copyTime,
            int wasteFactor, int batchSize, int maxRuns, double basePrice) {
        super(typeID, typeName, metaGroupID, categoryID);
        this.productTypeID = productTypeID;
        this.icon = icon;
        this.techLevel = techLevel;
        this.productionTime = productionTime;
        this.researchMETime = researchMETime;
        this.researchPETime = researchPETime;
        this.inventionTime = inventionTime;
        this.copyTime = copyTime;
        this.wasteFactor = wasteFactor;
        this.batchSize = batchSize;
        this.maxRuns = maxRuns;
        this.basePrice = basePrice;
    }

    // ////////////
    // accessors //
    // ////////////
    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

    public long getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(long productionTime) {
        this.productionTime = productionTime;
    }

    public long getResearchMETime() {
        return researchMETime;
    }

    public void setResearchMETime(long researchMETime) {
        this.researchMETime = researchMETime;
    }

    public long getResearchPETime() {
        return researchPETime;
    }

    public void setResearchPETime(long researchPETime) {
        this.researchPETime = researchPETime;
    }

    public long getInventionTime() {
        return inventionTime;
    }

    public void setInventionTime(long inventionTime) {
        this.inventionTime = inventionTime;
    }

    public long getCopyTime() {
        return copyTime;
    }

    public void setCopyTime(long copyTime) {
        this.copyTime = copyTime;
    }

    public int getWasteFactor() {
        return wasteFactor;
    }

    public void setWasteFactor(int wasteFactor) {
        this.wasteFactor = wasteFactor;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getMaxRuns() {
        return maxRuns;
    }

    public void setMaxRuns(int maxRuns) {
        this.maxRuns = maxRuns;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

}
