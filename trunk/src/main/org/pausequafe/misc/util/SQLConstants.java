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
	public static final String PARENTGRPID_COL = "parentGroupID";

	// attributes ids
	public static final int REQUIRED_SKILL_1_ATTID = 182;
	public static final int REQUIRED_SKILL_2_ATTID = 183;
	public static final int REQUIRED_SKILL_3_ATTID = 184;
	public static final int REQUIRED_SKILL_1_LEVEL_ATTID = 277;
	public static final int REQUIRED_SKILL_2_LEVEL_ATTID = 278;
	public static final int REQUIRED_SKILL_3_LEVEL_ATTID = 279;
	public static final int METALEVEL_ATTID = 633;
	public static final int RANK_ATTID = 275;
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
	public static final String QUERY_MARKETGRP_BY_ID_WITHCHILDREN = "select mk1."
			+ MARKETGRPID_COL
			+ " as "
			+ MARKETGRPID_COL
			+ ",mk1."
			+ MARKETGRPNAME_COL
			+ " as "
			+ MARKETGRPNAME_COL
			+ ",mk1."
			+ HASTYPE_COL
			+ " as "
			+ HASTYPE_COL
			+ ",mk2.marketGroupID as "
			+ CHILDID_COL
			+ " from invMarketGroups mk1,invMarketGroups mk2 where mk1.marketGroupID in (?) and mk1.marketGroupID=mk2.parentGroupID"
			+ " union "
			+ "select mk1."
			+ MARKETGRPID_COL
			+ " as "
			+ MARKETGRPID_COL
			+ ",mk1."
			+ MARKETGRPNAME_COL
			+ " as "
			+ MARKETGRPNAME_COL
			+ ",mk1."
			+ HASTYPE_COL
			+ " as "
			+ HASTYPE_COL
			+ ",t.typeID as "
			+ CHILDID_COL
			+ " from invMarketGroups mk1,invTypes t where mk1.marketGroupID in (?) and mk1.marketGroupID=t.marketGroupID";

	public static final String QUERY_TYPES_BY_ID = "SELECT t.typeID,t.metaGroupID,t.typeName,c.categoryName,at.attributeID,at.attributeName,IFNULL(a.valueInt, a.valueFloat) AS value  "
			+ "FROM invTypes t, dgmTypeAttributes a, dgmAttributeTypes at, invGroups g, invCategories c "
			+ "WHERE t.typeID in (?) "
			+ "AND t.typeID = a.typeID "
			+ "AND a.attributeID = at.attributeID "
			+ "AND t.groupID=g.groupID "
			+ "AND g.categoryID=c.categoryID "
			+ "AND at.attributeID in ("
			+ REQUIRED_SKILL_1_ATTID
			+ ","
			+ REQUIRED_SKILL_2_ATTID
			+ ","
			+ REQUIRED_SKILL_3_ATTID
			+ ","
			+ REQUIRED_SKILL_1_LEVEL_ATTID
			+ ","
			+ REQUIRED_SKILL_2_LEVEL_ATTID
			+ ","
			+ REQUIRED_SKILL_3_LEVEL_ATTID + "," + METALEVEL_ATTID + "," + RANK_ATTID + ") ";

	public static final String QUERY_TYPE_BY_PARENT = "SELECT t.typeID,t.marketGroupID,t.metaGroupID,t.typeName,c.categoryName,at.attributeID,at.attributeName,IFNULL(a.valueInt, a.valueFloat) AS value  "
			+ "FROM invTypes t, dgmTypeAttributes a, dgmAttributeTypes at, invGroups g, invCategories c "
			+ "WHERE t.marketGroupID in (?) "
			+ "AND t.typeID = a.typeID "
			+ "AND a.attributeID = at.attributeID "
			+ "AND t.groupID=g.groupID "
			+ "AND g.categoryID=c.categoryID "
			+ "AND at.attributeID in ("
			+ REQUIRED_SKILL_1_ATTID
			+ ","
			+ REQUIRED_SKILL_2_ATTID
			+ ","
			+ REQUIRED_SKILL_3_ATTID
			+ ","
			+ REQUIRED_SKILL_1_LEVEL_ATTID
			+ ","
			+ REQUIRED_SKILL_2_LEVEL_ATTID
			+ ","
			+ REQUIRED_SKILL_3_LEVEL_ATTID + "," + METALEVEL_ATTID + "," + RANK_ATTID + ") ";;

	public static final String QUERY_ITEM_DETAILS_BY_ID = "SELECT t.typeID,t.icon,t.metaGroupID,t.typeName,ac.categoryName,at.attributeID,at.attributeName,IFNULL(a.valueInt, a.valueFloat) AS value, u.unitID, u.displayName AS unit,t.radius,t.description,t.mass,t.volume,t.capacity,t.basePrice "
			+ "FROM invTypes t,dgmTypeAttributes a,dgmAttributeTypes at,dgmAttributeCategories ac,eveUnits u "
			+ "WHERE t.typeID in (?) AND t.typeID = a.typeID AND a.attributeID = at.attributeID AND at.categoryID = ac.categoryID AND at.unitID = u.unitID "
			+ "ORDER BY ac.categoryName ";

	public static final String QUERY_GROUP_NAME_BY_ID = "SELECT groupName FROM invGroups WHERE groupID IN (?)";
	public static final String QUERY_TYPE_NAME_BY_ID = "SELECT typeName FROM invTypes WHERE typeID IN (?)";

	/*
	 * User database queries
	 */

	// Columns name
	public static final String CHARACTERS_TABLE = "monitoredCharacters";
	public static final String SKILLPLANS_TABLE = "monitoredCharacters";
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
	+ CHARACTERID_COL + " int NOT NULL UNIQUE," 
	+ CHARACTERNAME_COL + " nvarchar(256),"
	+ USERID_COL + " int," 
	+ APIKEY_COL + " nvarchar(256)," 
	+ ISMONITORED_COL + " bit,"
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

	public static final String CREATE_SKILLPLANTABLE = "CREATE TABLE skillPlans " + "("
	+ CHARACTERID_COL + " int NOT NULL,"		
	+ SKILLPLANID_COL + " int NOT NULL UNIQUE," 
	+ SKILLPLANINDEX_COL + " int NOT NULL,"  
	+ SKILLPLANNAME_COL + " nvarchar(256)," 
	+ "CONSTRAINT skillPlanID_PK PRIMARY KEY (" + SKILLPLANID_COL + ")" + ")";
	public static final String QUERY_SKILLPLANS = "SELECT * FROM" + SKILLPLANS_TABLE 
			+ " WHERE " + CHARACTERID_COL + " IN (?)" 
			+ " ORDER BY " + SKILLPLANINDEX_COL;
	public static final String INSERT_SKILL_PLAN = "INSERT INTO " + SKILLPLANS_TABLE + "("
	+ CHARACTERID_COL + "," + SKILLPLANID_COL + "," + SKILLPLANINDEX_COL + "," + SKILLPLANNAME_COL + ") VALUES (?,?,?,?)";
	public static final String QUERY_SKILLPLAN_MAXID = "SELECT MAX(skillPlanID) FROM SKILLPLANS";
	public static final String DELETE_SKILLPLANS = "DELETE FROM SKILLPLANS where skillPlanID in (?)";
	public static final String UPDATE_SKILLPLAN = "UPDATE " + SKILLPLANS_TABLE + " SET "
			+ CHARACTERID_COL + "=?," + SKILLPLANINDEX_COL + "=?," + SKILLPLANNAME_COL + "=?"
			+ "WHERE " + SKILLPLANID_COL + "=?";
}
