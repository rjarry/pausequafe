package org.jevemon.model.characterlist;

import java.util.LinkedList;

import org.jevemon.misc.exceptions.JEVEMonException;

public class CharacterList {
	
	private LinkedList<CharacterLtd> charList;
	
	public CharacterList(){
		charList = new LinkedList<CharacterLtd>();
	}
	
	
	/**
	 * Adds a new CharacterLtd in the list.
	 * @param c The CharacterLtd to be inserted
	 */
	public void addCharacter(CharacterLtd c){
		charList.add(c);
	}
	
	/**
	 * Gets the CharacterLtd at the specified index in the list.
	 * @param index The index in the list
	 * @return A CharacterLtd
	 * @throws JEVEMonException
	 */
	public CharacterLtd getCharcterAt(int index) throws JEVEMonException 
	{
		CharacterLtd c;
		try{
			c = charList.get(index);
		} catch (IndexOutOfBoundsException i) {
			throw new JEVEMonException();
		}
		return c ;
	}
}
