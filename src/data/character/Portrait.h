/*
 * Portrait.h
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#ifndef PORTRAIT_H_
#define PORTRAIT_H_

#include <Qstring>
#include "data/APIObject.h"

class Portrait: public APIObject {
private:
    QString filePath;

public:
    Portrait(QString filePath = QString());
    virtual ~Portrait();

    APIObject::Function function();
    QString toString();

    QString getFilePath() const;

    void setFilePath(QString filePath);

};

#endif /* PORTRAIT_H_ */
