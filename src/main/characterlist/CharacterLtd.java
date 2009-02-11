package main.characterlist;

/**
 * Infos from characters.xml used for character selection when entering a new API key
 * 
 * @author diabeteman
 *
 */

public class CharacterLtd {

	private String name;
	private long characterId;
	
	public CharacterLtd(String name, long characterId) {
		this.name = name;
		this.characterId = characterId;
	}
	public String getName() {
		return name;
	}
	public long getCharacterId() {
		return characterId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}
	
}
