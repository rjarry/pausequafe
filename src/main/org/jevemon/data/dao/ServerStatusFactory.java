package org.jevemon.data.dao;

import org.jdom.Document;
import org.jevemon.data.business.ServerStatus;

import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveConfigurationException;

public class ServerStatusFactory {
	
	public static ServerStatus getStatus() {
		StandardAPI connection = null;
		Document doc = null;

		// create limited connection
		try {
			connection = JEveConnectionFactory.createFactory().getStandardConnection();
		} catch (JEveConfigurationException e) {
			return new ServerStatus();
		}
		// get server status and put it into a Document
		try {
			doc = connection.getServerStatus();
		} catch (Exception e) {
			return new ServerStatus();
		} 

		// parse the server status and return it
		return ServerStatusParser.getStatus(doc);
	}
}