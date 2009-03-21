package org.jevemon.misc.util;

import java.io.IOException;

import org.jevemon.data.business.APIData;
import org.jevemon.data.business.CharacterSheet;
import org.jevemon.data.dao.CharacterSheetFactory;
import org.jevemon.misc.exceptions.JEVEMonException;

public class SheetApiRequest extends Thread {

	////////////////////
	// private fields //
	////////////////////
	
	private APIData data;
	private int tabIndex;
	private CharacterSheet sheet;
	
	/////////////////
	// constructor //
	/////////////////
	public SheetApiRequest(APIData data, int tabIndex) {
		super();
		this.data = data;
		this.tabIndex = tabIndex;
	}
	
	
	@Override
	public void run(){
		try {
			sheet = CharacterSheetFactory.getCharacterSheet(data);
		} catch (JEVEMonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public APIData getData() {
		return data;
	}


	public int getTabIndex() {
		return tabIndex;
	}


	public CharacterSheet getSheet() {
		return sheet;
	}


	public void setData(APIData data) {
		this.data = data;
	}


	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}


	public void setSheet(CharacterSheet sheet) {
		this.sheet = sheet;
	}
	
}
