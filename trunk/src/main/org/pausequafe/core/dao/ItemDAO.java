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

import static org.pausequafe.misc.util.SQLConstants.ACTIVITYID_COL;
import static org.pausequafe.misc.util.SQLConstants.ATTRIBUTEID_COL;
import static org.pausequafe.misc.util.SQLConstants.ATTRIBUTE_CATEGORY_COL;
import static org.pausequafe.misc.util.SQLConstants.ATTRIBUTE_NAME_COL;
import static org.pausequafe.misc.util.SQLConstants.ATTRIBUTE_VALUE_COL;
import static org.pausequafe.misc.util.SQLConstants.BASEPRICE_COL;
import static org.pausequafe.misc.util.SQLConstants.BLUEPRINT_CATID;
import static org.pausequafe.misc.util.SQLConstants.CAPACITY_COL;
import static org.pausequafe.misc.util.SQLConstants.CATEGORYID_COL;
import static org.pausequafe.misc.util.SQLConstants.COPYTIME_COL;
import static org.pausequafe.misc.util.SQLConstants.DAMAGEPERJOB_COL;
import static org.pausequafe.misc.util.SQLConstants.DESCRIPTION_COL;
import static org.pausequafe.misc.util.SQLConstants.EVE_DATABASE;
import static org.pausequafe.misc.util.SQLConstants.EVE_DATABASE_FILE;
import static org.pausequafe.misc.util.SQLConstants.ICON_COL;
import static org.pausequafe.misc.util.SQLConstants.INVENTIONTIME_COL;
import static org.pausequafe.misc.util.SQLConstants.MARKETGRPID_COL;
import static org.pausequafe.misc.util.SQLConstants.MASS_COL;
import static org.pausequafe.misc.util.SQLConstants.MAXRUNS_COL;
import static org.pausequafe.misc.util.SQLConstants.METAGROUPID_COL;
import static org.pausequafe.misc.util.SQLConstants.METALEVEL_ATTID;
import static org.pausequafe.misc.util.SQLConstants.METIME_COL;
import static org.pausequafe.misc.util.SQLConstants.PETIME_COL;
import static org.pausequafe.misc.util.SQLConstants.PORTIONSIZE_COL;
import static org.pausequafe.misc.util.SQLConstants.PRODTIME_COL;
import static org.pausequafe.misc.util.SQLConstants.PRODUCTTYPEID_COL;
import static org.pausequafe.misc.util.SQLConstants.QUANTITY_COL;
import static org.pausequafe.misc.util.SQLConstants.QUERY_BLUEPRINTS_SIMPLE;
import static org.pausequafe.misc.util.SQLConstants.QUERY_BLUEPRINT_DETAILED;
import static org.pausequafe.misc.util.SQLConstants.QUERY_ITEM_DETAILS_BY_ID;
import static org.pausequafe.misc.util.SQLConstants.QUERY_PREREQUISITES_BY_ID;
import static org.pausequafe.misc.util.SQLConstants.QUERY_PREREQUISITES_BY_PARENT;
import static org.pausequafe.misc.util.SQLConstants.QUERY_TYPES_BY_ID;
import static org.pausequafe.misc.util.SQLConstants.QUERY_TYPE_BY_PARENT;
import static org.pausequafe.misc.util.SQLConstants.QUERY_TYPE_NAME_BY_ID;
import static org.pausequafe.misc.util.SQLConstants.RADIUS_COL;
import static org.pausequafe.misc.util.SQLConstants.RANK_ATTID;
import static org.pausequafe.misc.util.SQLConstants.REQTYPEID_COL;
import static org.pausequafe.misc.util.SQLConstants.SKILLID_COL;
import static org.pausequafe.misc.util.SQLConstants.SKILLLEVEL_COL;
import static org.pausequafe.misc.util.SQLConstants.SKILL_CATID;
import static org.pausequafe.misc.util.SQLConstants.TECHLEVEL_COL;
import static org.pausequafe.misc.util.SQLConstants.TYPEID_COL;
import static org.pausequafe.misc.util.SQLConstants.TYPENAME_COL;
import static org.pausequafe.misc.util.SQLConstants.UNITID_COL;
import static org.pausequafe.misc.util.SQLConstants.UNIT_COL;
import static org.pausequafe.misc.util.SQLConstants.VOLUME_COL;
import static org.pausequafe.misc.util.SQLConstants.WASTE_COL;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pausequafe.data.item.BPActivity;
import org.pausequafe.data.item.BPRequiredMaterial;
import org.pausequafe.data.item.Blueprint;
import org.pausequafe.data.item.BlueprintDetailed;
import org.pausequafe.data.item.Item;
import org.pausequafe.data.item.ItemAttribute;
import org.pausequafe.data.item.ItemDetailed;
import org.pausequafe.data.item.MarketGroup;
import org.pausequafe.data.item.PreRequisite;
import org.pausequafe.data.item.Skill;
import org.pausequafe.misc.exceptions.PQEveDatabaseCorrupted;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

/**
 * A DAO that creates Item from the eve database
 * 
 * @author Gobi
 */
public class ItemDAO extends AbstractSqlDAO {

    // ////////////////////
    // protected fields //
    // ////////////////////
    private static ItemDAO instance;
    public final HashMap<Integer, Item> memoryCache = new HashMap<Integer, Item>();

    // ///////////////
    // constructor //
    // ///////////////
    private ItemDAO() {
        super();
    }

    public static ItemDAO getInstance() {
        if (instance == null) {
            instance = new ItemDAO();
        }
        return instance;
    }

    // //////////////////
    // public methods //
    // //////////////////

    public Item findItemById(int typeIDrequired) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {

        Item result = null;
        List<Integer> list = new ArrayList<Integer>();
        list.add(typeIDrequired);

        List<Item> listResult = findItemsById(list);

        if (!listResult.isEmpty()) {
            result = listResult.get(0);
        }
        return result;
    }

    public List<Item> findItemsById(List<Integer> list) throws PQSQLDriverNotFoundException,
            PQEveDatabaseCorrupted, PQUserDatabaseFileCorrupted {
        ArrayList<Item> result = new ArrayList<Item>();
        List<Integer> toBeQueried = new ArrayList<Integer>();

        // get items from cache
        Item anItem;
        for (Integer anItemIndex : list) {
            anItem = memoryCache.get(anItemIndex);
            if (anItem == null) {
                toBeQueried.add(anItemIndex);
            } else {
                result.add(anItem);
            }
        }

        // get missing items from database
        if (!toBeQueried.isEmpty()) {
            result.addAll(queryItemsById(toBeQueried));
        }

        return result;
    }

    public String getItemName(int typeID) throws PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted, PQEveDatabaseCorrupted {
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);
        String name = null;
        try {
            ResultSet res = stat.executeQuery(QUERY_TYPE_NAME_BY_ID.replace("?", String
                    .valueOf(typeID)));
            if (res.next()) {
                name = res.getString(TYPENAME_COL);
            }
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }

        return name;
    }

    public ItemDetailed getItemDetails(Item baseItem) throws PQUserDatabaseFileCorrupted,
            PQEveDatabaseCorrupted, PQSQLDriverNotFoundException {

        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);

        String typeIDrequired = String.valueOf(baseItem.getTypeID());
        String query = QUERY_ITEM_DETAILS_BY_ID.replace("?", typeIDrequired);
        // System.out.println(query); // for convenience : uncomment to see DB queries

        ItemDetailed askedItem = null;

        try {
            ResultSet res = stat.executeQuery(query);
            while (res.next()) {
                if (askedItem == null) {
                    String description = res.getString(DESCRIPTION_COL);
                    description = description.replaceAll("\n", "<br>").replaceAll("\t", "<br>");

                    askedItem = new ItemDetailed(baseItem, res.getString(ICON_COL), description,
                            res.getDouble(BASEPRICE_COL), res.getDouble(RADIUS_COL), res
                                    .getDouble(MASS_COL), res.getDouble(VOLUME_COL), res
                                    .getDouble(CAPACITY_COL), res.getInt(PORTIONSIZE_COL));
                }

                int attributeID = res.getInt(ATTRIBUTEID_COL);

                String attributeName = res.getString(ATTRIBUTE_NAME_COL);
                String attributeCategory = res.getString(ATTRIBUTE_CATEGORY_COL);
                double attributeValue = res.getDouble(ATTRIBUTE_VALUE_COL);
                String unit = res.getString(UNIT_COL);
                int unitID = res.getInt(UNITID_COL);
                ItemAttribute attribute = new ItemAttribute(attributeID, attributeName,
                        attributeCategory, attributeValue, unit, unitID);
                askedItem.addAttribute(attribute);
            }
            res.close();

        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }
        return askedItem;
    }

    // ///////////////////
    // private methods //
    // ///////////////////

    private List<Item> queryItemsById(List<Integer> toBeQueried)
            throws PQSQLDriverNotFoundException, PQEveDatabaseCorrupted,
            PQUserDatabaseFileCorrupted {
        List<Item> result = new ArrayList<Item>();

        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);

        String inClause = buildInClause(toBeQueried);
        String query = QUERY_TYPES_BY_ID.replace("?", inClause);
        String query2 = QUERY_PREREQUISITES_BY_ID.replace("?", inClause);
        // System.out.println(query); // for convenience : uncomment to see DB queries

        try {
            ResultSet res = stat.executeQuery(query);
            Item newItem = null;
            while (res.next()) {
                int itemId = res.getInt(TYPEID_COL);
                int categoryId = res.getInt(CATEGORYID_COL);
                newItem = memoryCache.get(itemId);
                if (newItem == null) {
                    if (categoryId == SKILL_CATID) {
                        newItem = new Skill(itemId, res.getString(TYPENAME_COL));
                    } else {
                        newItem = new Item(itemId, res.getString(TYPENAME_COL), res
                                .getInt(METAGROUPID_COL), categoryId);
                    }

                    memoryCache.put(newItem.getTypeID(), newItem);
                    result.add(newItem);
                }

                int attributeID = res.getInt(ATTRIBUTEID_COL);

                switch (attributeID) {
                case METALEVEL_ATTID:
                    newItem.setMetaLevel(res.getInt(ATTRIBUTE_VALUE_COL));
                    break;
                case RANK_ATTID:
                    if (Skill.class.isInstance(newItem)) {
                        ((Skill) newItem).setRank(res.getInt(ATTRIBUTE_VALUE_COL));
                    }
                    break;
                default:
                    break;
                }

            }
            res.close();

            ResultSet res2 = stat.executeQuery(query2);
            while (res2.next()) {
                Integer itemId = res2.getInt(TYPEID_COL);
                newItem = memoryCache.get(itemId);
                PreRequisite p = new PreRequisite(res2.getInt(SKILLID_COL), res2
                        .getInt(SKILLLEVEL_COL));
                newItem.addPreRequisite(p);
            }
            res2.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }
        return result;
    }

    /**
     * Initialize children for market groups passed as parameters
     */
    public void initMarketGroupChildren(List<MarketGroup> parents)
            throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted,
            PQEveDatabaseCorrupted {

        if (parents == null || parents.size() == 0) {
            return;
        }

        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);

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

        String query = QUERY_TYPE_BY_PARENT.replace("?", inClause);
        String query2 = QUERY_PREREQUISITES_BY_PARENT.replace("?", inClause);
        // System.out.println(query); // for convenience : uncomment to see DB queries

        try {
            ResultSet res = stat.executeQuery(query);
            Item newItem = null;
            while (res.next()) {
                Integer itemId = res.getInt(TYPEID_COL);
                int categoryId = res.getInt(CATEGORYID_COL);
                newItem = memoryCache.get(itemId);
                if (newItem == null) {
                    if (categoryId == SKILL_CATID) {
                        newItem = new Skill(itemId, res.getString(TYPENAME_COL));
                    } else {
                        newItem = new Item(itemId, res.getString(TYPENAME_COL), res
                                .getInt(METAGROUPID_COL), categoryId);
                    }

                    memoryCache.put(newItem.getTypeID(), newItem);
                    MarketGroupDAO.getInstance().addMarketGroupChild(res.getInt(MARKETGRPID_COL),
                            itemId);
                }

                int attributeID = res.getInt(ATTRIBUTEID_COL);

                switch (attributeID) {
                case METALEVEL_ATTID:
                    newItem.setMetaLevel(res.getInt(ATTRIBUTE_VALUE_COL));
                    break;
                case RANK_ATTID:
                    if (Skill.class.isInstance(newItem)) {
                        ((Skill) newItem).setRank(res.getInt(ATTRIBUTE_VALUE_COL));
                    }
                    break;
                default:
                    break;
                }

            }
            res.close();

            ResultSet res2 = stat.executeQuery(query2);
            while (res2.next()) {
                Integer itemId = res.getInt(TYPEID_COL);
                newItem = memoryCache.get(itemId);
                PreRequisite p = new PreRequisite(res2.getInt(SKILLID_COL), res2
                        .getInt(SKILLLEVEL_COL));
                newItem.addPreRequisite(p);
            }
            res2.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }
    }

    /**
     * Initialize the blueprints from their parent marketgroups. Only retrieves the required skills
     * for the activities.
     * 
     * @param parents
     *            the market group list to init
     * @throws PQEveDatabaseCorrupted
     * @throws PQSQLDriverNotFoundException
     * @throws PQUserDatabaseFileCorrupted
     */
    public void initBlueprintGroupChildren(List<MarketGroup> parents)
            throws PQEveDatabaseCorrupted, PQSQLDriverNotFoundException,
            PQUserDatabaseFileCorrupted {
        if (parents == null || parents.size() == 0) {
            return;
        }
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);

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

        String query = QUERY_BLUEPRINTS_SIMPLE.replace("?", inClause);

        // System.out.println(query); // for convenience : uncomment to see DB queries

        try {
            ResultSet res = stat.executeQuery(query);
            Item newBlueprint = null;
            while (res.next()) {
                Integer itemId = res.getInt(TYPEID_COL);
                newBlueprint = (Blueprint) memoryCache.get(itemId);
                if (newBlueprint == null) {
                    String typeName = res.getString(TYPENAME_COL);
                    int metaGroupID = res.getInt(METAGROUPID_COL);
                    newBlueprint = new Blueprint(itemId, typeName, metaGroupID, BLUEPRINT_CATID);
                    memoryCache.put(newBlueprint.getTypeID(), newBlueprint);
                    MarketGroupDAO.getInstance().addMarketGroupChild(res.getInt(MARKETGRPID_COL),
                            itemId);
                }
                int activityID = res.getInt(ACTIVITYID_COL);
                BPActivity newActivity = ((Blueprint) newBlueprint).getActivities().get(activityID);

                if (newActivity == null) {
                    newActivity = new BPActivity(activityID);
                    ((Blueprint) newBlueprint).getActivities().put(activityID, newActivity);
                }
                int requiredSkillID = res.getInt(REQTYPEID_COL);
                int level = res.getInt(QUANTITY_COL);
                PreRequisite preReq = new PreRequisite(requiredSkillID, level);
                newActivity.getPrereqs().add(preReq);
            }
            res.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        }
    }

    public BlueprintDetailed getBlueprintDetails(Blueprint blueprint)
            throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted,
            PQEveDatabaseCorrupted {
        if (blueprint == null) {
            return null;
        }
        File db = new File(EVE_DATABASE_FILE);
        if (!db.exists()) {
            throw new PQEveDatabaseCorrupted(Constants.EVE_DB_NOT_FOUND_ERROR);
        }
        initConnection(EVE_DATABASE);

        BlueprintDetailed bpDetailed = null;
        String bpID = String.valueOf(blueprint.getTypeID());
        String query = QUERY_BLUEPRINT_DETAILED.replace("?", bpID);
        // System.out.println(query); // for convenience : uncomment to see DB queries

        try {
            ResultSet res = stat.executeQuery(query);
            while (res.next()) {
                if (bpDetailed == null) {
                    bpDetailed = new BlueprintDetailed(blueprint);
                    bpDetailed.setIcon(res.getString(ICON_COL));
                    bpDetailed.setProductTypeID(res.getInt(PRODUCTTYPEID_COL));
                    bpDetailed.setTechLevel(res.getInt(TECHLEVEL_COL));
                    bpDetailed.setProductionTime(res.getLong(PRODTIME_COL) * Constants.SECOND);
                    bpDetailed.setResearchMETime(res.getLong(METIME_COL) * Constants.SECOND);
                    bpDetailed.setResearchPETime(res.getLong(PETIME_COL) * Constants.SECOND);
                    bpDetailed.setInventionTime(res.getLong(INVENTIONTIME_COL) * Constants.SECOND);
                    bpDetailed.setCopyTime(res.getLong(COPYTIME_COL) * Constants.SECOND);
                    bpDetailed.setWasteFactor(res.getInt(WASTE_COL));
                    bpDetailed.setBatchSize(res.getInt(PORTIONSIZE_COL));
                    bpDetailed.setBasePrice(res.getDouble(BASEPRICE_COL));
                    bpDetailed.setMaxRuns(res.getInt(MAXRUNS_COL));
                }
                int activityID = res.getInt(ACTIVITYID_COL);
                BPActivity newActivity = bpDetailed.getActivities().get(activityID);
                if (newActivity == null) {
                    newActivity = new BPActivity(activityID);
                    bpDetailed.getActivities().put(activityID, newActivity);
                }

                int requiredTypeID = res.getInt(REQTYPEID_COL);
                int quantity = res.getInt(QUANTITY_COL);
                double damagePerJob = res.getDouble(DAMAGEPERJOB_COL);
                BPRequiredMaterial material = new BPRequiredMaterial(requiredTypeID, quantity,
                        damagePerJob);
                newActivity.getMaterials().add(material);
            }
            res.close();
        } catch (SQLException e) {
            throw new PQEveDatabaseCorrupted(e);
        } finally {
            closeConnection();
        }

        return bpDetailed;
    }
}
