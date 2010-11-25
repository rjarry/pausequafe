package be.fomp.jeve.core;

import org.jdom.Document;
import org.junit.Before;
import org.junit.Test;

import be.fomp.jeve.core.api.LimitedAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.util.FileHandler;

public class LimitedConnectorTest extends APIData{

	private final String FILE_PATH = "target/test-output/LimitedConnectorTest/";
	private final String ERRORMESSAGE = "Cached API key authentication failure.";
	
	LimitedAPI con;
	
	private void validateError(String e)
	{
		if(!ERRORMESSAGE.equals(e))
			fail("Should not get here");
	}
	
	@Before
	public void setUp() throws Exception {
		con = JEveConnectionFactory.createFactory().getLimitedConnection();
	}
	
	@Test
	public void testLimitedConnector() {
		try {
			JEveConnectionFactory.createFactory().getLimitedConnection();
		} catch (JEveException je) {
			fail();
		}
	}
	
	@Test
	public void testGetCharacterSheet() {
		try {
			System.out.print("Fetching Character Sheet...");
			Document doc = con.getCharacterSheet(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CharacterSheet.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCharacters() {
		try {
			System.out.print("Fetching Characters...");
			Document doc = con.getCharacters(userID, apiKey);
			FileHandler.writeXmlFile(doc, FILE_PATH, "Characters.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorporationSheet() {
		try {
			System.out.print("Fetching Corporation Sheet...");
			Document doc = con.getCorporationSheet(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationSheet.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetSkillInTraining() {
		try {
			System.out.print("Fetching Skill In Training...");
			Document doc = con.getSkillInTraining(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "SkillIntraining.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			validateError(e.getMessage());
		}
	}

}
