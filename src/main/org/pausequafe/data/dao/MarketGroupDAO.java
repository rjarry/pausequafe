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
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
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

	private MarketGroup queryMarketGroupById(Integer groupId) throws PQSQLDriverNotFoundException,
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

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
				marketGroup = new MarketGroup(res.getInt(SQLConstants.MARKETGRPID_COL), res
						.getString(SQLConstants.MARKETGRPNAME_COL), res
						.getBoolean(SQLConstants.HASTYPE_COL));

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
			throw new PQEveDatabaseNotFound();
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
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

		if (parents == null || parents.size() == 0){
			return;
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

		ItemDAO.getInstance().initBlueprintGroupChildren(itemContainerParents);
		if (groupContainerParents.size() != 0) {
			initBlueprintGroupChildren(groupContainerParents);
		}
	}

	/**
	 * Initialize children for market groups passed as parameters
	 */
	private void initMarketGroupChildren(List<MarketGroup> parents)
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

		if (parents == null || parents.size() == 0){
			return;
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
