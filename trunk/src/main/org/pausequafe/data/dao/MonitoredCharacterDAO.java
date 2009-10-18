package org.pausequafe.data.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;

public class MonitoredCharacterDAO extends AbstractSqlDAO {
	
	////////////////////
	// private fields //
	////////////////////
	
	private static MonitoredCharacterDAO instance;
	
    /////////////////
    // constructor //
    /////////////////
	
	private MonitoredCharacterDAO(){
		super();
	}
	
	public synchronized static MonitoredCharacterDAO getInstance(){
		if (instance == null){
			instance = new MonitoredCharacterDAO();
		}
		return instance;
	}
	
	////////////////////
    // public methods //
    ////////////////////

	
	
	
	public List<APIData> getMonitoredCharacters() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		List<APIData> monitoredCharacters = new ArrayList<APIData>();
		
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		
		initConnection(SQLConstants.USER_DATABASE);
		
		
		try {
			ResultSet rs = stat.executeQuery(SQLConstants.QUERY_MONITORED_CHARACTERS);
			
			while(rs.next()){
				APIData data = new APIData(
						rs.getInt(SQLConstants.CHARACTERID_COL),
						rs.getString(SQLConstants.CHARACTERNAME_COL),
						rs.getInt(SQLConstants.USERID_COL),
						rs.getString(SQLConstants.APIKEY_COL));
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
	
	
	
	
	
	
	
	
	
	public boolean isMonitored(APIData data) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(userdataBaseFile.exists()){
			List<APIData> list = getMonitoredCharacters();
			
			for(APIData mon : list){
				if(mon.getCharacterID() == data.getCharacterID()){
					return true;
				}
			}
		} 
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	public List<APIData> getDistinctApiData() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		List<APIData> apiData = new ArrayList<APIData>();
		
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		
		initConnection(SQLConstants.USER_DATABASE);
		
		
		try {
			ResultSet rs = stat.executeQuery(SQLConstants.QUERY_DISTINCT_API);
			while(rs.next()){
				APIData data = new APIData(	rs.getInt(SQLConstants.USERID_COL),
											rs.getString(SQLConstants.APIKEY_COL));
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
	
	
	
	
	
	
	
	
	
	public void addMonitoredCharacter(APIData data) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.CHARACTER_EXISTS);
		
		try {
			prep.setInt(1, data.getCharacterID());
			ResultSet rs = prep.executeQuery();
			
			if (rs.next()){
				rs.close();
				initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.UPDATE_MONITORED_CHARACTER);
				prep.setInt(1, 1);
				prep.setString(2, data.getCharacterName());
				prep.setInt(3, data.getUserID());
				prep.setString(4, data.getApiKey());

				prep.setInt(5, data.getCharacterID());
				prep.executeUpdate();
			} else {
				rs.close();
				initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.ADD_MONITORED_CHARACTER);
				
				prep.setInt(1, data.getCharacterID());
				prep.setString(2, data.getCharacterName());
				prep.setInt(3, data.getUserID());
				prep.setString(4, data.getApiKey());
				
				prep.executeUpdate();
			}
		} catch (SQLException e) {
			closeConnection();
			throw new PQUserDatabaseFileCorrupted();
		}
		closeConnection();
	}
	
	
	
	
	
	
	
	
	
	public void removeMonitoredCharacter(int characterID) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(userdataBaseFile.exists()){
			initConnection(SQLConstants.USER_DATABASE);
			
			try {
				PreparedStatement prep = conn.prepareStatement(SQLConstants.REMOVE_MONITORED_CHARACTER);
				
				prep.setInt(1, characterID);
				
				prep.executeUpdate();
			} catch (SQLException e) {
				closeConnection();
				throw new PQUserDatabaseFileCorrupted();
			}
			
			
			closeConnection();
		}
	}
	
	
	
	
	
	/////////////////////
    // private methods //
    /////////////////////
	
	
	private void createUserDataBase() throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		initConnection(SQLConstants.USER_DATABASE);
		try {
			stat.executeUpdate(SQLConstants.CREATE_USER_DATABASE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}
}
