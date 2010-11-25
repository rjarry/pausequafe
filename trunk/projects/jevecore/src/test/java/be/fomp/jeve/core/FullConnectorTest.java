package be.fomp.jeve.core;

import org.jdom.Document;
import org.junit.Before;
import org.junit.Test;

import be.fomp.jeve.core.api.FullAPI;
import be.fomp.jeve.core.api.connectors.JEveConnectionFactory;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.util.FileHandler;

public class FullConnectorTest extends APIData{

	private final String FILE_PATH = "target/test-output/FullConnectorTest/";
	private final String ERRORMESSAGE = "Cached API key authentication failure.";
	
	FullAPI con;
	
	private void validateError(String e)
	{
		if(!e.contains("retry after") && !e.contains("Character must") && !ERRORMESSAGE.equals(e))
			fail("Should not get here");
	}
	
	@Before
	public void setUp() throws Exception {
		con = JEveConnectionFactory.createFactory().getFullConnection();
	}
	
	@Test
	public void testFullConnector() {
		try {
		JEveConnectionFactory.createFactory().getFullConnection();
		} catch (JEveException je) {
			fail();
		}
	}

	@Test
	public void testGetAccountBallance() {
		try {
			System.out.print("Fetching Account Ballance...");
			Document doc = con.getAccountBallance(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "AccountBallance.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetAssetList() {
		try {
			System.out.print("Fetching Asset List...");
			Document doc = con.getAssetList(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "AssetList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
			
		}
	}

	
	@Test
	public void testGetCorpAccountBallance() {
		try {
			System.out.print("Fetching Corporation Account Ballance...");
			Document doc = con.getCorpAccountBallance(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationAccountBallance.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpAssetList() {
		try {
			System.out.print("Fetching Corporation Asset List...");
			Document doc = con.getCorpAssetList(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationAssetList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			if(!"Character must be a Director or CEO.".equals(e.getMessage()))
				validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpIndustryJobs() {
		try {
			System.out.print("Fetching Corporation Industry Jobs...");
			Document doc = con.getCorpIndustryJobs(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationIndustryJobs.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpKillLog() {
		try {
			System.out.print("Fetching Corporation Kill Log...");
			Document doc = con.getCorpKillLog(userID, apiKey, characterID, 0); // TODO Test with valid and invalid ID
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationKillLog.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			if(!"Character must be a Director or CEO.".equals(e.getMessage()))
				validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpMarketOrders() {
		try {
			System.out.print("Fetching Corporation Market Orders...");
			Document doc = con.getCorpMarketOrders(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationMarketOrders.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			if(!"Character must have Accountant, Junior Accountant, or Trader roles.".equals(e.getMessage()))
				validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpMemberTracking() {
		try {
			System.out.print("Fetching Corporation Member Tracking...");
			Document doc = con.getCorpMemberTracking(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationMemberTracking.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpStarbaseDetail() {
		try {
			System.out.print("Fetching Corporation Starbase Detail...");
			Document doc = con.getCorpStarbaseDetail(userID, apiKey, characterID, 0);// FIXME test with invalid ID
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationStarbaseDetail.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			if(!"Character must be a Director or CEO.".equals(e.getMessage()))
				validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpStarbaseList() {
		try {
			System.out.print("Fetching Corporation Starbase List...");
			Document doc = con.getCorpStarbaseList(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationStarbaseList.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			if(!"Character must be a Director or CEO.".equals(e.getMessage()))
				validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpWalletJournal() {
		try {
			System.out.print("Fetching Corporation Wallet Journal...");
			Document doc = con.getCorpWalletJournal(userID, apiKey, characterID, 1001, 0); //FIXME test with other ID's
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationWalletJournal.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetCorpWalletTransactions() {
		try {
			System.out.print("Fetching Corporation Wallet Transactions...");
			Document doc = con.getCorpWalletTransactions(userID, apiKey, characterID, 1001, 0); // FIXME check other ID's
			FileHandler.writeXmlFile(doc, FILE_PATH, "CorporationWalletTransactions.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetIndustryJobs() {
		try {
			System.out.print("Fetching Industry Jobs...");
			Document doc = con.getIndustryJobs(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "IndustryJobs.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetKillLog() {
		try {
			System.out.print("Fetching Kill Log...");
			Document doc = con.getKillLog(userID, apiKey, characterID, -1); // FIXME check other ID's
			FileHandler.writeXmlFile(doc, FILE_PATH, "KillLog.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetMarketOrders() {
		try {
			System.out.print("Fetching Market Orders...");
			Document doc = con.getMarketOrders(userID, apiKey, characterID);
			FileHandler.writeXmlFile(doc, FILE_PATH, "MarketOrders.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetWalletJournal() {
		try {
			System.out.print("Fetching Wallet Journal...");
			Document doc = con.getWalletJournal(userID, apiKey, characterID, 0); // FIXME Check other ID's
			FileHandler.writeXmlFile(doc, FILE_PATH, "WalletJournal.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

	@Test
	public void testGetWalletTransactions() {
		try {
			System.out.print("Fetching Wallet Transactions...");
			Document doc = con.getWalletTransactions(userID, apiKey, characterID, 0); // FIXME Check other Id's
			FileHandler.writeXmlFile(doc, FILE_PATH, "WalletTransactions.xml");
			System.out.println("success");
		} catch (JEveException e) {
			System.out.println("failed: " + e.getMessage());
			validateError(e.getMessage());
		}
	}

}
