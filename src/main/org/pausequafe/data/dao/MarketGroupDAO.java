package org.pausequafe.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
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
	private HashMap<Integer, MarketGroup> memoryCache = new HashMap<Integer, MarketGroup>();

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

	// //////////////////
	// public methods //
	// //////////////////
	public List<MarketGroup> findMarketGroupsById(List<Integer> idList)
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		ArrayList<MarketGroup> result = new ArrayList<MarketGroup>();
		List<Integer> toBeQueried = new ArrayList<Integer>();

		// get items from cache
		MarketGroup aMarketGroup;
		for (Integer aMarketGroupId : idList) {
			aMarketGroup = memoryCache.get(aMarketGroupId);
			if (aMarketGroup == null) {
				toBeQueried.add(aMarketGroupId);
			} else {
				result.add(aMarketGroup);
			}
		}

		// get missing items from database
		if (!toBeQueried.isEmpty()) {
			List<MarketGroup> queriedMarketGroups = queryMarketGroupsById(toBeQueried);
			result.addAll(queriedMarketGroups);
		} // missing items are now retrieved

		return result;
	}

	/**
	 * Provided for convenience.
	 * 
	 */
	public MarketGroup findMarketGroupById(Integer groupId) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		MarketGroup result = null;
		List<Integer> list = new ArrayList<Integer>();
		list.add(groupId);

		List<MarketGroup> listResult = findMarketGroupsById(list);

		if (!listResult.isEmpty()) {
			result = listResult.get(0);
		}
		return result;
	}

	public void addMarketGroupChild(int marketGroupID, int childID) {
		MarketGroup parent = memoryCache.get(marketGroupID);
		if (parent != null) {
			parent.addChild(childID);
		}
	}

	public String getGroupName(int groupID) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		String name = null;
		initConnection(SQLConstants.EVE_DATABASE);
		try {
			ResultSet res = stat.executeQuery(SQLConstants.QUERY_GROUP_NAME_BY_ID.replace("?",
					String.valueOf(groupID)));
			if (res.next()) {
				name = res.getString(SQLConstants.GROUPNAME_COL);
			}
		} catch (SQLException e) {
			throw new PQEveDatabaseNotFound();
		}

		return name;
	}

	// ///////////////////
	// private methods //
	// ///////////////////

	private List<MarketGroup> queryMarketGroupsById(List<Integer> idsToBeQueried)
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

		initConnection(SQLConstants.EVE_DATABASE);

		String inClause = buildInClause(idsToBeQueried);

		String query = SQLConstants.QUERY_MARKETGRP_BY_ID;
		query = query.replace("?", inClause);
//		System.out.println(query); // for convenience : uncomment to see DB
		// queries

		List<MarketGroup> result = new ArrayList<MarketGroup>();
		List<MarketGroup> itemContainerParents = new ArrayList<MarketGroup>();
		List<MarketGroup> groupContainerParents = new ArrayList<MarketGroup>();
		try {
			ResultSet res = stat.executeQuery(query);
			while (res.next()) {
				MarketGroup marketGroup = new MarketGroup(res.getInt(SQLConstants.MARKETGRPID_COL),
						res.getString(SQLConstants.MARKETGRPNAME_COL), res
								.getBoolean(SQLConstants.HASTYPE_COL));

				memoryCache.put(marketGroup.getGroupID(), marketGroup);
				result.add(marketGroup);
				// Now we have to query the groups children
				if (marketGroup.isItemContainer()) {
					itemContainerParents.add(marketGroup);
				} else {
					groupContainerParents.add(marketGroup);
				}
			}
			res.close();
		} catch (SQLException e) {
			throw new PQEveDatabaseNotFound();
		}

		ItemDAO.getInstance().initMarketGroupChildren(itemContainerParents);
		initMarketGroupChildren(groupContainerParents);

		return result;
	}

	/**
	 * Initialize children for market groups passed as parameters
	 */
	private void initMarketGroupChildren(List<MarketGroup> parents)
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

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
			ResultSet res = stat.executeQuery(query);
			while (res.next()) {
				int marketGroupID = res.getInt(SQLConstants.MARKETGRPID_COL);
				MarketGroup marketGroup = new MarketGroup(marketGroupID, res
						.getString(SQLConstants.MARKETGRPNAME_COL), res
						.getBoolean(SQLConstants.HASTYPE_COL));

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
			throw new PQEveDatabaseNotFound();
		}

		ItemDAO.getInstance().initMarketGroupChildren(itemContainerParents);
		if (groupContainerParents.size() != 0) {
			initMarketGroupChildren(groupContainerParents);
		}
	}

}
