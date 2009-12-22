/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application              *
 * Copyright © 2009  diabeteman & Kios Askoner                               *
 *                                                                           *
 * This file is part of Pause Quafé.                                         *
 *                                                                           *
 * Pause Quafé is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafé is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafé.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

package org.pausequafe.data.character;

public class APIData {

    // //////////////////
    // private fields //
    // //////////////////
    private int characterID = -1;
    private String characterName = "";
    private int userID;
    private String apiKey;

    // ////////////////
    // constructors //
    // ////////////////
    public APIData(int userID, String apiKey) {
        this.userID = userID;
        this.apiKey = apiKey;
    }

    public APIData(int characterID, int userID, String apiKey) {
        this.characterID = characterID;
        this.userID = userID;
        this.apiKey = apiKey;
    }

    public APIData(int characterID, String characterName, int userID, String apiKey) {
        this.characterID = characterID;
        this.characterName = characterName;
        this.userID = userID;
        this.apiKey = apiKey;
    }

    // ///////////
    // getters //
    // ///////////
    public int getCharacterID() {
        return characterID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public int getUserID() {
        return userID;
    }

    public String getApiKey() {
        return apiKey;
    }

    // ///////////
    // setters //
    // ///////////
    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
