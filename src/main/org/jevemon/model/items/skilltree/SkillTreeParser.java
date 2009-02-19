package org.jevemon.model.items.skilltree;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.model.items.ItemGroup;
import org.jevemon.model.items.PreRequisite;

public class SkillTreeParser {

	@SuppressWarnings("unchecked")
	public static TreeMap<Integer, ItemGroup> parse(String fileName) throws JEVEMonException{

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

		TreeMap<Integer, ItemGroup> tree = new TreeMap<Integer, ItemGroup>();
		
		for(Element group : groupsList){
			// parses a group into an ItemGroup and adds it to the skill tree
			ItemGroup grp = parseGroup(group);
			tree.put(Integer.valueOf(grp.getGroupID()), grp);
		}

		return tree;

	}
	
	@SuppressWarnings("unchecked")
	private static ItemGroup parseGroup(Element groupElement){
		ItemGroup group = new ItemGroup();
		group.setGroupID(Integer.valueOf(groupElement.getAttributeValue("groupID")));
		group.setGroupName(groupElement.getAttributeValue("groupName"));
		
		List<Element> skillsList = groupElement.getChild("rowset").getChildren();
		for(Element skillElement : skillsList){
			group.addItem(parseSkill(skillElement, group.getGroupName()));
		}
		return group;	
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
			PreRequisite preReq = new PreRequisite();
			preReq.setTypeID(Integer.parseInt(preReqElement.getAttributeValue("typeID")));
			preReq.setLevel(Integer.parseInt(preReqElement.getAttributeValue("skillLevel")));
			skill.addPreRequisite(preReq);
		}

		Element attributes = skillElement.getChild("requiredAttributes");
		skill.setPrimaryAttribute(attributes.getChild("primaryAttribute").getValue());
		skill.setSecondaryAttribute(attributes.getChild("secondaryAttribute").getValue());

		return skill;
	}
}
