package org.pausequafe.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.Item;
import org.pausequafe.data.dao.ItemDAO;

public class TestItemDAO {
	public static void main(String[] args) throws Exception{
		ItemDAO dao = ItemDAO.getInstance();
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(18));
		list.add(Integer.valueOf(19));
		list.add(Integer.valueOf(20));
		list.add(Integer.valueOf(21));
		list.add(Integer.valueOf(22));
		
		List<Item> result = dao.findItemsById(list);
		for(Item item : result){
			System.out.println(item);
		}
		
		System.out.println("");
		
		result = dao.findItemsById(list);
		for(Item item : result){
			System.out.println(item);
		}
	}
}
