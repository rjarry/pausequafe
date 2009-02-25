package org.jevemon.model.items.skilltreemodel;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.misc.util.Formater;
import org.jevemon.model.charactersheet.CharacterSheet;
import org.jevemon.model.items.PreRequisite;
import org.jevemon.model.items.skillmap.Skill;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QTreeModel;

public class SkillTreeModel extends QTreeModel {

	private final LinkedList<SkillTreeGroup> groups = new LinkedList<SkillTreeGroup>();
	private CharacterSheet sheet = null;
	
	
	public SkillTreeModel() throws JEVEMonException {
		this(null,null);
	}
	
	public SkillTreeModel(CharacterSheet sheet) throws JEVEMonException {
		this(null,sheet);
	}
	
	public SkillTreeModel(QObject parent, CharacterSheet sheet) throws JEVEMonException {
		super(parent);
		buildTree(sheet);
	}

	private void buildTree(CharacterSheet sheet) throws JEVEMonException {
		this.sheet = sheet;
		for(String groupName : Constants.SKILL_GROUP_NAMES){
			groups.add(new SkillTreeGroup(groupName, sheet));
		}
	}

	///////////////////////
	// inherited methods //
	///////////////////////
	@Override
	public Object child(Object parent, int index) {
		if (parent == null) { // root
			return groups.get(index);
		} else {
			if (parent instanceof SkillTreeGroup) {
				return ((SkillTreeGroup) parent).getSkill(index);
			} else { // the node is a skill and has no children
				return null;
			} 
		}
	}
	@Override
	public int childCount(Object parent) {
		if (parent == null) { // root
			return groups.size();
		} else {
			if (parent instanceof SkillTreeGroup) {
				return ((SkillTreeGroup) parent).getSkillCount();
			} else { // the node is a skill
				return 0;
			} 
		}
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
			// text color
			case ItemDataRole.ForegroundRole : return QColor.black;
			// the icon before the skills
			case ItemDataRole.DecorationRole : return decorationRole(value);
			// tool tips on the skills
			case ItemDataRole.ToolTipRole    : return toolTipRole(value);
			
			default : return null;
		}
	}

	@Override
	public String text(Object value) {
		return null;
	}
	
	/////////////////////
	// private methods //
	/////////////////////
	
	// is the skill trainable given the character's skills.
	private boolean skillIsTrainable(Skill skill){
		if (skill == null || sheet == null){
			return false;
		}
		
		boolean trainable = false;
		
		for(PreRequisite preReq : skill.getPreReqs()){
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
	
	// the icon before the skills
	private QIcon decorationRole(Object value){
		QIcon icon = null;
		if (value instanceof SkillTreeItem) {
			if (((SkillTreeItem) value).isKnown()){
				int level = ((SkillTreeItem) value).getCharSkill().getLevel();
				switch(level){
					case 1 : icon = new QIcon(Constants.SKILL_LEVEL_1); break;
					case 2 : icon = new QIcon(Constants.SKILL_LEVEL_2); break;
					case 3 : icon = new QIcon(Constants.SKILL_LEVEL_3); break;
					case 4 : icon = new QIcon(Constants.SKILL_LEVEL_4); break;
					case 5 : icon = new QIcon(Constants.SKILL_LEVEL_5); break;
				default : icon = new QIcon(Constants.SKILL_LEVEL_0);
				}
			} else {
				if (skillIsTrainable(((SkillTreeItem) value).getSkill())){
					icon = new QIcon(Constants.SKILL_TRAINABLE);
				} else {
					icon = new QIcon(Constants.SKILL_NOT_TRAINABLE);
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
			if (value instanceof SkillTreeGroup) {
				return null;
			} else { // the node is a skill
				String description = ((SkillTreeItem) value).getSkill().getDescription();
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
			if (value instanceof SkillTreeGroup) {
				return ((SkillTreeGroup) value).getGroupName() + " (" 
						+ Formater.printLong(((SkillTreeGroup) value).getTotalGroupSP())
																					+ " SP)";
			} else { // the node is a skill
				return ((SkillTreeItem) value).getSkill().getTypeName() 
						+ " (x" + ((SkillTreeItem) value).getSkill().getRank() + ")";
			} 
		}
	}
	
	// display font
	private QFont fontRole(Object value){
		if (value == null) { // root
			return null;
		} else {
			if (value instanceof SkillTreeGroup) {
				QFont font = new QFont();
				font.setBold(true);
				return font;
			} else { // the node is a skill
				return new QFont();
			} 
		}
	}


}
