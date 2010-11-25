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
package be.fomp.jeve.core.api;

import org.jdom.Document;

import be.fomp.jeve.core.exceptions.JEveConnectionException;
import be.fomp.jeve.core.exceptions.JEveParseException;

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
 * Interface containing the accessor methods for the EVE-Online API 
 * which require the full access API key.
 * 
 * @author Sven Meys
 *
 */
public interface FullAPI extends LimitedAPI {

	
	/*--[ API V1 ]--*/
		// Account Balances 	 /char/AccountBalance.xml.aspx
		Document getAccountBallance(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Account Balances 	 /corp/AccountBalance.xml.aspx
		Document getCorpAccountBallance(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
	
		// Journal Entries 	/char/WalletJournal.xml.aspx
		Document getWalletJournal(int userID, String apiKey, int characterID, int beforeRefID) throws JEveConnectionException, JEveParseException;
		
		// Journal Entries 	/corp/WalletJournal.xml.aspx   // characterID or corporationID ???
		Document getCorpWalletJournal(int userID, String apiKey, int characterID, int accountKey, int beforeRefID) throws JEveConnectionException, JEveParseException;
		
		// Market Transactions 	/char/WalletTransactions.xml.aspx
		Document getWalletTransactions(int userID, String apiKey, int characterID, int beforeTransID) throws JEveConnectionException, JEveParseException;
		
		// Market Transactions 	/corp/WalletTransactions.xml.aspx
		Document getCorpWalletTransactions(int userID, String apiKey, int characterID, int accountKey, int beforeTransID) throws JEveConnectionException, JEveParseException;
		
		//  Member Tracking 	 /corp/MemberTracking.xml.aspx    // guessing character ID???
		Document getCorpMemberTracking(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
	/*--[ API V2 ]--*/
	
		// Asset List 	 /char/AssetList.xml.aspx   // version=2 needed???
		Document getAssetList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Asset List 	 /corp/AssetList.xml.aspx   version must be 2
		Document getCorpAssetList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;

		// Industry Jobs 	 /char/IndustryJobs.xml.aspx
		Document getIndustryJobs(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Industry Jobs 	 /corp/IndustryJobs.xml.aspx    requires factory manager
		Document getCorpIndustryJobs(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Kill Log (Killmails) 	/char/Killlog.xml.aspx
		Document getKillLog(int userID, String apiKey, int characterID, int beforeKillID) throws JEveConnectionException, JEveParseException;
		
		// Kill Log (Killmails) 	/corp/Killlog.xml.aspx    // must be CEO or director
		Document getCorpKillLog(int userID, String apiKey, int characterID, int beforeKillID) throws JEveConnectionException, JEveParseException;
		
		// Market Orders 	/char/MarketOrders.xml.aspx
		Document getMarketOrders(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Market Orders 	/corp/MarketOrders.xml.aspx
		Document getCorpMarketOrders(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
		// Starbase (POS) Details 	/corp/StarbaseDetail.xml.aspx  // int version needed?
		Document getCorpStarbaseDetail(int userID, String apiKey, int characterID, int itemID) throws JEveConnectionException, JEveParseException;
		
		// Starbase (POS) List 	/corp/StarbaseList.xml.aspx   // int version needed?
		Document getCorpStarbaseList(int userID, String apiKey, int characterID) throws JEveConnectionException, JEveParseException;
		
	/*--[ API V3 ]--*/
		 
		 
}
