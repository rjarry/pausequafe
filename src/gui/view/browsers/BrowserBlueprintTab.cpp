#include "BrowserBlueprintTab.h"

BrowserBlueprintTab::BrowserBlueprintTab(QWidget *parent) :
	QWidget(parent),
	ui(new Ui::BrowserBlueprintTab)
{
	ui->setupUi(this);
}

BrowserBlueprintTab::~BrowserBlueprintTab()
{
	delete ui;
}


void BrowserBlueprintTab::changeEvent(QEvent *e)
{
    QWidget::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}
