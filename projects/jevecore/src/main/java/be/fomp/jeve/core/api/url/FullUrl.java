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
package be.fomp.jeve.core.api.url;

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
 * Enumeration containing the urls for the EVE-Online API 
 * which require the full access API key.
 * 
 * @author Sven Meys
 *
 */
public enum FullUrl implements ApiUrl {
	ACCOUNT_BALLANCE("http://api.eve-online.com/char/AccountBalance.xml.aspx"),
	ASSET_LIST("http://api.eve-online.com/char/AssetList.xml.aspx"),
	CORP_ACCOUNT_BALLANCE("http://api.eve-online.com/corp/AccountBalance.xml.aspx"),
	CORP_ASSET_LIST("http://api.eve-online.com/corp/AssetList.xml.aspx"),
	CORP_INDUSTRY_JOBS("http://api.eve-online.com/corp/IndustryJobs.xml.aspx"),
	CORP_KILL_LOG("http://api.eve-online.com/corp/Killlog.xml.aspx"),
	CORP_MARKET_ORDERS("http://api.eve-online.com/corp/MarketOrders.xml.aspx"),
	CORP_MEMBER_TRACKING("http://api.eve-online.com/corp/MemberTracking.xml.aspx"),
	CORP_STARBASE_DETAIL("http://api.eve-online.com/corp/StarbaseDetail.xml.aspx"),
	CORP_STARBASE_LIST("http://api.eve-online.com/corp/StarbaseList.xml.aspx"),
	CORP_WALLET_JOURNAL("http://api.eve-online.com/corp/WalletJournal.xml.aspx"),
	CORP_WALLET_TRANSACTIONS("http://api.eve-online.com/corp/WalletTransactions.xml.aspx"),
	INDUSTRY_JOBS("http://api.eve-online.com/corp/WalletTransactions.xml.aspx"),
	KILL_LOG("http://api.eve-online.com/char/Killlog.xml.aspx"),
	MARKET_ORDERS("http://api.eve-online.com/char/MarketOrders.xml.aspx"),
	WALLET_JOURNAL("http://api.eve-online.com/char/WalletJournal.xml.aspx"),
	WALLET_TRANSACTIONS("http://api.eve-online.com/char/WalletTransactions.xml.aspx")
	;
	
	private String url;
	
	private FullUrl(String url)	{ this.url = url; }
	
	public String getUrl()
	{
		return url;
	}
}
