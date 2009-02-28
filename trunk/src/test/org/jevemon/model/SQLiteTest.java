package org.jevemon.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteTest {
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:db/eve-online.db");
		Statement stat = conn.createStatement();

		/*PreparedStatement prep = conn.prepareStatement(
				"select * from people values (?, ?);");

		prep.setString(1, "Gandhi");
		prep.setString(2, "politics");
		prep.addBatch();
		prep.setString(1, "Turing");
		prep.setString(2, "computers");
		prep.addBatch();
		prep.setString(1, "Wittgenstein");
		prep.setString(2, "smartypants");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);*/

		ResultSet rs = stat.executeQuery("select * from invTypes where typeID between 30 and 40");
		while (rs.next()) {
			System.out.println("name = " + rs.getString("typeName"));
		}
	}
}
