/*
 * PortraitParser.cpp
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#include "PortraitParser.h"
#include <QtGui/QDesktopServices>
#include <QDir>

Portrait* PortraitParser::parse(QByteArray rawData, QString characterName) {

    QString systemCache = QDesktopServices::storageLocation(QDesktopServices::CacheLocation);
    QDir cacheDir(systemCache + "/PauseQuafe/images/");
    if (!cacheDir.exists()) {
        QDir::root().mkpath(systemCache + "/PauseQuafe/images/");
    }

    QDir::root().cd(cacheDir.path());

    QString imageFileName = cacheDir.filePath(characterName + ".jpg");

    QFile imageFile(imageFileName);
    if (imageFile.open(QIODevice::WriteOnly)) {
        imageFile.write(rawData);
        imageFile.close();
    }

    return new Portrait(imageFileName);
}
