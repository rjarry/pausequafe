package org.jevemon.model.charactersheet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;

import be.fomp.jeve.core.exceptions.JEveException;

public class FileHandler {
	private static final XMLOutputter xmlOutputter = new XMLOutputter();
	
	/**
	 * Takes a document object and saves it as an xml file.
	 * This will overwrite any existing file
	 * 
	 * @param doc The document to be saved
	 * @param fileName The file it should be written to
	 * @throws JEveException
	 * @author Sven Meys
	 */
	public static void writeXmlFile(Document doc,String pathName, String fileName) throws JEveException {
		
		FileWriter out = null;
		try {
			
			// Prepare the output file
			File path = new File (pathName);
			File file = new File(pathName + fileName);
			
			// Check if the directories exist
			if(!path.exists()) path.mkdirs();		
			
			out = new FileWriter(file);
			
			xmlOutputter.output(doc, out);
			
			out.close();
			
		} catch (FileNotFoundException e) {
			throw new JEveException("Unable to write to file");
		} catch (IOException e) {
			throw new JEveException("Unable to write to file");
		} finally {
			try {
				if(out != null) out.close();
			} catch (Exception e) {
				System.out.println("Failed to close stream\n"+e.getMessage());
			}
		}
	}

	/**
	 * Takes an image object and writes it to a jpg file.
	 * 
	 * @param image the image to be saved
	 * @param fileName The file to be written to
	 * @throws JEveException
	 * 
	 * @author Sven Meys
	 */
	public static void writeImage(Image image,String pathName, String fileName) throws JEveException {
		try {
		// Get an instance of the file
		File path = new File(pathName);
		File file = new File(pathName + fileName);
		
		// Check if the directories exist
		if(!path.exists()) path.mkdirs();
		
		// Write the image as a jpg to the file
		ImageIO.write((BufferedImage)image, "jpg", file);
		
		} catch (IOException ioe) { throw new JEveException("Unable to write image to file");}
	}
}
