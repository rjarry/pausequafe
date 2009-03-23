package org.pausequafe.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.pausequafe.data.business.MarketGroup;

public class TestMarketGroupDAO {
	public static void main(String[] args) throws Exception{
		MarketGroupDAO dao = MarketGroupDAO.getInstance();
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(5));
		list.add(Integer.valueOf(6));
		list.add(Integer.valueOf(7));
		list.add(Integer.valueOf(8));
		list.add(Integer.valueOf(9));
		
		List<MarketGroup> result = dao.findMarketGroupsById(list);
		for(MarketGroup mg : result){
			System.out.println(mg);
		}
		
		System.out.println("");
		
		list.add(75);
		result = dao.findMarketGroupsById(list);
		for(MarketGroup mg : result){
			System.out.println(mg);
		}
	}
}
