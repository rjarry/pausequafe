
package org.jevemon.model.charactersheet;

import java.util.Date;
import java.util.HashMap;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;

public class CharacterSheet {
	
	///////////////
	// constants //
	///////////////
	
	private int characterID;
	private String name;
	private String race;
	private String bloodLine;
	private String gender;
	private String corporationName;
	private int corporationID;
	private String cloneName;
	private long cloneSkillPoints;
	private double balance;
	
	private final HashMap<String, AttributeEnhancer> attributeEnhancers = new HashMap<String, AttributeEnhancer>();
	
	private int intelligence;
	private int memory;
	private int charisma;
	private int perception;
	private int willpower;
	
	private final HashMap<Integer, CharacterSkill> skills = new HashMap<Integer, CharacterSkill>();

	private Date cachedAt;
	private boolean cached = false;

	//////////////////
	// constructors //
	//////////////////
	public CharacterSheet() {
	}

	/////////////
	// getters //
	/////////////
	public int getCharacterID() {
		return characterID;
	}

	public String getName() {
		return name;
	}

	public String getRace() {
		return race;
	}

	public String getBloodLine() {
		return bloodLine;
	}

	public String getGender() {
		return gender;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public int getCorporationID() {
		return corporationID;
	}

	public String getCloneName() {
		return cloneName;
	}
	
	public long getCloneSkillPoints() {
		return cloneSkillPoints;
	}
	
	public double getBalance() {
		return balance;
	}

	public HashMap<String, AttributeEnhancer> getAttributeEnhancers() {
		return attributeEnhancers;
	}
	
	public AttributeEnhancer getAttributeEnhancer(String attribute){
		return attributeEnhancers.get(attribute);
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getMemory() {
		return memory;
	}

	public int getCharisma() {
		return charisma;
	}

	public int getPerception() {
		return perception;
	}

	public int getWillpower() {
		return willpower;
	}
	
	public HashMap<Integer, CharacterSkill> getSkills() {
		return skills;
	}
	
	public CharacterSkill getSkill(int typeID){
		return skills.get(Integer.valueOf(typeID));
	}

	public boolean isCached() {
		return cached;
	}
	
	public Date getCachedDate(){
		return cachedAt;
	}
	
	/////////////
	// setters //
	/////////////	
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public void setBloodLine(String bloodLine) {
		this.bloodLine = bloodLine;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public void setCorporationID(int corporationID) {
		this.corporationID = corporationID;
	}

	public void setCloneName(String cloneName) {
		this.cloneName = cloneName;
	}
	
	public void setCloneSkillPoints(long cloneSkillPoints) {
		this.cloneSkillPoints = cloneSkillPoints;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public void setPerception(int perception) {
		this.perception = perception;
	}

	public void setWillpower(int willpower) {
		this.willpower = willpower;
	}
	
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
	public void setCachedDate(Date date){
		this.cachedAt = date;
	}
	
	////////////////////
	// public methods //
	////////////////////
	
	/**
	 * Adds an attribute enhancer to the character
	 * @param e the implant to be added
	 * @throws JEVEMonException if the slot is already in use, to inform it has been overwritten.
	 */
	public void addAttributeEnhancer(AttributeEnhancer e) throws JEVEMonException {
		if (attributeEnhancers.containsKey(e.getAttribute())){
			attributeEnhancers.put(e.getAttribute(), e);
			throw new JEVEMonException("enhancer overwritten "+e.getAttribute());
		} else {
			attributeEnhancers.put(e.getAttribute(), e);
		}
	}
	
	/**
	 * Adds a CharacterSkill to the character's skill list.
	 * @param skill the skill to be added
	 * @throws JEVEMonException  if the skill was already there, to inform it has been overwritten.
	 */
	public void addSkill(CharacterSkill skill) throws JEVEMonException {
		Integer typeID = Integer.valueOf(skill.getTypeID());
		if (skills.containsKey(typeID)){
			skills.put(typeID, skill);
			throw new JEVEMonException("skill overwritten");
		} else {
			skills.put(typeID, skill);
		}
	}
	/**
	 * Calculates the effective attribute value which will be used for skill training speed.
	 * 
	 * @param attribute the attribute
	 * @return the effective attribute's value
	 */
	public double getEffectiveAttributeValue(String attribute){
				
		double learningBonus;
		if (skills.containsKey(Constants.LEARNING)){
			learningBonus = 1 + skills.get(Constants.LEARNING).getLevel() * 0.02;
		} else {
			learningBonus = 1;
		}
		
		int implantBonus;
		if (attributeEnhancers.containsKey(attribute)){
			implantBonus = attributeEnhancers.get(attribute).getAugmentatorValue();
		} else {
			implantBonus = 0;
		}
		
		int baseValue = 0;
		int skillsBonus = 0;
		if (attribute.equals("intelligence")){
			baseValue = intelligence;
			if (skills.containsKey(Constants.ANALYTICAL_MIND)){
				skillsBonus += skills.get(Constants.ANALYTICAL_MIND).getLevel();
			}
			if (skills.containsKey(Constants.LOGIC)){
				skillsBonus += skills.get(Constants.LOGIC).getLevel();
			}
		}
		if (attribute.equals("memory")){
			baseValue = memory;
			if (skills.containsKey(Constants.INSTANT_RECALL)){
				skillsBonus += skills.get(Constants.INSTANT_RECALL).getLevel();
			}
			if (skills.containsKey(Constants.EDEITIC_MEMORY)){
				skillsBonus += skills.get(Constants.EDEITIC_MEMORY).getLevel();
			}		
		}
		if (attribute.equals("willpower")){
			baseValue = willpower;
			if (skills.containsKey(Constants.IRON_WILL)){
				skillsBonus += skills.get(Constants.IRON_WILL).getLevel();
			}
			if (skills.containsKey(Constants.FOCUS)){
				skillsBonus += skills.get(Constants.FOCUS).getLevel();
			}
		}
		if (attribute.equals("charisma")){
			baseValue = charisma;
			if (skills.containsKey(Constants.EMPATHY)){
				skillsBonus += skills.get(Constants.EMPATHY).getLevel();
			}
			if (skills.containsKey(Constants.PRESENCE)){
				skillsBonus += skills.get(Constants.PRESENCE).getLevel();
			}
		}
		if (attribute.equals("perception")){
			baseValue = perception;
			if (skills.containsKey(Constants.SPATIAL_AWARENESS)){
				skillsBonus += skills.get(Constants.SPATIAL_AWARENESS).getLevel();
			}
			if (skills.containsKey(Constants.CLARITY)){
				skillsBonus += skills.get(Constants.CLARITY).getLevel();
			}
		}
		return (baseValue + skillsBonus + implantBonus) * learningBonus;
	}
	
	public double getTrainingSpeed(String attribute1, String attribute2){
		double att1 = getEffectiveAttributeValue(attribute1);
		double att2 = getEffectiveAttributeValue(attribute2);

		return (att1 + (att2 / 2.0)) / (60.0 * 1000.0);
	}
	
	
	
	public long getSkillPoints(){
		long skillPoints = 0;
		for(CharacterSkill skill : skills.values()){
			skillPoints += skill.getSkillPoints();
		}
		return skillPoints;
	}
	
	public int getSkillCount(){
		return skills.size();
	}
	
	public int getLevel5Skills(){
		int level5Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 5){
				level5Skills++;
			}
		}
		return level5Skills;
	}

}
