package org.pausequafe.data.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pausequafe.data.business.BPActivity;
import org.pausequafe.data.business.BPRequiredMaterial;
import org.pausequafe.data.business.Blueprint;
import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.data.business.ItemDetailed;
import org.pausequafe.data.business.MarketGroup;
import org.pausequafe.data.business.PreRequisite;
import org.pausequafe.data.business.Skill;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.exceptions.PQUserDatabaseFileCorrupted;
import org.pausequafe.misc.util.Constants;

import static org.pausequafe.misc.util.SQLConstants.*;

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
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

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
			PQEveDatabaseNotFound, PQUserDatabaseFileCorrupted {
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
			PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {
		String name = null;
		initConnection(EVE_DATABASE);
		try {
			ResultSet res = stat.executeQuery(QUERY_TYPE_NAME_BY_ID.replace("?", String
					.valueOf(typeID)));
			if (res.next()) {
				name = res.getString(TYPENAME_COL);
			}
		} catch (SQLException e) {
			throw new PQEveDatabaseNotFound();
		}

		return name;
	}

	public ItemDetailed getItemDetails(Item baseItem) throws PQUserDatabaseFileCorrupted,
			PQEveDatabaseNotFound, PQSQLDriverNotFoundException {

		File db = new File(EVE_DATABASE_FILE);
		if (!db.exists()) {
			throw new PQEveDatabaseNotFound();
		}
		initConnection(EVE_DATABASE);

		String typeIDrequired = String.valueOf(baseItem.getTypeID());
		String query = QUERY_ITEM_DETAILS_BY_ID.replace("?", typeIDrequired);
		// System.out.println(query); // for convenience : uncomment to see DB
		// queries

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
			throw new PQUserDatabaseFileCorrupted();
		}
		return askedItem;
	}

	// ///////////////////
	// private methods //
	// ///////////////////

	private List<Item> queryItemsById(List<Integer> toBeQueried)
			throws PQSQLDriverNotFoundException, PQEveDatabaseNotFound, PQUserDatabaseFileCorrupted {
		List<Item> result = new ArrayList<Item>();

		File db = new File(EVE_DATABASE_FILE);
		if (!db.exists()) {
			throw new PQEveDatabaseNotFound();
		}
		initConnection(EVE_DATABASE);

		String inClause = buildInClause(toBeQueried);
		String query = QUERY_TYPES_BY_ID.replace("?", inClause);
		String query2 = QUERY_PREREQUISITES_BY_ID.replace("?", inClause);
		// System.out.println(query); // for convenience : uncomment to see DB
		// queries

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
			throw new PQEveDatabaseNotFound();
		}
		return result;
	}

	/**
	 * Initialize children for market groups passed as parameters
	 */
	public void initMarketGroupChildren(List<MarketGroup> parents)
			throws PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted, PQEveDatabaseNotFound {

		if (parents == null || parents.size() == 0) {
			return;
		}

		File db = new File(EVE_DATABASE_FILE);
		if (!db.exists()) {
			throw new PQEveDatabaseNotFound();
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
		// System.out.println(query); // for convenience : uncomment to see DB
		// queries

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
			throw new PQEveDatabaseNotFound();
		}
	}

	public void initBlueprintGroupChildren(List<MarketGroup> parents) throws PQEveDatabaseNotFound,
			PQSQLDriverNotFoundException, PQUserDatabaseFileCorrupted {
		if (parents == null || parents.size() == 0) {
			return;
		}
		File db = new File(EVE_DATABASE_FILE);
		if (!db.exists()) {
			throw new PQEveDatabaseNotFound();
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

		String query = QUERY_BLUEPRINTS_BY_PARENT.replace("?", inClause);

		// System.out.println(query); // for convenience : uncomment to see DB
		// queries

		try {
			ResultSet res = stat.executeQuery(query);
			Item newBlueprint = null;
			while (res.next()) {
				Integer itemId = res.getInt(TYPEID_COL);
				newBlueprint = (Blueprint) memoryCache.get(itemId);
				if (newBlueprint == null) {
					String typeName = res.getString(TYPENAME_COL);
					String icon = res.getString(ICON_COL);
					int metaGroupID = res.getInt(METAGROUPID_COL);
					int productTypeID = res.getInt(PRODUCTTYPEID_COL);
					int techLevel = res.getInt(TECHLEVEL_COL);
					long productionTime = res.getLong(PRODTIME_COL) * Constants.SECOND;
					long researchMETime = res.getLong(METIME_COL) * Constants.SECOND;
					long researchPETime = res.getLong(PETIME_COL) * Constants.SECOND;
					long inventionTime = res.getLong(INVENTIONTIME_COL) * Constants.SECOND;
					long copyTime = res.getLong(COPYTIME_COL) * Constants.SECOND;
					int wasteFactor = res.getInt(WASTE_COL);
					int batchSize = res.getInt(PORTIONSIZE_COL);
					double basePrice = res.getDouble(BASEPRICE_COL);
					int maxRuns = res.getInt(MAXRUNS_COL);

					newBlueprint = new Blueprint(itemId, typeName, icon, metaGroupID,
							productTypeID, techLevel, productionTime, researchMETime,
							researchPETime, inventionTime, copyTime, wasteFactor, batchSize,
							basePrice, BLUEPRINT_CATID, maxRuns);

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

				int requiredTypeID = res.getInt(REQTYPEID_COL);
				int quantity = res.getInt(QUANTITY_COL);
				double damagePerJob = res.getDouble(DAMAGEPERJOB_COL);
				int categoryID = res.getInt(CATEGORYID_COL);

				if (categoryID == SKILL_CATID) {
					PreRequisite preReq = new PreRequisite(requiredTypeID, quantity);
					newActivity.getPrereqs().add(preReq);
				} else {
					BPRequiredMaterial material = new BPRequiredMaterial(requiredTypeID, quantity,
							damagePerJob);
					newActivity.getMaterials().add(material);
				}
			}
			res.close();
		} catch (SQLException e) {
			throw new PQEveDatabaseNotFound();
		}
	}
}
