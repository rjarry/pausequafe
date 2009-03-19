package org.jevemon.data.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jevemon.data.business.APIData;
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
	
	public static SessionDAO getInstance(){
		if (instance == null){
			instance = new SessionDAO();
		}
		return instance;
	}
	
	////////////////////
    // public methods //
    ////////////////////

	public List<APIData> getMonitoredCharacters() throws SQLException{
		List<APIData> monitoredCharacters = new ArrayList<APIData>();
		
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		
		initConnection(SQLConstants.USER_DATABASE);
		
		
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
		closeConnection();
		
		return monitoredCharacters;
	}
	
	public List<APIData> getDistinctApiData() throws SQLException{
		List<APIData> apiData = new ArrayList<APIData>();
		
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		
		initConnection(SQLConstants.USER_DATABASE);
		
		ResultSet rs = stat.executeQuery(SQLConstants.QUERY_DISTINCT_API);
		
		while(rs.next()){
			APIData data = new APIData(	rs.getInt(SQLConstants.USERID_COL),
										rs.getString(SQLConstants.APIKEY_COL));
			apiData.add(data);
		}
		rs.close();
		closeConnection();
		
		return apiData;
	}
	
	public void addMonitoredCharacter(APIData data) throws SQLException{
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(!userdataBaseFile.exists()){
			createUserDataBase();
		}
		initPrepareStatement(SQLConstants.USER_DATABASE, SQLConstants.CHARACTER_EXISTS);
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
		closeConnection();
	}
	
	public void removeMonitoredCharacter(int characterID) throws SQLException{
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if(userdataBaseFile.exists()){
			initConnection(SQLConstants.USER_DATABASE);
			
			PreparedStatement prep = conn.prepareStatement(SQLConstants.REMOVE_MONITORED_CHARACTER);
			
			prep.setInt(1, characterID);
			
			prep.executeUpdate();
			
			
			closeConnection();
		}
	}
	
	
	
	/////////////////////
    // private methods //
    /////////////////////
	private void createUserDataBase() throws SQLException {
		initConnection(SQLConstants.USER_DATABASE);
		stat.executeUpdate(SQLConstants.CREATE_USER_DATABASE);
	}
}
