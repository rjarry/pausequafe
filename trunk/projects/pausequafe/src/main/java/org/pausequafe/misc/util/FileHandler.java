/*****************************************************************************
 * Pause Quafé - An Eve-Online™ character assistance application             *
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

package org.pausequafe.misc.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.pausequafe.misc.exceptions.PQException;

public class FileHandler {
    private static final XMLOutputter xmlOutputter = new XMLOutputter();

    /**
     * Takes a document object and saves it as an xml file. This will overwrite any existing file
     * 
     * @param doc
     *            The document to be saved
     * @param fileName
     *            The file it should be written to
     * @throws PQException
     */
    public static void writeXmlFile(Document doc, String pathName, String fileName)
            throws PQException {

        Writer out = null;

        try {

            // Prepare the output file
            File path = new File(pathName);
            File file = new File(pathName + fileName);

            // Check if the directories exist
            if (!path.exists())
                path.mkdirs();

            // To force encoding to UTF-8
            FileOutputStream stream = new FileOutputStream(file);
            out = new OutputStreamWriter(stream, "UTF-8");

            xmlOutputter.output(doc, out);

            out.close();

        } catch (FileNotFoundException e) {
            throw new PQException("Unable to write to file");
        } catch (IOException e) {
            throw new PQException("Unable to write to file");
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                // System.out.println("Failed to close stream\n"+e.getMessage());
            }
        }
    }

    /**
     * Takes an image object and writes it to a jpg file.
     * 
     * @param image
     *            the image to be saved
     * @param fileName
     *            The file to be written to
     * @throws PQException
     * @throws IOException
     */
    public static void writeImage(Image image, String pathName, String fileName) throws IOException {

        // Get an instance of the file
        File path = new File(pathName);
        File file = new File(pathName + fileName);

        // Check if the directories exist
        if (!path.exists())
            path.mkdirs();

        // Write the image as a jpg to the file
        ImageIO.write((BufferedImage) image, "jpg", file);

    }
}
