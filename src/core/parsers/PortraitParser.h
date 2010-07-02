/*
 * PortraitParser.h
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#ifndef PORTRAITPARSER_H_
#define PORTRAITPARSER_H_

#include <QByteArray>
#include "data/character/Portrait.h"

class PortraitParser {
public:
    static Portrait* parse(QByteArray rawData);
};

#endif /* PORTRAITPARSER_H_ */
