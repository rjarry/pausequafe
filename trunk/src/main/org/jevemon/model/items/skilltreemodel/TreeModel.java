package org.jevemon.model.items.skilltreemodel;

import java.util.LinkedList;
import java.util.List;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.Formater;
import org.jevemon.model.character.sheet.CharacterSheet;
import org.jevemon.model.character.sheet.CharacterSkill;
import org.jevemon.model.items.Item;
import org.jevemon.model.items.ItemGroup;
import org.jevemon.model.items.PreRequisite;
import org.jevemon.model.items.skillmap.Skill;
import org.jevemon.model.items.skillmap.SkillMap;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QTreeModel;

public class TreeModel extends QTreeModel {

	////////////////////
	// private fields //
	////////////////////
	private List<TreeModel> children = null;
	private ItemGroup group = null;
	private Item item = null;
	private CharacterSkill charSkill = null;
	private CharacterSheet sheet = null;
	
	//////////////////
	// constructors //
	//////////////////
	public TreeModel(){
	}
	
	public TreeModel(QObject parent){
		super(parent);
	}
	
	public TreeModel(QObject parent, CharacterSheet sheet) throws JEVEMonException{
		super(parent);
		children = new LinkedList<TreeModel>();
		this.sheet = sheet;
		
		for(String groupName : Constants.SKILL_GROUP_NAMES){ // adding groups to the first level
			children.add(new TreeModel(new ItemGroup(groupName), null, null, sheet));
		}
		for(TreeModel treeGroup : this.children){
			treeGroup.children = new LinkedList<TreeModel>();
			for(Item item : SkillMap.getInstance().getGroup(treeGroup.group.getGroupName())){
				if (sheet != null && sheet.getSkills().containsKey(item.getTypeID())){
					treeGroup.children.add(new TreeModel(null, item, 
										sheet.getSkill(item.getTypeID()), sheet));
				} else {
					treeGroup.children.add(new TreeModel(null, item, null, sheet));
				}
			}
		}
	}
	
	private TreeModel(ItemGroup group, Item item, CharacterSkill charSkill, CharacterSheet sheet) {
		super();
		this.group = group;
		this.item = item;
		this.charSkill = charSkill;
		this.sheet = sheet;
	}

	/////////////
	// getters //
	/////////////
	public ItemGroup getGroup() {
		return group;
	}
	public Item getSkill() {
		return item;
	}
	public CharacterSkill getCharSkill() {
		return charSkill;
	}
	/////////////
	// setters //
	/////////////	
	public void setSkill(Skill skill) {
		this.item = skill;
	}
	public void setGroup(ItemGroup group) {
		this.group = group;
	}
	public void setCharSkill(CharacterSkill charSkill) {
		this.charSkill = charSkill;
	}
	
	///////////////////////
	// inherited methods //
	///////////////////////
	@Override
	public Object child(Object parent, int index) {
		if (parent == null) { // root
			return children.get(index);
		} else {
			if (((TreeModel) parent).children != null ) { // parent is a group
				return ((TreeModel) parent).children.get(index);
			} else { // the node is a skill and has no children
				return null;
			} 
		}
	}
	
	@Override
	public int childCount(Object parent) {
		if (parent == null){
			return children.size();
		} else {
			if (((TreeModel) parent).children != null){
				return ((TreeModel) parent).children.size();
			} else {
				return 0;
			} 
		}
	}
	@Override
	public String text(Object item) {
//		if (item == null) { // root
//			return null;
//		} else {
//			if (((TreeModel) item).children != null) { // parent is a group
//				return ((TreeModel) item).group.getGroupName();
//			} else { // the node is a skill
//				return ((TreeModel) item).skill.getTypeName();
//			} 
//		}
		return null;
	}
	
	@Override
	/**
	 * General method for nodes display.
	 */
	public Object data(Object value, int role){
		switch(role){
			// text to display
			case ItemDataRole.DisplayRole    : return displayRole(value);
			// display font
			case ItemDataRole.FontRole       : return fontRole(value);
			// the icon before the skills
			case ItemDataRole.DecorationRole : return decorationRole(value);
			// tool tips on the skills
			case ItemDataRole.ToolTipRole    : return toolTipRole(value);
			
			default : return null;
		}
	}
	
	
	
	
	/////////////////////////////////
	// private convenience methods //
	/////////////////////////////////
	
	// is the skill trainable given the character's skills.
	private boolean skillIsTrainable(Item item){
		if (item == null || sheet == null){
			return false;
		}
		
		boolean trainable = false;
		
		for(PreRequisite preReq : item.getPreReqs()){
			trainable = sheet.getSkills().containsKey(preReq.getTypeID());
			if (trainable) {
				trainable = sheet.getSkill(preReq.getTypeID()).getLevel() 
													>= preReq.getRequiredLevel();
			} else {
				break;
			}
		}
		
		return trainable;	
	}
	
	// group total sp
	private int getTotalGroupSP(){
		int groupSP = 0;
		if (children != null){		
			for(TreeModel skill : children){
				if (skill.charSkill != null){
					groupSP += skill.charSkill.getSkillPoints();
				}
			}
		}
		return groupSP;
	}
	
	// the icon before the skills
	private QIcon decorationRole(Object value){
		QIcon icon = null;
		
		if (value == null) { // root
			return null;
		} else {
			if (((TreeModel) value).children != null) {
				return null;
			} else { // the node is a skill
				if (((TreeModel) value).charSkill != null){
					int level = ((TreeModel) value).charSkill.getLevel();
					switch(level){
						case 1 : icon = new QIcon(Constants.SKILL_LEVEL_1); break;
						case 2 : icon = new QIcon(Constants.SKILL_LEVEL_2); break;
						case 3 : icon = new QIcon(Constants.SKILL_LEVEL_3); break;
						case 4 : icon = new QIcon(Constants.SKILL_LEVEL_4); break;
						case 5 : icon = new QIcon(Constants.SKILL_LEVEL_5); break;
						default : icon = new QIcon(Constants.SKILL_LEVEL_0);
					}
				} else {
					if (skillIsTrainable(((TreeModel) value).item)){
						icon = new QIcon(Constants.SKILL_TRAINABLE);
					} else {
						icon = new QIcon(Constants.SKILL_NOT_TRAINABLE);
					}
				}
			}
		}
		
		return icon;
	}
	
	// tool tips on the skills
	private String toolTipRole(Object value){
		if (value == null) { // root
			return null;
		} else {
			if (((TreeModel) value).children != null) {
				return Formater.printLong(((TreeModel) value).getTotalGroupSP()) + " points";
			} else { // the node is a skill
				String description = ((TreeModel) value).item.getDescription();
				if (description.length() < 80){
					return description;
				} else { // we just keep the begining of the description
					return description.substring(0, 80) + "...";
				}
			} 
		}
	}
	
	// text to display
	private String displayRole(Object value){
		if (value == null) { // root
			return null;
		} else {
			if (((TreeModel) value).children != null) {
				return ((TreeModel) value).group.getGroupName();
			} else { // the node is a skill
				return ((TreeModel) value).item.getTypeName() 
						+ " (x" + ((Skill) ((TreeModel) value).item).getRank() + ")";
			} 
		}
	}

	// display font
	private QFont fontRole(Object value){
		if (value == null) { // root
			return null;
		} else {
			if (((TreeModel) value).children != null) {
				QFont font = new QFont();
				font.setBold(true);
				return font;
			} else { // the node is a skill
				return new QFont();
			} 
		}
	}
	
	
}
