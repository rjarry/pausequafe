/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.data.dao;

import java.io.File;
import java.sql.SQLException;

import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.SQLConstants;

public class UserDatabaseDAO extends AbstractSqlDAO {

    protected void createUserDataBaseIfNotExists() throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        File userdataBaseFile = new File(SQLConstants.USER_DATABASE_FILE);

        if (!userdataBaseFile.exists()) {
            initConnection(SQLConstants.USER_DATABASE);
            try {
                stat.executeUpdate(SQLConstants.CREATE_CHARACTER_TABLE);
                stat.executeUpdate(SQLConstants.CREATE_SKILLPLANTABLE);
            } catch (SQLException e) {
                throw new PQUserDatabaseFileCorrupted(e);
            }
            closeConnection();
        }
    }

    public void beginTran() throws PQUserDatabaseFileCorrupted, PQSQLDriverNotFoundException {
        super.beginTran(SQLConstants.USER_DATABASE);
    }

}
