/*****************************************************************************
 * This file is part of Pause Quafe.                                         *
 *                                                                           *
 * Pause Quafe - An Eve-Online(TM) character assistance application          *
 * Copyright (c) 2009  diabeteman & Kios Askoner                             *
 *                                                                           *
 * Pause Quafe is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by      *
 * the Free Software Foundation, either version 3 of the License, or         *
 * (at your option) any later version.                                       *
 *                                                                           *
 * Pause Quafe is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 * GNU General Public License for more details.                              *
 *                                                                           *
 * You should have received a copy of the GNU General Public License         *
 * along with Pause Quafe.  If not, see http://www.gnu.org/licenses/.        *
 *****************************************************************************/

#include <QLocale>

#include "misc/util/Formatter.h"

#include "misc/util/Constants.h"
#include "core/dao/ItemDAO.h"

QString Formatter::printDouble(double number)
{
	return QLocale::system().toString(number, 'f', 2);
}

QString Formatter::printLong(long number)
{
	return QLocale::system().toString((double) number, 'f', 0);
}

QString Formatter::printPercent(double number)
{
	return QLocale::system().toString(number * 100, 'f', 0) + "%";
}

QString Formatter::printTime(long time)
{
	QString timeString("");

	unsigned temp = (unsigned) (time / DAY);
	switch (temp) {
	case 0:
		break;
	case 1:
		timeString += temp + " day ";
		break;
	default:
		timeString += temp + " days ";
	}

	temp = (unsigned) ((time % DAY) / HOUR);
	switch (temp) {
	case 0:
		break;
	case 1:
		timeString += temp + " hour ";
		break;
	default:
		timeString += temp + " hours ";
	}

	temp = (unsigned) ((time % HOUR) / MINUTE);
	switch (temp) {
	case 0:
		break;
	case 1:
		timeString += temp + " minute ";
		break;
	default:
		timeString += temp + " minutes ";
	}

	temp = (unsigned) ((time % MINUTE) / SECOND);
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

QString Formatter::printTimeShort(long time)
{
	QString timeString("");

	unsigned temp = (unsigned) (time / DAY);
	if (temp != 0) {
		timeString += temp + "d ";
	}

	temp = (unsigned) ((time % DAY) / HOUR);
	if (temp != 0) {
		timeString += temp + "h ";
	}

	temp = (unsigned) ((time % HOUR) / MINUTE);
	if (temp != 0) {
		timeString += temp + "m ";
	}

	temp = (unsigned) ((time % MINUTE) / SECOND);
	if (temp != 0) {
		timeString += temp + "s";
	}

	return timeString;
}

QString Formatter::printLevel(unsigned level)
{
	QString romanNumber;
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


QString Formatter::printSkillInQueue(SkillInQueue & skill) {
    QString typeName;
    try {
        typeName = ItemDAO::findItemById(skill.getTypeID())->getTypeName();
    } catch (std::exception e) {
        typeName = skill.getTypeID() + "";
    }
    QString level = printLevel(skill.getLevel());
    QString trainingTime = printTime(skill.trainingTime());

    return typeName + " " + level + " <i>(" + trainingTime + ")</i>";
}

