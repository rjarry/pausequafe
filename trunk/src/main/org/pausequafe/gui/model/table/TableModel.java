package org.pausequafe.gui.model.table;

import org.pausequafe.data.business.Item;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QAbstractTableModel;

public class TableModel extends QAbstractTableModel {

	Item item;
	
	public TableModel(Item item) {
		this(null,item);
	}

	public TableModel(QObject parent, Item item) {
		super(parent);
		this.item = item;
	}


	@Override
	public int columnCount(QModelIndex parent) {
		return 3;
	}

	@Override
	public Object data(QModelIndex index, int role) {
		Object result = null;
		
		
		switch(role){
			// text to display
			case ItemDataRole.DisplayRole    : 
				switch(index.column()){
					case 0 : result = item.getAttributeList().get(index.row()).getAttributeName();break;
					case 1 : result = item.getAttributeList().get(index.row()).getValue();break;
					case 2 : result = item.getAttributeList().get(index.row()).getUnit();break;
				} break;
					
//			// display font
//			case ItemDataRole.FontRole       : result =  elt.getFont(); break;
//			// the icon before the skills
//			case ItemDataRole.DecorationRole : result =  elt.getIcon(); break;
//			// tool tips on the skills
//			case ItemDataRole.ToolTipRole    : result =  elt.getTooltip(); break;

		default : result = null;
		}
		return result;
	}

	@Override
	public int rowCount(QModelIndex parent) {
		return item.getAttributeList().size();
	}

}
