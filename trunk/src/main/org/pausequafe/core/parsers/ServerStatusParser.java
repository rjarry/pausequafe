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

package org.pausequafe.core.parsers;

import org.jdom.Document;
import org.jdom.Element;
import org.pausequafe.data.business.ServerStatus;

public class ServerStatusParser {

    public static ServerStatus getStatus(Document doc) {
        Element root = doc.getRootElement().getChild("result");

        boolean isOnline = root.getChildText("serverOpen").equals("True");
        int playerCount = Integer.parseInt(root.getChildText("onlinePlayers"));

        return new ServerStatus(isOnline, playerCount);
    }

}
