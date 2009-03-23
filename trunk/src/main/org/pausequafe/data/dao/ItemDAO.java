package org.pausequafe.data.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.business.ItemAttribute;
import org.pausequafe.misc.exceptions.PQDatabaseFileCorrupted;
import org.pausequafe.misc.exceptions.PQEveDatabaseNotFound;
import org.pausequafe.misc.exceptions.PQSQLDriverNotFoundException;
import org.pausequafe.misc.util.SQLConstants;

/**
 * A DAO that creates Item from the eve database 
 * 
 * @author Administrateur
 */
public class ItemDAO  extends AbstractSqlDAO{
	
	//////////////////////
	// protected fields //
	//////////////////////	
	private static ItemDAO instance;
	private HashMap<Integer, Item> memoryCache = new HashMap<Integer, Item>();
	
    /////////////////
    // constructor //
    /////////////////
	private ItemDAO(){
		super();
	}
	
	public static ItemDAO getInstance(){
		if (instance == null){
			instance = new ItemDAO();
		}
		return instance;
	}
	
	////////////////////
    // public methods //
    ////////////////////
	public List<Item> findItemsById(List<Integer> list) throws PQSQLDriverNotFoundException, PQDatabaseFileCorrupted, PQEveDatabaseNotFound{
		ArrayList<Item> result = new ArrayList<Item>();
		List<Integer> toBeQueried = new ArrayList<Integer>();
		
		// get items from cache 
		Item anItem;
		for(Integer anItemIndex : list){
			anItem = memoryCache.get(anItemIndex);
			if(anItem == null){
				toBeQueried.add(anItemIndex);
			} else {
				result.add(anItem);
			}
		}
		
		// get missing items from database
		if(!toBeQueried.isEmpty()){
			result.addAll(queryItemsById(toBeQueried)); 
		}
		
		return result;
	}
	
	public Item findItemById(Integer itemId) throws PQSQLDriverNotFoundException, PQDatabaseFileCorrupted, PQEveDatabaseNotFound {
		Item result = null;
		List<Integer> list = new ArrayList<Integer>();
		list.add(itemId);
		
		List<Item> listResult = findItemsById(list);
		
		if(!listResult.isEmpty()){
			result = listResult.get(0);
		}
		return result;
	}

	/////////////////////
    // private methods //
    /////////////////////
	private List<Item> queryItemsById(List<Integer> toBeQueried) throws  PQSQLDriverNotFoundException, PQDatabaseFileCorrupted, PQEveDatabaseNotFound {
		List<Item> result = new ArrayList<Item>();
		
		File db = new File(SQLConstants.EVE_DATABASE_FILE);
		if(!db.exists()){
			throw new PQEveDatabaseNotFound();
		}
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
		String query = SQLConstants.QUERY_TYPES_BY_ID;
		query = query.replace("?", inClause);
		//System.out.println(query); // for convenience : uncomment to see DB queries
		
		try {
			ResultSet res = stat.executeQuery(query);
			while(res.next()){
				Integer itemId = res.getInt(SQLConstants.TYPEID_COL);
				Item newItem = memoryCache.get(itemId);
				if(newItem == null){
					newItem = new Item(itemId,
							res.getString(SQLConstants.TYPENAME_COL),
							res.getString(SQLConstants.DESCRIPTION_COL),
							res.getFloat(SQLConstants.BASEPRICE_COL),
							res.getInt(SQLConstants.GRAPHICID_COL),
							res.getFloat(SQLConstants.RADIUS_COL),
							res.getFloat(SQLConstants.MASS_COL),
							res.getFloat(SQLConstants.VOLUME_COL),
							res.getFloat(SQLConstants.CAPACITY_COL)				
					);

					memoryCache.put(newItem.getTypeID(), newItem);
					result.add(newItem);
				}
				
				int attributeID = res.getInt(SQLConstants.ATTRIBUTEID_COL);

				switch(attributeID){
					case SQLConstants.REQUIRED_SKILL_1_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(1).setTypeID(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					case SQLConstants.REQUIRED_SKILL_2_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(2).setTypeID(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					case SQLConstants.REQUIRED_SKILL_3_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(3).setTypeID(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					case SQLConstants.REQUIRED_SKILL_1_LEVEL_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(1).setRequiredLevel(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					case SQLConstants.REQUIRED_SKILL_2_LEVEL_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(2).setRequiredLevel(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					case SQLConstants.REQUIRED_SKILL_3_LEVEL_ATTID : 
						newItem.youDontWantToKnowWhatThisIs(3).setRequiredLevel(res.getInt(SQLConstants.ATTRIBUTE_VALUE_COL));
						break;
					default : 
						String attributeName = res.getString(SQLConstants.ATTRIBUTE_NAME_COL);
						String attributeCategory = res.getString(SQLConstants.ATTRIBUTE_CATEGORY_COL);
						float attributeValue = res.getFloat(SQLConstants.ATTRIBUTE_VALUE_COL);
						ItemAttribute attribute = new ItemAttribute(attributeName, attributeCategory, attributeValue);
						newItem.addAttribute(attribute);
				}
				
			}
			res.close();
		} catch (SQLException e) {
			throw new PQDatabaseFileCorrupted();
		}
		return result;
	}
}