package org.jevemon.misc.util;



public class SQLConstants {
	
	public static final String EVE_DATABASE = "jdbc:sqlite:db/eve-online.db";
	public static final String USER_DATABASE = "jdbc:sqlite:db/user-session.db";
	public static final String USER_DATABASE_FILE = "db/user-session.db";
	
	
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

	// Queries
	public static final String QUERY_TYPES_BY_ID = "select * from invTypes where typeID in (?)";
	public static final String QUERY_MARKETGRP_BY_ID = "select * from invMarketGroups where marketGroupID in (?)";
	public static final String QUERY_MARKETGRP_BY_ID_WITHCHILDREN
		= "select mk1.*,mk2.marketGroupID as " + CHILDID_COL + " from invMarketGroups mk1,invMarketGroups mk2 where mk1.marketGroupID in (?) and mk1.marketGroupID=mk2.parentGroupID"
		  + " union " +
		  "select mk.*,t.typeID as " + CHILDID_COL + " from invMarketGroups mk,invTypes t where mk.marketGroupID in (?) and mk.marketGroupID=t.marketGroupID" ;

	/*
	 * User database queries
	 */
	
	// Columns name
	public static final String CHARACTERID_COL = "characterID";
	public static final String CHARACTERNAME_COL = "characterName";
	public static final String USERID_COL = "userID";
	public static final String APIKEY_COL = "apiKey";
	
	// Queries
	public static final String QUERY_MONITORED_CHARACTERS = "select * from monitoredCharacters";
	public static final String ADD_MONITORED_CHARACTER = "INSERT IN " +
				"monitoredCharacters(characterID,characterName,userID,apiKey) VALUES (?,?,?,?)";
	public static final String CREATE_USER_DATABASE = 
									"CREATE TABLE monitoredCharacters " +
									"(" +
										"characterID int NOT NULL UNIQUE,"+
										"characterName nvarchar(256),"+
										"userID int,"+
										"apiKey nvarchar(256),"+
									
										"CONSTRAINT characterID_PK PRIMARY KEY (characterID)"+
									")";


}
