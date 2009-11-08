package org.pausequafe.gui.model.characters;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.data.dao.MonitoredCharacterDAO;
import org.pausequafe.data.dao.SkillPlanDAO;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt.ItemDataRole;
import com.trolltech.qt.gui.QTreeModel;

public class MonitoredCharactersAndSkillPlansModel extends QTreeModel {

	/////////////
	// fields ///
	/////////////
	private static MonitoredCharactersAndSkillPlansModel instance;
	private List<MonitoredCharacter> characterList = new ArrayList<MonitoredCharacter>();
	public Signal1<List<MonitoredCharacter>> listUpdated = new Signal1<List<MonitoredCharacter>>();

	// ///////////////
	// constructors //
	// ///////////////
	private MonitoredCharactersAndSkillPlansModel() throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		super();
		init();
	}

	private void init() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		List<APIData> list = MonitoredCharacterDAO.getInstance().findMonitoredCharacters();
		for (APIData data : list) {
			MonitoredCharacter character = new MonitoredCharacter(data);
			characterList.add(character);
		}
	}

	public static MonitoredCharactersAndSkillPlansModel getInstance() throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		if (instance == null) {
			instance = new MonitoredCharactersAndSkillPlansModel();
		}
		return instance;
	}

	// /////////////////
	// public methods //
	// /////////////////
	public void addCharacter(MonitoredCharacter character) throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		characterList.add(character);
		MonitoredCharacterDAO.getInstance().addMonitoredCharacter(character.getApi());
		listUpdated.emit(characterList);
	}

	public void removeCharacter(int currentIndex) throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		int characterID = characterList.get(currentIndex).getApi().getCharacterID();
		MonitoredCharacterDAO.getInstance().deleteMonitoredCharacter(characterID);
		characterList.remove(currentIndex);
		listUpdated.emit(characterList);
	}

	public MonitoredCharacter getCharacterAt(int index) {
		return characterList.get(index);
	}

	public int characterCount(){
		return characterList.size();
	}

	///////////////////////////////////
	// overridden QTreeModel methods //
	///////////////////////////////////
	@Override
	public Object child(Object parent, int index) {
		Object child=null;
		if(parent==null){
			child = characterList.get(index);
		} else if (parent instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parent;
			child = parentCharacter.getSkillPlanAt(index);
		}
		return child;
	}

	@Override
	public int childCount(Object parent) {
		int childCount = 0;
		if(parent==null){
			childCount = characterList.size();
		} else if (parent instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parent;
			childCount = parentCharacter.skillPlanCount();
		}
		return childCount;
	}

	@Override
	public String text(Object value) {
		return value.toString();
	}

	@Override
	public Object data(Object value, int role) {

		Object result = null;
		switch (role) {
		// text to display
		case ItemDataRole.DisplayRole:
			result = value;
			break;
		default:
			result = null;
		}

		return result;
	}


	//////////////////////////////
	// overridden D&D methods   //
	//////////////////////////////
	@Override
	public boolean setData(QModelIndex index, Object value, int role) {
		boolean result = true;

		if(role==ItemDataRole.DisplayRole){
			Object target=indexToValue(index);
			if(target instanceof MonitoredCharacter && value instanceof MonitoredCharacter){
				MonitoredCharacter c = (MonitoredCharacter) value; 
				MonitoredCharacterDAO.getInstance().updateMonitoredCharacter(c);
				result = true;
			} else if (target instanceof SkillPlan && value instanceof SkillPlan){
				SkillPlan sp = (SkillPlan) value;
				SkillPlan targetPlan = (SkillPlan) target;
				if(sp.getId()==SkillPlan.NOID){
					// SkillPlan passed as parameter has no id hence it's 
					// a new SP and we must create it in the database
					result = createSkillPlan(sp, targetPlan);
					if(result==false){
						removeRow(index.row(),index.parent());
					}
				} else {
					// SkillPlan passed as parameter has already an id 
					// hence it already exist
					result = moveSkillPlan(sp, targetPlan);
				}
			} else {
				// should not happen
				result = false;
			}
		} else {
			// if role!=DisplayRole , we do nothing
			result = false;
		}

		return result;
	}

	@Override
	public boolean insertRows(int row, int count, QModelIndex parent) {
		boolean result = false;

		Object parentObject = (MonitoredCharacter) indexToValue(parent);
		if(parentObject instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parentObject;
			parentCharacter.addSkillPlan(
					new SkillPlan(
							parentCharacter.getApi().getUserID(), 
							SkillPlan.NOID, 
							row,
							""
					)
			);
			childrenInserted(parent, row, row + count - 1);
			result =true;
		}

		return result;
	}

	@Override
	public boolean removeRows(int row, int count, QModelIndex parent) {
		boolean result = false;

		Object parentObject = (MonitoredCharacter) indexToValue(parent);
		if(parentObject instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parentObject;
			parentCharacter.deletePlan(row);
			childrenRemoved(parent, row, row + count -1);
			result =true;
		} 

		return result;
	}

	/////////////////////
	// private methods //
	/////////////////////
	private boolean createSkillPlan(SkillPlan toBeCreated, SkillPlan target) {
		MonitoredCharacter character = findCharacter(target.getCharacterID());
		SkillPlanDAO spDAO = null;
		spDAO = SkillPlanDAO.getInstance();
		try {
			spDAO.beginTran();
			SkillPlan newPlan = spDAO.createSkillPlan(toBeCreated);
			target = newPlan;
			spDAO.updateOrderIndices(character);
			spDAO.commit();
		} catch (PQException e) {
			e.printStackTrace();
			try {
				spDAO.rollback();
			} catch (PQUserDatabaseFileCorrupted e1) {
				// that would be very unlucky to fall here
				e1.printStackTrace();
			}
			return false;
		} 
		
		return true;
	}
	
	private boolean moveSkillPlan(SkillPlan source, SkillPlan target) {
		MonitoredCharacter targetCharacter = findCharacter(target.getCharacterID());
		MonitoredCharacter sourceCharacter = findCharacter(source.getCharacterID());
		SkillPlanDAO spDAO = null;
		return false;
	}
	
	private MonitoredCharacter findCharacter(int id){
		MonitoredCharacter result = null;
		for (MonitoredCharacter character : characterList){
			if (id == character.getApi().getCharacterID() ){
				return  character;
			}
		}
		return result;
	}

}
