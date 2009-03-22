
package org.pausequafe.data.business;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;
/**
 * Represents the API response for a character.
 * <br><br>It contains all the information regarding the character :
 * <ul><li>Name, Race, Corporation name, etc.
 * <li>Account balance
 * <li>Current backup clone info
 * <li>Base attributes
 * <li>Attribute enhancers (implants)
 * <li>Skills known
 * <li>Corporation titles
 * </ul>
 * @author diabeteman
 *
 */
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
	private final LinkedList<String> corporationTitles = new LinkedList<String>();
	
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
	
	////////////////////
	// public methods //
	////////////////////
	
	/**
	 * Adds an attribute enhancer to the character
	 * @param e 
	 * 			the implant to be added
	 * @throws PQException 
	 * 				if the implant slot is already in use, to inform it has been overwritten.
	 */
	public void addAttributeEnhancer(AttributeEnhancer e) throws PQException {
		if (attributeEnhancers.containsKey(e.getAttribute())){
			attributeEnhancers.put(e.getAttribute(), e);
			throw new PQException("enhancer overwritten "+e.getAttribute());
		} else {
			attributeEnhancers.put(e.getAttribute(), e);
		}
	}
	
	/**
	 * Adds a CharacterSkill to the character's skill list.
	 * @param skill 
	 * 			the skill to be added
	 * @throws PQException  
	 * 				if the skill was already there, to inform it has been overwritten.
	 */
	public void addSkill(CharacterSkill skill) throws PQException {
		Integer typeID = Integer.valueOf(skill.getTypeID());
		if (skills.containsKey(typeID)){
			skills.put(typeID, skill);
			throw new PQException("skill overwritten");
		} else {
			skills.put(typeID, skill);
		}
	}
	/**
	 * Adds a new corporation title to the character.
	 * 
	 * @param title
	 * 			the title to be added
	 */
	public void addTitle(String title){
		corporationTitles.add(title);
	}
	/**
	 * Calculates the effective attribute value which will be used for skill training speed.<br><br>
	 * Taking in consideration :<br>
	 * <ul><li>"Learning" skills
	 * <li>Implants bonuses
	 * </ul>
	 * 
	 * @param attribute 
	 * 				the attribute
	 * @return 
	 * 		the effective attribute's value
	 */
	public double getEffectiveAttributeValue(String attribute){
			
		int baseValue = 0;
		if (attribute.equals("intelligence")){
			baseValue = intelligence;
		}
		if (attribute.equals("memory")){
			baseValue = memory;
		}
		if (attribute.equals("willpower")){
			baseValue = willpower;
		}
		if (attribute.equals("charisma")){
			baseValue = charisma;
		}
		if (attribute.equals("perception")){
			baseValue = perception;
		}
		
		int skillsBonus = getSkillsBonus(attribute);
		
		int implantBonus;
		if (attributeEnhancers.containsKey(attribute)){
			implantBonus = attributeEnhancers.get(attribute).getAugmentatorValue();
		} else {
			implantBonus = 0;
		}
		
		double learningBonus = getLearningBonus();
				
		return (baseValue + skillsBonus + implantBonus) * learningBonus;
	}
	
	public int getSkillsBonus(String attribute){
		int skillsBonus = 0;
		
		if (attribute.equals("intelligence")){

			if (skills.containsKey(Constants.ANALYTICAL_MIND)){
				skillsBonus += skills.get(Constants.ANALYTICAL_MIND).getLevel();
			}
			if (skills.containsKey(Constants.LOGIC)){
				skillsBonus += skills.get(Constants.LOGIC).getLevel();
			}
		}
		if (attribute.equals("memory")){
			if (skills.containsKey(Constants.INSTANT_RECALL)){
				skillsBonus += skills.get(Constants.INSTANT_RECALL).getLevel();
			}
			if (skills.containsKey(Constants.EDEITIC_MEMORY)){
				skillsBonus += skills.get(Constants.EDEITIC_MEMORY).getLevel();
			}		
		}
		if (attribute.equals("willpower")){
			if (skills.containsKey(Constants.IRON_WILL)){
				skillsBonus += skills.get(Constants.IRON_WILL).getLevel();
			}
			if (skills.containsKey(Constants.FOCUS)){
				skillsBonus += skills.get(Constants.FOCUS).getLevel();
			}
		}
		if (attribute.equals("charisma")){
			if (skills.containsKey(Constants.EMPATHY)){
				skillsBonus += skills.get(Constants.EMPATHY).getLevel();
			}
			if (skills.containsKey(Constants.PRESENCE)){
				skillsBonus += skills.get(Constants.PRESENCE).getLevel();
			}
		}
		if (attribute.equals("perception")){
			if (skills.containsKey(Constants.SPATIAL_AWARENESS)){
				skillsBonus += skills.get(Constants.SPATIAL_AWARENESS).getLevel();
			}
			if (skills.containsKey(Constants.CLARITY)){
				skillsBonus += skills.get(Constants.CLARITY).getLevel();
			}
		}
		
		return skillsBonus;
	}
	
	public double getLearningBonus(){
		if (skills.containsKey(Constants.LEARNING)){
			return 1 + skills.get(Constants.LEARNING).getLevel() * 0.02;
		} else {
			return 1;
		}
	}
	
	
	
	
	
	
	/**
	 * Calculates the training speed (in SP per millisecond) for the given attributes.
	 * 
	 * @param primaryAttribute
	 * 					counts for 2/3 of the training speed
	 * @param secondaryAttribute
	 * 					counts for 1/3 of the training speed
	 * @return
	 * 		the training speed
	 */
	public double getTrainingSpeed(String primaryAttribute, String secondaryAttribute){
		double att1 = getEffectiveAttributeValue(primaryAttribute);
		double att2 = getEffectiveAttributeValue(secondaryAttribute);
		
		return (att1 + (att2 / 2.0)) / (60.0 * 1000.0);
	}
	
	/**
	 * Calculates the training speed (in SP per millisecond) for the given skill.
	 * 
	 * @param skill the given skill
	 * 			
	 * @return
	 * 		the training speed
	 */
	public double getTrainingSpeed(Skill skill){
		double att1 = getEffectiveAttributeValue(skill.getPrimaryAttribute());
		double att2 = getEffectiveAttributeValue(skill.getSecondaryAttribute());
		
		return (att1 + (att2 / 2.0)) / (60.0 * 1000.0);
	}
	
	/**
	 * Calculates the total number of skillpoints of the character (at the time of file caching).
	 * @return
	 * 		total SP
	 */
	public long getSkillPoints(){
		long skillPoints = 0;
		for(CharacterSkill skill : skills.values()){
			skillPoints += skill.getSkillPoints();
		}
		return skillPoints;
	}
	/**
	 * Returns the number of skills known.
	 * @return
	 * 		skills count
	 */
	public int getSkillCount(){
		return skills.size();
	}
	/**
	 * Calculates the number of skills known to level 0.
	 * @return
	 * 		level 0 skills count
	 */
	public int getLevel0Skills(){
		int level0Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 0){
				level0Skills++;
			}
		}
		return level0Skills;
	}
	/**
	 * Calculates the number of skills known to level 1.
	 * @return
	 * 		level 1 skills count
	 */
	public int getLevel1Skills(){
		int level1Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 1){
				level1Skills++;
			}
		}
		return level1Skills;
	}
	/**
	 * Calculates the number of skills known to level 2.
	 * @return
	 * 		level 2 skills count
	 */
	public int getLevel2Skills(){
		int level2Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 2){
				level2Skills++;
			}
		}
		return level2Skills;
	}
	/**
	 * Calculates the number of skills known to level 3.
	 * @return
	 * 		level 3 skills count
	 */
	public int getLevel3Skills(){
		int level3Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 3){
				level3Skills++;
			}
		}
		return level3Skills;
	}
	/**
	 * Calculates the number of skills known to level 4.
	 * @return
	 * 		level 4 skills count
	 */
	public int getLevel4Skills(){
		int level4Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 4){
				level4Skills++;
			}
		}
		return level4Skills;
	}
	/**
	 * Calculates the number of skills known to level 5.
	 * @return
	 * 		level 5 skills count
	 */
	public int getLevel5Skills(){
		int level5Skills = 0;
		for(CharacterSkill skill : skills.values()){
			if (skill.getLevel() == 5){
				level5Skills++;
			}
		}
		return level5Skills;
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

	public LinkedList<String> getCorporationTitles() {
		return corporationTitles;
	}

	public HashMap<String, AttributeEnhancer> getAttributeEnhancers() {
		return attributeEnhancers;
	}
	
	public AttributeEnhancer getAttributeEnhancer(String attribute){
		if (attributeEnhancers.containsKey(attribute)){
			return attributeEnhancers.get(attribute);
		} else {
			return new AttributeEnhancer();
		}
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
	

}
