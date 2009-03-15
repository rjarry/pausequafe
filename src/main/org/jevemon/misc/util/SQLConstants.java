package org.jevemon.misc.util;



public class SQLConstants {
	
	public static final String EVE_DATABASE = "jdbc:sqlite:db/eve-online.db"; 
	
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
}
