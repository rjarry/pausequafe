package org.jevemon.model.skilltree;

import java.util.LinkedList;

import org.jevemon.model.items.skillmap.Skill;

public class SkillTreeItem {
	
	////////////////////
	// private fields //
	////////////////////
	private int row;
	private Skill skill;
	private SkillTreeItem parent = null;
	private final LinkedList<SkillTreeItem> children = new LinkedList<SkillTreeItem>();
	
	/////////////////
	// constructor //
	/////////////////
	public SkillTreeItem(int row, Skill skill){
		this.skill = skill;
		this.row = row;
	}
	
	////////////////////
	// public methods //
	////////////////////

	public int childCount(){
		return children.size();	
	}
	public int columnCount(){
		return 1;
	}
	public void appendChild(SkillTreeItem child){
		children.add(child);
	}
	
	
	/////////////
	// getters //
	/////////////	
	public int getRow(){
		return row;
	}
	public Skill getSkill(){
		return skill;
	}
	public SkillTreeItem getParent(){
		return parent;
	}
	public LinkedList<SkillTreeItem> getChildren(){
		return children;
	}
	public SkillTreeItem getChild(int row){
		return children.get(row);
	}
	
	
	/////////////
	// setters //
	/////////////
	public void setRow(int row){
		this.row = row;
	}
	public void setSkill(Skill skill){
		this.skill = skill;
	}
	public void setParent(SkillTreeItem parent){
		this.parent = parent;		
	}
}