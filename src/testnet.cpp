#include "testnet.h"
#include "data/character/APIData.h"
#include "core/network/APIRequest.h"

TestNet::TestNet(QWidget *parent)
    : QWidget(parent)
{
	ui.setupUi(this);

    ui.comboBox->addItem("Server Status", APIObject::SERVER_STATUS);
    ui.comboBox->addItem("Portrait", APIObject::PORTRAIT);
    ui.comboBox->addItem("Character List", APIObject::CHARACTERS);
    ui.comboBox->addItem("Character Sheet", APIObject::CHARACTER_SHEET);
    ui.comboBox->addItem("Skill in Training", APIObject::SKILL_IN_TRAINING);
    ui.comboBox->addItem("Skill Queue", APIObject::SKILL_QUEUE);

	connect(ui.pushButton, SIGNAL(pressed()),this , SLOT(go()));
}

TestNet::~TestNet()
{

}

void TestNet::go() {

    uint userID = ui.lineEdit->text().toInt();
    uint characterID = ui.lineEdit_2->text().toInt();
    QString apiKey = ui.lineEdit_3->text();

    APIData data(characterID, "diabeteman", userID, apiKey);

    APIObject::Function function;

    QVariant var = ui.comboBox->itemData(ui.comboBox->currentIndex());

    APIRequest* request = new APIRequest(data, function);

    connect(request, SIGNAL(responseReady(APIObject*)),
               this, SLOT(processReply(APIObject*)));

    request->go();
}

void TestNet::processReply(APIObject * obj) {
    ui.textBrowser->append(obj->toString());
    delete obj;
    ui.textBrowser->append("==========================================================\n");
}
