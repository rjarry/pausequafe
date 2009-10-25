package org.pausequafe.gui.model.characters;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.dao.MonitoredCharacterDAO;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

import com.trolltech.qt.gui.QTreeModel;

public class MonitoredCharactersModel extends QTreeModel {

	private static MonitoredCharactersModel instance;

	private List<MonitoredCharacter> characterList = new ArrayList<MonitoredCharacter>();

	public Signal1<List<MonitoredCharacter>> listUpdated = new Signal1<List<MonitoredCharacter>>();
	
	// ///////////////
	// constructors //
	// ///////////////
	private MonitoredCharactersModel() throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted {
		super();
		init();
	}

	private void init() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		List<APIData> list = MonitoredCharacterDAO.getInstance().getMonitoredCharacters();
		for (APIData data : list) {
			MonitoredCharacter character = new MonitoredCharacter(data);
			characterList.add(character);
		}
	}

	public static MonitoredCharactersModel getInstance() throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted {
		if (instance == null) {
			instance = new MonitoredCharactersModel();
		}
		return instance;
	}

	// /////////////////
	// public methods //
	// /////////////////
	@Override
	public Object child(Object parent, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int childCount(Object parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String text(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCharacter(MonitoredCharacter character) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted {
		characterList.add(character);
		MonitoredCharacterDAO.getInstance().addMonitoredCharacter(character.getApi());
		listUpdated.emit(characterList);
	}

	public void removeCharacter(int currentIndex) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted {
		int characterID = characterList.get(currentIndex).getApi().getCharacterID();
		MonitoredCharacterDAO.getInstance().removeMonitoredCharacter(characterID);
		characterList.remove(currentIndex);
		listUpdated.emit(characterList);
	}

	public List<MonitoredCharacter> getList() {
		return characterList;
	}

}
