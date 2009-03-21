package org.jevemon.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jevemon.misc.exceptions.JEVEMonDatabaseFileCorrupted;
import org.jevemon.misc.exceptions.JEVEMonSQLDriverNotFoundException;

/**
 * An abstract class for DAOs that use SQL to retrieve data
 * 
 * @author Gobi
 */
public abstract class AbstractSqlDAO {
	
	protected Connection conn;
	protected Statement stat;
	protected PreparedStatement prep;
	
	protected void initConnection(String dataBaseName) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		if (conn == null){
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(dataBaseName);
				stat = conn.createStatement();
			} catch (ClassNotFoundException e) {
				throw new JEVEMonSQLDriverNotFoundException();
			} catch (SQLException e) {
				throw new JEVEMonDatabaseFileCorrupted();
			}
		}
	}
	
	protected void closeConnection(){
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
	
	protected void initPrepareStatement(String dataBaseName, String sql) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		if (conn==null){
			initConnection(dataBaseName);
		}
		try {
			prep = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
