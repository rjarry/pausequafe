package org.jevemon.model.qtrelated.skilltree;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;
import org.jevemon.misc.util.Constants;
import org.jevemon.model.items.skillmap.Skill;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QTreeModel;

public class SkillTreeModel extends QTreeModel {

	private final LinkedList<SkillTreeGroup> groups = new LinkedList<SkillTreeGroup>();
	
	
	
	public SkillTreeModel(QObject parent) throws JEVEMonException {
		super(parent);
		buildTree();
	}

	private void buildTree() throws JEVEMonException {
		for(String groupName : Constants.SKILL_GROUP_NAMES){
			groups.add(new SkillTreeGroup(groupName));
		}
	}

	///////////////////////
	// inherited methods //
	///////////////////////
	@Override
	public Object child(Object parent, int index) {
		if (parent instanceof Skill){
			return null;
		} else {
			return ((SkillTreeGroup) parent).getSkill(index);
		}
	}
	@Override
	public int childCount(Object parent) {
		if (parent instanceof Skill){
			return 0;
		} else {
			return ((SkillTreeGroup) parent).getSkillCount();
		}
	}
	@Override
	public String text(Object value) {
		if (value instanceof Skill){
			return ((Skill) value).getTypeName() + " (" + ((Skill) value).getRank() + ")";
		} else {
			return value.toString();
		}
	}




}
