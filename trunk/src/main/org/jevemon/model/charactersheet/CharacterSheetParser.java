package org.jevemon.model.charactersheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.jdom.Document;
import org.jdom.Element;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.Constants;

public class CharacterSheetParser {

	public static CharacterSheet parse(Document doc, boolean isCached) throws JEVEMonException {
		
		Element root = doc.getRootElement();
		
		if (!root.getAttributeValue("version").equals(Constants.API_VERSION)){
			throw new JEVEMonException("wrong API version");
		}
		
		CharacterSheet sheet = new CharacterSheet();
		
		// if the sheet was retrieved from a cached file
		sheet.setCached(isCached);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Date cachedDate = null;
		try {
			cachedDate = dateFormat.parse(root.getChild("currentTime").getValue());
		} catch (ParseException e) {
			// if there's an error during parsing the cached date we take the current system time
			cachedDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).getTime();
		}
		sheet.setCachedDate(cachedDate);
		
		root = root.getChild("result");
		
		sheet.setCharacterID(Integer.parseInt(root.getChild("characterID").getValue()));
		sheet.setName(root.getChild("name").getValue());
		sheet.setRace(root.getChild("race").getValue());
		sheet.setBloodLine(root.getChild("bloodLine").getValue());
		sheet.setGender(root.getChild("gender").getValue());
		sheet.setCorporationName(root.getChild("corporationName").getValue());
		sheet.setCorporationID(Integer.parseInt(root.getChild("corporationID").getValue()));
		sheet.setCloneName(root.getChild("cloneName").getValue());
		sheet.setCloneSkillPoints(Long.parseLong(root.getChild("cloneSkillPoints").getValue()));
		sheet.setBalance(Double.parseDouble(root.getChild("balance").getValue()));
		
		List<Element> enhancers  = root.getChild("attributeEnhancers").getChildren();
		
		for (Element row : enhancers) {
			String enhancerName = row.getName();
			AttributeEnhancer enhancer = new AttributeEnhancer();
			if (enhancerName.equals("willpowerBonus")){
				enhancer.setAttribute("willpower");
				enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
				enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue").getValue()));
			}
			if (enhancerName.equals("memoryBonus")){
				enhancer.setAttribute("memory");
				enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
				enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue").getValue()));
			}
			if (enhancerName.equals("perceptionBonus")){
				enhancer.setAttribute("perception");
				enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
				enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue").getValue()));
			}
			if (enhancerName.equals("intelligenceBonus")){
				enhancer.setAttribute("intelligence");
				enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
				enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue").getValue()));
			}
			if (enhancerName.equals("charismaBonus")){
				enhancer.setAttribute("charisma");
				enhancer.setAugmentatorName(row.getChild("augmentatorName").getValue());
				enhancer.setAugmentatorValue(Integer.parseInt(row.getChild("augmentatorValue").getValue()));
			}
			sheet.addAttributeEnhancer(enhancer);
		}
		
		sheet.setIntelligence(Integer.parseInt(root.getChild("attributes").getChild("intelligence").getValue()));
		sheet.setMemory(Integer.parseInt(root.getChild("attributes").getChild("memory").getValue()));
		sheet.setPerception(Integer.parseInt(root.getChild("attributes").getChild("perception").getValue()));
		sheet.setCharisma(Integer.parseInt(root.getChild("attributes").getChild("charisma").getValue()));
		sheet.setWillpower(Integer.parseInt(root.getChild("attributes").getChild("willpower").getValue()));
		
		List<Element> skills = null;
		// getting the "right" rowset
		if (root.getChild("rowset").getAttributeValue("name").equals("skills")){
			skills = root.getChild("rowset").getChildren();
		} else {
			throw new JEVEMonException("skill list parsing error");
		}
		

		// adding skills
		for(Element row : skills){
			CharacterSkill s = new CharacterSkill();
			s.setTypeID(Integer.parseInt(row.getAttributeValue("typeID")));
			s.setSkillPoints(Integer.parseInt(row.getAttributeValue("skillpoints")));
			s.setLevel(Integer.parseInt(row.getAttributeValue("level")));
			sheet.addSkill(s);
		}
		// done parsing
		return sheet;
	}

}
