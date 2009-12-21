/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
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

package org.pausequafe.core.dao;

import static org.pausequafe.misc.util.SQLConstants.EVE_DATABASE_FILE;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

/**
 * A DAO that creates Market Groups from the eve database.
 * 
 * @author Administrateur
 */
public class MarketGroupDAO extends AbstractSqlDAO {

    // ////////////////////
    // protected fields //
    // ////////////////////
    private static MarketGroupDAO instance;
    public final HashMap<Integer, MarketGroup> memoryCache = new HashMap<Integer, MarketGroup>();

    // ///////////////
    // constructor //
    // ///////////////
    private MarketGroupDAO() {
        super();
    }

    public static MarketGroupDAO getInstance() {
        if (instance == null) {
            instance = new MarketGroupDAO();
        }
        return instance;
    }

    /**
     * Provided for convenience.
     * 
     */
    public MarketGroup findMarketGroupById(Integer groupId) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {
        MarketGroup result = null;

        result = memoryCache.get(groupId);
        if (result == null) {
            // get missing items from database
            result = queryMarketGroupById(groupId);
        } // missing items are now retrieved

        return result;
    }

    public void addMarketGroupChild(int marketGroupID, int childID) {
        MarketGroup parent = memoryCache.get(marketGroupID);
        if (parent != null) {
            parent.addChild(childID);
        }
    }

    public String getGroupName(int groupID) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(SQLConstants.EVE_DATABASE);
        String name = null;
        try {
            ResultSet res = stat.executeQuery(SQLConstants.QUERY_GROUP_NAME_BY_ID.replace("?",
                    String.valueOf(groupID)));
            if (res.next()) {
                name = res.getString(SQLConstants.GROUPNAME_COL);
            }
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }

        return name;
    }

    // ///////////////////
    // private methods //
    // ///////////////////

    private MarketGroup queryMarketGroupById(Integer groupId) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(SQLConstants.EVE_DATABASE);

        String query = SQLConstants.QUERY_MARKETGRP_BY_ID;
        query = query.replace("?", String.valueOf(groupId));
        // System.out.println(query); // for convenience : uncomment to see DB
        // queries

        MarketGroup marketGroup = null;
        List<MarketGroup> itemContainerParents = new ArrayList<MarketGroup>();
        List<MarketGroup> groupContainerParents = new ArrayList<MarketGroup>();
        try {
            ResultSet res = stat.executeQuery(query);
            while (res.next()) {
                marketGroup = new MarketGroup(res.getInt(SQLConstants.MARKETGRPID_COL), 
                        res.getString(SQLConstants.MARKETGRPNAME_COL), 
                        res.getBoolean(SQLConstants.HASTYPE_COL));

                memoryCache.put(marketGroup.getGroupID(), marketGroup);

                // Now we have to query the groups children
                if (marketGroup.isItemContainer()) {
                    itemContainerParents.add(marketGroup);
                } else {
                    groupContainerParents.add(marketGroup);
                }
            }
            res.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }

        if (groupId == SQLConstants.BLUEPRINTS_MKTGRPID) {
            ItemDAO.getInstance().initBlueprintGroupChildren(itemContainerParents);
            initBlueprintGroupChildren(groupContainerParents);
        } else {
            ItemDAO.getInstance().initMarketGroupChildren(itemContainerParents);
            initMarketGroupChildren(groupContainerParents);
        }

        return marketGroup;
    }

    private void initBlueprintGroupChildren(List<MarketGroup> parents)
            throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted,
            PQEveDatabaseCorrupted {

        if (parents == null || parents.size() == 0) {
            return;
        }
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(SQLConstants.EVE_DATABASE);

        String inClause = "";
        boolean first = true;
        for (MarketGroup daddy : parents) {
            if (first) {
                first = false;
            } else {
                inClause += ",";
            }
            inClause += daddy.getGroupID();
        }

        String query = SQLConstants.QUERY_MARKETGRP_BY_PARENT;
        query = query.replace("?", inClause);
        // System.out.println(query); // for convenience : uncomment to see DB
        // queries

        List<MarketGroup> itemContainerParents = new ArrayList<MarketGroup>();
        List<MarketGroup> groupContainerParents = new ArrayList<MarketGroup>();
        try {
            ResultSet res = conn.createStatement().executeQuery(query);
            while (res.next()) {
                int marketGroupID = res.getInt(SQLConstants.MARKETGRPID_COL);
                MarketGroup marketGroup = new MarketGroup(marketGroupID, 
                        res.getString(SQLConstants.MARKETGRPNAME_COL), 
                        res.getBoolean(SQLConstants.HASTYPE_COL));

                memoryCache.put(marketGroup.getGroupID(), marketGroup);
                addMarketGroupChild(res.getInt(SQLConstants.PARENTGRPID_COL), marketGroupID);
                // Now we have to query the groups children
                if (marketGroup.isItemContainer()) {
                    itemContainerParents.add(marketGroup);
                } else {
                    groupContainerParents.add(marketGroup);
                }
            }
            res.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }

        ItemDAO.getInstance().initBlueprintGroupChildren(itemContainerParents);
        if (groupContainerParents.size() != 0) {
            initBlueprintGroupChildren(groupContainerParents);
        }
    }

    /**
     * Initialize children for market groups passed as parameters
     */
    private void initMarketGroupChildren(List<MarketGroup> parents)
            throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted,
            PQEveDatabaseCorrupted {

        if (parents == null || parents.size() == 0) {
            return;
        }
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(SQLConstants.EVE_DATABASE);

        String inClause = "";
        boolean first = true;
        for (MarketGroup daddy : parents) {
            if (first) {
                first = false;
            } else {
                inClause += ",";
            }
            inClause += daddy.getGroupID();
        }

        String query = SQLConstants.QUERY_MARKETGRP_BY_PARENT;
        query = query.replace("?", inClause);
        // System.out.println(query); // for convenience : uncomment to see DB
        // queries

        List<MarketGroup> itemContainerParents = new ArrayList<MarketGroup>();
        List<MarketGroup> groupContainerParents = new ArrayList<MarketGroup>();
        try {
            ResultSet res = conn.createStatement().executeQuery(query);
            while (res.next()) {
                int marketGroupID = res.getInt(SQLConstants.MARKETGRPID_COL);
                MarketGroup marketGroup = new MarketGroup(marketGroupID, 
                        res.getString(SQLConstants.MARKETGRPNAME_COL), 
                        res.getBoolean(SQLConstants.HASTYPE_COL));

                memoryCache.put(marketGroup.getGroupID(), marketGroup);
                addMarketGroupChild(res.getInt(SQLConstants.PARENTGRPID_COL), marketGroupID);
                // Now we have to query the groups children
                if (marketGroup.isItemContainer()) {
                    itemContainerParents.add(marketGroup);
                } else {
                    groupContainerParents.add(marketGroup);
                }
            }
            res.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }

        ItemDAO.getInstance().initMarketGroupChildren(itemContainerParents);
        if (groupContainerParents.size() != 0) {
            initMarketGroupChildren(groupContainerParents);
        }
    }

}
