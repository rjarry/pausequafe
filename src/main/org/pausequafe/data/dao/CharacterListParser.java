package org.pausequafe.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.business.APIData;

public class CharacterListParser {
	
	/**
	 * Generates a character list from a JDOM Document.
	 * @param doc
	 * @param apiKey 
	 * @param userID 
	 * @return a character list
	 */
	@SuppressWarnings("unchecked")
	public static List<APIData> getList(Document doc, int userID, String apiKey){
				
		Element root = doc.getRootElement();
		List<Element> xmlList = root.getChild("result").getChild("rowset").getChildren("row");
		
		List<APIData> list = new ArrayList<APIData>();
		
		for (Element row : xmlList) {
			String chrName = row.getAttributeValue("name");
			int charID = Integer.parseInt(row.getAttributeValue("characterID"));
			
			list.add(new APIData(charID, chrName, userID, apiKey));
		}
		return list;
	}
	

//	/**
//	 * parses an XML file to a document
//	 * @param fileName
//	 * @return a JDOM Document
//	 * @throws JEVEMonParseException
//	 */
//	public static Document parse(String fileName) throws JEVEMonParseException {
//		Document doc = null;
//		File file = new File(fileName);
//		try {
//			doc = new SAXBuilder().build(file);
//			if(doc.getRootElement().getChildren("error").size() > 0){
//				throw new JEVEMonParseException("Corrupted XML file");
//			}
//		} catch (JDOMException e) {
//			throw new JEVEMonParseException("Parsing error");
//		} catch (IOException i) {
//			throw new JEVEMonParseException("I/O error");
//		}
//		return doc;
//	}

}
