package org.jevemon.model.items.skillmap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.items.PreRequisiteTree;

public class SkillMapParser {

	@SuppressWarnings("unchecked")
	public static HashMap<Integer, Skill> parse(String fileName) throws JEVEMonException{

		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(fileName);
		} catch (JDOMException e) {
			throw new JEVEMonException("skill tree parse error");
		} catch (IOException e) {
			throw new JEVEMonException("file not found");
		}
		
		Element root = doc.getRootElement();
		
		if(!root.getAttributeValue("version").equals("2")){
			throw new JEVEMonException("wrong API version");
		}
		// puts the group elements in a list
		List<Element> groupsList  = root.getChild("result").getChild("rowset").getChildren();

		HashMap<Integer, Skill> map = new HashMap<Integer, Skill>();
		
		for(Element group : groupsList){
			// parses the XML file and builds a skills map with it
			parseGroup(group, map);
		}

		return map;

	}
	
	@SuppressWarnings("unchecked")
	private static void parseGroup(Element groupElement, HashMap<Integer, Skill> map){
		String groupName = groupElement.getAttributeValue("groupName");
		
		List<Element> skillsList = groupElement.getChild("rowset").getChildren();
		for(Element skillElement : skillsList){
			Skill skill = parseSkill(skillElement, groupName);
			map.put(Integer.valueOf(skill.getTypeID()), skill);
		}
	}

	@SuppressWarnings("unchecked")
	private static Skill parseSkill(Element skillElement, String groupName){
		Skill skill = new Skill();
		skill.setTypeName(skillElement.getAttributeValue("typeName"));
		skill.setGroupID(Integer.parseInt(skillElement.getAttributeValue("groupID")));
		skill.setTypeID(Integer.parseInt(skillElement.getAttributeValue("typeID")));
		skill.setDescription(skillElement.getChild("description").getValue());
		skill.setRank(Integer.parseInt(skillElement.getChild("rank").getValue()));
		skill.setGroupName(groupName);
		
		List<Element> preReqList = skillElement.getChild("rowset").getChildren();
		for(Element preReqElement : preReqList){
			PreRequisiteTree preReq = new PreRequisiteTree();
			preReq.setTypeID(Integer.parseInt(preReqElement.getAttributeValue("typeID")));
			preReq.setRequiredLevel(Integer.parseInt(preReqElement.getAttributeValue("skillLevel")));
			skill.addPreRequisite(preReq);
		}

		Element attributes = skillElement.getChild("requiredAttributes");
		skill.setPrimaryAttribute(attributes.getChild("primaryAttribute").getValue());
		skill.setSecondaryAttribute(attributes.getChild("secondaryAttribute").getValue());

		return skill;
	}
}
