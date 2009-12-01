package org.pausequafe.gui.model.browsers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.data.business.ItemDetailed;
import org.pausequafe.data.dao.ItemDAO;
import org.pausequafe.data.dao.MarketGroupDAO;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.gui.QAbstractTableModel;

public class AttributesTableModel extends QAbstractTableModel {

	List<ItemAttribute> displayList = new ArrayList<ItemAttribute>();
	
	public AttributesTableModel(ItemDetailed item) {
		this(null,item);
	}

	public AttributesTableModel(QObject parent, ItemDetailed item) {
		super(parent);
		for(ItemAttribute att : item.getAttributeList()){
			if(att.getValue() != 0){
				displayList.add(att);
			}
		}
	}

	@Override
	public int columnCount(QModelIndex parent) {
		return 2;
	}

	@Override
	public Object data(QModelIndex index, int role) {
		Object result = null;
		
		
		switch(role){
			// text to display
			case ItemDataRole.DisplayRole    : 
				switch(index.column()){
					case 0 : 
						result = displayList.get(index.row()).getAttributeName();
						break;
					case 1 : 
						result = displayUnit(displayList.get(index.row()).getAttributeID(),
											 displayList.get(index.row()).getValue(),
											 displayList.get(index.row()).getUnit(),
											 displayList.get(index.row()).getUnitID());
				} 
				break;
				
		default : result = null;
		}
		return result;
	}

	@Override
	public int rowCount(QModelIndex parent) {
		return displayList.size();
	}
	
	
	
	
	private String displayUnit(int attributeId, double value, String unitName, int unitID){
		if(attributeId == SQLConstants.MAINCOLOR_ATTID){
			return "#" + Integer.toHexString((int) value).toUpperCase();
		}
		DecimalFormat format = new DecimalFormat("#.######");
		double displayValue = 0;
		switch(unitID){
		case SQLConstants.DAMAGE_RESISTANCE_UNITID : 
			displayValue = (1.0 - value) * 100.0;
			break;
		case SQLConstants.PERCENT_MULTIPLIER_UNITID : 
			displayValue = (value - 1.0) * 100.0;
			break;
		case SQLConstants.DURATION_MILLISECS_UNITID : 
			displayValue = value / 1000.0;
			unitName = "sec"; 
			break;
		case SQLConstants.SIZECLASS_UNITID :
			int size = (int) value;
			switch(size){
				case 1 : return "S";
				case 2 : return "M";
				case 3 : return "L";
				case 4 : return "XL";
			}
			break;
		case SQLConstants.ATTRIBUTEID_UNITID :
			switch ((int) value) {
			case 164 : return "Charisma";
			case 165 : return "Intelligence";
			case 166 : return "Memory";
			case 167 : return "Perception";
			case 168 : return "Willpower";
			}
		case SQLConstants.GROUPID_UNITID :
			try {
				return MarketGroupDAO.getInstance().getGroupName((int) value);
			} catch (Exception e) {
				e.printStackTrace();
			}		
			break;
		case SQLConstants.TYPEID_UNITID :
			try {
				return ItemDAO.getInstance().getItemName((int) value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default :
			return value + " " + unitName;
		}
		return format.format(displayValue) + " " + unitName;
	}

	@Override
	public Object headerData(int section, Orientation orientation, int role) {
		if(role == Qt.ItemDataRole.DisplayRole){
			switch(section){
			case 0 : return "Attribute"; 
			case 1 : return "Value";
			default : return null;
			}
		} else {
			return null;
		}
	}

	

}
