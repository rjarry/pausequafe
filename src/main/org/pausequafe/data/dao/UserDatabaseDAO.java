package org.pausequafe.data.dao;

import java.io.File;
import java.sql.SQLException;

import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;

public class UserDatabaseDAO extends AbstractSqlDAO {

	protected void createUserDataBaseIfNotExists() 
		throws PQSQLDriverNotFoundException,PQUserDatabaseFileCorrupted {

		File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);
		if (!userdataBaseFile.exists()) {
			initConnection(SQLConstants.USER_DATABASE);
			try {
				stat.executeUpdate(SQLConstants.CREATE_CHARACTER_TABLE);
				stat.executeUpdate(SQLConstants.CREATE_SKILLPLANTABLE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection();
		}
	}

	public void beginTran() throws PQUserDatabaseFileCorrupted, PQSQLDriverNotFoundException {
		super.beginTran(SQLConstants.USER_DATABASE);
	}

}
