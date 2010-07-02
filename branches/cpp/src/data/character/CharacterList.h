/*
 * CharacterList.h
 *
 *  Created on: 1 juil. 2010
 *      Author: diabeteman
 */

#ifndef CHARACTERLIST_H_
#define CHARACTERLIST_H_

#include <QList>
#include "data/APIObject.h"
#include "data/character/APIData.h"

class CharacterList: public APIObject {

private:
    QList<APIData> list;

public:
    CharacterList();
    virtual ~CharacterList();

    APIFunction function();

    QList<APIData> getList() const;

    void setList(QList<APIData> list);

};

#endif /* CHARACTERLIST_H_ */
