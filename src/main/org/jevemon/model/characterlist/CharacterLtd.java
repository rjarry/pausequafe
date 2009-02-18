package org.jevemon.model.characterlist;

/**
 * Infos from characters.xml used for character selection when entering a new API key
 * 
 * @author diabeteman
 *
 */

public class CharacterLtd {

	private String name;
	private int characterId;
	
	public CharacterLtd(String name, int characterId) {
		this.name = name;
		this.characterId = characterId;
	}
	public String getName() {
		return name;
	}
	public int getCharacterId() {
		return characterId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}
	
}
