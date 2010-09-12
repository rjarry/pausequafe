#ifndef TESTNET_H
#define TESTNET_H

#include <QUrl>
#include <QWidget>
#include <QByteArray>

#include "ui_testnet.h"

#include "data/APIObject.h"


#define PORTRAIT_URL        "http://img.eve.is/serv.asp"
#define PORTRAIT_SIZE       256

#define BASE_URL            "http://api.eve-online.com"



class TestNet : public QWidget
{
    Q_OBJECT

private:
    Ui::TestNetClass ui;



private slots:
    void go();
    void processReply(APIObject * reply);

public:
    TestNet(QWidget *parent = 0);
    ~TestNet();
};

#endif // TESTNET_H
