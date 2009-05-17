package org.pausequafe.gui.view.main;

import java.util.LinkedList;
import java.util.List;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.dao.MonitoredCharacterDAO;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

public class MonitoredCharacterList {
	
	////////////
	// fields //
	////////////
	private List<MonitoredCharacter> characterList;
	
	public MonitoredCharacterList(){
		characterList = new LinkedList<MonitoredCharacter>();
	}

	
	/////////////
	// getters //
	/////////////
	public List<MonitoredCharacter> getCharacterList() {
		return characterList;
	}


	public void addCharacter(MonitoredCharacter character) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		this.characterList.add(character);
		MonitoredCharacterDAO.getInstance().addMonitoredCharacter(character.getApi());
	}


	public void removeCharacter(int currentIndex) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		int characterID = characterList.get(currentIndex).getSheet().getCharacterID();
		MonitoredCharacterDAO.getInstance().removeMonitoredCharacter(characterID);
		characterList.remove(currentIndex);
	}

	
	
}
