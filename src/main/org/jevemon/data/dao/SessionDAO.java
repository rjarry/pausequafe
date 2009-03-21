package org.jevemon.data.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jevemon.data.business.APIData;
import org.jevemon.misc.exceptions.JEVEMonDatabaseFileCorrupted;
import org.jevemon.misc.exceptions.JEVEMonSQLDriverNotFoundException;
import org.jevemon.misc.util.SQLConstants;

public class SessionDAO extends AbstractSqlDAO {
	
	////////////////////
	// private fields //
	////////////////////
	
	private static SessionDAO instance;
	
    /////////////////
    // constructor //
    /////////////////
	
	private SessionDAO(){
		super();
	}
	
	public synchronized static SessionDAO getInstance(){
		if (instance == null){
			instance = new SessionDAO();
		}
		return instance;
	}
	
	////////////////////
    // public methods //
    ////////////////////

	
	
	
	public List<APIData> getMonitoredCharacters() throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
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
			throw new JEVEMonDatabaseFileCorrupted();
		}
		closeConnection();
		
		return monitoredCharacters;
	}
	
	
	
	
	
	
	
	
	
	public boolean isMonitored(APIData data) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
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
	
	
	
	
	
	
	
	
	
	
	public List<APIData> getDistinctApiData() throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted{
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
			throw new JEVEMonDatabaseFileCorrupted();
		}
		closeConnection();
		
		return apiData;
	}
	
	
	
	
	
	
	
	
	
	public void addMonitoredCharacter(APIData data) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
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
				prep.setInt(1, data.getCharacterID());
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
			throw new JEVEMonDatabaseFileCorrupted();
		}
		closeConnection();
	}
	
	
	
	
	
	
	
	
	
	public void removeMonitoredCharacter(int characterID) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(userdataBaseFile.exists()){
			initConnection(SQLConstants.USER_DATABASE);
			
			try {
				PreparedStatement prep = conn.prepareStatement(SQLConstants.REMOVE_MONITORED_CHARACTER);
				
				prep.setInt(1, characterID);
				
				prep.executeUpdate();
			} catch (SQLException e) {
				closeConnection();
				throw new JEVEMonDatabaseFileCorrupted();
			}
			
			
			closeConnection();
		}
	}
	
	
	
	
	
	/////////////////////
    // private methods //
    /////////////////////
	
	
	private void createUserDataBase() throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		initConnection(SQLConstants.USER_DATABASE);
		try {
			stat.executeUpdate(SQLConstants.CREATE_USER_DATABASE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection();
	}
}
