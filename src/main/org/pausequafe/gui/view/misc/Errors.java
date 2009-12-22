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

package org.pausequafe.gui.view.misc;

import java.io.File;

import org.pausequafe.misc.util.Constants;
import org.pausequafe.misc.util.SQLConstants;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QWidget;

public class Errors {
    
    public static void popUserDBCorrupt(QWidget parent, Exception e) {
        String message = Constants.USER_DB_CORRUPTED_ERROR;
        message += "\n" + e.getMessage();

        ErrorQuestion error = new ErrorQuestion(parent, message);
        error.exec();
        if (error.result() == QDialog.DialogCode.Accepted.value()) {
            File userDb = new File(SQLConstants.USER_DATABASE_FILE);
            userDb.delete();
        }
    }

    public static void popSQLDriverError(QWidget parent, Exception e) {
        ErrorMessage error = new ErrorMessage(parent, Constants.DRIVER_NOT_FOUND_ERROR);
        error.exec();
    }

    public static void popEveDbCorrupted(QWidget parent, Exception e) {
        String message = Constants.EVE_DB_CORRUPTED_ERROR;
        message += "\n" + e.getMessage();

        ErrorMessage error = new ErrorMessage(parent, message);
        error.exec();
    }
    
    public static void popConnectionError(QWidget parent) {
        ErrorMessage error = new ErrorMessage(parent, Constants.CONNECTION_ERROR);
        error.exec();
    }
    
    public static void popError(QWidget parent, String message) {
        ErrorMessage error = new ErrorMessage(parent, message);
        error.exec();
    }
    
}
