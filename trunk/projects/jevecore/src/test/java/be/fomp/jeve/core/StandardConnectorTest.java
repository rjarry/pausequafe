package be.fomp.jeve.core;

import java.awt.Image;
import java.awt.image.BufferedImage;

import org.jdom.Document;
import org.junit.Before;
import org.junit.Test;

import be.fomp.jeve.core.api.StandardAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.util.FileHandler;
import be.fomp.jeve.core.config.Configuration;

public class StandardConnectorTest extends APIData{

	private final String FILE_PATH = "target/test-output/StandardConnectorTest/";
	StandardAPI con;
	
	@Before
	public void setUp() throws Exception {
		Configuration.setConfigurationFilePath("settings/proxy.configuration");
		con = JEveConnectionFactory.createFactory().getStandardConnection();
	}
	
	@Test
	public void testStandardConnector() {
		try {
			JEveConnectionFactory.createFactory().getStandardConnection();
		} catch (JEveException je) {
			fail();
		}
	}

	@Test
	public void testGetAllianceList() {
		try {
			System.out.print("Fetching Alliance List...");
			Document doc = con.getAllianceList();
			FileHandler.writeXmlFile(doc, FILE_PATH, "AllianceList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetCharacterID() {
		try {
			System.out.print("Fetching Character ID's...");
			Document doc = con.getCharacterID("Svekke");
			FileHandler.writeXmlFile(doc, FILE_PATH, "CharacterIDs.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetConquerableStationList() {
		try {
			System.out.print("Fetching Conquerable station List...");
			Document doc = con.getConquerableStationList();
			FileHandler.writeXmlFile(doc, FILE_PATH, "ConquerableStationList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetCorporationSheet() {
		try {
			System.out.print("Fetching Corporation Sheet...");
			Document doc = con.getCorporationSheet(userID,109788662);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationSheet.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetErrorList() {
		try {
			System.out.print("Fetching Error List...");
			Document doc = con.getErrorList();
			FileHandler.writeXmlFile(doc, FILE_PATH, "ErrorList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetJumps() {
		try {
			System.out.print("Fetching Jumps...");
			Document doc = con.getJumps();
			FileHandler.writeXmlFile(doc, FILE_PATH, "Jumps.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetKills() {
		try {
			System.out.print("Fetching Kills...");
			Document doc = con.getKills();
			FileHandler.writeXmlFile(doc, FILE_PATH, "Kills.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetPortrait() {
		try {
			System.out.print("Fetching Portrait...");
			Image img = con.getPortrait(256, 1121421600);
			FileHandler.writeImage(img, FILE_PATH, "Portrait.jpg");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed"+e.getMessage());
			fail("Should not get here");
		}
		try {
			System.out.print("Fetching Small Portrait...");
			Image img = con.getPortrait(4, 1121421600);
			FileHandler.writeImage(img, FILE_PATH, "Portrait-small.jpg");
			assertEquals(64,((BufferedImage)img).getHeight());
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed"+e.getMessage());
			fail("Should not get here");
		}
	}

	@Test
	public void testGetRefTypes() {
		try {
			System.out.print("Fetching Ref Types...");
			Document doc = con.getRefTypes();
			FileHandler.writeXmlFile(doc, FILE_PATH, "RefTypes.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetSkillTree() {
		try {
			System.out.print("Fetching Skill Tree...");
			Document doc = con.getSkillTree();
			FileHandler.writeXmlFile(doc, FILE_PATH, "SkillTree.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}

	@Test
	public void testGetSovereignty() {
		try {
			System.out.print("Fetching Sovereignty...");
			Document doc = con.getSovereignty();
			FileHandler.writeXmlFile(doc, FILE_PATH, "Sovereignty.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed");
			fail("Should not get here");
		}
	}
}
