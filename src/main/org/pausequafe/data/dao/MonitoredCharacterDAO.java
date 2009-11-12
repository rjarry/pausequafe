package org.pausequafe.data.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;

public class MonitoredCharacterDAO extends UserDatabaseDAO {

	// //////////////////
	// private fields //
	// //////////////////

	private static MonitoredCharacterDAO instance;

	// ///////////////
	// constructor //
	// ///////////////

	private MonitoredCharacterDAO() {
		super();
	}

	public synchronized static MonitoredCharacterDAO getInstance() {
		if (instance == null) {
			instance = new MonitoredCharacterDAO();
		}
		return instance;
	}

	// //////////////////
	// public methods //
	// //////////////////
	public List<APIData> findMonitoredCharacters() throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {
		
		List<APIData> monitoredCharacters = new ArrayList<APIData>();
		createUserDataBaseIfNotExists();
		initConnection(SQLConstants.USER_DATABASE);

		try {
			ResultSet rs = stat.executeQuery(SQLConstants.QUERY_MONITORED_CHARACTERS);

			while (rs.next()) {
				APIData data = new APIData(rs.getInt(SQLConstants.CHARACTERID_COL), rs
						.getString(SQLConstants.CHARACTERNAME_COL), rs
						.getInt(SQLConstants.USERID_COL), rs.getString(SQLConstants.APIKEY_COL));
				monitoredCharacters.add(data);
			}
			rs.close();
		} catch (SQLException e) {
			closeConnection();
			throw new PQUserDatabaseFileCorrupted();
		}
		closeConnection();

		return monitoredCharacters;
	}

	public List<APIData> findDistinctApiData() throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {
		
		createUserDataBaseIfNotExists();
		initConnection(SQLConstants.USER_DATABASE);

		List<APIData> apiData = new ArrayList<APIData>();
		try {
			ResultSet rs = stat.executeQuery(SQLConstants.QUERY_DISTINCT_API);
			while (rs.next()) {
				APIData data = new APIData(rs.getInt(SQLConstants.USERID_COL), rs
						.getString(SQLConstants.APIKEY_COL));
				apiData.add(data);
			}
			rs.close();
		} catch (SQLException e) {
			closeConnection();
			throw new PQUserDatabaseFileCorrupted();
		}
		closeConnection();

		return apiData;
	}
	public boolean isMonitored(APIData data) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if (userdataBaseFile.exists()) {
			List<APIData> list = findMonitoredCharacters();

			for (APIData mon : list) {
				if (mon.getCharacterID() == data.getCharacterID()) {
					return true;
				}
			}
		}
		return false;
	}


	public void addMonitoredCharacter(APIData data) throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {
		
		createUserDataBaseIfNotExists();
		initConnection(SQLConstants.USER_DATABASE);
		try {
			// TODO : gestion perso qui change de compte
			stat = conn.createStatement();
			int rowsChanged = stat.executeUpdate(SQLConstants.UPDATE_MONITORED_CHARACTER.replace(
					"?", String.valueOf(data.getCharacterID())));
			if (rowsChanged == 0) {
				// the characterID wasn't found in the database :
				// new character from a new account
				initPrepareStatement(SQLConstants.USER_DATABASE,
						SQLConstants.ADD_MONITORED_CHARACTER);
				prep.setInt(1, data.getCharacterID());
				prep.setString(2, data.getCharacterName());
				prep.setInt(3, data.getUserID());
				prep.setString(4, data.getApiKey());
				prep.executeUpdate();
			}

			// We update also the API keys of the characters of the same account
			initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.UPDATE_API_KEY_FROM_USERID);
			prep.setString(1, data.getApiKey());
			prep.setInt(2, data.getUserID());
			prep.executeUpdate();
		} catch (SQLException e) {
			throw new PQUserDatabaseFileCorrupted(e);
		} finally {
			closeConnection();
		}
	}

	public void updateMonitoredCharacter(MonitoredCharacter c) {
		// FIXME : Implémenter cette méthode manquante
	}
	
	public void deleteMonitoredCharacter(int characterID) throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if (userdataBaseFile.exists()) {
			initConnection(SQLConstants.USER_DATABASE);

			try {
				PreparedStatement prep = conn
						.prepareStatement(SQLConstants.REMOVE_MONITORED_CHARACTER);

				prep.setInt(1, characterID);

				prep.executeUpdate();
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			} finally {
				closeConnection();
			}

		}
	}
	
	public void changeApiKey(APIData data) throws PQSQLDriverNotFoundException,
	PQUserDatabaseFileCorrupted {
		
		createUserDataBaseIfNotExists();
		try {
			initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.UPDATE_API_KEY_FROM_USERID);
			prep.setString(1, data.getApiKey());
			prep.setInt(2, data.getUserID());
			prep.executeUpdate();
		} catch (SQLException e) {
			closeConnection();
			throw new PQUserDatabaseFileCorrupted();
		} finally {
			closeConnection();
		}
		
	}


	
}
