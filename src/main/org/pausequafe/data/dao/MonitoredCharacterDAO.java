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

import static org.pausequafe.misc.util.SQLConstants.ADD_MONITORED_CHARACTER;
import static org.pausequafe.misc.util.SQLConstants.APIKEY_COL;
import static org.pausequafe.misc.util.SQLConstants.CHARACTERID_COL;
import static org.pausequafe.misc.util.SQLConstants.CHARACTERNAME_COL;
import static org.pausequafe.misc.util.SQLConstants.QUERY_DISTINCT_API;
import static org.pausequafe.misc.util.SQLConstants.QUERY_MONITORED_CHARACTERS;
import static org.pausequafe.misc.util.SQLConstants.REMOVE_MONITORED_CHARACTER;
import static org.pausequafe.misc.util.SQLConstants.UPDATE_API_KEY_FROM_USERID;
import static org.pausequafe.misc.util.SQLConstants.UPDATE_MONITORED_CHARACTER;
import static org.pausequafe.misc.util.SQLConstants.USERID_COL;
import static org.pausequafe.misc.util.SQLConstants.USER_DATABASE;
import static org.pausequafe.misc.util.SQLConstants.USER_DATABASE_FILE;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.APIData;
import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

public class MonitoredCharacterDAO extends UserDatabaseDAO {

    // //////////////////
    // private fields //
    // //////////////////

    private static MonitoredCharacterDAO instance;

    // ///////////////
    // constructor //
    // ///////////////

    private MonitoredCharacterDAO() {
        super();
    }

    public synchronized static MonitoredCharacterDAO getInstance() {
        if (instance == null) {
            instance = new MonitoredCharacterDAO();
        }
        return instance;
    }

    // //////////////////
    // public methods //
    // //////////////////
    public List<APIData> findMonitoredCharacters() throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        List<APIData> monitoredCharacters = new ArrayList<APIData>();
        createUserDataBaseIfNotExists();
        initConnection(USER_DATABASE);

        try {
            ResultSet rs = stat.executeQuery(QUERY_MONITORED_CHARACTERS);

            while (rs.next()) {
                APIData data = new APIData(rs.getInt(CHARACTERID_COL), 
                                           rs.getString(CHARACTERNAME_COL), 
                                           rs.getInt(USERID_COL), 
                                           rs.getString(APIKEY_COL));
                monitoredCharacters.add(data);
            }
            rs.close();
        } catch (SQLException e) {
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }

        return monitoredCharacters;
    }

    public List<APIData> findDistinctApiData() throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        createUserDataBaseIfNotExists();
        initConnection(USER_DATABASE);

        List<APIData> apiData = new ArrayList<APIData>();
        try {
            ResultSet rs = stat.executeQuery(QUERY_DISTINCT_API);
            while (rs.next()) {
                APIData data = new APIData(rs.getInt(USERID_COL), rs.getString(APIKEY_COL));
                apiData.add(data);
            }
            rs.close();
        } catch (SQLException e) {
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }

        return apiData;
    }

    public boolean isMonitored(APIData data) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        File userdataBaseFile = new File(USER_DATABASE_FILE);
        if (userdataBaseFile.exists()) {
            List<APIData> list = findMonitoredCharacters();

            for (APIData mon : list) {
                if (mon.getCharacterID() == data.getCharacterID()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addMonitoredCharacter(APIData data) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        createUserDataBaseIfNotExists();
        initConnection(USER_DATABASE);
        try {
            // TODO : gestion perso qui change de compte
            stat = conn.createStatement();
            int rowsChanged = stat.executeUpdate(
                    UPDATE_MONITORED_CHARACTER.replace("?", String.valueOf(data.getCharacterID())));
            if (rowsChanged == 0) {
                // the characterID wasn't found in the database :
                // new character from a new account
                initPrepareStatement(USER_DATABASE, ADD_MONITORED_CHARACTER);
                prep.setInt(1, data.getCharacterID());
                prep.setString(2, data.getCharacterName());
                prep.setInt(3, data.getUserID());
                prep.setString(4, data.getApiKey());
                prep.executeUpdate();
            }

            // We update also the API keys of the characters of the same account
            initPrepareStatement(USER_DATABASE, UPDATE_API_KEY_FROM_USERID);
            prep.setString(1, data.getApiKey());
            prep.setInt(2, data.getUserID());
            prep.executeUpdate();
        } catch (SQLException e) {
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }
    }

    public void updateMonitoredCharacter(MonitoredCharacter c) {
        // TODO : Implémenter cette méthode manquante
    }

    public void deleteMonitoredCharacter(int characterID) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        File userdataBaseFile = new File(USER_DATABASE_FILE);
        if (userdataBaseFile.exists()) {
            initConnection(USER_DATABASE);

            try {
                PreparedStatement prep = conn.prepareStatement(REMOVE_MONITORED_CHARACTER);

                prep.setInt(1, characterID);

                prep.executeUpdate();
            } catch (SQLException e) {
                throw new PQUserDatabaseFileCorrupted(e);
            } finally {
                closeConnection();
            }

        }
    }

    public void changeApiKey(APIData data) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        createUserDataBaseIfNotExists();
        try {
            initPrepareStatement(USER_DATABASE, UPDATE_API_KEY_FROM_USERID);
            prep.setString(1, data.getApiKey());
            prep.setInt(2, data.getUserID());
            prep.executeUpdate();
        } catch (SQLException e) {
            closeConnection();
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }

    }

}
