package org.jevemon.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jevemon.data.business.MarketGroup;
import org.jevemon.misc.exceptions.JEVEMonDatabaseFileCorrupted;
import org.jevemon.misc.exceptions.JEVEMonSQLDriverNotFoundException;
import org.jevemon.misc.util.SQLConstants;

/**
 * A DAO that creates Market Groups from the eve database.
 * 
 * @author Administrateur
 */
public class MarketGroupDAO extends AbstractSqlDAO {
	
	//////////////////////
	// protected fields //
	//////////////////////
	private static MarketGroupDAO instance;
	private HashMap<Integer,MarketGroup> memoryCache = new HashMap<Integer, MarketGroup>();
	
    /////////////////
    // constructor //
    /////////////////
	private MarketGroupDAO(){
		super();
	}
	
	public static MarketGroupDAO getInstance(){
		if (instance == null){
			instance = new MarketGroupDAO();
		}
		return instance;
	}
	
	////////////////////
    // public methods //
    ////////////////////
	public List<MarketGroup> findMarketGroupsById(List<Integer> list) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted{
		ArrayList<MarketGroup> result = new ArrayList<MarketGroup>();
		List<Integer> toBeQueried = new ArrayList<Integer>();
		
		// get items from cache 
		MarketGroup aMarketGroup;
		for(Integer aMarketGroupIndex : list){
			aMarketGroup = memoryCache.get(aMarketGroupIndex);
			if(aMarketGroup == null){
				toBeQueried.add(aMarketGroupIndex);
			} else {
				result.add(aMarketGroup);
			}
		}
		
		// get missing items from database
		if(!toBeQueried.isEmpty()){
			result.addAll(queryMarketGroupsById(toBeQueried));
		}
		
		return result;
	}
	
	public MarketGroup findMarketGroupById(Integer groupId) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		MarketGroup result = null;
		List<Integer> list = new ArrayList<Integer>();
		list.add(groupId);
		
		List<MarketGroup> listResult = findMarketGroupsById(list);
		
		if(!listResult.isEmpty()){
			result = listResult.get(0);
		}
		return result;
	}

	/////////////////////
    // private methods //
    /////////////////////
	private List<MarketGroup> queryMarketGroupsById(List<Integer> toBeQueried) throws JEVEMonSQLDriverNotFoundException, JEVEMonDatabaseFileCorrupted {
		List<MarketGroup> result = new ArrayList<MarketGroup>();
		
		initConnection(SQLConstants.EVE_DATABASE);
		
		String inClause = "";
		boolean first = true;
		for(Integer typeID : toBeQueried){
			if(first){
				first = false;
			} else {
				inClause += ",";
			}
			inClause += typeID;
		}
		
		String query = SQLConstants.QUERY_MARKETGRP_BY_ID_WITHCHILDREN;
		query = query.replace("?", inClause);
		//System.out.println(query); // for convenience : uncomment to see DB queries
		
		try {
			ResultSet res = stat.executeQuery(query);
			while(res.next()){
				Integer marketGroupId = res.getInt(SQLConstants.MARKETGRPID_COL);
				MarketGroup marketGroup = memoryCache.get(marketGroupId);
				if(marketGroup==null){
					marketGroup = new MarketGroup(
							marketGroupId,
							res.getString(SQLConstants.MARKETGRPNAME_COL),
							res.getString(SQLConstants.DESCRIPTION_COL),
							res.getBoolean(SQLConstants.HASTYPE_COL)  );

					memoryCache.put(marketGroup.getGroupID(), marketGroup);
					result.add(marketGroup);
				}
				marketGroup.addChild(res.getInt(SQLConstants.CHILDID_COL));
				
			}
			res.close();
		} catch (SQLException e) {
			throw new JEVEMonDatabaseFileCorrupted();
		}

		return result;
	}

}
