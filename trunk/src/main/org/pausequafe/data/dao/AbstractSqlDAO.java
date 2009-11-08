package org.pausequafe.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

/**
 * An abstract class for DAOs that use SQL to retrieve data
 * 
 * @author Gobi
 */
public abstract class AbstractSqlDAO {

	protected Connection conn;
	protected Statement stat;
	protected PreparedStatement prep;
	protected boolean transactionMode = false;

	public void beginTran(String dataBaseName) throws PQUserDatabaseFileCorrupted, PQSQLDriverNotFoundException {
		if(transactionMode == false){
			try {
				initConnection(dataBaseName);
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			}
			transactionMode = true;
		}
	}

	public void commit() throws PQUserDatabaseFileCorrupted {
		if(transactionMode == true ){
			try {
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			}
			transactionMode = false;
			closeConnection();
		}
	}

	public void rollback() throws PQUserDatabaseFileCorrupted {
		if(transactionMode == true ){
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			}
			transactionMode = false;
			closeConnection();
		}

	}

	protected void initConnection(String dataBaseName) throws  PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		if (conn == null){
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(dataBaseName);
				stat = conn.createStatement();
			} catch (ClassNotFoundException e) {
				throw new PQSQLDriverNotFoundException();
			} catch (SQLException e) {
				throw new PQUserDatabaseFileCorrupted();
			}
		}
	}

	protected void closeConnection(){
		if(transactionMode==false){
			try {
				if(prep != null){
					prep.close();
					prep = null;
				}
				if(stat != null){
					stat.close();
					stat = null;
				}
				if(conn != null){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void initPrepareStatement(String dataBaseName, String sql) throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		if (conn==null){
			initConnection(dataBaseName);
		}
		try {
			prep = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected String buildInClause(List<Integer> toBeQueried) {
		String inClause = "";
		boolean first = true;
		for (Integer typeID : toBeQueried) {
			if (first) {
				first = false;
			} else {
				inClause += ",";
			}
			inClause += typeID;
		}
		return inClause;
	}
}
