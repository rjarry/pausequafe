package org.jevemon.data.dao;

import org.jdom.Document;
import org.jdom.Element;
import org.jevemon.data.business.ServerStatus;

public class ServerStatusParser {

	public static ServerStatus getStatus(Document doc) {
		Element root = doc.getRootElement().getChild("result");
		
		boolean isOnline = root.getChildText("serverOpen").equals("True");
		int playerCount = Integer.parseInt(root.getChildText("onlinePlayers"));
		
		return new ServerStatus(isOnline, playerCount);
	}

}
