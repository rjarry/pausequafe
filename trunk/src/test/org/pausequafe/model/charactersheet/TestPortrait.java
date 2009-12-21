package org.pausequafe.model.charactersheet;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.pausequafe.core.factory.CharacterListFactory;
import org.pausequafe.core.factory.CharacterSheetFactory;
import org.pausequafe.data.business.APIData;
import org.pausequafe.misc.exceptions.PQException;
import org.pausequafe.misc.util.Constants;

public class TestPortrait {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<APIData> list = null;
		try {
			System.out.print("fetching character list... ");
			list = CharacterListFactory.getCharList(3173522, 
					"39D32F567BC4491BA1FCC0F3D8EED9B2F52BFEC3A3DD4F66B33DF0FADB9DFABB");
			System.out.println("success");
		} catch (PQException e) {
			System.out.println("error, could not get character list");
			System.exit(0);
		}
		int charCount = 0;
		for (APIData data : list) {
			charCount++;
			System.out.println(charCount + " - " + data.getCharacterName());
		}
		Scanner in = new Scanner(System.in);
		System.out.print("Choose a character : ");
		int index = in.nextInt();
		
		//Image portrait = null;
		
		try {
			System.out.print("fetching character portrait... ");
			//portrait = 
			CharacterSheetFactory.getPortrait(list.get(index-1), true);
			System.out.println("success");
			
		} catch (PQException e) {
			System.out.println("error, could not get portrait");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(Constants.CHAR_SHEET_PATH + list.get(index-1).getCharacterName() + ".jpg"));
		JFrame frame = new JFrame();
		frame.add(label);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
