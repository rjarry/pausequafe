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
	public Signal0 listUpdated = new Signal0();

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
			MonitoredCharacter c = new MonitoredCharacter(data);
			initCharacter(c);
			characterList.add(c);
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
		int row = characterList.size();
		initCharacter(character);
		beginInsertRows(null, row, row);
		characterList.add(character);
		childrenInserted(null, row, row);
		MonitoredCharacterDAO.getInstance().addMonitoredCharacter(character.getApi());
		listUpdated.emit();
	}

	public void removeCharacter(int currentIndex) throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		int characterID = characterList.get(currentIndex).getApi().getCharacterID();
		beginRemoveRows(null, currentIndex, currentIndex);
		characterList.remove(currentIndex);
		childrenRemoved(null, currentIndex, currentIndex);
		MonitoredCharacterDAO.getInstance().deleteMonitoredCharacter(characterID);
		listUpdated.emit();
	}

	
	
	public MonitoredCharacter getCharacterAt(int index) {
		return characterList.get(index);
	}

	public int characterCount(){
		return characterList.size();
	}
	
	public List<MonitoredCharacter> getList() {
		return characterList;
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

		switch(role){
		case ItemDataRole.DisplayRole :
			Object target=indexToValue(index);
			if(target instanceof MonitoredCharacter && value instanceof MonitoredCharacter){
				MonitoredCharacter c = (MonitoredCharacter) value; 
				MonitoredCharacterDAO.getInstance().updateMonitoredCharacter(c);
				result = true;
			} else if (target instanceof SkillPlan && value instanceof SkillPlan){
				SkillPlan sourcePlan = (SkillPlan) value;
				SkillPlan targetPlan = (SkillPlan) target;
				if(sourcePlan.getId()==SkillPlan.NOID){
					// SkillPlan passed as parameter has no id hence it's 
					// a new SP and we must create it in the database
					result = createSkillPlan(sourcePlan, targetPlan);
					if(result==false){
						removeRow(index.row(),index.parent());
					}
				} else {
					// SkillPlan passed as parameter has already an id 
					// hence it already exist
					result = moveSkillPlan(sourcePlan, targetPlan);
				}
				dataChanged.emit(index, index);
			} else {
				// should not happen
				result = false;
			}
			break;
		default:
			break;
		}

		return result;
	}

	@Override
	public boolean insertRows(int row, int count, QModelIndex parent) {
		boolean result = false;

		Object parentObject = (MonitoredCharacter) indexToValue(parent);
		if(parentObject instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parentObject;
			beginInsertRows(parent, row, row + count - 1);
			parentCharacter.addSkillPlan(row,
					new SkillPlan(
							parentCharacter.getApi().getCharacterID(), 
							SkillPlan.NOID, 
							row,
							""
					)
			);
			childrenInserted(parent, row, row + count - 1);
			result = true;
		}

		return result;
	}

	@Override
	public boolean removeRows(int row, int count, QModelIndex parent) {
		boolean result = false;
		if(count!=1){
			return false;
		}
		SkillPlanDAO spDAO = SkillPlanDAO.getInstance();
		
		Object parentObject = (MonitoredCharacter) indexToValue(parent);
		if(parentObject instanceof MonitoredCharacter){
			MonitoredCharacter parentCharacter = (MonitoredCharacter) parentObject;
			SkillPlan skillPlanAt = parentCharacter.getSkillPlanAt(row);
			if(skillPlanAt.getId()==SkillPlan.NOID){
				// this is the end of a move action (because id has been set to "no id")
				// hence no deletion has to be done in database
				try {
					spDAO.updateOrderIndices(parentCharacter);
				} catch (PQException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				// this a real deletion of a SkillPlan
				// hence we have to delete the SP from DB
				SkillPlan sp = parentCharacter.getSkillPlanAt(row);
				try {
					spDAO.deleteSkillPlan(sp.getId());
				} catch (PQException e) {
					e.printStackTrace();
					return false;
				} 
			}
			beginRemoveRows(parent, row, row);
			parentCharacter.removePlan(row);
			childrenRemoved(parent, row, row );
			try {
				spDAO.updateOrderIndices(parentCharacter);
			} catch (PQException e) {
				e.printStackTrace();
			}
			result = true;
		}
		return result;
	}

	/////////////////////
	// private methods //
	/////////////////////
	private boolean createSkillPlan(SkillPlan toBeCreated, SkillPlan target) {
		MonitoredCharacter character = findCharacter(target.getCharacterID());
		SkillPlanDAO spDAO = SkillPlanDAO.getInstance();
		try {
			SkillPlan newPlan = spDAO.createSkillPlan(toBeCreated);
			character.setSkillPlanAt(target.getIndex(),newPlan);
			spDAO.updateOrderIndices(character);
		} catch (PQException e) {
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}
	
	private boolean moveSkillPlan(SkillPlan source, SkillPlan target) {
		MonitoredCharacter character = findCharacter(source.getCharacterID());
		SkillPlan originalSource = null;
		for(int i=0;i<character.skillPlanCount();i++){
			SkillPlan sp = character.getSkillPlanAt(i);
			if(sp.getId()==source.getId()){
				originalSource = sp;
				break;
			}
		}
		
		target.setCharacterID(source.getCharacterID());
		target.setId(source.getId());
		target.setName(source.getName());
		
		originalSource.setId(-1);

		return true;
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
	
	private void initCharacter(MonitoredCharacter c) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		List<SkillPlan> skillPlanList = 
			SkillPlanDAO.getInstance().findSkillPlanByChararcterID(c.getApi().getCharacterID());
		for(SkillPlan plan : skillPlanList){
			c.addSkillPlan(plan.getIndex(), plan);
		}
	}

}
