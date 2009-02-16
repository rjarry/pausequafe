package org.jevemon.model.characterlist;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jevemon.misc.exceptions.JEVEMonParseException;

public class CharacterListParser {
	
	private Document doc = null;
	
	public CharacterListParser(String fileName) throws JEVEMonParseException {
		File file = new File(fileName);
		try {
			 this.doc = new SAXBuilder().build(file);
			 if(doc.getRootElement().getChildren("error").size() > 0){
				 throw new JEVEMonParseException();
			 }
		} catch (JDOMException e) {
			throw new JEVEMonParseException();
		} catch (IOException e) {
			throw new JEVEMonParseException();
		}
	}
	
	public CharacterList getList(){
		Element root = doc.getRootElement();
		List<Element> xmlList = root.getChild("result").getChild("rowset").getChildren("row");
		
		CharacterList list = new CharacterList();
		
		for (Element row : xmlList) {
			String name = row.getAttributeValue("name");
			Long id = Long.parseLong(row.getAttributeValue("characterID"));
			
			list.addCharacter(new CharacterLtd(name, id));
		}
		
		return list;
	}
}
