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

import java.text.DecimalFormat;
import java.text.FieldPosition;

public class Formater {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;

    public static String printDouble(double number) {
        StringBuffer buff = new StringBuffer("");

        DecimalFormat form = new DecimalFormat();
        form.setGroupingSize(3);
        form.setMaximumFractionDigits(2);
        form.setMinimumFractionDigits(2);

        form.format(number, buff, new FieldPosition(1));

        return buff.toString();
    }

    public static String printLong(long number) {
        StringBuffer buff = new StringBuffer("");

        DecimalFormat form = new DecimalFormat();
        form.setGroupingSize(3);
        form.setMaximumFractionDigits(0);
        form.setMinimumFractionDigits(0);

        form.format(number, buff, new FieldPosition(1));

        return buff.toString();
    }

    public static String printPercent(double number) {
        StringBuffer buff = new StringBuffer("");

        DecimalFormat form = new DecimalFormat();
        form.setMaximumFractionDigits(0);
        form.setMinimumFractionDigits(0);

        form.format(number * 100.0, buff, new FieldPosition(1));

        return buff.toString();
    }

    public static String printTime(long time) {
        String timeString = "";

        int temp = (int) (time / DAY);
        switch (temp) {
        case 0:
            break;
        case 1:
            timeString += temp + " day ";
            break;
        default:
            timeString += temp + " days ";
        }

        temp = (int) ((time % DAY) / HOUR);
        switch (temp) {
        case 0:
            break;
        case 1:
            timeString += temp + " hour ";
            break;
        default:
            timeString += temp + " hours ";
        }

        temp = (int) ((time % HOUR) / MINUTE);
        switch (temp) {
        case 0:
            break;
        case 1:
            timeString += temp + " minute ";
            break;
        default:
            timeString += temp + " minutes ";
        }

        temp = (int) ((time % MINUTE) / SECOND);
        switch (temp) {
        case 0:
            break;
        case 1:
            timeString += temp + " second";
            break;
        default:
            timeString += temp + " seconds";
        }

        return timeString;
    }

    public static String printTimeCondensed(long time) {
        String timeString = "";

        int temp = (int) (time / DAY);
        if (temp != 0) {
            timeString += temp + "d ";
        }

        temp = (int) ((time % DAY) / HOUR);
        if (temp != 0) {
            timeString += temp + "h ";
        }

        temp = (int) ((time % HOUR) / MINUTE);
        if (temp != 0) {
            timeString += temp + "m ";
        }

        temp = (int) ((time % MINUTE) / SECOND);
        if (temp != 0) {
            timeString += temp + "s";
        }

        return timeString;
    }

    public static String printLevel(int level) {
        String romanNumber;
        switch (level) {
        case 1:
            romanNumber = "I";
            break;
        case 2:
            romanNumber = "II";
            break;
        case 3:
            romanNumber = "III";
            break;
        case 4:
            romanNumber = "IV";
            break;
        case 5:
            romanNumber = "V";
            break;
        default:
            romanNumber = "0";
        }
        return romanNumber;
    }
}
