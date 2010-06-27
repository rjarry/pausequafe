#ifndef BROWSERBLUEPRINTTAB_H
#define BROWSERBLUEPRINTTAB_H

#include <QtGui/QWidget>
#include "ui_BrowserBlueprintTab.h"

namespace Ui {
	class BrowserBlueprintTab;
}

class BrowserBlueprintTab : public QWidget
{
    Q_OBJECT

public:
    BrowserBlueprintTab(QWidget *parent = 0);
    ~BrowserBlueprintTab();

protected:
    void changeEvent(QEvent *e);

private:
    Ui::BrowserBlueprintTab* ui;
};

#endif // BROWSERBLUEPRINTTAB_H
