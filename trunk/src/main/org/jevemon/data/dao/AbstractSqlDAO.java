package org.jevemon.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An abstract class for DAOs that use SQL to retrieve data
 * 
 * @author Gobi
 */
public abstract class AbstractSqlDAO {
	
	protected Connection conn;
	protected Statement stat;
	protected PreparedStatement prep;
	
	protected void initConnection(String dataBaseName) {
		if (conn == null){
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(dataBaseName);
				stat = conn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO réviser la gestion de cette exception
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO réviser la gestion de cette exception
				e.printStackTrace();
			}
		}
	}
	
	protected void initPrepareStatement(String dataBaseName, String sql) {
		if (conn==null){
			initConnection(dataBaseName);
		}
		try {
			prep = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO réviser la gestion de cette exception
			e.printStackTrace();
		}
	}
}
