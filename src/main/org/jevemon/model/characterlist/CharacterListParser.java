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
	
	/**
	 * Generates a character list from a JDOM Document.
	 * @param doc
	 * @return a character list
	 */
	@SuppressWarnings("unchecked")
	public static CharacterList getList(Document doc){
				
		Element root = doc.getRootElement();
		List<Element> xmlList = root.getChild("result").getChild("rowset").getChildren("row");
		
		CharacterList list = new CharacterList();
		
		for (Element row : xmlList) {
			String name = row.getAttributeValue("name");
			int id = Integer.parseInt(row.getAttributeValue("characterID"));
			
			list.addCharacter(new CharacterLtd(name, id));
		}
		return list;
	}
	
	/**
	 * parses an XML file to a document
	 * @param fileName
	 * @return a JDOM Document
	 * @throws JEVEMonParseException
	 */
	public static Document parse(String fileName) throws JEVEMonParseException {
		Document doc = null;
		File file = new File(fileName);
		try {
			doc = new SAXBuilder().build(file);
			if(doc.getRootElement().getChildren("error").size() > 0){
				throw new JEVEMonParseException("Corrupted XML file");
			}
		} catch (JDOMException e) {
			throw new JEVEMonParseException("Parsing error");
		} catch (IOException i) {
			throw new JEVEMonParseException("I/O error");
		}
		return doc;
	}
}
