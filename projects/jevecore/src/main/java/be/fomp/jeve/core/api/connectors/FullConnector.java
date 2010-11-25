 /*	
   ______   ______    
  /\__  __\/\ \___\ 
  \/_/\ \_/\ \ \__/    __   __     __       
     \ \ \  \ \ \__\  /\ \ /\ \  /'__`\  
     _\_\ \  \ \ \_/_ \ \ \\_\ \/\  __/       
    /\____/   \ \_____\\ \_____/\ \____\   
    \_/__/     \/_____/ \/____/  \/____/                   
 
 	This file is part of the JEVE core API.

    JEve is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    JEve is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Copyright 2008 JEve Project (JEveProject@gmail.com)
 */
package be.fomp.jeve.core.api.connectors;

import org.jdom.Document;

import be.fomp.jeve.core.api.FullAPI;
import be.fomp.jeve.core.api.url.FullUrl;
import be.fomp.jeve.core.config.Configuration;
import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveException;
import be.fomp.jeve.core.exceptions.JEveParseException;
import be.fomp.jeve.core.util.ParameterString;
/**
 * <pre>
 *  ______   ______    
 * /\__  __\/\ \___\ 
 * \/_/\ \_/\ \ \__/    __   __     __       
 *    \ \ \  \ \ \__\  /\ \ /\ \  /'__`\  
 *    _\_\ \  \ \ \_/_ \ \ \\_\ \/\  __/       
 *   /\____/   \ \_____\\ \_____/\ \____\   
 *   \_/__/     \/_____/ \/____/  \/____/                   
 *</pre>
 *	This file is part of the JEVE core API.<br />
 *	<br />
 * 
 * This class contains the accessor methods for the EVE-Online API 
 * which require a full API key.
 * 
 * @author Sven Meys
 */
class FullConnector extends LimitedConnector implements FullAPI {
	
	/**
	 * Constructor which takes a configuration setting. This configures the correct 
	 * proxy settings to ensure the client can access the internet.
	 * 
	 * @param proxy The proxy settings
	 * @throws JEveException
	 */
	FullConnector(Configuration config) {super(config);}
	
	/**
	 * This method retrieves the account balance of the specified character's accounts
	 * from http://api.eve-online.com/char/AccountBalance.xml.aspx and
	 * puts it in a Document object.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return a list of accounts
	 * @throws JEveBadargumentException when proxy is null
	 * 
	 * @author Sven Meys
	 */
	public final Document getAccountBallance(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(FullUrl.ACCOUNT_BALLANCE, parameters);
	}

	/**
	 * This method retrieves the assets of the specified character
	 * from http://api.eve-online.com/char/AssetList.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of assets
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getAssetList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(FullUrl.ASSET_LIST, parameters);
	}

	/**
	 * This method retrieves the account ballances of the corp's accounts
	 * from http://api.eve-online.com/corp/AccountBalance.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of accounts and their ballance
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpAccountBallance(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(FullUrl.CORP_ACCOUNT_BALLANCE, parameters);
	}

	/**
	 * This method retrieves the assets of the character's corp
	 * from http://api.eve-online.com/corp/AssetList.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of assets
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpAssetList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		
		return getDocumentData(FullUrl.CORP_ASSET_LIST, parameters);
	}

	/**
	 * This method retrieves a list of industry jobs in character's corp
	 * from http://api.eve-online.com/corp/IndustryJobs.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of industry jobs
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpIndustryJobs(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.CORP_INDUSTRY_JOBS, parameters);
	}

	/**
	 * This method retrieves a list kills in character's corp
	 * from http://api.eve-online.com/corp/Killlog.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param beforeKillID Used to search further back in the database. 
	 * If this is 0 the value is ignored.
	 * @return A list of industry jobs
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpKillLog(int userID, String apiKey, int characterID, int beforeKillID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		if(beforeKillID > 0) 
			parameters.addParameter("beforeKillID", beforeKillID);
		
		return getDocumentData(FullUrl.CORP_KILL_LOG, parameters);
	}

	/**
	 * This method retrieves a list of market orders in character's corp
	 * from http://api.eve-online.com/corp/MarketOrders.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of market orders
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpMarketOrders(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.CORP_MARKET_ORDERS, parameters);
	}

	/**
	 * This method retrieves a list of members and their location in a character's corp
	 * from http://api.eve-online.com/corp/MemberTracking.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of members
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpMemberTracking(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.CORP_MEMBER_TRACKING, parameters);
	}

	/**
	 * This method retrieves the details of a starbase ran by the character's corp
	 * from http://api.eve-online.com/corp/StarbaseDetail.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param itemID The ID of the starbase. Use getCorpStarbaseList() to get it.
	 * @return A list of starbase details
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpStarbaseDetail(int userID, String apiKey, int characterID, int itemID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID)
				  .addParameter("itemID",itemID);
		
		return getDocumentData(FullUrl.CORP_STARBASE_DETAIL, parameters);
	}

	/**
	 * This method retrieves a list of starbases ran by the character's corp
	 * from http://api.eve-online.com/corp/StarbaseList.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of starbases
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpStarbaseList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.CORP_STARBASE_LIST, parameters);
	}

	/**
	 * This method retrieves the wallet journal of an account in the character's corp
	 * from http://api.eve-online.com/corp/WalletJournal.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param accountKey The account ID: between 1000 and 1006
	 * @param beforeRefID To look further back, if this value is 0 it is ignored
	 * @return A wallet journal
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpWalletJournal(int userID, String apiKey, int characterID, int accountKey, int beforeRefID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID)
				  .addParameter("accountKey",accountKey);
		
		if(beforeRefID > 0)
			parameters.addParameter("beforeRefID",beforeRefID);
		
		return getDocumentData(FullUrl.CORP_WALLET_JOURNAL, parameters);
	}

	/**
	 * This method retrieves the wallet transactions of an account in the character's corp
	 * from http://api.eve-online.com/corp/WalletTransactions.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param accountKey The account ID: between 1000 and 1006
	 * @param beforeTransID To look further back, if this value is 0 it is ignored
	 * @return A list of wallet transactions
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getCorpWalletTransactions(int userID, String apiKey, int characterID, int accountKey, int beforeTransID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID)
				  .addParameter("accountKey",accountKey);
		if(beforeTransID > 0)
			parameters.addParameter("beforeTransID",beforeTransID);
		
		return getDocumentData(FullUrl.CORP_WALLET_TRANSACTIONS, parameters);
	}

	/**
	 * This method retrieves a list of industry jobs ran by the character
	 * from http://api.eve-online.com/corp/WalletTransactions.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of industry jobs
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getIndustryJobs(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.INDUSTRY_JOBS, parameters);
	}

	/**
	 * This method retrieves the kill log of the character
	 * from http://api.eve-online.com/char/Killlog.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param beforeKillID To look further back, if value is 0 this is ignored
	 * @return A kill log
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getKillLog(int userID, String apiKey, int characterID, int beforeKillID) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		if(beforeKillID > 0)
			parameters.addParameter("beforeKillID",beforeKillID);
		
		return getDocumentData(FullUrl.KILL_LOG, parameters);
	}

	/**
	 * This method retrieves a list of market orders of the character
	 * from http://api.eve-online.com/char/MarketOrders.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @return A list of market orders
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getMarketOrders(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException{
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID", characterID);
		
		return getDocumentData(FullUrl.MARKET_ORDERS, parameters);
	}

	/**
	 * This method retrieves the wallet journal of the character
	 * from http://api.eve-online.com/char/WalletJournal.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param beforeRefID To look further back, if value is 0 this is ignored
	 * @return A wallet journal
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getWalletJournal(int userID, String apiKey, int characterID, int beforeRefID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID)
				  // Account key is always 1000 for characters
				  .addParameter("accountKey",1000);
		if(beforeRefID > 0)
			parameters.addParameter("beforeRefID",beforeRefID);
		
		return getDocumentData(FullUrl.WALLET_JOURNAL, parameters);
	}

	/**
	 * This method retrieves the wallet transactions of the character
	 * from http://api.eve-online.com/char/WalletTransactions.xml.aspx.
	 * 
	 * @param userID The user ID
	 * @param apiKey The API key
	 * @param characterID The Character ID
	 * @param beforeTransID To look further back, if value is 0 this is ignored
	 * @return A list of wallet transactions
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public final Document getWalletTransactions(int userID, String apiKey, int characterID, int beforeTransID) throws JEveConnectionException, JEveParseException {
		
		ParameterString parameters = new ParameterString();
		parameters.addParameter("userID", userID)
				  .addParameter("apiKey", apiKey)
				  .addParameter("characterID",characterID);
		if(beforeTransID > 0)
			parameters.addParameter("beforeRefID",beforeTransID);
		
		return getDocumentData(FullUrl.WALLET_TRANSACTIONS, parameters);
	}
}
