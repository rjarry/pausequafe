package org.pausequafe.data.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;


public class SkillPlanDAO extends UserDatabaseDAO {
	
	// //////////////////
	// private fields //
	// //////////////////

	private static SkillPlanDAO instance;

	// ///////////////
	// constructor //
	// ///////////////

	private SkillPlanDAO() {
		super();
	}

	public synchronized static SkillPlanDAO getInstance() {
		if (instance == null) {
			instance = new SkillPlanDAO();
		}
		return instance;
	}

	// //////////////////
	// public methods //
	// //////////////////
	public List<SkillPlan> findSkillPlanByChararcterID(int characterID) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		
		List<SkillPlan> spList = new ArrayList<SkillPlan>();
		createUserDataBaseIfNotExists();
		initConnection(SQLConstants.USER_DATABASE);
		
		try {
			ResultSet rs = stat.executeQuery(
					SQLConstants.QUERY_SKILLPLANS.replace("?",String.valueOf(characterID)));
			while(rs.next()){
				SkillPlan sp = new SkillPlan(
						rs.getInt(SQLConstants.CHARACTERID_COL),
						rs.getInt(SQLConstants.SKILLPLANID_COL),
						rs.getInt(SQLConstants.SKILLPLANINDEX_COL),
						rs.getString(SQLConstants.SKILLPLANNAME_COL));
				spList.add(sp);
			}
			rs.close();
		} catch (SQLException e) {
			throw new PQUserDatabaseFileCorrupted();
		} finally {
			closeConnection();
		}
		
		return spList;
	}
	
	public SkillPlan createSkillPlan(SkillPlan sp) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		
		SkillPlan createdSP = null;
		createUserDataBaseIfNotExists();
		initConnection(SQLConstants.USER_DATABASE);
		
		initPrepareStatement(SQLConstants.USER_DATABASE,
				SQLConstants.INSERT_SKILL_PLAN);
		
		int id;
		
		try {
			id = getNewIdForPlan();
			prep.setInt(1,sp.getCharacterID());
			prep.setInt(2,id); 
			prep.setInt(3, sp.getIndex());
			prep.setString(4, sp.getName());
			prep.executeUpdate();
			
			createdSP = new SkillPlan(sp.getCharacterID(), id, sp.getIndex(), sp.getName());
		} catch (SQLException e) {
			throw new PQUserDatabaseFileCorrupted();
		} finally {
			closeConnection();
		}
		
		return createdSP;
	}

	public void updateSkillPlan(int id,SkillPlan sp) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted{
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if (userdataBaseFile.exists()) {
			try {
				initConnection(SQLConstants.USER_DATABASE);
				initPrepareStatement(SQLConstants.USER_DATABASE,
						SQLConstants.UPDATE_SKILLPLAN);
				prep.setInt(0, sp.getCharacterID());
				prep.setInt(1, sp.getIndex());
				prep.setString(2, sp.getName());
				prep.setInt(3, id);
				prep.executeUpdate();
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			} finally { 
				closeConnection();
			}
			
		}
		
	}
	
	public void updateOrderIndices(MonitoredCharacter parentCharacter) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		for(int i = 0 ; i<parentCharacter.skillPlanCount();i++){
			SkillPlan sp = parentCharacter.getSkillPlanAt(i);
			sp.setIndex(i);
			updateSkillPlan(sp.getId(), sp);
		}
	}
	
	public void deleteSkillPlan(List<Integer> ids)throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {
		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if (userdataBaseFile.exists()) {
			String InClause = buildInClause(ids);

			try {
				initConnection(SQLConstants.USER_DATABASE);
				stat.executeUpdate(SQLConstants.DELETE_SKILLPLANS.replaceAll("?", InClause ));
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			} finally {
				closeConnection();
			}

		}
	}
	
	private int getNewIdForPlan() throws SQLException {
		ResultSet rs = stat.executeQuery(SQLConstants.QUERY_SKILLPLAN_MAXID);
		rs.next();
		int maxID = rs.getInt(0);
		
		return maxID + 1;
	}

}
