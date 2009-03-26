package org.pausequafe.misc.util;



public class SQLConstants {
	
	public static final String EVE_DATABASE = "jdbc:sqlite:resources/eve-online.db";
	public static final String EVE_DATABASE_FILE = "resources/eve-online.db";;
	public static final String USER_DATABASE = "jdbc:sqlite:resources/user.db";
	public static final String USER_DATABASE_FILE = "resources/user.db";
	
	
	/*
	 * EVE database queries
	 */
	
	// Columns name
	public static final String TYPEID_COL = "typeID";
	public static final String TYPENAME_COL = "typeName";
	public static final String DESCRIPTION_COL = "description";
	public static final String MARKETGRPID_COL = "marketGroupID";
	public static final String MARKETGRPNAME_COL = "marketGroupName";
	public static final String HASTYPE_COL = "hasTypes";
	public static final String CHILDID_COL = "childID";
	public static final String BASEPRICE_COL = "basePrice";
	public static final String GRAPHICID_COL = "graphicID";
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
	
	// attributes ids
	public static final int REQUIRED_SKILL_1_ATTID = 182;
	public static final int REQUIRED_SKILL_2_ATTID = 183;
	public static final int REQUIRED_SKILL_3_ATTID = 184;
	public static final int REQUIRED_SKILL_1_LEVEL_ATTID = 277;
	public static final int REQUIRED_SKILL_2_LEVEL_ATTID = 278;
	public static final int REQUIRED_SKILL_3_LEVEL_ATTID = 279;
	public static final int METALEVEL_ATTID = 633;
	
	// unit IDs
	public static final int DURATION_MILLISECS_UNITID = 101;
	public static final int DAMAGE_RESISTANCE_UNITID = 108;
	public static final int PERCENT_MULTIPLIER_UNITID = 109;
	public static final int SIZECLASS_UNITID = 117;

	// Queries
//	public static final String QUERY_TYPES_BY_ID = "select * from invTypes where typeID in (?)";
	public static final String QUERY_MARKETGRP_BY_ID = "select * from invMarketGroups where marketGroupID in (?)";
	public static final String QUERY_MARKETGRP_BY_ID_WITHCHILDREN
		= "select mk1.*,mk2.marketGroupID as " + CHILDID_COL + " from invMarketGroups mk1,invMarketGroups mk2 where mk1.marketGroupID in (?) and mk1.marketGroupID=mk2.parentGroupID"
		  + " union " +
		  "select mk.*,t.typeID as " + CHILDID_COL + " from invMarketGroups mk,invTypes t where mk.marketGroupID in (?) and mk.marketGroupID=t.marketGroupID" ;

	public static final String QUERY_TYPES_BY_ID =	"SELECT t.typeID,t.typeName,ac.categoryName,at.attributeID,at.attributeName,IFNULL(a.valueInt, a.valueFloat) AS value, u.unitID, u.displayName AS unit,t.radius,t.description,t.mass,t.volume,t.capacity,t.basePrice " +
													"FROM invTypes t,dgmTypeAttributes a,dgmAttributeTypes at,dgmAttributeCategories ac,eveUnits u " +
													"WHERE t.typeID in (?) AND t.typeID = a.typeID AND a.attributeID = at.attributeID AND at.categoryID = ac.categoryID AND ac.categoryName != 'NULL' AND at.unitID = u.unitID " +
													"ORDER BY ac.categoryName ";
	
	public static final String QUERY_METAGROUP_BY_TYPEID = "SELECT metaGroupID FROM invMetaTypes WHERE typeID in (?)";
	public static final String QUERY_ICON_BY_TYPEID = "SELECT g.icon FROM eveGraphics g, invTypes t WHERE t.graphicID = g.graphicID AND t.typeID IN (?)";
	
	
	
	
	/*
	 * User database queries
	 */
	
	// Columns name
	public static final String CHARACTERID_COL = "characterID";
	public static final String CHARACTERNAME_COL = "characterName";
	public static final String USERID_COL = "userID";
	public static final String APIKEY_COL = "apiKey";
	
	// Queries
	public static final String QUERY_MONITORED_CHARACTERS = "SELECT * FROM monitoredCharacters WHERE isMonitored=1";
	public static final String QUERY_DISTINCT_API = "SELECT DISTINCT userID, apiKey FROM monitoredCharacters";
	public static final String ADD_MONITORED_CHARACTER = "INSERT INTO " +
				"monitoredCharacters(characterID,characterName,userID,apiKey,isMonitored) VALUES (?,?,?,?,1)";
	public static final String REMOVE_MONITORED_CHARACTER = "UPDATE monitoredCharacters SET isMonitored=0 WHERE characterID=?";
	public static final String UPDATE_MONITORED_CHARACTER = "UPDATE monitoredCharacters SET isMonitored=1 WHERE characterID=?";
	public static final String CREATE_USER_DATABASE = 
									"CREATE TABLE monitoredCharacters " +
									"(" +
										"characterID int NOT NULL UNIQUE,"+
										"characterName nvarchar(256),"+
										"userID int,"+
										"apiKey nvarchar(256)," +
										"isMonitored bit," +
									
										"CONSTRAINT characterID_PK PRIMARY KEY (characterID)"+
									")";
	public static final String CHARACTER_EXISTS = "SELECT isMonitored FROM monitoredCharacters WHERE characterID=?";



}
