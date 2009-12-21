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

import static org.pausequafe.misc.util.SQLConstants.CHARACTERID_COL;
import static org.pausequafe.misc.util.SQLConstants.DELETE_SKILLPLANS;
import static org.pausequafe.misc.util.SQLConstants.INSERT_SKILL_PLAN;
import static org.pausequafe.misc.util.SQLConstants.QUERY_SKILLPLANS;
import static org.pausequafe.misc.util.SQLConstants.QUERY_SKILLPLAN_MAXID;
import static org.pausequafe.misc.util.SQLConstants.SKILLPLANID_COL;
import static org.pausequafe.misc.util.SQLConstants.SKILLPLANINDEX_COL;
import static org.pausequafe.misc.util.SQLConstants.SKILLPLANNAME_COL;
import static org.pausequafe.misc.util.SQLConstants.UPDATE_SKILLPLAN;
import static org.pausequafe.misc.util.SQLConstants.USER_DATABASE;
import static org.pausequafe.misc.util.SQLConstants.USER_DATABASE_FILE;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.MonitoredCharacter;
import org.pausequafe.data.business.SkillPlan;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;

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
    public List<SkillPlan> findSkillPlanByChararcterID(int characterID)
            throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {

        List<SkillPlan> spList = new ArrayList<SkillPlan>();
        createUserDataBaseIfNotExists();
        initConnection(USER_DATABASE);

        try {
            ResultSet rs = stat.executeQuery(QUERY_SKILLPLANS.replace("?", String
                    .valueOf(characterID)));
            while (rs.next()) {
                SkillPlan sp = new SkillPlan(rs.getInt(CHARACTERID_COL),
                                             rs.getInt(SKILLPLANID_COL), 
                                             rs.getInt(SKILLPLANINDEX_COL), 
                                             rs.getString(SKILLPLANNAME_COL));
                spList.add(sp);
            }
            rs.close();
        } catch (SQLException e) {
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }

        return spList;
    }

    public SkillPlan createSkillPlan(SkillPlan sp) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {

        SkillPlan createdSP = null;
        createUserDataBaseIfNotExists();
        initConnection(USER_DATABASE);

        initPrepareStatement(USER_DATABASE, INSERT_SKILL_PLAN);

        int id;

        try {
            id = getNewIdForPlan();
            prep.setInt(1, sp.getCharacterID());
            prep.setInt(2, id);
            prep.setInt(3, sp.getIndex());
            prep.setString(4, sp.getName());
            prep.executeUpdate();

            createdSP = new SkillPlan(sp.getCharacterID(), id, sp.getIndex(), sp.getName());
        } catch (SQLException e) {
            throw new PQUserDatabaseFileCorrupted(e);
        } finally {
            closeConnection();
        }

        return createdSP;
    }

    public void updateSkillPlan(int id, SkillPlan sp) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        File userdataBaseFile = new File(USER_DATABASE_FILE);
        if (userdataBaseFile.exists()) {
            try {
                initConnection(USER_DATABASE);
                initPrepareStatement(USER_DATABASE, UPDATE_SKILLPLAN);
                prep.setInt(1, sp.getCharacterID());
                prep.setInt(2, sp.getIndex());
                prep.setString(3, sp.getName());
                prep.setInt(4, id);
                prep.executeUpdate();
            } catch (SQLException e) {
                throw new PQUserDatabaseFileCorrupted(e);
            } finally {
                closeConnection();
            }

        }

    }

    public void updateOrderIndices(MonitoredCharacter parentCharacter)
            throws PQUserDatabaseFileCorrupted, PQSQLDriverNotFoundException {
        beginTran();
        for (int i = 0; i < parentCharacter.skillPlanCount(); i++) {
            SkillPlan sp = parentCharacter.getSkillPlanAt(i);
            sp.setIndex(i);
            try {
                updateSkillPlan(sp.getId(), sp);
            } catch (PQUserDatabaseFileCorrupted e) {
                rollback();
            }
        }
        commit();
    }

    public void deleteSkillPlan(List<Integer> ids) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        File userdataBaseFile = new File(USER_DATABASE_FILE);
        if (userdataBaseFile.exists()) {
            String InClause = buildInClause(ids);

            try {
                initConnection(USER_DATABASE);
                stat.executeUpdate(DELETE_SKILLPLANS.replaceAll("?", InClause));
            } catch (SQLException e) {
                throw new PQUserDatabaseFileCorrupted(e);
            } finally {
                closeConnection();
            }

        }
    }

    public void deleteSkillPlan(int id) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        File userdataBaseFile = new File(USER_DATABASE_FILE);
        if (userdataBaseFile.exists()) {
            String InClause = Integer.toString(id);

            try {
                initConnection(USER_DATABASE);
                stat.executeUpdate(DELETE_SKILLPLANS.replace("?", InClause));
            } catch (SQLException e) {
                throw new PQUserDatabaseFileCorrupted(e);
            } finally {
                closeConnection();
            }

        }
    }

    private int getNewIdForPlan() throws SQLException {
        ResultSet rs = stat.executeQuery(QUERY_SKILLPLAN_MAXID);
        rs.next();
        int maxID = rs.getInt(1);
        rs.close();

        return maxID + 1;
    }

}
