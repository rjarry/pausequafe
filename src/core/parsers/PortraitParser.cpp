/*
 * PortraitParser.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "core/parsers/PortraitParser.h"
#include "misc/util/Constants.h"

#include <QDir>
#include <QImageWriter>

Portrait* PortraitParser::parse(QByteArray rawData, QString characterName) {

    if (!Settings::APP_CACHE.exists()) {
        QDir::root().mkpath(Settings::APP_CACHE.path());
    }

    QString imageFileName = Settings::APP_CACHE.filePath(characterName + ".jpg");

    QFile imageFile(imageFileName);
    if (imageFile.open(QIODevice::WriteOnly)) {
        imageFile.write(rawData);
        imageFile.close();
    }

    return new Portrait(imageFileName);
}
