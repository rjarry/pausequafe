package org.jevemon.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jevemon.misc.util.SQLConstants;

/**
 * An abstract class for DAOs that use SQL to retrieve data
 * 
 * @author Gobi
 */
public abstract class AbstractSqlDAO {
	
	protected Connection conn;
	protected Statement stat;
	protected PreparedStatement prep;
	
	protected void initConnection() {
		if (conn == null){
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(SQLConstants.EVE_DATABASE);
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
	
	protected void initPrepareStatement(String sql) {
		if (conn==null){
			initConnection();
		}
		try {
			prep = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO réviser la gestion de cette exception
			e.printStackTrace();
		}
	}
}
