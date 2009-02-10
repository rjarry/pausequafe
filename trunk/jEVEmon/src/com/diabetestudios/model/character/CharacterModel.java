package com.diabetestudios.jevemon.model.character;

import java.util.Date;
import java.util.HashSet;

import com.trolltech.qt.core.QAbstractItemModel;
import com.trolltech.qt.core.QModelIndex;

public class CharacterModel extends QAbstractItemModel{

	private int 	charID;
	private String 	name;
	private String 	bloodLine;
	private String	gender;
	private String 	corpName;
	private int		corpID;
	private double	balance;
	
	private HashSet<AttributeEnhancer> attributeEnhancers;
	
	//attributes
	private int intelligence;
	private int memory;
	private int charisma;
	private int perception;
	private int willpower;
	
	//skill list
	private SkillListModel skills;
	
	private Date cachedUntil;
	
	
	
	
	
	
	
	
	@Override
	public int columnCount(QModelIndex parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object data(QModelIndex index, int role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QModelIndex index(int row, int column, QModelIndex parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QModelIndex parent(QModelIndex child) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int rowCount(QModelIndex parent) {
		// TODO Auto-generated method stub
		return 0;
	}

}
