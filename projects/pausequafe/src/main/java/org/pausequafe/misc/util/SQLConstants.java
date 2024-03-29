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

package org.pausequafe.misc.util;

public class SQLConstants {

    public static final String EVE_DATABASE = "jdbc:sqlite:resources/eve-online.db";
    public static final String EVE_DATABASE_FILE = "resources/eve-online.db";;
    public static final String USER_DATABASE = "jdbc:sqlite:settings/user.db";
    public static final String USER_DATABASE_FILE = "settings/user.db";

    /*
     * EVE database queries
     */
    public static final double WARP_SPEED_BASE = 3.0;

    // Columns name
    public static final String TYPEID_COL = "typeID";
    public static final String BPTYPEID_COL = "blueprintTypeID";
    public static final String TYPENAME_COL = "typeName";
    public static final String GROUPNAME_COL = "groupName";
    public static final String DESCRIPTION_COL = "description";
    public static final String MARKETGRPID_COL = "marketGroupID";
    public static final String MARKETGRPNAME_COL = "marketGroupName";
    public static final String HASTYPE_COL = "hasTypes";
    public static final String CHILDID_COL = "childID";
    public static final String BASEPRICE_COL = "basePrice";
    public static final String ICON_COL = "icon";
    public static final String METAGROUPID_COL = "metaGroupID";
    public static final String MASS_COL = "mass";
    public static final String RADIUS_COL = "radius";
    public static final String VOLUME_COL = "volume";
    public static final String CAPACITY_COL = "capacity";
    public static final String ATTRIBUTE_NAME_COL = "attributeName";
    public static final String ATTRIBUTE_CATEGORY_COL = "categoryName";
    public static final String ATTRIBUTE_VALUE_COL = "value";
    public static final String ATTRIBUTEID_COL = "attributeID";
    public static final String UNIT_COL = "unit";
    public static final String UNITID_COL = "unitID";
    public static final String CATEGORYNAME_COL = "categoryName";
    public static final String CATEGORYID_COL = "categoryID";
    public static final String PARENTGRPID_COL = "parentGroupID";
    public static final String SKILLID_COL = "skillID";
    public static final String SKILLLEVEL_COL = "skillLevel";
    public static final String PORTIONSIZE_COL = "portionSize";
    public static final String PRODUCTTYPEID_COL = "productTypeID";
    public static final String TECHLEVEL_COL = "techLevel";
    public static final String PRODTIME_COL = "productionTime";
    public static final String METIME_COL = "researchMaterialTime";
    public static final String PETIME_COL = "researchProductivityTime";
    public static final String INVENTIONTIME_COL = "researchTechTime";
    public static final String COPYTIME_COL = "researchCopyTime";
    public static final String WASTE_COL = "wasteFactor";
    public static final String ACTIVITYID_COL = "activityID";
    public static final String REQTYPEID_COL = "requiredTypeID";
    public static final String QUANTITY_COL = "quantity";
    public static final String DAMAGEPERJOB_COL = "damagePerJob";
    public static final String MAXRUNS_COL = "maxProductionLimit";
    public static final String RECYCLE_COL = "recycle";

    // item categories
    public static final int BLUEPRINT_CATID = 9;
    public static final int SKILL_CATID = 16;

    // marketgroup ids
    public static final int BLUEPRINTS_MKTGRPID = 2;
    public static final int SKILLS_MKTGRPID = 150;
    public static final int SHIPS_MKTGRPID = 4;
    public static final int ITEMS_MKTGRPID = 9;

    // attributes ids
    public static final int METALEVEL_ATTID = 633;
    public static final int RANK_ATTID = 275;
    public static final int PREREQUISITE_ATTRIBUTE_CATEGORY = 8;
    public static final int MAINCOLOR_ATTID = 124;
    // armor
    public static final int ARMOR_HP_ATTID = 265;
    public static final int ARMOR_EM_RESIST_ATTID = 267;
    public static final int ARMOR_EXPLO_RESIST_ATTID = 268;
    public static final int ARMOR_KINE_RESIST_ATTID = 269;
    public static final int ARMOR_THERM_RESIST_ATTID = 270;
    // capa
    public static final int CAPACITOR_CAPACITY_ATTID = 482;
    public static final int CAPACITOR_RECHARGE_TIME_ATTID = 55;
    // fitting
    public static final int CPU_OUTPUT_ATTID = 48;
    public static final int GUN_HARDPOINTS_ATTID = 102;
    public static final int LAUNCHER_HARDPOINTS_ATTID = 101;
    public static final int RIG_SLOTS_ATTID = 1154;
    public static final int CALIBRATION_PONTS_ATTID = 1132;
    public static final int LOW_SLOTS_ATTID = 12;
    public static final int MED_SLOTS_ATTID = 13;
    public static final int HI_SLOTS_ATTID = 14;
    public static final int POWERGRID_OUTPUT_ATTID = 11;
    // shield
    public static final int SHIELD_HP_ATTID = 263;
    public static final int SHIELD_RECHARGE_TIME_ATTID = 479;
    public static final int SHIELD_EM_RESIST_ATTID = 271;
    public static final int SHIELD_EXPLO_RESIST_ATTID = 272;
    public static final int SHIELD_KINE_RESIST_ATTID = 273;
    public static final int SHIELD_THERM_RESIST_ATTID = 274;
    // structure
    public static final int STRUCT_HP_ATTID = 9;
    public static final int STRUCT_EM_RESIST_ATTID = 113;
    public static final int STRUCT_EXPLO_RESIST_ATTID = 111;
    public static final int STRUCT_KINE_RESIST_ATTID = 109;
    public static final int STRUCT_THERM_RESIST_ATTID = 110;
    public static final int RADIUS_ATTID = 162;
    public static final int MASS_ATTID = 4;
    public static final int VOLUME_ATTID = 161;
    public static final int CAPACITY_ATTID = 38;
    // drones
    public static final int DRONEBAY_CAPACITY_ATTID = 283;
    public static final int DRONE_BANDWIDTH = 1271;
    // propulsion
    public static final int MAX_VELOCITY_ATTID = 37;
    public static final int AGILITY_ATTID = 70;
    public static final int WARP_SPEED_MULTUPLIER_ATTID = 600;
    // targeting
    public static final int TARGETING_RANGE_ATTID = 76;
    public static final int TARGET_COUNT_ATTID = 192;
    public static final int SCAN_RESOLUTION_ATTID = 564;
    public static final int SIGNATURE_RADIUS_ATTID = 552;
    public static final int SENSOR_RADAR_ATTID = 208;
    public static final int SENSOR_LADAR_ATTID = 209;
    public static final int SENSOR_MAGNETO_ATTID = 210;
    public static final int SENSOR_GRAVI_ATTID = 211;

    // unit IDs
    public static final int DURATION_MILLISECS_UNITID = 101;
    public static final int DAMAGE_RESISTANCE_UNITID = 108;
    public static final int PERCENT_MULTIPLIER_UNITID = 109;
    public static final int GROUPID_UNITID = 115;
    public static final int TYPEID_UNITID = 116;
    public static final int SIZECLASS_UNITID = 117;
    public static final int ATTRIBUTEID_UNITID = 119;

    // Queries
    // public static final String QUERY_TYPES_BY_ID =
    // "select * from invTypes where typeID in (?)";
    public static final String QUERY_MARKETGRP_BY_ID = "select " + MARKETGRPID_COL + ","
            + MARKETGRPNAME_COL + "," + HASTYPE_COL
            + " from invMarketGroups where marketGroupID in (?)";

    public static final String QUERY_MARKETGRP_BY_PARENT = "select " + MARKETGRPID_COL + ","
            + MARKETGRPNAME_COL + "," + HASTYPE_COL + "," + PARENTGRPID_COL
            + " from invMarketGroups where parentGroupID in (?)";

    public static final String QUERY_MARKETGRP_BY_ID_WITHCHILDREN = "select mk1." + MARKETGRPID_COL
            + " as " + MARKETGRPID_COL + ",mk1." + MARKETGRPNAME_COL + " as " + MARKETGRPNAME_COL
            + ",mk1." + HASTYPE_COL + " as " + HASTYPE_COL + ",mk2.marketGroupID as " + CHILDID_COL
            + " from invMarketGroups mk1,invMarketGroups mk2 "
            + "where mk1.marketGroupID in (?) and mk1.marketGroupID=mk2.parentGroupID" + " union "
            + "select mk1." + MARKETGRPID_COL + " as " + MARKETGRPID_COL + ",mk1."
            + MARKETGRPNAME_COL + " as " + MARKETGRPNAME_COL + ",mk1." + HASTYPE_COL + " as "
            + HASTYPE_COL + ",t.typeID as " + CHILDID_COL + " from invMarketGroups mk1,invTypes t "
            + "where mk1.marketGroupID in (?) and mk1.marketGroupID=t.marketGroupID";

    public static final String QUERY_TYPES_BY_ID = "SELECT t.typeID,t.metaGroupID,t.typeName,"
            + "g.categoryID,at.attributeID,at.attributeName,"
            + "IFNULL(a.valueInt, a.valueFloat) AS value  "
            + "FROM invTypes t, dgmTypeAttributes a, dgmAttributeTypes at, invGroups g "
            + "WHERE t.typeID in (?) " + "AND t.typeID = a.typeID "
            + "AND a.attributeID = at.attributeID " + "AND t.groupID=g.groupID "
            + "AND at.attributeID in (" + METALEVEL_ATTID + "," + RANK_ATTID + ") ";

    public static final String QUERY_PREREQUISITES_BY_ID = "SELECT t.typeID,"
            + "IFNULL(ta1.valueInt, ta1.valueFloat) AS skillID,"
            + "IFNULL(ta2.valueInt, ta2.valueFloat) AS skillLevel "
            + "FROM INVTYPES t,DGMTYPEATTRIBUTES ta1,DGMTYPEATTRIBUTES ta2,"
            + "DGMATTRIBUTETYPES a1 ,DGMATTRIBUTETYPES a2 " + "WHERE t.marketGroupID in (?) "
            + "AND t.typeID=ta1.typeID " + "AND t.typeID=ta2.typeID "
            + "AND ta1.attributeID=a1.attributeID " + "AND ta2.attributeID=a2.attributeID "
            + "AND a1.categoryID=" + PREREQUISITE_ATTRIBUTE_CATEGORY + " "
            + "AND a2.attributeName like '%Level' "
            + "AND a2.attributeName like (a1.attributeName || '%') "
            + "AND a2.attributeName != a1.attributeName " + "order by a1.attributeName";

    public static final String QUERY_TYPE_BY_PARENT = "SELECT t.typeID,t.marketGroupID,"
            + "t.metaGroupID,t.typeName,g.categoryID,at.attributeID,"
            + "at.attributeName,IFNULL(a.valueInt, a.valueFloat) AS value  "
            + "FROM invTypes t, dgmTypeAttributes a, " + "dgmAttributeTypes at, invGroups g "
            + "WHERE t.marketGroupID in (?) " + "AND t.typeID = a.typeID "
            + "AND a.attributeID = at.attributeID " + "AND t.groupID=g.groupID "
            + "AND at.attributeID in (" + METALEVEL_ATTID + "," + RANK_ATTID + ") ";

    public static final String QUERY_PREREQUISITES_BY_PARENT = "SELECT t.typeID,"
            + "IFNULL(ta1.valueInt, ta1.valueFloat) AS skillID,"
            + "IFNULL(ta2.valueInt, ta2.valueFloat) AS skillLevel "
            + "FROM INVTYPES t,DGMTYPEATTRIBUTES ta1,DGMTYPEATTRIBUTES ta2,"
            + "DGMATTRIBUTETYPES a1 ,DGMATTRIBUTETYPES a2 " + "WHERE t.marketGroupID in (?) "
            + "AND t.typeID=ta1.typeID " + "AND t.typeID=ta2.typeID "
            + "AND ta1.attributeID=a1.attributeID " + "AND ta2.attributeID=a2.attributeID "
            + "AND a1.categoryID=" + PREREQUISITE_ATTRIBUTE_CATEGORY + " "
            + "AND a2.attributeName like '%Level' "
            + "AND a2.attributeName like (a1.attributeName || '%') "
            + "AND a2.attributeName != a1.attributeName " + "order by a1.attributeName";

    public static final String QUERY_ITEM_DETAILS_BY_ID = "SELECT t.typeID,t.icon,t.metaGroupID,"
            + "t.typeName,t.portionSize,ac.categoryName,a.attributeID,a.attributeName,"
            + "IFNULL(ta.valueInt, ta.valueFloat) AS value, u.unitID, u.displayName AS unit,"
            + "t.radius,t.description,t.mass,t.volume,t.capacity,t.basePrice "
            + "FROM invTypes t,dgmTypeAttributes ta,dgmAttributeTypes a,"
            + "dgmAttributeCategories ac,eveUnits u " + "WHERE t.typeID in (?) "
            + "AND t.typeID = ta.typeID " + "AND ta.attributeID = a.attributeID "
            + "AND a.categoryID = ac.categoryID AND a.unitID = u.unitID " + "AND a.categoryID!="
            + PREREQUISITE_ATTRIBUTE_CATEGORY + " ORDER BY ac.categoryName ";

    public static final String QUERY_BLUEPRINTS_SIMPLE = "SELECT t.typeID, t.icon, t2.metaGroupID, t.typeName, t.marketGroupID, "
            + "b.productTypeID, m.activityID, m.requiredTypeID, m.quantity "
            + "FROM invTypes t, invTypes t2, invTypes t3, invBlueprintTypes b, ramBlueprintReqs m, invGroups g "
            + "WHERE t.marketGroupID IN (?) "
            + "AND b.productTypeID=t2.typeID "
            + "AND t3.typeID = m.requiredTypeID "
            + "AND t3.groupID = g.groupID "
            + "AND g.categoryID IN ("
            + SKILL_CATID
            + ") "
            + "AND t.typeID = b.blueprintTypeID "
            + "AND t.typeID = m.blueprintTypeID ";

    public static final String QUERY_BLUEPRINT_DETAILED = "SELECT t.typeID, t.icon, t2.metaGroupID, t.typeName, t.portionSize, t.basePrice, t.marketGroupID, "
            + "b.productTypeID, b.researchProductivityTime, b.researchMaterialTime, b.techLevel, g.categoryID, "
            + "b.productionTime ,b.researchCopyTime, b.researchTechTime, b.wasteFactor, b.maxProductionLimit, "
            + "m.activityID, m.requiredTypeID, m.quantity, m.damagePerJob "
            + "FROM invTypes t, invTypes t2, invTypes t3, invBlueprintTypes b, ramBlueprintReqs m, invGroups g "
            + "WHERE t.typeID IN (?) "
            + "AND b.productTypeID=t2.typeID "
            + "AND t3.typeID = m.requiredTypeID "
            + "AND t3.groupID = g.groupID "
            + "AND g.categoryID NOT IN ("
            + SKILL_CATID
            + ") "
            + "AND t.typeID = b.blueprintTypeID " + "AND t.typeID = m.blueprintTypeID ";

    public static final String QUERY_GROUP_NAME_BY_ID = "SELECT groupName FROM invGroups WHERE groupID IN (?)";
    public static final String QUERY_TYPE_NAME_BY_ID = "SELECT typeName FROM invTypes WHERE typeID IN (?)";

    /*
     * User database queries
     */

    // Columns name
    public static final String CHARACTERS_TABLE = "monitoredCharacters";
    public static final String SKILLPLANS_TABLE = "skillPlans";
    public static final String CHARACTERID_COL = "characterID";
    public static final String CHARACTERNAME_COL = "characterName";
    public static final String USERID_COL = "userID";
    public static final String APIKEY_COL = "apiKey";
    public static final String ISMONITORED_COL = "isMonitored";
    public static final String SKILLPLANID_COL = "skillPlanID";
    public static final String SKILLPLANINDEX_COL = "skillPlanIndex";
    public static final String SKILLPLANNAME_COL = "skillPlanName";

    // Queries
    public static final String CREATE_CHARACTER_TABLE = "CREATE TABLE monitoredCharacters " + "("
            + CHARACTERID_COL + " int NOT NULL UNIQUE," + CHARACTERNAME_COL + " nvarchar(256),"
            + USERID_COL + " int," + APIKEY_COL + " nvarchar(256)," + ISMONITORED_COL + " bit,"
            + "CONSTRAINT characterID_PK PRIMARY KEY (" + CHARACTERID_COL + ")" + ")";
    public static final String QUERY_MONITORED_CHARACTERS = "SELECT * FROM " + CHARACTERS_TABLE
            + " WHERE " + ISMONITORED_COL + "=1";
    public static final String QUERY_DISTINCT_API = "SELECT DISTINCT " + USERID_COL + ", "
            + APIKEY_COL + " FROM " + CHARACTERS_TABLE;
    public static final String ADD_MONITORED_CHARACTER = "INSERT INTO " + CHARACTERS_TABLE + "("
            + CHARACTERID_COL + "," + CHARACTERNAME_COL + "," + USERID_COL + "," + APIKEY_COL + ","
            + ISMONITORED_COL + ") VALUES (?,?,?,?,1)";
    public static final String REMOVE_MONITORED_CHARACTER = "UPDATE " + CHARACTERS_TABLE + " SET "
            + ISMONITORED_COL + "=0 WHERE " + CHARACTERID_COL + " IN (?)";
    public static final String UPDATE_MONITORED_CHARACTER = "UPDATE " + CHARACTERS_TABLE + " SET "
            + ISMONITORED_COL + "=1 WHERE " + CHARACTERID_COL + " IN (?)";
    public static final String UPDATE_API_KEY_FROM_USERID = "UPDATE " + CHARACTERS_TABLE + " SET "
            + APIKEY_COL + "=? WHERE " + USERID_COL + " in (?)";
    public static final String UPDATE_API_KEY_FROM_CHARID = "UPDATE " + CHARACTERS_TABLE + " SET "
            + APIKEY_COL + "=? WHERE " + CHARACTERID_COL + " in (?)";

    public static final String CREATE_SKILLPLANTABLE = "CREATE TABLE skillPlans ("
            + CHARACTERID_COL + " int NOT NULL," + SKILLPLANID_COL + " int NOT NULL UNIQUE,"
            + SKILLPLANINDEX_COL + " int NOT NULL," + SKILLPLANNAME_COL + " nvarchar(256),"
            + "CONSTRAINT skillPlanID_PK PRIMARY KEY (" + SKILLPLANID_COL + ")" + ")";
    public static final String QUERY_SKILLPLANS = "SELECT * FROM " + SKILLPLANS_TABLE + " WHERE "
            + CHARACTERID_COL + " IN (?)" + " ORDER BY " + SKILLPLANINDEX_COL;
    public static final String INSERT_SKILL_PLAN = "INSERT INTO " + SKILLPLANS_TABLE + "("
            + CHARACTERID_COL + "," + SKILLPLANID_COL + "," + SKILLPLANINDEX_COL + ","
            + SKILLPLANNAME_COL + ") VALUES (?,?,?,?)";
    public static final String QUERY_SKILLPLAN_MAXID = "SELECT MAX(skillPlanID) FROM SKILLPLANS";
    public static final String DELETE_SKILLPLANS = "DELETE FROM SKILLPLANS where skillPlanID in (?)";
    public static final String UPDATE_SKILLPLAN = "UPDATE " + SKILLPLANS_TABLE + " SET "
            + CHARACTERID_COL + "=?," + SKILLPLANINDEX_COL + "=?," + SKILLPLANNAME_COL + "=?"
            + " WHERE " + SKILLPLANID_COL + "=?";

}
