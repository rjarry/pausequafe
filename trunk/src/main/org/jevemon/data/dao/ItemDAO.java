package org.jevemon.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jevemon.data.business.Item;
import org.jevemon.misc.util.SQLConstants;

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
	public List<Item> findItemsById(List<Integer> list){
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
			try {
				result.addAll(queryItemsById(toBeQueried));
			} catch (SQLException e) {
				// TODO gestion de cette exception
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public Item findItemById(Integer itemId) {
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
	private List<Item> queryItemsById(List<Integer> toBeQueried) throws SQLException {
		List<Item> result = new ArrayList<Item>();
		
		initConnection();

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
		
		ResultSet res = stat.executeQuery(query);
		while(res.next()){
			Item newItem = new Item(
					res.getInt(SQLConstants.TYPEID_COL),
					res.getString(SQLConstants.TYPENAME_COL), 
					res.getInt(SQLConstants.MARKETGRPID_COL), 
					res.getString(SQLConstants.DESCRIPTION_COL)  );
			
			memoryCache.put(newItem.getTypeID(), newItem);
			result.add(newItem);
		}
		
		return result;
	}
}
